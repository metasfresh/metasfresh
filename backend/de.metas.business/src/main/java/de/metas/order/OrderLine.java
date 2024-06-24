package de.metas.order;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.contracts
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

@Value
@Builder
public class OrderLine
{
	@Nullable
	OrderLineId id;

	@NonNull
	OrderId orderId;

	@NonNull
	BPartnerId bPartnerId;

	@NonNull
	ProductId productId;

	@NonNull
	AttributeSetInstanceId asiId;

	@NonNull
	ProductPrice priceActual;

	@NonNull
	Quantity orderedQty;

	@NonNull
	WarehouseId warehouseId;

	int line;

	@NonNull
	PaymentTermId paymentTermId;

	// Note: i think that the following two should go to "Order" once we have it.
	@NonNull
	OrgId orgId;

	/** note: besides the name "datePromised", it's also in the application dictionary declared as date+time, and some businesses need it that way. */
	@NonNull
	ZonedDateTime datePromised;

	@NonNull
	SOTrx soTrx;

	@Nullable
	HUPIItemProductId huPIItemProductId;
}
