/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.util;

import lombok.NonNull;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

public final class StringUtils
{
	private StringUtils()
	{
	}

	@Nullable
	public static String trim(@Nullable final String untrimmedStringOrNull)
	{
		if (untrimmedStringOrNull == null)
		{
			return untrimmedStringOrNull;
		}
		return untrimmedStringOrNull.trim();
	}

	@Nullable
	public static String trimBlankToNull(@Nullable final String str)
	{
		if (str == null || str.isEmpty())
		{
			return null;
		}

		final String strTrim = str.trim();
		if (strTrim.isEmpty())
		{
			return null;
		}

		return strTrim;
	}

	@NonNull
	public static String removeWhitespaces(@NonNull final String str)
	{
		return str.replaceAll("\\s+", "");
	}

	/**
	 * Casts a string to integer. Returns 0 if the operation fails.
	 */
	public static int toIntegerOrZero(final String str)
	{
		if (Check.isEmpty(str, true))
		{
			return 0;
		}

		try
		{
			return Integer.parseInt(str.trim());
		}
		catch (final NumberFormatException e)
		{
			return 0;
		}

	}

	/**
	 * Casts a string to BigDecimal. Returns BigDecimal.ZERO if the operation fails.
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
	 */
	@Nullable
	public static Boolean toBooleanOrNull(@Nullable final String strBoolean)
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
	 * @param value,        may be null
	 * @param defaultValue, may be null
	 * @return <ul>
	 * <li>true if value is boolean true, "true" or "Y"
	 * <li>false if value is boolean false, "false" or "N"
	 * <li><code>defaultValue</code> if value is null or other
	 * </ul>
	 */
	@Nullable
	public static Boolean toBoolean(@Nullable final Object value, @Nullable final Boolean defaultValue)
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
	 * Converts the give object to boolean value, same as {@link #toBoolean(Object, Boolean)}  but assumes default value is <code>false</code>.
	 *
	 * @param value may be {@code null}. in that case, {@code false} is returned.
	 * @return <ul>
	 * <li>true if value is boolean true, "true" or "Y"
	 * <li>false if value is boolean false, "false" or "N"
	 * <li>false if value is null or other
	 * </ul>
	 */
	public static boolean toBoolean(final Object value)
	{
		final Boolean defaultValue = Boolean.FALSE;
		return toBoolean(value, defaultValue);
	}

	/**
	 * Converts given boolean value to ADempiere's string representation of it
	 *
	 * @return <ul>
	 * <li><code>null</code> if value is null
	 * <li>"Y" if value is true
	 * <li>"N" if value is false
	 * </ul>
	 */
	@Nullable
	public static String ofBoolean(@Nullable final Boolean value)
	{
		if (value == null)
		{
			return null;
		}
		return value ? "Y" : "N";
	}

	@Nullable
	public static String ofBoolean(
			@Nullable final Boolean booleanOrNull,
			@Nullable final String defaultValue)
	{
		if (booleanOrNull == null)
		{
			return defaultValue;
		}

		return booleanOrNull ? "Y" : "N";
	}

	/**
	 * Formats the given message, using either {@link Format} or {@link MessageFormatter}.<br>
	 * If the given <code>message</code> contains <code>{0}</code> as a substring and the given <code>params</code> has at least one item, then {@link Format} is used, otherwise the SLF4J
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
	 */
	private static String formatSLF4JMessage(final String message, @Nullable final Object... params)
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
	 */
	public static String formatJavaTextFormatMessage(final String message, @Nullable final Object... params)
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
						.append(" (").append(Arrays.toString(params)).append(")")
						.toString();
			}
		}
		else
		{
			messageFormated = message;
		}
		return messageFormated;
	}

	@Nullable
	public static String maskString(@Nullable final String sensitiveString)
	{
		if (Check.isBlank(sensitiveString))
		{
			return sensitiveString;
		}

		final int length = sensitiveString.length();

		if (length >= 8)
		{
			return sensitiveString.substring(0, 4) + sensitiveString.substring(4, length).replaceAll(".", "*");
		}
		else if (length >= 4)
		{
			return sensitiveString.charAt(0) + sensitiveString.substring(1, length).replaceAll(".", "*");
		}
		else
		{
			return sensitiveString.replaceAll(".", "*");
		}
	}

	@SafeVarargs
	@Nullable
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
				@SuppressWarnings("rawtypes") final Supplier paramSupplier = (Supplier)param;

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
	 * Check if given string contains digits only.
	 *
	 * @return {@code true} if the given string consists only of digits (i.e. contains no letter, whitespace decimal point etc).
	 */
	public static boolean isNumber(final String stringToVerify)
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

	public static String toString(final Collection<?> collection, final String separator)
	{
		return toStringBuilder(collection, separator)
				.toString();
	}

	public static StringBuilder toStringBuilder(final Collection<?> collection, final String separator)
	{
		if (collection == null)
		{
			return new StringBuilder("null");
		}
		if (collection.isEmpty())
		{
			return new StringBuilder();
		}

		final StringBuilder sb = new StringBuilder();
		for (final Object item : collection)
		{
			if (sb.length() > 0)
			{
				sb.append(separator);
			}

			sb.append(item);
		}
		return sb;
	}

	/**
	 * Remove CR / LF from String
	 *
	 * @param in input
	 * @return cleaned string
	 */
	@Nullable
	public static String removeCRLF(@Nullable final String in)
	{
		if (in == null)
		{
			return null;
		}
		return in.replaceAll("(\\r|\\n)", "");
	}

	/**
	 * Fetch the numbers from a string
	 *
	 * @return string which contains all digits, or null if text is null
	 */
	@Nullable
	public static String getDigits(final String text)
	{
		if (text == null)
		{
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.length(); i++)
		{
			if (Character.isDigit(text.charAt(i)))
			{
				sb.append(text.charAt(i));
			}
		}
		return sb.toString();
	}

	/**
	 * Fetch only the text from a given string <br>
	 * E.g. text= '9000 St. Gallen'<br>
	 * This method will return test St. Gallen
	 *
	 * @return string which contains all letters not digits, or null if text is null
	 */
	@Nullable
	public static String stripDigits(final String text)
	{
		if (text == null)
		{
			return null;
		}
		final char[] inArray = text.toCharArray();
		final StringBuilder out = new StringBuilder(inArray.length);
		for (final char ch : inArray)
		{
			if (Character.isLetter(ch) || !Character.isDigit(ch))
			{
				out.append(ch);
			}
		}
		return out.toString();
	}

	/**
	 * @param in input {@link String}
	 * @return {@param in} if != null, empty string otherwise
	 */
	@NonNull
	public static String nullToEmpty(@Nullable final String in)
	{
		return in != null ? in : "";
	}

	@Nullable
	public static String createHash(@Nullable final String str, @Nullable final String algorithm) throws NoSuchAlgorithmException
	{
		if (str == null || str.isEmpty())
		{
			return null;
		}

		final String algorithmToUse = algorithm != null ? algorithm : "SHA-512";

		final MessageDigest md = MessageDigest.getInstance(algorithmToUse);

		// Update MessageDigest with input text in bytes
		md.update(str.getBytes(StandardCharsets.UTF_8));

		// Get the hashbytes
		final byte[] hashBytes = md.digest();

		//Convert hash bytes to hex format
		final StringBuilder sb = new StringBuilder();

		for (final byte b : hashBytes)
		{
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	public static String ident(@Nullable final String text, int tabs)
	{
		if (text == null || text.isEmpty() || tabs <= 0)
		{
			return text;
		}

		final String ident = repeat("\t", tabs);
		return ident
				+ text.trim().replace("\n", "\n" + ident);
	}

	public static String repeat(@NonNull final String string, final int times)
	{
		if (string.isEmpty())
		{
			return string;
		}

		if (times <= 0)
		{
			return "";
		}
		else if (times == 1)
		{
			return string;
		}
		else
		{
			final StringBuilder result = new StringBuilder(string.length() * times);
			for (int i = 0; i < times; i++)
			{
				result.append(string);
			}
			return result.toString();
		}
	}
}
