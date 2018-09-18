package de.metas.inoutcandidate.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Packageable
{
	@NonNull
	ShipmentScheduleId shipmentScheduleId;

	@NonNull
	Quantity qtyOrdered;
	@NonNull
	Quantity qtyToDeliver;
	@NonNull
	Quantity qtyPicked;
	@NonNull
	Quantity qtyPickedPlanned;

	@NonNull
	BPartnerId customerId;
	String customerBPValue;
	String customerName;

	@NonNull
	BPartnerLocationId customerLocationId;
	String customerBPLocationName;
	String customerAddress;

	@NonNull
	WarehouseId warehouseId;
	String warehouseName;
	WarehouseTypeId warehouseTypeId;

	String deliveryVia;

	ShipperId shipperId;
	String shipperName;

	boolean displayed;

	LocalDateTime deliveryDate;
	LocalDateTime preparationDate;

	String freightCostRule;

	@NonNull
	ProductId productId;
	String productName;

	@NonNull
	AttributeSetInstanceId asiId;

	OrderId salesOrderId;
	String salesOrderDocumentNo;
	String salesOrderDocSubType;

	@Nullable
	OrderLineId salesOrderLineIdOrNull;
	BigDecimal salesOrderLineNetAmt;
}
