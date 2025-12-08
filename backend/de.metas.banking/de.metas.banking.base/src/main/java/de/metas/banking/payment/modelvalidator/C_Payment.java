/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.banking.payment.modelvalidator;

import com.google.common.collect.ImmutableList;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.ICashStatementBL;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.PaymentReconcileReference;
import de.metas.payment.api.PaymentReconcileRequest;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;

import java.util.Collection;

/**
 * @author cg
 */
@Interceptor(I_C_Payment.class)
public class C_Payment
{
	private final IBankStatementBL bankStatementBL;
	private final IPaymentBL paymentBL;
	private final ISysConfigBL sysConfigBL;
	private final ICashStatementBL cashStatementBL;

	public C_Payment(
			@NonNull final IBankStatementBL bankStatementBL,
			@NonNull final IPaymentBL paymentBL,
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final ICashStatementBL cashStatementBL)
	{
		this.bankStatementBL = bankStatementBL;
		this.paymentBL = paymentBL;
		this.sysConfigBL = sysConfigBL;
		this.cashStatementBL = cashStatementBL;
	}

	@Init
	public void init(final IModelValidationEngine engine)
	{

		CopyRecordFactory.enableForTableName(I_C_Payment.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Payment.COLUMNNAME_C_Currency_ID, I_C_Payment.COLUMNNAME_C_ConversionType_ID })
	public void onCurrencyChange(final I_C_Payment payment)
	{
		paymentBL.onCurrencyChange(payment);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_Payment.COLUMNNAME_IsOverUnderPayment)
	public void onIsOverUnderPaymentChange(final I_C_Payment payment)
	{
		paymentBL.onIsOverUnderPaymentChange(payment, true);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_Payment.COLUMNNAME_PayAmt)
	public void onPayAmtChange(final I_C_Payment payment)
	{
		paymentBL.onPayAmtChange(payment, true);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID })
	public void onBeforeVoid(final I_C_Payment payment)
	{
		//
		// Make sure we are not allowing a payment, which is on bank statement, to be voided.
		// It shall be reversed if really needed.
		final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
		if (bankStatementBL.isPaymentOnBankStatement(paymentId))
		{
			throw new AdempiereException("@void.payment@");
		}

	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void onAfterReverse(final I_C_Payment payment)
	{
		//
		// Auto-reconcile the payment and it's reversal if the payment is not present on bank statements
		final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
		if (!bankStatementBL.isPaymentOnBankStatement(paymentId))
		{
			final PaymentId reversalId = PaymentId.ofRepoId(payment.getReversal_ID());

			final ImmutableList<PaymentReconcileRequest> requests = ImmutableList.of(
					PaymentReconcileRequest.of(paymentId, PaymentReconcileReference.reversal(reversalId)),
					PaymentReconcileRequest.of(reversalId, PaymentReconcileReference.reversal(paymentId)));

			final Collection<I_C_Payment> preloadedPayments = ImmutableList.of(payment);
			paymentBL.markReconciled(requests, preloadedPayments);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createCashStatementLineIfNeeded(final I_C_Payment payment)
	{
		if (!paymentBL.isCashTrx(payment))
		{
			return;
		}

		if (!sysConfigBL.getBooleanValue("CASH_AS_PAYMENT", true, payment.getAD_Client_ID()))
		{
			return;
		}

		if (bankStatementBL.isPaymentOnBankStatement(PaymentId.ofRepoId(payment.getC_Payment_ID())))
		{
			return;
		}

		cashStatementBL.createCashStatementLine(payment);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Payment.COLUMNNAME_DateTrx })
	public void onDateChange(final I_C_Payment payment)
	{
		paymentBL.updateDiscountAndPayAmtFromInvoiceIfAny(payment);
	}

}
