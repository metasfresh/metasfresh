package de.metas.bpartner.product.stats.interceptor;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.BPartnerProductStatsEventSender;
import de.metas.bpartner.product.stats.InvoiceChangedEvent;
import de.metas.bpartner.product.stats.InvoiceChangedEvent.ProductPrice;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_C_Invoice.class)
@Component
public class C_Invoice
{
	private final BPartnerProductStatsEventSender eventSender;

	public C_Invoice()
	{
		eventSender = new BPartnerProductStatsEventSender();
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void onComplete(final I_C_Invoice invoice)
	{
		if (isCreditMemo(invoice))
		{
			return;
		}

		if (isReversal(invoice))
		{
			// this is the reversal completion. no need to fire event because a reversal event will be fired
			return;
		}

		final boolean reversal = false;
		eventSender.send(createInvoiceChangedEvent(invoice, reversal));
	}

	private boolean isReversal(final I_C_Invoice invoice)
	{
		return invoice.getReversal_ID() > 0;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_VOID, ModelValidator.TIMING_AFTER_REACTIVATE })
	public void onReverse(final I_C_Invoice invoice)
	{
		if (isCreditMemo(invoice))
		{
			return;
		}

		final boolean reversal = true;
		eventSender.send(createInvoiceChangedEvent(invoice, reversal));
	}

	private InvoiceChangedEvent createInvoiceChangedEvent(final I_C_Invoice invoice, final boolean reversal)
	{
		return InvoiceChangedEvent.builder()
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.invoiceDate(TimeUtil.asLocalDate(invoice.getDateInvoiced()))
				.bpartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()))
				.soTrx(SOTrx.ofBoolean(invoice.isSOTrx()))
				.reversal(reversal)
				.productPrices(extractProductPrices(invoice))
				.build();
	}

	private ImmutableList<ProductPrice> extractProductPrices(final I_C_Invoice invoice)
	{
		final IInvoiceDAO invoicesRepo = Services.get(IInvoiceDAO.class);

		final CurrencyId currencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());

		final ImmutableMap<ProductId, Money> pricesByProductId = invoicesRepo.retrieveLines(invoice)
				.stream()
				.filter(invoiceLine -> invoiceLine.getM_Product_ID() > 0)
				.collect(ImmutableMap.toImmutableMap(
						invoiceLine -> ProductId.ofRepoId(invoiceLine.getM_Product_ID()), // keyMapper
						invoiceLine -> Money.of(invoiceLine.getPriceActual(), currencyId), // valueMapper
						Money::max)); // mergeFunction

		return pricesByProductId.keySet()
				.stream()
				.map(productId -> ProductPrice.builder()
						.productId(productId)
						.price(pricesByProductId.get(productId))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private boolean isCreditMemo(final I_C_Invoice invoice)
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		return invoiceBL.isCreditMemo(invoice);
	}
}
