package de.metas.inoutcandidate.api;

import java.time.LocalDateTime;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/** Lines which have to be picked and delivered */
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
	Quantity qtyDelivered;
	/** quantity picked, not yet delivered */
	@NonNull
	Quantity qtyPicked;
	/** quantity picked planned (i.e. picking candidates not already processed) */
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

	@Nullable
	OrderId salesOrderId;
	@Nullable
	String salesOrderDocumentNo;
	@Nullable
	String salesOrderDocSubType;

	@Nullable
	OrderLineId salesOrderLineIdOrNull;
	@Nullable
	Money salesOrderLineNetAmt;
}
