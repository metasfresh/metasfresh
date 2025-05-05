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
import org.compiere.model.I_C_TaxCategory;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class C_TaxCategory_StepDef
{
	private final C_TaxCategory_StepDefData taxCategoryTable;

	public C_TaxCategory_StepDef(@NonNull final C_TaxCategory_StepDefData taxCategoryTable)
	{
		this.taxCategoryTable = taxCategoryTable;
	}

	@And("load C_TaxCategory:")
	public void load_C_TaxCategory(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			loadTaxCategory(tableRow);
		}
	}

	@And("update C_TaxCategory:")
	public void update_C_TaxCategory(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			updateTaxCategory(tableRow);
		}
	}

	private void updateTaxCategory(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_TaxCategory taxCategoryRecord = taxCategoryTable.get(identifier);

		final Boolean isManualTax = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, "OPT." + I_C_TaxCategory.COLUMNNAME_IsManualTax);
		if (isManualTax != null)
		{
			taxCategoryRecord.setIsManualTax(isManualTax);
		}

		saveRecord(taxCategoryRecord);

		taxCategoryTable.putOrReplace(identifier, taxCategoryRecord);
	}

	private void loadTaxCategory(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID);

		if (id != null)
		{
			final I_C_TaxCategory taxCategoryRecord = InterfaceWrapperHelper.load(id, I_C_TaxCategory.class);

			taxCategoryTable.putOrReplace(identifier, taxCategoryRecord);
		}
	}
}