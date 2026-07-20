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

import de.metas.bpartner.BPartnerId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.DeliveryRule;
import de.metas.order.OrderLineId;
import de.metas.order.model.I_C_Order;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandSegment;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class OlAndSched
{
	@NonNull private final OlAndSchedSupportingService services;
	@NonNull private final I_M_ShipmentSchedule shipmentSchedule;
	@NonNull private final Optional<I_C_OrderLine> salesOrderLine;
	@NonNull private final Optional<I_C_Order> salesOrder;
	@Nullable private final IDeliverRequest deliverRequest;
	@NonNull @Getter private final BigDecimal initialSchedQtyDelivered;

	@Builder
	private OlAndSched(
			@NonNull OlAndSchedSupportingService services,
			@Nullable final org.compiere.model.I_C_OrderLine orderLineOrNull,
			@Nullable final org.compiere.model.I_C_Order orderOrNull,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@Nullable final IDeliverRequest deliverRequest)
	{
		this.services = services;

		this.salesOrderLine = Optional.ofNullable(InterfaceWrapperHelper.create(orderLineOrNull, I_C_OrderLine.class));
		this.salesOrder = Optional.ofNullable(InterfaceWrapperHelper.create(orderOrNull, I_C_Order.class));

		this.shipmentSchedule = shipmentSchedule;

		if (deliverRequest == null)
		{
			this.deliverRequest = services.createDeliverRequest(shipmentSchedule, orderLineOrNull);
		}
		else
		{
			this.deliverRequest = deliverRequest;
		}

		initialSchedQtyDelivered = shipmentSchedule.getQtyDelivered();
	}

	@Override
	public String toString()
	{
		return shipmentSchedule + "/" + salesOrderLine.orElse(null);
	}

	public BigDecimal getQtyOrdered()
	{
		return deliverRequest.getQtyOrdered();
	}

	public boolean hasSalesOrderLine()
	{
		return salesOrderLine.isPresent();
	}

	private I_C_OrderLine getSalesOrderLine()
	{
		return salesOrderLine.orElseThrow(
				() -> new AdempiereException("No sales order line")
						.setParameter("shipmentSchedule", shipmentSchedule)
		);
	}

	@NonNull
	public ProductId getProductId()
	{
		return ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
	}

	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return AttributeSetInstanceId.ofRepoIdOrNone(shipmentSchedule.getM_AttributeSetInstance_ID());
	}

	@NonNull
	public WarehouseId getWarehouseId()
	{
		return services.getWarehouseId(shipmentSchedule);
	}

	@NonNull
	public BPartnerId getBPartnerId()
	{
		return services.getBPartnerId(shipmentSchedule);
	}

	public I_C_UOM getOrderPriceUOM()
	{
		final UomId priceUomId = UomId.ofRepoId(getSalesOrderLine().getPrice_UOM_ID());
		return services.getUOMById(priceUomId);
	}

	public BigDecimal getOrderQtyReserved()
	{
		return getSalesOrderLine().getQtyReserved();
	}

	public BigDecimal getOrderPriceActual()
	{
		return getSalesOrderLine().getPriceActual();
	}

	public I_M_ShipmentSchedule getSched()
	{
		return shipmentSchedule;
	}

	public Optional<OrderLineId> getSalesOrderLineId()
	{
		return OrderLineId.optionalOfRepoId(getSched().getC_OrderLine_ID());
	}

	public ShipmentScheduleId getShipmentScheduleId()
	{
		return ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
	}

	/**
	 * @return shipment schedule's QtyToDeliver_Override or <code>null</code>
	 */
	public BigDecimal getQtyOverride()
	{
		return InterfaceWrapperHelper.getValueOrNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override);
	}

	public void setShipmentScheduleLineNetAmt(final BigDecimal lineNetAmt)
	{
		shipmentSchedule.setLineNetAmt(lineNetAmt);
	}

	@Nullable
	public String getSalesOrderPORef()
	{
		return salesOrder.map(I_C_Order::getPOReference).orElse(null);
	}

	public ImmutableShipmentScheduleSegment getShipmentScheduleSegment()
	{
		return ImmutableShipmentScheduleSegment.builder()
				.anyBPartner()
				.bpartnerId(getBPartnerId().getRepoId())
				.productId(getProductId().getRepoId())
				.warehouseId(getWarehouseId().getRepoId())
				.build();

	}

	public ShipmentScheduleQtyOnHandSegment getQtyOnHandSegment()
	{
		return ShipmentScheduleQtyOnHandSegment.builder()
				.warehouseId(getWarehouseId())
				.productId(getProductId())
				.pickFromManufacturingOrderId(getPickFromManufacturingOrderId())
				.shipmentScheduleId(getShipmentScheduleId())
				.sourceRef(TableRecordReference.ofReferenced(shipmentSchedule))
				.build();
	}

	private @Nullable PPOrderId getPickFromManufacturingOrderId()
	{
		return PPOrderId.ofRepoIdOrNull(shipmentSchedule.getPickFrom_Order_ID());
	}

	public DeliveryRule getDeliveryRule()
	{
		return services.getDeliveryRule(shipmentSchedule);
	}

	public BigDecimal getQtyPickList()
	{
		return shipmentSchedule.getQtyPickList();
	}

	public void setQtyOnHand(final BigDecimal qtyOnHand)
	{
		shipmentSchedule.setQtyOnHand(qtyOnHand);
	}

}
