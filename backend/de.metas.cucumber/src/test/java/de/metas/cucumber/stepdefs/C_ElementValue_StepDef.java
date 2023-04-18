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

import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_ElementValue_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Element_StepDefData elementTable;
	private final C_ElementValue_StepDefData elementValueTable;

	public C_ElementValue_StepDef(
			@NonNull final C_Element_StepDefData elementTable,
			@NonNull final C_ElementValue_StepDefData elementValueTable)
	{
		this.elementTable = elementTable;
		this.elementValueTable = elementValueTable;
	}

	@And("load C_ElementValue:")
	public void load_C_ElementValue(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			loadElementValue(row);
		}
	}

	private void loadElementValue(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_ElementValue.COLUMNNAME_C_ElementValue_ID + "." + TABLECOLUMN_IDENTIFIER);

		final String elementIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_ElementValue.COLUMNNAME_C_Element_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Element elementRecord = elementTable.get(elementIdentifier);
		final String value = DataTableUtil.extractStringForColumnName(tableRow, I_C_ElementValue.COLUMNNAME_Value);

		final I_C_ElementValue elementValueRecord = queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ElementValue.COLUMNNAME_Value, value)
				.addEqualsFilter(I_C_ElementValue.COLUMNNAME_C_Element_ID, elementRecord.getC_Element_ID())
				.create()
				.firstOnlyNotNull(I_C_ElementValue.class);

		elementValueTable.putOrReplace(identifier, elementValueRecord);
	}
}
