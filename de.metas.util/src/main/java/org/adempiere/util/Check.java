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

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;

/**
 *
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
	 *
	 * @param clazz
	 */
	public static void setDefaultExClass(final Class<? extends RuntimeException> clazz)
	{
		defaultExClazz = clazz;
	}

	/**
	 * Decides if an exception shall be thrown on a failed assumption. Note that if this is set to <code>false</code>, then also {@link #setLogger(Logger)} needs to be called to set a logger.
	 * Otherwise the <code>false</code> parameter will be ingored.
	 *
	 * @param throwException
	 */
	public static void setThrowException(final boolean throwException)
	{
		Check.throwException = throwException;
	}

	/**
	 * If an exception is thrown and a message was set with this method, then that message plus a line-break is prepended to the exception message.
	 *
	 * @param exceptionHeaderMessage
	 */
	public static void setExceptionHeaderMessage(final String exceptionHeaderMessage)
	{
		Check.exceptionHeaderMessage = exceptionHeaderMessage;
	}

	/**
	 * Set a logger to be used by this class to <b>log</b> exceptions instead of throwing them, if {@link #setThrowException(boolean)} was called with <code>false</code>.
	 *
	 * @param logger
	 */
	public static void setLogger(final Logger logger)
	{
		Check.logger = logger;
	}

	private static RuntimeException mkEx(final Class<? extends RuntimeException> exClazz, final String msg)
	{
		final StringBuilder msgToUse = new StringBuilder();
		if (!Check.isEmpty(exceptionHeaderMessage))
		{
			msgToUse.append(exceptionHeaderMessage);
			msgToUse.append("\n\n");
		}
		msgToUse.append(msg);

		try
		{
			final Constructor<? extends RuntimeException> c = exClazz.getConstructor(String.class);
			final RuntimeException ex = c.newInstance(msgToUse.toString());
			return ex;
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Failure throwing exception with class '" + exClazz + "' and message '" + msg + "'", e);
		}
	}

	private static void throwOrLogEx(final Class<? extends RuntimeException> exClazz, final String msg)
	{
		final RuntimeException ex = mkEx(exClazz, msg);

		Loggables.get().addLog("{}; Exception: {}", msg, ex);

		if (throwException || logger == null)
		{
			throw ex;
		}
		else
		{
			logger.error(msg, ex);
		}
	}

	/**
	 * Little method that throws an {@link AdempiereException} if the given boolean condition is false. It might be a good idea to use "assume" instead of the assert keyword, because
	 * <li>assert is
	 * globally switched on and off and you never know what else libs are using assert</li>
	 * <li>there are critical assumptions that should always be validated. Not only during development time or when
	 * someone minds to use the -ea cmdline parameter</li>
	 *
	 * @param cond
	 * @param errMsg the error message to pass to the assertion error, if the condition is <code>false</code>
	 * @param params message parameters (@see {@link MessageFormat})
	 */
	public static void assume(final boolean cond, final String errMsg, final Object... params)
	{
		assume(cond, defaultExClazz, errMsg, params);
	}

	/**
	 * Like {@link #assume(boolean, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 *
	 * @param cond
	 * @param exceptionClass
	 * @param errMsg
	 * @param params
	 */
	public static void assume(final boolean cond, final Class<? extends RuntimeException> exceptionClass, final String errMsg, final Object... params)
	{
		if (!cond)
		{
			final String errMsgFormated = StringUtils.formatMessage(errMsg, params);
			throwOrLogEx(exceptionClass, "Assumption failure: " + errMsgFormated);
		}
	}

	/**
	 * Assumes that <code>obj</code> is instanceof <code>interfaceClass</code>.
	 *
	 * If <code>obj</code> is null, it's fine.
	 *
	 * @param obj
	 * @param interfaceClass
	 * @param objectName user readable object name (i.e. variable name)
	 */
	public static void assumeInstanceOfOrNull(final Object obj, final Class<?> interfaceClass, final String objectName)
	{
		assumeInstanceOfOrNull(obj, interfaceClass, objectName, defaultExClazz);
	}

	/**
	 * Like {@link #assumeInstanceOfOrNull(Object, Class, String)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in
	 * {@link #setDefaultExClass(Class)}.
	 *
	 * @param obj
	 * @param interfaceClass
	 * @param objectName
	 * @param exceptionClass
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
	 *
	 * If <code>obj</code> is <code>null</code>, then an exception will be thrown.
	 *
	 * @param obj
	 * @param interfaceClass
	 * @param objectName user readable object name (i.e. variable name)
	 */
	public static void assumeInstanceOf(final Object obj, final Class<?> interfaceClass, final String objectName)
	{
		assumeInstanceOf(obj, interfaceClass, objectName, defaultExClazz);
	}

	/**
	 * Like {@link #assumeInstanceOf(Object, Class, String)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 *
	 * @param obj
	 * @param interfaceClass
	 * @param objectName
	 * @param exceptionClass
	 */
	public static void assumeInstanceOf(final Object obj, final Class<?> interfaceClass, final String objectName, final Class<? extends RuntimeException> exceptionClass)
	{
		assumeNotNull(obj, exceptionClass, "{} ({}) is not null", objectName, obj);
		assumeInstanceOfOrNull(obj, interfaceClass, objectName, exceptionClass);
	}

	/**
	 * Assumes that given <code>object</code> is not null
	 *
	 * @param object
	 * @param assumptionMessage message
	 * @param params message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	public static void assumeNotNull(final Object object, final String assumptionMessage, final Object... params)
	{
		assumeNotNull(object, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotNull(Object, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 *
	 * @param object
	 * @param exceptionClass
	 * @param assumptionMessage
	 * @param params
	 */
	public static void assumeNotNull(final Object object, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean cond = object != null;
		assume(cond, exceptionClass, assumptionMessage, params);
	}

	/**
	 * Assumes that given <code>object</code> is null
	 *
	 * @param object
	 * @param assumptionMessage message
	 * @param params message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	public static void assumeNull(final Object object, final String assumptionMessage, final Object... params)
	{
		assumeNull(object, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotNull(Object, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 *
	 * @param object
	 * @param exceptionClass
	 * @param assumptionMessage
	 * @param params
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
	public static String assumeNotEmpty(final String str, final String assumptionMessage, final Object... params)
	{
		return assumeNotEmpty(str, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotEmpty(String, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 *
	 * @param str
	 * @param exceptionClass
	 * @param assumptionMessage
	 * @param params
	 */
	public static String assumeNotEmpty(final String str, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean trimWhitespaces = true;
		final boolean cond = !isEmpty(str, trimWhitespaces);

		assume(cond, exceptionClass, assumptionMessage, params);
		return str;
	}

	/**
	 * Assumes that given <code>collection</code> is not null or empty.
	 *
	 * @param collection
	 * @param assumptionMessage message
	 * @param params message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	public static void assumeNotEmpty(final Collection<? extends Object> collection, final String assumptionMessage, final Object... params)
	{
		assumeNotEmpty(collection, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotEmpty(Collection, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in
	 * {@link #setDefaultExClass(Class)}.
	 *
	 * @param collection
	 * @param exceptionClass
	 * @param assumptionMessage
	 * @param params
	 */
	public static void assumeNotEmpty(final Collection<? extends Object> collection, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean cond = !isEmpty(collection);

		assume(cond, exceptionClass, assumptionMessage, params);
	}

	/**
	 * Assumes that given <code>array</code> is not null or empty.
	 *
	 * @param array
	 * @param assumptionMessage message
	 * @param params message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	public static <T> void assumeNotEmpty(final T[] array, final String assumptionMessage, final Object... params)
	{
		assumeNotEmpty(array, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotEmpty(Object[], String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 *
	 * @param array
	 * @param exceptionClass
	 * @param assumptionMessage
	 * @param params
	 */
	public static <T> void assumeNotEmpty(final T[] array, final Class<? extends RuntimeException> exceptionClass, final String assumptionMessage, final Object... params)
	{
		final boolean cond = array != null && array.length > 0;

		assume(cond, exceptionClass, assumptionMessage, params);
	}

	/**
	 * Assumes that given <code>map</code> is not null or empty.
	 *
	 * @param map
	 * @param assumptionMessage message
	 * @param params message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	public static void assumeNotEmpty(final Map<?, ?> map, final String assumptionMessage, final Object... params)
	{
		assumeNotEmpty(map, defaultExClazz, assumptionMessage, params);
	}

	/**
	 * Like {@link #assumeNotEmpty(Map, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 *
	 * @param map
	 * @param exceptionClass
	 * @param assumptionMessage
	 * @param params
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

	/**
	 * This method similar to {@link #assume(boolean, String, Object...)}, but the message should be formulated in terms of an error message instead of an assumption.
	 * <p>
	 * Example: instead of "parameter 'xy' is not null" (description of the assumption that was violated), one should write "parameter 'xy' is null" (description of the error).
	 *
	 * @param cond
	 * @param errMsg
	 * @param params
	 */
	public static void errorUnless(final boolean cond, final String errMsg, final Object... params)
	{
		errorUnless(cond, defaultExClazz, errMsg, params);
	}

	/**
	 * Like {@link #errorUnless(boolean, String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 *
	 * @param cond
	 * @param exceptionClass
	 * @param errMsg
	 * @param params
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
	 *
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

	public static void fail(final String errMsg, final Object... params)
	{
		final String errMsgFormated = StringUtils.formatMessage(errMsg, params);
		throwOrLogEx(defaultExClazz, "Error: " + errMsgFormated);
	}

	/**
	 * Supplier for an exception. Can be used with {@link Optional#orElseThrow(Supplier)}.
	 *
	 * @param errMsg
	 * @param params
	 * @return
	 */
	public static Supplier<? extends RuntimeException> supplyEx(final String errMsg, final Object... params)
	{
		return supplyEx(errMsg, defaultExClazz, params);
	}

	/**
	 * Like {@link #supplyEx(String, Object...)}, but throws an instance of the given <code>exceptionClass</code> instead of the one which was set in {@link #setDefaultExClass(Class)}.
	 *
	 * @param errMsg
	 * @param exceptionClass
	 * @param params
	 * @return
	 */
	public static Supplier<? extends RuntimeException> supplyEx(final String errMsg, final Class<? extends RuntimeException> exceptionClass, final Object... params)
	{
		return () -> {
			final String errMsgFormated = StringUtils.formatMessage(errMsg, params);
			final RuntimeException ex = mkEx(exceptionClass, errMsgFormated);
			return ex;
		};
	}

	public static boolean isEmpty(final String str)
	{
		return isEmpty(str, false);
	}

	/**
	 * Is String Empty
	 *
	 * @param str string
	 * @param trimWhitespaces trim whitespaces
	 * @return true if >= 1 char
	 */
	public static boolean isEmpty(final String str, final boolean trimWhitespaces)
	{
		if (str == null)
		{
			return true;
		}
		if (trimWhitespaces)
		{
			return str.trim().length() == 0;
		}
		else
		{
			return str.length() == 0;
		}
	}	// isEmpty

	/**
	 *
	 * @param bd
	 * @return true if bd is null or bd.signum() is zero
	 */
	public static boolean isEmpty(final BigDecimal bd)
	{
		return bd == null || bd.signum() == 0;
	}

	/**
	 * @return true if the array is null or it's length is zero.
	 */
	public static <T> boolean isEmpty(final T[] arr)
	{
		return arr == null || arr.length == 0;
	}

	/**
	 *
	 * @param collection
	 * @return true if given collection is <code>null</code> or it has no elements
	 */
	public static <T> boolean isEmpty(final Collection<T> collection)
	{
		return collection == null || collection.isEmpty();
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
	 *
	 * NOTE: this is a copy paste from org.zkoss.lang.Objects.equals(Object, Object)
	 *
	 * @deprecated: as of java-8, there is {@link Objects#equals(Object, Object)}. Please use that instead.
	 */
	@Deprecated
	public static final boolean equals(final Object a, final Object b)
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
			for (int j = as.length; --j >= 0;)
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
			for (int j = as.length; --j >= 0;)
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
			for (int j = as.length; --j >= 0;)
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
			for (int j = as.length; --j >= 0;)
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
			for (int j = as.length; --j >= 0;)
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
			for (int j = as.length; --j >= 0;)
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
			for (int j = as.length; --j >= 0;)
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
			for (int j = as.length; --j >= 0;)
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
			for (int j = as.length; --j >= 0;)
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
