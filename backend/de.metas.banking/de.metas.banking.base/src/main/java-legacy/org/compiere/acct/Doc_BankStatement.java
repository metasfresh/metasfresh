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

import org.adempiere.util.Check;
import org.compiere.acct.Fact.FactLineBuilder;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.util.Env;
import org.compiere.util.Util;

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
		final MBankStatement bs = (MBankStatement)getPO();
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
		return null;
	}   // loadDocumentDetails

	private DocLine[] loadLines(final MBankStatement bs)
	{
		final List<DocLine> docLines = new ArrayList<>();
		for (final MBankStatementLine line : bs.getLines(false))
		{
			final DocLine_BankStatement docLine = new DocLine_BankStatement(line, this);

			// Set DateAcct from first line
			if (docLines.isEmpty())
			{
				setDateAcct(line.getDateAcct());
			}

			docLines.add(docLine);
		}

		return docLines.toArray(new DocLine[docLines.size()]);
	}

	@Override
	public final BigDecimal getBalance()
	{
		BigDecimal retValue = BigDecimal.ZERO;

		// Total
		retValue = retValue.add(getAmount(Doc.AMTTYPE_Gross));

		// minus Lines
		for (int i = 0; i < p_lines.length; i++)
		{
			final BigDecimal lineBalance = ((DocLine_BankStatement)p_lines[i]).getStmtAmt();
			retValue = retValue.subtract(lineBalance);
		}

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
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		final int bankOrgId = getBank_Org_ID();	// Bank Account Organization

		// Lines
		for (int i = 0; i < p_lines.length; i++)
		{
			final DocLine_BankStatement line = (DocLine_BankStatement)p_lines[i];
			final int C_BPartner_ID = line.getC_BPartner_ID();

			//
			// BankAsset DR CR (Statement)
			final FactLine factLine_BankAsset = fact.createLine()
					.setDocLine(line)
					.setAccount(getAccount(Doc.ACCTTYPE_BankAsset, as))
					.setAmtSourceDrOrCr(line.getStmtAmt())
					.setC_Currency_ID(line.getC_Currency_ID())
					.setAD_Org_ID_IfValid(bankOrgId)
					.setC_BPartner_ID_IfValid(C_BPartner_ID)
					.buildAndAdd();

			//
			// Bank transfer
			if (line.isBankTransfer())
			{
				createFacts_BankTransfer(fact, line, factLine_BankAsset);
			}
			//
			// or Payments
			else
			{
				createFacts_Payments(fact, line);
			}

			//
			// Charge DR (Charge)
			final BigDecimal chargeAmt = line.getChargeAmt();
			if (chargeAmt.signum() != 0)
			{
				final MAccount acct_Charge = line.getChargeAccount(as, chargeAmt.negate());

				final FactLineBuilder flBuilder = fact.createLine()
						.setDocLine(line)
						.setAccount(acct_Charge)
						.setC_Currency_ID(line.getC_Currency_ID())
						.setAD_Org_ID_IfValid(bankOrgId)
						.setC_BPartner_ID_IfValid(C_BPartner_ID);
				if (chargeAmt.signum() > 0)
				{
					flBuilder.setAmtSource(null, chargeAmt.negate());
				}
				else
				{
					flBuilder.setAmtSource(chargeAmt.negate(), null);
				}

				flBuilder.buildAndAdd();
			}

			//
			// Interest DR CR (Interest)
			final BigDecimal interestAmt = line.getInterestAmt();
			if (interestAmt.signum() != 0)
			{
				final FactLineBuilder flBuilder = fact.createLine()
						.setDocLine(line)
						.setC_Currency_ID(line.getC_Currency_ID())
						.setAD_Org_ID_IfValid(bankOrgId)
						.setC_BPartner_ID_IfValid(C_BPartner_ID);

				if (interestAmt.signum() >= 0)
				{
					flBuilder.setAccount(getAccount(Doc.ACCTTYPE_InterestRev, as));
					flBuilder.setAmtSource(null, interestAmt);
				}
				else
				{
					flBuilder.setAccount(getAccount(Doc.ACCTTYPE_InterestExp, as));
					flBuilder.setAmtSource(interestAmt, null);
				}

				flBuilder.buildAndAdd();
			}
		}
		//
		final List<Fact> facts = new ArrayList<>();
		facts.add(fact);
		return facts;
	}   // createFact

	/**
	 * Create facts for bank transfer
	 *
	 * @param fact
	 * @param line
	 * @param factLine_BankAsset
	 */
	private final void createFacts_BankTransfer(final Fact fact, final DocLine_BankStatement line, final FactLine factLine_BankAsset)
	{
		//
		// Make sure it's a valid bank transfer line
		Check.assume(line.isBankTransfer(), "Line is bank transfer: {}", line);
		Check.assume(line.getReferences().isEmpty(), "Line has no referenced payments: {}", line);

		//
		// Get the transferred amount.
		// If the amount is zero, we have nothing to do.
		final BigDecimal trxAmt = line.getTrxAmt();
		if (trxAmt.signum() == 0)
		{
			return;
		}
		// NOTE: atm we support only the case when StmtAmt=TrxAmt because we also have to calculate the currency gain and loss (i.e. BankAsset minus BankInTransit),
		// and the factLine_BankAsset is booking the StmtAmt.
		Check.assume(trxAmt.compareTo(line.getStmtAmt()) == 0, "StmtAmt equals = TrxAmt for line {}", line);

		//
		// Book the transfer to/from BankInTransit.
		// We are using the currency conversion for bank transfers (e.g. Spot) and not the default one which could be different (e.g. Company conversion type).
		//

		final MAcctSchema as = fact.getAcctSchema();
		final int bankOrgId = getBank_Org_ID();	// Bank Account Org
		final FactLineBuilder factLine_BankTransfer_Builder = fact.createLine()
				.setDocLine(line)
				.setAccount(getAccount(Doc.ACCTTYPE_BankInTransit, as))
				.setC_Currency_ID(line.getC_Currency_ID())
				.setCurrencyConversionCtx(line.getBankTransferCurrencyConversionCtx())
				.setAD_Org_ID(bankOrgId > 0 ? bankOrgId : line.getAD_Org_ID()) // bank org, line org
				.setC_BPartner_ID_IfValid(line.getC_BPartner_ID());

		final FactLine factLine_BankTransfer;
		final BigDecimal amtAcct_BankAsset;
		final BigDecimal amtAcct_BankTransfer;

		//
		// Bank transfer: income
		if (line.isInboundTrx())
		{
			factLine_BankTransfer_Builder.setAmtSource(null, trxAmt);
			factLine_BankTransfer = factLine_BankTransfer_Builder.buildAndAdd();

			amtAcct_BankAsset = factLine_BankAsset.getAmtAcctDr();
			amtAcct_BankTransfer = factLine_BankTransfer.getAmtAcctCr();
		}
		//
		// Bank transfer: outgoing
		else
		{
			factLine_BankTransfer_Builder.setAmtSource(trxAmt.negate(), null);
			factLine_BankTransfer = factLine_BankTransfer_Builder.buildAndAdd();

			amtAcct_BankAsset = factLine_BankAsset.getAmtAcctCr();
			amtAcct_BankTransfer = factLine_BankTransfer.getAmtAcctDr();
		}

		//
		// Currency Gain/Loss bookings
		final BigDecimal amtAcct_BankAssetMinusTransferred = amtAcct_BankAsset.subtract(amtAcct_BankTransfer);
		createFacts_BankTransfer_RealizedGainOrLoss(fact, line, amtAcct_BankAssetMinusTransferred);
	}

	/**
	 * Create facts for bank transfer's currency gain/loss
	 *
	 * @param fact
	 * @param line
	 * @param amtAcct_BankAssetMinusTransferred
	 */
	private final void createFacts_BankTransfer_RealizedGainOrLoss(final Fact fact, final DocLine_BankStatement line, final BigDecimal amtAcct_BankAssetMinusTransferred)
	{
		// If there is no difference between how much we booked in BankAsset and how much we book in Bank Transfer
		// => stop here, there are no currency gain/loss
		if (amtAcct_BankAssetMinusTransferred.signum() == 0)
		{
			return;
		}

		//
		// Flag this document as multi-currency to prevent source amounts balancing.
		// Our source amounts won't be source balanced anymore because the bank transfer is booked in allocation's currency
		// and the currency gain/loss is booked in accounting currency.
		setIsMultiCurrency(true);

		final MAcctSchema as = fact.getAcctSchema();
		final MAccount account;
		final BigDecimal amtSourceDr;
		final BigDecimal amtSourceCr;
		if (line.isInboundTrx())
		{
			// Inbound Gain
			if (amtAcct_BankAssetMinusTransferred.signum() >= 0)
			{
				account = getRealizedGainAcct(as);
				amtSourceDr = null;
				amtSourceCr = amtAcct_BankAssetMinusTransferred;
			}
			// Inbound Loss
			else
			{
				account = getRealizedLossAcct(as);
				amtSourceDr = amtAcct_BankAssetMinusTransferred.negate();
				amtSourceCr = null;
			}
		}
		// Outbound
		else
		{
			// Outbound Loss
			if (amtAcct_BankAssetMinusTransferred.signum() >= 0)
			{
				account = getRealizedLossAcct(as);
				amtSourceDr = amtAcct_BankAssetMinusTransferred;
				amtSourceCr = null;

			}
			// Outbound Gain
			else
			{
				account = getRealizedGainAcct(as);
				amtSourceDr = null;
				amtSourceCr = amtAcct_BankAssetMinusTransferred.negate();
			}
		}

		fact.createLine()
				.setDocLine(line)
				.setAccount(account)
				.setC_Currency_ID(as.getC_Currency_ID())
				.setAmtSource(amtSourceDr, amtSourceCr)
				.buildAndAdd();
	}

	/**
	 * Create facts for booking the payments.
	 *
	 * @param fact
	 * @param line
	 */
	private final void createFacts_Payments(final Fact fact, final DocLine_BankStatement line)
	{
		final MAcctSchema as = fact.getAcctSchema();
		final MAccount acct_BankInTransit = getAccount(Doc.ACCTTYPE_BankInTransit, as);
		final int bankOrgId = getBank_Org_ID();	// Bank Account Org
		final int C_BPartner_ID = line.getC_BPartner_ID();

		final List<I_C_BankStatementLine_Ref> lineReferences = line.getReferences();
		if (lineReferences.isEmpty())
		{
			fact.createLine()
					.setDocLine(line)
					.setAccount(acct_BankInTransit)
					.setAmtSourceDrOrCr(line.getTrxAmt().negate())
					.setC_Currency_ID(line.getC_Currency_ID())
					.setAD_Org_ID(bankOrgId > 0 ? bankOrgId : line.getPaymentOrg_ID()) // bank org, payment org
					.setC_BPartner_ID_IfValid(C_BPartner_ID)
					.buildAndAdd();
		}
		else
		{
			for (final I_C_BankStatementLine_Ref lineRef : lineReferences)
			{
				fact.createLine()
						.setDocLine(line)
						.setSubLine_ID(lineRef.getC_BankStatementLine_Ref_ID())
						.setAccount(acct_BankInTransit)
						.setAmtSourceDrOrCr(lineRef.getTrxAmt().negate())
						.setC_Currency_ID(lineRef.getC_Currency_ID())
						.setAD_Org_ID(bankOrgId > 0 ? bankOrgId : line.getPaymentOrg_ID(lineRef.getC_Payment())) // bank org, payment org
						.setC_BPartner_ID_IfValid(
								Util.firstGreaterThanZero(lineRef.getC_BPartner_ID(), C_BPartner_ID)) // if the lineref has a C_BPartner, then use it
						.buildAndAdd();
			}
		}

	}

	/**
	 * Get AD_Org_ID from Bank Account
	 *
	 * @return AD_Org_ID or {@link Env#CTXVALUE_AD_Org_ID_System}
	 */
	private final int getBank_Org_ID()
	{
		final I_C_BP_BankAccount bpBankAccount = getC_BP_BankAccount();
		if (bpBankAccount == null)
		{
			return Env.CTXVALUE_AD_Org_ID_System;
		}
		return bpBankAccount.getAD_Org_ID();
	}

}   // Doc_Bank
