/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.compiere.acct;

/*
 * #%L
 * de.metas.commission.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MTax;
import org.compiere.model.X_C_ElementValue;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.I_HR_Concept_Acct;
import org.eevolution.model.MHRMovement;
import org.eevolution.model.MHRProcess;

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
public class Doc_HRProcess extends Doc
{
	public MHRProcess process = null;

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
	protected String loadDocumentDetails()
	{
		process = (MHRProcess)getPO();
		setDateDoc(getDateAcct());
		// Contained Objects
		p_lines = loadLines(process);
		log.fine("Lines=" + p_lines.length);
		return null;
	}   // loadDocumentDetails

	/**
	 * Load Payroll Line
	 * 
	 * @param Payroll Process
	 * @return DocLine Array
	 */
	private DocLine[] loadLines(final MHRProcess process)
	{
		final ArrayList<DocLine> list = new ArrayList<DocLine>();
		final MHRMovement[] lines = process.getLines(true);
		for (int i = 0; i < lines.length; i++)
		{
			final MHRMovement line = lines[i];
			final DocLine_Payroll docLine = new DocLine_Payroll(line, this);
			//
			log.fine(docLine.toString());
			list.add(docLine);
		}
		// Return Array
		final DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	// loadLines

	@Override
	public BigDecimal getBalance()
	{
		final BigDecimal retValue = Env.ZERO;
		return retValue;
	}   // getBalance

	@Override
	public ArrayList<Fact> createFacts(final MAcctSchema as)
	{
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		// metas (2009 0023 G107): adding C_Tax_ID to the accounting
		final String sql = "SELECT m.HR_Concept_id, MAX(c.Name), SUM(m.Amount), MAX(c.AccountSign), MAX(CA.IsBalancing), e.AD_Org_ID, d.C_Activity_ID" // 1,2,3,4,5,6,7
				+ ", m.C_Tax_ID" // metas (2009 0023 G107): adding C_Tax_ID to the accounting
				+ " FROM HR_Movement m"
				+ " INNER JOIN HR_Concept_Acct ca ON (ca.HR_Concept_ID=m.HR_Concept_ID AND ca.IsActive = 'Y')"
				+ " INNER JOIN HR_Concept      c  ON (c.HR_Concept_ID=m.HR_Concept_ID AND c.IsActive = 'Y')"
				+ " INNER JOIN C_BPartner      bp ON (bp.C_BPartner_ID = m.C_BPartner_ID)"
				+ " INNER JOIN HR_Employee	 e  ON (bp.C_BPartner_ID=e.C_BPartner_ID)"
				+ " INNER JOIN HR_Department   d  ON (d.HR_Department_ID=e.HR_Department_ID)"
				+ " WHERE m.HR_Process_ID=? AND (m.Qty <> 0 OR m.Amount <> 0) AND c.AccountSign != 'N' AND ca.IsBalancing != 'Y'"
				+ " GROUP BY m.HR_Concept_ID,e.AD_Org_ID,d.C_Activity_ID"
				+ ", m.C_Tax_ID" // metas (2009 0023 G107): adding C_Tax_ID to the accounting
				+ " ORDER BY e.AD_Org_ID,d.C_Activity_ID";

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, process.get_TrxName());
			pstmt.setInt(1, process.getHR_Process_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final int HR_Concept_ID = rs.getInt(1);
				BigDecimal sumAmount = rs.getBigDecimal(3);
				// round amount according to currency
				sumAmount = sumAmount.setScale(as.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
				final String AccountSign = rs.getString(4);
				final int AD_OrgTrx_ID = rs.getInt(6);
				final int C_Activity_ID = rs.getInt(7);
				//
				if (AccountSign != null && AccountSign.length() > 0 && (AccountSign.equals("D") || AccountSign.equals("C")))
				{
					// HR_Expense_Acct DR
					// HR_Revenue_Acct CR

					final BigDecimal taxAmount;
					final int taxId = rs.getInt(8);
					if (taxId > 0)
					{

						taxAmount = MTax.get(getCtx(), taxId).calculateTax(sumAmount, false, as.getC_Currency().getStdPrecision());
					}
					else
					{
						taxAmount = BigDecimal.ZERO;
					}

					final BigDecimal drAmount = sumAmount.add(taxAmount);

					final MAccount accountBPD = MAccount.get(getCtx(), getAccountBalancing(as.getC_AcctSchema_ID(), HR_Concept_ID, "D"));
					final FactLine debit = fact.createLine(null, accountBPD, as.getC_Currency_ID(), drAmount, null);
					debit.setAD_OrgTrx_ID(AD_OrgTrx_ID);
					debit.setC_Activity_ID(C_Activity_ID);
					debit.saveEx();

					final BigDecimal crAmount = sumAmount;

					final MAccount accountBPC = MAccount.get(getCtx(), getAccountBalancing(as.getC_AcctSchema_ID(), HR_Concept_ID, "C"));
					final FactLine credit = fact.createLine(null, accountBPC, as.getC_Currency_ID(), null, crAmount);
					credit.setAD_OrgTrx_ID(AD_OrgTrx_ID);
					credit.setC_Activity_ID(C_Activity_ID);
					credit.saveEx();

					// metas (2009 0023 G107): adding C_Tax_ID to the accounting
					if (taxId > 0)
					{

						final MAccount accountTax = MTax.getRevenueAccount(taxId, as);
						final FactLine tax = fact.createLine(null, accountTax, as.getC_Currency_ID(), null, taxAmount);
						tax.setAD_OrgTrx_ID(AD_OrgTrx_ID);
						tax.setC_Activity_ID(C_Activity_ID);
						tax.saveEx();
					}
					// metas: end
				}
			}
		}
		catch (final Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			p_Error = e.getLocalizedMessage();
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			pstmt = null;
			rs = null;
		}

		final ArrayList<Fact> facts = new ArrayList<Fact>();
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
	private int getAccountBalancing(final int AcctSchema_ID, final int HR_Concept_ID, final String AccountSign)
	{
		String field;
		if (X_C_ElementValue.ACCOUNTSIGN_Debit.equals(AccountSign))
		{
			field = I_HR_Concept_Acct.COLUMNNAME_HR_Expense_Acct;
		}
		else if (X_C_ElementValue.ACCOUNTSIGN_Credit.equals(AccountSign))
		{
			field = I_HR_Concept_Acct.COLUMNNAME_HR_Revenue_Acct;
		}
		else
		{
			throw new IllegalArgumentException("Invalid value for AccountSign=" + AccountSign);
		}
		final String sqlAccount = "SELECT " + field + " FROM HR_Concept_Acct"
				+ " WHERE HR_Concept_ID=? AND C_AcctSchema_ID=?";
		final int Account_ID = DB.getSQLValueEx(getTrxName(), sqlAccount, HR_Concept_ID, AcctSchema_ID);
		return Account_ID;
	}

}   // Doc_Payroll
