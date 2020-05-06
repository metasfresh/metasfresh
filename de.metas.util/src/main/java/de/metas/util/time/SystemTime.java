package de.metas.util.time;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.util
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Code taken from the book "Test Driven", Chapter 7 ("Test-driving the
 * unpredictable") by Lasse Koskela.
 *
 * @author ts
 */
@UtilityClass
public class SystemTime
{
	private static final TimeSource defaultTimeSource = new SystemTimeSource();

	private TimeSource timeSource;

	public long millis()
	{
		return getTimeSource().millis();
	}

	/**
	 * For WEBUI/Json please use de.metas.ui.web.window.datatypes.json.DateTimeConverters#toJson(java.time.ZonedDateTime, java.time.ZoneId)
	 * For NON webui please use use de.metas.organization.IOrgDAO.getTimeZone(OrgId)
	 */
	public ZoneId zoneId()
	{
		return getTimeSource().zoneId();
	}

	public GregorianCalendar asGregorianCalendar()
	{

		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(millis());

		return cal;
	}

	public Date asDate()
	{
		return new Date(millis());
	}

	public Timestamp asTimestamp()
	{

		return new Timestamp(millis());
	}

	public Instant asInstant()
	{
		return Instant.ofEpochMilli(millis());
	}

	public LocalDateTime asLocalDateTime()
	{
		return asZonedDateTime().toLocalDateTime();
	}

	@NonNull
	public LocalDate asLocalDate()
	{
		return asZonedDateTime().toLocalDate();
	}

	public ZonedDateTime asZonedDateTime()
	{
		return asZonedDateTime(zoneId());
	}

	public ZonedDateTime asZonedDateTimeAtStartOfDay()
	{
		return asZonedDateTime(zoneId()).truncatedTo(ChronoUnit.DAYS);
	}

	public ZonedDateTime asZonedDateTime(@NonNull final ZoneId zoneId)
	{
		return asInstant().atZone(zoneId);
	}

	/**
	 * Same as {@link #asTimestamp()} but the returned date will be truncated to DAY.
	 */
	public Timestamp asDayTimestamp()
	{
		final GregorianCalendar cal = asGregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * "Why not go with {@link #asDayTimestamp()}" you ask?
	 * See https://stackoverflow.com/questions/8929242/compare-date-object-with-a-timestamp-in-java
	 */
	public Date asDayDate()
	{
		final GregorianCalendar cal = asGregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());
	}

	private TimeSource getTimeSource()
	{
		return timeSource == null ? defaultTimeSource : timeSource;
	}

	/**
	 * After invocation of this method, the time returned will be the system
	 * time again.
	 */
	public void resetTimeSource()
	{
		timeSource = null;
	}

	/**
	 * @param newTimeSource the given TimeSource will be used for the time returned by the
	 *            methods of this class (unless it is null).
	 */
	public void setTimeSource(@Nullable final TimeSource newTimeSource)
	{
		timeSource = newTimeSource;
	}

	private static final class SystemTimeSource implements TimeSource
	{
		@Override
		public long millis()
		{
			return System.currentTimeMillis();
		}

		@Override
		public ZoneId zoneId()
		{
			return ZoneId.systemDefault();
		}
	}
}
