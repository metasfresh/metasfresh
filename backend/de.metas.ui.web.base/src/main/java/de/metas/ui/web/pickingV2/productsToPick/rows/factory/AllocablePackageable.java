package de.metas.ui.web.pickingV2.productsToPick.rows.factory;

import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.inoutcandidate.ShipmentScheduleId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import lombok.Builder;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Getter
@ToString
final class AllocablePackageable
{
	private final BPartnerId customerId;
	private final ProductId productId;
	private final AttributeSetInstanceId asiId;
	private final ShipmentScheduleId shipmentScheduleId;
	private final Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy;
	private final WarehouseId warehouseId;
	private final OrderLineId salesOrderLineIdOrNull;
	private final ShipperId shipperId;

	private final PPOrderId pickFromOrderId;
	private final PPOrderBOMLineId issueToOrderBOMLineId;

	private final Quantity qtyToAllocateTarget;
	private Quantity qtyToAllocate;

	@Builder(toBuilder = true)
	private AllocablePackageable(
			@NonNull final BPartnerId customerId,
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy,
			@NonNull final WarehouseId warehouseId,
			@Nullable final OrderLineId salesOrderLineIdOrNull,
			@Nullable final ShipperId shipperId,
			@Nullable final PPOrderId pickFromOrderId,
			@Nullable final PPOrderBOMLineId issueToOrderBOMLineId,
			@NonNull final Quantity qtyToAllocateTarget)
	{
		this.customerId = customerId;
		this.productId = productId;
		this.asiId = asiId != null ? asiId : AttributeSetInstanceId.NONE;
		this.shipmentScheduleId = shipmentScheduleId;
		this.bestBeforePolicy = bestBeforePolicy != null ? bestBeforePolicy : Optional.empty();
		this.warehouseId = warehouseId;
		this.salesOrderLineIdOrNull = salesOrderLineIdOrNull;
		this.shipperId = shipperId;
		this.pickFromOrderId = pickFromOrderId;
		this.issueToOrderBOMLineId = issueToOrderBOMLineId;

		this.qtyToAllocateTarget = qtyToAllocateTarget;
		this.qtyToAllocate = qtyToAllocateTarget;
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
