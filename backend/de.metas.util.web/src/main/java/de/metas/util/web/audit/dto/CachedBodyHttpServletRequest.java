/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.util.web.audit.dto;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper
{
	private byte[] cachedBody;

	public CachedBodyHttpServletRequest(final HttpServletRequest request) throws IOException
	{
		super(request);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (this.cachedBody == null)
		{
			cacheBody();
		}

		return new CachedBodyServletInputStream(this.cachedBody);
	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (cachedBody == null)
		{
			cacheBody();
		}

		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);

		return new BufferedReader(new InputStreamReader(byteArrayInputStream));
	}

	private void cacheBody() throws IOException
	{
		this.cachedBody = StreamUtils.copyToByteArray(super.getInputStream());
	}

	public static class CachedBodyServletInputStream extends ServletInputStream
	{

		private final ByteArrayInputStream cachedBodyInputStream;

		public CachedBodyServletInputStream(final byte[] cachedBody) {
			this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
		}

		@Override
		public int read() throws IOException {
			return cachedBodyInputStream.read();
		}

		@Override
		public boolean isFinished() {
			return cachedBodyInputStream.available() == 0;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(final ReadListener readListener)
		{
			throw new AdempiereException("Not implemented");
		}
	}
}
