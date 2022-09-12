package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.service.IBankStatementBL;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.FixedConversionRate;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

class DocLine_BankStatement extends DocLine<Doc_BankStatement>
{
	// services
	private final IBankStatementBL bankStatementBL = SpringContextHolder.instance.getBean(IBankStatementBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	private final ImmutableList<BankStatementLineReferenceAcctInfo> _lineRefs;
	@Getter
	private final boolean reversal;
	private final I_C_Payment _payment;

	@Getter
	private final BigDecimal stmtAmt;
	@Getter
	private final BigDecimal trxAmt;
	@Getter
	private final BigDecimal bankFeeAmt;
	@Getter
	private final BigDecimal chargeAmt;
	@Getter
	private final BigDecimal interestAmt;

	@Getter
	private final BigDecimal fixedCurrencyRate;

	private CurrencyConversionContext _currencyConversionContextForBankAsset; // lazy
	private CurrencyConversionContext _currencyConversionContextForBankInTransit; // lazy

	public DocLine_BankStatement(
			@NonNull final I_C_BankStatementLine line,
			@NonNull final Doc_BankStatement doc,
			@NonNull final ImmutableList<BankStatementLineReferenceAcctInfo> lineRefs)
	{
		super(InterfaceWrapperHelper.getPO(line), doc);
		this._lineRefs = lineRefs;

		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		this._payment = paymentId != null ? paymentBL.getById(paymentId) : null;
		this.reversal = line.isReversal();

		//
		final BankStatementLineAmounts amounts = BankStatementLineAmounts.of(line);
		amounts.assertNoDifferences();
		this.stmtAmt = amounts.getStmtAmt();
		this.trxAmt = amounts.getTrxAmt();
		this.bankFeeAmt = amounts.getBankFeeAmt();
		this.chargeAmt = amounts.getChargeAmt();
		this.interestAmt = amounts.getInterestAmt();

		fixedCurrencyRate = line.getCurrencyRate();
		//
		setDateDoc(TimeUtil.asLocalDate(line.getValutaDate()));
		setBPartnerId(BPartnerId.ofRepoIdOrNull(line.getC_BPartner_ID()));

		//
		// Period
		final MPeriod period = MPeriod.get(Env.getCtx(), line.getDateAcct(), line.getAD_Org_ID());
		if (period != null && period.isOpen(Doc.DOCTYPE_BankStatement, line.getDateAcct(), line.getAD_Org_ID()))
		{
			setC_Period_ID(period.getC_Period_ID());
		}

	}   // DocLine_Bank

	public final List<BankStatementLineReferenceAcctInfo> getReferences()
	{
		return _lineRefs;
	}

	private I_C_Payment getC_Payment()
	{
		return _payment;
	}

	/**
	 * @return payment org (if exists) or line's org
	 */
	public OrgId getPaymentOrgId()
	{
		final I_C_Payment paymentToUse = getC_Payment();
		return getPaymentOrgId(paymentToUse);
	}    // getAD_Org_ID

	/**
	 * @return C_Payment.AD_Org_ID (if any); fallback to {@link #getOrgId()}
	 */
	private OrgId getPaymentOrgId(@Nullable final I_C_Payment payment)
	{
		return payment != null
				? OrgId.ofRepoId(payment.getAD_Org_ID())
				: super.getOrgId();
	}

	/**
	 * @return <ul>
	 * <li>true if this line is an inbound transaction (i.e. we received money in our bank account)
	 * <li>false if this line is an outbound transaction (i.e. we paid money from our bank account)
	 * </ul>
	 */
	public boolean isInboundTrx()
	{
		return getStmtAmt().signum() >= 0;
	}

	private I_C_BankStatementLine getC_BankStatementLine()
	{
		return getModel(I_C_BankStatementLine.class);
	}

	CurrencyConversionContext getCurrencyConversionCtxForBankAsset()
	{
		CurrencyConversionContext currencyConversionContext = this._currencyConversionContextForBankAsset;
		if (currencyConversionContext == null)
		{
			currencyConversionContext = this._currencyConversionContextForBankAsset = createCurrencyConversionCtxForBankAsset();
		}
		return currencyConversionContext;
	}

	private CurrencyConversionContext createCurrencyConversionCtxForBankAsset()
	{
		final I_C_BankStatementLine line = getC_BankStatementLine();

		final OrgId orgId = OrgId.ofRepoId(line.getAD_Org_ID());
		final ZoneId timeZone = services.getTimeZone(orgId);

		// IMPORTANT for Bank Asset Account booking,
		// * we shall NOT consider the fixed Currency Rate because we want to compute currency gain/loss
		// * use default conversion types

		return services.createCurrencyConversionContext(
				TimeUtil.asLocalDate(line.getDateAcct(), timeZone),
				null,
				ClientId.ofRepoId(line.getAD_Client_ID()),
				orgId);
	}

	CurrencyConversionContext getCurrencyConversionCtxForBankInTransit()
	{
		CurrencyConversionContext currencyConversionContext = this._currencyConversionContextForBankInTransit;
		if (currencyConversionContext == null)
		{
			currencyConversionContext = this._currencyConversionContextForBankInTransit = createCurrencyConversionCtxForBankInTransit();
		}
		return currencyConversionContext;
	}

	private CurrencyConversionContext createCurrencyConversionCtxForBankInTransit()
	{
		final I_C_Payment payment = getC_Payment();
		if (payment != null)
		{
			return paymentBL.extractCurrencyConversionContext(payment);
		}
		else
		{
			final I_C_BankStatementLine line = getC_BankStatementLine();

			final PaymentCurrencyContext paymentCurrencyContext = bankStatementBL.getPaymentCurrencyContext(line);

			final OrgId orgId = OrgId.ofRepoId(line.getAD_Org_ID());
			final ZoneId timeZone = services.getTimeZone(orgId);

			CurrencyConversionContext conversionCtx = services.createCurrencyConversionContext(
					TimeUtil.asLocalDate(line.getDateAcct(), timeZone),
					paymentCurrencyContext.getCurrencyConversionTypeId(),
					ClientId.ofRepoId(line.getAD_Client_ID()),
					orgId);

			final FixedConversionRate fixedCurrencyRate = paymentCurrencyContext.toFixedConversionRateOrNull();
			if (fixedCurrencyRate != null)
			{
				conversionCtx = conversionCtx.withFixedConversionRate(fixedCurrencyRate);
			}

			return conversionCtx;
		}
	}
}
