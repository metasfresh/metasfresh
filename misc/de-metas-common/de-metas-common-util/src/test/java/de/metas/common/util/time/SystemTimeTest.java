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
import java.time.Instant;
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

	/**
	 * {@link SystemTime#setFixedTimeSource(String)} must interpret a zoned literal
	 * <b>zone-authoritatively</b>: the wall-clock local time is resolved against the region zone's
	 * own DST rules, so a redundant explicit offset that is wrong for that zone/season does NOT
	 * shift the frozen instant.
	 * <p>
	 * Concrete failure this guards: hundreds of cucumber fixtures freeze the clock with literals such
	 * as {@code 2024-09-20T08:00:00+01:00[Europe/Berlin]} -- a summer date carrying Berlin's WINTER
	 * offset (+01:00) instead of the actual CEST +02:00. Older JDK builds resolved such a literal
	 * zone-first (08:00 Berlin CEST = 06:00Z); JDK 21/25 (and recent 17.0.x) keep the stated offset
	 * (09:00 Berlin = 07:00Z), shifting every derived record (M_Transaction, MD_Candidate, ...) by one
	 * hour and breaking the materialDispo/ATP assertions. Resolving zone-first keeps the fixtures'
	 * intended wall-clock meaning across JDKs.
	 */
	@Test
	public void setFixedTimeSource_string_isZoneAuthoritative()
	{
		// summer literal carrying Berlin's (wrong) winter offset -> the zone wins: 08:00 CEST = 06:00Z
		SystemTime.setFixedTimeSource("2024-09-20T08:00:00+01:00[Europe/Berlin]");
		assertThat(Instant.ofEpochMilli(SystemTime.millis()))
				.isEqualTo(Instant.parse("2024-09-20T06:00:00Z"));

		// April literal with the same mismatch -> 08:00 CEST = 06:00Z
		SystemTime.setFixedTimeSource("2021-04-11T08:00:00+01:00[Europe/Berlin]");
		assertThat(Instant.ofEpochMilli(SystemTime.millis()))
				.isEqualTo(Instant.parse("2021-04-11T06:00:00Z"));

		// a correct winter offset stays untouched: 08:00 CET = 07:00Z
		SystemTime.setFixedTimeSource("2022-01-15T08:00:00+01:00[Europe/Berlin]");
		assertThat(Instant.ofEpochMilli(SystemTime.millis()))
				.isEqualTo(Instant.parse("2022-01-15T07:00:00Z"));

		// a correct summer offset stays untouched: 13:30:13 CEST = 11:30:13Z
		SystemTime.setFixedTimeSource("2022-05-17T13:30:13+02:00[Europe/Berlin]");
		assertThat(Instant.ofEpochMilli(SystemTime.millis()))
				.isEqualTo(Instant.parse("2022-05-17T11:30:13Z"));

		// a plain UTC literal stays untouched
		SystemTime.setFixedTimeSource("2021-04-11T06:00:00Z");
		assertThat(Instant.ofEpochMilli(SystemTime.millis()))
				.isEqualTo(Instant.parse("2021-04-11T06:00:00Z"));

		// an offset-only literal (no region) stays untouched
		SystemTime.setFixedTimeSource("2021-04-14T08:00:00+00:00");
		assertThat(Instant.ofEpochMilli(SystemTime.millis()))
				.isEqualTo(Instant.parse("2021-04-14T08:00:00Z"));
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