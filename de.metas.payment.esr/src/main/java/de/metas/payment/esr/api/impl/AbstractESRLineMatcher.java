package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
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


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.compiere.util.CLogger;
import org.compiere.util.TimeUtil;

import de.metas.payment.esr.api.IESRLineMatcher;

public abstract class AbstractESRLineMatcher implements IESRLineMatcher
{
	protected final transient CLogger logger = CLogger.getCLogger(getClass());
	
	/**
	 * Method to remove the left zeros from a string.
	 * 
	 * @param value
	 * @return the initial String if it's made of only zeros; the string without the left zeros otherwise.
	 */
	protected final String removeLeftZeros(final String value)
	{
		final int size = value.length();
		int counter;
		for (counter = 0; counter < size; counter++)
		{
			if (value.charAt(counter) != '0')
			{
				break;
			}
		}
		if (counter == size)
		{
			return value;
		}
		else
		{
			return value.substring(counter, size);
		}
	}

	/**
	 * 
	 * @param dateStr date string in format yyMMdd
	 * @return
	 */
	protected final Timestamp extractTimestampFromString(final String dateStr) throws ParseException
	{
		final DateFormat df = new SimpleDateFormat("yyMMdd");
		final Date date = df.parse(dateStr);
		return TimeUtil.asTimestamp(date);
	}

}
