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

package de.metas.cucumber.stepdefs.edi;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_EXP_Format;
import org.compiere.model.I_EXP_FormatLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Step-def for the {@code EXP_FormatLine} records of the replication import formats.
 * The lines are shared by every scenario on the executor, so a changed line is put back to the migrated state by the {@code @After} hook below.
 */
public class EXP_FormatLine_StepDef
{
	private static final boolean MIGRATED_IS_IMPORT_LENIENT = true;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final List<Integer> changedFormatLineIds = new ArrayList<>();

	/**
	 * Sets {@code IsImportLenient} of the format line with the given {@code Value} (the column name) of the given format.
	 * Saved through the model layer, so the cached format lines are invalidated.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>EXP_Format.Value</b> - (required) Value of the EXP_Format<br>
	 *   <b>Value</b> - (required) Value of the EXP_FormatLine<br>
	 *   <b>IsImportLenient</b> - (required) Y/N<br>
	 * @cucumber.example
	 * <pre>
	 * Given the following EXP_FormatLines are set:
	 *   | EXP_Format.Value                | Value        | IsImportLenient |
	 *   | EDI_C_BPartner_Lookup_BPL_GLN_v | Lookup_Label | N               |
	 * </pre>
	 */
	@Given("the following EXP_FormatLines are set:")
	public void set_EXP_FormatLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String formatValue = row.getAsString("EXP_Format.Value");
			final String lineValue = row.getAsString(I_EXP_FormatLine.COLUMNNAME_Value);
			final boolean isImportLenient = row.getAsBoolean(I_EXP_FormatLine.COLUMNNAME_IsImportLenient);

			final I_EXP_FormatLine formatLine = retrieveFormatLine(formatValue, lineValue);
			formatLine.setIsImportLenient(isImportLenient);
			InterfaceWrapperHelper.saveRecord(formatLine);
			changedFormatLineIds.add(formatLine.getEXP_FormatLine_ID());
		});
	}

	@After
	public void restoreMigratedFormatLines()
	{
		for (final int formatLineId : changedFormatLineIds)
		{
			final I_EXP_FormatLine formatLine = InterfaceWrapperHelper.load(formatLineId, I_EXP_FormatLine.class);
			formatLine.setIsImportLenient(MIGRATED_IS_IMPORT_LENIENT);
			InterfaceWrapperHelper.saveRecord(formatLine);
		}
		changedFormatLineIds.clear();
	}

	@NonNull
	private I_EXP_FormatLine retrieveFormatLine(@NonNull final String formatValue, @NonNull final String lineValue)
	{
		final I_EXP_Format format = queryBL.createQueryBuilder(I_EXP_Format.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EXP_Format.COLUMNNAME_Value, formatValue)
				.create()
				.firstOnlyNotNull(I_EXP_Format.class);

		return queryBL.createQueryBuilder(I_EXP_FormatLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EXP_FormatLine.COLUMNNAME_EXP_Format_ID, format.getEXP_Format_ID())
				.addEqualsFilter(I_EXP_FormatLine.COLUMNNAME_Value, lineValue)
				.create()
				.firstOnlyNotNull(I_EXP_FormatLine.class);
	}
}
