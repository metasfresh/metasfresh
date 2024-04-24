/*
 * #%L
 * de.metas.swat.base
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

package de.metas.picking.api;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.freighcost.FreightCostRule;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.money.Money;
import de.metas.order.DeliveryViaRule;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Lines which have to be picked and delivered
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Value
@Builder
public class Packageable
{
	@NonNull OrgId orgId;

	@NonNull
	ShipmentScheduleId shipmentScheduleId;

	@NonNull
	Quantity qtyOrdered;
	@NonNull
	Quantity qtyToDeliver;
	@NonNull
	Quantity qtyDelivered;
	@NonNull
	Quantity qtyPickedAndDelivered;
	@NonNull
	Quantity qtyPickedNotDelivered;
	/**
	 * quantity picked planned (i.e. picking candidates not already processed)
	 */
	@NonNull
	Quantity qtyPickedPlanned;

	@Nullable UomId catchWeightUomId;

	@NonNull
	BPartnerId customerId;
	String customerBPValue;
	String customerName;

	@NonNull
	BPartnerLocationId customerLocationId;
	String customerBPLocationName;
	String customerAddress;
	@NonNull
	BPartnerLocationId handoverLocationId;

	@NonNull
	WarehouseId warehouseId;
	String warehouseName;
	WarehouseTypeId warehouseTypeId;

	DeliveryViaRule deliveryViaRule;

	ShipperId shipperId;
	String shipperName;

	boolean displayed;

	InstantAndOrgId deliveryDate;
	InstantAndOrgId preparationDate;

	@NonNull
	@Default
	Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy = Optional.empty();

	FreightCostRule freightCostRule;

	@NonNull
	ProductId productId;
	String productName;

	@NonNull
	AttributeSetInstanceId asiId;

	@Nullable
	OrderId salesOrderId;
	@Nullable
	String salesOrderDocumentNo;
	@Nullable
	String salesOrderDocSubType;
	@Nullable
	String poReference;

	@Nullable
	OrderLineId salesOrderLineIdOrNull;
	@Nullable
	Money salesOrderLineNetAmt;

	@Nullable
	PPOrderId pickFromOrderId;

	@NonNull
	@Default
	HUPIItemProductId packToHUPIItemProductId = HUPIItemProductId.VIRTUAL_HU;

	@Nullable
	UserId lockedBy;

	@Nullable
	public OrderAndLineId getSalesOrderAndLineIdOrNull() {return OrderAndLineId.ofNullable(salesOrderId, salesOrderLineIdOrNull);}

	public Quantity getQtyPickedOrDelivered()
	{
		// NOTE: keep in sync with M_Packageable_V.QtyPickedOrDelivered
		return getQtyDelivered()
				.add(getQtyPickedNotDelivered())
				.add(getQtyPickedPlanned());

	}

	public Quantity getQtyToPick()
	{
		return qtyToDeliver
				// .subtract(qtyPickedNotDelivered) don't subtract the qtyPickedNotDelivered as it was already subtracted from the qtyToDeliver
				// IMPORTANT: don't subtract the Qty PickedPlanned
				// because we will also allocate existing DRAFT picking candidates
				// .subtract(qtyPickedPlanned)
				.toZeroIfNegative();
	}
}
