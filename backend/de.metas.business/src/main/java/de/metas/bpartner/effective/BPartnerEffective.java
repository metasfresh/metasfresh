/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.bpartner.effective;

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@Builder
public class BPartnerEffective
{
	@NonNull @Getter BPartnerId id;
	@Nullable PaymentTermId paymentTermId;
	@Nullable PaymentTermId poPaymentTermId;
	@Nullable PricingSystemId pricingSystemId;
	@Nullable PricingSystemId poPricingSystemId;
	@NonNull InvoiceRule invoiceRule;
	@NonNull InvoiceRule poInvoiceRule;
	@NonNull PaymentRule paymentRule;
	@NonNull PaymentRule poPaymentRule;
	boolean isAutoInvoice;

	@Nullable
	public PaymentTermId getPaymentTermId(@NonNull final SOTrx soTrx)
	{
		return soTrx.isSales() ? paymentTermId : poPaymentTermId;
	}

	@Nullable
	public PricingSystemId getPricingSystemId(@NonNull final SOTrx soTrx)
	{
		return soTrx.isSales() ? pricingSystemId : poPricingSystemId;
	}

	@NonNull
	public PaymentRule getPaymentRule(@NonNull final SOTrx soTrx)
	{
		return soTrx.isSales() ? paymentRule : poPaymentRule;
	}

	@NonNull
	public InvoiceRule getInvoiceRule(@NonNull final SOTrx soTrx)
	{
		return soTrx.isSales() ? invoiceRule : poInvoiceRule;
	}

	public boolean isAutoInvoice(@NonNull final SOTrx soTrx)
	{
		return soTrx.isSales() && isAutoInvoice;
	}
}
