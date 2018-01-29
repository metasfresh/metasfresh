/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MElementValue;
import org.compiere.util.DB;
import org.eevolution.model.I_HR_Process;
import org.eevolution.model.MHRMovement;
import org.eevolution.model.MHRProcess;
import org.eevolution.model.X_HR_Concept_Acct;

/**
 * Post Payroll Documents.
 * 
 * <pre>
 *  Table:              HR_Process (??)
 *  Document Types:     HR_Process
 * </pre>
 * 
 * @author Oscar Gomez Islas
 * @version $Id: Doc_Payroll.java,v 1.1 2007/01/20 00:40:02 ogomezi Exp $
 * @author Cristina Ghita, www.arhipac.ro
 */
public class Doc_HRProcess extends Doc<DocLine_Payroll>
{
	/** Process Payroll **/
	public static final String DOCTYPE_Payroll = "HRP";

	/**
	 * Constructor
	 * 
	 * @param ass accounting schema
	 * @param rs record
	 * @parem trxName trx
	 */
	public Doc_HRProcess(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_Payroll);
	}	// Doc_Payroll

	@Override
	protected void loadDocumentDetails()
	{
		final I_HR_Process process = getModel(I_HR_Process.class);
		setDateDoc(getDateAcct());
		setDocLines(loadLines(process));
	}

	/**
	 * Load Payroll Line
	 * 
	 * @param Payroll Process
	 * @return DocLine Array
	 */
	private List<DocLine_Payroll> loadLines(I_HR_Process process)
	{
		List<DocLine_Payroll> list = new ArrayList<>();
		final MHRProcess processPO = LegacyAdapters.convertToPO(process);
		for (final MHRMovement line : processPO.getLines(true))
		{
			DocLine_Payroll docLine = new DocLine_Payroll(line, this);
			//
			list.add(docLine);
		}

		return list;
	}

	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as)
	{
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		String sql = "SELECT m.HR_Concept_id, MAX(c.Name), SUM(m.Amount), MAX(c.AccountSign), MAX(CA.IsBalancing), e.AD_Org_ID, d.C_Activity_ID" // 1,2,3,4,5,6,7
				+ " FROM HR_Movement m"
				+ " INNER JOIN HR_Concept_Acct ca ON (ca.HR_Concept_ID=m.HR_Concept_ID AND ca.IsActive = 'Y')"
				+ " INNER JOIN HR_Concept      c  ON (c.HR_Concept_ID=m.HR_Concept_ID AND c.IsActive = 'Y')"
				+ " INNER JOIN C_BPartner      bp ON (bp.C_BPartner_ID = m.C_BPartner_ID)"
				+ " INNER JOIN HR_Employee	 e  ON (bp.C_BPartner_ID=e.C_BPartner_ID)"
				+ " INNER JOIN HR_Department   d  ON (d.HR_Department_ID=e.HR_Department_ID)"
				+ " WHERE m.HR_Process_ID=? AND (m.Qty <> 0 OR m.Amount <> 0) AND c.AccountSign != 'N' AND ca.IsBalancing != 'Y'"
				+ " GROUP BY m.HR_Concept_ID,e.AD_Org_ID,d.C_Activity_ID"
				+ " ORDER BY e.AD_Org_ID,d.C_Activity_ID";

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setInt(1, get_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int HR_Concept_ID = rs.getInt(1);
				BigDecimal sumAmount = rs.getBigDecimal(3);
				// round amount according to currency
				sumAmount = sumAmount.setScale(as.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
				String AccountSign = rs.getString(4);
				int AD_OrgTrx_ID = rs.getInt(6);
				int C_Activity_ID = rs.getInt(7);
				//
				if (AccountSign != null && AccountSign.length() > 0 && (AccountSign.equals("D") || AccountSign.equals("C")))
				{
					// HR_Expense_Acct DR
					// HR_Revenue_Acct CR
					MAccount accountBPD = MAccount.get(getCtx(), getAccountBalancing(as.getC_AcctSchema_ID(), HR_Concept_ID, "D"));
					FactLine debit = fact.createLine(null, accountBPD, as.getC_Currency_ID(), sumAmount, null);
					debit.setAD_OrgTrx_ID(AD_OrgTrx_ID);
					debit.setC_Activity_ID(C_Activity_ID);
					debit.saveEx();
					MAccount accountBPC = MAccount.get(getCtx(), this.getAccountBalancing(as.getC_AcctSchema_ID(), HR_Concept_ID, "C"));
					FactLine credit = fact.createLine(null, accountBPC, as.getC_Currency_ID(), null, sumAmount);
					credit.setAD_OrgTrx_ID(AD_OrgTrx_ID);
					credit.setC_Activity_ID(C_Activity_ID);
					credit.saveEx();
				}
			}
		}
		catch (Exception e)
		{
			throw newPostingException(e).setC_AcctSchema(as);
		}
		finally
		{
			DB.close(rs, pstmt);
			pstmt = null;
			rs = null;
		}

		ArrayList<Fact> facts = new ArrayList<>();
		facts.add(fact);
		return facts;
	}

	/**
	 * get account balancing
	 * 
	 * @param AcctSchema_ID
	 * @param HR_Concept_ID
	 * @param AccountSign D or C only
	 * @return
	 */
	private int getAccountBalancing(int AcctSchema_ID, int HR_Concept_ID, String AccountSign)
	{
		String field;
		if (MElementValue.ACCOUNTSIGN_Debit.equals(AccountSign))
		{
			field = X_HR_Concept_Acct.COLUMNNAME_HR_Expense_Acct;
		}
		else if (MElementValue.ACCOUNTSIGN_Credit.equals(AccountSign))
		{
			field = X_HR_Concept_Acct.COLUMNNAME_HR_Revenue_Acct;
		}
		else
		{
			throw new IllegalArgumentException("Invalid value for AccountSign=" + AccountSign);
		}
		final String sqlAccount = "SELECT " + field + " FROM HR_Concept_Acct"
				+ " WHERE HR_Concept_ID=? AND C_AcctSchema_ID=?";
		int Account_ID = DB.getSQLValueEx(getTrxName(), sqlAccount, HR_Concept_ID, AcctSchema_ID);
		return Account_ID;
	}

}   // Doc_Payroll
