/*
 * #%L
 * de-metas-camel-externalsystems-common
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.common.http;

import lombok.Getter;
import org.apache.camel.RuntimeCamelException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmptyBodyRequestWrapper extends HttpServletRequestWrapper
{
	@Getter
	private final ServletInputStream realStream;

	public EmptyBodyRequestWrapper(final HttpServletRequest request)
	{
		super(request);

		try
		{
			this.realStream = request.getInputStream();
		}
		catch (final IOException exception)
		{
			throw new RuntimeCamelException("Failed to retrieve inputStream! " + exception.getMessage());
		}
	}

	@Override
	public ServletInputStream getInputStream()
	{
		return new ServletInputStream()
		{
			@Override
			public boolean isFinished()
			{
				return true;
			}

			@Override
			public boolean isReady()
			{
				return false;
			}

			@Override
			public void setReadListener(final ReadListener listener)
			{

			}

			/**
			 *	We want to return -1 (end of stream), because the input stream has already been written to a file, see {@link java.io.InputStream#read()}
			 */
			public int read()
			{
				return -1;
			}
		};
	}

	@Override
	public BufferedReader getReader()
	{
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}
}