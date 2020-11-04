package org.adempiere.test;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.common.util.time.SystemTime;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UnitTestTools
{
	/**
	 * Returns a timestamp that is <code>days</code> days before the current time.
	 */
	public static Timestamp daysBefore(final int days)
	{
		if (days < 0)
		{
			throw new IllegalArgumentException(
					"Parameter days must be 0 or positive; Was: " + days + ".");
		}

		Calendar cal = GregorianCalendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days * -1);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * Returns a timestamp that is <code>days</code> days after the current time.
	 */
	public static Timestamp daysAfter(final int days)
	{
		if (days < 0)
		{
			throw new IllegalArgumentException(
					"Parameter days must be 0 or positive; Was: " + days + ".");
		}

		Calendar cal = SystemTime.asGregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, days);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static void assertMethodExists(final Class<?> className,
			final String methodName, final Class<?>[] params,
			final Class<?> returnType)
	{
		try
		{
			final Method method = className.getMethod(methodName, params);
			assertEquals(returnType, method.getReturnType());
		}
		catch (SecurityException e)
		{
			fail();
		}
		catch (NoSuchMethodException e)
		{
			fail("Method doesn't exist");
		}
	}




	/**
	 * @param string
	 *            with format "yyyy-MM-dd".
	 */
	public static Date toDate(final String string)
	{
		try
		{
			return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(string)
					.getTime());
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}
}
