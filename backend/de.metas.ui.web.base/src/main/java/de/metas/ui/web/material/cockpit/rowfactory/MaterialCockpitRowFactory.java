/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.material.cockpit.rowfactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableSet;
import de.metas.dimension.DimensionSpec;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.material.cockpit.ProductWithDemandSupply;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceService;
import de.metas.ui.web.material.cockpit.MaterialCockpitDetailsRowAggregation;
import de.metas.ui.web.material.cockpit.MaterialCockpitDetailsRowAggregationIdentifier;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitRowCache;
import de.metas.ui.web.material.cockpit.MaterialCockpitRowLookups;
import de.metas.ui.web.material.cockpit.MaterialCockpitUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseRepository;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MaterialCockpitRowFactory
{
	@NonNull private final MaterialCockpitRowLookups rowLookups;
	@NonNull private final WarehouseRepository warehouseRepository;
	@NonNull private final ResourceService resourceService;
	@NonNull private final ProductRepository productRepository;

	@VisibleForTesting
	public static MaterialCockpitRowFactory newInstanceForUnitTesting(final MaterialCockpitRowLookups rowLookups)
	{
		Adempiere.assertUnitTestMode();
		return new MaterialCockpitRowFactory(
				rowLookups,
				WarehouseRepository.newInstanceForUnitTesting(),
				ResourceService.newInstanceForJUnitTesting(),
				ProductRepository.newInstanceForUnitTesting()
		);
	}

	@Value
	@lombok.Builder
	public static class CreateRowsRequest
	{
		@NonNull LocalDate date;
		@NonNull @Singular("productIdToListEvenIfEmpty") ImmutableSet<ProductId> productIdsToListEvenIfEmpty;
		@NonNull @Singular List<I_MD_Cockpit> cockpitRecords;
		@NonNull @Singular List<I_MD_Stock> stockRecords;
		@NonNull @Singular List<ProductWithDemandSupply> quantitiesRecords;
		@NonNull MaterialCockpitDetailsRowAggregation detailsRowAggregation;
	}

	public List<MaterialCockpitRow> createRows(@NonNull final CreateRowsRequest request)
	{
		return newCreateRowsCommand(request).execute();
	}

	@VisibleForTesting
	CreateRowsCommand newCreateRowsCommand(final @NonNull CreateRowsRequest request)
	{
		return CreateRowsCommand.builder()
				.rowLookups(rowLookups)
				.warehouseRepository(warehouseRepository)
				.resourceService(resourceService)
				.productRepository(productRepository)
				//
				.request(request)
				//
				.build();
	}

	@VisibleForTesting
	@lombok.Builder
	static class CreateRowsCommand
	{
		@NonNull private final MaterialCockpitRowLookups rowLookups;
		@NonNull private final WarehouseRepository warehouseRepository;
		@NonNull private final ProductRepository productRepository;
		@NonNull private final ResourceService resourceService;

		@NonNull private final CreateRowsRequest request;
		
		@NonNull private final MaterialCockpitRowCache cache = new MaterialCockpitRowCache();

		public List<MaterialCockpitRow> execute()
		{
			cache.warmUpProducts(request.getProductIdsToListEvenIfEmpty());
			
			final Map<MainRowBucketId, MainRowWithSubRows> emptyRowBuckets = createEmptyRowBuckets(
					request.getProductIdsToListEvenIfEmpty(),
					request.getDate(),
					request.getDetailsRowAggregation());

			final DimensionSpec dimensionSpec = MaterialCockpitUtil.retrieveDimensionSpec();

			final Map<MainRowBucketId, MainRowWithSubRows> result = new HashMap<>(emptyRowBuckets);

			addCockpitRowsToResult(dimensionSpec, result);
			addStockRowsToResult(dimensionSpec, result);
			addQuantitiesRowsToResult(dimensionSpec, result);

			return result.values()
					.stream()
					.map(MainRowWithSubRows::createMainRowWithSubRows)
					.collect(ImmutableList.toImmutableList());
		}

		@VisibleForTesting
		protected Map<MainRowBucketId, MainRowWithSubRows> createEmptyRowBuckets(
				@NonNull final ImmutableSet<ProductId> productIds,
				@NonNull final LocalDate timestamp,
				@NonNull final MaterialCockpitDetailsRowAggregation detailsRowAggregation)
		{
			final DimensionSpec dimensionSpec = MaterialCockpitUtil.retrieveDimensionSpec();

			final List<DimensionSpecGroup> groups = dimensionSpec.retrieveGroups();

			final Set<ResourceId> plantIds = retrieveCountingPlants();

			final ImmutableSet<WarehouseId> warehouseIds = retrieveWarehouses();

			final Builder<MainRowBucketId, MainRowWithSubRows> result = ImmutableMap.builder();
			for (final ProductId productId : productIds)
			{
				final MainRowBucketId key = MainRowBucketId.createPlainInstance(productId, timestamp);
				final MainRowWithSubRows mainRowBucket = newMainRowWithSubRows(key);

				if (detailsRowAggregation.isPlant())
				{
					for (final ResourceId plantId : plantIds)
					{
						final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder()
								.detailsRowAggregation(detailsRowAggregation)
								.aggregationId(plantId.getRepoId())
								.build();
						mainRowBucket.addEmptyCountingSubRowBucket(detailsRowAggregationIdentifier);
					}
				}
				else if (detailsRowAggregation.isWarehouse())
				{
					for (final WarehouseId warehouseId : warehouseIds)
					{
						final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder()
								.detailsRowAggregation(detailsRowAggregation)
								.aggregationId(WarehouseId.toRepoId(warehouseId))
								.build();
						mainRowBucket.addEmptyCountingSubRowBucket(detailsRowAggregationIdentifier);
					}
				}

				for (final DimensionSpecGroup group : groups)
				{
					mainRowBucket.addEmptyAttributesSubrowBucket(group);
				}
				result.put(key, mainRowBucket);

			}
			return result.build();
		}

		private Set<ResourceId> retrieveCountingPlants()
		{
			return resourceService.getActivePlantIds();
		}

		private ImmutableSet<WarehouseId> retrieveWarehouses()
		{
			return warehouseRepository.retrieveAllActiveIds();
		}

		private void addCockpitRowsToResult(
				@NonNull final DimensionSpec dimensionSpec,
				@NonNull final Map<MainRowBucketId, MainRowWithSubRows> result)
		{
			for (final I_MD_Cockpit cockpitRecord : request.getCockpitRecords())
			{
				final MainRowBucketId mainRowBucketId = MainRowBucketId.createInstanceForCockpitRecord(cockpitRecord);

				final MainRowWithSubRows mainRowBucket = result.computeIfAbsent(mainRowBucketId, this::newMainRowWithSubRows);
				mainRowBucket.addCockpitRecord(cockpitRecord, dimensionSpec, request.getDetailsRowAggregation());
			}
		}

		private void addStockRowsToResult(
				@NonNull final DimensionSpec dimensionSpec,
				@NonNull final Map<MainRowBucketId, MainRowWithSubRows> result)
		{
			for (final I_MD_Stock stockRecord : request.getStockRecords())
			{
				final MainRowBucketId mainRowBucketId = MainRowBucketId.createInstanceForStockRecord(stockRecord, request.getDate());

				final MainRowWithSubRows mainRowBucket = result.computeIfAbsent(mainRowBucketId, this::newMainRowWithSubRows);
				mainRowBucket.addStockRecord(stockRecord, dimensionSpec, request.getDetailsRowAggregation());
			}
		}

		private void addQuantitiesRowsToResult(
				@NonNull final DimensionSpec dimensionSpec,
				@NonNull final Map<MainRowBucketId, MainRowWithSubRows> result)
		{
			for (final ProductWithDemandSupply qtyRecord : request.getQuantitiesRecords())
			{
				final MainRowBucketId mainRowBucketId = MainRowBucketId.createInstanceForQuantitiesRecord(qtyRecord, request.getDate());

				final MainRowWithSubRows mainRowBucket = result.computeIfAbsent(mainRowBucketId, this::newMainRowWithSubRows);
				mainRowBucket.addQuantitiesRecord(qtyRecord, dimensionSpec, request.getDetailsRowAggregation());
			}
		}

		private MainRowWithSubRows newMainRowWithSubRows(@NonNull final MainRowBucketId mainRowBucketId)
		{
			return MainRowWithSubRows.builder()
					.warehouseRepository(warehouseRepository)
					.productRepository(productRepository)
					.rowLookups(rowLookups)
					.productIdAndDate(mainRowBucketId)
					.build();
		}
	}
}
