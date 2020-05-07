package de.metas.handlingunits.movement.api.impl;

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

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;

import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector;

/**
 * Collects HUs for a particular locator from/to and creates {@link HUPackingMaterialDocumentLineCandidate}s.
 *
 * It is used by {@link HUPackingMaterialMovementLineAggregateBuilder}.
 */
/* package */class HUPackingMaterialMovementLineBuilder
{
	// task 07734: we don't want to track M_MaterialTrackings, so we don't need to provide a HU context.
	private final IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> packingMaterialsCollector = new HUPackingMaterialsCollector(null);
	private final int locatorFromId;
	private final int locatorToId;

	public HUPackingMaterialMovementLineBuilder(final int locatorFromId, final int locatorToId)
	{
		super();
		this.locatorFromId = locatorFromId;
		this.locatorToId = locatorToId;

		packingMaterialsCollector.setisCollectTUNumberPerOrigin(true);
		packingMaterialsCollector.setisCollectAggregatedHUs(true);
	}

	public int getM_Locator_From_ID()
	{
		return locatorFromId;
	}

	public int getM_Locator_To_ID()
	{
		return locatorToId;
	}

	public HUPackingMaterialMovementLineBuilder setProductIdSortComparator(final Comparator<Integer> productIdsSortComparator)
	{
		packingMaterialsCollector.setProductIdSortComparator(productIdsSortComparator);
		return this;
	}

	public void addHURecursively(final IQueryBuilder<I_M_HU_Assignment> huAssignments)
	{

		packingMaterialsCollector.addHURecursively(huAssignments);
	}

	public List<HUPackingMaterialDocumentLineCandidate> getAndClearCandidates()
	{
		return packingMaterialsCollector.getAndClearCandidates();
	}

	/**
	 * Sets a shared "seen list" for added HUs.
	 *
	 * To be used in case you have multiple {@link HUPackingMaterialsCollector}s and you want if an HU is added to one of them then don't add it to the others.
	 *
	 * @param seenM_HU_IDs_ToAdd
	 */
	public void setSeenM_HU_IDs_ToAdd(final Set<Integer> seenM_HU_IDs_ToAdd)
	{
		packingMaterialsCollector.setSeenM_HU_IDs_ToAdd(seenM_HU_IDs_ToAdd);
	}
}
