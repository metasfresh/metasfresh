package de.metas.handlingunits.picking;

import java.util.Collection;

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

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;

import com.google.common.collect.SetMultimap;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;

public interface IHUPickingSlotDAO extends ISingletonService
{
	I_M_PickingSlot_HU retrievePickingSlotHU(de.metas.picking.model.I_M_PickingSlot pickingSlot, I_M_HU hu);

	I_M_PickingSlot_HU retrievePickingSlotHU(I_M_HU hu);

	/**
	 * Retrieve {@link I_M_PickingSlot} where given HU is in queue or is the current one.
	 *
	 * @param hu
	 * @return picking slot or null
	 */
	I_M_PickingSlot retrievePickingSlotForHU(I_M_HU hu);

	/***
	 * Retrieves the picking slot which currently references the given <code>hu</code> or <code>null</code> if there is no such slot.
	 *
	 * @param hu
	 * @return
	 */
	I_M_PickingSlot retrievePickingSlotForCurrentHU(I_M_HU hu);

	/**
	 * Retrieves all HUs that are assigned to the given <code>pickingSlot</code> via {@link I_M_PickingSlot_HU} (i.e. which are in the queue),<br>
	 * and also the one which is currently open within the picking slot.
	 *
	 * @param pickingSlot
	 */
	List<I_M_HU> retrieveAllHUs(de.metas.picking.model.I_M_PickingSlot pickingSlot);

	/** @see {@link #retrieveAllHUs(de.metas.picking.model.I_M_PickingSlot)} */
	Set<Integer> retrieveAllHUIds(final int pickingSlotId);

	/**
	 * @param pickingSlot
	 * @return true if there are no {@link I_M_PickingSlot_HU} assignments
	 */
	boolean isEmpty(de.metas.picking.model.I_M_PickingSlot pickingSlot);

	/**
	 * Retrieves those {@link I_M_PickingSlot_HU} records (ordered by <code>M_PickingSlot_HU_ID</code>) whose HUs are assigned to the given C_BPartner.<br>
	 * This is a partial workaround for task 06974, 2nd problem.
	 *
	 * @param bPartner
	 * @return
	 */
	List<I_M_PickingSlot_HU> retrievePickingSlotHUsForBPartner(I_C_BPartner bPartner);

	/**
	 * Picking slots matching partner and locator
	 *
	 * @param partner
	 * @param locator
	 * @return picking slots
	 */
	List<I_M_PickingSlot> retrievePickingSlots(I_C_BPartner partner, I_M_Locator locator);

	SetMultimap<Integer, Integer> retrieveAllHUIdsIndexedByPickingSlotId(Collection<? extends de.metas.picking.model.I_M_PickingSlot> pickingSlots);

	/**
	 * Creates an {@link I_M_HU} query filter which will select only those HUs which are currently on a picking slot or are in a picking slot queue.
	 *
	 * @param contextProvider
	 * @return
	 */
	IQueryFilter<I_M_HU> createHUOnPickingSlotQueryFilter(final Object contextProvider);

	/**
	 * Return {@code true} if the given {@code M_HU_ID} is referenced by an active {@link I_M_Picking_Candidate}.<br>
	 * Note that we use the ID for performance reasons.
	 * 
	 * @param huId
	 * @return
	 */
	boolean isHuIdPicked(int huId);
	
	boolean isPickingRackSystem(final int pickingSlotId);

	Set<Integer> retrieveAllPickingSlotIdsWhichAreRackSystems();
}
