package de.metas.util;
/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
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

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.Check;
import de.metas.common.util.EmptyUtil;
import lombok.NonNull;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils
{
	public static final String REGEXP_STREET_AND_NUMBER_SPLIT = "^([^0-9]+) ?([0-9]+.*$)?";

	private StringUtils()
	{
	}

	@Nullable
	public static IPair<String, String> splitStreetAndHouseNumberOrNull(@Nullable final String streetAndNumber)
	{
		if (EmptyUtil.isBlank(streetAndNumber))
		{
			return null;
		}
		final Pattern pattern = Pattern.compile(StringUtils.REGEXP_STREET_AND_NUMBER_SPLIT);
		final Matcher matcher = pattern.matcher(streetAndNumber);
		if (!matcher.matches())
		{
			return null;
		}

		final String street = matcher.group(1);
		final String number = matcher.group(2);
		return ImmutablePair.of(trim(street), trim(number));
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

	public static Optional<String> trimBlankToOptional(@Nullable final String str)
	{
		return Optional.ofNullable(trimBlankToNull(str));
	}

	@Nullable
	public static <T> T trimBlankToNullAndMap(@Nullable final String str, @NonNull Function<String, T> mapper)
	{
		final String strNorm = trimBlankToNull(str);
		return strNorm != null
				? mapper.apply(strNorm)
				: null;
	}

	/**
	 * TODO: consider using this method to improve {@link de.metas.util.lang.RepoIdAwares#ofCommaSeparatedList(String, Class)}.
	 */
	public static ImmutableList<Integer> tokenizeStringToIntegers(@Nullable final String str)
	{
		if (Check.isBlank(str))
		{
			return ImmutableList.of();
		}
		final ImmutableList.Builder<Integer> result = ImmutableList.builder();
		final String[] integerStrings = str.split("[^0-9]");
		for (final String integerString : integerStrings)
		{
			if (Check.isBlank(integerString))
			{
				continue;
			}
			result.add(Integer.parseInt(integerString));
		}
		return result.build();
	}

	public enum TruncateAt
	{
		STRING_START, STRING_END
	}

	/**
	 * Truncate string to a given length, if required.
	 */
	@Nullable
	public static String trunc(
			@Nullable final String str,
			final int length)
	{
		return trunc(str, length, TruncateAt.STRING_END);
	}

	@Nullable
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
				return string.substring(string.length() - maxLength);
			case STRING_END:
				return string.substring(0, maxLength);
			default:
				Check.errorIf(true, "Unexpected parameter TruncateAt={}; lenght={}; string={}", side, maxLength, string);
				return null;
		}
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

	@NonNull
	public static OptionalBoolean toOptionalBoolean(@Nullable final Object value)
	{
		return OptionalBoolean.ofNullableBoolean(toBoolean(value, null));
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
	public static boolean toBoolean(@Nullable final Object value)
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
	 * Formats the given message the using {@link MessageFormat}.
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
	@Deprecated
	// note: we just moved the method here, and left it unchanged. as of now idk why all this code is commented out
	public static String stripDiacritics(final String s)
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
	 * <p>
	 * Note: if any parameter is <code>null</code>, then <code>""</code> is assumed isntead.
	 */
	public static String overlayAtEnd(final String string, final String overlay)
	{
		final String overlayToUse = overlay == null ? "" : overlay;
		final String stringToUse = string == null ? "" : string;

		final int stringLength = stringToUse.length();
		final int overlayLength = overlayToUse.length();

		return org.apache.commons.lang3.StringUtils.overlay(stringToUse, overlayToUse, stringLength - overlayLength, stringLength);
	}

	/**
	 * Check if given string contains digits only.
	 *
	 * @return {@code true} if the given string consists only of digits (i.e. contains no letter, whitespace decimal point etc).
	 */
	public static boolean isNumber(@Nullable final String stringToVerify)
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
	 * Useful if you e.g. have a path expression and want to make sure it ends with {@code /}.
	 */
	public static String appendIfNotEndingWith(
			@NonNull final String string,
			@NonNull final String suffix)
	{
		if (string.endsWith(suffix))
		{
			return string;
		}
		return string + suffix;
	}

	public static String prependIfNotStartingWith(
			@NonNull final String string,
			@NonNull final String prefix)
	{
		if (string.startsWith(prefix))
		{
			return string;
		}
		return prefix + string;
	}

	/**
	 * Replace String values.
	 *
	 * @param value   string to be processed
	 * @param oldPart old part
	 * @param newPart replacement - can be null or ""
	 * @return String with replaced values
	 */
	@Nullable
	public static String replace(final String value, final String oldPart, final String newPart)
	{
		if (value == null || value.length() == 0
				|| oldPart == null || oldPart.length() == 0)
		{
			return value;
		}
		//
		final int oldPartLength = oldPart.length();
		String oldValue = value;
		final StringBuilder retValue = new StringBuilder();
		int pos = oldValue.indexOf(oldPart);
		while (pos != -1)
		{
			retValue.append(oldValue, 0, pos);
			if (newPart != null && newPart.length() > 0)
			{
				retValue.append(newPart);
			}
			oldValue = oldValue.substring(pos + oldPartLength);
			pos = oldValue.indexOf(oldPart);
		}
		retValue.append(oldValue);
		// log.debug( "Env.replace - " + value + " - Old=" + oldPart + ", New=" + newPart + ", Result=" + retValue.toString());
		return retValue.toString();
	}    // replace

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
	 * Clean - Remove all white spaces
	 *
	 * @param in in
	 * @return cleaned string
	 */
	public static String cleanWhitespace(@Nullable String in)
	{
		if (in == null)
		{
			return in;
		}
		
		return CharMatcher.whitespace().removeFrom(in);
	}    // cleanWhitespace

	
	/**
	 * remove white space from the begin
	 */
	public static String cleanBeginWhitespace(final String in)
	{
		final int len = in.length();
		int st = 0;
		final int off = 0;
		final char[] val = in.toCharArray();

		while ((st < len) && (val[off + st] <= ' '))
		{
			st++;
		}
		return ((st > 0) || (len < in.length())) ? in.substring(st, len) : in;
	}

	/**
	 * @param value note: <code>null</code> is threaded like ""
	 */
	public static String lpadZero(final String value, final int size, final String description)
	{
		final String valueFixed = prepareValueForPadding(value, size, description);

		final String s = "0000000000000000000" + valueFixed;
		return s.substring(s.length() - size);
	}

	/**
	 * Add 0 digits at the end of a String until it gets to the size given as paraameter.
	 */
	public static String rpadZero(final String value, final int size, final String description)
	{
		final String valueFixed = prepareValueForPadding(value, size, description);

		final String s = valueFixed + "0000000000000000000";
		return s.substring(0, size);
	}

	private static String prepareValueForPadding(final String value, final int size, final String description)
	{
		final String valueFixed;

		if (value == null)
		{
			valueFixed = "";
		}
		else
		{
			valueFixed = value.trim();
		}
		if (valueFixed.length() > size)
		{
			Check.fail("value='" + valueFixed + "' of '" + description + "' is bigger than " + size + " characters");
		}

		return valueFixed;
	}

	/**
	 * Mask HTML content. i.e. replace characters with &values; CR is not masked
	 *
	 * @param content content
	 * @return masked content
	 * @see #maskHTML(String, boolean)
	 */
	public static String maskHTML(final String content)
	{
		return maskHTML(content, false);
	}    // maskHTML

	/**
	 * Mask HTML content. i.e. replace characters with &values;
	 *
	 * @param content content
	 * @param maskCR  convert CR into <br>
	 * @return masked content or null if the <code>content</code> is null
	 * @deprecated please consider using {@link StringEscapeUtils#escapeHtml4(String)} instead.
	 */
	@Deprecated
	@Nullable
	public static String maskHTML(final String content, final boolean maskCR)
	{
		// If the content is null, then return null - teo_sarca [ 1748346 ]
		if (content == null || content.isEmpty())
		{
			return content;
		}
		//
		final StringBuilder out = new StringBuilder();
		final char[] chars = content.toCharArray();
		for (final char c : chars)
		{
			switch (c)
			{
				case '<':
					out.append("&lt;");
					break;
				case '>':
					out.append("&gt;");
					break;
				case '&':
					out.append("&amp;");
					break;
				case '"':
					out.append("&quot;");
					break;
				case '\'':
					out.append("&#039;");
					break;
				case '\n':
					if (maskCR)
					{
						out.append("<br>");
					}
					break;
				//
				default:
					final int ii = c;
					if (ii > 255)
					{
						out.append("&#").append(ii).append(";");
					}
					else
					{
						out.append(c);
					}
					break;
			}
		}
		return out.toString();
	}    // maskHTML

	/**
	 * Get the number of occurances of countChar in string.
	 *
	 * @param string    String to be searched
	 * @param countChar to be counted character
	 * @return number of occurances
	 */
	public static int getCount(String string, char countChar)
	{
		if (string == null || string.length() == 0)
		{
			return 0;
		}
		int counter = 0;
		final char[] array = string.toCharArray();
		for (final char element : array)
		{
			if (element == countChar)
			{
				counter++;
			}
		}
		return counter;
	}    // getCount

	/**************************************************************************
	 * Find index of search character in str. This ignores content in () and 'texts'
	 *
	 * @param str string
	 * @param search search character
	 * @return index or -1 if not found
	 */
	public static int findIndexOf(String str, char search)
	{
		return findIndexOf(str, search, search);
	}   // findIndexOf

	/**
	 * Find index of search characters in str. This ignores content in () and 'texts'
	 *
	 * @param str     string
	 * @param search1 first search character
	 * @param search2 second search character (or)
	 * @return index or -1 if not found
	 */
	public static int findIndexOf(String str, char search1, char search2)
	{
		if (str == null)
		{
			return -1;
		}
		//
		int endIndex = -1;
		int parCount = 0;
		boolean ignoringText = false;
		final int size = str.length();
		while (++endIndex < size)
		{
			final char c = str.charAt(endIndex);
			if (c == '\'')
			{
				ignoringText = !ignoringText;
			}
			else if (!ignoringText)
			{
				if (parCount == 0 && (c == search1 || c == search2))
				{
					return endIndex;
				}
				else if (c == ')')
				{
					parCount--;
				}
				else if (c == '(')
				{
					parCount++;
				}
			}
		}
		return -1;
	}   // findIndexOf

	/**
	 * Find index of search character in str. This ignores content in () and 'texts'
	 *
	 * @param str    string
	 * @param search search character
	 * @return index or -1 if not found
	 */
	public static int findIndexOf(String str, String search)
	{
		if (str == null || search == null || search.length() == 0)
		{
			return -1;
		}
		//
		int endIndex = -1;
		int parCount = 0;
		boolean ignoringText = false;
		final int size = str.length();
		while (++endIndex < size)
		{
			final char c = str.charAt(endIndex);
			if (c == '\'')
			{
				ignoringText = !ignoringText;
			}
			else if (!ignoringText)
			{
				if (parCount == 0 && c == search.charAt(0))
				{
					if (str.substring(endIndex).startsWith(search))
					{
						return endIndex;
					}
				}
				else if (c == ')')
				{
					parCount--;
				}
				else if (c == '(')
				{
					parCount++;
				}
			}
		}
		return -1;
	}   // findIndexOf

	/**************************************************************************
	 * Return Hex String representation of byte b
	 *
	 * @param b byte
	 * @return Hex
	 */
	static public String toHex(byte b)
	{
		final char hexDigit[] = {
				'0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};
		final char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return new String(array);
	}

	/**
	 * Return Hex String representation of char c
	 *
	 * @param c character
	 * @return Hex
	 */
	static public String toHex(char c)
	{
		final byte hi = (byte)(c >>> 8);
		final byte lo = (byte)(c & 0xff);
		return toHex(hi) + toHex(lo);
	}   // toHex

	/**************************************************************************
	 * Init Cap Words With Spaces
	 *
	 * @param in string
	 * @return init cap
	 */
	public static String initCap(String in)
	{
		if (in == null || in.length() == 0)
		{
			return in;
		}
		//
		boolean capitalize = true;
		final char[] data = in.toCharArray();
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] == ' ' || Character.isWhitespace(data[i]))
			{
				capitalize = true;
			}
			else if (capitalize)
			{
				data[i] = Character.toUpperCase(data[i]);
				capitalize = false;
			}
			else
			{
				data[i] = Character.toLowerCase(data[i]);
			}
		}
		return new String(data);
	}    // initCap

	/**
	 * @param in input {@link String}
	 * @return {@param in} if != null, empty string otherwise
	 */
	@Nullable
	public static String nullToEmpty(@Nullable final String in)
	{
		return in != null ? in : "";
	}

	/**
	 * Example:
	 * <pre>
	 * - string = `87whdhA7008S` (length 14)
	 * - groupSeparator = "--"
	 * - groupSize = 4
	 *
	 * Results into `87wh--dhA7--008S` of length 18.
	 * </pre>
	 *
	 * @param string         the input string
	 * @param groupSeparator the separator string
	 * @param groupSize      the size of each character group, after which a groupSeparator is inserted
	 * @return the input string containing the groupSeparator
	 */
	@NonNull
	public static String insertSeparatorEveryNCharacters(
			@NonNull final String string,
			@NonNull final String groupSeparator,
			final int groupSize)
	{
		if (groupSize < 1)
		{
			return string;
		}

		final StringBuilder result = new StringBuilder(string);
		int insertPosition = groupSize;
		while (insertPosition < result.length())
		{
			result.insert(insertPosition, groupSeparator);
			insertPosition += groupSize + groupSeparator.length();
		}
		return result.toString();
	}
}