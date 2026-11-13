/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Year;
import org.compiere.model.MYear;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.util.Locale;

@RequiredArgsConstructor
public class C_Calendar_StepDef
{
	@NonNull private final C_Year_StepDefData yearTable;

	/**
	 * Creates a new, separate {@link I_C_Calendar} (the name gets a unique suffix, because the DB is reused across runs)
	 * and one {@link I_C_Year} per table row, each with its 12 standard monthly periods ({@code PeriodType='S'}, calendar months).
	 * Does not touch the organisation's or client's own calendar.
	 * <p>
	 * Columns: {@code C_Year_ID} (identifier), {@code FiscalYear} (e.g. {@code 2025}).
	 * Saving a period creates its {@code C_PeriodControl} rows (via {@code MPeriod.afterSave}), as in the "Create Periods" process.
	 */
	@Given("metasfresh contains C_Calendar {string} with fiscal years:")
	public void metasfresh_contains_c_calendar_with_fiscal_years(@NonNull final String calendarName, @NonNull final DataTable dataTable)
	{
		final I_C_Calendar calendar = InterfaceWrapperHelper.newInstance(I_C_Calendar.class);
		calendar.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		calendar.setName(calendarName + "_" + System.currentTimeMillis());
		InterfaceWrapperHelper.saveRecord(calendar);

		DataTableRows.of(dataTable).forEach(row -> createYear(calendar, row));
	}

	private void createYear(@NonNull final I_C_Calendar calendar, @NonNull final DataTableRow row)
	{
		final String fiscalYear = row.getAsString(I_C_Year.COLUMNNAME_FiscalYear);

		final I_C_Year year = InterfaceWrapperHelper.newInstance(I_C_Year.class);
		year.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		year.setC_Calendar_ID(calendar.getC_Calendar_ID());
		year.setFiscalYear(fiscalYear);
		InterfaceWrapperHelper.saveRecord(year);

		final LocalDate startDate = LocalDate.of(Integer.parseInt(fiscalYear), 1, 1);
		MYear.createStdPeriods(year, Locale.ENGLISH, TimeUtil.asTimestamp(startDate), null);

		yearTable.putOrReplace(row.getAsIdentifier(I_C_Year.COLUMNNAME_C_Year_ID), year);
	}
}
