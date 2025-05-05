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

package de.metas.cucumber.stepdefs.period;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class C_Period_StepDef
{
	private final C_Year_StepDefData yearTable;
	private final C_Period_StepDefData periodTable;

	public C_Period_StepDef(
			@NonNull final C_Year_StepDefData yearTable,
			@NonNull final C_Period_StepDefData periodTable)
	{
		this.yearTable = yearTable;
		this.periodTable = periodTable;
	}

	@And("metasfresh contains C_Period")
	public void createC_Period(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createPeriod(tableRow);
		}
	}

	private void createPeriod(@NonNull final Map<String, String> tableRow)
	{
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_Period.COLUMNNAME_Name);
		final Timestamp startDate = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_C_Period.COLUMNNAME_StartDate);
		final Timestamp endDate = DataTableUtil.extractDateTimestampForColumnNameOrNull(tableRow, "OPT." + I_C_Period.COLUMNNAME_EndDate);
		final String yearIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Period.COLUMNNAME_C_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer periodNo = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_C_Period.COLUMNNAME_PeriodNo);
		final I_C_Year yearRecord = yearTable.get(yearIdentifier);

		final I_C_Period periodRecord = InterfaceWrapperHelper.newInstance(I_C_Period.class);

		periodRecord.setName(name);
		periodRecord.setStartDate(startDate);
		periodRecord.setC_Year_ID(yearRecord.getC_Year_ID());

		if (endDate != null)
		{
			periodRecord.setEndDate(endDate);
		}
		
		if (periodNo != null)
		{
			periodRecord.setPeriodNo(periodNo);
		}

		saveRecord(periodRecord);

		final String periodIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Period.COLUMNNAME_C_Period_ID + "." + TABLECOLUMN_IDENTIFIER);
		periodTable.putOrReplace(periodIdentifier, periodRecord);
	}
}
