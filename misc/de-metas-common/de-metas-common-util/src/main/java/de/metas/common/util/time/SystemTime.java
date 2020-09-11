package de.metas.common.util.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class SystemTime
{
	private static final TimeSource defaultTimeSource = new SystemTimeSource();

	private TimeSource timeSource;

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
	public void setTimeSource(@NonNull final TimeSource newTimeSource)
	{
		timeSource = newTimeSource;
	}

	public void setFixedTimeSource(@NonNull final ZonedDateTime date)
	{
		setTimeSource(FixedTimeSource.ofZonedDateTime(date));
	}

	public long millis()
	{
		return getTimeSource().millis();
	}

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
}
