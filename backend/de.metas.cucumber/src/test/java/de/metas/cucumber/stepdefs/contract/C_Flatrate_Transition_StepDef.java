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

package de.metas.cucumber.stepdefs.contract;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Year;
import org.compiere.model.MYear;
import org.compiere.util.TimeUtil;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Creates {@link I_C_Flatrate_Transition} records ("Vertragsverlängerung/-übergang") used by contract fixtures,
 * and ensures the {@code C_Year}/{@code C_Period} rows a transition's calendar needs (e.g. for a term whose
 * duration spans into the next year) exist.
 */
@RequiredArgsConstructor
public class C_Flatrate_Transition_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final @NonNull C_Flatrate_Transition_StepDefData transitionTable;

	/**
	 * Creates {@link I_C_Flatrate_Transition} records. The calendar is always the calendar of the seeded
	 * {@link StepDefConstants#FLATRATE_TRANSITION_ID} transition (no step exists yet to create/load a named
	 * {@code C_Calendar}, so a per-row calendar override is not offered).
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code Identifier} (required) — identifier for later reference</li>
	 *     <li>{@code TermDuration} (required) — the contract duration; {@code 0} means "not computed automatically",
	 *         i.e. the term keeps its entered {@code EndDate}</li>
	 *     <li>{@code TermDurationUnit} (required) — {@code day}/{@code week}/{@code month}/{@code year}</li>
	 *     <li>{@code OPT.TermOfNotice}, {@code OPT.TermOfNoticeUnit} (optional) — the notice period</li>
	 *     <li>{@code OPT.ExtensionType} (optional) — {@code EA} (extend all) / {@code EO} (extend one)</li>
	 *     <li>{@code OPT.EnsurePeriodsForYears} (optional, comma-separated) — calendar years (e.g. {@code 2022,2023})
	 *         for which a {@code C_Year} + its 12 standard {@code C_Period} rows are created on the transition's
	 *         calendar, if not already there — needed for a term whose end date/notice date falls into a year that
	 *         has no periods yet</li>
	 * </ul>
	 * <pre>
	 * And metasfresh contains C_Flatrate_Transition:
	 *   | Identifier         | TermDuration | TermDurationUnit | OPT.EnsurePeriodsForYears |
	 *   | transition_zeroDur | 0            | day              | 2022,2023                 |
	 * </pre>
	 */
	@Given("metasfresh contains C_Flatrate_Transition:")
	public void createTransitions(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Calendar calendar = getDefaultCalendar();

			final I_C_Flatrate_Transition record = newInstance(I_C_Flatrate_Transition.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			record.setName(row.suggestValueAndName().getName());
			record.setC_Calendar_Contract_ID(calendar.getC_Calendar_ID());
			record.setTermDuration(row.getAsInt(I_C_Flatrate_Transition.COLUMNNAME_TermDuration));
			record.setTermDurationUnit(row.getAsString(I_C_Flatrate_Transition.COLUMNNAME_TermDurationUnit));

			row.getAsOptionalInt(I_C_Flatrate_Transition.COLUMNNAME_TermOfNotice).ifPresent(record::setTermOfNotice);
			row.getAsOptionalString(I_C_Flatrate_Transition.COLUMNNAME_TermOfNoticeUnit).ifPresent(record::setTermOfNoticeUnit);
			row.getAsOptionalString(I_C_Flatrate_Transition.COLUMNNAME_ExtensionType).ifPresent(record::setExtensionType);

			saveRecord(record);

			row.getAsOptionalCommaSeparatedString("EnsurePeriodsForYears")
					.orElseGet(ImmutableList::of)
					.forEach(yearStr -> ensurePeriodsForYear(calendar, Integer.parseInt(yearStr.trim())));

			transitionTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}

	private I_C_Calendar getDefaultCalendar()
	{
		final I_C_Flatrate_Transition seededTransition = load(StepDefConstants.FLATRATE_TRANSITION_ID.getRepoId(), I_C_Flatrate_Transition.class);
		return seededTransition.getC_Calendar_Contract();
	}

	private void ensurePeriodsForYear(@NonNull final I_C_Calendar calendar, final int year)
	{
		final boolean periodsAlreadyExist = queryBL.createQueryBuilder(I_C_Year.class)
				.addEqualsFilter(I_C_Year.COLUMNNAME_C_Calendar_ID, calendar.getC_Calendar_ID())
				.addEqualsFilter(I_C_Year.COLUMNNAME_FiscalYear, String.valueOf(year))
				.create()
				.anyMatch();
		if (periodsAlreadyExist)
		{
			return;
		}

		final I_C_Year yearRecord = newInstance(I_C_Year.class);
		yearRecord.setC_Calendar_ID(calendar.getC_Calendar_ID());
		yearRecord.setFiscalYear(String.valueOf(year));
		saveRecord(yearRecord);

		MYear.createStdPeriods(yearRecord, null, TimeUtil.getDay(year, 1, 1), null);
	}
}
