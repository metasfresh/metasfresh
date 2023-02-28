package de.metas.payment.paymentterm.interceptor;

import java.math.BigDecimal;

import de.metas.payment.paymentterm.impl.PaymentTerm;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PaymentTerm;
import org.springframework.stereotype.Component;

import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Callout(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final PaymentTermService paymentTermService;

	public C_OrderLine(@NonNull final PaymentTermService paymentTermService)
	{
		this.paymentTermService = paymentTermService;

		// There is no callout-pendant to ModelValidationEngine that automatically registers callout classes for us,
		// so we need to do it ourselves.
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(skipIfCopying = true, columnNames = I_C_OrderLine.COLUMNNAME_PaymentDiscount)
	public void updatePaymentTermIdFromPaymentDiscount(@NonNull final I_C_OrderLine orderLineRecord, @NonNull final ICalloutField field)
	{
		updatePaymentTermId(orderLineRecord);
	}

	private void updatePaymentTermId(@NonNull final I_C_OrderLine orderLineRecord)
	{
		final PaymentTermId basePaymentTermId = Services.get(IOrderLineBL.class).getPaymentTermId(orderLineRecord);
		final Percent paymentDiscount = Percent.of(orderLineRecord.getPaymentDiscount());
		final PaymentTermId derivedPaymentTermId = paymentTermService.getOrCreateDerivedPaymentTerm(basePaymentTermId, paymentDiscount);
		orderLineRecord.setC_PaymentTerm_Override_ID(PaymentTermId.toRepoId(derivedPaymentTermId));
	}

	@CalloutMethod(skipIfCopying = true, columnNames = { I_C_OrderLine.COLUMNNAME_C_PaymentTerm_Override_ID, I_C_OrderLine.COLUMNNAME_PaymentDiscount })
	public void setManualPaymentTermFlag(@NonNull final I_C_OrderLine orderLineRecord, @NonNull final ICalloutField field)
	{
		orderLineRecord.setIsManualPaymentTerm(true);
	}

	@CalloutMethod(skipIfCopying = true, columnNames = { I_C_OrderLine.COLUMNNAME_C_PaymentTerm_Override_ID })
	public void resetPaymentDiscount(@NonNull final I_C_OrderLine orderLineRecord, @NonNull final ICalloutField field)
	{
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoIdOrNull(orderLineRecord.getC_PaymentTerm_Override_ID());
		if (paymentTermId == null)
		{
			orderLineRecord.setPaymentDiscount(null);
			return;
		}

		final PaymentTerm paymentTermRecord = Services.get(IPaymentTermRepository.class).getById(paymentTermId);
		final Percent paymentDiscount = paymentTermRecord.getDiscount();
		orderLineRecord.setPaymentDiscount(paymentDiscount.toBigDecimal());
	}

	@CalloutMethod(skipIfCopying = true, columnNames = I_C_OrderLine.COLUMNNAME_IsManualPaymentTerm)
	public void resetPrices(@NonNull final I_C_OrderLine orderLineRecord, @NonNull final ICalloutField field)
	{
		if (orderLineRecord.isManualPaymentTerm())
		{
			return;
		}

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		orderLineBL.updatePrices(OrderLinePriceUpdateRequest.ofOrderLine(orderLineRecord));
	}
}
