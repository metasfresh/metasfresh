package org.compiere.acct;

import com.google.common.collect.ImmutableList;
<<<<<<< HEAD
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReference;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
=======
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.service.IBankStatementBL;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.FixedConversionRate;
import de.metas.document.DocBaseType;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentCurrencyContext;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
<<<<<<< HEAD
=======
import org.adempiere.service.ClientId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;
<<<<<<< HEAD
import org.compiere.util.TimeUtil;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

class DocLine_BankStatement extends DocLine<Doc_BankStatement>
{
	// services
	private final IBankStatementBL bankStatementBL = SpringContextHolder.instance.getBean(IBankStatementBL.class);
<<<<<<< HEAD

	private final List<BankStatementLineReference> _bankStatementLineReferences;
=======
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	private final ImmutableList<BankStatementLineReferenceAcctInfo> _lineRefs;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	public DocLine_BankStatement(final I_C_BankStatementLine line, final Doc_BankStatement doc)
	{
		super(InterfaceWrapperHelper.getPO(line), doc);

		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		this._payment = paymentId != null
				? Services.get(IPaymentBL.class).getById(paymentId)
				: null;
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		setDateDoc(TimeUtil.asLocalDate(line.getValutaDate()));
		setBPartnerId(BPartnerId.ofRepoIdOrNull(line.getC_BPartner_ID()));

		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(line.getC_BankStatementLine_ID());
		this._bankStatementLineReferences = ImmutableList.copyOf(bankStatementDAO.getLineReferences(bankStatementLineId));

		//
		// Period
		final MPeriod period = MPeriod.get(Env.getCtx(), line.getDateAcct(), line.getAD_Org_ID());
		if (period != null && period.isOpen(Doc.DOCTYPE_BankStatement, line.getDateAcct(), line.getAD_Org_ID()))
=======
		setDateDoc(LocalDateAndOrgId.ofTimestamp(
				line.getValutaDate(),
				OrgId.ofRepoId(line.getAD_Org_ID()),
				services::getTimeZone));
		setBPartnerId(BPartnerId.ofRepoIdOrNull(line.getC_BPartner_ID()));

		//
		// Period
		final MPeriod period = MPeriod.get(Env.getCtx(), line.getDateAcct(), line.getAD_Org_ID());
		if (period != null && period.isOpen(DocBaseType.BankStatement, line.getDateAcct(), line.getAD_Org_ID()))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			setC_Period_ID(period.getC_Period_ID());
		}

	}   // DocLine_Bank

<<<<<<< HEAD
	public final List<BankStatementLineReference> getReferences()
	{
		return _bankStatementLineReferences;
=======
	public final List<BankStatementLineReferenceAcctInfo> getReferences()
	{
		return _lineRefs;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	public final OrgId getPaymentOrgId(@Nullable final PaymentId paymentId)
	{
		final I_C_Payment payment = paymentId != null
				? Services.get(IPaymentBL.class).getById(paymentId)
				: null;

		return getPaymentOrgId(payment);
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
	public CurrencyConversionContext getCurrencyConversionCtx()
	{
		final I_C_BankStatementLine line = getC_BankStatementLine();
		return bankStatementBL.getCurrencyConversionCtx(line);
	}

	public boolean isBankTransfer()
	{
		final I_C_BankStatementLine bsl = getC_BankStatementLine();
		return bsl.getC_BP_BankAccountTo_ID() > 0 || bsl.getLink_BankStatementLine_ID() > 0;
=======
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

		// IMPORTANT for Bank Asset Account booking,
		// * we shall NOT consider the fixed Currency Rate because we want to compute currency gain/loss
		// * use default conversion types

		return services.createCurrencyConversionContext(
				LocalDateAndOrgId.ofTimestamp(line.getDateAcct(), orgId, services::getTimeZone),
				null,
				ClientId.ofRepoId(line.getAD_Client_ID()));
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

			CurrencyConversionContext conversionCtx = services.createCurrencyConversionContext(
					LocalDateAndOrgId.ofTimestamp(line.getDateAcct(), orgId, services::getTimeZone),
					paymentCurrencyContext.getCurrencyConversionTypeId(),
					ClientId.ofRepoId(line.getAD_Client_ID()));

			final FixedConversionRate fixedCurrencyRate = paymentCurrencyContext.toFixedConversionRateOrNull();
			if (fixedCurrencyRate != null)
			{
				conversionCtx = conversionCtx.withFixedConversionRate(fixedCurrencyRate);
			}

			return conversionCtx;
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
