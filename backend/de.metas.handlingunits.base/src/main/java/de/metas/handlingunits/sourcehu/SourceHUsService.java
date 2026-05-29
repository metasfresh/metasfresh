package de.metas.handlingunits.sourcehu;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsBL.TopLevelHusQuery;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.product.Product;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.PlainContextAware;
import org.adempiere.warehouse.Warehouse;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseRepository;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.eevolution.api.IProductBOMDAO;
import org.springframework.stereotype.Service;

import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.save;

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
@RequiredArgsConstructor
public class SourceHUsService
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final ISourceHuDAO sourceHuDAO = Services.get(ISourceHuDAO.class);
	@NonNull private final IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);

	@NonNull private final WarehouseRepository warehouseRepository;
	@NonNull private final ProductRepository productRepository;

	public static SourceHUsService get()
	{
		if (Adempiere.isUnitTestMode())
		{
			return new SourceHUsService(
					WarehouseRepository.newInstanceForUnitTesting(),
					ProductRepository.newInstanceForUnitTesting()
			);
		}
		else
		{
			return SpringContextHolder.instance.getBean(SourceHUsService.class);
		}
	}

	public List<I_M_HU> retrieveParentHusThatAreSourceHUs(@NonNull final List<I_M_HU> vhus)
	{
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
		//noinspection UnnecessaryLocalVariable
		final boolean huIdHasAsSourceHuSomewhereAmongItsParents = topLevelHuThatHasNoSourceHuInItsPath.isEmpty();
		return huIdHasAsSourceHuSomewhereAmongItsParents;
	}

	private List<I_M_HU> retrieveTopLevelHuIfNoSourceHuIsOnThePath(final HuId huId)
	{
		final Predicate<I_M_HU> filterToExcludeSourceHus = currentHu -> !isSourceHu(HuId.ofRepoId(currentHu.getM_HU_ID()));

		final TopLevelHusQuery query = TopLevelHusQuery.builder()
				.includeAll(false)
				.filter(filterToExcludeSourceHus)
				.hus(ImmutableList.of(handlingUnitsBL.getById(huId)))
				.build();

		//noinspection UnnecessaryLocalVariable
		final List<I_M_HU> topLevelHuThatHasNoSourceHuInItsPath = handlingUnitsBL.getTopLevelHUs(query);
		return topLevelHuThatHasNoSourceHuInItsPath;
	}

	public boolean isEligibleFroSourceHuMarker(@NonNull final I_M_HU hu, @NonNull final Warehouse warehouse)
	{
		if(!warehouse.isReceiveAsSourceHU())
		{
			return false;
		}
		final Set<ProductId> productIds = handlingUnitsBL.getStorageFactory().getStorage(hu).getProductStorages().stream()
				.map(IProductStorage::getProductId)
				.collect(Collectors.toSet());
		return productRepository.getByIds(productIds).stream()
				.map(Product::getProductCategoryId)
				.anyMatch(warehouse::isConfiguredToReceiveAsSourceHU);
	}

	public void addSourceHuMarker(@NonNull final HuId huId)
	{
		sourceHuDAO.addSourceHuMarker(huId);
	}

	public boolean deleteSourceHuMarker(@NonNull final HuId huId)
	{
		final int deleteCount = queryBL
				.createQueryBuilder(I_M_Source_HU.class)
				.addEqualsFilter(I_M_Source_HU.COLUMN_M_HU_ID, huId)
				.create()
				.delete();

		return deleteCount > 0;
	}

	public void snapshotSourceHU(@NonNull final I_M_Source_HU sourceHU)
	{
		final String snapshotId = huSnapshotDAO.createSnapshot()
				.setContext(PlainContextAware.newWithThreadInheritedTrx())
				.addModel(sourceHU.getM_HU())
				.createSnapshots()
				.getSnapshotId();
		sourceHU.setPreDestroy_Snapshot_UUID(snapshotId);
		save(sourceHU);
	}

	public void restoreHuFromSourceHuMarkerIfPossible(@NonNull final I_M_HU destroyedHU)
	{
		final I_M_Source_HU sourceHuRecord = sourceHuDAO.retrieveSourceHuMarkerOrNull(destroyedHU);
		if (sourceHuRecord == null)
		{
			return;
		}

		huSnapshotDAO.restoreHUs()
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
		final I_M_Source_HU sourceHuMarker = sourceHuDAO.retrieveSourceHuMarkerOrNull(hu);
		if (sourceHuMarker != null)
		{
			snapshotSourceHU(sourceHuMarker);
		}
	}

	public List<I_M_Source_HU> retrieveMatchingSourceHuMarkers(@NonNull final MatchingSourceHusQuery query)
	{
		return sourceHuDAO.retrieveActiveSourceHuMarkers(query);
	}

	@NonNull
	public ImmutableList<I_M_Source_HU> retrieveSourceHuMarkers(@NonNull final Collection<HuId> huIds)
	{
		return sourceHuDAO.retrieveSourceHuMarkers(huIds);
	}

	public Set<HuId> retrieveMatchingSourceHUIds(@NonNull final MatchingSourceHusQuery query)
	{
		return sourceHuDAO.retrieveActiveSourceHUIds(query);
	}

	public boolean isSourceHu(final HuId huId)
	{
		return sourceHuDAO.isSourceHu(huId);
	}

	public void addSourceHUMarkerIfCarryingComponents(@NonNull final HuId huId, @NonNull final ProductId productId, @NonNull final WarehouseId warehouseId)
	{
		addSourceHUMarkerIfCarryingComponents(ImmutableSet.of(huId), productId, warehouseId);
	}

	/**
	 * Creates an M_Source_HU record for the given HU, if it carries component products and the target warehouse has
	 * the org.compiere.model.I_M_Warehouse#isReceiveAsSourceHU() flag.
	 */
	public void addSourceHUMarkerIfCarryingComponents(@NonNull final Set<HuId> huIds, @NonNull final ProductId productId, @NonNull final WarehouseId warehouseId)
	{
		if (huIds.isEmpty()) {return;}

		final Warehouse warehouse = warehouseRepository.getById(warehouseId);
		if (!warehouse.isReceiveAsSourceHU())
		{
			return;
		}

		final ProductCategoryId productCategoryId = productRepository.getById(productId).getProductCategoryId();
		if (!warehouse.isConfiguredToReceiveAsSourceHU(productCategoryId))
		{
			return;
		}

		final boolean referencedInComponentOrVariant = productBOMDAO.isComponent(productId);
		if (!referencedInComponentOrVariant)
		{
			return;
		}

		huIds.forEach(sourceHuDAO::addSourceHuMarker);
	}

	/**
	 * Specifies which source HUs (products and warehouse) to retrieve in particular
	 */
	@Value
	@Builder
	@Immutable
	public static class MatchingSourceHusQuery
	{
		/**
		 * Query for HUs that have any of the given product IDs. Empty means that no HUs will be found.
		 */
		@Singular
		ImmutableSet<ProductId> productIds;

		@Singular
		ImmutableSet<WarehouseId> warehouseIds;

		public static MatchingSourceHusQuery fromHuId(final HuId huId)
		{
			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

			final I_M_HU hu = handlingUnitsBL.getById(huId);
			final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
			final IHUStorage storage = storageFactory.getStorage(hu);

			final ImmutableSet<ProductId> productIds = storage.getProductStorages().stream()
					.filter(productStorage -> !productStorage.isEmpty())
					.map(IProductStorage::getProductId)
					.collect(ImmutableSet.toImmutableSet());

			final WarehouseId warehouseId = IHandlingUnitsBL.extractWarehouseId(hu);
			return new MatchingSourceHusQuery(productIds, ImmutableSet.of(warehouseId));
		}
	}
}
