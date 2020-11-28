package de.metas.picking.service;

import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.order.DeliveryRule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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
@Builder(toBuilder = true)
public final class PackingItemPart
{
	@NonNull
	private final PackingItemPartId id;

	@NonNull
	private final ProductId productId;
	@NonNull
	private final BPartnerId bpartnerId;
	@NonNull
	private final BPartnerLocationId bpartnerLocationId;
	@Nullable
	private final HUPIItemProductId packingMaterialId;
	@NonNull
	private final WarehouseId warehouseId;
	@NonNull
	private final DeliveryRule deliveryRule;
	@NonNull
	private final TableRecordReference sourceDocumentLineRef;

	@NonNull
	private final Quantity qty;

	public ShipmentScheduleId getShipmentScheduleId()
	{
		return id.getShipmentScheduleId();
	}

	public PackingItemPart withQty(@NonNull final Quantity qty)
	{
		if (Objects.equals(this.qty, qty))
		{
			return this;
		}
		return toBuilder().qty(qty).build();
	}

	public PackingItemPart addQty(@NonNull final Quantity qtyToAdd)
	{
		return withQty(this.qty.add(qtyToAdd));
	}

	public PackingItemPart subtractQty(@NonNull final Quantity qtyToSubtract)
	{
		return withQty(this.qty.subtract(qtyToSubtract));
	}

}
