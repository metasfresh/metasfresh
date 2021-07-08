package de.metas.ui.web.material.cockpit.rowfactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import de.metas.dimension.DimensionSpec;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow.MainRowBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

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
	public static MainRowWithSubRows create(@NonNull final MainRowBucketId productIdAndDate)
	{
		return new MainRowWithSubRows(productIdAndDate);
	}

	private final MainRowBucketId productIdAndDate;

	private final MainRowBucket mainRow = new MainRowBucket();

	private final Map<DimensionSpecGroup, DimensionGroupSubRowBucket> dimensionGroupSubRows = new LinkedHashMap<>();

	private final Map<Integer, CountingSubRowBucket> countingSubRows = new LinkedHashMap<>();

	private MainRowWithSubRows(@NonNull final MainRowBucketId productIdAndDate)
	{
		this.productIdAndDate = productIdAndDate;
	}

	public void addEmptyAttributesSubrowBucket(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
		dimensionGroupSubRows.computeIfAbsent(dimensionSpecGroup, DimensionGroupSubRowBucket::create);
	}

	public void addEmptyCountingSubrowBucket(final int plantId)
	{
		countingSubRows.computeIfAbsent(plantId, CountingSubRowBucket::create);
	}

	public void addCockpitRecord(
			@NonNull final I_MD_Cockpit cockpitRecord,
			@NonNull final DimensionSpec dimensionSpec,
			final boolean includePerPlantDetailRows)
	{
		boolean addedToAtLeastOneBucket = false;
		if (cockpitRecord.getQtyOnHandCount().signum() != 0 || cockpitRecord.getPP_Plant_ID() > 0)
		{
			if (includePerPlantDetailRows)
			{
				addCockpitRecordToCounting(cockpitRecord);
				addedToAtLeastOneBucket = true;
			}
		}
		else
		{
			addedToAtLeastOneBucket = addCockpitRecordToDimensionGroups(cockpitRecord, dimensionSpec);
		}

		if (!addedToAtLeastOneBucket)
		{
			// we need at least one subRow-bucket, even if there is no qtyOnHandCount, no plant and our dimensionSpec is empty!
			final DimensionGroupSubRowBucket fallbackBucket = dimensionGroupSubRows.computeIfAbsent(DimensionSpecGroup.OTHER_GROUP, DimensionGroupSubRowBucket::create);
			fallbackBucket.addCockpitRecord(cockpitRecord);
		}
		mainRow.addDataRecord(cockpitRecord);
	}

	private void addCockpitRecordToCounting(@NonNull final I_MD_Cockpit stockEstimate)
	{
		final CountingSubRowBucket countingSubRow = countingSubRows.computeIfAbsent(stockEstimate.getPP_Plant_ID(), CountingSubRowBucket::create);
		countingSubRow.addCockpitRecord(stockEstimate);
	}

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
				result.add(dimensionGroupSubRows.computeIfAbsent(group, DimensionGroupSubRowBucket::create));
				addedToAnyGroup = true;
				continue;
			}
			else if (dataRecordAttributesKey.intersects(dimensionAttributesKey))
			{
				result.add(dimensionGroupSubRows.computeIfAbsent(group, DimensionGroupSubRowBucket::create));
				addedToAnyGroup = true;
				continue;
			}

			// while iterating, also look out out for "otherGroup"
			if (DimensionSpecGroup.OTHER_GROUP.equals(group))
			{
				otherGroup = group;
			}
		}

		if (!addedToAnyGroup && otherGroup != null)
		{
			result.add(dimensionGroupSubRows.computeIfAbsent(otherGroup, DimensionGroupSubRowBucket::create));
		}
		return result.build();
	}

	public void addStockRecord(
			@NonNull final I_MD_Stock stockRecord,
			@NonNull final DimensionSpec dimensionSpec,
			final boolean includePerPlantDetailRows)
	{
		boolean addedToAtLeastOneBucket = false;
		if (includePerPlantDetailRows)
		{
			addStockRecordToCounting(stockRecord);
			addedToAtLeastOneBucket = true;
		}
		addedToAtLeastOneBucket = addStockRecordToDimensionGroups(stockRecord, dimensionSpec) || addedToAtLeastOneBucket;

		if (!addedToAtLeastOneBucket)
		{
			// we need at least one subRow-bucket, even if there is no qtyOnHandCount, no plant and our dimensionSpec is empty!
			final DimensionGroupSubRowBucket fallbackBucket = dimensionGroupSubRows.computeIfAbsent(DimensionSpecGroup.OTHER_GROUP, DimensionGroupSubRowBucket::create);
			fallbackBucket.addStockRecord(stockRecord);
		}

		mainRow.addStockRecord(stockRecord);
	}

	private void addStockRecordToCounting(@NonNull final I_MD_Stock stockRecord)
	{
		final int plantId = stockRecord.getM_Warehouse().getPP_Plant_ID();
		final CountingSubRowBucket countingSubRow = countingSubRows.computeIfAbsent(plantId, CountingSubRowBucket::create);
		countingSubRow.addStockRecord(stockRecord);
	}

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
				.qtyMaterialentnahme(mainRow.getQtyMaterialentnahme())
				.qtyRequiredForProduction(mainRow.getQtyRequiredForProduction())
				.qtyOnHandEstimate(mainRow.getQtyOnHandEstimate())
				.qtyOnHandStock(mainRow.getQtyOnHand())
				.qtyReservedPurchase(mainRow.getQtyReservedPurchase())
				.qtyAvailableToPromiseEstimate(mainRow.getQtyAvailableToPromiseEstimate())
				.qtyReservedSale(mainRow.getQtyReservedSale())
				.pmmQtyPromised(mainRow.getPmmQtyPromised())
				.allIncludedCockpitRecordIds(mainRow.getCockpitRecordIds())
				.allIncludedStockRecordIds(mainRow.getStockRecordIds());

		for (final CountingSubRowBucket subRowBucket : countingSubRows.values())
		{
			final MaterialCockpitRow subRow = subRowBucket.createIncludedRow(this);
			mainRowBuilder.includedRow(subRow);
		}

		for (final DimensionGroupSubRowBucket subRowBucket : dimensionGroupSubRows.values())
		{
			final MaterialCockpitRow subRow = subRowBucket.createIncludedRow(this);
			mainRowBuilder.includedRow(subRow);
		}

		return mainRowBuilder.build();

	}
}
