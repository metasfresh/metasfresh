package de.metas.ui.web.pickingV2.productsToPick.rows.factory;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.picking.plan.generator;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.picking.plan.model.IssueToBOMLine;
import de.metas.handlingunits.picking.plan.model.SourceDocumentInfo;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.util.Optional;

@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
@Getter
@ToString
public final class AllocablePackageable
{
	@NonNull private final SourceDocumentInfo sourceDocumentInfo;

	@NonNull private final BPartnerId customerId;
	@NonNull private final ProductId productId;
	@NonNull private final AttributeSetInstanceId asiId;
	@NonNull private final Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy;
	@NonNull private final WarehouseId warehouseId;

	@Nullable private final PPOrderId pickFromOrderId;
	@Nullable private final IssueToBOMLine issueToBOMLine;

	@NonNull private final Quantity qtyToAllocateTarget;
	@NonNull private Quantity qtyToAllocate;

	@Builder(toBuilder = true)
	private AllocablePackageable(
			@NonNull final SourceDocumentInfo sourceDocumentInfo,
			@NonNull final BPartnerId customerId,
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId,
			@Nullable final Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy,
			@NonNull final WarehouseId warehouseId,
			@Nullable final PPOrderId pickFromOrderId,
			@Nullable final IssueToBOMLine issueToBOMLine,
			@NonNull final Quantity qtyToAllocateTarget)
	{
		this.sourceDocumentInfo = sourceDocumentInfo;

		this.customerId = customerId;
		this.productId = productId;
		this.asiId = asiId != null ? asiId : AttributeSetInstanceId.NONE;
		this.bestBeforePolicy = bestBeforePolicy != null ? bestBeforePolicy : Optional.empty();
		this.warehouseId = warehouseId;
		this.pickFromOrderId = pickFromOrderId;
		this.issueToBOMLine = issueToBOMLine;

		this.qtyToAllocateTarget = qtyToAllocateTarget;
		this.qtyToAllocate = qtyToAllocateTarget;
	}

	public void allocateQty(@NonNull final Quantity qty)
	{
		qtyToAllocate = qtyToAllocate.subtract(qty);
	}

	public boolean isAllocated()
	{
		return getQtyToAllocate().signum() == 0;
	}

	public Optional<HUReservationDocRef> getReservationRef()
	{
		return Optional.ofNullable(sourceDocumentInfo.getSalesOrderLineId()).map(HUReservationDocRef::ofSalesOrderLineId);
	}
}
