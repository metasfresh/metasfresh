package de.metas.inoutcandidate.spi.impl;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLineProvider;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class ShipmentScheduleOrderReferenceProvider implements ShipmentScheduleReferencedLineProvider
{
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);

	/**
	 * @return {@link I_C_OrderLine#Table_Name}
	 */
	@Override
	public String getTableName()
	{
		return I_C_OrderLine.Table_Name;
	}

	@Override
	public ShipmentScheduleReferencedLine provideFor(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return ShipmentScheduleReferencedLine.builder()
				.groupId(shipmentSchedule.getC_Order_ID())
				.preparationDate(getOrderPreparationDate(shipmentSchedule))
				.deliveryDate(getOrderLineDeliveryDate(shipmentSchedule))
				.warehouseId(getWarehouseId(shipmentSchedule))
				.shipperId(ShipperId.optionalOfRepoId(getOrderLine(shipmentSchedule).getM_Shipper_ID()))
				.documentLineDescriptor(getDocumentLineDescriptor(shipmentSchedule))
				.build();
	}

	/**
	 * Fetch it from order header if possible.
	 *
	 * @param shipmentSchedule
	 * @return
	 */
	private Timestamp getOrderPreparationDate(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_C_Order order = getOrder(shipmentSchedule);
		return order.getPreparationDate();
	}

	private I_C_Order getOrder(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final OrderId orderId = extractOrderId(shipmentSchedule);
		return ordersRepo.getById(orderId);
	}

	private OrderId extractOrderId(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return OrderId.ofRepoId(shipmentSchedule.getC_Order_ID());
	}

	private I_C_OrderLine getOrderLine(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final OrderAndLineId orderAndLineId = extractOrderAndLineId(shipmentSchedule);
		return ordersRepo.getOrderLineById(orderAndLineId);
	}

	private OrderAndLineId extractOrderAndLineId(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return OrderAndLineId.ofRepoIds(shipmentSchedule.getC_Order_ID(), shipmentSchedule.getC_OrderLine_ID());
	}

	private Timestamp getOrderLineDeliveryDate(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		// Fetch it from order line if possible
		final I_C_OrderLine orderLine = getOrderLine(shipmentSchedule);
		final Timestamp datePromised = orderLine.getDatePromised();
		if (datePromised != null)
		{
			return datePromised;
		}

		// Fetch it from order header if possible
		final I_C_Order order = getOrder(shipmentSchedule);
		final Timestamp datePromisedFromOrder = order.getDatePromised();
		if (datePromisedFromOrder != null)
		{
			return datePromisedFromOrder;
		}

		// Fail miserably...
		throw new AdempiereException("@NotFound@ @DeliveryDate@")
				.appendParametersToMessage()
				.setParameter("shipmentSchedule", shipmentSchedule)
				.setParameter("oderLine", getOrderLine(shipmentSchedule))
				.setParameter("order", getOrder(shipmentSchedule));
	}

	private WarehouseId getWarehouseId(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return warehouseAdvisor.evaluateWarehouse(getOrderLine(shipmentSchedule));
	}

	private DocumentLineDescriptor getDocumentLineDescriptor(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_C_Order order = getOrder(shipmentSchedule);
		final OrderAndLineId orderAndLineId = extractOrderAndLineId(shipmentSchedule);

		return OrderLineDescriptor.builder()
				.orderId(orderAndLineId.getOrderRepoId())
				.orderLineId(orderAndLineId.getOrderLineRepoId())
				.orderBPartnerId(order.getC_BPartner_ID())
				.docTypeId(order.getC_DocType_ID())
				.build();
	}

}
