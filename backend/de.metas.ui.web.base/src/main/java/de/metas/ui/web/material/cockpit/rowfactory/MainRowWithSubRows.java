package de.metas.ui.web.material.cockpit.rowfactory;

import com.google.common.collect.ImmutableList;
import de.metas.dimension.DimensionSpec;
import de.metas.dimension.DimensionSpecGroup;
<<<<<<< HEAD
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
import de.metas.printing.esb.base.util.Check;
import de.metas.product.IProductBL;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow.MainRowBuilder;
import de.metas.ui.web.material.cockpit.QtyConvertorService;
import de.metas.util.Services;
=======
import de.metas.material.cockpit.ProductWithDemandSupply;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
import de.metas.money.Money;
import de.metas.printing.esb.base.util.Check;
import de.metas.product.ResourceId;
import de.metas.ui.web.material.cockpit.MaterialCockpitDetailsRowAggregation;
import de.metas.ui.web.material.cockpit.MaterialCockpitDetailsRowAggregationIdentifier;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow.MainRowBuilder;
import de.metas.ui.web.material.cockpit.MaterialCockpitRowCache;
import de.metas.ui.web.material.cockpit.MaterialCockpitRowLookups;
import de.metas.ui.web.material.cockpit.QtyConvertorService;
import de.metas.util.MFColor;
import lombok.Builder;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
<<<<<<< HEAD
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Warehouse;

=======
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.LinkedHashMap;
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

@Data
@EqualsAndHashCode(of = "productIdAndDate")
public class MainRowWithSubRows
{
<<<<<<< HEAD
	@NonNull
	private final MainRowBucketId productIdAndDate;
	@NonNull
	private final MainRowBucket mainRow = new MainRowBucket();
	@NonNull
	private final Map<DimensionSpecGroup, DimensionGroupSubRowBucket> dimensionGroupSubRows = new LinkedHashMap<>();
	@NonNull
	private final Map<Integer, CountingSubRowBucket> countingSubRows = new LinkedHashMap<>();
	@NonNull
	private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final QtyConvertorService qtyConvertorService = SpringContextHolder.instance.getBean(QtyConvertorService.class);

	public static MainRowWithSubRows create(@NonNull final MainRowBucketId productIdAndDate)
	{
		return new MainRowWithSubRows(productIdAndDate);
	}

	private MainRowWithSubRows(@NonNull final MainRowBucketId productIdAndDate)
	{
		this.productIdAndDate = productIdAndDate;
=======
	@NonNull private final MaterialCockpitRowCache cache;
	@NonNull private final MaterialCockpitRowLookups rowLookups;
    @NonNull private final QtyConvertorService qtyConvertorService;

	@NonNull private final MainRowBucketId productIdAndDate;
	@NonNull private final MainRowBucket mainRow = new MainRowBucket();
	@NonNull private final Map<DimensionSpecGroup, DimensionGroupSubRowBucket> dimensionGroupSubRows = new LinkedHashMap<>();
	@NonNull private final Map<MaterialCockpitDetailsRowAggregationIdentifier, CountingSubRowBucket> countingSubRows = new LinkedHashMap<>();

	@Builder
	private MainRowWithSubRows(
			@NonNull final MaterialCockpitRowCache cache,
			@NonNull final MaterialCockpitRowLookups rowLookups,
			@NonNull final MainRowBucketId productIdAndDate,
			@Nullable final MFColor procurementStatusColor,
			@Nullable final Money maxPurchasePrice)
	{
		this.cache = cache;
		this.rowLookups = rowLookups;

		this.productIdAndDate = productIdAndDate;
		this.mainRow.setProcurementStatus(procurementStatusColor != null ? procurementStatusColor.toHexString() : null);
		this.mainRow.setHighestPurchasePrice_AtDate(maxPurchasePrice);
		this.qtyConvertorService = SpringContextHolder.instance.getBean(QtyConvertorService.class);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public void addEmptyAttributesSubrowBucket(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
<<<<<<< HEAD
		dimensionGroupSubRows.computeIfAbsent(dimensionSpecGroup, DimensionGroupSubRowBucket::create);
	}

	public void addEmptyCountingSubrowBucket(final int plantId)
	{
		countingSubRows.computeIfAbsent(plantId, CountingSubRowBucket::create);
=======
		dimensionGroupSubRows.computeIfAbsent(dimensionSpecGroup, this::newSubRowBucket);
	}

	private DimensionGroupSubRowBucket newSubRowBucket(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
		return new DimensionGroupSubRowBucket(rowLookups, dimensionSpecGroup, cache);
	}

	public void addEmptyCountingSubRowBucket(final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier)
	{
		countingSubRows.computeIfAbsent(detailsRowAggregationIdentifier, this::newCountingSubRowBucket);
	}

	private CountingSubRowBucket newCountingSubRowBucket(final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier)
	{
		return new CountingSubRowBucket(cache, rowLookups, detailsRowAggregationIdentifier);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public void addCockpitRecord(
			@NonNull final I_MD_Cockpit cockpitRecord,
			@NonNull final DimensionSpec dimensionSpec,
<<<<<<< HEAD
			final boolean includePerPlantDetailRows)
=======
			@NonNull final MaterialCockpitDetailsRowAggregation detailsRowAggregation)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		boolean addedToAtLeastOneBucket = false;

		int ppPlantId = 0;
<<<<<<< HEAD
		if (cockpitRecord.getM_Warehouse_ID() > 0)
		{
			final I_M_Warehouse warehouse = warehouseDAO.getById(WarehouseId.ofRepoId(cockpitRecord.getM_Warehouse_ID()));
			ppPlantId = warehouse.getPP_Plant_ID();
		}

		if (cockpitRecord.getQtyStockEstimateCount().signum() != 0 || ppPlantId > 0)
		{
			if (includePerPlantDetailRows)
			{
				addCockpitRecordToCounting(cockpitRecord, ppPlantId);
				addedToAtLeastOneBucket = true;
			}
		}
		else
=======
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(cockpitRecord.getM_Warehouse_ID());
		if (warehouseId != null)
		{
			ppPlantId = getPlantId(warehouseId);
		}

		if (detailsRowAggregation.isPlant() && (cockpitRecord.getQtyStockEstimateCount_AtDate().signum() != 0 || ppPlantId > 0))
		{
			final MaterialCockpitDetailsRowAggregationIdentifier rowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder().detailsRowAggregation(detailsRowAggregation)
					.aggregationId(ppPlantId)
					.build();
			addCockpitRecordToCounting(cockpitRecord, rowAggregationIdentifier);
			addedToAtLeastOneBucket = true;
		}
		if (detailsRowAggregation.isWarehouse() && (cockpitRecord.getQtyStockEstimateCount_AtDate().signum() != 0 || warehouseId != null))
			{
			final MaterialCockpitDetailsRowAggregationIdentifier rowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder().detailsRowAggregation(detailsRowAggregation)
					.aggregationId(WarehouseId.toRepoId(warehouseId))
					.build();
			addCockpitRecordToCounting(cockpitRecord, rowAggregationIdentifier);
				addedToAtLeastOneBucket = true;
			}
		if (!addedToAtLeastOneBucket)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			addedToAtLeastOneBucket = addCockpitRecordToDimensionGroups(cockpitRecord, dimensionSpec);
		}

		if (!addedToAtLeastOneBucket)
		{
			// we need at least one subRow-bucket, even if there is no qtyOnHandCount, no plant and our dimensionSpec is empty!
<<<<<<< HEAD
			final DimensionGroupSubRowBucket fallbackBucket = dimensionGroupSubRows.computeIfAbsent(DimensionSpecGroup.OTHER_GROUP, DimensionGroupSubRowBucket::create);
=======
			final DimensionGroupSubRowBucket fallbackBucket = dimensionGroupSubRows.computeIfAbsent(DimensionSpecGroup.OTHER_GROUP, this::newSubRowBucket);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			fallbackBucket.addCockpitRecord(cockpitRecord);
		}
		mainRow.addDataRecord(cockpitRecord);
	}

<<<<<<< HEAD
	private void addCockpitRecordToCounting(@NonNull final I_MD_Cockpit stockEstimate, final int ppPlantId)
	{
		final CountingSubRowBucket countingSubRow = countingSubRows.computeIfAbsent(ppPlantId, CountingSubRowBucket::create);
		countingSubRow.addCockpitRecord(stockEstimate);
	}

=======
	private void addCockpitRecordToCounting(@NonNull final I_MD_Cockpit stockEstimate, final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier)
	{
		final CountingSubRowBucket countingSubRow = countingSubRows.computeIfAbsent(detailsRowAggregationIdentifier, this::newCountingSubRowBucket);
		countingSubRow.addCockpitRecord(stockEstimate);
	}

	private void addQuantitiesRecordToCounting(@NonNull final ProductWithDemandSupply quantitiesRecord, @NonNull final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier)
	{
		final CountingSubRowBucket countingSubRow = countingSubRows.computeIfAbsent(detailsRowAggregationIdentifier, this::newCountingSubRowBucket);
		countingSubRow.addQuantitiesRecord(quantitiesRecord);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/**
	 * @return true if there was at least one {@link DimensionGroupSubRowBucket} to which the given dataRecord could be added.
	 */
	private boolean addCockpitRecordToDimensionGroups(
			@NonNull final I_MD_Cockpit dataRecord,
			@NonNull final DimensionSpec dimensionSpec)
	{
		assertProductIdAndDateOfDataRecord(dataRecord);

		final AttributesKey attributesKey = AttributesKey.ofString(dataRecord.getAttributesKey());
		final List<DimensionGroupSubRowBucket> subRowBuckets = findOrCreateSubRowBucket(attributesKey, dimensionSpec);
		subRowBuckets.forEach(bucket -> bucket.addCockpitRecord(dataRecord));
		return !subRowBuckets.isEmpty();
	}

<<<<<<< HEAD
=======
	/**
	 * @return true if there was at least one {@link DimensionGroupSubRowBucket} to which the given quantitiesRecord could be added.
	 */
	private boolean addQuantitiesRecordToDimensionGroups(
			@NonNull final ProductWithDemandSupply quantitiesRecord,
			@NonNull final DimensionSpec dimensionSpec)
	{
		final List<DimensionGroupSubRowBucket> subRowBuckets = findOrCreateSubRowBucket(quantitiesRecord.getAttributesKey(), dimensionSpec);
		subRowBuckets.forEach(bucket -> bucket.addQuantitiesRecord(quantitiesRecord));
		return !subRowBuckets.isEmpty();
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private void assertProductIdAndDateOfDataRecord(@NonNull final I_MD_Cockpit dataRecord)
	{
		final MainRowBucketId key = MainRowBucketId.createInstanceForCockpitRecord(dataRecord);

		Check.errorUnless(
				productIdAndDate.equals(key),
				"The given parameter 'dataRecord' does not fit into this bucket; our productIdAndDate={}; dataRecord's productIdAndDate={}; fdataRecord={}",
				productIdAndDate, key, dataRecord);
	}

	private List<DimensionGroupSubRowBucket> findOrCreateSubRowBucket(
			@NonNull final AttributesKey dataRecordAttributesKey,
			@NonNull final DimensionSpec dimensionSpec)
	{
		final ImmutableList.Builder<DimensionGroupSubRowBucket> result = ImmutableList.builder();

		DimensionSpecGroup otherGroup = null;
		boolean addedToAnyGroup = false;

		for (final DimensionSpecGroup group : dimensionSpec.retrieveGroups())
		{
			final AttributesKey dimensionAttributesKey = group.getAttributesKey();

			if (DimensionSpecGroup.EMPTY_GROUP.equals(group) && AttributesKey.NONE.equals(dataRecordAttributesKey))
			{
<<<<<<< HEAD
				result.add(dimensionGroupSubRows.computeIfAbsent(group, DimensionGroupSubRowBucket::create));
=======
				result.add(dimensionGroupSubRows.computeIfAbsent(group, this::newSubRowBucket));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				addedToAnyGroup = true;
				continue;
			}
			else if (dataRecordAttributesKey.intersects(dimensionAttributesKey))
			{
<<<<<<< HEAD
				result.add(dimensionGroupSubRows.computeIfAbsent(group, DimensionGroupSubRowBucket::create));
=======
				result.add(dimensionGroupSubRows.computeIfAbsent(group, this::newSubRowBucket));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				addedToAnyGroup = true;
				continue;
			}

<<<<<<< HEAD
			// while iterating, also look out out for "otherGroup"
=======
			// while iterating, also look out for "otherGroup"
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			if (DimensionSpecGroup.OTHER_GROUP.equals(group))
			{
				otherGroup = group;
			}
		}

		if (!addedToAnyGroup && otherGroup != null)
		{
<<<<<<< HEAD
			result.add(dimensionGroupSubRows.computeIfAbsent(otherGroup, DimensionGroupSubRowBucket::create));
=======
			result.add(dimensionGroupSubRows.computeIfAbsent(otherGroup, this::newSubRowBucket));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		return result.build();
	}

	public void addStockRecord(
			@NonNull final I_MD_Stock stockRecord,
			@NonNull final DimensionSpec dimensionSpec,
<<<<<<< HEAD
			final boolean includePerPlantDetailRows)
	{
		boolean addedToAtLeastOneBucket = false;
		if (includePerPlantDetailRows)
		{
			addStockRecordToCounting(stockRecord);
=======
			@NonNull final MaterialCockpitDetailsRowAggregation detailsRowAggregation)
	{
		boolean addedToAtLeastOneBucket = false;
		if (!detailsRowAggregation.isNone())
		{
			addStockRecordToCounting(stockRecord, detailsRowAggregation);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			addedToAtLeastOneBucket = true;
		}
		addedToAtLeastOneBucket = addStockRecordToDimensionGroups(stockRecord, dimensionSpec) || addedToAtLeastOneBucket;

		if (!addedToAtLeastOneBucket)
		{
			// we need at least one subRow-bucket, even if there is no qtyOnHandCount, no plant and our dimensionSpec is empty!
<<<<<<< HEAD
			final DimensionGroupSubRowBucket fallbackBucket = dimensionGroupSubRows.computeIfAbsent(DimensionSpecGroup.OTHER_GROUP, DimensionGroupSubRowBucket::create);
=======
			final DimensionGroupSubRowBucket fallbackBucket = dimensionGroupSubRows.computeIfAbsent(DimensionSpecGroup.OTHER_GROUP, this::newSubRowBucket);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			fallbackBucket.addStockRecord(stockRecord);
		}

		mainRow.addStockRecord(stockRecord);
	}

<<<<<<< HEAD
	private void addStockRecordToCounting(@NonNull final I_MD_Stock stockRecord)
	{
		final I_M_Warehouse warehouseRecord = warehouseDAO.getById(WarehouseId.ofRepoId(stockRecord.getM_Warehouse_ID()));
		final int plantId = warehouseRecord.getPP_Plant_ID();
		final CountingSubRowBucket countingSubRow = countingSubRows.computeIfAbsent(plantId, CountingSubRowBucket::create);
		countingSubRow.addStockRecord(stockRecord);
	}
=======
	public void addQuantitiesRecord(
			@NonNull final ProductWithDemandSupply productWithDemandSupply,
			@NonNull final DimensionSpec dimensionSpec,
			@NonNull final MaterialCockpitDetailsRowAggregation detailsRowAggregation)
	{
		boolean addedToAtLeastOneBucket = false;

		int ppPlantId = 0;
		final WarehouseId warehouseId = productWithDemandSupply.getWarehouseId();
		if (warehouseId != null)
		{
			ppPlantId = getPlantId(warehouseId);
		}

		if (ppPlantId > 0)
		{
			if (detailsRowAggregation.isPlant())
			{
				final MaterialCockpitDetailsRowAggregationIdentifier rowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder().detailsRowAggregation(detailsRowAggregation)
						.aggregationId(ppPlantId)
						.build();
				addQuantitiesRecordToCounting(productWithDemandSupply, rowAggregationIdentifier);
				addedToAtLeastOneBucket = true;

			}
		}
		if (warehouseId != null)
			{
			if (detailsRowAggregation.isWarehouse())
			{
				final MaterialCockpitDetailsRowAggregationIdentifier rowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder().detailsRowAggregation(detailsRowAggregation)
						.aggregationId(WarehouseId.toRepoId(warehouseId))
						.build();

				addQuantitiesRecordToCounting(productWithDemandSupply, rowAggregationIdentifier);
				addedToAtLeastOneBucket = true;
			}
		}

		if (!addedToAtLeastOneBucket)
		{
			addedToAtLeastOneBucket = addQuantitiesRecordToDimensionGroups(productWithDemandSupply, dimensionSpec);
		}

		if (!addedToAtLeastOneBucket)
		{
			final DimensionGroupSubRowBucket fallbackBucket = dimensionGroupSubRows.computeIfAbsent(DimensionSpecGroup.OTHER_GROUP, this::newSubRowBucket);
			fallbackBucket.addQuantitiesRecord(productWithDemandSupply);
		}

		mainRow.addQuantitiesRecord(productWithDemandSupply);
	}

	private void addStockRecordToCounting(final @NonNull I_MD_Stock stockRecord,
										  final @NonNull MaterialCockpitDetailsRowAggregation detailsRowAggregation)
	{

		if (detailsRowAggregation.isNone())
		{
			// nothing to do
			return;
		}
		final WarehouseId warehouseId = WarehouseId.ofRepoId(stockRecord.getM_Warehouse_ID());
		final int plantId = getPlantId(warehouseId);

		if (detailsRowAggregation.isPlant())
		{
			final MaterialCockpitDetailsRowAggregationIdentifier rowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder().detailsRowAggregation(detailsRowAggregation)
					.aggregationId(plantId)
					.build();

			final CountingSubRowBucket countingSubRow = countingSubRows.computeIfAbsent(rowAggregationIdentifier, this::newCountingSubRowBucket);
		countingSubRow.addStockRecord(stockRecord);
	}
		else if (detailsRowAggregation.isWarehouse())
		{
			final MaterialCockpitDetailsRowAggregationIdentifier rowAggregationIdentifier = MaterialCockpitDetailsRowAggregationIdentifier.builder().detailsRowAggregation(detailsRowAggregation)
					.aggregationId(stockRecord.getM_Warehouse_ID())
					.build();

			final CountingSubRowBucket countingSubRow = countingSubRows.computeIfAbsent(rowAggregationIdentifier, this::newCountingSubRowBucket);
			countingSubRow.addStockRecord(stockRecord);
		}
	}

	private int getPlantId(final WarehouseId warehouseId)
	{
		return ResourceId.toRepoId(cache.getWarehouseById(warehouseId).getResourceId());
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * @return true if there was at least one {@link DimensionGroupSubRowBucket} to which the given dataRecord could be added.
	 */
	private boolean addStockRecordToDimensionGroups(
			@NonNull final I_MD_Stock dataRecord,
			@NonNull final DimensionSpec dimensionSpec)
	{
		final AttributesKey attributesKey = AttributesKey.ofString(dataRecord.getAttributesKey());
		final List<DimensionGroupSubRowBucket> subRowBuckets = findOrCreateSubRowBucket(attributesKey, dimensionSpec);
		subRowBuckets.forEach(bucket -> bucket.addStockRecord(dataRecord));

		return !subRowBuckets.isEmpty();
	}

	public MaterialCockpitRow createMainRowWithSubRows()
	{

		final MainRowBuilder mainRowBuilder = MaterialCockpitRow.mainRowBuilder()
<<<<<<< HEAD
				.productId(productIdAndDate.getProductId())
				.date(productIdAndDate.getDate())
				.qtyMaterialentnahme(mainRow.getQtyMaterialentnahme())
				.qtyDemandPPOrder(mainRow.getQtyDemandPPOrder())
				.qtyStockCurrent(mainRow.getQtyStockCurrent())
				.qtyOnHandStock(mainRow.getQtyOnHand())
				.qtySupplyPPOrder(mainRow.getQtySupplyPPOrder())
				.qtySupplyPurchaseOrder(mainRow.getQtySupplyPurchaseOrder())
				.qtySupplyDDOrder(mainRow.getQtySupplyDDOrder())
				.qtySupplySum(mainRow.getQtySupplySum())
				.qtySupplyRequired(mainRow.getQtySupplyRequired())
				.qtySupplyToSchedule(mainRow.getQtySupplyToSchedule())
				.qtyExpectedSurplus(mainRow.getQtyExpectedSurplus())
				.qtyDemandSalesOrder(mainRow.getQtyDemandSalesOrder())
				.qtyDemandDDOrder(mainRow.getQtyDemandDDOrder())
				.qtyDemandSum(mainRow.getQtyDemandSum())
				.qtyInventoryCount(mainRow.getQtyInventoryCount())
				.qtyInventoryTime(mainRow.getQtyInventoryTime())
				.qtyStockEstimateCount(mainRow.getQtyStockEstimateCount())
				.qtyStockEstimateTime(mainRow.getQtyStockEstimateTime())
				.qtyStockEstimateSeqNo(mainRow.getQtyStockEstimateSeqNo())
				.pmmQtyPromised(mainRow.getPmmQtyPromised())
=======
				.cache(cache)
				.lookups(rowLookups)
				.productId(productIdAndDate.getProductId())
				.date(productIdAndDate.getDate())
				.qtyMaterialentnahmeAtDate(mainRow.getQtyMaterialentnahmeAtDate())
				.qtyDemandPPOrderAtDate(mainRow.getQtyDemandPPOrderAtDate())
				.qtyStockCurrentAtDate(mainRow.getQtyStockCurrentAtDate())
				.qtyOnHandStock(mainRow.getQtyOnHand())
				.qtySupplyPPOrderAtDate(mainRow.getQtySupplyPPOrderAtDate())
				.qtySupplyPurchaseOrderAtDate(mainRow.getQtySupplyPurchaseOrderAtDate())
				.qtySupplyPurchaseOrder(mainRow.getQtySupplyPurchaseOrder())
				.qtySupplyDDOrderAtDate(mainRow.getQtySupplyDDOrderAtDate())
				.qtySupplySumAtDate(mainRow.getQtySupplySumAtDate())
				.qtySupplyRequiredAtDate(mainRow.getQtySupplyRequiredAtDate())
				.qtySupplyToScheduleAtDate(mainRow.getQtySupplyToScheduleAtDate())
				.qtyExpectedSurplusAtDate(mainRow.getQtyExpectedSurplusAtDate())
				.qtyDemandSalesOrderAtDate(mainRow.getQtyDemandSalesOrderAtDate())
				.qtyDemandSalesOrder(mainRow.getQtyDemandSalesOrder())
				.qtyDemandDDOrderAtDate(mainRow.getQtyDemandDDOrderAtDate())
				.qtyDemandSumAtDate(mainRow.getQtyDemandSumAtDate())
				.qtyInventoryCountAtDate(mainRow.getQtyInventoryCountAtDate())
				.qtyInventoryTimeAtDate(mainRow.getQtyInventoryTimeAtDate())
				.qtyStockEstimateCountAtDate(mainRow.getQtyStockEstimateCountAtDate())
				.qtyStockEstimateTimeAtDate(mainRow.getQtyStockEstimateTimeAtDate())
				.qtyStockEstimateSeqNoAtDate(mainRow.getQtyStockEstimateSeqNoAtDate())
				.pmmQtyPromisedAtDate(mainRow.getPmmQtyPromisedAtDate())
				.procurementStatus(mainRow.getProcurementStatus())
				.highestPurchasePrice_AtDate(mainRow.getHighestPurchasePrice_AtDate())
				.qtyOrdered_PurchaseOrder_AtDate(mainRow.getQtyOrdered_PurchaseOrder_AtDate())
				.qtyOrdered_SalesOrder_AtDate(mainRow.getQtyOrdered_SalesOrder_AtDate())
				.availableQty_AtDate(mainRow.getAvailableQty_AtDate())
				.remainingStock_AtDate(mainRow.getRemainingStock_AtDate())
				.pmm_QtyPromised_NextDay(mainRow.getPmm_QtyPromised_NextDay())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.allIncludedCockpitRecordIds(mainRow.getCockpitRecordIds())
				.allIncludedStockRecordIds(mainRow.getStockRecordIds())
				.qtyConvertor(qtyConvertorService.getQtyConvertorIfConfigured(productIdAndDate));

		for (final CountingSubRowBucket subRowBucket : countingSubRows.values())
		{
			final MaterialCockpitRow subRow = subRowBucket.createIncludedRow(this);
			final boolean subRowIsEmpty = subRow.getAllIncludedStockRecordIds().isEmpty() && subRow.getAllIncludedCockpitRecordIds().isEmpty();
			if (!subRowIsEmpty)
			{
				mainRowBuilder.includedRow(subRow);
			}
		}

		for (final DimensionGroupSubRowBucket subRowBucket : dimensionGroupSubRows.values())
		{
			final MaterialCockpitRow subRow = subRowBucket.createIncludedRow(this);
			final boolean subRowIsEmpty = subRow.getAllIncludedStockRecordIds().isEmpty() && subRow.getAllIncludedCockpitRecordIds().isEmpty();
			if (!subRowIsEmpty)
			{
				mainRowBuilder.includedRow(subRow);
			}
		}

		return mainRowBuilder.build();

	}
}
