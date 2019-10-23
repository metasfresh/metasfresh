package de.metas.ui.web.pickingV2.productsToPick.rows.factory;

import java.util.Optional;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


@ToString class AllocablePackageable
{
	public static AllocablePackageable of(final Packageable packageable)
	{
		return new AllocablePackageable(packageable);
	}

	@Getter
	private BPartnerId customerId;
	@Getter
	private final ProductId productId;
	@Getter
	private final AttributeSetInstanceId asiId;
	@Getter
	private final ShipmentScheduleId shipmentScheduleId;
	@Getter
	private final Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy;
	@Getter
	private final WarehouseId warehouseId;
	@Getter
	private final OrderLineId salesOrderLineIdOrNull;

	private final Quantity qtyToAllocateTarget;
	@Getter
	private Quantity qtyToAllocate;

	private AllocablePackageable(@NonNull final Packageable packageable)
	{
		this.customerId = packageable.getCustomerId();
		this.productId = packageable.getProductId();
		this.asiId = packageable.getAsiId();
		this.shipmentScheduleId = packageable.getShipmentScheduleId();
		this.bestBeforePolicy = packageable.getBestBeforePolicy();
		this.warehouseId = packageable.getWarehouseId();
		this.salesOrderLineIdOrNull = packageable.getSalesOrderLineIdOrNull();

		qtyToAllocateTarget = packageable.getQtyOrdered()
				.subtract(packageable.getQtyPickedOrDelivered())
				.toZeroIfNegative();

		qtyToAllocate = qtyToAllocateTarget;
	}

	public void allocateQty(final Quantity qty)
	{
		qtyToAllocate = qtyToAllocate.subtract(qty);
	}

	public boolean isAllocated()
	{
		return getQtyToAllocate().signum() == 0;
	}
}