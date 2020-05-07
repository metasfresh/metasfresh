package de.metas.email;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import org.adempiere.util.Check;
import org.compiere.util.MimeType;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * A {@link DataSource} implementation that wraps a byte array.
 * <p>
 * This is basically a reimplementation of the class <code>org.compiere.util.ByteArrayDataSource</code> which was authored (according to the javadoc) by author John Mani, Bill Shannon and Spivak.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ByteArrayBackedDataSource implements DataSource
{
	private static final String DEFAULT_MimeType = MimeType.TYPE_TextPlain;

	private final String _name;
	private final String _mimeType;
	private final byte[] _data;

	/**
	 * Create a DataSource from a byte array
	 *
	 * @param name
	 * @param type type e.g. text/html (see {@link MimeType}.TYPE_*)
	 * @param data data
	 */
	public static final ByteArrayBackedDataSource of(final String name, final String mimeType, final byte[] data)
	{
		return new ByteArrayBackedDataSource(name, mimeType, data);
	}

	/**
	 * Create a DataSource from a byte array.
	 *
	 * The mime type will be extracted from <code>filename</code>.
	 *
	 * @param filename
	 * @param data data
	 */
	public static final ByteArrayBackedDataSource of(final String filename, final byte[] data)
	{
		Check.assumeNotEmpty(filename, "filename not empty");
		final String mimeType = MimeType.getMimeType(filename);
		return new ByteArrayBackedDataSource(filename, mimeType, data);
	}

	private ByteArrayBackedDataSource(final String name, final String mimeType, final byte[] data)
	{
		_mimeType = Check.isEmpty(mimeType, true) ? DEFAULT_MimeType : mimeType;

		Check.assumeNotNull(data, "data not null");
		_data = data;

		if (Check.isEmpty(name, true))
		{
			_name = "data" + MimeType.getExtensionByType(_mimeType);
		}
		else
		{
			_name = name.trim();
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", _name)
				.add("contentType", _mimeType)
				.add("data bytes", (_data == null ? 0 : _data.length))
				.toString();
	}

	/**
	 * @return file name
	 */
	@Override
	public String getName()
	{
		return _name;
	}

	@Override
	public InputStream getInputStream() throws IOException
	{
		if (_data == null)
		{
			throw new IOException("no data");
		}

		// a new stream must be returned each time.
		return new ByteArrayInputStream(_data);
	}

	/**
	 * Throws exception
	 *
	 * @throws IOException
	 */
	@Override
	public OutputStream getOutputStream() throws IOException
	{
		throw new IOException("cannot do this");
	}

	/**
	 * Get Content Type
	 *
	 * @return MIME type e.g. text/html
	 */
	@Override
	public String getContentType()
	{
		return _mimeType;
	}
}
