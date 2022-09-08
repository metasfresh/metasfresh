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

package de.metas.cucumber.stepdefs.calendar;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Calendar;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_Calendar_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Calendar_StepDefData calendarTable;

	public C_Calendar_StepDef(@NonNull final C_Calendar_StepDefData calendarTable)
	{
		this.calendarTable = calendarTable;
	}

	@And("load C_Calendar from metasfresh:")
	public void load_C_Calendar(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_Calendar.COLUMNNAME_Name);

			final I_C_Calendar calendar = queryBL.createQueryBuilder(I_C_Calendar.class)
					.addEqualsFilter(I_C_Calendar.COLUMNNAME_Name, name)
					.create()
					.firstOnlyNotNull(I_C_Calendar.class);

			final String calendarIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Calendar.COLUMNNAME_C_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
			calendarTable.put(calendarIdentifier, calendar);
		}
	}

}
