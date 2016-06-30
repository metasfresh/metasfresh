/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

/**
 * Byte array {@link DataSource}.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public final class ByteArrayDataSource implements DataSource
{
	/**
	 * Create a DataSource from a byte array
	 *
	 * @param name
	 * @param type type e.g. text/html (see {@link MimeType}.TYPE_*)
	 * @param data data
	 */
	public static final ByteArrayDataSource of(final String name, final String mimeType, final byte[] data)
	{
		return new ByteArrayDataSource(name, mimeType, data);
	}

	/**
	 * Create a DataSource from a byte array.
	 *
	 * The mime type will be extracted from <code>filename</code>.
	 *
	 * @param filename
	 * @param data data
	 */
	public static final ByteArrayDataSource of(final String filename, final byte[] data)
	{
		Check.assumeNotEmpty(filename, "filename not empty");
		final String mimeType = MimeType.getMimeType(filename);
		return new ByteArrayDataSource(filename, mimeType, data);
	}

	private ByteArrayDataSource(final String name, final String mimeType, final byte[] data)
	{
		super();

		m_mimeType = Check.isEmpty(mimeType, true) ? DEFAULT_MimeType : mimeType;

		Check.assumeNotNull(data, "data not null");
		m_data = data;

		if (Check.isEmpty(name, true))
		{
			m_name = "data" + MimeType.getExtensionByType(m_mimeType);
		}
		else
		{
			m_name = name.trim();
		}
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", m_name)
				.add("contentType", m_mimeType)
				.add("data bytes", (m_data == null ? 0 : m_data.length))
				.toString();
	}

	/** Name **/
	private final String m_name;
	/** Content Type **/
	private final String m_mimeType;
	private static final String DEFAULT_MimeType = MimeType.TYPE_TextPlain;
	/** Data **/
	private final byte[] m_data;

	/**
	 * @return file name
	 */
	@Override
	public String getName()
	{
		return m_name;
	}	// getName

	@Override
	public InputStream getInputStream() throws IOException
	{
		if (m_data == null)
		{
			throw new IOException("no data");
		}

		// a new stream must be returned each time.
		return new ByteArrayInputStream(m_data);
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
		return m_mimeType;
	}
}
