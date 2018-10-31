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

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Wraps a {@link I_M_ShipmentSchedule} and behave like {@link IDeliveryDayAllocable}
 * 
 * @author tsa
 *
 */
/* package */class ShipmentScheduleDeliveryDayAllocable implements IDeliveryDayAllocable
{
	//
	// Services
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IShipmentScheduleDeliveryDayBL shipmentScheduleDeliveryDayBL = Services.get(IShipmentScheduleDeliveryDayBL.class);

	private final I_M_ShipmentSchedule sched;

	public ShipmentScheduleDeliveryDayAllocable(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "sched not null");
		this.sched = sched;
	}

	public I_M_ShipmentSchedule getM_ShipmentSchedule()
	{
		return sched;
	}

	@Override
	public String getTableName()
	{
		return I_M_ShipmentSchedule.Table_Name;
	}

	@Override
	public int getRecord_ID()
	{
		return sched.getM_ShipmentSchedule_ID();
	}

	@Override
	public Timestamp getDeliveryDate()
	{
		return shipmentScheduleDeliveryDayBL.getDeliveryDateCurrent(sched);
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return shipmentScheduleEffectiveBL.getC_BP_Location_ID(sched);
	}

	@Override
	public int getM_Product_ID()
	{
		return sched.getM_Product_ID();
	}

	@Override
	public boolean isToBeFetched()
	{
		// Shipment Schedules are about sending materials to customer
		return false;
	}

	@Override
	public int getAD_Org_ID()
	{
		return sched.getAD_Org_ID();
	}

}
