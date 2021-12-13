package de.metas.handlingunits.picking.impl.HUPickingSlotBLs;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.lang.IMutable;
import org.compiere.Adempiere;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsBL.TopLevelHusQuery;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.impl.HUPickingSlotBL;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.SpringContextHolder;

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
	/**
	 * Excludes HU that are already picked or already selected as fine picking source HUs.
	 */
	public List<I_M_HU> retrieveFullTreeAndExcludePickingHUs(@NonNull final List<I_M_HU> vhus)
	{
		final List<I_M_HU> husTopLevel = retrieveTopLevelUs(vhus);

		return filterForValidPaths(husTopLevel);
	}

	/**
	 * Gets the the top level HUs from for our VHUs.
	 */
	private List<I_M_HU> retrieveTopLevelUs(@NonNull final List<I_M_HU> vhus)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final TopLevelHusQuery topLevelHusRequest = TopLevelHusQuery.builder()
				.hus(vhus)
				.includeAll(false)
				.filter(RetrieveAvailableHUsToPickFilters::isNotPickedAndNotSourceHU) // exclude HUs that are already picked or flagged as source HUs
				.build();
		return handlingUnitsBL.getTopLevelHUs(topLevelHusRequest);
	}

	private static boolean isNotPickedAndNotSourceHU(final I_M_HU hu)
	{
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final PickingCandidateRepository pickingCandidatesRepo = SpringContextHolder.instance.getBean(PickingCandidateRepository.class);
		final SourceHUsService sourceHuService = SourceHUsService.get();

		return !pickingCandidatesRepo.isHuIdPicked(huId) && !sourceHuService.isSourceHu(huId);
	}

	/**
	 * We still need to iterate the HUs trees from the top level HUs.
	 * Even if we had called handlingUnitsBL.getTopLevelHUs with includeAll(true),
	 * There might be a VHU with a picked TU. Because the TU is picked, also its un-picked VHU may not be in the result we return
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
						public Result beforeHU(@NonNull final IMutable<I_M_HU> hu)
						{
							if (!isNotPickedAndNotSourceHU(hu.getValue()))
							{
								return Result.SKIP_DOWNSTREAM;
							}
							else
							{
								result.add(hu.getValue());
								return Result.CONTINUE;
							}
						}
					})
					.iterate(huTopLevel);
		}
		return result;
	}
}
