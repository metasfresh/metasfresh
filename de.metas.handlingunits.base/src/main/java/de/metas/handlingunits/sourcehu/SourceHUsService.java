package de.metas.handlingunits.sourcehu;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.PlainContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.Adempiere;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsBL.TopLevelHusQuery;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;
import lombok.Singular;

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

@Service
public class SourceHUsService
{
	private static final Logger logger = LogManager.getLogger(SourceHUsService.class);

	public static SourceHUsService get()
	{
		if (Adempiere.isUnitTestMode())
		{
			return new SourceHUsService();
		}
		return Adempiere.getBean(SourceHUsService.class);
	}

	public List<I_M_HU> retrieveParentHusThatAreSourceHUs(@NonNull final List<I_M_HU> vhus)
	{
		final ISourceHuDAO sourceHuDAO = Services.get(ISourceHuDAO.class);

		final TreeSet<I_M_HU> sourceHUs = new TreeSet<>(Comparator.comparing(I_M_HU::getM_HU_ID));

		// this filter's real job is to collect those HUs that are flagged as "source"
		// FIXME: avoid using filters in such a way...
		final Predicate<I_M_HU> filter = hu -> {
			if (sourceHuDAO.isSourceHu(HuId.ofRepoId(hu.getM_HU_ID())))
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

	public boolean isHuOrAnyParentSourceHu(final HuId huId)
	{
		if (isSourceHu(huId)) // check if there is a quick answer
		{
			return true;
		}

		final List<I_M_HU> topLevelHuThatHasNoSourceHuInItsPath = retrieveTopLevelHuIfNoSourceHuIsOnThePath(huId);
		final boolean huIdHasAsSourceHuSomewhereAmongItsParents = topLevelHuThatHasNoSourceHuInItsPath.isEmpty();
		return huIdHasAsSourceHuSomewhereAmongItsParents;
	}

	private List<I_M_HU> retrieveTopLevelHuIfNoSourceHuIsOnThePath(final HuId huId)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final Predicate<I_M_HU> filterToExcludeSourceHus = currentHu -> !isSourceHu(HuId.ofRepoId(currentHu.getM_HU_ID()));

		final TopLevelHusQuery query = TopLevelHusQuery.builder()
				.includeAll(false)
				.filter(filterToExcludeSourceHus)
				.hus(ImmutableList.of(handlingUnitsDAO.getById(huId)))
				.build();

		final List<I_M_HU> topLevelHuThatHasNoSourceHuInItsPath = Services.get(IHandlingUnitsBL.class).getTopLevelHUs(query);
		return topLevelHuThatHasNoSourceHuInItsPath;
	}

	public I_M_Source_HU addSourceHuMarker(@NonNull final HuId huId)
	{
		final I_M_Source_HU sourceHU = newInstance(I_M_Source_HU.class);
		sourceHU.setM_HU_ID(huId.getRepoId());
		save(sourceHU);

		logger.info("Created one M_Source_HU record for M_HU_ID={}", huId);
		return sourceHU;
	}

	public boolean deleteSourceHuMarker(@NonNull final HuId huId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final int deleteCount = queryBL
				.createQueryBuilder(I_M_Source_HU.class)
				.addEqualsFilter(I_M_Source_HU.COLUMN_M_HU_ID, huId)
				.create()
				.delete();

		return deleteCount > 0;
	}

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

	public void restoreHuFromSourceHuMarkerIfPossible(I_M_HU destroyedHU)
	{
		final ISourceHuDAO sourceHuDAO = Services.get(ISourceHuDAO.class);
		final I_M_Source_HU sourceHuRecord = sourceHuDAO.retrieveSourceHuMarkerOrNull(destroyedHU);
		if (sourceHuRecord == null)
		{
			return;
		}

		Services.get(IHUSnapshotDAO.class).restoreHUs()
				.addModelId(destroyedHU.getM_HU_ID())
				.setContext(PlainContextAware.newWithThreadInheritedTrx())
				.setDateTrx(SystemTime.asDate())
				.setSnapshotId(sourceHuRecord.getPreDestroy_Snapshot_UUID())
				.restoreFromSnapshot();

		sourceHuRecord.setPreDestroy_Snapshot_UUID(null);
		save(sourceHuRecord);
	}

	public void snapshotHuIfMarkedAsSourceHu(@NonNull final I_M_HU hu)
	{
		final I_M_Source_HU sourceHuMarker = Services.get(ISourceHuDAO.class).retrieveSourceHuMarkerOrNull(hu);
		if (sourceHuMarker != null)
		{
			snapshotSourceHU(sourceHuMarker);
		}
	}

	public List<I_M_Source_HU> retrieveMatchingSourceHuMarkers(@NonNull final MatchingSourceHusQuery query)
	{
		return Services.get(ISourceHuDAO.class).retrieveActiveSourceHuMarkers(query);
	}

	public Set<HuId> retrieveMatchingSourceHUIds(@NonNull final MatchingSourceHusQuery query)
	{
		return Services.get(ISourceHuDAO.class).retrieveActiveSourceHUIds(query);
	}

	public boolean isSourceHu(final HuId huId)
	{
		return Services.get(ISourceHuDAO.class).isSourceHu(huId);
	}

	/**
	 * Specifies which source HUs (products and warehouse) to retrieve in particular
	 */
	@lombok.Value
	@lombok.Builder
	@Immutable
	public static class MatchingSourceHusQuery
	{
		/**
		 * Query for HUs that have any of the given product IDs. Empty means that no HUs will be found.
		 */
		@Singular
		ImmutableSet<ProductId> productIds;

		WarehouseId warehouseId;

		public static MatchingSourceHusQuery fromHuId(final HuId huId)
		{
			final I_M_HU hu = Services.get(IHandlingUnitsDAO.class).getById(huId);
			final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
			final IHUStorage storage = storageFactory.getStorage(hu);

			final ImmutableSet<ProductId> productIds = storage.getProductStorages().stream()
					.filter(productStorage -> !productStorage.isEmpty())
					.map(IProductStorage::getProductId)
					.collect(ImmutableSet.toImmutableSet());

			final WarehouseId warehouseId = WarehouseId.ofRepoId(hu.getM_Locator().getM_Warehouse_ID());
			return new MatchingSourceHusQuery(productIds, warehouseId);
		}
	}
}
