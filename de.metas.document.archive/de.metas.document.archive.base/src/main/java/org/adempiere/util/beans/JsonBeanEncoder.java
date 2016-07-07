package org.adempiere.util.beans;

/*
 * #%L
 * de.metas.document.archive.base
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


import java.io.InputStream;

import org.adempiere.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBeanEncoder implements IBeanEnconder
{
	private final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	@Override
	public <T> T decodeString(final String text, final Class<T> clazz)
	{
		try
		{
			return mapper.readValue(text, clazz);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Cannot parse text: " + text, e);
		}
	}

	@Override
	public <T> T decodeStream(final InputStream in, final Class<T> clazz)
	{
		if (in == null)
		{
			throw new IllegalArgumentException("'in' input stream is null");
		}

		String str = null;
		try
		{
			str = StreamUtils.toString(in);
			return mapper.readValue(str, clazz);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Cannot parse input stream for class " + clazz + ": " + str, e);
		}
	}

	@Override
	public <T> T decodeBytes(final byte[] data, final Class<T> clazz)
	{
		try
		{
			return mapper.readValue(data, clazz);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Cannot parse bytes: " + data, e);
		}
	}

	@Override
	public <T> byte[] encode(final T object)
	{
		try
		{
			return mapper.writeValueAsBytes(object);
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getContentType()
	{
		return "application/json";
	}
}
