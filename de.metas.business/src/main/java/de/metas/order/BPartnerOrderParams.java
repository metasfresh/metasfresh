package de.metas.order;

import java.util.Optional;

import de.metas.freighcost.FreightCostRule;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

/**
 * Holds bpartner related parameters that are required for an oder.
 * Note that e.g. the delivery rule might be related to the order's drop ship partner, while the pricing system id might be related to the order's bill partner.
 */
@Value
@Builder
public class BPartnerOrderParams
{
	@NonNull
	Optional<DeliveryRule> deliveryRule;

	@NonNull
	Optional<DeliveryViaRule> deliveryViaRule;

	@NonNull
	Optional<FreightCostRule> freightCostRule;

	@NonNull
	Optional<InvoiceRule> invoiceRule;

	@NonNull
	PaymentRule paymentRule;

	@NonNull
	Optional<PaymentTermId> paymentTermId;

	@NonNull
	Optional<PricingSystemId> pricingSystemId;

	@NonNull
	Optional<ShipperId> shipperId;
}
