package de.metas.inoutcandidate.spi.impl;

import java.time.ZonedDateTime;

import org.adempiere.util.lang.impl.TableRecordReference;
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
import de.metas.tourplanning.api.IOrderDeliveryDayBL;
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
	private final IOrderDeliveryDayBL orderDeliveryDayBL = Services.get(IOrderDeliveryDayBL.class);

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
		final OrderAndLineId orderAndLineId = extractOrderAndLineId(shipmentSchedule);
		return provideFor(orderAndLineId);
	}

	private ShipmentScheduleReferencedLine provideFor(@NonNull final OrderAndLineId orderAndLineId)
	{
		final OrderId orderId = orderAndLineId.getOrderId();

		final I_C_Order order = ordersRepo.getById(orderId);
		final I_C_OrderLine orderLine = ordersRepo.getOrderLineById(orderAndLineId);

		// Derive the delivery date per order line (presetDateShipped / line DatePromised / header DatePromised),
		// then derive the preparation date FROM that per-line delivery date using the same tour/fallback/offset logic
		// the order header uses. For a single-date order the per-line delivery date equals the header DatePromised,
		// so the preparation date equals today's header value (no regression); for multi-date orders each line gets
		// its own preparation date.
		//
		// The shipment schedule is per order line. The header flag C_Order.IsFixedDatePromised ("ship after the
		// promised date") applies to ALL lines uniformly; what is per-line is the DATE it enforces — this delivery
		// date (the line's DatePromised, overriding the header) becomes the schedule's DeliveryDate. So with the flag
		// on, every line is held until its OWN delivery date (not the whole order until the header date). Same shape
		// for IsFixedPreparationDate (header flag, all lines) + the per-line preparation date. Enforced in
		// de.metas.handlingunits...ShipmentService's enqueue filter.
		final ZonedDateTime deliveryDate = orderDeliveryDayBL.computeDeliveryDate(order, orderLine);

		return ShipmentScheduleReferencedLine.builder()
				.recordRef(TableRecordReference.of(I_C_Order.Table_Name, orderId))
				.preparationDate(orderDeliveryDayBL.computePreparationDate(order, orderLine, deliveryDate))
				.deliveryDate(deliveryDate)
				.warehouseId(warehouseAdvisor.evaluateWarehouse(orderLine))
				.shipperId(ShipperId.optionalOfRepoId(orderLine.getM_Shipper_ID()))
				.documentLineDescriptor(createDocumentLineDescriptor(orderAndLineId, order))
				.build();
	}

	private static OrderAndLineId extractOrderAndLineId(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return OrderAndLineId.ofRepoIds(shipmentSchedule.getC_Order_ID(), shipmentSchedule.getC_OrderLine_ID());
	}

	private static DocumentLineDescriptor createDocumentLineDescriptor(
			@NonNull final OrderAndLineId orderAndLineId,
			@NonNull final I_C_Order order)
	{
		return OrderLineDescriptor.builder()
				.orderId(orderAndLineId.getOrderRepoId())
				.orderLineId(orderAndLineId.getOrderLineRepoId())
				.orderBPartnerId(order.getC_BPartner_ID())
				.docTypeId(order.getC_DocType_ID())
				.build();
	}

}
