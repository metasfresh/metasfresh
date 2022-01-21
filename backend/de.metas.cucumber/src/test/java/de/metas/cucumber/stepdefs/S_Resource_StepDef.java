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
import org.compiere.model.I_S_Resource;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_S_Resource.COLUMNNAME_S_Resource_ID;

public class S_Resource_StepDef
{
	private final StepDefData<I_S_Resource> resourceTable;

	public S_Resource_StepDef(@NonNull final StepDefData<I_S_Resource> resourceTable)
	{
		this.resourceTable = resourceTable;
	}

	@And("load S_Resource:")
	public void load_S_Resource(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_S_Resource_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int resourceId = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_S_Resource_ID);

			final I_S_Resource testResource = InterfaceWrapperHelper.load(resourceId, I_S_Resource.class);
			assertThat(testResource).isNotNull();

			resourceTable.put(resourceIdentifier, testResource);
		}
	}
}
