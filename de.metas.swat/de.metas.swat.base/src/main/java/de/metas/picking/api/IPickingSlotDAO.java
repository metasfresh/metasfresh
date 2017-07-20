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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.picking.model.I_M_PickingSlot;

public interface IPickingSlotDAO extends ISingletonService
{
	/**
	 * Retrieve all picking slots for current tenant/client.
	 * 
	 * @param ctx
	 * @param trxName
	 * @return list of picking slots
	 */
	List<I_M_PickingSlot> retrievePickingSlots(Properties ctx, String trxName);

	List<I_M_PickingSlot> retrivePickingSlotsForBPartners(final Properties ctx, final Collection<Integer> bpartnerIds, final Collection<Integer> bpLocIds);

	/**
	 * Retrieve all {@link I_M_PickingSlot}s and filter them using {@link IPickingSlotBL#isAvailableForBPartnerAndLocation(I_M_PickingSlot, int, int)} using the given parameters.
	 * 
	 * @param ctx
	 * @param bpartnerId
	 * @param bpartnerLocationId
	 * @return
	 */
	List<I_M_PickingSlot> retrivePickingSlotsForBPartner(Properties ctx, int bpartnerId, int bpartnerLocationId);

	List<I_M_PickingSlot> retrievePickingSlotsByIds(Collection<Integer> pickingSlotIds);
}
