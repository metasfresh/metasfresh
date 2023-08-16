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

import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_Aggregation_StepDef
{
	private final C_Aggregation_StepDefData aggregationTable;

	public C_Aggregation_StepDef(@NonNull final C_Aggregation_StepDefData aggregationTable)
	{
		this.aggregationTable = aggregationTable;
	}

	@And("load C_Aggregation:")
	public void load_C_Aggregation(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::loadAggregation);
	}

	@And("update C_Aggregation:")
	public void update_C_Aggregation(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::updateAggregation);
	}

	private void loadAggregation(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_Aggregation.COLUMNNAME_C_Aggregation_ID + "." + TABLECOLUMN_IDENTIFIER);

		final String id = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Aggregation.COLUMNNAME_C_Aggregation_ID);

		if (Check.isNotBlank(id))
		{
			final I_C_Aggregation aggregationRecord = InterfaceWrapperHelper.load(Integer.parseInt(id), I_C_Aggregation.class);

			aggregationTable.putOrReplace(identifier, aggregationRecord);
		}
	}

	private void updateAggregation(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_Aggregation.COLUMNNAME_C_Aggregation_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_Aggregation aggregationRecord = aggregationTable.get(identifier);

		final Boolean isDefault = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_C_Aggregation.COLUMNNAME_IsDefault);
		if (isDefault != null)
		{
			aggregationRecord.setIsDefault(isDefault);
		}

		InterfaceWrapperHelper.saveRecord(aggregationRecord);
	}
}
