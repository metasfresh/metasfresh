package de.metas.printing.esb.base.util;

/*
 * #%L
 * de.metas.printing.esb.base
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


import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;

public final class StringUtils
{
	private StringUtils()
	{
		super();
	}

	/**
	 * Truncate string to a given length.
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static final String trunc(final String str, final int length)
	{
		if (str == null)
		{
			return str;
		}

		if (str.length() <= length)
		{
			return str;
		}

		return str.substring(0, length);
	}

	/**
	 * Casts a string to integer. Returns 0 if the operation fails.
	 * 
	 * @param str
	 * @return
	 */
	public static int toIntegerOrZero(final String str)
	{
		if (str == null || str.isEmpty())
		{
			return 0;
		}

		try
		{
			return Integer.valueOf(str);
		}
		catch (NumberFormatException e)
		{
			return 0;
		}

	}

	/**
	 * Casts a string to BigDecimal. Returns BigDecimal.ZERO if the operation fails.
	 * 
	 * @param str
	 * @return
	 */
	public static BigDecimal toBigDecimalOrZero(final String str)
	{
		if (str == null || str.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		try
		{
			return new BigDecimal(str);
		}
		catch (NumberFormatException e)
		{
			return BigDecimal.ZERO;
		}
	}

	public static String formatMessage(final String message, Object... params)
	{
		String messageFormated;
		if (params != null && params.length > 0)
		{
			try
			{
				messageFormated = MessageFormat.format(message, params);
			}
			catch (Exception e)
			{
				// In case message formating failed, we have a fallback format to use
				messageFormated = new StringBuilder()
						.append(message)
						.append(" (").append(params).append(")")
						.toString();
			}
		}
		else
		{
			messageFormated = message;
		}
		return messageFormated;
	}

	/**
	 * String diacritics from given string
	 * 
	 * @param s original string
	 * @return string without diacritics
	 */
	// note: we just moved the method here, and left it unchanges. as of now idk why all this code is commented out
	public static String stripDiacritics(String s)
	{
		/* JAVA5 behaviour */
		return s;
		/*
		 * JAVA6 behaviour * if (s == null) { return s; } String normStr = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
		 * 
		 * StringBuffer sb = new StringBuffer(); for (int i = 0; i < normStr.length(); i++) { char ch = normStr.charAt(i); if (ch < 255) sb.append(ch); } return sb.toString(); /*
		 */
	}

	/**
	 * Check if given string contains digits only.
	 * 
	 * @param stringToVerify
	 * @return {@link code true} if the given string consists only of digits (i.e. contains no letter, whitespace decimal point etc).
	 */
	public static final boolean isNumber(final String stringToVerify)
	{
		// Null or empty strings are not numbers
		if (stringToVerify == null || stringToVerify.isEmpty())
		{
			return false;
		}

		final int length = stringToVerify.length();
		for (int i = 0; i < length; i++)
		{
			if (!Character.isDigit(stringToVerify.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	public static final String toString(final Collection<?> collection, final String separator)
	{
		if (collection == null)
		{
			return "null";
		}
		if (collection.isEmpty())
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder();
		for (final Object item : collection)
		{
			if (sb.length() > 0)
			{
				sb.append(separator);
			}
			
			sb.append(String.valueOf(item));
		}
		
		return sb.toString();
	}
}
