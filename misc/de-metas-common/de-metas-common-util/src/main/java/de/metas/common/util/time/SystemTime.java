package de.metas.common.util.time;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

public class SystemTime
{
	private static final TimeSource defaultTimeSource = new SystemTimeSource();

	@Nullable
	private static TimeSource timeSource;

	private static TimeSource getTimeSource()
	{
		return timeSource == null ? defaultTimeSource : timeSource;
	}

	/**
	 * After invocation of this method, the time returned will be the system time again.
	 */
	public static void resetTimeSource()
	{
		timeSource = null;
	}

	/**
	 * @param newTimeSource the given TimeSource will be used for the time returned by the
	 *                      methods of this class (unless it is null).
	 */
	public static void setTimeSource(@NonNull final TimeSource newTimeSource)
	{
		timeSource = newTimeSource;
	}

	public static void setFixedTimeSource(@NonNull final ZonedDateTime date)
	{
		setTimeSource(FixedTimeSource.ofZonedDateTime(date));
	}

	/**
	 * @param zonedDateTime ISO 8601 date time format (see {@link ZonedDateTime#parse(CharSequence)}).
	 *                      e.g. 2018-02-28T13:13:13+01:00[Europe/Berlin]
	 */
	public static void setFixedTimeSource(@NonNull final String zonedDateTime)
	{
		setTimeSource(FixedTimeSource.ofZonedDateTime(ZonedDateTime.parse(zonedDateTime)));
	}

	public static long millis()
	{
		return getTimeSource().millis();
	}

	public static ZoneId zoneId()
	{
		return getTimeSource().zoneId();
	}

	public static GregorianCalendar asGregorianCalendar()
	{
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(millis());

		return cal;
	}

	public static Date asDate()
	{
		return new Date(millis());
	}

	public static Timestamp asTimestamp()
	{
		return new Timestamp(millis());
	}

	/**
	 * Same as {@link #asTimestamp()} but the returned date will be truncated to DAY.
	 */
	public static Timestamp asDayTimestamp()
	{
		final GregorianCalendar cal = asGregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static Instant asInstant()
	{
		return Instant.ofEpochMilli(millis());
	}

	public static LocalDateTime asLocalDateTime()
	{
		return asZonedDateTime().toLocalDateTime();
	}

	@NonNull
	public static LocalDate asLocalDate()
	{
		return asLocalDate(zoneId());
	}

	@NonNull
	public static LocalDate asLocalDate(@NonNull final ZoneId zoneId)
	{
		return asZonedDateTime(zoneId).toLocalDate();
	}

	public static ZonedDateTime asZonedDateTime()
	{
		return asZonedDateTime(zoneId());
	}

	public static ZonedDateTime asZonedDateTimeAtStartOfDay()
	{
		return asZonedDateTime(zoneId()).truncatedTo(ChronoUnit.DAYS);
	}

	public static ZonedDateTime asZonedDateTimeAtEndOfDay(@NonNull final ZoneId zoneId)
	{
		return asZonedDateTime(zoneId)
				.toLocalDate()
				.atTime(LocalTime.MAX)
				.atZone(zoneId);
	}

	public static ZonedDateTime asZonedDateTime(@NonNull final ZoneId zoneId)
	{
		return asInstant().atZone(zoneId);
	}
}
