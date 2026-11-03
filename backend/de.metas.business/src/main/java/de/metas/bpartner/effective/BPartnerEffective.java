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
import de.metas.freighcost.FreightCostRule;
import de.metas.incoterms.Incoterms;
import de.metas.shipping.ShipperId;
import de.metas.lang.SOTrx;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.InvoiceRule;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

@Builder
public class BPartnerEffective
{
	@NonNull @Getter private final BPartnerId id;
	@Nullable private final PaymentTermId paymentTermId;
	@Nullable private final PaymentTermId poPaymentTermId;
	@Nullable private final PricingSystemId pricingSystemId;
	@Nullable private final PricingSystemId poPricingSystemId;
	@NonNull private final InvoiceRule invoiceRule;
	@NonNull private final InvoiceRule poInvoiceRule;
	@NonNull private final PaymentRule paymentRule;
	@NonNull private final PaymentRule poPaymentRule;
	@Nullable private final Incoterms incoterms;
	@Nullable private final Incoterms poIncoterms;
	private final boolean isAutoInvoice;
	@Nullable private final Integer purchaseTransportDays;
	@Getter private final boolean isPreAdviceRequired;
	@Getter @Nullable private final ShipperId shipperId;
	// sales-only: C_BPartner.SalesRep_ID has no purchase counterpart, hence no SOTrx split
	@Getter @Nullable private final UserId salesRepId;
	@Getter @Nullable private final FreightCostRule freightCostRule;
	@Nullable private final DeliveryRule deliveryRule;
	@Nullable private final DeliveryViaRule deliveryViaRule;
	@Nullable private final DeliveryViaRule poDeliveryViaRule;

	@Nullable
	public DeliveryRule getDeliveryRule(@NonNull final SOTrx soTrx)
	{
		// sales-only: C_BPartner.DeliveryRule has no purchase counterpart
		return soTrx.isSales() ? deliveryRule : null;
	}

	@Nullable
	public DeliveryViaRule getDeliveryViaRule(@NonNull final SOTrx soTrx)
	{
		return soTrx.isSales() ? deliveryViaRule : poDeliveryViaRule;
	}

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

	@Nullable
	public Incoterms getIncoterms(@NonNull final SOTrx soTrx)
	{
		return soTrx.isSales() ? incoterms : poIncoterms;
	}

	@NonNull
	public Optional<Integer> getPurchaseTransportDaysIfSet()
	{
		return Optional.ofNullable(purchaseTransportDays);
	}

	public int getPurchaseTransportDays()
	{
		return getPurchaseTransportDaysIfSet().orElse(0);
	}
}
