package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankStatementLineReference;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

class DocLine_BankStatement extends DocLine<Doc_BankStatement>
{
	// services
	private final IBankStatementBL bankStatementBL = SpringContextHolder.instance.getBean(IBankStatementBL.class);

	private final List<BankStatementLineReference> _bankStatementLineReferences;
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

	public DocLine_BankStatement(final I_C_BankStatementLine line, final Doc_BankStatement doc)
	{
		super(InterfaceWrapperHelper.getPO(line), doc);

		final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		this._payment = paymentId != null
				? Services.get(IPaymentBL.class).getById(paymentId)
				: null;
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

		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(line.getC_BankStatementLine_ID());
		this._bankStatementLineReferences = ImmutableList.copyOf(bankStatementDAO.getLineReferences(bankStatementLineId));

		//
		// Period
		final MPeriod period = MPeriod.get(Env.getCtx(), line.getDateAcct(), line.getAD_Org_ID());
		if (period != null && period.isOpen(Doc.DOCTYPE_BankStatement, line.getDateAcct(), line.getAD_Org_ID()))
		{
			setC_Period_ID(period.getC_Period_ID());
		}

	}   // DocLine_Bank

	public final List<BankStatementLineReference> getReferences()
	{
		return _bankStatementLineReferences;
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

	public final OrgId getPaymentOrgId(@Nullable final PaymentId paymentId)
	{
		final I_C_Payment payment = paymentId != null
				? Services.get(IPaymentBL.class).getById(paymentId)
				: null;

		return getPaymentOrgId(payment);
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

	public CurrencyConversionContext getCurrencyConversionCtx()
	{
		final I_C_BankStatementLine line = getC_BankStatementLine();
		return bankStatementBL.getCurrencyConversionCtx(line);
	}

	public boolean isBankTransfer()
	{
		final I_C_BankStatementLine bsl = getC_BankStatementLine();
		return bsl.getC_BP_BankAccountTo_ID() > 0 || bsl.getLink_BankStatementLine_ID() > 0;
	}
}
