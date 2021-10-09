/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.util.time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

class SystemTimeTest
{
	@BeforeEach
	@AfterEach
	public void resetTimeSource()
	{
		// make sure we are not leaving this dirty for other tests
		SystemTime.resetTimeSource();
	}

	@Test
	public void test_asDayTimestamp()
	{
		SystemTime.setFixedTimeSource("2014-04-01T10:11:12+01:00");

		final Timestamp dayTS = SystemTime.asDayTimestamp();

		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(dayTS.getTime());

		assertThat(cal.get(Calendar.YEAR)).isEqualTo(2014);
		assertThat(cal.get(Calendar.MONTH)).isEqualTo(4 - 1);
		assertThat(cal.get(Calendar.DAY_OF_MONTH)).isEqualTo(1);
		assertThat(cal.get(Calendar.HOUR_OF_DAY)).isEqualTo(0);
		assertThat(cal.get(Calendar.MINUTE)).isEqualTo(0);
		assertThat(cal.get(Calendar.SECOND)).isEqualTo(0);
		assertThat(cal.get(Calendar.MILLISECOND)).isEqualTo(0);
	}

	@Test
	public void test_asZonedDateTimeAtStartOfDay()
	{
		SystemTime.setFixedTimeSource("2014-04-01T10:11:12+01:00");

		final ZonedDateTime dayTS = SystemTime.asZonedDateTimeAtStartOfDay();
		assertThat(dayTS.getYear()).isEqualTo(2014);
		assertThat(dayTS.getMonth()).isEqualTo(Month.APRIL);
		assertThat(dayTS.getDayOfMonth()).isEqualTo(1);
		assertThat(dayTS.getHour()).isEqualTo(0);
		assertThat(dayTS.getMinute()).isEqualTo(0);
		assertThat(dayTS.getSecond()).isEqualTo(0);
		assertThat(dayTS.getNano()).isEqualTo(0);
	}

	@Test
	public void test_2359()
	{
		SystemTime.setTimeSource(
				FixedTimeSource.ofZonedDateTime(LocalDate.of(2020, Month.JULY, 1)
						.atTime(LocalTime.of(18, 12))
						.atZone(ZoneId.of("UTC+2"))));

		final ZonedDateTime todayEndOfDay = SystemTime.asLocalDate().atTime(LocalTime.MAX).atZone(SystemTime.zoneId());

		assertThat(todayEndOfDay.getYear()).isEqualTo(2020);
		assertThat(todayEndOfDay.getMonth()).isEqualTo(Month.JULY);
		assertThat(todayEndOfDay.getDayOfMonth()).isEqualTo(1);
		assertThat(todayEndOfDay.getHour()).isEqualTo(23);
		assertThat(todayEndOfDay.getMinute()).isEqualTo(59);
		assertThat(todayEndOfDay.getSecond()).isEqualTo(59);
	}
}