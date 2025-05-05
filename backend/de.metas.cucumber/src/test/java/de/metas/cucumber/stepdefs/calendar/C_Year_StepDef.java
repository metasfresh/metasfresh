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

package de.metas.cucumber.stepdefs.calendar;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Year;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_Year_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Year_StepDefData yearTable;

	private final C_Calendar_StepDefData calendarTable;

	public C_Year_StepDef(@NonNull final C_Year_StepDefData yearTable, final C_Calendar_StepDefData calendarTable)
	{
		this.yearTable = yearTable;
		this.calendarTable = calendarTable;
	}

	@And("load C_Year from metasfresh:")
	public void load_C_Year(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String fiscalYear = DataTableUtil.extractStringForColumnName(tableRow, I_C_Year.COLUMNNAME_FiscalYear);
			assertThat(fiscalYear).as(I_C_Year.COLUMNNAME_FiscalYear + "is a mandatory column").isNotBlank();

			final String calendarIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Year.COLUMNNAME_C_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(calendarIdentifier).as(I_C_Year.COLUMNNAME_C_Calendar_ID + "is a mandatory column").isNotBlank();

			final I_C_Calendar calendar = calendarTable.get(calendarIdentifier);
			assertThat(calendar).as("No calendar found for identifier: {}", calendarIdentifier).isNotNull();

			final I_C_Year yearRecord = queryBL.createQueryBuilder(I_C_Year.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Year.COLUMNNAME_C_Calendar_ID, calendar.getC_Calendar_ID())
					.addEqualsFilter(I_C_Year.COLUMNNAME_FiscalYear, fiscalYear)
					.create()
					.firstOnlyNotNull(I_C_Year.class);

			assertThat(yearRecord).as("No Record found for Calendar: {} and FiscalYear: {}", calendar.getName(), fiscalYear).isNotNull();

			final String yearIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Year.COLUMNNAME_C_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
			yearTable.put(yearIdentifier, yearRecord);
		}
	}

	@And("metasfresh contains C_Year")
	public void createC_Year(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createYear(tableRow);
		}
	}
	
	private void createYear(@NonNull final Map<String, String> tableRow)
	{
		final String fiscalYear = DataTableUtil.extractStringForColumnName(tableRow, I_C_Year.COLUMNNAME_FiscalYear);
		final String calendarIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Year.COLUMNNAME_C_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Calendar calendarRecord = calendarTable.get(calendarIdentifier);

		final I_C_Year yearRecord = InterfaceWrapperHelper.newInstance(I_C_Year.class);

		yearRecord.setFiscalYear(fiscalYear);
		yearRecord.setC_Calendar_ID(calendarRecord.getC_Calendar_ID());
		
		saveRecord(yearRecord);

		final String yearIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Year.COLUMNNAME_C_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
		yearTable.put(yearIdentifier, yearRecord);
	}
}
