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

package de.metas.cucumber.stepdefs.incoterms;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Incoterms;

import java.util.Map;

public class C_Incoterms_StepDef
{
	final C_Incoterms_StepDefData incotermsTable;

	public C_Incoterms_StepDef(@NonNull final C_Incoterms_StepDefData incotermsTable)
	{
		this.incotermsTable = incotermsTable;
	}

	@And("load C_Incoterms by id:")
	public void load_C_Incoterms(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::loadC_Incoterm_ById);
	}

	private void loadC_Incoterm_ById(@NonNull final Map<String, String> row)
	{
		final int incotermsId = DataTableUtil.extractIntForColumnName(row, I_C_Incoterms.COLUMNNAME_C_Incoterms_ID);

		final I_C_Incoterms record = InterfaceWrapperHelper.load(incotermsId, I_C_Incoterms.class);

		final String incotermsIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Incoterms.COLUMNNAME_C_Incoterms_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		incotermsTable.put(incotermsIdentifier, record);
	}

	@And("validate C_Incoterms:")
	public void validateC_Incoterms(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final SoftAssertions softly = new SoftAssertions();

			final String value = DataTableUtil.extractStringForColumnName(row, I_C_Incoterms.COLUMNNAME_Value);
			final String name = DataTableUtil.extractStringForColumnName(row, I_C_Incoterms.COLUMNNAME_Name);

			final String incotermsIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Incoterms.COLUMNNAME_C_Incoterms_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Incoterms incoterms = incotermsTable.get(incotermsIdentifier);

			softly.assertThat(incoterms.getValue()).isEqualTo(value);
			softly.assertThat(incoterms.getName()).isEqualTo(name);

			softly.assertAll();
		}
	}
}
