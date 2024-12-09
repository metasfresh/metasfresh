package de.metas.ui.web.material.cockpit.rowfactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableSet;
<<<<<<< HEAD
import de.metas.dimension.DimensionSpec;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.ProductId;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitUtil;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
=======
import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.currency.CurrencyRepository;
import de.metas.dimension.DimensionSpec;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.material.cockpit.ProductWithDemandSupply;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.planning.IResourceDAO;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.stats.purchase_max_price.PurchaseLastMaxPriceProvider;
import de.metas.order.stats.purchase_max_price.PurchaseLastMaxPriceRequest;
import de.metas.order.stats.purchase_max_price.PurchaseLastMaxPriceService;
import de.metas.product.IProductBL;
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
import de.metas.uom.IUOMDAO;
import de.metas.util.IColorRepository;
import de.metas.util.MFColor;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseRepository;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.Adempiere;
import org.compiere.model.X_M_Product;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
<<<<<<< HEAD
=======
import java.util.Optional;
import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
public class MaterialCockpitRowFactory
{
=======
@RequiredArgsConstructor
public class MaterialCockpitRowFactory
{
	@NonNull private final MaterialCockpitRowLookups rowLookups;
	@NonNull private final ResourceService resourceService;
	@NonNull private final ProductRepository productRepository;
	@NonNull private final WarehouseRepository warehouseRepository;
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);
	@NonNull private final IColorRepository colorRepository = Services.get(IColorRepository.class);
	@NonNull private final ADReferenceService adReferenceService;
	@NonNull private final PurchaseLastMaxPriceService purchaseLastMaxPriceService;

	@VisibleForTesting
	public static MaterialCockpitRowFactory newInstanceForUnitTesting(final MaterialCockpitRowLookups rowLookups)
	{
		Adempiere.assertUnitTestMode();
		return new MaterialCockpitRowFactory(
				rowLookups,
				ResourceService.newInstanceForJUnitTesting(),
				ProductRepository.newInstanceForUnitTesting(),
				WarehouseRepository.newInstanceForUnitTesting(),
				ADReferenceService.get(),
				new PurchaseLastMaxPriceService(new MoneyService(new CurrencyRepository()))
		);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Value
	@lombok.Builder
	public static class CreateRowsRequest
	{
<<<<<<< HEAD
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

		boolean includePerPlantDetailRows;
=======
		@NonNull LocalDate date;
		@NonNull @Singular("productIdToListEvenIfEmpty") ImmutableSet<ProductId> productIdsToListEvenIfEmpty;
		@NonNull @Singular List<I_MD_Cockpit> cockpitRecords;
		@NonNull @Singular List<I_MD_Stock> stockRecords;
		@NonNull @Singular List<ProductWithDemandSupply> quantitiesRecords;
		@NonNull MaterialCockpitDetailsRowAggregation detailsRowAggregation;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public List<MaterialCockpitRow> createRows(@NonNull final CreateRowsRequest request)
	{
<<<<<<< HEAD
		final Map<MainRowBucketId, MainRowWithSubRows> emptyRowBuckets = createEmptyRowBuckets(
				request.getProductIdsToListEvenIfEmpty(),
				request.getDate(),
				request.isIncludePerPlantDetailRows());

		final DimensionSpec dimensionSpec = MaterialCockpitUtil.retrieveDimensionSpec();

		final Map<MainRowBucketId, MainRowWithSubRows> result = new HashMap<>(emptyRowBuckets);

		addCockpitRowsToResult(request, dimensionSpec, result);
		addStockRowsToResult(request, dimensionSpec, result);

		return result.values()
				.stream()
				.map(MainRowWithSubRows::createMainRowWithSubRows)
				.collect(ImmutableList.toImmutableList());
	}

	@VisibleForTesting
	Map<MainRowBucketId, MainRowWithSubRows> createEmptyRowBuckets(
			@NonNull final ImmutableSet<ProductId> productIds,
			@NonNull final LocalDate timestamp,
			final boolean includePerPlantDetailRows)
	{
		final DimensionSpec dimensionSpec = MaterialCockpitUtil.retrieveDimensionSpec();

		final List<DimensionSpecGroup> groups = dimensionSpec.retrieveGroups();
		final List<I_S_Resource> plants = retrieveCountingPlants(includePerPlantDetailRows);

		final Builder<MainRowBucketId, MainRowWithSubRows> result = ImmutableMap.builder();
		for (final ProductId productId : productIds)
		{
			final MainRowBucketId key = MainRowBucketId.createPlainInstance(productId, timestamp);
			final MainRowWithSubRows mainRowBucket = MainRowWithSubRows.create(key);

			for (final I_S_Resource plant : plants)
			{
				mainRowBucket.addEmptyCountingSubrowBucket(plant.getS_Resource_ID());
			}

			for (final DimensionSpecGroup group : groups)
			{
				mainRowBucket.addEmptyAttributesSubrowBucket(group);
			}
			result.put(key, mainRowBucket);

		}
		return result.build();
	}

	private List<I_S_Resource> retrieveCountingPlants(final boolean includePerPlantDetailRows)
	{
		if (!includePerPlantDetailRows)
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_Resource.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Resource.COLUMNNAME_ManufacturingResourceType, X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant)
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

			final MainRowWithSubRows mainRowBucket = result.computeIfAbsent(mainRowBucketId, MainRowWithSubRows::create);
			mainRowBucket.addCockpitRecord(cockpitRecord, dimensionSpec, request.isIncludePerPlantDetailRows());
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

			final MainRowWithSubRows mainRowBucket = result.computeIfAbsent(mainRowBucketId, MainRowWithSubRows::create);
			mainRowBucket.addStockRecord(stockRecord, dimensionSpec, request.isIncludePerPlantDetailRows());
		}
	}

=======
		return newCreateRowsCommand(request).execute();
	}

	@VisibleForTesting
	CreateRowsCommand newCreateRowsCommand(final @NonNull CreateRowsRequest request)
	{
		final MaterialCockpitRowCache cache = MaterialCockpitRowCache.builder()
				.productRepository(productRepository)
				.warehouseRepository(warehouseRepository)
				.uomDAO(uomDAO)
				.build();

		return CreateRowsCommand.builder()
				.rowLookups(rowLookups)
				.resourceService(resourceService)
				.cache(cache)
				.colorRepository(colorRepository)
				.adReferenceService(adReferenceService)
				.purchaseLastMaxPriceProvider(purchaseLastMaxPriceService.newProvider())
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
		@NonNull private final ResourceService resourceService;
		@NonNull private MaterialCockpitRowCache cache;
		@NonNull private final IColorRepository colorRepository;
		@NonNull private final ADReferenceService adReferenceService;
		@NonNull private final PurchaseLastMaxPriceProvider purchaseLastMaxPriceProvider;

		@NonNull private final CreateRowsRequest request;

		private static final ReferenceId PROCUREMENTSTATUS_Reference_ID = ReferenceId.ofRepoId(X_M_Product.PROCUREMENTSTATUS_AD_Reference_ID);

		public List<MaterialCockpitRow> execute()
		{
			purchaseLastMaxPriceProvider.warmUp(request.getProductIdsToListEvenIfEmpty(), request.getDate());

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

			final ImmutableSet<WarehouseId> warehouseIds = cache.getAllActiveWarehouseIds();

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
			final Money maxPurchasePrice = purchaseLastMaxPriceProvider.getPrice(
							PurchaseLastMaxPriceRequest.builder()
									.productId(mainRowBucketId.getProductId())
									.evalDate(mainRowBucketId.getDate())
									.build())
					.getMaxPurchasePrice();

			final MFColor procurementStatusColor = getProcurementStatusColor(mainRowBucketId.getProductId()).orElse(null);

			return MainRowWithSubRows.builder()
					.cache(cache)
					.rowLookups(rowLookups)
					.productIdAndDate(mainRowBucketId)
					.procurementStatusColor(procurementStatusColor)
					.maxPurchasePrice(maxPurchasePrice)
					.build();
		}

		private Optional<MFColor> getProcurementStatusColor(@NonNull final ProductId productId)
		{
			return adReferenceService.getRefListById(PROCUREMENTSTATUS_Reference_ID)
					.getItemByValue(cache.getProductById(productId).getProcurementStatus())
					.map(ADRefListItem::getColorId)
					.map(colorRepository::getColorById);
		}
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
