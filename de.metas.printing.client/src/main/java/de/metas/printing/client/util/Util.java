package de.metas.printing.client.util;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public final class Util
{
	private Util()
	{
		super();
	}

	public static byte[] toByteArray(final InputStream in) throws RuntimeException
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		try
		{
			final byte[] buf = new byte[1024 * 4];
			int len = -1;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}

		return out.toByteArray();
	}

	public static <T> T getInstance(final String classname, final Class<T> interfaceClazz)
	{
		if (classname == null)
		{
			throw new IllegalArgumentException("classname is null");
		}
		if (interfaceClazz == null)
		{
			throw new IllegalArgumentException("interfaceClazz is null");
		}
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = Util.class.getClassLoader();
			}
			final Class<?> clazz = classLoader.loadClass(classname);

			if (!interfaceClazz.isAssignableFrom(clazz))
			{
				throw new RuntimeException("Class " + classname + " doesn't implement " + interfaceClazz);
			}
			return clazz.asSubclass(interfaceClazz).newInstance();

		}
		catch (final ClassNotFoundException e)
		{
			throw new RuntimeException("Unable to instantiate '" + classname + "'", e);
		}
		catch (final InstantiationException e)
		{
			throw new RuntimeException("Unable to instantiate '" + classname + "'", e);
		}
		catch (final IllegalAccessException e)
		{
			throw new RuntimeException("Unable to instantiate '" + classname + "'", e);
		}
	}

	public static String changeFileExtension(final String filename, final String extension)
	{
		final int idx = filename.lastIndexOf(".");
		final StringBuilder sb = new StringBuilder();
		if (idx > 0)
		{
			sb.append(filename.substring(0, idx));
		}
		else
		{
			sb.append(filename);
		}

		if (extension == null || extension.trim().length() == 0)
		{
			return sb.toString();
		}

		sb.append(".").append(extension.trim());
		return sb.toString();
	}

	/**
	 *
	 * @param filename
	 * @return file extension or null
	 */
	public static String getFileExtension(final String filename)
	{
		final int idx = filename.lastIndexOf(".");
		if (idx > 0)
		{
			return filename.substring(idx + 1);
		}

		return null;
	}

	public static void close(final Closeable closeable)
	{
		if (closeable == null)
		{
			return;
		}

		try
		{
			closeable.close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String toString(final InputStream in)
	{
		return new String(toByteArray(in));
	}

}
