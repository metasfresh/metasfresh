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
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;

public class Context_StepDef
{
	@And("set context properties:")
	public void setContextProperties(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> externalReferencesTableList = dataTable.asMaps();
		for (final Map<String, String> row : externalReferencesTableList)
		{
			final String key = DataTableUtil.extractStringForColumnName(row, "Key");
			final String value = DataTableUtil.extractStringForColumnName(row, "Value");

			Env.getCtx().setProperty(key, value);
		}
	}
}
