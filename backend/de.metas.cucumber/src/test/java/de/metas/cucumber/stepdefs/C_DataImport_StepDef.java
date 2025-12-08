/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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
import org.compiere.model.I_C_DataImport;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_DataImport_StepDef
{
	private final C_DataImport_StepDefData dataImportTable;

	public C_DataImport_StepDef(@NonNull final C_DataImport_StepDefData dataImportTable)
	{
		this.dataImportTable = dataImportTable;
	}

	@And("load C_DataImport:")
	public void load_C_DataImport(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			loadDataImport(row);
		}
	}

	private void loadDataImport(@NonNull final Map<String, String> row)
	{
		final String dataImportIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DataImport.COLUMNNAME_C_DataImport_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_C_DataImport.COLUMNNAME_C_DataImport_ID);

		if (id != null)
		{
			final I_C_DataImport dataImportRecord = InterfaceWrapperHelper.load(id, I_C_DataImport.class);
			assertThat(dataImportRecord).isNotNull();

			dataImportTable.putOrReplace(dataImportIdentifier, dataImportRecord);
		}
	}
}
