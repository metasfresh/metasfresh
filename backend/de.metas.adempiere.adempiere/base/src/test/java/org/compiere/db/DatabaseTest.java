package org.compiere.db;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.common.util.time.SystemTime;
import org.assertj.core.api.AbstractStringAssert;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest
{
	@BeforeEach
	void beforeEach()
	{
		SystemTime.resetTimeSource();
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	@Test
	public void test_convertDecimalPatternToPG()
	{
		// NOTE: "FM" means "fill mode (suppress padding blanks and trailing zeroes)"
		// see http://www.postgresql.org/docs/9.1/static/functions-formatting.html#FUNCTIONS-FORMATTING-NUMERIC-TABLE
		test_convertDecimalPatternToPG("00000", "FM00000");
		test_convertDecimalPatternToPG("#####", "FM99999");
		test_convertDecimalPatternToPG("00###", "FM00999");
	}

	private void test_convertDecimalPatternToPG(final String javaDecimalFormat, final String expectedPGFormat)
	{
		final String actualPGFormat = Database.convertDecimalPatternToPG(javaDecimalFormat);
		assertThat(actualPGFormat).as("Invalid converted PG format for java format: " + javaDecimalFormat).isEqualTo(expectedPGFormat);
	}

	private AbstractStringAssert<?> assert_TO_DATE(final Timestamp timestamp, boolean dayOnly)
	{
		return assertThat(Database.TO_DATE(timestamp, dayOnly))
				.as(() -> "TO_DATE('" + timestamp + "', dayOnly=" + dayOnly + ")");
	}

	@Nested
	class TO_DATE
	{
		@Test
		void null_dayOnlyFalse() {assert_TO_DATE(null, false).isEqualTo("current_date()");}

		@Test
		void null_dayOnlyTrue() {assert_TO_DATE(null, true).isEqualTo("current_date()");}

		@Test
		void ZonedDateTime_asTimestamp_dayOnlyTrue()
		{
			final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2023-12-04T23:59:59.000+00:00")
					.withZoneSameInstant(ZoneId.of("Etc/UTC"));
			final Timestamp time = TimeUtil.asTimestamp(zonedDateTime);

			// setting zoneId to Europe/Berlin
			SystemTime.setFixedTimeSource(ZonedDateTime.now(ZoneId.of("Europe/Berlin")));

			// dev-note: we expect 2023-12-05 as for column without time information, the values should always be in local time
			assert_TO_DATE(time, true).isEqualTo("'2023-12-05'::timestamp without time zone");
		}

		@Test
		void ZonedDateTime_asTimestamp_dayOnlyFalse()
		{
			final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2023-12-04T23:59:59.123456+00:00")
					.withZoneSameInstant(ZoneId.of("Etc/UTC"));
			final Timestamp time = TimeUtil.asTimestamp(zonedDateTime);

			assert_TO_DATE(time, false).isEqualTo("TO_TIMESTAMP('2023-12-04 23:59:59.123456','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC'");
		}

		@Test
		void ZonedDateTime_asTimestamp_differentTimeZone_dayOnlyFalse()
		{
			final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2023-12-04T23:59:59.000+01:00")
					.withZoneSameInstant(ZoneId.of("Europe/Berlin"));
			final Timestamp time = TimeUtil.asTimestamp(zonedDateTime);

			assert_TO_DATE(time, false).isEqualTo("TO_TIMESTAMP('2023-12-04 22:59:59.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC'");
		}

		@Test
		void Instant_asTimestamp_dayOnlyFalse()
		{
			final Instant instant = Instant.parse("2020-12-12T23:59:00.00Z");
			final Timestamp time = TimeUtil.asTimestamp(instant);

			assert_TO_DATE(time, false).isEqualTo("TO_TIMESTAMP('2020-12-12 23:59:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC'");
		}
	}

	@ParameterizedTest(name = "SystemTime.zoneId={0}")
	@ValueSource(strings = { "UTC-9", "UTC-5", "UTC-1", "UTC", "Europe/Berlin", "Europe/Bucharest", "UTC+5", "UTC+9" })
	void parseLocalDateAsTimestamp_TO_DATE_dayOnlyTrue(final String timezone)
	{
		SystemTime.setFixedTimeSource(ZonedDateTime.now(ZoneId.of(timezone)));

		final Timestamp timestamp = TimeUtil.parseLocalDateAsTimestamp("2022-03-23");
		assert_TO_DATE(timestamp, true).isEqualTo("'2022-03-23'::timestamp without time zone");
	}

}
