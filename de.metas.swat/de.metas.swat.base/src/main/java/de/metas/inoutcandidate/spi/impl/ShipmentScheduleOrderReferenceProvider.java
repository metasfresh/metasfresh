package de.metas.inoutcandidate.spi.impl;

import lombok.NonNull;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLineProvider;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;

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

@Service
public class ShipmentScheduleOrderReferenceProvider implements ShipmentScheduleReferencedLineProvider
{
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
				.shipperId(ShipperId.optionalOfRepoId(shipmentSchedule.getC_OrderLine().getM_Shipper_ID()))
				.documentLineDescriptor(getDocumentLineDescriptor(shipmentSchedule))
				.build();
	}

	/**
	 * Fetch it from order header if possible.
	 *
	 * @param shipmentSchedule
	 * @return
	 */
	private static Timestamp getOrderPreparationDate(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_C_Order order = shipmentSchedule.getC_Order();
		return order.getPreparationDate();
	}

	private static Timestamp getOrderLineDeliveryDate(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		// Fetch it from order line if possible
		final I_C_OrderLine orderLine = shipmentSchedule.getC_OrderLine();
		final Timestamp datePromised = orderLine.getDatePromised();
		if (datePromised != null)
		{
			return datePromised;
		}

		// Fetch it from order header if possible
		final I_C_Order order = shipmentSchedule.getC_Order();
		final Timestamp datePromisedFromOrder = order.getDatePromised();
		if (datePromisedFromOrder != null)
		{
			return datePromisedFromOrder;
		}

		// Fail miserably...
		throw new AdempiereException("@NotFound@ @DeliveryDate@")
				.appendParametersToMessage()
				.setParameter("shipmentSchedule", shipmentSchedule)
				.setParameter("oderLine", shipmentSchedule.getC_OrderLine())
				.setParameter("order", shipmentSchedule.getC_Order());
	}

	private WarehouseId getWarehouseId(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return Services.get(IWarehouseAdvisor.class).evaluateWarehouse(shipmentSchedule.getC_OrderLine());
	}

	private DocumentLineDescriptor getDocumentLineDescriptor(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return OrderLineDescriptor.builder()
				.orderId(shipmentSchedule.getC_Order_ID())
				.orderLineId(shipmentSchedule.getC_OrderLine_ID())
				.orderBPartnerId(shipmentSchedule.getC_Order().getC_BPartner_ID())
				.docTypeId(shipmentSchedule.getC_Order().getC_DocType_ID())
				.build();
	}

}
