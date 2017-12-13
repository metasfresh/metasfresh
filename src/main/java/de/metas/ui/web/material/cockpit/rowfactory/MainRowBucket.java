package de.metas.ui.web.material.cockpit.rowfactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.mm.attributes.api.AttributesKeys;

import com.google.common.collect.ImmutableList;

import de.metas.dimension.DimensionSpec;
import de.metas.dimension.DimensionSpec.DimensionSpecGroup;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
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
public class MaterialCockpitMainRowBucket
{
	public static MaterialCockpitMainRowBucket create(@NonNull final MaterialCockpitMainRowId productIdAndDate)
	{
		return new MaterialCockpitMainRowBucket(productIdAndDate);
	}

	private final MaterialCockpitMainRowId productIdAndDate;

	private final Map<DimensionSpecGroup, AttributeSubRowBucket> attributeSubRows = new LinkedHashMap<>();

	private final Map<Integer, CountingSubRowBucket> countingSubRows = new LinkedHashMap<>();

	private MaterialCockpitMainRowBucket(@NonNull final MaterialCockpitMainRowId productIdAndDate)
	{
		this.productIdAndDate = productIdAndDate;
	}

	public void addEmptyAttributesSubrowBucket(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
		attributeSubRows.computeIfAbsent(dimensionSpecGroup, AttributeSubRowBucket::create);
	}

	public void addEmptyCountingSubrowBucket(int plantId)
	{
		countingSubRows.computeIfAbsent(plantId, CountingSubRowBucket::create);

	}

	public void addDataRecord(
			@NonNull final I_X_MRP_ProductInfo_Detail_MV dataRecord,
			@NonNull final DimensionSpec dimensionSpec)
	{
		assertProductIdAndDateOfDataRecord(dataRecord);

		final List<AttributeSubRowBucket> subRowBuckets = findOrCreateSubRowBucket(dataRecord, dimensionSpec);
		subRowBuckets.forEach(bucket -> bucket.addDataRecord(dataRecord));
	}

	private void assertProductIdAndDateOfDataRecord(@NonNull final I_X_MRP_ProductInfo_Detail_MV dataRecord)
	{
		final MaterialCockpitMainRowId key = MaterialCockpitMainRowId.createInstanceForDataRecord(dataRecord);

		Check.errorUnless(
				productIdAndDate.equals(key),
				"The given parameter 'dataRecord' does not fit into this bucket; our productIdAndDate={}; dataRecord's productIdAndDate={}; fdataRecord={}",
				productIdAndDate, key, dataRecord);
	}

	private List<AttributeSubRowBucket> findOrCreateSubRowBucket(
			@NonNull final I_X_MRP_ProductInfo_Detail_MV dataRecord,
			@NonNull final DimensionSpec dimensionSpec)
	{
		final ImmutableList.Builder<AttributeSubRowBucket> result = ImmutableList.builder();

		final AttributesKey dataRecordAttributesKey = AttributesKeys.createAttributesKeyFromASIAllAttributeValues(dataRecord.getM_AttributeSetInstance_ID());

		for (final DimensionSpecGroup group : dimensionSpec.retrieveGroups())
		{
			final AttributesKey dimensionAttributesKey = group.getAttributesKey();
			if (dataRecordAttributesKey.contains(dimensionAttributesKey))
			{
				result.add(attributeSubRows.computeIfAbsent(group, AttributeSubRowBucket::create));
			}
		}
		return result.build();
	}

	public void addCounting(@NonNull final I_Fresh_QtyOnHand_Line counting)
	{
		final CountingSubRowBucket countingSubRow = countingSubRows.computeIfAbsent(counting.getPP_Plant_ID(), CountingSubRowBucket::create);
		countingSubRow.addDataRecord(counting);
	}

	public MaterialCockpitRow createMainRowWithSubRows()
	{
		final MainRowBuilder mainRowBuilder = MaterialCockpitRow.mainRowBuilder();

		for (final CountingSubRowBucket subRowBucket : countingSubRows.values())
		{
			final MaterialCockpitRow subRow = subRowBucket.createIncludedRow(this);
			mainRowBuilder.includedRow(subRow);
		}

		for (final AttributeSubRowBucket subRowBucket : attributeSubRows.values())
		{
			final MaterialCockpitRow subRow = subRowBucket.createIncludedRow(this);
			mainRowBuilder.includedRow(subRow);
		}
		return mainRowBuilder.build();

	}

}
