package de.metas.handlingunits.tourplanning.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.tourplanning.model.I_M_DeliveryDay;
import de.metas.handlingunits.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.handlingunits.tourplanning.model.I_M_Tour_Instance;
import de.metas.handlingunits.util.HUDeliveryQuantitiesHelper;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.tourplanning.api.impl.ShipmentScheduleDeliveryDayHandler;
import de.metas.tourplanning.spi.DeliveryDayHandlerAdapter;

/**
 * Handles specific case of HU fields for Shipment Schedules that needs to be allocated to delivery days.
 *
 * Please note, this implementation is just setting HU specific fields and it's NOT create delivery day allocations.<br>
 * For that purpose we relly on {@link ShipmentScheduleDeliveryDayHandler}.
 *
 * @author tsa
 *
 */
public final class HUShipmentScheduleDeliveryDayHandler extends DeliveryDayHandlerAdapter
{
	public static final transient HUShipmentScheduleDeliveryDayHandler instance = new HUShipmentScheduleDeliveryDayHandler();

	private HUShipmentScheduleDeliveryDayHandler()
	{
		super();
	}

	@Override
	public void updateDeliveryDayAllocFromModel(
			final de.metas.tourplanning.model.I_M_DeliveryDay_Alloc deliveryDayAlloc,
			final IDeliveryDayAllocable deliveryDayAllocable)
	{
		// Services
		final IShipmentScheduleDeliveryDayBL shipmentScheduleDeliveryDayBL = Services.get(IShipmentScheduleDeliveryDayBL.class);

		//
		// Get underlying shipment schedule
		final I_M_ShipmentSchedule huShipmentSchedule = shipmentScheduleDeliveryDayBL
				.getShipmentScheduleOrNull(deliveryDayAllocable, I_M_ShipmentSchedule.class);

		if (huShipmentSchedule == null)
		{
			// not applicable for our model
			return;
		}

		//
		// Get HU's version of delivery day allocation
		final I_M_DeliveryDay_Alloc huDeliveryDayAlloc = InterfaceWrapperHelper.create(deliveryDayAlloc, I_M_DeliveryDay_Alloc.class);

		//
		// Copy current HU Quantities from shipment schedule to delivery day allocation
		HUDeliveryQuantitiesHelper.copy(huDeliveryDayAlloc, huShipmentSchedule);
	}

	@Override
	public void updateDeliveryDayWhenAllocationChanged(
			final de.metas.tourplanning.model.I_M_DeliveryDay deliveryDay,
			final de.metas.tourplanning.model.I_M_DeliveryDay_Alloc deliveryDayAlloc,
			final de.metas.tourplanning.model.I_M_DeliveryDay_Alloc deliveryDayAllocOld)
	{
		final I_M_DeliveryDay huDeliveryDay = InterfaceWrapperHelper.create(deliveryDay, I_M_DeliveryDay.class);

		//
		// Get Old Qtys from M_DeliveryDay_Alloc
		I_M_DeliveryDay_Alloc qtysToRemove = InterfaceWrapperHelper.create(deliveryDayAllocOld, I_M_DeliveryDay_Alloc.class);
		if (qtysToRemove != null && !qtysToRemove.isActive())
		{
			qtysToRemove = null;
		}

		//
		// Get New Qtys from M_DeliveryDay_Alloc
		I_M_DeliveryDay_Alloc qtysToAdd = InterfaceWrapperHelper.create(deliveryDayAlloc, I_M_DeliveryDay_Alloc.class);
		if (qtysToAdd != null && !qtysToAdd.isActive())
		{
			qtysToAdd = null;
		}

		// Adjust Delivery Day's quantities
		HUDeliveryQuantitiesHelper.adjust(huDeliveryDay, qtysToRemove, qtysToAdd);
	}

	@Override
	public void updateTourInstanceWhenDeliveryDayChanged(
			final de.metas.tourplanning.model.I_M_Tour_Instance tourInstance,
			final de.metas.tourplanning.model.I_M_DeliveryDay deliveryDay,
			final de.metas.tourplanning.model.I_M_DeliveryDay deliveryDayOld)
	{
		final I_M_Tour_Instance huTourInstance = InterfaceWrapperHelper.create(tourInstance, I_M_Tour_Instance.class);

		//
		// Get Old Qtys from M_DeliveryDay
		I_M_DeliveryDay qtysToRemove = InterfaceWrapperHelper.create(deliveryDayOld, I_M_DeliveryDay.class);
		if (qtysToRemove != null && !qtysToRemove.isActive())
		{
			qtysToRemove = null;
		}

		//
		// Get New Qtys from M_DeliveryDay_Alloc
		I_M_DeliveryDay qtysToAdd = InterfaceWrapperHelper.create(deliveryDay, I_M_DeliveryDay.class);
		if (qtysToAdd != null && !qtysToAdd.isActive())
		{
			qtysToAdd = null;
		}

		// Adjust Tour Instance's quantities
		HUDeliveryQuantitiesHelper.adjust(huTourInstance, qtysToRemove, qtysToAdd);
	}
}
