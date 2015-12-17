package de.metas.inoutcandidate.spi.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentScheduleQtyUpdateListener;

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

public class CompositeShipmentScheduleQtyUpdateListener implements IShipmentScheduleQtyUpdateListener
{
	private final List<IShipmentScheduleQtyUpdateListener> listeners = new ArrayList<IShipmentScheduleQtyUpdateListener>();

	/**
	 * Add a new Shipment Schedule Qty Update listener
	 * 
	 * @param listener
	 */
	public void addShipmentScheduleQtyUpdateListener(final IShipmentScheduleQtyUpdateListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		if (listeners.contains(listener))
		{
			return;
		}

		listeners.add(listener);
	}

	@Override
	public void updateQtys(I_M_ShipmentSchedule schedule)
	{
		// trigger all the update methods from all the registered listeners
		for (final IShipmentScheduleQtyUpdateListener listener : listeners)
		{
			listener.updateQtys(schedule);
		}
	}
}
