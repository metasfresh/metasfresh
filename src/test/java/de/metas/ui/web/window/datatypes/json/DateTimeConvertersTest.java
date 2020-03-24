/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.window.datatypes.json;

import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DateTimeConvertersTest
{
	private AbstractCharSequenceAssert<?, String> assertLocalDateToJson(final LocalDate localDate, final JSONDateConfig config)
	{
		final String json = DateTimeConverters.toJson(localDate, config);
		return assertThat(json)
				.as("localDate=" + localDate + ", config=" + config);
	}

	private AbstractCharSequenceAssert<?, String> assertLocalTimeToJson(final LocalTime localTime, final JSONDateConfig config)
	{
		final String json = DateTimeConverters.toJson(localTime, config);
		return assertThat(json)
				.as("zonedDateTime=" + localTime + ", config=" + config);
	}

	private AbstractCharSequenceAssert<?, String> assertZonedDateTimeToJson(final ZonedDateTime zonedDateTime, final ZoneId zoneId, final JSONDateConfig config)
	{
		final String json = DateTimeConverters.toJson(zonedDateTime, zoneId, config);
		return assertThat(json)
				.as("zonedDateTime=" + zonedDateTime + ", zoneId=" + zoneId + ", config=" + config);
	}

	private AbstractCharSequenceAssert<?, String> assertInstantToJson(final Instant instant, final ZoneId zoneId, final JSONDateConfig config)
	{
		final String json = DateTimeConverters.toJson(instant, zoneId, config);
		return assertThat(json)
				.as("instant=" + instant + ", zoneId=" + zoneId + ", config=" + config);
	}

	@Test
	public void testDefaultFormatters()
	{
		final JSONDateConfig config = JSONDateConfig.DEFAULT;

		assertLocalDateToJson(LocalDate.of(2019, 03, 05), config)
				.isEqualTo("2019-03-05");

		assertLocalTimeToJson(LocalTime.of(13, 14, 15, 16), config)
				.isEqualTo("13:14");

		final ZonedDateTime zonedDateTime = LocalDate.of(2019, 03, 05)
				.atTime(3, 14, 15, 16)
				.atZone(ZoneId.of("UTC-9"));
		assertZonedDateTimeToJson(zonedDateTime, zonedDateTime.getZone(), config)
				// NOTE: nanos are not considered
				.isEqualTo("2019-03-05T03:14:15.000-09:00");

		assertInstantToJson(zonedDateTime.toInstant(), zonedDateTime.getZone(), config)
				// NOTE: nanos are not considered
				.isEqualTo("2019-03-05T03:14:15.000-09:00");
	}

	@Test
	public void toJson_zoneId()
	{
		assertZoneIdToJson(ZoneId.of("UTC-9")).isEqualTo("UTC-09:00");
		assertZoneIdToJson(ZoneId.of("Europe/Berlin")).isEqualTo("Europe/Berlin");
		assertZoneIdToJson(ZoneId.of("Europe/Bucharest")).isEqualTo("Europe/Bucharest");
	}

	private AbstractCharSequenceAssert<?, String> assertZoneIdToJson(final ZoneId zoneId)
	{
		final String json = DateTimeConverters.toJson(zoneId);
		return assertThat(json)
				.as("zoneId=" + zoneId);
	}

	@Nested
	public class fromObjectToLocalDate
	{
		@Test
		public void from_StringLookupValue()
		{
			assertThat(DateTimeConverters.fromObjectToLocalDate(StringLookupValue.of("2019-02-11", "bla bla")))
					.isEqualTo(LocalDate.of(2019, Month.FEBRUARY, 11));
		}
	}

	@Nested
	class Legacy_FromUserQuery
	{
		@Test
		void jdbcTimestampToLocalDate()
		{
			final String jdbcTimestamp = "2016-06-11 00:00:00.0";
			assertThat(Timestamp.valueOf(jdbcTimestamp).toString()).isEqualTo("2016-06-11 00:00:00.0");

			final DocumentFieldWidgetType widgetType = DocumentFieldWidgetType.LocalDate;
			final Object valueDate = DateTimeConverters.fromObject(jdbcTimestamp, widgetType);
			assertThat(valueDate).isEqualTo(LocalDate.of(2016,6,11));
		}

	}
}
