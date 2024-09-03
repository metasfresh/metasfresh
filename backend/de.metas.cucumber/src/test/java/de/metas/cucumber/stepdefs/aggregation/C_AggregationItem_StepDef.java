/*
 * #%L
 * de.metas.cucumber
 * %%
<<<<<<< HEAD
 * Copyright (C) 2023 metas GmbH
=======
 * Copyright (C) 2024 metas GmbH
>>>>>>> 44e73d2f360 (cucumber scenario for invoice with aggregation attributes)
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

package de.metas.cucumber.stepdefs.aggregation;

<<<<<<< HEAD
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
=======
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.aggregation.model.I_C_Aggregation_Attribute;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
>>>>>>> 44e73d2f360 (cucumber scenario for invoice with aggregation attributes)
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_AggregationItem_StepDef
{
<<<<<<< HEAD
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_AggregationItem_StepDefData aggregationItemTable;

	public C_AggregationItem_StepDef(@NonNull final C_AggregationItem_StepDefData aggregationItemTable)
	{
		this.aggregationItemTable = aggregationItemTable;
	}

	@And("load C_AggregationItem")
	public void load_C_AggregationItem(@NonNull final DataTable dataTable)
=======
	private final C_Aggregation_StepDefData aggregationTable;
	private final C_AggregationItem_StepDefData aggregationItemTable;
	private final C_Aggregation_Attribute_StepDefData aggregationAttributeTable;

	public C_AggregationItem_StepDef(
			@NonNull final C_Aggregation_StepDefData aggregationTable,
			@NonNull final C_AggregationItem_StepDefData aggregationItemTable,
			@NonNull final C_Aggregation_Attribute_StepDefData aggregationAttributeTable)
	{
		this.aggregationTable = aggregationTable;
		this.aggregationItemTable = aggregationItemTable;
		this.aggregationAttributeTable = aggregationAttributeTable;
	}

	@Given("metasfresh contains C_AggregationItems:")
	public void metasfresh_contains_c_aggregation_item(@NonNull final DataTable dataTable)
>>>>>>> 44e73d2f360 (cucumber scenario for invoice with aggregation attributes)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
<<<<<<< HEAD
			loadAggregationItem(tableRow);
		}
	}

	@And("update C_AggregationItem")
	public void update_C_AggregationItem(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			updateAggregationItem(tableRow);
		}
	}

	@And("update C_AggregationItem:")
	public void update_C_AggregationItem_directly(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final int aggregationId = DataTableUtil.extractIntForColumnName(row, I_C_AggregationItem.COLUMNNAME_C_Aggregation_ID);
			final int columnId = DataTableUtil.extractIntForColumnName(row, I_C_AggregationItem.COLUMNNAME_AD_Column_ID);
			final Boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_AggregationItem.COLUMNNAME_IsActive, null);

			final I_C_AggregationItem aggItem = queryBL.createQueryBuilder(I_C_AggregationItem.class)
					.addEqualsFilter(I_C_AggregationItem.COLUMNNAME_C_Aggregation_ID, aggregationId)
					.addEqualsFilter(I_C_AggregationItem.COLUMNNAME_AD_Column_ID, columnId)
					.orderBy(I_C_AggregationItem.COLUMN_C_AggregationItem_ID)
					.create()
					.firstNotNull(I_C_AggregationItem.class);

			if (isActive != null)
			{
				aggItem.setIsActive(isActive);
			}

			InterfaceWrapperHelper.save(aggItem);
		}
	}

	private void loadAggregationItem(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_AggregationItem.COLUMNNAME_C_AggregationItem_ID + "." + TABLECOLUMN_IDENTIFIER);

		final String id = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_AggregationItem.COLUMNNAME_C_AggregationItem_ID);

		if (Check.isNotBlank(id))
		{
			final I_C_AggregationItem aggregationItemRecord = InterfaceWrapperHelper.load(Integer.parseInt(id), I_C_AggregationItem.class);

			aggregationItemTable.putOrReplace(identifier, aggregationItemRecord);
		}
	}

	private void updateAggregationItem(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_AggregationItem.COLUMNNAME_C_AggregationItem_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_AggregationItem aggregationItemRecord = aggregationItemTable.get(identifier);

		final boolean isActive = DataTableUtil.extractBooleanForColumnName(row, I_C_AggregationItem.COLUMNNAME_IsActive);
		aggregationItemRecord.setIsActive(isActive);

		InterfaceWrapperHelper.saveRecord(aggregationItemRecord);
=======
			createC_Aggregation_Item(tableRow);
		}
	}

	private void createC_Aggregation_Item(@NonNull final Map<String, String> tableRow)
	{
		final String type = DataTableUtil.extractStringForColumnName(tableRow, I_C_AggregationItem.COLUMNNAME_Type);
		final String entityType = DataTableUtil.extractStringForColumnName(tableRow, I_C_AggregationItem.COLUMNNAME_EntityType);

		final String aggregationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_AggregationItem.COLUMNNAME_C_Aggregation_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Aggregation aggregationRecord = aggregationTable.get(aggregationIdentifier);

		final I_C_AggregationItem aggregationItemRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_AggregationItem.class);

		aggregationItemRecord.setType(type);
		aggregationItemRecord.setEntityType(entityType);
		aggregationItemRecord.setC_Aggregation_ID(aggregationRecord.getC_Aggregation_ID());

		final String includedAggregationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_AggregationItem.COLUMNNAME_Included_Aggregation_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(includedAggregationIdentifier))
		{
			final I_C_Aggregation includedAggregationRecord = aggregationTable.get(includedAggregationIdentifier);
			aggregationItemRecord.setIncluded_Aggregation_ID(includedAggregationRecord.getC_Aggregation_ID());
		}

		final String aggregationAttributeIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_AggregationItem.COLUMNNAME_C_Aggregation_Attribute_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(aggregationAttributeIdentifier))
		{
			final I_C_Aggregation_Attribute aggregationAttributeRecord = aggregationAttributeTable.get(aggregationAttributeIdentifier);
			aggregationItemRecord.setC_Aggregation_Attribute_ID(aggregationAttributeRecord.getC_Aggregation_Attribute_ID());
		}

		InterfaceWrapperHelper.saveRecord(aggregationItemRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "C_AggregationItem");
		aggregationItemTable.putOrReplace(recordIdentifier, aggregationItemRecord);
>>>>>>> 44e73d2f360 (cucumber scenario for invoice with aggregation attributes)
	}
}
