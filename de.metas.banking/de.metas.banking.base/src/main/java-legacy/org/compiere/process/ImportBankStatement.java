/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.X_I_BankStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 *	Import Bank Statement from I_BankStatement
 *
 *	author Eldir Tomassen
 *	@version $Id: ImportBankStatement.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ImportBankStatement extends SvrProcess
{
	/**	Client to be imported to		*/
	private int				p_AD_Client_ID = 0;
	/**	Organization to be imported to	*/
	private int				p_AD_Org_ID = 0;
	/** Default Bank Account			*/
	private int				p_C_BP_BankAccount_ID = 0;
	/**	Delete old Imported				*/
	private boolean			p_deleteOldImported = false;
	
	/** Properties						*/
	private Properties 		m_ctx;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Client_ID"))
				p_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_BP_BankAccount_ID"))
				p_C_BP_BankAccount_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DeleteOldImported"))
				p_deleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
		m_ctx = Env.getCtx();
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	@Override
	protected String doIt() throws java.lang.Exception
	{
		log.info("AD_Org_ID=" + p_AD_Org_ID + ", C_BP_BankAccount_ID" + p_C_BP_BankAccount_ID);
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + p_AD_Client_ID;

		//	****	Prepare	****

		//	Delete Old Imported
		if (p_deleteOldImported)
		{
			sql = new StringBuffer ("DELETE FROM I_BankStatement "
				  + "WHERE I_IsImported='Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.debug("Deleted Old Imported =" + no);
		}

		//	Set Client, Org, IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_BankStatement "
			  + "SET AD_Client_ID = COALESCE (AD_Client_ID,").append (p_AD_Client_ID).append ("),"
			  + " AD_Org_ID = COALESCE (AD_Org_ID,").append (p_AD_Org_ID).append ("),");
		sql.append(" IsActive = COALESCE (IsActive, 'Y'),"
			  + " Created = COALESCE (Created, now()),"
			  + " CreatedBy = COALESCE (CreatedBy, 0),"
			  + " Updated = COALESCE (Updated, now()),"
			  + " UpdatedBy = COALESCE (UpdatedBy, 0),"
			  + " I_ErrorMsg = ' ',"
			  + " I_IsImported = 'N' "
			  + "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL OR AD_Client_ID IS NULL OR AD_Org_ID IS NULL OR AD_Client_ID=0 OR AD_Org_ID=0");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Reset=" + no);

		sql = new StringBuffer ("UPDATE I_BankStatement o "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Org, '"
			+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0"
			+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Org=" + no);
			
		//	Set Bank Account
		sql = new StringBuffer("UPDATE I_BankStatement i "
			+ "SET C_BP_BankAccount_ID="
			+ "( "
			+ " SELECT C_BP_BankAccount_ID "
			+ " FROM C_BP_BankAccount a, C_Bank b "
			+ " WHERE b.IsOwnBank='Y' "
			+ " AND a.AD_Client_ID=i.AD_Client_ID "
			+ " AND a.C_Bank_ID=b.C_Bank_ID "
			+ " AND a.AccountNo=i.BankAccountNo "
			+ " AND b.RoutingNo=i.RoutingNo "
			+ " OR b.SwiftCode=i.RoutingNo "
			+ ") "
			+ "WHERE i.C_BP_BankAccount_ID IS NULL "
			+ "AND i.I_IsImported<>'Y' "
			+ "OR i.I_IsImported IS NULL").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Bank Account (With Routing No)=" + no);
		//
		sql = new StringBuffer("UPDATE I_BankStatement i " 
		 	+ "SET C_BP_BankAccount_ID="
			+ "( "
			+ " SELECT C_BP_BankAccount_ID "
			+ " FROM C_BP_BankAccount a, C_Bank b "
			+ " WHERE b.IsOwnBank='Y' "
			+ " AND a.C_Bank_ID=b.C_Bank_ID " 
			+ " AND a.AccountNo=i.BankAccountNo "
			+ " AND a.AD_Client_ID=i.AD_Client_ID "
			+ ") "
			+ "WHERE i.C_BP_BankAccount_ID IS NULL "
			+ "AND i.I_isImported<>'Y' "
			+ "OR i.I_isImported IS NULL").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Bank Account (Without Routing No)=" + no);
		//
		sql = new StringBuffer("UPDATE I_BankStatement i "
			+ "SET C_BP_BankAccount_ID=(SELECT C_BP_BankAccount_ID FROM C_BP_BankAccount a WHERE a.C_BP_BankAccount_ID=").append(p_C_BP_BankAccount_ID);
		sql.append(" and a.AD_Client_ID=i.AD_Client_ID) "
			+ "WHERE i.C_BP_BankAccount_ID IS NULL "
			+ "AND i.BankAccountNo IS NULL "
			+ "AND i.I_isImported<>'Y' "
			+ "OR i.I_isImported IS NULL").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Bank Account=" + no);
		//	
		sql = new StringBuffer("UPDATE I_BankStatement "
			+ "SET I_isImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Bank Account, ' "
			+ "WHERE C_BP_BankAccount_ID IS NULL "
			+ "AND I_isImported<>'Y' "
			+ "OR I_isImported IS NULL").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Bank Account=" + no);
		 
		//	Set Currency
		sql = new StringBuffer ("UPDATE I_BankStatement i "
			+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c"
			+ " WHERE i.ISO_Code=c.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
			+ "WHERE C_Currency_ID IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Set Currency=" + no);
		//
		sql = new StringBuffer("UPDATE I_BankStatement i "
			+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_BP_BankAccount WHERE C_BP_BankAccount_ID=i.C_BP_BankAccount_ID) "
			+ "WHERE i.C_Currency_ID IS NULL "
			+ "AND i.ISO_Code IS NULL").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Set Currency=" + no);
		//
		sql = new StringBuffer ("UPDATE I_BankStatement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Currency,' "
			+ "WHERE C_Currency_ID IS NULL "
			+ "AND I_IsImported<>'E' "
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Currency=" + no);
		
		 
		//	Set Amount
		 sql = new StringBuffer("UPDATE I_BankStatement "
		 	+ "SET ChargeAmt=0 "
			+ "WHERE ChargeAmt IS NULL "
			+ "AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Charge Amount=" + no);
		//
		 sql = new StringBuffer("UPDATE I_BankStatement "
		 	+ "SET InterestAmt=0 "
			+ "WHERE InterestAmt IS NULL "
			+ "AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Interest Amount=" + no);
		//
		 sql = new StringBuffer("UPDATE I_BankStatement "
		 	+ "SET TrxAmt=StmtAmt - InterestAmt - ChargeAmt "
			+ "WHERE TrxAmt IS NULL "
			+ "AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Transaction Amount=" + no);
		//
		sql = new StringBuffer("UPDATE I_BankStatement "
			+ "SET I_isImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Amount, ' "
			+ "WHERE TrxAmt + ChargeAmt + InterestAmt <> StmtAmt "
			+ "AND I_isImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Invaid Amount=" + no);
		 
		 //	Set Valuta Date
		sql = new StringBuffer("UPDATE I_BankStatement "
		 	+ "SET ValutaDate=StatementLineDate "
			+ "WHERE ValutaDate IS NULL "
			+ "AND I_isImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Valuta Date=" + no);
			
		//	Check Payment<->Invoice combination
		sql = new StringBuffer("UPDATE I_BankStatement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Payment<->Invoice, ' "
			+ "WHERE I_BankStatement_ID IN "
				+ "(SELECT I_BankStatement_ID "
				+ "FROM I_BankStatement i"
				+ " INNER JOIN C_Payment p ON (i.C_Payment_ID=p.C_Payment_ID) "
				+ "WHERE i.C_Invoice_ID IS NOT NULL "
				+ " AND p.C_Invoice_ID IS NOT NULL "
				+ " AND p.C_Invoice_ID<>i.C_Invoice_ID) ")
			.append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Payment<->Invoice Mismatch=" + no);
			
		//	Check Payment<->BPartner combination
		sql = new StringBuffer("UPDATE I_BankStatement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Payment<->BPartner, ' "
			+ "WHERE I_BankStatement_ID IN "
				+ "(SELECT I_BankStatement_ID "
				+ "FROM I_BankStatement i"
				+ " INNER JOIN C_Payment p ON (i.C_Payment_ID=p.C_Payment_ID) "
				+ "WHERE i.C_BPartner_ID IS NOT NULL "
				+ " AND p.C_BPartner_ID IS NOT NULL "
				+ " AND p.C_BPartner_ID<>i.C_BPartner_ID) ")
			.append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Payment<->BPartner Mismatch=" + no);
			
		//	Check Invoice<->BPartner combination
		sql = new StringBuffer("UPDATE I_BankStatement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Invoice<->BPartner, ' "
			+ "WHERE I_BankStatement_ID IN "
				+ "(SELECT I_BankStatement_ID "
				+ "FROM I_BankStatement i"
				+ " INNER JOIN C_Invoice v ON (i.C_Invoice_ID=v.C_Invoice_ID) "
				+ "WHERE i.C_BPartner_ID IS NOT NULL "
				+ " AND v.C_BPartner_ID IS NOT NULL "
				+ " AND v.C_BPartner_ID<>i.C_BPartner_ID) ")
			.append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Invoice<->BPartner Mismatch=" + no);
			
		//	Check Invoice.BPartner<->Payment.BPartner combination
		sql = new StringBuffer("UPDATE I_BankStatement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Invoice.BPartner<->Payment.BPartner, ' "
			+ "WHERE I_BankStatement_ID IN "
				+ "(SELECT I_BankStatement_ID "
				+ "FROM I_BankStatement i"
				+ " INNER JOIN C_Invoice v ON (i.C_Invoice_ID=v.C_Invoice_ID)"
				+ " INNER JOIN C_Payment p ON (i.C_Payment_ID=p.C_Payment_ID) "
				+ "WHERE p.C_Invoice_ID<>v.C_Invoice_ID"
				+ " AND v.C_BPartner_ID<>p.C_BPartner_ID) ")
			.append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.info("Invoice.BPartner<->Payment.BPartner Mismatch=" + no);
			
		//	Detect Duplicates
		 sql = new StringBuffer("SELECT i.I_BankStatement_ID, l.C_BankStatementLine_ID, i.EftTrxID "
			+ "FROM I_BankStatement i, C_BankStatement s, C_BankStatementLine l "
			+ "WHERE i.I_isImported='N' "
			+ "AND s.C_BankStatement_ID=l.C_BankStatement_ID "
			+ "AND i.EftTrxID IS NOT NULL AND "
			//	Concatenate EFT Info
			+ "(l.EftTrxID||l.EftAmt||l.EftStatementLineDate||l.EftValutaDate||l.EftTrxType||l.EftCurrency||l.EftReference||s.EftStatementReference "
			+ "||l.EftCheckNo||l.EftMemo||l.EftPayee||l.EftPayeeAccount) "
			+ "= "
			+ "(i.EftTrxID||i.EftAmt||i.EftStatementLineDate||i.EftValutaDate||i.EftTrxType||i.EftCurrency||i.EftReference||i.EftStatementReference "
			+ "||i.EftCheckNo||i.EftMemo||i.EftPayee||i.EftPayeeAccount) ");
		
		StringBuffer updateSql = new StringBuffer("UPDATE I_Bankstatement "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Duplicate['||?||']' "
				+ "WHERE I_BankStatement_ID=?").append(clientCheck);
		PreparedStatement pupdt = DB.prepareStatement(updateSql.toString(), get_TrxName());
		
		PreparedStatement pstmtDuplicates = null;
		no = 0;
		try
		{
			pstmtDuplicates = DB.prepareStatement(sql.toString(), get_TrxName());
			ResultSet rs = pstmtDuplicates.executeQuery();
			while (rs.next())
			{
				String info = "Line_ID=" + rs.getInt(2)		//	l.C_BankStatementLine_ID
				 + ",EDTTrxID=" + rs.getString(3);			//	i.EftTrxID
				pupdt.setString(1, info);	
				pupdt.setInt(2, rs.getInt(1));	//	i.I_BankStatement_ID
				pupdt.executeUpdate();
				no++;
			}
			rs.close();
			pstmtDuplicates.close();
			pupdt.close();
			
			rs = null;
			pstmtDuplicates = null;
			pupdt = null;
		}
		catch(Exception e)
		{
			log.error("DetectDuplicates " + e.getMessage());
		}
		if (no != 0)
			log.info("Duplicates=" + no);
		
		commitEx();
		
		//Import Bank Statement
		sql = new StringBuffer("SELECT * FROM I_BankStatement"
			+ " WHERE I_IsImported='N'"
			+ " ORDER BY C_BP_BankAccount_ID, Name, EftStatementDate, EftStatementReference");
			
		MBankStatement statement = null;
		I_C_BP_BankAccount account = null;
		PreparedStatement pstmt = null;
		int lineNo = 10;
		int noInsert = 0;
		int noInsertLine = 0;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery();
				
			while (rs.next())
			{ 
				X_I_BankStatement imp = new X_I_BankStatement(m_ctx, rs, get_TrxName());
				//	Get the bank account for the first statement
				if (account == null)
				{
					account = InterfaceWrapperHelper.create(m_ctx, imp.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class, getTrxName());
					statement = null;
					log.info("New Statement, Account=" + account.getAccountNo());
				}
				//	Create a new Bank Statement for every account
				else if (account.getC_BP_BankAccount_ID() != imp.getC_BP_BankAccount_ID())
				{
					account = InterfaceWrapperHelper.create(m_ctx, imp.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class, getTrxName());
					statement = null;
					log.info("New Statement, Account=" + account.getAccountNo());
				}
				//	Create a new Bank Statement for every statement name
				else if ((statement.getName() != null) && (imp.getName() != null))
				{
					if (!statement.getName().equals(imp.getName()))
					{
						statement = null;
						log.info("New Statement, Statement Name=" + imp.getName());
					}
				}
				//	Create a new Bank Statement for every statement reference
				else if ((statement.getEftStatementReference() != null) && (imp.getEftStatementReference() != null))
				{
					if (!statement.getEftStatementReference().equals(imp.getEftStatementReference()))
					{
						statement = null;
						log.info("New Statement, Statement Reference=" + imp.getEftStatementReference());
					}
				}
				//	Create a new Bank Statement for every statement date
				else if ((statement.getStatementDate() != null) && (imp.getStatementDate() != null))
				{
					if (!statement.getStatementDate().equals(imp.getStatementDate()))
					{
						statement = null;
						log.info("New Statement, Statement Date=" + imp.getStatementDate());
					}
				}
				
				//	New Statement
				if (statement == null)
				{
					statement = new MBankStatement(account);
					statement.setEndingBalance(Env.ZERO);
					
					//	Copy statement data
					if (imp.getName() != null)
					{
						statement.setName(imp.getName());
					}
					if (imp.getStatementDate() != null)
					{
						statement.setStatementDate(imp.getStatementDate());
					}
					statement.setDescription(imp.getDescription());
					statement.setEftStatementReference(imp.getEftStatementReference());
					statement.setEftStatementDate(imp.getEftStatementDate());
					if (statement.save())
					{
						noInsert++;
					}
					lineNo = 10;
				}
				
				//	New StatementLine
				MBankStatementLine line = new MBankStatementLine(statement, lineNo);
				
				//	Copy statement line data
				//line.setC_BPartner_ID(imp.getC_BPartner_ID());
				//line.setC_Invoice_ID(imp.getC_Invoice_ID());
				line.setReferenceNo(imp.getReferenceNo());
				line.setDescription(imp.getLineDescription());
				line.setStatementLineDate(imp.getStatementLineDate());
				line.setDateAcct(imp.getStatementLineDate());
				line.setValutaDate(imp.getValutaDate());
				line.setIsReversal(imp.isReversal());
				line.setC_Currency_ID(imp.getC_Currency_ID());
				line.setTrxAmt(imp.getTrxAmt());
				line.setStmtAmt(imp.getStmtAmt());
				if (imp.getC_Charge_ID() != 0)
				{
					line.setC_Charge_ID(imp.getC_Charge_ID());
				}
				line.setInterestAmt(imp.getInterestAmt());
				line.setChargeAmt(imp.getChargeAmt());
				line.setMemo(imp.getMemo());
				if (imp.getC_Payment_ID() != 0)
				{
					line.setC_Payment_ID(imp.getC_Payment_ID());
				}
				
				//	Copy statement line reference data
				line.setEftTrxID(imp.getEftTrxID());
				line.setEftTrxType(imp.getEftTrxType());
				line.setEftCheckNo(imp.getEftCheckNo());
				line.setEftReference(imp.getEftReference());
				line.setEftMemo(imp.getEftMemo());
				line.setEftPayee(imp.getEftPayee());
				line.setEftPayeeAccount(imp.getEftPayeeAccount());
				line.setEftStatementLineDate(imp.getEftStatementLineDate());
				line.setEftValutaDate(imp.getEftValutaDate());
				line.setEftCurrency(imp.getEftCurrency());
				line.setEftAmt(imp.getEftAmt());
				
				//	Save statement line
				if (line.save())
				{
					imp.setC_BankStatement_ID(statement.getC_BankStatement_ID());
					imp.setC_BankStatementLine_ID(line.getC_BankStatementLine_ID());
					imp.setI_IsImported(true);
					imp.setProcessed(true);
					imp.save();
					noInsertLine++;
					lineNo += 10;	
				}
				line = null;
				
			}
			
			//	Close database connection
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;

		}
		catch(Exception e)
		{
			log.error(sql.toString(), e);
		}
		
		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_BankStatement "
			+ "SET I_IsImported='N', Updated=now() "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		//
		addLog (0, null, new BigDecimal (noInsert), "@C_BankStatement_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noInsertLine), "@C_BankStatementLine_ID@: @Inserted@");
		return "";

	}	//	doIt

}	//	ImportBankStatement
