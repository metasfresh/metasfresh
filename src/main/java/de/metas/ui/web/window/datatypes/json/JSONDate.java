package de.metas.ui.web.window.datatypes.json;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.time.SimpleDateFormatThreadLocal;
import de.metas.util.time.SystemTime;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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
public final class JSONDate
{
	private static final transient Logger logger = LogManager.getLogger(JSONDate.class);

	public static final String DATE_PATTEN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(JSONDate.DATE_PATTEN);

	private static final SimpleDateFormatThreadLocal TIMEZONE_FORMAT = new SimpleDateFormatThreadLocal("XXX");

	private static final LocalDate DATE_1970_01_01 = LocalDate.of(1970, Month.JANUARY, 1);
	private static ZoneId fixedSystemZoneId = null; // used for testing

	private static ZoneId getSystemZoneId()
	{
		final ZoneId fixedSystemZoneId = JSONDate.fixedSystemZoneId;
		if (fixedSystemZoneId != null)
		{
			return fixedSystemZoneId;
		}

		return ZoneId.systemDefault();
	}

	@VisibleForTesting
	public synchronized static <T> T withFixedSystemZoneId(@NonNull final ZoneId zoneId, @NonNull final Callable<T> callable)
	{
		final ZoneId fixedSystemZoneIdBackup = fixedSystemZoneId;
		fixedSystemZoneId = zoneId;
		try
		{
			return callable.call();
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
		finally
		{
			fixedSystemZoneId = fixedSystemZoneIdBackup;
		}
	}

	public static String toJson(final Date date)
	{
		return toJson(date.getTime());
	}

	public static String toJson(final LocalDate date)
	{
		return toJson(date.atStartOfDay(getSystemZoneId()));
	}

	public static String toJson(final LocalDateTime date)
	{
		return toJson(date.atZone(getSystemZoneId()));
	}

	public static String toJson(final long millis)
	{
		final ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), getSystemZoneId());
		return toJson(zdt);
	}

	public static String toJson(final LocalTime time)
	{
		return toJson(time.atDate(DATE_1970_01_01).atZone(getSystemZoneId()));
	}

	public static String toJson(final ZonedDateTime date)
	{
		return DATE_FORMAT.format(date);
	}

	public static Date fromJson(final String valueStr, final DocumentFieldWidgetType widgetType)
	{
		if (widgetType == DocumentFieldWidgetType.Date)
		{
			return toJULDate(valueStr);
		}
		else
		{
			return toJULDateWithTime(valueStr);
		}
	}

	public static LocalDateTime localDateTimeFromJson(final String valueStr)
	{
		// TODO: optimize, convert directly
		return TimeUtil.asLocalDateTime(toJULDateWithTime(valueStr));
	}

	public static ZonedDateTime zonedDateTimeFromJson(final String valueStr)
	{
		// TODO: optimize, convert directly
		return TimeUtil.asZonedDateTime(toJULDateWithTime(valueStr));
	}

	public static Date fromObject(final Object value, final DocumentFieldWidgetType widgetType)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof Date)
		{
			return (Date)value;
		}
		else if (value instanceof String)
		{
			final String valueStr = value.toString().trim();
			return fromJson(valueStr, widgetType);
		}
		else
		{
			return TimeUtil.asDate(value);
		}
	}

	public static LocalDateTime localDateTimeFromObject(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		if (value instanceof LocalDateTime)
		{
			return (LocalDateTime)value;
		}

		final String valueStr = value.toString().trim();
		return localDateTimeFromJson(valueStr);
	}

	public static Date fromTimestamp(final Timestamp ts)
	{
		if (ts == null)
		{
			return null;
		}
		return new Date(ts.getTime());
	}

	private static final Date toJULDateWithTime(final String valueStr)
	{
		try
		{
			final ZonedDateTime zdt = ZonedDateTime.parse(valueStr, DATE_FORMAT);
			Date date = Date.from(zdt.toInstant());
			return date;
		}
		catch (final DateTimeParseException ex1)
		{
			// second try
			// FIXME: this is not optimum. We shall unify how we store Dates (as String)
			logger.warn("Using Env.parseTimestamp to convert '{}' to Date", valueStr);
			try
			{
				final Timestamp ts = Env.parseTimestamp(valueStr);
				return fromTimestamp(ts);
			}
			catch (final Exception ex2)
			{
				final String errmsg = "Failed converting '" + valueStr + "' to date."
						+ "\n Please use following format: " + DATE_PATTEN + "."
						+ "\n e.g. " + DATE_FORMAT.format(ZonedDateTime.now());
				final IllegalArgumentException exFinal = new IllegalArgumentException(errmsg, ex1);
				exFinal.addSuppressed(ex2);
				throw exFinal;
			}
		}
	}

	private static final Date toJULDate(final String valueStr)
	{
		try
		{
			// NOTE: no matter which timezone was used by the frontend, we are always taking only the date part,
			// because our Date (without time) fields are without timezone.
			final ZonedDateTime zdt = ZonedDateTime.parse(valueStr, DATE_FORMAT);
			final Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, zdt.getYear());
			cal.set(Calendar.MONTH, zdt.getMonthValue() - 1);
			cal.set(Calendar.DAY_OF_MONTH, zdt.getDayOfMonth());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			Date date = cal.getTime();
			return date;
		}
		catch (final DateTimeParseException ex1)
		{
			// second try
			// FIXME: this is not optimum. We shall unify how we store Dates (as String)
			logger.warn("Using Env.parseTimestamp to convert '{}' to Date", valueStr);
			try
			{
				final Timestamp ts = TimeUtil.truncToDay(Env.parseTimestamp(valueStr));
				return fromTimestamp(ts);
			}
			catch (final Exception ex2)
			{
				final String errmsg = "Failed converting '" + valueStr + "' to date."
						+ "\n Please use following format: " + DATE_PATTEN + "."
						+ "\n e.g. " + DATE_FORMAT.format(ZonedDateTime.now());
				final AdempiereException exFinal = new AdempiereException(errmsg, ex1);
				exFinal.addSuppressed(ex2);
				throw exFinal;
			}
		}
	}

	public static String getCurrentTimeZoneAsJson()
	{
		final Date now = SystemTime.asDate();
		final String timeZone = TIMEZONE_FORMAT.format(now);
		return timeZone;
	}
}
