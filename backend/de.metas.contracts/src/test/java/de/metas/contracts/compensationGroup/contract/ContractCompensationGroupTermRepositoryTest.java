package de.metas.contracts.compensationGroup.contract;

import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
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

class ContractCompensationGroupTermRepositoryTest
{
	/** The terms' dates are written via {@code TimeUtil.asTimestamp} (JVM default zone); pinned so the test does not depend on the machine's zone. */
	private TimeZone jvmTimezoneBackup;

	private ContractCompensationGroupTermRepository repository;

	@BeforeEach
	void beforeEach()
	{
		jvmTimezoneBackup = TimeZone.getDefault();
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
		AdempiereTestHelper.get().init();
		repository = new ContractCompensationGroupTermRepository();
	}

	@AfterEach
	void afterEach()
	{
		TimeZone.setDefault(jvmTimezoneBackup);
	}

	@Builder(builderMethodName = "term", buildMethodName = "create")
	private static I_C_Flatrate_Term createTerm(
			@Nullable final TypeConditions typeConditions,
			@Nullable final DocStatus docStatus,
			@NonNull final LocalDateTime startDate,
			@Nullable final LocalDate endDate,
			@NonNull final FlatrateTermStatus contractStatus,
			@Nullable final I_C_Flatrate_Term next)
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions((typeConditions != null ? typeConditions : TypeConditions.COMPENSATION_GROUP).getCode());
		term.setDocStatus((docStatus != null ? docStatus : DocStatus.Completed).getCode());
		term.setStartDate(TimeUtil.asTimestamp(startDate));
		term.setEndDate(endDate != null ? TimeUtil.asTimestamp(endDate) : null);
		term.setContractStatus(contractStatus.getCode());
		term.setC_FlatrateTerm_Next_ID(next != null ? next.getC_Flatrate_Term_ID() : -1);
		saveRecord(term);
		return term;
	}

	private static LocalDateTime day(@NonNull final String localDate)
	{
		return LocalDate.parse(localDate).atStartOfDay();
	}

	@Test
	void getTermsDueForDailyContractStatusUpdate_onlyWaitingStartedAndRunningEndedNotExtended()
	{
		final LocalDate today = LocalDate.parse("2026-07-01");

		final I_C_Flatrate_Term waitingStartsToday = term().startDate(day("2026-07-01")).endDate(LocalDate.parse("2026-12-31")).contractStatus(FlatrateTermStatus.Waiting).create();
		final I_C_Flatrate_Term waitingStartsTodayWithTime = term().startDate(LocalDateTime.parse("2026-07-01T10:30:00")).endDate(LocalDate.parse("2026-12-31")).contractStatus(FlatrateTermStatus.Waiting).create();
		final I_C_Flatrate_Term waitingStartedEarlier = term().startDate(day("2026-06-01")).endDate(LocalDate.parse("2026-12-31")).contractStatus(FlatrateTermStatus.Waiting).create();
		term().startDate(day("2026-07-02")).endDate(LocalDate.parse("2026-12-31")).contractStatus(FlatrateTermStatus.Waiting).create(); // starts tomorrow

		final I_C_Flatrate_Term runningEndedYesterday = term().startDate(day("2025-07-01")).endDate(LocalDate.parse("2026-06-30")).contractStatus(FlatrateTermStatus.Running).create();
		term().startDate(day("2025-07-02")).endDate(LocalDate.parse("2026-07-01")).contractStatus(FlatrateTermStatus.Running).create(); // ends today
		term().startDate(day("2025-07-01")).endDate(null).contractStatus(FlatrateTermStatus.Running).create(); // no end date
		final I_C_Flatrate_Term successor = term().startDate(day("2026-07-01")).endDate(LocalDate.parse("2027-06-30")).contractStatus(FlatrateTermStatus.Running).create();
		term().startDate(day("2025-07-01")).endDate(LocalDate.parse("2026-06-30")).contractStatus(FlatrateTermStatus.Running).next(successor).create(); // ended, but extended

		term().startDate(day("2025-07-01")).endDate(LocalDate.parse("2026-06-30")).contractStatus(FlatrateTermStatus.Quit).create();
		term().startDate(day("2025-07-01")).endDate(LocalDate.parse("2026-06-30")).contractStatus(FlatrateTermStatus.EndingContract).create();
		term().docStatus(DocStatus.Drafted).startDate(day("2026-07-01")).endDate(LocalDate.parse("2026-12-31")).contractStatus(FlatrateTermStatus.Waiting).create();
		term().typeConditions(TypeConditions.SUBSCRIPTION).startDate(day("2025-07-01")).endDate(LocalDate.parse("2026-06-30")).contractStatus(FlatrateTermStatus.Running).create();

		assertThat(repository.getTermsDueForDailyContractStatusUpdate(today))
				.extracting(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.containsExactly(
						waitingStartsToday.getC_Flatrate_Term_ID(),
						waitingStartsTodayWithTime.getC_Flatrate_Term_ID(),
						waitingStartedEarlier.getC_Flatrate_Term_ID(),
						runningEndedYesterday.getC_Flatrate_Term_ID());
	}
}
