/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.aggregation;

import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_AggregationItem_StepDef
{
	private final StepDefData<I_C_AggregationItem> aggregationItemTable;

	public C_AggregationItem_StepDef(@NonNull final StepDefData<I_C_AggregationItem> aggregationItemTable)
	{
		this.aggregationItemTable = aggregationItemTable;
	}

	@And("load C_AggregationItem")
	public void load_C_AggregationItem(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
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
	}
}
