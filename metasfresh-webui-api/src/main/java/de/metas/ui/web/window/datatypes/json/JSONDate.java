package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

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
	private static final DateFormat getDateFormat()
	{
		// TODO: optimize dateToJsonObject; maybe use joda time or java8 API
		// TODO: user current session Locale
		return new SimpleDateFormat(DATE_PATTEN);
	}

	public static Object toJson(final java.util.Date valueDate)
	{
		final DateFormat dateFormat = getDateFormat();
		final String valueStr = dateFormat.format(valueDate);
		return valueStr;
	}

	public static java.util.Date fromJson(final String valueStr)
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
				final IllegalArgumentException exFinal = new IllegalArgumentException("Failed converting '" + valueStr + "' to date", ex1);
				exFinal.addSuppressed(ex2);
				throw exFinal;
			}
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

	private static final Logger logger = LogManager.getLogger(JSONDate.class);
	private static final String DATE_PATTEN = "yyyy-MM-dd'T'HH:mm'Z'"; // Quoted "Z" to indicate UTC, no timezone offset // TODO fix the pattern

	private JSONDate()
	{
		super();
	}
}
