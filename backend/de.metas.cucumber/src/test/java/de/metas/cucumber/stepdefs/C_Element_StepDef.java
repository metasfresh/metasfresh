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

package de.metas.cucumber.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Element;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_Element_StepDef
{
	private final C_Element_StepDefData elementTable;

	public C_Element_StepDef(@NonNull final C_Element_StepDefData elementTable)
	{
		this.elementTable = elementTable;
	}

	@And("load C_Element:")
	public void load_C_Element(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			loadElement(row);
		}
	}

	private void loadElement(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Element.COLUMNNAME_C_Element_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_C_Element.COLUMNNAME_C_Element_ID);

		if (id != null)
		{
			final I_C_Element elementRecord = InterfaceWrapperHelper.load(id, I_C_Element.class);

			elementTable.putOrReplace(identifier, elementRecord);
		}
	}
}
