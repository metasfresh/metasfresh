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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Table;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

public class C_Aggregation_StepDef
{
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final C_Aggregation_StepDefData aggregationTable;

	public C_Aggregation_StepDef(@NonNull final C_Aggregation_StepDefData aggregationTable)
	{
		this.aggregationTable = aggregationTable;
	}

	@Given("metasfresh contains C_Aggregations:")
	public void metasfresh_contains_c_aggregation(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createC_Aggregation(tableRow);
		}
	}

	@Given("load C_Aggregations:")
	public void load_C_Aggregation(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadC_Aggregation(tableRow);
		}
	}

	private void loadC_Aggregation(@NonNull final Map<String, String> tableRow)
	{
		final int aggregationId = DataTableUtil.extractIntForColumnName(tableRow, I_C_Aggregation.COLUMNNAME_C_Aggregation_ID);
		final I_C_Aggregation aggregationRecord = InterfaceWrapperHelper.load(aggregationId, I_C_Aggregation.class);

		final String aggregationIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "C_Aggregation");
		aggregationTable.putOrReplace(aggregationIdentifier, aggregationRecord);
	}

	private void createC_Aggregation(@NonNull final Map<String, String> tableRow)
	{
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_Aggregation.COLUMNNAME_Name);
		final String tableName = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Table.COLUMNNAME_TableName);
		final AdTableId adTableId = tableDAO.retrieveAdTableId(tableName);
		final String entityType = DataTableUtil.extractStringForColumnName(tableRow, I_C_Aggregation.COLUMNNAME_EntityType);

		final I_C_Aggregation aggregationRecord = newInstanceOutOfTrx(I_C_Aggregation.class);

		aggregationRecord.setName(name);
		aggregationRecord.setAD_Table_ID(AdTableId.toRepoId(adTableId));
		aggregationRecord.setEntityType(entityType);

		final String aggregationUsageLevel = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Aggregation.COLUMNNAME_AggregationUsageLevel);
		if (Check.isNotBlank(aggregationUsageLevel))
		{
			aggregationRecord.setAggregationUsageLevel(aggregationUsageLevel);
		}

		InterfaceWrapperHelper.saveRecord(aggregationRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "C_Aggregation");
		aggregationTable.putOrReplace(recordIdentifier, aggregationRecord);
	}
}
