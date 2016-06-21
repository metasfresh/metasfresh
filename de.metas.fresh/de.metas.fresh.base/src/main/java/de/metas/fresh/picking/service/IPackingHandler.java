package de.metas.fresh.picking.service;

import de.metas.adempiere.form.IPackingItem;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/*
 * #%L
 * de.metas.fresh.base
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
 * To be used with {@link IPackingService} methods to guide their job along the road.
 *
 * @author tsa
 *
 */
public interface IPackingHandler
{
	/**
	 * @param shipmentSchedule
	 * @return true if we are allowed to pack given <code>shipmentSchedule</code>
	 */
	boolean isPackingAllowedForShipmentSchedule(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Called when item was packed
	 *
	 * @param itemPacked item that was packed
	 */
	void itemPacked(final IPackingItem itemPacked);
}
