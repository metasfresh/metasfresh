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
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Callout(I_C_Payment.class)
@Interceptor(I_C_Payment.class)
@Component
public class C_Payment
{
	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	private final CurrencyRepository currencyRepository;

	public C_Payment(@NonNull final CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}

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
	public void updateDiscountAndPaymentAmount(@NonNull final I_C_Payment record)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(record.getC_Order_ID());

		if (orderId == null)
		{
			record.setPayAmt(BigDecimal.ZERO);
			record.setDiscountAmt(null);

			return;
		}

		final I_C_Order order = orderDAO.getById(orderId);

		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(order.getC_PaymentTerm_ID());
		final Percent paymentTermDiscountPercent = paymentTermRepository.getPaymentTermDiscount(paymentTermId);
		final Currency currency = currencyRepository.getById(record.getC_Currency_ID());

		final BigDecimal priceActual = paymentTermDiscountPercent.subtractFromBase(order.getGrandTotal(), currency.getPrecision().toInt());
		final BigDecimal discountAmount = paymentTermDiscountPercent.computePercentageOf(order.getGrandTotal(), currency.getPrecision().toInt());

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
}