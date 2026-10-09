package de.metas.contracts.compensationGroup.contract;

import de.metas.common.util.time.SystemTime;
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.TimeZone;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
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

class ContractCompensationGroupTermServiceTest
{
	/** The code reads "today" from SystemTime's zone and the term's dates via {@code Timestamp#toLocalDateTime} (JVM default zone); as on a server, both are the same zone here. */
	private TimeZone jvmTimezoneBackup;

	private ContractCompensationGroupTermService service;

	@BeforeEach
	void beforeEach()
	{
		jvmTimezoneBackup = TimeZone.getDefault();
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
		AdempiereTestHelper.get().init();
		service = ContractCompensationGroupTermService.newInstanceForUnitTesting();
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
		TimeZone.setDefault(jvmTimezoneBackup);
	}

	private static void setToday(@NonNull final String localDate)
	{
		SystemTime.setFixedTimeSource(localDate + "T10:00:00+02:00[Europe/Berlin]");
	}

	@Builder(builderMethodName = "term", buildMethodName = "create")
	private static I_C_Flatrate_Term createTerm(
			@Nullable final TypeConditions typeConditions,
			@Nullable final DocStatus docStatus,
			@NonNull final String startDate,
			@Nullable final String endDate,
			@Nullable final FlatrateTermStatus contractStatus,
			@Nullable final I_C_Flatrate_Term next,
			@Nullable final String dateContracted,
			@Nullable final String masterStartDate)
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions((typeConditions != null ? typeConditions : TypeConditions.COMPENSATION_GROUP).getCode());
		term.setDocStatus((docStatus != null ? docStatus : DocStatus.Completed).getCode());
		term.setStartDate(TimeUtil.asTimestamp(LocalDate.parse(startDate)));
		term.setEndDate(endDate != null ? TimeUtil.asTimestamp(LocalDate.parse(endDate)) : null);
		term.setContractStatus(contractStatus != null ? contractStatus.getCode() : null);
		term.setC_FlatrateTerm_Next_ID(next != null ? next.getC_Flatrate_Term_ID() : -1);
		term.setDateContracted(dateContracted != null ? TimeUtil.asTimestamp(LocalDate.parse(dateContracted)) : null);
		term.setMasterStartDate(masterStartDate != null ? TimeUtil.asTimestamp(LocalDate.parse(masterStartDate)) : null);
		saveRecord(term);
		return term;
	}

	@Nullable
	private static FlatrateTermStatus statusOf(@NonNull final I_C_Flatrate_Term term)
	{
		refresh(term);
		return FlatrateTermStatus.ofNullableCode(term.getContractStatus());
	}

	@Nested
	class beforeComplete
	{
		@Test
		void futureStart_waiting()
		{
			setToday("2026-07-01");
			final I_C_Flatrate_Term term = term().docStatus(DocStatus.Drafted).startDate("2026-07-02").endDate("2026-12-31").create();

			service.setDefaultsBeforeComplete(term);

			assertThat(term.getContractStatus()).isEqualTo(FlatrateTermStatus.Waiting.getCode());
		}

		@Test
		void startToday_running()
		{
			setToday("2026-07-01");
			final I_C_Flatrate_Term term = term().docStatus(DocStatus.Drafted).startDate("2026-07-01").endDate("2026-12-31").create();

			service.setDefaultsBeforeComplete(term);

			assertThat(term.getContractStatus()).isEqualTo(FlatrateTermStatus.Running.getCode());
		}

		@Test
		void quitStatus_kept()
		{
			setToday("2026-07-01");
			final I_C_Flatrate_Term term = term().docStatus(DocStatus.Drafted).startDate("2026-07-02").endDate("2026-12-31").contractStatus(FlatrateTermStatus.Quit).create();

			service.setDefaultsBeforeComplete(term);

			assertThat(term.getContractStatus()).isEqualTo(FlatrateTermStatus.Quit.getCode());
		}

		@Test
		void dateContracted_isTheCreationDay()
		{
			setToday("2026-06-10");
			final I_C_Flatrate_Term term = term().docStatus(DocStatus.Drafted).startDate("2026-07-01").endDate("2026-12-31").create();

			setToday("2026-06-20");
			service.setDefaultsBeforeComplete(term);

			assertThat(TimeUtil.asLocalDate(term.getDateContracted())).isEqualTo(LocalDate.parse("2026-06-10"));
		}

		@Test
		void dateContracted_enteredValueKept()
		{
			setToday("2026-06-10");
			final I_C_Flatrate_Term term = term().docStatus(DocStatus.Drafted).startDate("2026-07-01").endDate("2026-12-31").dateContracted("2026-05-05").create();

			service.setDefaultsBeforeComplete(term);

			assertThat(TimeUtil.asLocalDate(term.getDateContracted())).isEqualTo(LocalDate.parse("2026-05-05"));
		}

		@Test
		void masterStartDate_firstTerm_isItsStartDate()
		{
			setToday("2026-06-10");
			final I_C_Flatrate_Term term = term().docStatus(DocStatus.Drafted).startDate("2026-07-01").endDate("2026-12-31").create();

			service.setDefaultsBeforeComplete(term);

			assertThat(TimeUtil.asLocalDate(term.getMasterStartDate())).isEqualTo(LocalDate.parse("2026-07-01"));
		}

		@Test
		void masterStartDate_successor_isThePredecessorsMasterStartDate()
		{
			setToday("2026-12-15");
			final I_C_Flatrate_Term successor = term().docStatus(DocStatus.Drafted).startDate("2027-01-01").endDate("2027-12-31").create();
			term().startDate("2026-01-01").endDate("2026-12-31").masterStartDate("2025-01-01").next(successor).create();

			service.setDefaultsBeforeComplete(successor);

			assertThat(TimeUtil.asLocalDate(successor.getMasterStartDate())).isEqualTo(LocalDate.parse("2025-01-01"));
		}

		@Test
		void masterStartDate_successorOfPredecessorWithoutMasterStartDate_isItsOwnStartDate()
		{
			setToday("2026-12-15");
			final I_C_Flatrate_Term successor = term().docStatus(DocStatus.Drafted).startDate("2027-01-01").endDate("2027-12-31").create();
			term().startDate("2026-01-01").endDate("2026-12-31").next(successor).create();

			service.setDefaultsBeforeComplete(successor);

			assertThat(TimeUtil.asLocalDate(successor.getMasterStartDate())).isEqualTo(LocalDate.parse("2027-01-01"));
		}

		@Test
		void masterStartDate_enteredValueKept()
		{
			setToday("2026-06-10");
			final I_C_Flatrate_Term term = term().docStatus(DocStatus.Drafted).startDate("2026-07-01").endDate("2026-12-31").masterStartDate("2024-01-01").create();

			service.setDefaultsBeforeComplete(term);

			assertThat(TimeUtil.asLocalDate(term.getMasterStartDate())).isEqualTo(LocalDate.parse("2024-01-01"));
		}
	}

	@Nested
	class updateContractStatusOfCompletedTerms
	{
		@Test
		void appliesTheDailyRuleOnlyToCompletedCompensationGroupTerms()
		{
			setToday("2026-07-01");
			final I_C_Flatrate_Term startsToday = term().startDate("2026-07-01").endDate("2026-12-31").contractStatus(FlatrateTermStatus.Waiting).create();
			final I_C_Flatrate_Term startsTomorrow = term().startDate("2026-07-02").endDate("2026-12-31").contractStatus(FlatrateTermStatus.Waiting).create();
			final I_C_Flatrate_Term endedYesterday = term().startDate("2025-07-01").endDate("2026-06-30").contractStatus(FlatrateTermStatus.Running).create();
			final I_C_Flatrate_Term endsToday = term().startDate("2025-07-02").endDate("2026-07-01").contractStatus(FlatrateTermStatus.Running).create();
			final I_C_Flatrate_Term successor = term().startDate("2026-07-01").endDate("2027-06-30").contractStatus(FlatrateTermStatus.Running).create();
			final I_C_Flatrate_Term endedYesterdayExtended = term().startDate("2025-07-01").endDate("2026-06-30").contractStatus(FlatrateTermStatus.Running).next(successor).create();
			final I_C_Flatrate_Term quitEndedYesterday = term().startDate("2025-07-01").endDate("2026-06-30").contractStatus(FlatrateTermStatus.Quit).create();
			final I_C_Flatrate_Term draftStartsToday = term().docStatus(DocStatus.Drafted).startDate("2026-07-01").endDate("2026-12-31").contractStatus(FlatrateTermStatus.Waiting).create();
			final I_C_Flatrate_Term subscriptionEndedYesterday = term().typeConditions(TypeConditions.SUBSCRIPTION).startDate("2025-07-01").endDate("2026-06-30").contractStatus(FlatrateTermStatus.Running).create();

			final int updatedCount = service.updateContractStatusOfCompletedTerms();

			assertThat(statusOf(startsToday)).isEqualTo(FlatrateTermStatus.Running);
			assertThat(statusOf(startsTomorrow)).isEqualTo(FlatrateTermStatus.Waiting);
			assertThat(statusOf(endedYesterday)).isEqualTo(FlatrateTermStatus.EndingContract);
			assertThat(statusOf(endsToday)).isEqualTo(FlatrateTermStatus.Running);
			assertThat(statusOf(endedYesterdayExtended)).isEqualTo(FlatrateTermStatus.Running);
			assertThat(statusOf(successor)).isEqualTo(FlatrateTermStatus.Running);
			assertThat(statusOf(quitEndedYesterday)).isEqualTo(FlatrateTermStatus.Quit);
			assertThat(statusOf(draftStartsToday)).isEqualTo(FlatrateTermStatus.Waiting);
			assertThat(statusOf(subscriptionEndedYesterday)).isEqualTo(FlatrateTermStatus.Running);
			assertThat(updatedCount).isEqualTo(2);
		}

		@Test
		void oneFailingTerm_doesNotStopTheOthers()
		{
			setToday("2026-07-01");
			final I_C_Flatrate_Term endedBefore = term().startDate("2025-07-01").endDate("2026-06-30").contractStatus(FlatrateTermStatus.Running).create();
			final I_C_Flatrate_Term failing = term().startDate("2025-07-01").endDate("2026-06-30").contractStatus(FlatrateTermStatus.Running).create();
			failing.setStartDate(null); // corrupt data: the status rule throws for this term
			saveRecord(failing);
			final I_C_Flatrate_Term endedAfter = term().startDate("2025-07-01").endDate("2026-06-30").contractStatus(FlatrateTermStatus.Running).create();

			final int updatedCount = service.updateContractStatusOfCompletedTerms();

			assertThat(statusOf(endedBefore)).isEqualTo(FlatrateTermStatus.EndingContract);
			assertThat(statusOf(failing)).isEqualTo(FlatrateTermStatus.Running);
			assertThat(statusOf(endedAfter)).isEqualTo(FlatrateTermStatus.EndingContract);
			assertThat(updatedCount).isEqualTo(2);
		}
	}
}
