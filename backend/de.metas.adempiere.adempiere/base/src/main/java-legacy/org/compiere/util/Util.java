/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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
package org.compiere.util;

import com.google.common.io.BaseEncoding;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.reflect.ClassInstanceProvider;
import org.adempiere.util.reflect.IClassInstanceProvider;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Objects;

/**
 * General Utilities
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - BF [ 1748346 ]
 * @version $Id: Util.java,v 1.3 2006/07/30 00:52:23 jjanke Exp $
 */
public class Util
{
	/**
	 * Logger
	 */
	private static Logger log = LogManager.getLogger(Util.class.getName());

	/**
	 * Clean Ampersand (used to indicate shortcut)
	 *
	 * @param in input
	 * @return cleaned string
	 */
	public static String cleanAmp(String in)
	{
		if (in == null || in.length() == 0)
		{
			return in;
		}
		final int pos = in.indexOf('&');
		if (pos == -1)
		{
			return in;
		}
		//
		if (pos + 1 < in.length() && in.charAt(pos + 1) != ' ')
		{
			in = in.substring(0, pos) + in.substring(pos + 1);
		}
		return in;
	}    // cleanAmp

	/**
	 * Trim to max byte size
	 *
	 * @param str  string
	 * @param size max size in bytes
	 * @return string
	 */
	public static String trimSize(String str, int size)
	{
		if (str == null)
		{
			return str;
		}
		if (size <= 0)
		{
			throw new IllegalArgumentException("Trim size invalid: " + size);
		}
		// Assume two byte code
		final int length = str.length();
		if (length < size / 2)
		{
			return str;
		}
		try
		{
			final byte[] bytes = str.getBytes("UTF-8");
			if (bytes.length <= size)
			{
				return str;
			}
			// create new - may cut last character in half
			final byte[] result = new byte[size];
			System.arraycopy(bytes, 0, result, 0, size);
			return new String(result, "UTF-8");
		}
		catch (final UnsupportedEncodingException e)
		{
			log.error(str, e);
		}
		return str;
	}    // trimSize

	private static IClassInstanceProvider classInstanceProvider = ClassInstanceProvider.instance; // default/production implementation.

	/**
	 * Sets an alternative {@link IClassInstanceProvider} implementation. Intended use is for testing. This method is called by {@link org.adempiere.test.AdempiereTestHelper#init()}.
	 * Also see {@link org.adempiere.util.reflect.TestingClassInstanceProvider}.
	 *
	 * @param classInstanceProvider
	 */
	public static void setClassInstanceProvider(final IClassInstanceProvider classInstanceProvider)
	{
		Util.classInstanceProvider = classInstanceProvider;
	}

	/**
	 * Loads the class with <code>classname</code> and makes sure that it's implementing given <code>interfaceClazz</code>.
	 *
	 * @param interfaceClazz
	 * @param classname
	 * @return loaded class
	 * @see #setClassInstanceProvider(IClassInstanceProvider)
	 */
	public static final <T> Class<? extends T> loadClass(final Class<T> interfaceClazz, final String classname)
	{
		Check.assumeNotNull(classname, "className is not null");
		try
		{
			final Class<?> instanceClazz = classInstanceProvider.provideClass(classname);

			Check.errorUnless(interfaceClazz.isAssignableFrom(instanceClazz), "Class {} doesn't implement {}", instanceClazz, interfaceClazz);

			@SuppressWarnings("unchecked") final Class<? extends T> instanceClassCasted = (Class<? extends T>)instanceClazz;
			return instanceClassCasted;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Unable to instantiate '" + classname + "' implementing " + interfaceClazz, e);
		}
	}

	/**
	 * Creates a new instance of given <code>instanceClazz</code>.
	 * Also it makes sure that it's implementing given <code>interfaceClass</code>.
	 *
	 * @param interfaceClazz
	 * @param instanceClazz
	 * @return instance
	 * @see #setClassInstanceProvider(IClassInstanceProvider)
	 */
	public static final <T> T newInstance(final Class<T> interfaceClazz, final Class<?> instanceClazz)
	{
		try
		{
			return classInstanceProvider.provideInstance(interfaceClazz, instanceClazz);
		}
		catch (final ReflectiveOperationException e)
		{
			final Throwable cause = AdempiereException.extractCause(e);
			throw cause instanceof AdempiereException
					? (AdempiereException)cause
					: new AdempiereException("Unable to instantiate '" + instanceClazz + "' implementing " + interfaceClazz, cause);
		}
	}

	public static Class<?> validateJavaClassname(
			@NonNull final String classname,
			@Nullable final Class<?> parentClass)
	{
		if (Check.isBlank(classname))
		{
			throw Check.mkEx("Given classname is blank");
		}
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null)
		{
			cl = Util.class.getClassLoader();
		}

		final Class<?> clazz;
		try
		{
			clazz = cl.loadClass(classname);
		}
		catch (final ClassNotFoundException e)
		{
			throw Check.mkEx("Classname not found: " + classname, e);
		}

		if (parentClass != null)
		{
			if (!parentClass.isAssignableFrom(clazz))
			{
				throw Check.mkEx("Class " + clazz + " is not assignable from " + parentClass);
			}
		}

		return clazz;
	}

	/**
	 * Create an instance of given className.
	 * <p>
	 * This method works exactly like {@link #getInstanceOrNull(Class, String)} but it also throws and {@link AdempiereException} if class was not found.
	 * <p>
	 * For unit testing, see {@link org.adempiere.util.reflect.TestingClassInstanceProvider#throwExceptionForClassName(String, RuntimeException)}.
	 *
	 * @param interfaceClazz interface class or super class that needs to be implemented by class. May be <code>NULL</code>. If set, then the method will check if the given class name extends this
	 *                       param value.
	 * @param className      class name
	 * @return instance
	 * @throws AdempiereException if class does not implement given interface or if there is an error on instantiation or if class was not found
	 */
	public static <T> T getInstance(final Class<T> interfaceClazz, final String className)
	{
		Check.assumeNotNull(className, "className is not null");
		try
		{
			final Class<?> clazz = classInstanceProvider.provideClass(className);

			if (interfaceClazz != null)
			{
				return classInstanceProvider.provideInstance(interfaceClazz, clazz);
			}
			else
			{
				final Object instanceObj = clazz.newInstance();
				@SuppressWarnings("unchecked") final T instance = (T)instanceObj;
				return instance;
			}
		}
		catch (final ReflectiveOperationException e)
		{
			throw new AdempiereException("Unable to instantiate '" + className + "' implementing " + interfaceClazz, e);
		}
	}

	/**
	 * Create an instance of given className.
	 * <p>
	 * For unit testing, see {@link org.adempiere.util.reflect.TestingClassInstanceProvider#throwExceptionForClassName(String, RuntimeException)}.
	 *
	 * @param interfaceClazz interface class that needs to be implemented by class
	 * @param className      class name
	 * @return instance or null if class was not found
	 * @throws AdempiereException if class does not implement given interface or if there is an error on instantiation
	 */
	public static <T> T getInstanceOrNull(final Class<T> interfaceClazz, final String className)
	{
		Check.assumeNotNull(className, "className may not be null");
		Check.assumeNotNull(interfaceClazz, "interfaceClazz may not be null");
		try
		{
			final Class<?> clazz = classInstanceProvider.provideClass(className);
			return classInstanceProvider.provideInstance(interfaceClazz, clazz);
		}
		catch (final ClassNotFoundException e)
		{
			return null;
		}
		catch (final ReflectiveOperationException e)
		{
			throw new AdempiereException("Unable to instantiate '" + className + "'", e);
		}
	}

	/**
	 * Returns an instance of {@link ArrayKey} that can be used as a key in HashSets and HashMaps.
	 *
	 * @param input
	 * @return
	 */
	public static ArrayKey mkKey(final Object... input)
	{
		return ArrayKey.of(input);
	}

	/**
	 * @return {@link ArrayKey} builder
	 */
	public static ArrayKeyBuilder mkKey()
	{
		return ArrayKey.builder();
	}

	/**
	 * Immutable wrapper for arrays that uses {@link Arrays#hashCode(Object[])} and {@link Arrays#equals(Object)}. Instances of this class are obtained by {@link Util#mkKey(Object...)} and can be
	 * used as keys in hashmaps and hash sets.
	 * <p>
	 * Thanks to http://stackoverflow.com/questions/1595588/java-how-to-be-sure-to-store-unique-arrays-based-on -its-values-on-a-list
	 *
	 * @author ts
	 */
	@Immutable
	public static class ArrayKey implements Comparable<ArrayKey>
	{
		public static final String SEPARATOR = "#";

		public static final ArrayKey of(final Object... input)
		{
			return new ArrayKey(input);
		}

		public static final ArrayKeyBuilder builder()
		{
			return new ArrayKeyBuilder();
		}

		private final Object[] array;
		private String _stringBuilt = null;

		public ArrayKey(final Object... input)
		{
			this.array = input;
		}

		@Override
		public int hashCode()
		{
			return Arrays.hashCode(array);
		}

		@Override
		public boolean equals(final Object other)
		{
			if (this == other)
			{
				return true;
			}
			if (other instanceof ArrayKey)
			{
				return Arrays.equals(this.array, ((ArrayKey)other).array);
			}
			return false;
		}

		@Override
		public String toString()
		{
			String stringBuilt = this._stringBuilt;
			if (stringBuilt == null)
			{
				stringBuilt = _stringBuilt = buildToString();
			}
			return stringBuilt;
		}

		private String buildToString()
		{
			final StringBuilder sb = new StringBuilder();
			for (final Object k : array)
			{
				if (sb.length() > 0)
				{
					sb.append(SEPARATOR);
				}
				if (k == null)
				{
					sb.append("NULL");
				}
				else
				{
					sb.append(k.toString());
				}
			}

			return sb.toString();
		}

		@Override
		public int compareTo(final ArrayKey other)
		{
			if (this == other)
			{
				return 0;
			}

			if (other == null)
			{
				return -1; // NULLs last
			}

			// NOTE: ideally we would compare each array element, using natural order,
			// but for now, comparing the strings is sufficient
			return toString().compareTo(other.toString());
		}

		public static int compare(final ArrayKey key1, final ArrayKey key2)
		{
			if (key1 == key2)
			{
				return 0;
			}
			if (key1 == null)
			{
				return +1; // NULLs last
			}
			if (key2 == null)
			{
				return -1; // NULLs last
			}
			return key1.compareTo(key2);
		}
	}

	/**
	 * Tests whether two objects refer to the same object.
	 * <p>
	 * It's advisable to use this method instead of directly comparing those 2 objects by o1 == o2, because in this way you are telling to static analyzer tool that comparing by reference was your
	 * intention.
	 *
	 * @return true if objects are the same (i.e. o1 == o2)
	 */
	public static <T> boolean same(@Nullable final T o1, @Nullable final T o2)
	{
		return o1 == o2;
	}

	public static <T> boolean equals(@Nullable final T o1, @Nullable final T o2)
	{
		return Objects.equals(o1, o2);
	}

	/**
	 * Read given file and returns it as byte array
	 *
	 * @param file
	 * @return file contents as byte array
	 * @throws AdempiereException on any {@link IOException}
	 */
	public static byte[] readBytes(File file)
	{
		FileInputStream in = null;
		try
		{
			in = new FileInputStream(file);
			final byte[] data = readBytes(in);
			in = null; // stream was closed by readBytes(InputStream)

			return data;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Error reading file: " + file, e);
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
				in = null;
			}
		}
	}

	/**
	 * Read bytes from given InputStream. This method closes the stream.
	 *
	 * @param in
	 * @return stream contents as byte array
	 * @throws AdempiereException on error
	 */
	public static byte[] readBytes(final InputStream in)
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final byte[] buf = new byte[4096];

		try
		{
			int len = -1;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Error reading stream", e);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		return out.toByteArray();
	}

	// metas: 03749
	public static String encodeBase64(final byte[] b)
	{
		return BaseEncoding.base64().encode(b);
	}

	// metas: 03749
	public static byte[] decodeBase64(final String str)
	{
		return BaseEncoding.base64().decode(str);
	}

	// 03743
	public static void writeBytes(final File file, final byte[] data)
	{
		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(file, false);
			out.write(data);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Cannot write file " + file + "."
					+ "\n " + e.getLocalizedMessage() // also append the original error message because it could be helpful for user.
					, e);
		}
		finally
		{
			if (out != null)
			{
				close(out);
				out = null;
			}
		}
	}

	public static final void close(Closeable c)
	{
		try
		{
			c.close();
		}
		catch (final IOException e)
		{
			// e.printStackTrace();
		}
	}

	/**
	 * Writes the given {@link Throwable}s stack trace into a string.
	 *
	 * @param e
	 * @return
	 */
	public static String dumpStackTraceToString(Throwable e)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * Smart converting given exception to string
	 *
	 * @param e
	 * @return
	 */
	public static String getErrorMsg(Throwable e)
	{
		// save the exception for displaying to user
		String msg = e.getLocalizedMessage();
		if (Check.isEmpty(msg, true))
		{
			msg = e.getMessage();
		}
		if (Check.isEmpty(msg, true))
		{
			// note that e.g. a NullPointerException doesn't have a nice message
			msg = dumpStackTraceToString(e);
		}

		return msg;
	}

	public static String replaceNonDigitCharsWithZero(String stringToModify)
	{
		final int size = stringToModify.length();

		final StringBuilder stringWithZeros = new StringBuilder();

		for (int i = 0; i < size; i++)
		{
			final char currentChar = stringToModify.charAt(i);

			if (!Character.isDigit(currentChar))
			{
				stringWithZeros.append('0');
			}
			else
			{
				stringWithZeros.append(currentChar);
			}
		}

		return stringWithZeros.toString();
	}

	public static int getMinimumOfThree(final int no1, final int no2, final int no3)
	{
		return no1 < no2 ? (no1 < no3 ? no1 : no3) : (no2 < no3 ? no2 : no3);
	}
}   // Util
