/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.payment;

import de.metas.currency.Currency;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

@Callout(I_C_Payment.class)
@Interceptor(I_C_Payment.class)
@Component
@RequiredArgsConstructor
public class C_Payment
{
	private static final AdMessageKey MSG_ProformaPaymentMustBeFull = AdMessageKey.of("de.metas.invoice.proforma.PaymentMustBeFull");

	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final OrderPayScheduleService orderPayScheduleService;

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	/**
	 * Note that because of C_Payment.C_Order_ID's validation rule we can one have only prepay orders in this field.
	 * Before the payment is made, the prepay-order is not even completed, so the payment's date is effectively also the order's date.
	 * So in case of a prepay order there is no need to take the term's DiscountDays into account.
	 */
	@CalloutMethod(columnNames = I_C_Payment.COLUMNNAME_C_Order_ID)
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = I_C_Payment.COLUMNNAME_C_Order_ID)
	public void updateFromOrder(@NonNull final I_C_Payment record)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(record.getC_Order_ID());

		if (orderId == null)
		{
			return;
		}

		// Proforma payments (https://github.com/metasfresh/me03/issues/29368) carry C_Order_ID
		// from the proforma↔order allocation, but their PayAmt comes from the pay-selection line
		// (= proforma's open amount), NOT from order.GrandTotal. Skipping the recompute keeps the
		// builder-set PayAmt intact and lets the BEFORE_PREPARE full-payment guard verify it.
		if (InvoiceId.ofRepoIdOrNull(record.getProforma_Invoice_ID()) != null)
		{
			return;
		}

		final OrderPayScheduleId orderPayScheduleId = OrderPayScheduleId.ofRepoIdOrNull(record.getC_OrderPaySchedule_ID());
		if (orderPayScheduleId != null)
		{
			return; // do not update form order, since all amounts are calculated
		}

		final I_C_Order order = orderDAO.getById(orderId);
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(order.getC_PaymentTerm_ID());

		final Percent paymentTermDiscountPercent = paymentTermService.getPaymentTermDiscount(paymentTermId);
		final Currency currency = currencyRepository.getById(record.getC_Currency_ID());

		final BigDecimal priceActual = paymentTermDiscountPercent.subtractFromBase(order.getGrandTotal(), currency.getPrecision().toInt());
		final BigDecimal discountAmount = paymentTermDiscountPercent.computePercentageOf(order.getGrandTotal(), currency.getPrecision().toInt());

		record.setC_BPartner_ID(firstGreaterThanZero(order.getBill_BPartner_ID(), order.getC_BPartner_ID()));
		record.setC_Currency_ID(order.getC_Currency_ID());

		record.setC_Invoice(null);
		record.setC_Charge_ID(0);
		record.setIsPrepayment(true);

		record.setWriteOffAmt(BigDecimal.ZERO);
		record.setIsOverUnderPayment(false);
		record.setOverUnderAmt(BigDecimal.ZERO);

		//
		record.setPayAmt(priceActual);
		record.setDiscountAmt(discountAmount);
		paymentBL.validateDocTypeIsInSync(record);
	}

	/**
	 * Proforma payments must be full payments (AC #16, https://github.com/metasfresh/me03/issues/29368):
	 * abs(PayAmt) must equal the proforma's GrandTotal. Partial payments are forbidden because there
	 * are no C_AllocationLine rows for proforma payments (no accounting), so a residual balance
	 * cannot be reconciled. Reversal symmetry is automatic — abs(-GrandTotal) == GrandTotal.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE })
	public void assertProformaPaymentIsFull(@NonNull final I_C_Payment payment)
	{
		final InvoiceId proformaInvoiceId = InvoiceId.ofRepoIdOrNull(payment.getProforma_Invoice_ID());
		if (proformaInvoiceId == null)
		{
			return; // not a proforma payment
		}

		final I_C_Invoice proforma = invoiceBL.getById(proformaInvoiceId);
		final BigDecimal expected = proforma.getGrandTotal();
		final BigDecimal actualAbs = payment.getPayAmt().abs();
		if (actualAbs.compareTo(expected) != 0)
		{
			throw new AdempiereException(MSG_ProformaPaymentMustBeFull)
					.appendParametersToMessage()
					.setParameter("proformaInvoiceId", proformaInvoiceId)
					.setParameter("expectedGrandTotal", expected)
					.setParameter("actualPayAmtAbs", actualAbs);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void updateOrderPayScheduleStatus(final I_C_Payment payment)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(payment.getC_Order_ID());
		final OrderPayScheduleId orderPayScheduleId = OrderPayScheduleId.ofRepoIdOrNull(payment.getC_OrderPaySchedule_ID());

		if (orderId == null || orderPayScheduleId == null)
		{
			return;
		}

		orderPayScheduleService.markAsPaid(orderId, orderPayScheduleId);
	}
}