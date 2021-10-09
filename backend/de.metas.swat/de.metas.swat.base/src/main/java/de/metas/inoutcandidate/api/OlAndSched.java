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

import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class OlAndSched
{
	private final I_M_ShipmentSchedule shipmentSchedule;
	private final Optional<I_C_OrderLine> salesOrderLine;
	private final Optional<I_C_Order> salesOrder;
	private final IDeliverRequest deliverRequest;
	private final BigDecimal initialSchedQtyDelivered;

	@Builder
	private OlAndSched(
			@Nullable final org.compiere.model.I_C_OrderLine orderLineOrNull,
			@Nullable final org.compiere.model.I_C_Order orderOrNull,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@Nullable final IDeliverRequest deliverRequest)
	{
		this.salesOrderLine = Optional.ofNullable(InterfaceWrapperHelper.create(orderLineOrNull, I_C_OrderLine.class));
		this.salesOrder = Optional.ofNullable(InterfaceWrapperHelper.create(orderOrNull, I_C_Order.class));

		this.shipmentSchedule = shipmentSchedule;

		if (deliverRequest == null)
		{
			this.deliverRequest = Services.get(IShipmentScheduleHandlerBL.class).createDeliverRequest(shipmentSchedule, orderLineOrNull);
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
		return salesOrderLine.get();
	}

	public ProductId getProductId()
	{
		return ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
	}

	public WarehouseId getWarehouseId()
	{
		return Services.get(IShipmentScheduleEffectiveBL.class).getWarehouseId(shipmentSchedule);
	}

	public I_C_UOM getOrderPriceUOM()
	{
		final int priceUomId = getSalesOrderLine().getPrice_UOM_ID();
		return Services.get(IUOMDAO.class).getById(priceUomId);
	}

	public BigDecimal getOrderQtyReserved()
	{
		return getSalesOrderLine().getQtyReserved();
	}

	public BigDecimal getOrderPriceActual()
	{
		return getSalesOrderLine().getPriceActual();
	}

	public DocStatus getOrderDocStatus()
	{
		return DocStatus.ofCode(getSalesOrderLine().getC_Order().getDocStatus());
	}

	public I_M_ShipmentSchedule getSched()
	{
		return shipmentSchedule;
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

	public BigDecimal getInitialSchedQtyDelivered()
	{
		return initialSchedQtyDelivered;
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
}
