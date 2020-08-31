/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.MAccount;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineReference;
import de.metas.banking.service.IBankStatementBL;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;

/**
 * Post Bank Statement Documents.
 *
 * <pre>
 *  Table:              C_BankStatement (392)
 *  Document Types:     CMB
 * </pre>
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @version $Id: Doc_Bank.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 *          <p>
 *          FR [ 1840016 ] Avoid usage of clearing accounts - subject to C_AcctSchema.IsPostIfClearingEqual Avoid posting if both accounts BankAsset and BankInTransit are equal
 * Also see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class Doc_BankStatement extends Doc<DocLine_BankStatement>
{
	private final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	public Doc_BankStatement(final AcctDocContext ctx)
	{
		super(ctx, DOCTYPE_BankStatement);
	}

	@Override
	protected void loadDocumentDetails()
	{
		final I_C_BankStatement bs = getModel(I_C_BankStatement.class);
		setDateDoc(bs.getStatementDate());
		setDateAcct(bs.getStatementDate());    // Overwritten on Line Level
		setBPBankAccountId(BankAccountId.ofRepoId(bs.getC_BP_BankAccount_ID()));

		// Amounts
		setAmount(AMTTYPE_Gross, bs.getStatementDifference());

		// Set Bank Account Info (Currency)
		final BankAccount bankAccount = getBankAccount(); // shall not be null
		setC_Currency_ID(bankAccount.getCurrencyId());

		final List<DocLine_BankStatement> lines = loadLines(bs);
		setDocLines(lines);

		// Set DateAcct from first line
		if (!lines.isEmpty())
		{
			setDateAcct(lines.get(0).getDateAcct());
		}
	}

	private List<DocLine_BankStatement> loadLines(final I_C_BankStatement bankStatement)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID());
		final List<DocLine_BankStatement> docLines = new ArrayList<>();
		for (final I_C_BankStatementLine line : bankStatementBL.getLinesByBankStatementId(bankStatementId))
		{
			final DocLine_BankStatement docLine = new DocLine_BankStatement(line, this);
			docLines.add(docLine);
		}

		return docLines;
	}

	@Override
	public final BigDecimal getBalance()
	{
		BigDecimal retValue = BigDecimal.ZERO;

		// Total
		retValue = retValue.add(getAmount(Doc.AMTTYPE_Gross));

		// minus Lines
		for (DocLine_BankStatement line : getDocLines())
		{
			final BigDecimal lineBalance = line.getStmtAmt();
			retValue = retValue.subtract(lineBalance);
		}

		return retValue;
	}

	/**
	 * @return bank account's Org or {@link OrgId#ANY}
	 */
	private final OrgId getBankOrgId()
	{
		final BankAccount bankAccount = getBankAccount();
		return bankAccount != null ? bankAccount.getOrgId() : OrgId.ANY;
	}

	private CurrencyConversionContext createCurrencyConversionContext(
			@NonNull final DocLine_BankStatement line,
			@NonNull final CurrencyId acctSchemaCurrencyId)
	{
		CurrencyConversionContext currencyConversionCtx = currencyBL.createCurrencyConversionContext(
				line.getDateAcct(),
				line.getCurrencyConversionTypeId(),
				line.getClientId(),
				line.getOrgId());

		BigDecimal fixedCurrencyRate = line.getFixedCurrencyRate();
		if (fixedCurrencyRate != null && fixedCurrencyRate.signum() != 0)
		{
			currencyConversionCtx = currencyConversionCtx.withFixedConversionRate(
					line.getCurrencyId(),
					acctSchemaCurrencyId,
					fixedCurrencyRate);
		}

		return currencyConversionCtx;
	}

	private FactLineBuilder prepareBankAssetFactLine(
			@NonNull final Fact fact,
			@NonNull final DocLine_BankStatement line)
	{
		final AcctSchema as = fact.getAcctSchema();
		final OrgId bankOrgId = getBankOrgId();    // Bank Account Organization
		final BPartnerId bpartnerId = line.getBPartnerId();
		final CurrencyConversionContext currencyConversionCtx = createCurrencyConversionContext(line, as.getCurrencyId());

		//
		// BankAsset DR/CR (StmtAmt)
		return fact.createLine()
				.setDocLine(line)
				.setAccount(getAccount(AccountType.BankAsset, as))
				// .setAmtSourceDrOrCr(line.getStmtAmt())
				.setCurrencyId(line.getCurrencyId())
				.setCurrencyConversionCtx(currencyConversionCtx)
				.orgIdIfValid(bankOrgId)
				.bpartnerIdIfNotNull(bpartnerId);
	}

	/**
	 * Create Facts (the accounting logic) for CMB.
	 *
	 * <pre>
	 *      BankAsset       DR      CR  (Statement)
	 *      BankInTransit   DR      CR              (Payment)
	 *      Charge          DR          (Charge)
	 *      Interest        DR      CR  (Interest)
	 * </pre>
	 */
	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
		final ArrayList<Fact> facts = new ArrayList<>();

		// Lines
		for (DocLine_BankStatement line : getDocLines())
		{
			facts.addAll(createFacts(as, line));
		}

		return facts;
	}

	private List<Fact> createFacts(final AcctSchema as, final DocLine_BankStatement line)
	{
		final ArrayList<Fact> facts = new ArrayList<>();

		//
		// Bank transfer or Payment
		if (line.isBankTransfer())
		{
			facts.addAll(createFacts_BankTransfer(as, line));
		}
		else
		{
			facts.addAll(createFacts_Payments(as, line));
		}

		facts.addAll(createFacts_BankFee(as, line));
		facts.addAll(createFacts_Charge(as, line));
		facts.addAll(createFacts_Interest(as, line));

		return facts;
	}

	/**
	 * Create facts for bank transfer
	 */
	private final List<Fact> createFacts_BankTransfer(
			final AcctSchema as,
			// final Fact fact,
			final DocLine_BankStatement line
	// final FactLine factLine_BankAsset
	)
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
			return ImmutableList.of();
		}

		// NOTE: atm we support only the case when StmtAmt=TrxAmt because we also have to calculate the currency gain and loss (i.e. BankAsset minus BankInTransit),
		// and the factLine_BankAsset is booking the StmtAmt.
		Check.assume(trxAmt.compareTo(line.getStmtAmt()) == 0, "StmtAmt equals = TrxAmt for line {}", line);

		final OrgId bankOrgId = getBankOrgId();    // Bank Account Org
		final Fact fact = new Fact(this, as, PostingType.Actual);

		//
		// Bank Asset: DR/CR
		final FactLineBuilder factLine_BankAsset_Builder = prepareBankAssetFactLine(fact, line);

		//
		// Bank In Transit: CR/DR
		final FactLineBuilder factLine_BankTransfer_Builder = fact.createLine()
				.setDocLine(line)
				.setAccount(getAccount(AccountType.BankInTransit, as))
				.setCurrencyId(line.getCurrencyId())
				.setCurrencyConversionCtx(line.getBankTransferCurrencyConversionCtx(as.getCurrencyId()))
				.orgId(bankOrgId.isRegular() ? bankOrgId : line.getOrgId()) // bank org, line org
				.bpartnerIdIfNotNull(line.getBPartnerId());

		final FactLine factLine_BankAsset;
		final FactLine factLine_BankTransfer;
		final BigDecimal amtAcct_BankAsset;
		final BigDecimal amtAcct_BankTransfer;

		//
		// Bank transfer: income
		// Bank Asset: DR
		// Bank In transit: CR
		if (line.isInboundTrx())
		{
			factLine_BankAsset_Builder.setAmtSource(trxAmt, null);
			factLine_BankTransfer_Builder.setAmtSource(null, trxAmt);

			factLine_BankAsset = factLine_BankAsset_Builder.buildAndAdd();
			factLine_BankTransfer = factLine_BankTransfer_Builder.buildAndAdd();

			amtAcct_BankAsset = factLine_BankAsset.getAmtAcctDr();
			amtAcct_BankTransfer = factLine_BankTransfer.getAmtAcctCr();
		}
		//
		// Bank transfer: outgoing
		// Bank Asset: CR
		// Bank In transit: DR
		else
		{
			factLine_BankAsset_Builder.setAmtSource(null, trxAmt.negate());
			factLine_BankTransfer_Builder.setAmtSource(trxAmt.negate(), null);

			factLine_BankAsset = factLine_BankAsset_Builder.buildAndAdd();
			factLine_BankTransfer = factLine_BankTransfer_Builder.buildAndAdd();

			amtAcct_BankAsset = factLine_BankAsset.getAmtAcctCr();
			amtAcct_BankTransfer = factLine_BankTransfer.getAmtAcctDr();
		}

		//
		// Currency Gain/Loss bookings
		final BigDecimal amtAcct_BankAssetMinusTransferred = amtAcct_BankAsset.subtract(amtAcct_BankTransfer);
		createFacts_BankTransfer_RealizedGainOrLoss(fact, line, amtAcct_BankAssetMinusTransferred);

		return ImmutableList.of(fact);
	}

	/**
	 * Create facts for bank transfer's currency gain/loss
	 */
	private final void createFacts_BankTransfer_RealizedGainOrLoss(
			final Fact fact,
			final DocLine_BankStatement line,
			final BigDecimal amtAcct_BankAssetMinusTransferred)
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

		final AcctSchema as = fact.getAcctSchema();
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
				.setCurrencyId(as.getCurrencyId())
				.setAmtSource(amtSourceDr, amtSourceCr)
				.buildAndAdd();
	}

	/**
	 * Create facts for booking the payments.
	 */
	private final List<Fact> createFacts_Payments(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_BankStatement line)
	{
		Check.assume(!line.isBankTransfer(), "Line is NOT bank transfer: {}", line);

		final MAccount acct_BankInTransit = getAccount(AccountType.BankInTransit, as);
		final OrgId bankOrgId = getBankOrgId();    // Bank Account Org
		final BPartnerId bpartnerId = line.getBPartnerId();

		final CurrencyConversionContext currencyConversionCtx = createCurrencyConversionContext(line, as.getCurrencyId());

		final List<BankStatementLineReference> lineReferences = line.getReferences();
		if (lineReferences.isEmpty())
		{
			final BigDecimal trxAmt = line.getTrxAmt();
			if (trxAmt.signum() == 0)
			{
				return ImmutableList.of();
			}

			final Fact fact = new Fact(this, as, PostingType.Actual);
			final FactLineBuilder bankAssetFactLine = prepareBankAssetFactLine(fact, line);
			final FactLineBuilder bankInTransitFactLine = fact.createLine()
					.setDocLine(line)
					.setAccount(acct_BankInTransit)
					.setAmtSourceDrOrCr(trxAmt.negate())
					.setCurrencyId(line.getCurrencyId())
					.setCurrencyConversionCtx(currencyConversionCtx)
					.orgId(bankOrgId.isRegular() ? bankOrgId : line.getPaymentOrgId()) // bank org, payment org
					.bpartnerIdIfNotNull(bpartnerId);

			//
			// Inbound:
			// Bank Asset: DR
			// Bank In Transit: CR
			if (trxAmt.signum() > 0)
			{
				bankAssetFactLine.setAmtSource(trxAmt, null);
				bankInTransitFactLine.setAmtSource(null, trxAmt);
			}
			//
			// Outbound
			// Bank Asset: CR
			// Bank In Transit: DR
			else // trxAmt.signum() < 0
			{
				bankAssetFactLine.setAmtSource(null, trxAmt.negate());
				bankInTransitFactLine.setAmtSource(trxAmt.negate(), null);
			}

			bankAssetFactLine.buildAndAdd();
			bankInTransitFactLine.buildAndAdd();

			return ImmutableList.of(fact);
		}
		else
		{
			final Fact fact = new Fact(this, as, PostingType.Actual);

			//
			// Bank Asset: DR/CR
			final boolean inboundTrx;
			{
				final BigDecimal trxAmt = line.getTrxAmt();
				final FactLineBuilder bankAssetFactLine = prepareBankAssetFactLine(fact, line);
				if (trxAmt.signum() >= 0)
				{
					bankAssetFactLine.setAmtSource(trxAmt, null);
					inboundTrx = true;
				}
				else
				{
					bankAssetFactLine.setAmtSource(null, trxAmt.negate());
					inboundTrx = false;
				}
				bankAssetFactLine.buildAndAdd();
			}

			//
			// Bank In Transit: CR/DR
			for (final BankStatementLineReference lineRef : lineReferences)
			{
				final BPartnerId lineRefBPartnerId = lineRef.getBpartnerId();
				final FactLineBuilder bankInTransitFactLine = fact.createLine()
						.setDocLine(line)
						.setSubLine_ID(lineRef.getBankStatementLineRefId().getRepoId())
						.setAccount(acct_BankInTransit)
						// .setAmtSourceDrOrCr(lineRef.getTrxAmt().toBigDecimal().negate())
						.setCurrencyId(lineRef.getTrxAmt().getCurrencyId())
						.setCurrencyConversionCtx(currencyConversionCtx)
						.orgId(bankOrgId.isRegular() ? bankOrgId : line.getPaymentOrgId(lineRef.getPaymentId())) // bank org, payment org
						.bpartnerIdIfNotNull(CoalesceUtil.coalesce(lineRefBPartnerId, bpartnerId)); // if the line ref has a C_BPartner, then use it

				if (inboundTrx)
				{
					bankInTransitFactLine.setAmtSource(null, lineRef.getTrxAmt().toBigDecimal());
				}
				else
				{
					bankInTransitFactLine.setAmtSource(lineRef.getTrxAmt().toBigDecimal(), null);
				}

				bankInTransitFactLine.buildAndAdd();
			}

			return ImmutableList.of(fact);
		}
	}

	private List<Fact> createFacts_BankFee(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_BankStatement line)
	{
		BigDecimal bankFeeAmt = line.getBankFeeAmt();
		if (bankFeeAmt.signum() == 0)
		{
			return ImmutableList.of();
		}

		final Fact fact = new Fact(this, as, PostingType.Actual);
		final FactLineBuilder bankAssetFactLine = prepareBankAssetFactLine(fact, line);
		final FactLineBuilder interestFactLine = fact.createLine()
				.setDocLine(line)
				.setCurrencyId(line.getCurrencyId())
				.orgIdIfValid(getBankOrgId())
				.bpartnerIdIfNotNull(line.getBPartnerId());

		bankAssetFactLine.setAmtSource(null, bankFeeAmt);
		interestFactLine.setAmtSource(bankFeeAmt, null);
		interestFactLine.setAccount(getAccount(AccountType.PayBankFee, as));

		bankAssetFactLine.buildAndAdd();
		interestFactLine.buildAndAdd();

		return ImmutableList.of(fact);
	}

	private List<Fact> createFacts_Charge(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_BankStatement line)
	{
		final BigDecimal chargeAmt = line.getChargeAmt();
		if (chargeAmt.signum() == 0)
		{
			return ImmutableList.of();
		}

		final MAccount acct_Charge = line.getChargeAccount(as, chargeAmt);
		if (acct_Charge == null)
		{
			throw newPostingException()
					.setAcctSchema(as)
					.setDocLine(line)
					.addDetailMessage("No charge set");
		}

		final Fact fact = new Fact(this, as, PostingType.Actual);
		final FactLineBuilder bankAssetFactLine = prepareBankAssetFactLine(fact, line);
		final FactLineBuilder chargeFactLine = fact.createLine()
				.setDocLine(line)
				.setAccount(acct_Charge)
				.setCurrencyId(line.getCurrencyId())
				.orgIdIfValid(getBankOrgId())
				.bpartnerIdIfNotNull(line.getBPartnerId());

		//
		// Revenue
		if (chargeAmt.signum() > 0)
		{
			bankAssetFactLine.setAmtSource(chargeAmt, null);
			chargeFactLine.setAmtSource(null, chargeAmt);
		}
		//
		// Expense
		else
		{
			bankAssetFactLine.setAmtSource(null, chargeAmt.negate());
			chargeFactLine.setAmtSource(chargeAmt.negate(), null);
		}

		bankAssetFactLine.buildAndAdd();
		chargeFactLine.buildAndAdd();

		return ImmutableList.of(fact);
	}

	private List<Fact> createFacts_Interest(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_BankStatement line)
	{
		final BigDecimal interestAmt = line.getInterestAmt();
		if (interestAmt.signum() == 0)
		{
			return ImmutableList.of();
		}

		final Fact fact = new Fact(this, as, PostingType.Actual);
		final FactLineBuilder bankAssetFactLine = prepareBankAssetFactLine(fact, line);
		final FactLineBuilder interestFactLine = fact.createLine()
				.setDocLine(line)
				.setCurrencyId(line.getCurrencyId())
				.orgIdIfValid(getBankOrgId())
				.bpartnerIdIfNotNull(line.getBPartnerId());

		//
		// Revenue
		if (interestAmt.signum() >= 0)
		{
			bankAssetFactLine.setAmtSource(interestAmt, null);
			interestFactLine.setAmtSource(null, interestAmt);
			interestFactLine.setAccount(getAccount(AccountType.InterestRev, as));
		}
		//
		// Expense
		else
		{
			bankAssetFactLine.setAmtSource(null, interestAmt);
			interestFactLine.setAmtSource(interestAmt, null);
			interestFactLine.setAccount(getAccount(AccountType.InterestExp, as));
		}

		bankAssetFactLine.buildAndAdd();
		interestFactLine.buildAndAdd();

		return ImmutableList.of(fact);
	}
}   // Doc_Bank
