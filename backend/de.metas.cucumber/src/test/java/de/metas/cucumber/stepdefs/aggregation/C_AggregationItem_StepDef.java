/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.aggregation.model.I_C_Aggregation_Attribute;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_AggregationItem_StepDef
{
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
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
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
	}
}
