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

import de.metas.aggregation.model.I_C_Aggregation_Attribute;
import de.metas.cucumber.stepdefs.DataTableUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

public class C_Aggregation_Attribute_StepDef
{
	private final C_Aggregation_Attribute_StepDefData aggregationAttributeTable;

	public C_Aggregation_Attribute_StepDef(@NonNull final C_Aggregation_Attribute_StepDefData aggregationAttributeTable)
	{
		this.aggregationAttributeTable = aggregationAttributeTable;
	}

	@Given("load C_Aggregation_Attributes:")
	public void load_C_Aggregation_Attribute(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadC_Aggregation_Attribute(tableRow);
		}
	}

	private void loadC_Aggregation_Attribute(@NonNull final Map<String, String> tableRow)
	{
		final int aggregationAttributeId = DataTableUtil.extractIntForColumnName(tableRow, I_C_Aggregation_Attribute.COLUMNNAME_C_Aggregation_Attribute_ID);
		final I_C_Aggregation_Attribute aggregationAttributeRecord = InterfaceWrapperHelper.load(aggregationAttributeId, I_C_Aggregation_Attribute.class);

		final String aggregationAttributeIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "C_Aggregation_Attribute");
		aggregationAttributeTable.putOrReplace(aggregationAttributeIdentifier, aggregationAttributeRecord);
	}
}
