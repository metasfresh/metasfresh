package de.metas.util;

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

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class IOStreamUtils
{
	public static String toString(@NonNull final InputStream in)
	{
		return new String(toByteArray(in), StandardCharsets.UTF_8);
	}

	public static byte[] toByteArray(@NonNull final InputStream in) throws RuntimeException
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

	/**
	 * Close given closeable stream.
	 *
	 * No errors will be thrown.
	 *
	 * @param closeable
	 */
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

	/**
	 * Close all given input streams.
	 *
	 * No errors will be thrown.
	 *
	 * @param closeables
	 */
	public static void close(final Closeable... closeables)
	{
		if (closeables == null || closeables.length == 0)
		{
			return;
		}

		for (final Closeable closeable : closeables)
		{
			close(closeable);
		}
	}

	/**
	 * Copy data from input stream to output stream.
	 *
	 * NOTE: no matter what, both streams are closed after this call.
	 *
	 * @throws RuntimeException if something fails
	 */
	public static void copy(@NonNull final OutputStream out, @NonNull final InputStream in)
	{
		try
		{
			final byte[] buf = new byte[4 * 1024];
			int len = 0;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		finally
		{
			close(out);
			close(in);
		}
	}
}
