package de.metas.banking.impexp;

import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_ErrorMsg;
import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.banking.model.I_C_BankStatement;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class BankStatementImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(BankStatementImportTableSqlUpdater.class);

	private void dbUpdateOrgs(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE "
				+ I_C_BankStatement.Table_Name
				+ " i "
				+ "SET AD_Org_ID=(SELECT AD_Org_ID FROM AD_Org o"
				+ " WHERE i.OrgValue=o.Value AND o.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.debug("Set Org ={}", no);
		//
		sql = new StringBuilder("UPDATE "
				+ I_C_BankStatement.Table_Name
				+ " i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Greeting, ' "
				+ "WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Invalid Org={}", no);
	}

	private void updateBankAccount(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BankStatement i "
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
				+ "OR i.I_IsImported IS NULL").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Bank Account (With Routing No)=", no);

		// TODO: What's this?
		// sql = new StringBuilder("UPDATE I_BankStatement i "
		// + "SET C_BP_BankAccount_ID=(SELECT C_BP_BankAccount_ID FROM C_BP_BankAccount a WHERE a.C_BP_BankAccount_ID=").append(p_C_BP_BankAccount_ID);
		// sql.append(" and a.AD_Client_ID=i.AD_Client_ID) "
		// + "WHERE i.C_BP_BankAccount_ID IS NULL "
		// + "AND i.BankAccountNo IS NULL "
		// + "AND i.I_isImported<>'Y' "
		// + "OR i.I_isImported IS NULL").append(whereClause);
		//
		// no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		//
		// if (no != 0)
		// {
		// logger.info("Bank Account=", no);
		// }

		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET I_isImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Bank Account, ' "
				+ "WHERE C_BP_BankAccount_ID IS NULL "
				+ "AND I_isImported<>'Y' "
				+ "OR I_isImported IS NULL").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.warn("Invalid Bank Account=", no);

	}

	private void updateCurrency(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BankStatement i "
				+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c"
				+ " WHERE i.ISO_Code=c.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE C_Currency_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		if (no != 0)
		{
			logger.info("Set Currency=", no);
		}

		sql = new StringBuilder("UPDATE I_BankStatement i "
				+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_BP_BankAccount WHERE C_BP_BankAccount_ID=i.C_BP_BankAccount_ID) "
				+ "WHERE i.C_Currency_ID IS NULL "
				+ "AND i.ISO_Code IS NULL").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Set Currency=", no);

		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Currency,' "
				+ "WHERE C_Currency_ID IS NULL "
				+ "AND I_IsImported<>'E' "
				+ " AND I_IsImported<>'Y'").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.warn("Invalid Currency=", no);
	}

	private void updateAmount(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET ChargeAmt=0 "
				+ "WHERE ChargeAmt IS NULL "
				+ "AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Charge Amount=", no);

		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET InterestAmt=0 "
				+ "WHERE InterestAmt IS NULL "
				+ "AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Interest Amount=", no);

		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET TrxAmt=StmtAmt - InterestAmt - ChargeAmt "
				+ "WHERE TrxAmt IS NULL "
				+ "AND I_IsImported<>'Y'").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Transaction Amount=", no);

		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET I_isImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Amount, ' "
				+ "WHERE TrxAmt + ChargeAmt + InterestAmt <> StmtAmt "
				+ "AND I_isImported<>'Y'").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Invaid Amount=", no);
	}

	private void updateValutaDate(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET ValutaDate=StatementLineDate "
				+ "WHERE ValutaDate IS NULL "
				+ "AND I_isImported<>'Y'").append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Valuta Date=", no);
	}

	private void updateCheckPaymentInvoiceCombination(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Payment<->Invoice, ' "
				+ "WHERE I_BankStatement_ID IN "
				+ "(SELECT I_BankStatement_ID "
				+ "FROM I_BankStatement i"
				+ " INNER JOIN C_Payment p ON (i.C_Payment_ID=p.C_Payment_ID) "
				+ "WHERE i.C_Invoice_ID IS NOT NULL "
				+ " AND p.C_Invoice_ID IS NOT NULL "
				+ " AND p.C_Invoice_ID<>i.C_Invoice_ID) ")
						.append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Payment<->Invoice Mismatch=", no);
	}

	private void updateCheckPaymentBPartnerCombination(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Payment<->BPartner, ' "
				+ "WHERE I_BankStatement_ID IN "
				+ "(SELECT I_BankStatement_ID "
				+ "FROM I_BankStatement i"
				+ " INNER JOIN C_Payment p ON (i.C_Payment_ID=p.C_Payment_ID) "
				+ "WHERE i.C_BPartner_ID IS NOT NULL "
				+ " AND p.C_BPartner_ID IS NOT NULL "
				+ " AND p.C_BPartner_ID<>i.C_BPartner_ID) ")
						.append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		logger.info("Payment<->BPartner Mismatch=", no);
	}

	private void updateCheckInvoiceBPartnerCombination(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BankStatement "
				 + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Invoice<->BPartner, ' "
				 + "WHERE I_BankStatement_ID IN "
				 + "(SELECT I_BankStatement_ID "
				 + "FROM I_BankStatement i"
				 + " INNER JOIN C_Invoice v ON (i.C_Invoice_ID=v.C_Invoice_ID) "
				 + "WHERE i.C_BPartner_ID IS NOT NULL "
				 + " AND v.C_BPartner_ID IS NOT NULL "
				 + " AND v.C_BPartner_ID<>i.C_BPartner_ID) ")
				 .append(whereClause);

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);


				 logger.info("Invoice<->BPartner Mismatch=", no);
	}

}

//

//
// // Check Invoice<->BPartner combination

//
// // Check Invoice.BPartner<->Payment.BPartner combination
// sql = new StringBuffer("UPDATE I_BankStatement "
// + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Invoice.BPartner<->Payment.BPartner, ' "
// + "WHERE I_BankStatement_ID IN "
// + "(SELECT I_BankStatement_ID "
// + "FROM I_BankStatement i"
// + " INNER JOIN C_Invoice v ON (i.C_Invoice_ID=v.C_Invoice_ID)"
// + " INNER JOIN C_Payment p ON (i.C_Payment_ID=p.C_Payment_ID) "
// + "WHERE p.C_Invoice_ID<>v.C_Invoice_ID"
// + " AND v.C_BPartner_ID<>p.C_BPartner_ID) ")
// .append(clientCheck);
// no = DB.executeUpdate(sql.toString(), get_TrxName());
// if (no != 0)
// log.info("Invoice.BPartner<->Payment.BPartner Mismatch=" + no);
//
// // Detect Duplicates
// sql = new StringBuffer("SELECT i.I_BankStatement_ID, l.C_BankStatementLine_ID, i.EftTrxID "
// + "FROM I_BankStatement i, C_BankStatement s, C_BankStatementLine l "
// + "WHERE i.I_isImported='N' "
// + "AND s.C_BankStatement_ID=l.C_BankStatement_ID "
// + "AND i.EftTrxID IS NOT NULL AND "
// // Concatenate EFT Info
// + "(l.EftTrxID||l.EftAmt||l.EftStatementLineDate||l.EftValutaDate||l.EftTrxType||l.EftCurrency||l.EftReference||s.EftStatementReference "
// + "||l.EftCheckNo||l.EftMemo||l.EftPayee||l.EftPayeeAccount) "
// + "= "
// + "(i.EftTrxID||i.EftAmt||i.EftStatementLineDate||i.EftValutaDate||i.EftTrxType||i.EftCurrency||i.EftReference||i.EftStatementReference "
// + "||i.EftCheckNo||i.EftMemo||i.EftPayee||i.EftPayeeAccount) ");
//
// StringBuffer updateSql = new StringBuffer("UPDATE I_Bankstatement "
// + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Duplicate['||?||']' "
// + "WHERE I_BankStatement_ID=?").append(clientCheck);
// PreparedStatement pupdt = DB.prepareStatement(updateSql.toString(), get_TrxName());
//
// PreparedStatement pstmtDuplicates = null;
// no = 0;
// try
// {
// pstmtDuplicates = DB.prepareStatement(sql.toString(), get_TrxName());
// ResultSet rs = pstmtDuplicates.executeQuery();
// while (rs.next())
// {
// String info = "Line_ID=" + rs.getInt(2) // l.C_BankStatementLine_ID
// + ",EDTTrxID=" + rs.getString(3); // i.EftTrxID
// pupdt.setString(1, info);
// pupdt.setInt(2, rs.getInt(1)); // i.I_BankStatement_ID
// pupdt.executeUpdate();
// no++;
// }
// rs.close();
// pstmtDuplicates.close();
// pupdt.close();
//
// rs = null;
// pstmtDuplicates = null;
// pupdt = null;
// }
// catch(Exception e)
// {
// log.error("DetectDuplicates " + e.getMessage());
// }
// if (no != 0)
// log.info("Duplicates=" + no);
//
// commitEx();
//
// //Import Bank Statement
// sql = new StringBuffer("SELECT * FROM I_BankStatement"
// + " WHERE I_IsImported='N'"
// + " ORDER BY C_BP_BankAccount_ID, Name, EftStatementDate, EftStatementReference");
//
// MBankStatement statement = null;
// I_C_BP_BankAccount account = null;
// PreparedStatement pstmt = null;
// int lineNo = 10;
// int noInsert = 0;
// int noInsertLine = 0;
// try
// {
// pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
// ResultSet rs = pstmt.executeQuery();
//
// while (rs.next())
// {
// X_I_BankStatement imp = new X_I_BankStatement(m_ctx, rs, get_TrxName());
// // Get the bank account for the first statement
// if (account == null)
// {
// account = InterfaceWrapperHelper.create(m_ctx, imp.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class, getTrxName());
// statement = null;
// log.info("New Statement, Account=" + account.getAccountNo());
// }
// // Create a new Bank Statement for every account
// else if (account.getC_BP_BankAccount_ID() != imp.getC_BP_BankAccount_ID())
// {
// account = InterfaceWrapperHelper.create(m_ctx, imp.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class, getTrxName());
// statement = null;
// log.info("New Statement, Account=" + account.getAccountNo());
// }
// // Create a new Bank Statement for every statement name
// else if ((statement.getName() != null) && (imp.getName() != null))
// {
// if (!statement.getName().equals(imp.getName()))
// {
// statement = null;
// log.info("New Statement, Statement Name=" + imp.getName());
// }
// }
// // Create a new Bank Statement for every statement reference
// else if ((statement.getEftStatementReference() != null) && (imp.getEftStatementReference() != null))
// {
// if (!statement.getEftStatementReference().equals(imp.getEftStatementReference()))
// {
// statement = null;
// log.info("New Statement, Statement Reference=" + imp.getEftStatementReference());
// }
// }
// // Create a new Bank Statement for every statement date
// else if ((statement.getStatementDate() != null) && (imp.getStatementDate() != null))
// {
// if (!statement.getStatementDate().equals(imp.getStatementDate()))
// {
// statement = null;
// log.info("New Statement, Statement Date=" + imp.getStatementDate());
// }
// }
//
// // New Statement
// if (statement == null)
// {
// statement = new MBankStatement(account);
// statement.setEndingBalance(Env.ZERO);
//
// // Copy statement data
// if (imp.getName() != null)
// {
// statement.setName(imp.getName());
// }
// if (imp.getStatementDate() != null)
// {
// statement.setStatementDate(imp.getStatementDate());
// }
// statement.setDescription(imp.getDescription());
// statement.setEftStatementReference(imp.getEftStatementReference());
// statement.setEftStatementDate(imp.getEftStatementDate());
// if (statement.save())
// {
// noInsert++;
// }
// lineNo = 10;
// }
//
// // New StatementLine
// MBankStatementLine line = new MBankStatementLine(statement, lineNo);
//
// // Copy statement line data
// //line.setC_BPartner_ID(imp.getC_BPartner_ID());
// //line.setC_Invoice_ID(imp.getC_Invoice_ID());
// line.setReferenceNo(imp.getReferenceNo());
// line.setDescription(imp.getLineDescription());
// line.setStatementLineDate(imp.getStatementLineDate());
// line.setDateAcct(imp.getStatementLineDate());
// line.setValutaDate(imp.getValutaDate());
// line.setIsReversal(imp.isReversal());
// line.setC_Currency_ID(imp.getC_Currency_ID());
// line.setTrxAmt(imp.getTrxAmt());
// line.setStmtAmt(imp.getStmtAmt());
// if (imp.getC_Charge_ID() != 0)
// {
// line.setC_Charge_ID(imp.getC_Charge_ID());
// }
// line.setInterestAmt(imp.getInterestAmt());
// line.setChargeAmt(imp.getChargeAmt());
// line.setMemo(imp.getMemo());
// if (imp.getC_Payment_ID() != 0)
// {
// line.setC_Payment_ID(imp.getC_Payment_ID());
// }
//
// // Copy statement line reference data
// line.setEftTrxID(imp.getEftTrxID());
// line.setEftTrxType(imp.getEftTrxType());
// line.setEftCheckNo(imp.getEftCheckNo());
// line.setEftReference(imp.getEftReference());
// line.setEftMemo(imp.getEftMemo());
// line.setEftPayee(imp.getEftPayee());
// line.setEftPayeeAccount(imp.getEftPayeeAccount());
// line.setEftStatementLineDate(imp.getEftStatementLineDate());
// line.setEftValutaDate(imp.getEftValutaDate());
// line.setEftCurrency(imp.getEftCurrency());
// line.setEftAmt(imp.getEftAmt());
//
// // Save statement line
// if (line.save())
// {
// imp.setC_BankStatement_ID(statement.getC_BankStatement_ID());
// imp.setC_BankStatementLine_ID(line.getC_BankStatementLine_ID());
// imp.setI_IsImported(true);
// imp.setProcessed(true);
// imp.save();
// noInsertLine++;
// lineNo += 10;
// }
// line = null;
//
// }
//
// // Close database connection
// rs.close();
// pstmt.close();
// rs = null;
// pstmt = null;
//
// }
// catch(Exception e)
// {
// log.error(sql.toString(), e);
// }
//
// // Set Error to indicator to not imported
// sql = new StringBuffer ("UPDATE I_BankStatement "
// + "SET I_IsImported='N', Updated=now() "
// + "WHERE I_IsImported<>'Y'").append(clientCheck);
// no = DB.executeUpdate(sql.toString(), get_TrxName());
// addLog (0, null, new BigDecimal (no), "@Errors@");
// //
// addLog (0, null, new BigDecimal (noInsert), "@C_BankStatement_ID@: @Inserted@");
// addLog (0, null, new BigDecimal (noInsertLine), "@C_BankStatementLine_ID@: @Inserted@");
// return "";
//
// } // doIt
//
// }
//
// }
