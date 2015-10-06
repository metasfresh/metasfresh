package org.adempiere.util.time;

/*
 * #%L
 * org.adempiere.util
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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

/**
 * {@link SimpleDateFormat} wrapped in a {@link ThreadLocal} which makes it thread safe.
 *
 * Use this instead of {@link SimpleDateFormat} whenever you need to have a {@link SimpleDateFormat} as class field and you want to be thread safe.
 *
 * Alternatives to this approach are:
 * <ul>
 * <li>use method local {@link SimpleDateFormat} (which sometimes can be a performance penalty)
 * <li>consider switching to joda-time (http://www.joda.org/joda-time/)
 * </ul>
 *
 * @author tsa
 *
 */
public final class SimpleDateFormatThreadLocal extends ThreadLocal<SimpleDateFormat>
{
	private final String pattern;

	/**
	 * 
	 * @param pattern {@link SimpleDateFormat}'s pattern
	 */
	public SimpleDateFormatThreadLocal(final String pattern)
	{
		super();
		Check.assumeNotEmpty(pattern, "pattern not empty");
		this.pattern = pattern;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	protected SimpleDateFormat initialValue()
	{
		return new SimpleDateFormat(pattern);
	}

	/**
	 * @see SimpleDateFormat#format(Date)
	 */
	public String format(final Date date)
	{
		return get().format(date);
	}

	/**
	 * @see SimpleDateFormat#format(Object)
	 */
	public Object format(final Object value)
	{
		return get().format(value);
	}

	/**
	 * @see SimpleDateFormat#parse(String)
	 */
	public Date parse(final String source) throws ParseException
	{
		return get().parse(source);
	}

	/**
	 * @see SimpleDateFormat#parseObject(String)
	 */
	public Object parseObject(final String source) throws ParseException
	{
		return get().parseObject(source);
	}
}
