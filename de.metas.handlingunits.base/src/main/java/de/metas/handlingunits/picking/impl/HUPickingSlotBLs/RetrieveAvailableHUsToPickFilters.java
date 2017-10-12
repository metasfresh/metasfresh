package de.metas.handlingunits.picking.impl.HUPickingSlotBLs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsBL.TopLevelHusQuery;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import de.metas.handlingunits.picking.impl.HUPickingSlotBL;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Contains methods used to support
 * {@link HUPickingSlotBL#retrieveAvailableHUsToPick(de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery)} and
 * {@link HUPickingSlotBL#retrieveAvailableSourceHUs(de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery)}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class RetrieveAvailableHUsToPickFilters
{

	private static final Predicate<I_M_HU> NOT_PICKED_NOT_SOURCE_HU = hu -> {

		final IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);
		final SourceHUsService sourceHuService = SourceHUsService.get();

		return !huPickingSlotDAO.isHuIdPicked(hu.getM_HU_ID()) && !sourceHuService.isSourceHu(hu.getM_HU_ID());
	};

	/**
	 * Excludes HU that are already picked or already selected as fine picking source HUs.
	 * 
	 * @param vhus
	 * @return
	 */
	public List<I_M_HU> retrieveFullTreeAndExcludePickingHUs(@NonNull final List<I_M_HU> vhus)
	{
		final List<I_M_HU> husTopLevel = retrieveTopLevelUs(vhus);
		final List<I_M_HU> result = filterForValidPaths(husTopLevel);

		return result;
	}

	/**
	 * Gets the the top level HUs from for our VHUs.
	 * 
	 * @param vhus
	 * @return
	 */
	private List<I_M_HU> retrieveTopLevelUs(@NonNull final List<I_M_HU> vhus)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final TopLevelHusQuery topLevelHusRequest = TopLevelHusQuery.builder()
				.hus(vhus)
				.includeAll(false)
				.filter(NOT_PICKED_NOT_SOURCE_HU) // exclude HUs that are already picked or flagged as source HUs
				.build();
		final List<I_M_HU> husTopLevel = handlingUnitsBL.getTopLevelHUs(topLevelHusRequest);
		return husTopLevel;
	}

	/**
	 * We still need to iterate the HUs trees from the top level HUs.
	 * Even if we had called handlingUnitsBL.getTopLevelHUs with includeAll(true),
	 * There might be a VHU with a picked TU. Because the TU is picked, also its un-picked VHU may not be in the result we return
	 * 
	 * @param husTopLevel
	 * @return
	 */
	private List<I_M_HU> filterForValidPaths(@NonNull final List<I_M_HU> husTopLevel)
	{
		final List<I_M_HU> result = new ArrayList<>();
		for (final I_M_HU huTopLevel : husTopLevel)
		{
			new HUIterator()
					.setEnableStorageIteration(false)
					.setListener(new HUIteratorListenerAdapter()
					{
						@Override
						public Result beforeHU(IMutable<I_M_HU> hu)
						{
							if (!NOT_PICKED_NOT_SOURCE_HU.test(hu.getValue()))
							{
								return Result.SKIP_DOWNSTREAM;
							}
							result.add(hu.getValue());
							return Result.CONTINUE;
						}
					})
					.iterate(huTopLevel);
		}
		return result;
	}
}
