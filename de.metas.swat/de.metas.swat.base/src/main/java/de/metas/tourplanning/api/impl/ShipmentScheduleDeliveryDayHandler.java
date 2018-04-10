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


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.tourplanning.spi.DeliveryDayHandlerAdapter;
import de.metas.tourplanning.spi.IDeliveryDayCreateHandler;
import lombok.NonNull;

public final class ShipmentScheduleDeliveryDayHandler extends DeliveryDayHandlerAdapter implements IDeliveryDayCreateHandler
{
	public static final transient ShipmentScheduleDeliveryDayHandler INSTANCE = new ShipmentScheduleDeliveryDayHandler();

	private ShipmentScheduleDeliveryDayHandler()
	{
	}

	@Override
	public String getModelTableName()
	{
		return I_M_ShipmentSchedule.Table_Name;
	}

	@Override
	public IDeliveryDayAllocable asDeliveryDayAllocable(@NonNull final Object model)
	{
		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.create(model, I_M_ShipmentSchedule.class);
		return new ShipmentScheduleDeliveryDayAllocable(sched);
	}

	@Override
	public void updateDeliveryDayAllocFromModel(
			@NonNull final I_M_DeliveryDay_Alloc deliveryDayAlloc,
			@NonNull final IDeliveryDayAllocable deliveryDayAllocable)
	{
		// Services
		final IShipmentScheduleDeliveryDayBL shipmentScheduleDeliveryDayBL = Services.get(IShipmentScheduleDeliveryDayBL.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		//
		// Get shipment schedule
		final I_M_ShipmentSchedule sched = shipmentScheduleDeliveryDayBL.getShipmentScheduleOrNull(deliveryDayAllocable, I_M_ShipmentSchedule.class);
		if (sched == null)
		{
			// not applicable for our model
			return;
		}

		deliveryDayAlloc.setM_Product_ID(sched.getM_Product_ID());

		// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
		final BigDecimal qtyOrdered = Services.get(IShipmentScheduleEffectiveBL.class).computeQtyOrdered(sched);

		deliveryDayAlloc.setQtyOrdered(qtyOrdered);

		final BigDecimal qtyDelivered = sched.getQtyDelivered();
		deliveryDayAlloc.setQtyDelivered(qtyDelivered);

		final BigDecimal qtyToDeliver = shipmentScheduleEffectiveBL.getQtyToDeliver(sched);
		deliveryDayAlloc.setQtyToDeliver(qtyToDeliver);
	}

	/**
	 * Does nothing
	 */
	@Override
	public void updateDeliveryDayWhenAllocationChanged(I_M_DeliveryDay deliveryDay, I_M_DeliveryDay_Alloc deliveryDayAlloc, I_M_DeliveryDay_Alloc deliveryDayAllocOld)
	{
		// nothing
	}

	/**
	 * Does nothing
	 */
	@Override
	public void updateTourInstanceWhenDeliveryDayChanged(I_M_Tour_Instance tourInstance, I_M_DeliveryDay deliveryDay, I_M_DeliveryDay deliveryDayOld)
	{
		// nothing
	}
}
