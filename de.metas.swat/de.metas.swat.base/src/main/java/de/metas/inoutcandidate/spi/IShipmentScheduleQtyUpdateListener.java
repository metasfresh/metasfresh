package de.metas.inoutcandidate.spi;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

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

/**
 * This interface will have Listener implementations in projects different than de.metas.swat, so the qtys of the shipment schedules can be modified based on new logics.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IShipmentScheduleQtyUpdateListener
{

	/**
	 * Implement this method in the subclasses of IShipmentScheduleQtyUpdateListener by creating an update logic from other projects.
	 * 
	 * @param schedule
	 */
	void updateQtys(final I_M_ShipmentSchedule schedule);
}
