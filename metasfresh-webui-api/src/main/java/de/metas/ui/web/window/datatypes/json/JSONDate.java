package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.TimeZone;

import org.adempiere.util.time.SimpleDateFormatThreadLocal;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;

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

@SuppressWarnings("serial")
public final class JSONDate implements Serializable
{
	private static final transient Logger logger = LogManager.getLogger(JSONDate.class);

	public static final String DATE_PATTEN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	// private static final SimpleDateFormatThreadLocal DATE_FORMAT = new SimpleDateFormatThreadLocal(DATE_PATTEN);
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(JSONDate.DATE_PATTEN);

	private static final SimpleDateFormatThreadLocal TIMEZONE_FORMAT = new SimpleDateFormatThreadLocal("XXX");

	public static String toJson(final java.util.Date date)
	{
		final ZonedDateTime d = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return DATE_FORMAT.format(d);
	}

	public static String toJson(final java.util.Date date, final TimeZone timeZone)
	{
		final LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), timeZone.toZoneId());
		final String valueStr = DATE_FORMAT.format(ldt);
		return valueStr;
	}

	public static String toJson(final long millis)
	{
		final LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
		return DATE_FORMAT.format(ldt);
	}

	public static java.util.Date fromJson(final String valueStr, final DocumentFieldWidgetType widgetType)
	{
		if (widgetType == DocumentFieldWidgetType.Date)
		{
			return fromDateString(valueStr);
		}
		else
		{
			return fromDateTimeString(valueStr);
		}
	}

	public static java.util.Date fromTimestamp(final Timestamp ts)
	{
		if (ts == null)
		{
			return null;
		}
		return new java.util.Date(ts.getTime());
	}

	private static final java.util.Date fromDateTimeString(final String valueStr)
	{
		try
		{
			final ZonedDateTime zdt = ZonedDateTime.parse(valueStr, DATE_FORMAT);
			java.util.Date date = java.util.Date.from(zdt.toInstant());
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
						+ "\n e.g. " + DATE_FORMAT.format(Instant.now());
				final IllegalArgumentException exFinal = new IllegalArgumentException(errmsg, ex1);
				exFinal.addSuppressed(ex2);
				throw exFinal;
			}
		}
	}

	private static final java.util.Date fromDateString(final String valueStr)
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

			java.util.Date date = cal.getTime();
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
				final IllegalArgumentException exFinal = new IllegalArgumentException(errmsg, ex1);
				exFinal.addSuppressed(ex2);
				throw exFinal;
			}
		}
	}

	public static String getCurrentTimeZoneAsJson()
	{
		final java.util.Date now = SystemTime.asDate();
		final String timeZone = TIMEZONE_FORMAT.format(now);
		return timeZone;
	}

	private JSONDate()
	{
		super();
	}
}
