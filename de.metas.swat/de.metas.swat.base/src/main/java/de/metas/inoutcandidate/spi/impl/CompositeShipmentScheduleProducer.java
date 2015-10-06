package de.metas.inoutcandidate.spi.impl;

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentScheduleProducer;

public class CompositeShipmentScheduleProducer implements IShipmentScheduleProducer
{
	private final List<IShipmentScheduleProducer> producers = new ArrayList<IShipmentScheduleProducer>();

	public CompositeShipmentScheduleProducer()
	{
		super();
	}

	public void addShipmentScheduleProducer(final IShipmentScheduleProducer producer)
	{
		Check.assumeNotNull(producer, "producer not null");
		producers.add(producer);
	}

	@Override
	public List<I_M_ShipmentSchedule> createOrUpdateShipmentSchedules(final Object model, final List<I_M_ShipmentSchedule> previousSchedules)
	{
		// 05295: ts: temporarily commenting out this check as a quick-fix
//		for (final I_M_ShipmentSchedule previousSched : previousSchedules)
//		{
//			// if we update an existing schedule, this must happen in one (single) background process, unless we have a way of making sure that concurrent updates of one schedule are not taking place.
//			Check.assume(Services.get(IShipmentScheduleBL.class).isChangedByUpdateProcess(previousSched), "");
//		}
		
		final List<I_M_ShipmentSchedule> currentPreviousSchedules = new ArrayList<I_M_ShipmentSchedule>(previousSchedules);
		final List<I_M_ShipmentSchedule> currentPreviousSchedulesRO = Collections.unmodifiableList(currentPreviousSchedules);
		final List<I_M_ShipmentSchedule> schedules = new ArrayList<I_M_ShipmentSchedule>();

		for (final IShipmentScheduleProducer producer : producers)
		{
			final List<I_M_ShipmentSchedule> currentSchedules = producer.createOrUpdateShipmentSchedules(model, currentPreviousSchedulesRO);

			if (currentSchedules != null)
			{
				currentPreviousSchedules.addAll(currentSchedules);
				schedules.addAll(currentSchedules);
			}
		}

		return schedules;
	}

	@Override
	public void inactivateShipmentSchedules(final Object model)
	{
		for (final IShipmentScheduleProducer producer : producers)
		{
			producer.inactivateShipmentSchedules(model);
		}
	}

}
