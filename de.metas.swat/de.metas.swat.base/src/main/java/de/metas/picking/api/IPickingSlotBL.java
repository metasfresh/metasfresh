package de.metas.picking.api;

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


import org.adempiere.util.ISingletonService;

import de.metas.picking.model.I_M_PickingSlot;

public interface IPickingSlotBL extends ISingletonService
{
	/**
	 * Returns <code>true</code> if the given picking slot has no partner assigned.
	 * 
	 * @param pickingSlot
	 * @return
	 */
	boolean isAvailableForAnyBPartner(I_M_PickingSlot pickingSlot);

	boolean isAvailableForBPartnerID(I_M_PickingSlot pickingSlot, int bpartnerId);

	boolean isAvailableForBPartnerAndLocation(I_M_PickingSlot pickingSlot, int bpartnerId, int bpartnerLocationId);
}
