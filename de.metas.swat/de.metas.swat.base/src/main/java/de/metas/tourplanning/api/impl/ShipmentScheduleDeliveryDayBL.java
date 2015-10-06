package de.metas.tourplanning.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;

public class ShipmentScheduleDeliveryDayBL implements IShipmentScheduleDeliveryDayBL
{
	@Override
	public IDeliveryDayAllocable asDeliveryDayAllocable(final I_M_ShipmentSchedule sched)
	{
		return ShipmentScheduleDeliveryDayHandler.instance.asDeliveryDayAllocable(sched);
	}

	@Override
	public <T extends I_M_ShipmentSchedule> T getShipmentScheduleOrNull(IDeliveryDayAllocable deliveryDayAllocable, final Class<T> modelClass)
	{
		if (deliveryDayAllocable == null)
		{
			return null;
		}
		else if (deliveryDayAllocable instanceof ShipmentScheduleDeliveryDayAllocable)
		{
			final ShipmentScheduleDeliveryDayAllocable shipmentScheduleDeliveryDayAllocable = (ShipmentScheduleDeliveryDayAllocable)deliveryDayAllocable;
			final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleDeliveryDayAllocable.getM_ShipmentSchedule();
			return InterfaceWrapperHelper.create(shipmentSchedule, modelClass);
		}
		else
		{
			return null;
		}
	}

	@Override
	public final void updateDeliveryDayInfo(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "sched not null");

		updateDeliveryDayInfoFromOrder(sched);

		//
		// Get Delivery Day Allocation
		final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);
		final IContextAware context = InterfaceWrapperHelper.getContextAware(sched);
		final IDeliveryDayAllocable deliveryDayAllocable = asDeliveryDayAllocable(sched);
		final I_M_DeliveryDay_Alloc deliveryDayAlloc = deliveryDayBL.getCreateDeliveryDayAlloc(context, deliveryDayAllocable);
		if (deliveryDayAlloc == null)
		{
			return;
		}

		InterfaceWrapperHelper.save(deliveryDayAlloc); // make sure is saved
	}

	private final void updateDeliveryDayInfoFromOrder(final I_M_ShipmentSchedule sched)
	{
		final Timestamp deliveryDate = getOrderLineDeliveryDate(sched);
		final Timestamp preparationDate = getOrderPreparationDate(sched);

		sched.setDeliveryDate(deliveryDate);
		sched.setPreparationDate(preparationDate);
	}

	@Override
	public Timestamp getDeliveryDateCurrent(final I_M_ShipmentSchedule sched)
	{
		//
		// If DeliveryDate was set, return it
		final Timestamp deliveryDate = Services.get(IShipmentScheduleBL.class).getDeliveryDateEffective(sched);

		if (deliveryDate != null)
		{
			return deliveryDate;
		}

		return getOrderLineDeliveryDate(sched);
	}

	private Timestamp getOrderLineDeliveryDate(final I_M_ShipmentSchedule sched)
	{
		//
		// Fetch it from order line if possible
		final I_C_OrderLine orderLine = sched.getC_OrderLine();
		if (orderLine != null && orderLine.getC_OrderLine_ID() > 0)
		{
			final Timestamp datePromised = orderLine.getDatePromised();
			if (datePromised != null)
			{
				return datePromised;
			}
		}

		//
		// Fetch it from order header if possible
		final org.compiere.model.I_C_Order order = sched.getC_Order();
		if (order != null && order.getC_Order_ID() > 0)
		{
			final Timestamp datePromised = order.getDatePromised();
			if (datePromised != null)
			{
				return datePromised;
			}
		}

		//
		// Fail miserably...
		throw new AdempiereException("@NotFound@ @DeliveryDate@"
				+ "\n @M_ShipmentSchedule_ID@: " + sched
				+ "\n @C_OrderLine_ID@: " + orderLine
				+ "\n @C_Order_ID@: " + order);
	}

	private Timestamp getOrderPreparationDate(final I_M_ShipmentSchedule sched)
	{
		//
		// Fetch it from order header if possible
		final org.compiere.model.I_C_Order order = sched.getC_Order();
		if (order != null && order.getC_Order_ID() > 0)
		{
			final Timestamp preparationDate = order.getPreparationDate();

			if (preparationDate != null)
			{
				return preparationDate;
			}
		}

		return null;
	}
}
