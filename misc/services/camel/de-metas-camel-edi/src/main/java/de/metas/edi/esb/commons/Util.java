package de.metas.edi.esb.commons;

import de.metas.edi.esb.commons.api.ILookupTemplate;
import de.metas.edi.esb.commons.api.ILookupValue;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoicVType;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.support.DefaultMessage;
import org.springframework.lang.Nullable;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.math.BigDecimal.ZERO;

/*
 * #%L
 * de.metas.edi.esb
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

public final class Util
{
	private static final transient Logger LOGGER = Logger.getLogger(Util.class.getName());

	private Util()
	{
		super();
	}

	// public static void readProperties(
	// 		final CamelContext context,
	// 		final String... propertiesLocations)
	// {
	// 	if (context.hasComponent("properties") == null)
	// 	{
	// 		final StringBuilder msg = new StringBuilder("Going to add a PropertiesComponent with propertiesLocation(s)=");
	// 		for (final String loc : propertiesLocations)
	// 		{
	// 			msg.append(loc + " ");
	// 		}
	// 		Util.LOGGER.info(msg.toString());
	//
	// 		final PropertiesComponent pc = new PropertiesComponent();
	// 		pc.setLocations(propertiesLocations);
	// 		context.addComponent("properties", pc);
	// 	}
	// }

	/**
	 * @return date converted to {@link XMLGregorianCalendar} or <code>null</code> if date was <code>null</code>.
	 */
	public static XMLGregorianCalendar toXMLCalendar(final Date date)
	{
		if (date != null)
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			try
			{
				return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			}
			catch (final DatatypeConfigurationException e)
			{
				throw new RuntimeCamelException("Error getting datatype for XMLGregorianCalendar", e);
			}
		}
		return null;
	}

	public static String toFormattedStringDate(final Date date, @NonNull final String datePattern)
	{
		if (date == null)
		{
			return null;
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
		return dateFormat.format(date);
	}

	public static Date toDate(final XMLGregorianCalendar xmlCalendar)
	{
		if (xmlCalendar == null)
		{
			return null;
		}

		final GregorianCalendar cal = xmlCalendar.toGregorianCalendar();
		return cal.getTime();
	}

	public static BigDecimal toBigDecimal(final String numberStr)
	{
		if (isEmpty(numberStr))
		{
			return ZERO;
		}
		return new BigDecimal(numberStr.trim());
	}

	/**
	 * Creates an {@link XMLGregorianCalendar} date.<br />
	 * If the isNewDateOverride is used, on exception, create today's date.
	 *
	 * @return {@link XMLGregorianCalendar} date
	 */
	public static XMLGregorianCalendar createCalendarDate(final String date, final boolean isNewDateOverride, final String datePattern)
	{
		try
		{
			final Date created = new SimpleDateFormat(datePattern).parse(date); // NOPMD by al on 4/15/13 11:09 AM

			return Util.toXMLCalendar(created);
		}
		catch (final Exception e)
		{
			if (isNewDateOverride)
			{
				return Util.toXMLCalendar(new Date());
			}

			return null;
		}
	}

	/**
	 * Creates an {@link XMLGregorianCalendar} date.<br />
	 * If the isNewDateOverride is used, on exception, create today's date.<br />
	 * See {@link #createCalendarDate(String, boolean, String)}<br />
	 * <br />
	 * Date pattern used: {@link Constants#METASFRESH_DATE_PATTERN}
	 *
	 * @return {@link XMLGregorianCalendar} date
	 */
	public static XMLGregorianCalendar createCalendarDate(final String date, final boolean isNewDateOverride)
	{
		return Util.createCalendarDate(date, isNewDateOverride, Constants.METASFRESH_DATE_PATTERN);
	}

	/**
	 * Creates an {@link XMLGregorianCalendar} date, using the datePattern provided.<br />
	 * Assumes isNewDateOverride=false<br />
	 * See {@link #createCalendarDate(String, boolean, String)}<br />
	 * <br />
	 *
	 * @return {@link XMLGregorianCalendar} date or null if the given date was null or empty
	 */
	public static XMLGregorianCalendar createCalendarDate(final String date, final String datePattern)
	{
		return Util.createCalendarDate(date, false, datePattern);
	}

	/**
	 * Creates an {@link XMLGregorianCalendar} date.<br />
	 * On exception, assumes isNewDateOverride=false (see {@link #createCalendarDate(String, boolean)}).<br />
	 * <br />
	 * Date pattern used: {@link Constants#METASFRESH_DATE_PATTERN}
	 *
	 * @return {@link XMLGregorianCalendar} date
	 */
	public static XMLGregorianCalendar createCalendarDate(final String date)
	{
		return Util.createCalendarDate(date, false, Constants.METASFRESH_DATE_PATTERN);
	}

	/**
	 * Use UNIX line endings.
	 */
	public static String fixLineEnding(final String s)
	{
		if (s == null || s.isEmpty())
		{
			return s;
		}

		return s.replace("\r\n", "\n").trim();
	}

	/**
	 * Returns metasfresh boolean String
	 *
	 * @return String "Y" or "N"
	 */
	public static String getADBooleanString(final String value)
	{
		if (value == null)
		{
			return value;
		}

		if (value.trim().equals("true"))
		{
			return "Y";
		}

		else if (value.trim().equals("false"))
		{
			return "N";
		}

		return value;
	}

	public static boolean isEmpty(final String s)
	{
		if (s == null)
		{
			return true;
		}
		return s.trim().isEmpty();
	}

	/**
	 * Returns a {@link Message} with the body of a {@link JAXBElement}
	 */
	public static Message createJaxbMessage(
			@NonNull final JAXBElement<?> element,
			@NonNull final CamelContext camelContext)
	{
		final Message message = new DefaultMessage(camelContext);
		message.setBody(element);

		return message;
	}

	/**
	 * Returns null if the string is null or the trimmed string is empty, or the trimmed string otherwise
	 *
	 * @return {@link String}
	 */
	public static String trimString(@Nullable final String value)
	{
		if (value == null)
		{
			return null;
		}

		final String trimmedValue = value.trim();
		if (trimmedValue.isEmpty())
		{
			return null;
		}

		return trimmedValue;
	}

	public static String trimAndTruncate(@Nullable final String value, final int targetLength)
	{
		final String valueToWorkWith = trimString(value);
		if (valueToWorkWith == null || valueToWorkWith.length() <= targetLength)
		{
			return valueToWorkWith;// nothing to do
		}
		return valueToWorkWith.substring(0, targetLength);
	}

	/**
	 * @return generic lookup object
	 */
	public static <T> T createGenericLookup(final Class<T> templateClass, final ILookupValue<?>... lookupValues)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchMethodException
	{
		final T object = templateClass.getDeclaredConstructor().newInstance();

		// flag used to decide if the lookup is valid or not (return null if not to not confuse replication)
		boolean containsValue = false;
		for (final ILookupValue<?> lookupValue : lookupValues)
		{
			final Object value = setLookupValue(templateClass, object, lookupValue);
			if (value != null)
			{
				containsValue = true;
			}
			else if (lookupValue.isMandatoryLookup())
			{
				// If the value is mandatory and null, do NOT make the lookup
				return null;
			}
		}

		if (!containsValue)
		{
			return null;
		}
		return object;
	}

	private static <T, KT extends Object> KT setLookupValue(final Class<T> templateClass, final T templateInstance, final ILookupValue<KT> lookupValue)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException
	{
		final KT key = lookupValue.getValue();
		if (key == null || isEmptyKey(key))
		{
			return null;
		}

		final ILookupTemplate<KT> lookupTemplate = lookupValue.getLookupTemplate();
		final Method setKeyMethod;
		try
		{
			setKeyMethod = templateClass.getDeclaredMethod(lookupTemplate.getMethodName(), lookupTemplate.getKeyType());
		}
		catch (final NoSuchMethodException e)
		{
			throw new IllegalArgumentException("Supplied lookup class does not contain " + lookupTemplate.getMethodName() + " method", e);
		}

		// Convert the string key to the requested parameter type.
		setKeyMethod.invoke(templateInstance, key);
		return key;
	}

	private static boolean isEmptyKey(final Object key)
	{
		if (key instanceof String)
		{
			return key.toString().isEmpty();
		}
		else
		{
			// TODO else if other types
			return false;
		}
	}

	/**
	 * @return generic lookup object
	 */
	public static <T> T resolveGenericLookup(final Class<T> templateClass, final ILookupValue<?>... lookupValues)
	{
		try
		{
			return createGenericLookup(templateClass, lookupValues);
		}
		catch (final Exception e)
		{
			throw new RuntimeCamelException("Error generating lookup", e);
		}
	}

	public static String resolveProperty(
			@NonNull final CamelContext context,
			@NonNull final String propertyKey)
	{
		return resolveProperty(context, propertyKey, null);
	}

	/**
	 * @return String resolved property from context, or the @code defaultValue} if given
	 */
	public static String resolveProperty(
			@NonNull final CamelContext context,
			@NonNull final String propertyKey,
			@Nullable final String defaultValue)
	{
		try
		{
			return context.resolvePropertyPlaceholders("{{" + propertyKey + "}}");
		}
		catch (final Exception e)
		{
			if (defaultValue != null)
			{
				return defaultValue;
			}
			throw new IllegalArgumentException("Exception occured when retrieving property key: " + propertyKey, e);
		}
	}

	public static int resolvePropertyPlaceholdersAsInt(
			@NonNull final CamelContext context,
			@NonNull final String propertyKey)
	{
		final String valueStr = resolveProperty(context, propertyKey, null);
		return Integer.parseInt(valueStr);
	}

	public static boolean resolvePropertyAsBool(
			@NonNull final CamelContext context,
			@NonNull final String propertyKey,
			final String defaultValue)
	{
		final String valueStr = resolveProperty(context, propertyKey, defaultValue);
		return Boolean.parseBoolean(valueStr);
	}

	/**
	 * Invoke setter method on object.
	 */
	public static <T> void invokeSetterMethod(final T object, final Object value, final Class<?> valueType, final String methodName)
			throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException
	{
		final Method setterMethod;
		try
		{
			setterMethod = object.getClass().getDeclaredMethod(methodName, valueType);
		}
		catch (final NoSuchMethodException e)
		{
			throw new IllegalArgumentException("Supplied lookup class does not contain " + methodName + " method", e);
		}

		// Convert the string key to the requested parameter type.
		setterMethod.invoke(object, value);
	}

	/**
	 * Invoke getter method on object.
	 */
	public static <T> T invokeGetterMethod(final Object object, final String methodName)
			throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException
	{
		final Method getterMethod;
		try
		{
			getterMethod = object.getClass().getDeclaredMethod(methodName);
		}
		catch (final NoSuchMethodException e)
		{
			throw new IllegalArgumentException("Supplied lookup class does not contain " + methodName + " method", e);
		}

		@SuppressWarnings("unchecked")
		final T result = (T)getterMethod.invoke(object);
		return result;
	}

	/**
	 * Format number using decimal format.
	 *
	 * @return formatted number if not null, {@code "0"} otherwise
	 */
	public static String formatNumber(@Nullable final Number n, @NonNull final DecimalFormat decimalFormat)
	{
		if (n == null)
		{
			return "0";
		}

		try
		{
			return decimalFormat.format(n);
		}
		catch (final Exception e)
		{
			throw new RuntimeCamelException("Exception occured while formatting number " + n, e);
		}
	}

	/**
	 * TODO: please check this and remove duplicates later...
	 */
	private static final Map<CharSequence, CharSequence> notNormalizableChars = new HashMap<>();

	static
	{
		Util.notNormalizableChars.put("â", "a");
		Util.notNormalizableChars.put("é", "e");

		// legacy (from the StackOverflow resource)
		Util.notNormalizableChars.put("\u0141", "l"); // BiaLystock
		Util.notNormalizableChars.put("\u0142", "l"); // Bialystock
		Util.notNormalizableChars.put("ß", "ss");
		Util.notNormalizableChars.put("©", "c");
		Util.notNormalizableChars.put("\u00D0", "d"); // All Ð ð from http://de.wikipedia.org/wiki/%C3%90
		Util.notNormalizableChars.put("\u00F0", "d");
		Util.notNormalizableChars.put("\u0110", "d");
		Util.notNormalizableChars.put("\u0111", "d");
		Util.notNormalizableChars.put("\u0189", "d");
		Util.notNormalizableChars.put("\u0256", "d");
		Util.notNormalizableChars.put("\u00DE", "th"); // thorn Þ
		Util.notNormalizableChars.put("\u00FE", "th"); // thorn þ

		// taken from http://terpconnect.umd.edu/~zben/Web/CharSet/htmlchars.html
		Util.notNormalizableChars.put("À", "A");
		Util.notNormalizableChars.put("Á", "A");
		Util.notNormalizableChars.put("Â", "A");
		Util.notNormalizableChars.put("Ã", "A");
		Util.notNormalizableChars.put("Ä", "A");
		Util.notNormalizableChars.put("Å", "A");
		Util.notNormalizableChars.put("Æ", "AE");
		Util.notNormalizableChars.put("Ç", "C");
		Util.notNormalizableChars.put("È", "E");
		Util.notNormalizableChars.put("É", "E");
		Util.notNormalizableChars.put("Ê", "E");
		Util.notNormalizableChars.put("Ë", "E");
		Util.notNormalizableChars.put("Ì", "I");
		Util.notNormalizableChars.put("Í", "I");
		Util.notNormalizableChars.put("Î", "I");
		Util.notNormalizableChars.put("Ï", "I");
		Util.notNormalizableChars.put("Ð", "D");
		Util.notNormalizableChars.put("Ñ", "N");
		Util.notNormalizableChars.put("Ò", "O");
		Util.notNormalizableChars.put("Ó", "O");
		Util.notNormalizableChars.put("Ô", "O");
		Util.notNormalizableChars.put("Õ", "O");
		Util.notNormalizableChars.put("Ö", "O");
		Util.notNormalizableChars.put("×", "x");
		Util.notNormalizableChars.put("Ø", "O");
		Util.notNormalizableChars.put("Ù", "U");
		Util.notNormalizableChars.put("Ú", "U");
		Util.notNormalizableChars.put("Û", "U");
		Util.notNormalizableChars.put("Ü", "U");
		Util.notNormalizableChars.put("Ý", "Y");
		Util.notNormalizableChars.put("à", "a");
		Util.notNormalizableChars.put("á", "a");
		Util.notNormalizableChars.put("â", "a");
		Util.notNormalizableChars.put("ã", "a");
		Util.notNormalizableChars.put("ä", "a");
		Util.notNormalizableChars.put("å", "a");
		Util.notNormalizableChars.put("æ", "a");
		Util.notNormalizableChars.put("ç", "c");
		Util.notNormalizableChars.put("è", "e");
		Util.notNormalizableChars.put("é", "e");
		Util.notNormalizableChars.put("ê", "e");
		Util.notNormalizableChars.put("ë", "e");
		Util.notNormalizableChars.put("ì", "i");
		Util.notNormalizableChars.put("í", "i");
		Util.notNormalizableChars.put("î", "i");
		Util.notNormalizableChars.put("ï", "i");
		Util.notNormalizableChars.put("ð", "o");
		Util.notNormalizableChars.put("ñ", "n");
		Util.notNormalizableChars.put("ò", "o");
		Util.notNormalizableChars.put("ó", "o");
		Util.notNormalizableChars.put("ô", "o");
		Util.notNormalizableChars.put("õ", "o");
		Util.notNormalizableChars.put("ö", "o");
		Util.notNormalizableChars.put("ø", "o");
		Util.notNormalizableChars.put("ù", "u");
		Util.notNormalizableChars.put("ú", "u");
		Util.notNormalizableChars.put("û", "u");
		Util.notNormalizableChars.put("ü", "u");
		Util.notNormalizableChars.put("ý", "y");
		Util.notNormalizableChars.put("ÿ", "y");
	}

	/**
	 * Special regular expression character ranges relevant for simplification
	 *
	 * @see <a href="http://docstore.mik.ua/orelly/perl/prog3/ch05_04.htm">InCombiningDiacriticalMarks: special marks that are part of "normal" ä, ö, î etc..</a>
	 * @see <a href="http://www.fileformat.info/info/unicode/category/Sk/list.htm">IsSk: Symbol, Modifier</a>
	 * @see <a href="http://www.fileformat.info/info/unicode/category/Lm/list.htm">IsLm: Letter, Modifier</a>
	 */
	public static final Pattern DIACRITICS_AND_FRIENDS = Pattern.compile("[\\p{InCombiningDiacriticalMarks}]+"); // original pattern: [\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+

	/**
	 * <b>TODO</b> Maybe set the RegEx pattern externally/via properties? <br>
	 * <br>
	 * Normalize string using {@link Normalizer}, and replacing remaining special characters with "".<br>
	 * <br>
	 *
	 * @return ASCII-friendly string
	 * @see <a href="http://stackoverflow.com/questions/3322152/java-getting-rid-of-accents-and-converting-them-to-regular-letters">Reference 1</a>
	 * @see <a href="http://stackoverflow.com/questions/2096667/convert-unicode-to-ascii-without-changing-the-string-length-in-java">Reference 2</a>
	 */
	public static String normalize(final String str)
	{
		if (str == null)
		{
			return null;
		}

		String normalizedString = Normalizer.normalize(str, Normalizer.Form.NFKD);
		for (final Entry<CharSequence, CharSequence> charDuo : Util.notNormalizableChars.entrySet())
		{
			normalizedString = normalizedString.replace(charDuo.getKey(), charDuo.getValue());
		}

		// remove remaining special characters with "" (should not normally occur, as Normalize is enough)
		return Util.DIACRITICS_AND_FRIENDS.matcher(normalizedString).replaceAll("");
	}

	/**
	 * @return resulting string with preceding zeros removed. Or null, if the input was <code>null</code>.
	 * @see <a href="http://stackoverflow.com/questions/2800739/how-to-remove-leading-zeros-from-alphanumeric-text">Reference</a>
	 */
	public static String removePrecedingZeros(final String str)
	{
		if (str == null)
		{
			return null;
		}
		return str.replaceFirst("^0+(?!$)", "");
	}

	public static String lpadZero(final String value, final int size)
	{
		if (value == null)
		{
			throw new IllegalArgumentException("value is null");
		}

		final String valueFixed = value.trim();

		final String s = "0000000000000000000" + valueFixed;
		return s.substring(s.length() - size);
	}

	public static String mkOwnOrderNumber(final String documentNo)
	{
		final String sevenDigitString = documentNo.length() <= 7 ? documentNo : documentNo.substring(documentNo.length() - 7);
		return "006" + lpadZero(sevenDigitString, 7);
	}

	/**
	 * Remove trailing zeros after decimal separator
	 *
	 * @return <code>bd</code> without trailing zeros after separator; if argument is NULL then NULL will be retu
	 */
	// NOTE: this is copy-paste of de.metas.util.NumberUtils.stripTrailingDecimalZeros(BigDecimal)
	public static BigDecimal stripTrailingDecimalZeros(final BigDecimal bd)
	{
		if (bd == null)
		{
			return null;
		}

		//
		// Remove all trailing zeros
		BigDecimal result = bd.stripTrailingZeros();

		// Fix very weird java 6 bug: stripTrailingZeros doesn't work on 0 itself
		// http://stackoverflow.com/questions/5239137/clarification-on-behavior-of-bigdecimal-striptrailingzeroes
		if (result.signum() == 0)
		{
			result = BigDecimal.ZERO;
		}

		//
		// If after removing our scale is negative, we can safely set the scale to ZERO because we don't want to get rid of zeros before decimal point
		if (result.scale() < 0)
		{
			result = result.setScale(0, RoundingMode.UNNECESSARY);
		}

		return result;
	}
}
