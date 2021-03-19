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

package de.metas.util;

import de.metas.common.util.EmptyUtil;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 */
public final class Check
{
	private static Class<? extends RuntimeException> defaultExClazz = RuntimeException.class;

	private static boolean throwException = true;

	private static String exceptionHeaderMessage = null;

	private static Logger logger = null;

	private Check()
	{
	}

	/**
	 * Set the class of exceptions to be thrown by this classe's methods. Usually ADempiere will call this method on startup, setting <code>org.adempiere.exceptions.AdempiereException</code>.
	 */
	public static void setDefaultExClass(@NonNull final Class<? extends RuntimeException> clazz)
	{
		defaultExClazz = clazz;
	}

	/**
	 * Decides if an exception shall be thrown on a failed assumption. Note that if this is set to <code>false</code>, then also {@link #setLogger(Logger)} needs to be called to set a logger.
	 * Otherwise the <code>false</code> parameter will be ingored.
	 */
	public static void setThrowException(final boolean throwException)
	{
		Check.throwException = throwException;
	}

	/**
	 * If an exception is thrown
	 * <li>and the exception class does not implement {@link ExceptionWithOwnHeaderMessage}
	 * <li>and a message was set with this method,
	 * <li>then that message plus a line-break is prepended to the exception message.
	 */
	public static void setExceptionHeaderMessage(final String exceptionHeaderMessage)
	{
		Check.exceptionHeaderMessage = exceptionHeaderMessage;
	}

	/**
	 * Set a logger to be used by this class to <b>log</b> exceptions instead of throwing them, if {@link #setThrowException(boolean)} was called with <code>false</code>.
	 */
	public static void setLogger(final Logger logger)
	{
		Check.logger = logger;
	}

	public static RuntimeException mkEx(final String msg)
	{
		return mkEx(defaultExClazz, msg);
	}

	private static RuntimeException mkEx(final Class<? extends RuntimeException> exClazz, final String msg)
	{
		final boolean exceptionHasItsOwnHeaderMessage = ExceptionWithOwnHeaderMessage.class.isAssignableFrom(exClazz);

		final StringBuilder msgToUse = new StringBuilder();
		if (!exceptionHasItsOwnHeaderMessage && !Check.isEmpty(exceptionHeaderMessage))
		{
			msgToUse.append(exceptionHeaderMessage);
			msgToUse.append("\n\n");
		}
		msgToUse.append(msg);

		try
		{
			final Constructor<? extends RuntimeException> c = exClazz.getConstructor(String.class);
			return c.newInstance(msgToUse.toString());
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Failure throwing exception with class '" + exClazz + "' and message '" + msg + "'", e);
		}
	}

	public interface ExceptionWithOwnHeaderMessage
	{
	}

	private static RuntimeException throwOrLogEx(final Class<? extends RuntimeException> exClazz, final String msg)
	{
		final RuntimeException ex = mkEx(exClazz, msg);

		Loggables.addLog("{}; Exception: {}", msg, ex);

		if (throwException || logger == null)
		{
			throw ex;
		}
		else
		{
			logger.error(msg, ex);
			return ex;
		}
	}

	/**
	 * Little method that throws an {@link Exception} if the given boolean condition is false. It might be a good idea to use "assume" instead of the assert keyword, because
	 * <li>assert is
	 * globally switched on and off and you never know what else libs are using assert</li>
	 * <li>there are critical assumptions that should always be validated. Not only during development time or when
	 * someone minds to use the -ea cmdline parameter</li>
	 *
	 * @param errMsg the error message to pass to the assertion error, if the condition is <code>false</code>
	 * @param params message parameters (@see {@link MessageFormat})
	 */
	public static void assume(final boolean cond, final String errMsg, final Object... params)
	{
		assume(cond, defaultExClazz, errMsg, params);
	}

	public static <T> void assumeEquals(@Nullable final T obj1, @Nullable final T obj2, final String objectName)
	{
		assume(Objects.equals(obj1, obj2), "assumed same {} but they were different: {}, {}", objectName, obj1, obj2);
	}

	/**
	 * Like {@link #assume(boolean, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 */
	public static void assume(final boolean cond,
			@NonNull final Class<? extends RuntimeException> exceptionClass,
			@NonNull final String errMsg,
			@NonNull final Object... params)
	{
		if (!cond)
		{
			final String errMsgFormated = StringUtils.formatMessage(errMsg, params);
			throwOrLogEx(exceptionClass, "Assumption failure: " + errMsgFormated);
		}
	}

	/**
	 * Assumes that <code>obj</code> is instanceof <code>interfaceClass</code>.
	 * <p>
	 * If <code>obj</code> is null, it's fine.
	 *
	 * @param objectName     user readable object name (i.e. variable name)
	 */
	public static void assumeInstanceOfOrNull(final Object obj, final Class<?> interfaceClass, final String objectName)
	{
		assumeInstanceOfOrNull(obj, interfaceClass, objectName, defaultExClazz);
	}

	/**
	 * Like {@link #assumeInstanceOfOrNull(Object, Class, String)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in
	 * {@link #setDefaultExClass(Class)}.
	 */
	public static void assumeInstanceOfOrNull(final Object obj, final Class<?> interfaceClass, final String objectName, final Class<? extends RuntimeException> exceptionClass)
	{
		if (obj == null)
		{
			return;
		}

		assumeNotNull(interfaceClass, exceptionClass, "interfaceClass not null");

		if (!interfaceClass.isAssignableFrom(obj.getClass()))
		{
			final String errMsg = "Object " + objectName + " (" + obj + ") does not implement " + interfaceClass;
			throwOrLogEx(exceptionClass, "Assumption failure: " + errMsg);
		}
	}

	/**
	 * Assumes that <code>obj</code> is <code>instanceof interfaceClass</code>.
	 * <p>
	 * If <code>obj</code> is <code>null</code>, then an exception will be thrown.
	 *
	 * @param objectName user readable object name (i.e. variable name)
	 */
	public static void assumeInstanceOf(final Object obj, final Class<?> interfaceClass, final String objectName)
	{
		assumeInstanceOf(obj, interfaceClass, objectName, defaultExClazz);
	}

	/**
	 * Like {@link #assumeInstanceOf(Object, Class, String)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 */
	public static void assumeInstanceOf(final Object obj, final Class<?> interfaceClass, final String objectName, final Class<? extends RuntimeException> exceptionClass)
	{
		assumeNotNull(obj, exceptionClass, "{} ({}) is not null", objectName, obj);
		assumeInstanceOfOrNull(obj, interfaceClass, objectName, exceptionClass);
	}

	/**
	 * Assumes that given <code>object</code> is not null
	 *
	 * @param assumptionMessage message
	 * @param params            message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	@NonNull
	public static <T> T assumeNotNull(@Nullable final T object, final String assumptionMessage, final Object... params)
	{
		return assumeNotNull(object, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotNull(Object, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 */
	@NonNull
	public static <T> T assumeNotNull(@Nullable final T object, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean cond = object != null;
		assume(cond, exceptionClass, assumptionMessage, params);
		return object;
	}

	public static <T> T assumePresent(@NonNull final Optional<T> optional, final String assumptionMessage, final Object... params)
	{
		return assumePresent(optional, defaultExClazz, assumptionMessage, params);
	}

	public static <T> T assumePresent(@NonNull final Optional<T> optional, @NonNull final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		assume(optional.isPresent(), exceptionClass, assumptionMessage, params);
		return optional.get();
	}

	/**
	 * Assumes that given <code>object</code> is null
	 *
	 * @param assumptionMessage message
	 * @param params            message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	public static void assumeNull(@Nullable final Object object, final String assumptionMessage, final Object... params)
	{
		assumeNull(object, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotNull(Object, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 */
	public static void assumeNull(final Object object, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean cond = object == null;
		assume(cond, exceptionClass, assumptionMessage, params);
	}

	/**
	 * Assumes that given <code>str</code> string is not empty. When checking, whitespaces will be discarded, so a string which contains only whitespaces will be considered as empty.
	 *
	 * @param params message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 * @see #isEmpty(String, boolean)
	 */
	public static String assumeNotEmpty(@Nullable final String str, final String assumptionMessage, final Object... params)
	{
		return assumeNotEmpty(str, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotEmpty(String, String, Object...)},
	 * but throws an instance of the given <code>exceptionClass</code>
	 * instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 * <p>
	 * Also see {@link ExceptionWithOwnHeaderMessage}
	 */
	public static String assumeNotEmpty(@Nullable final String str, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean trimWhitespaces = true;
		final boolean cond = !isEmpty(str, trimWhitespaces);

		assume(cond, exceptionClass, assumptionMessage, params);
		return str;
	}

	/**
	 * Assumes that given <code>collection</code> is not null or empty.
	 *
	 * @param assumptionMessage message
	 * @param params            message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	public static <T extends Collection<? extends Object>> T assumeNotEmpty(
			final T collection,
			final String assumptionMessage,
			final Object... params)
	{
		return assumeNotEmpty(collection, defaultExClazz, assumptionMessage, params);
	}

	public static <T> T assumeNotEmpty(
			final Optional<T> optional,
			final String assumptionMessage,
			final Object... params)
	{
		final boolean cond = optional.isPresent();

		assume(cond, defaultExClazz, assumptionMessage, params);
		return optional.get();
	}

	/**
	 * Like {@link #assumeNotEmpty(Collection, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in
	 * {@link #setDefaultExClass(Class)}.
	 */
	public static <T extends Collection<? extends Object>> T assumeNotEmpty(final T collection, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean cond = !isEmpty(collection);

		assume(cond, exceptionClass, assumptionMessage, params);
		return collection;
	}

	/**
	 * Assumes that given <code>array</code> is not null or empty.
	 *
	 * @param assumptionMessage message
	 * @param params            message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	public static <T> void assumeNotEmpty(final T[] array, final String assumptionMessage, final Object... params)
	{
		assumeNotEmpty(array, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotEmpty(Object[], String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 */
	public static <T> void assumeNotEmpty(final T[] array, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean cond = array != null && array.length > 0;

		assume(cond, exceptionClass, assumptionMessage, params);
	}

	/**
	 * Assumes that given <code>map</code> is not null or empty.
	 *
	 * @param assumptionMessage message
	 * @param params            message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	public static void assumeNotEmpty(final Map<?, ?> map, final String assumptionMessage, final Object... params)
	{
		assumeNotEmpty(map, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotEmpty(Map, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 */
	public static void assumeNotEmpty(final Map<?, ?> map, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean cond = map != null && !map.isEmpty();
		assume(cond, exceptionClass, assumptionMessage, params);
	}

	public static int assumeGreaterThanZero(final int valueInt, final String valueName)
	{
		if (valueInt <= 0)
		{
			throwOrLogEx(defaultExClazz, "Assumption failure: " + valueName + " > 0 but it was " + valueInt);
		}
		return valueInt;
	}

	public static int assumeGreaterThanZero(
			final int valueInt,
			final Class<? extends RuntimeException> exceptionClass,
			final String valueName)
	{
		if (valueInt <= 0)
		{
			throwOrLogEx(exceptionClass, "Assumption failure: " + valueName + " > 0 but it was " + valueInt);
		}
		return valueInt;
	}

	public static long assumeGreaterThanZero(final long valueLong, final String valueName)
	{
		if (valueLong <= 0)
		{
			throwOrLogEx(defaultExClazz, "Assumption failure: " + valueName + " > 0 but it was " + valueLong);
		}
		return valueLong;
	}

	public static BigDecimal assumeGreaterThanZero(final BigDecimal valueBD, final String valueName)
	{
		assumeNotNull(valueName, "" + valueName + " is not null");
		if (valueBD == null || valueBD.signum() <= 0)
		{
			throwOrLogEx(defaultExClazz, "Assumption failure: " + valueName + " > 0 but it was " + valueBD);
		}
		return valueBD;
	}

	public static int assumeGreaterOrEqualToZero(final int valueInt, final String valueName)
	{
		if (valueInt < 0)
		{
			throwOrLogEx(defaultExClazz, "Assumption failure: " + valueName + " >= 0 but it was " + valueInt);
		}
		return valueInt;
	}

	public static BigDecimal assumeGreaterOrEqualToZero(final BigDecimal valueBD, final String valueName)
	{
		assumeNotNull(valueName, "" + valueName + " is not null");
		if (valueBD == null || valueBD.signum() < 0)
		{
			throwOrLogEx(defaultExClazz, "Assumption failure: " + valueName + " >= 0 but it was " + valueBD);
		}
		return valueBD;
	}

	public static <T> void assumeEquals(final T value1, final T value2)
	{
		if (Objects.equals(value1, value2))
		{
			return;
		}

		fail("values not equal: '{}', '{}'", value1, value2);
	}

	public static <T> void assumeEquals(final T value1, final T value2, final String assumptionMessage, final Object... params)
	{
		if (Objects.equals(value1, value2))
		{
			return;
		}

		fail(assumptionMessage, params);
	}

	/**
	 * This method similar to {@link #assume(boolean, String, Object...)}, but the message should be formulated in terms of an error message instead of an assumption.
	 * <p>
	 * Example: instead of "parameter 'xy' is not null" (description of the assumption that was violated), one should write "parameter 'xy' is null" (description of the error).
	 */
	public static void errorUnless(final boolean cond, final String errMsg, final Object... params)
	{
		errorUnless(cond, defaultExClazz, errMsg, params);
	}

	/**
	 * Like {@link #errorUnless(boolean, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 */
	public static void errorUnless(final boolean cond, final Class<? extends RuntimeException> exceptionClass, final String errMsg, final Object... params)
	{
		if (!cond)
		{
			final String errMsgFormated = StringUtils.formatMessage(errMsg, params);
			throwOrLogEx(exceptionClass, "Error: " + errMsgFormated);
		}
	}

	public static void errorIf(
			final boolean cond,
			final String errMsg,
			final Object... params)
	{
		errorIf(cond, defaultExClazz, errMsg, params);
	}

	/**
	 * This method similar to {@link #assume(boolean, String, Object...)}, the error is thrown <b>if the condition is true</b> and the message should be formulated in terms of an error message instead
	 * of an assumption.
	 * <p>
	 * Example: instead of "parameter 'xy' is not null" (description of the assumption that was violated), one should write "parameter 'xy' is null" (description of the error).
	 */
	public static void errorIf(
			final boolean cond,
			final Class<? extends RuntimeException> exceptionClass,
			final String errMsg,
			final Object... params)
	{
		if (cond)
		{
			final String errMsgFormated = StringUtils.formatMessage(errMsg, params);
			throwOrLogEx(exceptionClass, "Error: " + errMsgFormated);
		}
	}

	public static RuntimeException fail(final String errMsg, final Object... params)
	{
		final String errMsgFormated = StringUtils.formatMessage(errMsg, params);
		return throwOrLogEx(defaultExClazz, "Error: " + errMsgFormated);
	}

	/**
	 * Supplier for an exception. Can be used with {@link Optional#orElseThrow(Supplier)}.
	 */
	public static Supplier<? extends RuntimeException> supplyEx(final String errMsg, final Object... params)
	{
		return supplyEx(errMsg, defaultExClazz, params);
	}

	/**
	 * Like {@link #supplyEx(String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 */
	public static Supplier<? extends RuntimeException> supplyEx(final String errMsg, final Class<? extends RuntimeException> exceptionClass, final Object... params)
	{
		return () -> {
			final String errMsgFormated = StringUtils.formatMessage(errMsg, params);
			final RuntimeException ex = mkEx(exceptionClass, errMsgFormated);
			return ex;
		};
	}

	public static RuntimeException newException(final String errMsg, final Object... params)
	{
		final String errMsgFormated = StringUtils.formatMessage(errMsg, params);
		return mkEx(defaultExClazz, errMsgFormated);
	}

	public static boolean isEmpty(@Nullable final Object value)
	{
		return EmptyUtil.isEmpty(value);
	}

	public static boolean isEmpty(@Nullable final String str)
	{
		return EmptyUtil.isEmpty(str);
	}

	/**
	 * @return return true if the string is null, has length 0, or contains only whitespace.
	 */
	public static boolean isBlank(@Nullable final String str)
	{
		return EmptyUtil.isBlank(str);
	}

	/**
	 * @return return true if the string is not null, has length > 0, and does not contain only whitespace.
	 */
	public static boolean isNotBlank(@Nullable final String str)
	{
		return EmptyUtil.isNotBlank(str);
	}

	/**
	 * Is String Empty
	 *
	 * @param str             string
	 * @param trimWhitespaces trim whitespaces
	 * @return true if >= 1 char
	 */
	public static boolean isEmpty(@Nullable final String str, final boolean trimWhitespaces)
	{
		return EmptyUtil.isEmpty(str, trimWhitespaces);
	}

	/**
	 * @return true if bd is null or bd.signum() is zero
	 */
	public static boolean isEmpty(final BigDecimal bd)
	{
		return EmptyUtil.isEmpty(bd);
	}

	/**
	 * @return true if the array is null or it's length is zero.
	 */
	public static <T> boolean isEmpty(final T[] arr)
	{
		return EmptyUtil.isEmpty(arr);
	}

	/**
	 * @return true if given collection is <code>null</code> or it has no elements
	 */
	public static boolean isEmpty(@Nullable final Collection<?> collection)
	{
		return EmptyUtil.isEmpty(collection);
	}

	/**
	 * Tests whether two objects are equals.
	 *
	 * <p>
	 * It takes care of the null case. Thus, it is helpful to implement Object.equals.
	 *
	 * <p>
	 * Notice: it uses compareTo if BigDecimal is found. So, in this case, a.equals(b) might not be the same as Objects.equals(a, b).
	 *
	 * <p>
	 * If both a and b are Object[], they are compared item-by-item.
	 * <p>
	 * NOTE: this is a copy paste from org.zkoss.lang.Objects.equals(Object, Object)
	 *
	 * @deprecated: as of java-8, there is {@link Objects#equals(Object, Object)}. Please use that instead.
	 */
	@Deprecated
	public static boolean equals(final Object a, final Object b)
	{
		if (a == b || a != null && b != null && a.equals(b))
		{
			return true;
		}
		if (a instanceof BigDecimal && b instanceof BigDecimal)
		{
			return ((BigDecimal)a).compareTo((BigDecimal)b) == 0;
		}

		if (a == null || !a.getClass().isArray())
		{
			return false;
		}

		if (a instanceof Object[] && b instanceof Object[])
		{
			final Object[] as = (Object[])a;
			final Object[] bs = (Object[])b;
			if (as.length != bs.length)
			{
				return false;
			}
			for (int j = as.length; --j >= 0; )
			{
				if (!equals(as[j], bs[j]))
				{
					return false;
				}
			}
			return true;
		}
		if (a instanceof int[] && b instanceof int[])
		{
			final int[] as = (int[])a;
			final int[] bs = (int[])b;
			if (as.length != bs.length)
			{
				return false;
			}
			for (int j = as.length; --j >= 0; )
			{
				if (as[j] != bs[j])
				{
					return false;
				}
			}
			return true;
		}
		if (a instanceof byte[] && b instanceof byte[])
		{
			final byte[] as = (byte[])a;
			final byte[] bs = (byte[])b;
			if (as.length != bs.length)
			{
				return false;
			}
			for (int j = as.length; --j >= 0; )
			{
				if (as[j] != bs[j])
				{
					return false;
				}
			}
			return true;
		}
		if (a instanceof char[] && b instanceof char[])
		{
			final char[] as = (char[])a;
			final char[] bs = (char[])b;
			if (as.length != bs.length)
			{
				return false;
			}
			for (int j = as.length; --j >= 0; )
			{
				if (as[j] != bs[j])
				{
					return false;
				}
			}
			return true;
		}
		if (a instanceof long[] && b instanceof long[])
		{
			final long[] as = (long[])a;
			final long[] bs = (long[])b;
			if (as.length != bs.length)
			{
				return false;
			}
			for (int j = as.length; --j >= 0; )
			{
				if (as[j] != bs[j])
				{
					return false;
				}
			}
			return true;
		}
		if (a instanceof short[] && b instanceof short[])
		{
			final short[] as = (short[])a;
			final short[] bs = (short[])b;
			if (as.length != bs.length)
			{
				return false;
			}
			for (int j = as.length; --j >= 0; )
			{
				if (as[j] != bs[j])
				{
					return false;
				}
			}
			return true;
		}
		if (a instanceof double[] && b instanceof double[])
		{
			final double[] as = (double[])a;
			final double[] bs = (double[])b;
			if (as.length != bs.length)
			{
				return false;
			}
			for (int j = as.length; --j >= 0; )
			{
				if (as[j] != bs[j])
				{
					return false;
				}
			}
			return true;
		}
		if (a instanceof float[] && b instanceof float[])
		{
			final float[] as = (float[])a;
			final float[] bs = (float[])b;
			if (as.length != bs.length)
			{
				return false;
			}
			for (int j = as.length; --j >= 0; )
			{
				if (as[j] != bs[j])
				{
					return false;
				}
			}
			return true;
		}
		if (a instanceof boolean[] && b instanceof boolean[])
		{
			final boolean[] as = (boolean[])a;
			final boolean[] bs = (boolean[])b;
			if (as.length != bs.length)
			{
				return false;
			}
			for (int j = as.length; --j >= 0; )
			{
				if (as[j] != bs[j])
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
