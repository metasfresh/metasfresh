package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.TimeZone;

import org.adempiere.util.time.SimpleDateFormatThreadLocal;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
public final class JSONDate implements Serializable
{
	private static final transient Logger logger = LogManager.getLogger(JSONDate.class);

	private static final String DATE_PATTEN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	private static final SimpleDateFormatThreadLocal DATE_FORMAT = new SimpleDateFormatThreadLocal(DATE_PATTEN);

	private static final DateFormat getDateFormat()
	{
		return DATE_FORMAT.getDateFormat();
	}

	public static String toJson(final java.util.Date date)
	{
		return DATE_FORMAT.format(date);
	}

	public static String toJson(final java.util.Date date, final TimeZone timeZone)
	{
		final DateFormat dateFormat = DATE_FORMAT.getDateFormat();
		dateFormat.setTimeZone(timeZone);
		final String valueStr = dateFormat.format(date);
		return valueStr;
	}

	public static String toJson(final long millis)
	{
		return DATE_FORMAT.format(new java.util.Date(millis));
	}

	public static java.util.Date fromJson(final String valueStr, final DocumentFieldWidgetType widgetType)
	{
		java.util.Date valueDate = fromString(valueStr);
		if(valueDate == null)
		{
			return null;
		}

		//
		// Apply widget type rounding, if any
		if (widgetType == DocumentFieldWidgetType.Date)
		{
			return TimeUtil.truncToDay(valueDate); 
		}
		else
		{
			return valueDate;
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
	
	private static final java.util.Date fromString(final String valueStr)
	{
		try
		{
			final DateFormat dateFormat = getDateFormat();
			return dateFormat.parse(valueStr);
		}
		catch (final ParseException ex1)
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
						+ "\n e.g. " + getDateFormat().format(new java.util.Date());
				final IllegalArgumentException exFinal = new IllegalArgumentException(errmsg, ex1);
				exFinal.addSuppressed(ex2);
				throw exFinal;
			}
		}
	}

	private JSONDate()
	{
		super();
	}
}
