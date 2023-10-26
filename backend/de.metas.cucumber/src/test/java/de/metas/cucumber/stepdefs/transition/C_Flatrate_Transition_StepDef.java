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

package de.metas.cucumber.stepdefs.transition;

import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.calendar.C_Calendar_StepDefData;
import de.metas.document.engine.DocStatus;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Calendar;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class C_Flatrate_Transition_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Flatrate_Transition_StepDefData transitionTable;
	private final C_Calendar_StepDefData calendarTable;

	public C_Flatrate_Transition_StepDef(
			@NonNull final C_Flatrate_Transition_StepDefData transitionTable,
			@NonNull final C_Calendar_StepDefData calendarTable)
	{
		this.transitionTable = transitionTable;
		this.calendarTable = calendarTable;
	}

	@And("load C_Flatrate_Transition from metasfresh:")
	public void load_C_Flatrate_Transition(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadTransition(tableRow);
		}
	}

	@And("validate C_Flatrate_Transition")
	public void validate_C_Flatrate_Transition(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateTransition(tableRow);
		}
	}

	@And("metasfresh contains C_Flatrate_Transition")
	public void create_C_Flatrate_Transition(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createTransition(tableRow);
		}
	}

	private void loadTransition(@NonNull final Map<String, String> tableRow)
	{
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_Name);

		final I_C_Flatrate_Transition transitionRecord = queryBL.createQueryBuilder(I_C_Flatrate_Transition.class)
				.addEqualsFilter(I_C_Flatrate_Transition.COLUMNNAME_Name, name)
				.create()
				.firstOnlyNotNull(I_C_Flatrate_Transition.class);

		final String transitionIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_C_Flatrate_Transition_ID + "." + TABLECOLUMN_IDENTIFIER);
		transitionTable.putOrReplace(transitionIdentifier, transitionRecord);
	}

	private void validateTransition(@NonNull final Map<String, String> tableRow)
	{
		final String transitionIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_C_Flatrate_Transition_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Flatrate_Transition transitionRecord = transitionTable.get(transitionIdentifier);

		final SoftAssertions softly = new SoftAssertions();

		final Boolean isEndsWithCalendarYear = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, "OPT." + I_C_Flatrate_Transition.COLUMNNAME_EndsWithCalendarYear);
		if (isEndsWithCalendarYear != null)
		{
			softly.assertThat(transitionRecord.isEndsWithCalendarYear()).as(I_C_Flatrate_Transition.COLUMNNAME_EndsWithCalendarYear).isEqualTo(isEndsWithCalendarYear);
		}

		final Integer termDuration = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Transition.COLUMNNAME_TermDuration);
		if (termDuration != null)
		{
			softly.assertThat(transitionRecord.getTermDuration()).as(I_C_Flatrate_Transition.COLUMNNAME_TermDuration).isEqualTo(termDuration);
		}

		final String termDurationUnit = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Transition.COLUMNNAME_TermDurationUnit);
		if (Check.isNotBlank(termDurationUnit))
		{
			softly.assertThat(transitionRecord.getTermDurationUnit()).as(I_C_Flatrate_Transition.COLUMNNAME_TermDurationUnit).isEqualTo(termDurationUnit);
		}

		final Integer termOfNotice = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Transition.COLUMNNAME_TermOfNotice);
		if (termOfNotice != null)
		{
			softly.assertThat(transitionRecord.getTermOfNotice()).as(I_C_Flatrate_Transition.COLUMNNAME_TermOfNotice).isEqualTo(termOfNotice);
		}

		final String termOfNoticeUnit = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Transition.COLUMNNAME_TermOfNoticeUnit);
		if (Check.isNotBlank(termOfNoticeUnit))
		{
			softly.assertThat(transitionRecord.getTermOfNoticeUnit()).as(I_C_Flatrate_Transition.COLUMNNAME_TermOfNoticeUnit).isEqualTo(termOfNoticeUnit);
		}

		final Boolean isNotifyUserInCharge = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, "OPT." + I_C_Flatrate_Transition.COLUMNNAME_IsNotifyUserInCharge);
		if (isNotifyUserInCharge != null)
		{
			softly.assertThat(transitionRecord.isNotifyUserInCharge()).as(I_C_Flatrate_Transition.COLUMNNAME_IsNotifyUserInCharge).isEqualTo(isNotifyUserInCharge);
		}

		final String calendarIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Transition.COLUMNNAME_C_Calendar_Contract_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(calendarIdentifier))
		{
			final I_C_Calendar calendarRecord = calendarTable.get(calendarIdentifier);
			softly.assertThat(transitionRecord.getC_Calendar_Contract_ID()).as(I_C_Flatrate_Transition.COLUMNNAME_C_Calendar_Contract_ID).isEqualTo(calendarRecord.getC_Calendar_ID());
		}

		softly.assertAll();
	}

	private void createTransition(@NonNull final Map<String, String> tableRow)
	{
		final boolean isEndsWithCalendarYear = DataTableUtil.extractBooleanForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_EndsWithCalendarYear);
		final int termDuration = DataTableUtil.extractIntForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_TermDuration);
		final String termDurationUnit = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_TermDurationUnit);
		final int termOfNotice = DataTableUtil.extractIntForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_TermOfNotice);
		final String termOfNoticeUnit = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_TermOfNoticeUnit);
		final boolean isNotifyUserInCharge = DataTableUtil.extractBooleanForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_IsNotifyUserInCharge);
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_Name);

		final String calendarIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_C_Calendar_Contract_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Calendar calendarRecord = calendarTable.get(calendarIdentifier);

		final I_C_Flatrate_Transition transitionRecord = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class);

		transitionRecord.setName(name);
		transitionRecord.setEndsWithCalendarYear(isEndsWithCalendarYear);
		transitionRecord.setTermDuration(termDuration);
		transitionRecord.setTermDurationUnit(termDurationUnit);
		transitionRecord.setTermOfNotice(termOfNotice);
		transitionRecord.setTermOfNoticeUnit(termOfNoticeUnit);
		transitionRecord.setIsNotifyUserInCharge(isNotifyUserInCharge);
		transitionRecord.setC_Calendar_Contract_ID(calendarRecord.getC_Calendar_ID());
		transitionRecord.setDocStatus(DocStatus.Completed.getCode());

		saveRecord(transitionRecord);

		final String transitionIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Flatrate_Transition.COLUMNNAME_C_Flatrate_Transition_ID + "." + TABLECOLUMN_IDENTIFIER);
		transitionTable.putOrReplace(transitionIdentifier, transitionRecord);
	}
}
