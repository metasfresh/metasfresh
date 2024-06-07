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
import de.metas.resource.ManufacturingResourceType;
import de.metas.ui.web.material.cockpit.MaterialCockpitDetailsRowAggregation;
import de.metas.ui.web.material.cockpit.MaterialCockpitDetailsRowAggregationIdentifier;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitRowLookups;
import de.metas.ui.web.material.cockpit.MaterialCockpitUtil;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * #%L
 * metasfresh-webui-api
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
public class MaterialCockpitRowFactory
{
	@NonNull private final MaterialCockpitRowLookups rowLookups;

	@Value
	@lombok.Builder
	public static class CreateRowsRequest
	{
		@NonNull
		LocalDate date;

		@NonNull
		@Singular("productIdToListEvenIfEmpty")
		ImmutableSet<ProductId> productIdsToListEvenIfEmpty;

		@NonNull
		@Singular
		List<I_MD_Cockpit> cockpitRecords;

		@NonNull
		@Singular
		List<I_MD_Stock> stockRecords;

		@NonNull
		@Singular
		List<ProductWithDemandSupply> quantitiesRecords;

		@NonNull
		MaterialCockpitDetailsRowAggregation detailsRowAggregation;
	}

	public List<MaterialCockpitRow> createRows(@NonNull final CreateRowsRequest request)
	{
		final Map<MainRowBucketId, MainRowWithSubRows> emptyRowBuckets = createEmptyRowBuckets(
				request.getProductIdsToListEvenIfEmpty(),
				request.getDate(),
				request.getDetailsRowAggregation());

		final DimensionSpec dimensionSpec = MaterialCockpitUtil.retrieveDimensionSpec();

		final Map<MainRowBucketId, MainRowWithSubRows> result = new HashMap<>(emptyRowBuckets);

		addCockpitRowsToResult(request, dimensionSpec, result);
		addStockRowsToResult(request, dimensionSpec, result);
		addQuantitiesRowsToResult(request, dimensionSpec, result);

		return result.values()
				.stream()
				.map(MainRowWithSubRows::createMainRowWithSubRows)
				.collect(ImmutableList.toImmutableList());
	}

	@VisibleForTesting
	Map<MainRowBucketId, MainRowWithSubRows> createEmptyRowBuckets(
			@NonNull final ImmutableSet<ProductId> productIds,
			@NonNull final LocalDate timestamp,
			@NonNull final MaterialCockpitDetailsRowAggregation detailsRowAggregation)
	{
		final DimensionSpec dimensionSpec = MaterialCockpitUtil.retrieveDimensionSpec();

		final List<DimensionSpecGroup> groups = dimensionSpec.retrieveGroups();


		final List<I_S_Resource> plants = retrieveCountingPlants();


		final List<I_M_Warehouse> warehouses = retrieveWarehouses();

		final Builder<MainRowBucketId, MainRowWithSubRows> result = ImmutableMap.builder();
		for (final ProductId productId : productIds)
		{
			final MainRowBucketId key = MainRowBucketId.createPlainInstance(productId, timestamp);
			final MainRowWithSubRows mainRowBucket = newMainRowWithSubRows(key);

			if (detailsRowAggregation.isPlant())
			{
				for (final I_S_Resource plant : plants)
				{
					final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder()
							.detailsRowAggregation(detailsRowAggregation)
							.aggregationId(plant.getS_Resource_ID())
							.build();
					mainRowBucket.addEmptyCountingSubrowBucket(detailsRowAggregationIdentifier);
				}
			}
			else if (detailsRowAggregation.isWarehouse())
			{
				for (final I_M_Warehouse warehouse : warehouses)
				{
					final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder()
							.detailsRowAggregation(detailsRowAggregation)
							.aggregationId(warehouse.getM_Warehouse_ID())
							.build();
					mainRowBucket.addEmptyCountingSubrowBucket(detailsRowAggregationIdentifier);
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
	private MainRowWithSubRows newMainRowWithSubRows(final MainRowBucketId key)
	{
		return new MainRowWithSubRows(rowLookups, key);
	}

	private List<I_S_Resource> retrieveCountingPlants()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_Resource.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Resource.COLUMNNAME_ManufacturingResourceType, ManufacturingResourceType.Plant)
				.create()
				.list();
	}

	private List<I_M_Warehouse> retrieveWarehouses()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	private void addCockpitRowsToResult(
			@NonNull final CreateRowsRequest request,
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
			@NonNull final CreateRowsRequest request,
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
			@NonNull final CreateRowsRequest request,
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
}
