package de.metas.calendar;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class BusinessDayMatcherTest
{
	private ExcludeWeekendBusinessDayMatcher businessDayMatcher;

	@Test
	public void test_calculateBusinessDaysBetween()
	{
		businessDayMatcher = ExcludeWeekendBusinessDayMatcher.DEFAULT;

		assertBusinessDaysBetween(0, "2018-07-07", "2018-07-08"); // Saturday -> Sunday

		assertBusinessDaysBetween(5, "2018-07-02", "2018-07-07");
		assertBusinessDaysBetween(5, "2018-07-02", "2018-07-08");
		assertBusinessDaysBetween(5, "2018-07-02", "2018-07-09");
	}

	private void assertBusinessDaysBetween(final int expectedDays, final String fromStr, final String toStr)
	{
		final LocalDate from = LocalDate.parse(fromStr);
		final LocalDate to = LocalDate.parse(toStr);
		final int days = businessDayMatcher.calculateBusinessDaysBetween(from, to);
		assertThat(days).isEqualTo(expectedDays);
	}

	@Test
	public void test_getPreviousBusinessDay_withTargetWorkingDays()
	{
		this.businessDayMatcher = ExcludeWeekendBusinessDayMatcher.DEFAULT;

		assertPreviousWorkingDay("2018-07-06", 0, "2018-07-06");
		assertPreviousWorkingDay("2018-07-07", 0, "2018-07-06");
		assertPreviousWorkingDay("2018-07-08", 0, "2018-07-06");

		assertPreviousWorkingDay("2018-07-06", 5, "2018-06-29");
		assertPreviousWorkingDay("2018-07-07", 5, "2018-06-29");
		assertPreviousWorkingDay("2018-07-08", 5, "2018-06-29");

		assertPreviousWorkingDay("2018-07-06", 6, "2018-06-28");
		assertPreviousWorkingDay("2018-07-07", 6, "2018-06-28");
		assertPreviousWorkingDay("2018-07-08", 6, "2018-06-28");
	}

	private void assertPreviousWorkingDay(final String dateStr, final int targetWorkingDays, final String expectedPreviousDateStr)
	{
		final LocalDate date = LocalDate.parse(dateStr);
		final LocalDate expectedPreviousDate = LocalDate.parse(expectedPreviousDateStr);

		assertThat(businessDayMatcher.getPreviousBusinessDay(date, targetWorkingDays))
				.isEqualTo(expectedPreviousDate);

		//
		if (targetWorkingDays == 0)
		{
			assertThat(businessDayMatcher.getPreviousBusinessDay(date)).isEqualTo(expectedPreviousDate);
		}
	}
}
