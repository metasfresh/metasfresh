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
import java.time.format.DateTimeFormatter;
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
	 *                      <p>
	 *                      When the literal carries both a region zone (e.g. {@code [Europe/Berlin]})
	 *                      and an explicit UTC offset, the <b>zone is authoritative</b>: see
	 *                      {@link #parseZoneAuthoritative(String)}.
	 */
	public static void setFixedTimeSource(@NonNull final String zonedDateTime)
	{
		setTimeSource(FixedTimeSource.ofZonedDateTime(parseZoneAuthoritative(zonedDateTime)));
	}

	/**
	 * Parses an ISO-8601 zoned date-time with the <b>region zone as the authority</b>: the literal's
	 * wall-clock local date-time is resolved against that zone's own DST rules, and an explicit UTC
	 * offset in the literal is ignored when it contradicts the zone at that instant.
	 * <p>
	 * This is deliberately different from {@link ZonedDateTime#parse(CharSequence)}, which treats the
	 * explicit offset as authoritative and merely relabels the zone. That matters only for a literal
	 * whose offset is wrong for its region/season, e.g. {@code 2024-09-20T08:00:00+01:00[Europe/Berlin]}
	 * (a summer date carrying Berlin's winter offset +01:00 instead of CEST +02:00):
	 * {@code ZonedDateTime.parse} yields 07:00Z, whereas resolving zone-first yields the intended
	 * 06:00Z (08:00 Berlin wall-clock). Older JDK builds resolved such literals zone-first; JDK 21/25
	 * (and recent 17.0.x) switched to offset-first. Our test fixtures encode wall-clock intent with a
	 * (sometimes stale) offset, so we pin the zone-first meaning here to stay JDK-independent.
	 * <p>
	 * Literals with no region zone (offset-only, or {@code Z}) and literals whose offset is correct for
	 * the zone are unaffected -- they resolve to exactly the same instant either way.
	 */
	private static ZonedDateTime parseZoneAuthoritative(@NonNull final String zonedDateTime)
	{
		final ZoneId zone = ZonedDateTime.parse(zonedDateTime).getZone();
		final LocalDateTime localDateTime = LocalDateTime.parse(zonedDateTime, DateTimeFormatter.ISO_DATE_TIME);
		return localDateTime.atZone(zone);
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
