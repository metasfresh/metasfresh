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
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;

/**
 * Post Bank Statement Documents.
 * 
 * <pre>
 *  Table:              C_BankStatement (392)
 *  Document Types:     CMB
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: Doc_Bank.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 * 
 *          FR [ 1840016 ] Avoid usage of clearing accounts - subject to C_AcctSchema.IsPostIfClearingEqual Avoid posting if both accounts BankAsset and BankInTransit are equal
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 * 
 */
public class Doc_BankStatement extends Doc
{
	/**
	 * Constructor
	 * 
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_BankStatement(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_BankStatement);
	}	// Doc_Bank

	/**
	 * Load Specific Document Details
	 * 
	 * @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		MBankStatement bs = (MBankStatement)getPO();
		setDateDoc(bs.getStatementDate());
		setDateAcct(bs.getStatementDate());	// Overwritten on Line Level
		setC_BP_BankAccount_ID(bs.getC_BP_BankAccount_ID());
		// Amounts
		setAmount(AMTTYPE_Gross, bs.getStatementDifference());

		// Set Bank Account Info (Currency)
		final I_C_BP_BankAccount ba = getC_BP_BankAccount(); // shall not be null
		setC_Currency_ID(ba.getC_Currency_ID());

		// Contained Objects
		p_lines = loadLines(bs);
		log.fine("Lines=" + p_lines.length);
		return null;
	}   // loadDocumentDetails

	/**
	 * Load Invoice Line.
	 *
	 * @param bs bank statement 4 amounts AMTTYPE_Payment AMTTYPE_Statement2 AMTTYPE_Charge AMTTYPE_Interest
	 * @return DocLine Array
	 */
	private DocLine[] loadLines(final MBankStatement bs)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MBankStatementLine[] lines = bs.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MBankStatementLine line = lines[i];
			DocLine_BankStatement docLine = new DocLine_BankStatement(line, this);
			// Set Date Acct
			if (i == 0)
				setDateAcct(line.getDateAcct());
			MPeriod period = MPeriod.get(getCtx(), line.getDateAcct(), line.getAD_Org_ID());
			if (period != null && period.isOpen(DOCTYPE_BankStatement, line.getDateAcct(), bs.getAD_Org_ID()))
			{
				docLine.setC_Period_ID(period.getC_Period_ID());
			}
			//
			list.add(docLine);
		}

		// Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	// loadLines

	/**************************************************************************
	 * Get Source Currency Balance - subtracts line amounts from total - no rounding
	 * 
	 * @return positive amount, if total invoice is bigger than lines
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		StringBuilder sb = new StringBuilder(" [");
		// Total
		retValue = retValue.add(getAmount(Doc.AMTTYPE_Gross));
		sb.append(getAmount(Doc.AMTTYPE_Gross));
		// - Lines
		for (int i = 0; i < p_lines.length; i++)
		{
			BigDecimal lineBalance = ((DocLine_BankStatement)p_lines[i]).getStmtAmt();
			retValue = retValue.subtract(lineBalance);
			sb.append("-").append(lineBalance);
		}
		sb.append("]");
		//
		log.fine(toString() + " Balance=" + retValue + sb.toString());
		return retValue;
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for CMB.
	 * 
	 * <pre>
	 *      BankAsset       DR      CR  (Statement)
	 *      BankInTransit   DR      CR              (Payment)
	 *      Charge          DR          (Charge)
	 *      Interest        DR      CR  (Interest)
	 * </pre>
	 * 
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public List<Fact> createFacts(final MAcctSchema as)
	{
		// create Fact Header
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		// boolean isInterOrg = isInterOrg(as);

		// Header -- there may be different currency amounts

		final int AD_Org_ID = getBank_Org_ID();	// Bank Account Org
		// Lines
		for (int i = 0; i < p_lines.length; i++)
		{
			final DocLine_BankStatement line = (DocLine_BankStatement)p_lines[i];
			final int C_BPartner_ID = line.getC_BPartner_ID();

			// Avoid usage of clearing accounts
			// If both accounts BankAsset and BankInTransit are equal
			// then remove the posting

			MAccount acct_bank_asset = getAccount(Doc.ACCTTYPE_BankAsset, as);
			MAccount acct_bank_in_transit = getAccount(Doc.ACCTTYPE_BankInTransit, as);

			// if ((!as.isPostIfClearingEqual()) && acct_bank_asset.equals(acct_bank_in_transit) && (!isInterOrg)) {
			// don't validate interorg on banks for this - normally banks are balanced by orgs
			if ((!as.isPostIfClearingEqual()) && acct_bank_asset.equals(acct_bank_in_transit))
			{
				// Not using clearing accounts
				// just post the difference (if any)

				BigDecimal amt_stmt_minus_trx = line.getStmtAmt().subtract(line.getTrxAmt());
				if (amt_stmt_minus_trx.compareTo(Env.ZERO) != 0)
				{

					// BankAsset DR CR (Statement minus Payment)
					final FactLine fl = fact.createLine(line, acct_bank_asset, line.getC_Currency_ID(), amt_stmt_minus_trx);
					if (fl != null && AD_Org_ID != 0)
						fl.setAD_Org_ID(AD_Org_ID);
					if (fl != null && C_BPartner_ID != 0)
						fl.setC_BPartner_ID(C_BPartner_ID);

				}
			}
			// Normal Adempiere behavior -- unchanged if using clearing accounts
			else
			{
				// BankAsset DR CR (Statement)
				{
					FactLine fl = fact.createLine(line,
							acct_bank_asset,
							line.getC_Currency_ID(), line.getStmtAmt());
					if (fl != null && AD_Org_ID != 0)
						fl.setAD_Org_ID(AD_Org_ID);
					if (fl != null && C_BPartner_ID != 0)
						fl.setC_BPartner_ID(C_BPartner_ID);
				}

				//
				// BankInTransit DR CR (Payment)
				{
					final List<I_C_BankStatementLine_Ref> lineReferences = line.getReferences();
					if (lineReferences.isEmpty())
					{
						final FactLine fl = fact.createLine(line,
								acct_bank_in_transit,
								line.getC_Currency_ID(), line.getTrxAmt().negate());
						if (fl != null)
						{
							if (C_BPartner_ID != 0)
								fl.setC_BPartner_ID(C_BPartner_ID);
							if (AD_Org_ID != 0)
								fl.setAD_Org_ID(AD_Org_ID);
							else
								fl.setAD_Org_ID(line.getAD_Org_ID(true)); // from payment
						}
					}
					else
					{
						for (final I_C_BankStatementLine_Ref lineRef : lineReferences)
						{
							final FactLine fl = fact.createLine(line,
									acct_bank_in_transit,
									lineRef.getC_Currency_ID(), lineRef.getTrxAmt().negate());
							if (fl != null)
							{
								fl.setC_BPartner_ID(lineRef.getC_BPartner_ID());
								if (AD_Org_ID != 0)
									fl.setAD_Org_ID(AD_Org_ID);
								else
									fl.setAD_Org_ID(line.getAD_Org_ID(lineRef.getC_Payment())); // from payment
							}
							
						}
					}
				}
			}

			//
			// Charge DR (Charge)
			{
				final FactLine fl;
				if (line.getChargeAmt().compareTo(Env.ZERO) > 0)
				{
					fl = fact.createLine(line,
							line.getChargeAccount(as, line.getChargeAmt().negate()),
							line.getC_Currency_ID(), null, line.getChargeAmt());
				}
				else
				{
					fl = fact.createLine(line,
							line.getChargeAccount(as, line.getChargeAmt().negate()),
							line.getC_Currency_ID(), line.getChargeAmt().negate(), null);
				}
				if (fl != null && C_BPartner_ID != 0)
					fl.setC_BPartner_ID(C_BPartner_ID);
			}

			//
			// Interest DR CR (Interest)
			{
				final FactLine fl;
				if (line.getInterestAmt().signum() < 0)
				{
					fl = fact.createLine(line,
							getAccount(Doc.ACCTTYPE_InterestExp, as), getAccount(Doc.ACCTTYPE_InterestExp, as),
							line.getC_Currency_ID(), line.getInterestAmt().negate());
				}
				else
				{
					fl = fact.createLine(line,
							getAccount(Doc.ACCTTYPE_InterestRev, as), getAccount(Doc.ACCTTYPE_InterestRev, as),
							line.getC_Currency_ID(), line.getInterestAmt().negate());
				}
				if (fl != null && C_BPartner_ID != 0)
					fl.setC_BPartner_ID(C_BPartner_ID);
			}

			//
			// fact.createTaxCorrection();
		}
		//
		final ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   // createFact

	/**
	 * Verify if the posting involves two or more organizations
	 * 
	 * @return true if there are more than one org involved on the posting private boolean isInterOrg(MAcctSchema as) { MAcctSchemaElement elementorg =
	 *         as.getAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_Organization); if (elementorg == null || !elementorg.isBalanced()) { // no org element or not need to be balanced return false; }
	 * 
	 *         if (p_lines.length <= 0) { // no lines return false; }
	 * 
	 *         int startorg = getBank_Org_ID(); if (startorg == 0) startorg = p_lines[0].getAD_Org_ID(); // validate if the allocation involves more than one org for (int i = 0; i < p_lines.length;
	 *         i++) { if (p_lines[i].getAD_Org_ID() != startorg) return true; }
	 * 
	 *         return false; }
	 */

	/**
	 * Get AD_Org_ID from Bank Account
	 * 
	 * @return AD_Org_ID or 0
	 */
	private final int getBank_Org_ID()
	{
		final I_C_BP_BankAccount bpBankAccount = getC_BP_BankAccount();
		if (bpBankAccount == null)
		{
			return 0;
		}
		return bpBankAccount.getAD_Org_ID();
	}

}   // Doc_Bank
