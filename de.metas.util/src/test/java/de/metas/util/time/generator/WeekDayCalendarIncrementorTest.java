package de.metas.util.time.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import de.metas.util.time.generator.WeekDayCalendarIncrementor;

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

public class WeekDayCalendarIncrementorTest
{
	@Test
	public void test_increment_1week_monday()
	{
		final WeekDayCalendarIncrementor incrementor = new WeekDayCalendarIncrementor(1, DayOfWeek.MONDAY);
		assertThat(incrementor.increment(may2018(6))).isEqualTo(may2018(7));
		assertThat(incrementor.increment(may2018(7))).isEqualTo(may2018(14));
		assertThat(incrementor.increment(may2018(8))).isEqualTo(may2018(14));
	}

	@Test
	public void test_decrement_1week_monday()
	{
		final WeekDayCalendarIncrementor incrementor = new WeekDayCalendarIncrementor(1, DayOfWeek.MONDAY);
		assertThat(incrementor.decrement(may2018(15))).isEqualTo(may2018(14));
		assertThat(incrementor.decrement(may2018(14))).isEqualTo(may2018(7));
		assertThat(incrementor.decrement(may2018(13))).isEqualTo(may2018(7));
	}

	private static final LocalDateTime may2018(final int dayOfMonth)
	{
		return LocalDateTime.of(2018, Month.MAY, dayOfMonth, 0, 0);
	}

}
