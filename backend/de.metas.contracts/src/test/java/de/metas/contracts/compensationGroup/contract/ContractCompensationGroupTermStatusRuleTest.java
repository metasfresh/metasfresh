package de.metas.contracts.compensationGroup.contract;

import de.metas.contracts.FlatrateTermStatus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDate;

import static de.metas.contracts.compensationGroup.contract.ContractCompensationGroupTermStatusRule.computeStatusOnComplete;
import static de.metas.contracts.compensationGroup.contract.ContractCompensationGroupTermStatusRule.computeStatusUpdate;
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

class ContractCompensationGroupTermStatusRuleTest
{
	private static final LocalDate TODAY = LocalDate.parse("2026-07-01");
	private static final LocalDate YESTERDAY = TODAY.minusDays(1);
	private static final LocalDate TOMORROW = TODAY.plusDays(1);

	@Nested
	class onComplete
	{
		@Test
		void noStatus_startTomorrow_waiting()
		{
			assertThat(computeStatusOnComplete(null, TOMORROW, TODAY)).contains(FlatrateTermStatus.Waiting);
		}

		@Test
		void noStatus_startToday_running()
		{
			assertThat(computeStatusOnComplete(null, TODAY, TODAY)).contains(FlatrateTermStatus.Running);
		}

		@Test
		void noStatus_startYesterday_running()
		{
			assertThat(computeStatusOnComplete(null, YESTERDAY, TODAY)).contains(FlatrateTermStatus.Running);
		}

		@ParameterizedTest
		@EnumSource(FlatrateTermStatus.class)
		void existingStatus_isNeverOverwritten(final FlatrateTermStatus existingStatus)
		{
			assertThat(computeStatusOnComplete(existingStatus, TOMORROW, TODAY)).isEmpty();
			assertThat(computeStatusOnComplete(existingStatus, YESTERDAY, TODAY)).isEmpty();
		}
	}

	@Nested
	class dailyUpdate
	{
		@Test
		void waiting_startTomorrow_unchanged()
		{
			assertThat(computeStatusUpdate(FlatrateTermStatus.Waiting, TOMORROW, TOMORROW.plusYears(1), false, TODAY)).isEmpty();
		}

		@Test
		void waiting_startToday_running()
		{
			assertThat(computeStatusUpdate(FlatrateTermStatus.Waiting, TODAY, TODAY.plusYears(1), false, TODAY)).contains(FlatrateTermStatus.Running);
		}

		@Test
		void running_endToday_unchanged()
		{
			assertThat(computeStatusUpdate(FlatrateTermStatus.Running, TODAY.minusYears(1), TODAY, false, TODAY)).isEmpty();
		}

		@Test
		void running_endYesterday_notExtended_endingContract()
		{
			assertThat(computeStatusUpdate(FlatrateTermStatus.Running, TODAY.minusYears(1), YESTERDAY, false, TODAY)).contains(FlatrateTermStatus.EndingContract);
		}

		@Test
		void running_endYesterday_extended_unchanged()
		{
			assertThat(computeStatusUpdate(FlatrateTermStatus.Running, TODAY.minusYears(1), YESTERDAY, true, TODAY)).isEmpty();
		}

		@Test
		void running_noEndDate_unchanged()
		{
			assertThat(computeStatusUpdate(FlatrateTermStatus.Running, TODAY.minusYears(1), null, false, TODAY)).isEmpty();
		}

		@Test
		void waiting_whoseWholePeriodLiesInThePast_notExtended_endingContract()
		{
			assertThat(computeStatusUpdate(FlatrateTermStatus.Waiting, TODAY.minusYears(1), YESTERDAY, false, TODAY)).contains(FlatrateTermStatus.EndingContract);
		}

		@ParameterizedTest
		@EnumSource(value = FlatrateTermStatus.class, names = { "Quit", "Voided", "EndingContract", "DeliveryPause", "Info" })
		void otherStatuses_untouched(final FlatrateTermStatus status)
		{
			assertThat(computeStatusUpdate(status, TODAY.minusYears(1), YESTERDAY, false, TODAY)).isEmpty();
		}

		@Test
		void noStatus_untouched()
		{
			assertThat(computeStatusUpdate(null, TODAY.minusYears(1), YESTERDAY, false, TODAY)).isEmpty();
		}
	}
}
