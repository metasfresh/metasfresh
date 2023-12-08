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
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DatabaseTest
{
	@Test
	public void test_convertDecimalPatternToPG()
	{
		// NOTE: "FM" means "fill mode (suppress padding blanks and trailing zeroes)"
		// see http://www.postgresql.org/docs/9.1/static/functions-formatting.html#FUNCTIONS-FORMATTING-NUMERIC-TABLE
		test_convertDecimalPatternToPG("00000", "FM00000");
		test_convertDecimalPatternToPG("#####", "FM99999");
		test_convertDecimalPatternToPG("00###", "FM00999");
	}

	@Test
	public void test_TO_DATE_null_time_dayOnly_false()
	{
		final String dateString = Database.TO_DATE(null, false);
		Assert.assertEquals("current_date()", dateString);
	}

	@Test
	public void test_TO_DATE_null_time_dayOnly_true()
	{
		final String dateString = Database.TO_DATE(null, true);
		Assert.assertEquals("current_date()", dateString);
	}

	@Test
	public void test_TO_DATE_starting_from_Zoned_Date_Time_dayOnly_true()
	{
		final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2023-12-04T23:59:59.000+00:00")
				.withZoneSameInstant(ZoneId.of("Etc/UTC"));

		final Timestamp time = TimeUtil.asTimestamp(zonedDateTime);
		// setting zoneId to Europe/Berlin
		SystemTime.setFixedTimeSource(ZonedDateTime.now(ZoneId.of("Europe/Berlin")));
		final String dateString = Database.TO_DATE(time, true);
		// dev-note: we expect 2023-12-05 as for column without time information, the values should always be in local time
		Assert.assertEquals("TO_TIMESTAMP('2023-12-05','YYYY-MM-DD')", dateString);
	}

	@Test
	public void test_TO_DATE_starting_from_Zoned_Date_Time_dayOnly_false()
	{
		final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2023-12-04T23:59:59.123456+00:00")
				.withZoneSameInstant(ZoneId.of("Etc/UTC"));

		final Timestamp time = TimeUtil.asTimestamp(zonedDateTime.toInstant());
		final String dateString = Database.TO_DATE(time, false);
		Assert.assertEquals("TO_TIMESTAMP('2023-12-04 23:59:59.123456','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC'", dateString);
	}

	@Test
	public void test_TO_DATE_starting_from_Zoned_Date_Time_different_timeZone_dayOnly_false()
	{
		final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2023-12-04T23:59:59.000+01:00")
				.withZoneSameInstant(ZoneId.of("Europe/Berlin"));

		final Timestamp time = TimeUtil.asTimestamp(zonedDateTime.toInstant());
		final String dateString = Database.TO_DATE(time, false);
		Assert.assertEquals("TO_TIMESTAMP('2023-12-04 22:59:59.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC'", dateString);
	}

	@Test
	public void test_TO_DATE_starting_from_Instant_dayOnly_false()
	{
		final Instant instant = Instant.parse("2020-12-12T23:59:00.00Z");

		final Timestamp time = TimeUtil.asTimestamp(instant);
		final String dateString = Database.TO_DATE(time, false);
		Assert.assertEquals("TO_TIMESTAMP('2020-12-12 23:59:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC'", dateString);
	}

	private void test_convertDecimalPatternToPG(final String javaDecimalFormat, final String expectedPGFormat)
	{
		final String actualPGFormat = Database.convertDecimalPatternToPG(javaDecimalFormat);
		Assert.assertEquals("Invalid converted PG format for java format: " + javaDecimalFormat, expectedPGFormat, actualPGFormat);
	}
}
