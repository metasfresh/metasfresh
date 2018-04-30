package org.adempiere.util;

/*
 * #%L
 * de.metas.util
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

import java.math.BigDecimal;
import java.text.Format;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import lombok.NonNull;

public final class StringUtils
{
	private StringUtils()
	{
		super();
	}

	
	public enum TruncateAt
	{
		STRING_START, STRING_END
	};
	
	/**
	 * Truncate string to a given length, if required.
	 */
	public static String trunc(
			@Nullable final String str,
			final int length)
	{
		return trunc(str, length, TruncateAt.STRING_END);
	}

	public static String trunc(
			@Nullable final String string,
			final int maxLength,
			@NonNull final TruncateAt side)
	{
		if (string == null)
		{
			return string;
		}

		if (string.length() <= maxLength)
		{
			return string;
		}

		switch (side)
		{
			case STRING_START:
				return string.substring(string.length() - maxLength, string.length());
			case STRING_END:
				return string.substring(0, maxLength);
			default:
				Check.errorIf(true, "Unexpected parameter TruncateAt={}; lenght={}; string={}", side, maxLength, string);
				return null;
		}
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
		catch (final NumberFormatException e)
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
		catch (final NumberFormatException e)
		{
			return BigDecimal.ZERO;
		}
	}

	/**
	 * Converts the given {@code strBoolean} to a Boolean (or {@code null}), using the following rules:
	 * <ul>
	 * <li>empty string, just whitespaces or {@code null} => {@code null}
	 * <li>{@code "N"} or {@code "n"} => {@code false}
	 * <li>{@code "Y"} or {@code "y"} => {@code true}
	 * <li>otherwise, the return value of {@link Boolean#parseBoolean(String)} is returned.
	 * </ul>
	 *
	 * @param strBoolean the parameter to convert. May be empty or {@code null}.
	 * @return
	 */
	public static Boolean toBooleanOrNull(final String strBoolean)
	{
		if (Check.isEmpty(strBoolean, true))
		{
			return null;
		}

		if ("Y".equalsIgnoreCase(strBoolean))
		{
			return true;
		}
		if ("N".equalsIgnoreCase(strBoolean))
		{
			return false;
		}

		return Boolean.parseBoolean(strBoolean);
	}

	/**
	 * Convert given object to ADempiere's boolean value.
	 *
	 * @param value
	 * @param defaultValue
	 * @return
	 *         <ul>
	 *         <li>true if value is boolean true, "true" or "Y"
	 *         <li>false if value is boolean false, "false" or "N"
	 *         <li><code>defaultValue</code> if value is null or other
	 *         </ul>
	 */
	public static final Boolean toBoolean(final Object value, final Boolean defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		else if (value instanceof Boolean)
		{
			return (Boolean)value;
		}
		else
		{
			final String valueStr = value.toString();
			if ("true".equalsIgnoreCase(valueStr)
					|| "Y".equalsIgnoreCase(valueStr))
			{
				return Boolean.TRUE;
			}
			else if ("false".equalsIgnoreCase(valueStr)
					|| "N".equalsIgnoreCase(valueStr))
			{
				return Boolean.FALSE;
			}
			else
			{
				return defaultValue;
			}
		}
	}

	/**
	 * Converts the give object to boolean value, same as {@link #toBoolean(Object, boolean)} but assumes default value is <code>false</code>.
	 *
	 * @param value may be {@code null}. in that case, {@code false} is returned.
	 * @return
	 *         <ul>
	 *         <li>true if value is boolean true, "true" or "Y"
	 *         <li>false if value is boolean false, "false" or "N"
	 *         <li>false if value is null or other
	 *         </ul>
	 */
	public static final boolean toBoolean(final Object value)
	{
		final Boolean defaultValue = Boolean.FALSE;
		return toBoolean(value, defaultValue);
	}

	/**
	 * Converts given boolean value to ADempiere's string representation of it
	 *
	 * @param value
	 * @return
	 *         <ul>
	 *         <li><code>null</code> if value is null
	 *         <li>"Y" if value is true
	 *         <li>"N" if value is false
	 *         </ul>
	 */
	public static final String toBooleanString(final Boolean value)
	{
		if (value == null)
		{
			return null;
		}
		return value ? "Y" : "N";
	}

	/**
	 * Formats the given message, using either {@link java.text.Format} or {@link org.slf4j.helpers.MessageFormatter}.<br>
	 * If the given <code>message</code> contains <code>{0}</code> as a substring and the given <code>params</code> has at least one item, then {@link java.text.Format} is used, otherwise the SLF4J
	 * formatter.
	 *
	 * @param params may be {@code null}
	 */
	public static String formatMessage(
			@NonNull final String message,
			@Nullable final Object... params)
	{
		if (message.contains("{0}") && params != null && params.length > 0)
		{
			return formatJavaTextFormatMessage(message, params);
		}

		return formatSLF4JMessage(message, params);
	}

	/**
	 * Formats the given message the same way our log messages are formatted. See {@link MessageFormatter} for further infos
	 *
	 * @param message
	 * @param params
	 * @return
	 */
	private static String formatSLF4JMessage(final String message, Object... params)
	{
		if (params == null)
		{
			return message; // i think null would also be handled by MessageFormatter, but better be save than sorry
		}

		final Object[] effectiveParams = invokeSuppliers(params);
		final FormattingTuple arrayFormat = MessageFormatter.arrayFormat(message, effectiveParams);

		return arrayFormat.getMessage();
	}

	/**
	 * Formats the given message the using {@link Format}.
	 *
	 * @param message
	 * @param params
	 * @return
	 */
	public static String formatJavaTextFormatMessage(final String message, Object... params)
	{
		String messageFormated;
		if (params != null && params.length > 0)
		{
			try
			{
				// for the problem description, see
				// https://community.oracle.com/thread/1254810 and
				// http://stackoverflow.com/questions/17544794/escaping-single-quotes-for-java-messageformat
				// note that we use the simpler method from the oracle thread, because...
				// [09:38:38] Teo metas: because we ASSUME our input test is always plain text and NEVER a preformated text
				// [09:38:38] Teo metas: so even if you find "''" in your text
				// [09:38:45] Teo metas: that shall be displayed as it is
				// [09:38:52] Teo metas: and not as only one "'"
				// however if it turns out that we need to use the regexp solution, make sure to have the patters as a pre-compiled constant
				final String msgToUse = message.replace("'", "''");

				final Object[] effectiveParams = invokeSuppliers(params);
				messageFormated = MessageFormat.format(msgToUse, effectiveParams);
			}
			catch (final Exception e)
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

	@SafeVarargs
	private static Object[] invokeSuppliers(final Object... params)
	{
		if (params == null)
		{
			return null;
		}

		final ArrayList<Object> result = new ArrayList<>(params.length);
		for (final Object param : params)
		{
			if (param instanceof Supplier)
			{
				@SuppressWarnings("rawtypes")
				final Supplier paramSupplier = (Supplier)param;

				result.add(paramSupplier.get());
			}
			else
			{
				result.add(param);
			}
		}
		return result.toArray();
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
	 * Overlays the given <code>string</code> with the given <code>overlay</code>, with the overlay's last character overylaying the string's last character.<br>
	 *
	 * <pre>
	 * 0000000000 + 123456 => 0000123456
	 *        000 + 123456 =>     123456
	 * </pre>
	 *
	 * Note: if any parameter is <code>null</code>, then <code>""</code> is assumed isntead.
	 *
	 * @param string
	 * @param overlay
	 * @return
	 */
	public static String overlayAtEnd(final String string, final String overlay)
	{
		final String overlayToUse = overlay == null ? "" : overlay;
		final String stringToUse = string == null ? "" : string;

		final int stringLength = stringToUse.length();
		final int overlayLength = overlayToUse.length();

		final String result = org.apache.commons.lang3.StringUtils.overlay(stringToUse, overlayToUse, stringLength - overlayLength, stringLength);
		return result;
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
		return toStringBuilder(collection, separator)
				.toString();
	}

	public static final StringBuilder toStringBuilder(final Collection<?> collection, final String separator)
	{
		if (collection == null)
		{
			return new StringBuilder("null");
		}
		if (collection.isEmpty())
		{
			return new StringBuilder("");
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

		return sb;
	}
}
