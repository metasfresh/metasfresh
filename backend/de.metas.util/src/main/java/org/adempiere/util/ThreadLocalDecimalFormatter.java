package org.adempiere.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.function.Supplier;

import lombok.ToString;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ToString(exclude = "formatterHolder")
public final class ThreadLocalDecimalFormatter
{
	public static ThreadLocalDecimalFormatter ofPattern(final String pattern)
	{
		final DecimalFormatSymbols symbols = null; // auto
		return new ThreadLocalDecimalFormatter(pattern, symbols);
	}

	public static ThreadLocalDecimalFormatter ofPattern(final String pattern, final DecimalFormatSymbols symbols)
	{
		return new ThreadLocalDecimalFormatter(pattern, symbols);
	}

	private final String pattern; // just for toString()
	private final ThreadLocal<DecimalFormat> formatterHolder;

	private ThreadLocalDecimalFormatter(final String pattern, final DecimalFormatSymbols symbols)
	{
		Check.assumeNotEmpty(pattern, "pattern is not empty");
		this.pattern = pattern;

		final Supplier<? extends DecimalFormat> supplier;
		if (symbols == null)
		{
			supplier = () -> new DecimalFormat(pattern);
		}
		else
		{
			supplier = () -> new DecimalFormat(pattern, symbols);
		}
		formatterHolder = ThreadLocal.withInitial(supplier);

		formatterHolder.get(); // get an instance just to make sure the pattern is OK
	}

	private DecimalFormat getFormatter()
	{
		return formatterHolder.get();
	}

	public String format(final Object obj)
	{
		return getFormatter().format(obj);
	}

	public String format(final double number)
	{
		return getFormatter().format(number);
	}

	public String format(final long number)
	{
		return getFormatter().format(number);
	}
}
