package de.metas.handlingunits.sourcehu.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsBL.TopLevelHusQuery;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.sourcehu.ISourceHuService;
import de.metas.logging.LogManager;
import lombok.NonNull;

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

public class SourceHuService implements ISourceHuService
{

	private static final Logger logger = LogManager.getLogger(SourceHuService.class);

	@Override
	@Cached(cacheName = I_M_Source_HU.Table_Name + "#by#" + I_M_Source_HU.COLUMNNAME_M_HU_ID)
	public boolean isHuMarkedAsSourceHu(final int huId)
	{
		final boolean isSourceHU = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Source_HU.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Source_HU.COLUMNNAME_M_HU_ID, huId)
				.create()
				.match();
		return isSourceHU;
	}

	@Override
	public List<I_M_HU> retrieveParentHusThatAreMarkedAsSourceHUs(@NonNull final List<I_M_HU> vhus)
	{
		final ISourceHuService sourceHuService = Services.get(ISourceHuService.class); // get the interface because we want to benefit from caching

		final TreeSet<I_M_HU> sourceHUs = new TreeSet<>(Comparator.comparing(I_M_HU::getM_HU_ID));

		// this filter's real job is to collect those HUs that are flagged as "source"
		final Predicate<I_M_HU> filter = hu -> {
			if (sourceHuService.isHuMarkedAsSourceHu(hu.getM_HU_ID()))
			{
				sourceHUs.add(hu);
			}
			return true;
		};

		final TopLevelHusQuery topLevelHusRequest = TopLevelHusQuery.builder()
				.hus(vhus)
				.includeAll(false)
				.filter(filter)
				.build();

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsBL.getTopLevelHUs(topLevelHusRequest);

		return ImmutableList.copyOf(sourceHUs);
	}

	@Override
	public boolean isHuOrAnyParentMarkedAsSourceHu(final int huId)
	{
		final ISourceHuService sourceHuService = Services.get(ISourceHuService.class); // benefit from caching
		if (sourceHuService.isHuMarkedAsSourceHu(huId)) // check if there is a quick answer
		{
			return true;
		}

		final List<I_M_HU> topLevelHuThatHasNoSourceHuInItsPath = retrieveTopLevelHuIfNoSourceHuIsOnThePath(huId, sourceHuService);

		final boolean huIdHasAsSourceHuSomewhereAmongItsParents = topLevelHuThatHasNoSourceHuInItsPath.isEmpty();
		return huIdHasAsSourceHuSomewhereAmongItsParents;
	}

	private static List<I_M_HU> retrieveTopLevelHuIfNoSourceHuIsOnThePath(final int huId, final ISourceHuService sourceHuService)
	{
		final Predicate<I_M_HU> filterToExcludeSourceHus = //
				currentHu -> !sourceHuService.isHuMarkedAsSourceHu(currentHu.getM_HU_ID());

		final TopLevelHusQuery query = TopLevelHusQuery.builder()
				.includeAll(false)
				.filter(filterToExcludeSourceHus)
				.hus(ImmutableList.of(load(huId, I_M_HU.class)))
				.build();

		final List<I_M_HU> topLevelHuThatHasNoSourceHuInItsPath = Services.get(IHandlingUnitsBL.class).getTopLevelHUs(query);
		return topLevelHuThatHasNoSourceHuInItsPath;
	}

	@Override
	public List<I_M_HU> retrieveActiveHusthatAreMarkedAsSourceHu(@NonNull final ActiveSourceHusQuery query)
	{
		if (query.getProductIds().isEmpty())
		{
			return ImmutableList.of();
		}
		final ICompositeQueryFilter<I_M_HU> huFilters = createHuFilter(query);

		final IQueryBuilder<I_M_HU> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_Source_HU.class)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_M_Source_HU.COLUMN_M_HU_ID)
				.filter(huFilters);

		final List<I_M_HU> result = queryBuilder.create()
				.list();
		return result;
	}

	@Override
	public List<I_M_Source_HU> retrieveActiveSourceHUs(@NonNull final ActiveSourceHusQuery query)
	{
		if (query.getProductIds().isEmpty())
		{
			return ImmutableList.of();
		}
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_HU> huQuery = queryBL.createQueryBuilder(I_M_HU.class)
				.addOnlyActiveRecordsFilter()
				.filter(createHuFilter(query))
				.create();

		return queryBL.createQueryBuilder(I_M_Source_HU.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_Source_HU.COLUMN_M_HU_ID, I_M_HU.COLUMN_M_HU_ID, huQuery)
				.create()
				.list();
	}

	@VisibleForTesting
	static ICompositeQueryFilter<I_M_HU> createHuFilter(@NonNull final ActiveSourceHusQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final ICompositeQueryFilter<I_M_HU> huFilters = queryBL.createCompositeQueryFilter(I_M_HU.class)
				.setJoinOr();

		final IQueryFilter<I_M_HU> huFilter = handlingUnitsDAO.createHUQueryBuilder()
				.setOnlyActiveHUs(true)
				.setAllowEmptyStorage()
				.addOnlyWithProductIds(query.getProductIds())
				.addOnlyInWarehouseId(query.getWarehouseId())
				.createQueryFilter();
		huFilters.addFilter(huFilter);

		return huFilters;
	}

	@Override
	public I_M_Source_HU addSourceHu(final int huId)
	{
		final I_M_Source_HU sourceHU = newInstance(I_M_Source_HU.class);
		sourceHU.setM_HU_ID(huId);
		save(sourceHU);

		logger.info("Created one M_Source_HU record for M_HU_ID={}", huId);
		return sourceHU;
	}

	@Override
	public boolean removeSourceHu(final int huId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final int deleteCount = queryBL.createQueryBuilder(I_M_Source_HU.class)
				.addEqualsFilter(I_M_Source_HU.COLUMN_M_HU_ID, huId)
				.create()
				.delete();

		return deleteCount > 0;
	}

	@Override
	public void snapshotSourceHU(@NonNull final I_M_Source_HU sourceHU)
	{
		final IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);

		final String snapshotId = huSnapshotDAO.createSnapshot()
				.setContext(PlainContextAware.newWithThreadInheritedTrx())
				.addModel(sourceHU.getM_HU())
				.createSnapshots()
				.getSnapshotId();
		sourceHU.setPreDestroy_Snapshot_UUID(snapshotId);
		save(sourceHU);
	}

	@Override
	public I_M_Source_HU retrieveSourceHuOrNull(@NonNull final I_M_HU hu)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Source_HU.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Source_HU.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.create()
				.firstOnly(I_M_Source_HU.class);
	}

	@Override
	public void restoreHuFromSourceHuIfPossible(I_M_HU destroyedHU)
	{
		final I_M_Source_HU sourceHuRecord = retrieveSourceHuOrNull(destroyedHU);
		if (sourceHuRecord == null)
		{
			return;
		}

		Services.get(IHUSnapshotDAO.class).restoreHUs()
				.addModel(destroyedHU)
				.setContext(PlainContextAware.newWithThreadInheritedTrx())
				.setDateTrx(SystemTime.asDate())
				.setSnapshotId(sourceHuRecord.getPreDestroy_Snapshot_UUID())
				.restoreFromSnapshot();

		sourceHuRecord.setPreDestroy_Snapshot_UUID(null);
		save(sourceHuRecord);
	}
}
