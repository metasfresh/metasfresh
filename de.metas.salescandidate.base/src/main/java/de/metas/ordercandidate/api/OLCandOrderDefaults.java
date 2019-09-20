package de.metas.ordercandidate.api;

import org.adempiere.warehouse.WarehouseId;

import de.metas.document.DocTypeId;
import de.metas.freighcost.FreightCostRule;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceRule;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
@Builder
public class OLCandOrderDefaults
{
	public static final OLCandOrderDefaults NULL = builder().build();

	private final DocTypeId docTypeTargetId;

	private final DeliveryRule deliveryRule;
	private final DeliveryViaRule deliveryViaRule;
	private final ShipperId shipperId;
	private final WarehouseId warehouseId;
	private final FreightCostRule freightCostRule;

	private final PaymentRule paymentRule;
	private final PaymentTermId paymentTermId;

	private final InvoiceRule invoiceRule;
	private final PricingSystemId pricingSystemId;
}
