package de.metas.calendar;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

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
	@Test
	public void test_calculateBusinessDaysBetween()
	{
		assertBusinessDaysBetween(0, LocalDate.of(2018, Month.JULY, 7), LocalDate.of(2018, Month.JULY, 8)); // Saturday -> Sunday

		assertBusinessDaysBetween(5, LocalDate.of(2018, Month.JULY, 2), LocalDate.of(2018, Month.JULY, 7));
		assertBusinessDaysBetween(5, LocalDate.of(2018, Month.JULY, 2), LocalDate.of(2018, Month.JULY, 8));
		assertBusinessDaysBetween(5, LocalDate.of(2018, Month.JULY, 2), LocalDate.of(2018, Month.JULY, 9));
	}

	private void assertBusinessDaysBetween(final int expectedDays, final LocalDate from, final LocalDate to)
	{
		final ExcludeWeekendBusinessDayMatcher businessDayMatcher = ExcludeWeekendBusinessDayMatcher.DEFAULT;
		final int days = businessDayMatcher.calculateBusinessDaysBetween(from, to);
		assertThat(days).isEqualTo(expectedDays);
	}

}
