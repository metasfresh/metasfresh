package de.metas.handlingunits.allocation.impl;

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


import java.util.List;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleQtyPickedProductStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Check;

/**
 * Allocate over a list of {@link I_M_ShipmentSchedule}s using QtyPicked as storage level.
 *
 * @author tsa
 *
 */
public class ShipmentScheduleListAllocationSource extends GenericListAllocationSourceDestination
{
	public ShipmentScheduleListAllocationSource(final List<I_M_ShipmentSchedule> schedules)
	{
		super();

		Check.assumeNotNull(schedules, "schedules not null");
		Check.assume(!schedules.isEmpty(), "schedules not empty");

		addShipmentSchedules(schedules);
	}

	public void addShipmentSchedule(final I_M_ShipmentSchedule schedule)
	{
		final IProductStorage storage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		final GenericAllocationSourceDestination sourceOrDestination = new GenericAllocationSourceDestination(storage,
				(I_M_HU_Item)null, // we don't have any HU Item
				schedule // we use our shipment schedule as a reference model
		);

		addAllocationSourceOrDestination(sourceOrDestination);
	}

	public void addShipmentSchedules(final List<I_M_ShipmentSchedule> schedules)
	{
		if (schedules == null || schedules.isEmpty())
		{
			// nothing to add
			return;
		}

		for (final I_M_ShipmentSchedule schedule : schedules)
		{
			addShipmentSchedule(schedule);
		}

	}

	@Override
	public IAllocationResult unload(final IAllocationRequest request)
	{
		// Make sure that request has no referenced object because we will use the shipment schedules are referenced objects.
		Check.assumeNull(request.getReference(), "Request shall have no referenced object: {}", request);

		return super.unload(request);
	}
}
