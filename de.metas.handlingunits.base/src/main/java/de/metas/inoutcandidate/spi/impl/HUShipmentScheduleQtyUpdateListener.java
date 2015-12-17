package de.metas.inoutcandidate.spi.impl;

import org.adempiere.util.Services;

import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentScheduleQtyUpdateListener;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class HUShipmentScheduleQtyUpdateListener implements IShipmentScheduleQtyUpdateListener
{

	public static final transient HUShipmentScheduleQtyUpdateListener instance = new HUShipmentScheduleQtyUpdateListener();

	@Override
	public void updateQtys(final I_M_ShipmentSchedule schedule)
	{
		// Trigger the HU Delivery Qty update.
		// This way, we make sure the HU qtys are updated each time the shipment schedule update process is triggered
		Services.get(IHUShipmentScheduleBL.class).updateHUDeliveryQuantities(schedule);
	}

}
