package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.acct.Account;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReference;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.accounting.BankAccountAcctType;
import de.metas.banking.service.IBankStatementBL;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyConversionContext;
import de.metas.document.DocBaseType;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Post Bank Statement Documents.
 *
 * <pre>
 *  Table:              C_BankStatement (392)
 *  Document Types:     CMB
 * </pre>
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution <a href="http://www.e-evolution.com">...</a>
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @version $Id: Doc_Bank.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 * <p>
 * FR [ 1840016 ] Avoid usage of clearing accounts - subject to C_AcctSchema.IsPostIfClearingEqual Avoid posting if both accounts BankAsset and BankInTransit are equal
 */
public class Doc_BankStatement extends Doc<DocLine_BankStatement>
{
	private final IBankStatementBL bankStatementBL = SpringContextHolder.instance.getBean(IBankStatementBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	public Doc_BankStatement(final AcctDocContext ctx)
	{
		super(ctx, DocBaseType.BankStatement);
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
		final List<I_C_BankStatementLine> lineRecords = bankStatementBL.getLinesByBankStatementId(bankStatementId);
		final ImmutableSet<BankStatementLineId> lineIds = extractBankStatementLineIds(lineRecords);
		final ImmutableListMultimap<BankStatementLineId, BankStatementLineReferenceAcctInfo> lineRefs = loadLineRefs(lineIds);

		final ArrayList<DocLine_BankStatement> docLines = new ArrayList<>();
		for (final I_C_BankStatementLine line : lineRecords)
		{
			final BankStatementLineId lineId = BankStatementLineId.ofRepoId(line.getC_BankStatementLine_ID());
			final DocLine_BankStatement docLine = new DocLine_BankStatement(line, this, lineRefs.get(lineId));
			docLines.add(docLine);
		}

		return docLines;
	}

	private static ImmutableSet<BankStatementLineId> extractBankStatementLineIds(final List<I_C_BankStatementLine> lines)
	{
		return lines.stream()
				.map(line -> BankStatementLineId.ofRepoId(line.getC_BankStatementLine_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableListMultimap<BankStatementLineId, BankStatementLineReferenceAcctInfo> loadLineRefs(final ImmutableSet<BankStatementLineId> lineIds)
	{
		final BankStatementLineReferenceList lineRefs = bankStatementBL.getLineReferences(lineIds);
		final ImmutableSet<PaymentId> paymentIds = lineRefs.stream().map(BankStatementLineReference::getPaymentId).collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<PaymentId, I_C_Payment> payments = Maps.uniqueIndex(paymentBL.getByIds(paymentIds), payment -> PaymentId.ofRepoId(payment.getC_Payment_ID()));

		final ImmutableListMultimap.Builder<BankStatementLineId, BankStatementLineReferenceAcctInfo> result = ImmutableListMultimap.builder();
		for (final BankStatementLineReference lineRef : lineRefs)
		{
			final I_C_Payment payment = payments.get(lineRef.getPaymentId());
			final CurrencyConversionContext currencyConversionContext = paymentBL.extractCurrencyConversionContext(payment);

			result.put(
					lineRef.getBankStatementLineId(),
					BankStatementLineReferenceAcctInfo.builder()
							.id(lineRef.getId())
							.bpartnerId(lineRef.getBpartnerId())
							.paymentOrgId(OrgId.ofRepoId(payment.getAD_Org_ID()))
							.trxAmt(lineRef.getTrxAmt())
							.currencyConversionContext(currencyConversionContext)
							// TODO
							.build());
		}

		return result.build();
	}

	@Override
	public final BigDecimal getBalance()
	{
		BigDecimal retValue = BigDecimal.ZERO;

		// Total
		retValue = retValue.add(getAmount(Doc.AMTTYPE_Gross));

		// minus Lines
		for (final DocLine_BankStatement line : getDocLines())
		{
			final BigDecimal lineBalance = line.getStmtAmt();
			retValue = retValue.subtract(lineBalance);
		}

		return retValue;
	}

	/**
	 * @return bank account's Org or {@link OrgId#ANY}
	 */
	private OrgId getBankOrgId()
	{
		final BankAccount bankAccount = getBankAccount();
		return bankAccount != null ? bankAccount.getOrgId() : OrgId.ANY;
	}

	private FactLineBuilder prepareBankAssetFactLine(
			@NonNull final Fact fact,
			@NonNull final DocLine_BankStatement line)
	{
		final AcctSchema as = fact.getAcctSchema();
		final OrgId bankOrgId = getBankOrgId();    // Bank Account Organization
		final BPartnerId bpartnerId = line.getBPartnerId();

		//
		// BankAsset DR/CR (StmtAmt)
		return fact.createLine()
				.setDocLine(line)
				.setAccount(getBankAccountAccount(BankAccountAcctType.B_Asset_Acct, as))
				// .setAmtSourceDrOrCr(line.getStmtAmt())
				.setCurrencyId(line.getCurrencyId())
				.setCurrencyConversionCtx(line.getCurrencyConversionCtxForBankAsset())
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
		for (final DocLine_BankStatement line : getDocLines())
		{
			facts.addAll(createFacts(as, line));
		}

		return facts;
	}

	private List<Fact> createFacts(final AcctSchema as, final DocLine_BankStatement line)
	{
		final ArrayList<Fact> facts = new ArrayList<>();

		facts.addAll(createFacts_TrxAmt(as, line));
		facts.addAll(createFacts_BankFee(as, line));
		facts.addAll(createFacts_Charge(as, line));
		facts.addAll(createFacts_Interest(as, line));

		return facts;
	}

	private List<Fact> createFacts_TrxAmt(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_BankStatement line)
	{
		final Fact fact;
		final List<BankStatementLineReferenceAcctInfo> lineReferences = line.getReferences();
		if (lineReferences.isEmpty())
		{
			fact = createFacts_TrxAmt_SimpleLine(as, line);
		}
		else
		{
			fact = createFacts_TrxAmt_LineRefs(as, line);
		}

		if (fact == null)
		{
			return ImmutableList.of();
		}

		createFacts_CurrencyExchangeGainOrLoss(fact, line);

		return ImmutableList.of(fact);
	}

	@Nullable
	private Fact createFacts_TrxAmt_SimpleLine(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_BankStatement line)
	{
		final BigDecimal trxAmt = line.getTrxAmt();
		if (trxAmt.signum() == 0)
		{
			return null;
		}

		final OrgId bankOrgId = getBankOrgId();    // Bank Account Org

		final Fact fact = new Fact(this, as, PostingType.Actual);
		final FactLineBuilder bankAssetFactLineBuilder = prepareBankAssetFactLine(fact, line);
		final FactLineBuilder bankInTransitFactLineBuilder = fact.createLine()
				.setDocLine(line)
				.setAccount(getBankAccountAccount(BankAccountAcctType.B_InTransit_Acct, as))
				.setAmtSourceDrOrCr(trxAmt.negate())
				.setCurrencyId(line.getCurrencyId())
				.setCurrencyConversionCtx(line.getCurrencyConversionCtxForBankInTransit())
				.orgId(bankOrgId.isRegular() ? bankOrgId : line.getPaymentOrgId()) // bank org, payment org/line org
				.bpartnerIdIfNotNull(line.getBPartnerId());

		//
		// Inbound:
		// Bank Asset: DR
		// Bank In Transit: CR
		if (trxAmt.signum() > 0)
		{
			bankAssetFactLineBuilder.setAmtSource(trxAmt, null);
			bankInTransitFactLineBuilder.setAmtSource(null, trxAmt);
		}
		//
		// Outbound
		// Bank Asset: CR
		// Bank In Transit: DR
		else // trxAmt.signum() < 0
		{
			bankAssetFactLineBuilder.setAmtSource(null, trxAmt.negate());
			bankInTransitFactLineBuilder.setAmtSource(trxAmt.negate(), null);
		}

		bankAssetFactLineBuilder.buildAndAdd();
		bankInTransitFactLineBuilder.buildAndAdd();

		return fact;
	}

	private Fact createFacts_TrxAmt_LineRefs(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_BankStatement line)
	{
		final Account acct_BankInTransit = getBankAccountAccount(BankAccountAcctType.B_InTransit_Acct, as);
		final OrgId bankOrgId = getBankOrgId();    // Bank Account Org
		final BPartnerId bpartnerId = line.getBPartnerId();
		final List<BankStatementLineReferenceAcctInfo> lineReferences = line.getReferences();

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
		for (final BankStatementLineReferenceAcctInfo lineRef : lineReferences)
		{
			final FactLineBuilder bankInTransitFactLineBuilder = fact.createLine()
					.setDocLine(line)
					.setSubLine_ID(lineRef.getBankStatementLineRefId().getRepoId())
					.setAccount(acct_BankInTransit)
					// .setAmtSourceDrOrCr(lineRef.getTrxAmt().toBigDecimal().negate())
					.setCurrencyId(lineRef.getTrxAmt().getCurrencyId())
					.setCurrencyConversionCtx(lineRef.getCurrencyConversionContext())
					.orgId(bankOrgId.isRegular() ? bankOrgId : line.getPaymentOrgId()) // bank org, payment org
					.bpartnerIdIfNotNull(CoalesceUtil.coalesce(lineRef.getBpartnerId(), bpartnerId)); // if the line ref has a C_BPartner, then use it

			if (inboundTrx)
			{
				bankInTransitFactLineBuilder.setAmtSource(null, lineRef.getTrxAmt().toBigDecimal());
			}
			else
			{
				bankInTransitFactLineBuilder.setAmtSource(lineRef.getTrxAmt().toBigDecimal().negate(), null);
			}

			bankInTransitFactLineBuilder.buildAndAdd();
		}

		return fact;
	}

	private List<Fact> createFacts_BankFee(
			@NonNull final AcctSchema as,
			@NonNull final DocLine_BankStatement line)
	{
		final BigDecimal bankFeeAmt = line.getBankFeeAmt();
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
		interestFactLine.setAccount(getBankAccountAccount(BankAccountAcctType.PayBankFee_Acct, as));

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

		final Account acct_Charge = line.getChargeAccount(as, chargeAmt);
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
			interestFactLine.setAccount(getBankAccountAccount(BankAccountAcctType.B_InterestRev_Acct, as));
		}
		//
		// Expense
		else
		{
			bankAssetFactLine.setAmtSource(null, interestAmt);
			interestFactLine.setAmtSource(interestAmt, null);
			interestFactLine.setAccount(getBankAccountAccount(BankAccountAcctType.B_InterestExp_Acct, as));
		}

		bankAssetFactLine.buildAndAdd();
		interestFactLine.buildAndAdd();

		return ImmutableList.of(fact);
	}

	private void createFacts_CurrencyExchangeGainOrLoss(
			final Fact fact,
			final DocLine_BankStatement line)
	{
		final BigDecimal bankAssetMinusInTransitAmount = computeBankAssetMinusInTransitAcctAmt(fact);
		// If there is no difference between how much we booked in BankAsset and how much we book in Bank In Transit
		// => stop here, there are no currency gain/loss
		if (bankAssetMinusInTransitAmount.signum() == 0)
		{
			return;
		}

		//
		// Flag this document as multi-currency to prevent source amounts balancing.
		// Our source amounts won't be source balanced anymore because the bank transfer is booked in allocation's currency
		// and the currency gain/loss is booked in accounting currency.
		setIsMultiCurrency();

		final AcctSchema as = fact.getAcctSchema();
		final Account account;
		final BigDecimal amtSourceDr;
		final BigDecimal amtSourceCr;
		if (line.isInboundTrx())
		{
			// Inbound Gain
			if (bankAssetMinusInTransitAmount.signum() >= 0)
			{
				account = getRealizedGainAcct(as);
				amtSourceDr = null;
				amtSourceCr = bankAssetMinusInTransitAmount;
			}
			// Inbound Loss
			else
			{
				account = getRealizedLossAcct(as);
				amtSourceDr = bankAssetMinusInTransitAmount.negate();
				amtSourceCr = null;
			}
		}
		// Outbound
		else
		{
			// Outbound Loss
			if (bankAssetMinusInTransitAmount.signum() >= 0)
			{
				account = getRealizedLossAcct(as);
				amtSourceDr = bankAssetMinusInTransitAmount;
				amtSourceCr = null;

			}
			// Outbound Gain
			else
			{
				account = getRealizedGainAcct(as);
				amtSourceDr = null;
				amtSourceCr = bankAssetMinusInTransitAmount.negate();
			}
		}

		fact.createLine()
				.setDocLine(line)
				.setAccount(account)
				.setCurrencyId(as.getCurrencyId())
				.setAmtSource(amtSourceDr, amtSourceCr)
				.buildAndAdd();
	}

	private BigDecimal computeBankAssetMinusInTransitAcctAmt(final Fact fact)
	{
		if (fact.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		final Account bankAssetAccount = getBankAccountAccount(BankAccountAcctType.B_Asset_Acct, fact.getAcctSchema());
		final FactLine factLine_BankAsset = fact.getSingleLineByAccountId(bankAssetAccount.getAccountId());

		final AmountSourceAndAcct bankAssetAmt;
		final boolean isIncomeAmount;
		if (factLine_BankAsset.getAmtAcctDr().signum() != 0)
		{
			bankAssetAmt = extractAmountSourceAndAcct(factLine_BankAsset, true);
			isIncomeAmount = true;
		}
		else
		{
			bankAssetAmt = extractAmountSourceAndAcct(factLine_BankAsset, false);
			isIncomeAmount = false;
		}

		final HashMap<CurrencyAndRate, AmountSourceAndAcct> bankAssetMinusInTransitAmounts = new HashMap<>();
		bankAssetMinusInTransitAmounts.put(bankAssetAmt.getCurrencyAndRate(), bankAssetAmt);

		for (final FactLine factLine : fact.getLines())
		{
			if (factLine == factLine_BankAsset)
			{
				continue;
			}

			final AmountSourceAndAcct bankInTransitAmt = isIncomeAmount
					? extractAmountSourceAndAcct(factLine, false)
					: extractAmountSourceAndAcct(factLine, true);

			bankAssetMinusInTransitAmounts.compute(
					bankInTransitAmt.getCurrencyAndRate(),
					(currencyAndRate, amt) -> amt == null ? bankInTransitAmt.negate() : amt.subtract(bankInTransitAmt));
		}

		BigDecimal bankAssetMinusInTransitAcctAmt = BigDecimal.ZERO;
		for (final AmountSourceAndAcct amtSourceAndAcct : bankAssetMinusInTransitAmounts.values())
		{
			// Case: for a given Currency/Rate we have Zero Source Amount
			// => skip it even if Accounting amount is not zero, because that difference (if it exists)
			// it's because of conversion roundings and NOT because exchange rate difference.
			if (amtSourceAndAcct.isZeroAmtSource())
			{
				continue;
			}

			bankAssetMinusInTransitAcctAmt = bankAssetMinusInTransitAcctAmt.add(amtSourceAndAcct.getAmtAcct());
		}

		return bankAssetMinusInTransitAcctAmt;
	}

	private static AmountSourceAndAcct extractAmountSourceAndAcct(@NonNull final FactLine factLine, boolean isDR)
	{
		return AmountSourceAndAcct.builder()
				.currencyAndRate(CurrencyAndRate.of(factLine.getCurrencyId(), factLine.getCurrencyRate()))
				.amtSource(isDR ? factLine.getAmtSourceDr() : factLine.getAmtSourceCr())
				.amtAcct(isDR ? factLine.getAmtAcctDr() : factLine.getAmtAcctCr())
				.build();

	}

	@Value(staticConstructor = "of")
	private static class CurrencyAndRate
	{
		@NonNull CurrencyId currencyId;
		@NonNull BigDecimal currencyRate;

		private CurrencyAndRate(@NonNull final CurrencyId currencyId, @NonNull final BigDecimal currencyRate)
		{
			this.currencyId = currencyId;
			this.currencyRate = NumberUtils.stripTrailingDecimalZeros(currencyRate);
		}
	}

	@Value
	@Builder(toBuilder = true)
	private static class AmountSourceAndAcct
	{
		@NonNull CurrencyAndRate currencyAndRate;
		@NonNull BigDecimal amtSource;
		@NonNull BigDecimal amtAcct;

		public static AmountSourceAndAcct zero(@NonNull final CurrencyAndRate currencyAndRate)
		{
			return builder().currencyAndRate(currencyAndRate).amtSource(BigDecimal.ZERO).amtAcct(BigDecimal.ZERO).build();
		}

		public boolean isZero()
		{
			return amtSource.signum() == 0 && amtAcct.signum() == 0;
		}

		public boolean isZeroAmtSource()
		{
			return amtSource.signum() == 0;
		}

		public AmountSourceAndAcct negate()
		{
			return isZero()
					? this
					: toBuilder().amtSource(this.amtSource.negate()).amtAcct(this.amtAcct.negate()).build();
		}

		public AmountSourceAndAcct add(@NonNull final AmountSourceAndAcct other)
		{
			assertCurrencyAndRateMatching(other);
			if (other.isZero())
			{
				return this;
			}
			else if (this.isZero())
			{
				return other;
			}
			else
			{
				return toBuilder()
						.amtSource(this.amtSource.add(other.amtSource))
						.amtAcct(this.amtAcct.add(other.amtAcct))
						.build();
			}
		}

		public AmountSourceAndAcct subtract(@NonNull final AmountSourceAndAcct other)
		{
			return add(other.negate());
		}

		private void assertCurrencyAndRateMatching(@NonNull final AmountSourceAndAcct other)
		{
			if (!Objects.equals(this.currencyAndRate, other.currencyAndRate))
			{
				throw new AdempiereException("Currency rate not matching: " + this.currencyAndRate + ", " + other.currencyAndRate);
			}
		}
	}
}   // Doc_Bank
