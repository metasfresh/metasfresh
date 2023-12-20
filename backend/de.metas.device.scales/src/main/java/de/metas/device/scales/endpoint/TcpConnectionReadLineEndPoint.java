/*
 * #%L
 * de.metas.device.scales
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

package de.metas.device.scales.endpoint;

import de.metas.device.scales.impl.ICmd;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

/**
 * Like its superclass, but
 * assumes that each response from the external device will be terminated with a line-terminator like {@code \r}, {@code \r\n} or similar.
 * Allows to be configured to use either use the first or last of those lines.
 * Required for some scales that first send not-so-interesting stuff before getting to the point.
 */
public class TcpConnectionReadLineEndPoint extends TcpConnectionEndPoint
{

	private final static transient Logger logger = LogManager.getLogger(TcpConnectionReadLineEndPoint.class);

	/**
	 * see {@link #setReturnLastLine(boolean)}.
	 */
	private boolean returnLastLine = false;
	
	/**
	 * If <code>false</code>, then the endpoint will return the first line coming out of the socket.
	 * If <code>true</code>, if will discard all lines until there is nothing more coming out of the socket, and then return the last line if got.
	 */
	public TcpConnectionEndPoint setReturnLastLine(final boolean returnLastLine)
	{
		this.returnLastLine = returnLastLine;
		return this;
	}

	 @Override
	String readSocketResponse(@NonNull final InputStream in) throws IOException
	{
		return readLineSocketResponse(in);
	}
	
	@Nullable
	private String readLineSocketResponse(@NonNull final InputStream in) throws IOException
	{
		try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, ICmd.DEFAULT_CMD_CHARSET)))
		{
			String result = null;
			String lastReadLine = bufferedReader.readLine();
			if (returnLastLine)
			{
				while (lastReadLine != null)
				{
					result = lastReadLine;
					lastReadLine = readLineWithTimeout(bufferedReader);
				}
				logger.debug("Result (last line) as read from the socket: {}", result);
			}
			else
			{
				result = lastReadLine;
				logger.debug("Result (first line) as read from the socket: {}", result);
			}
			return result;
		}
	}

	@Nullable
	private String readLineWithTimeout(@NonNull final BufferedReader in) throws IOException
	{
		try
		{
			final String lastReadLine = in.readLine();
			logger.debug("Read line from the socket: {}", lastReadLine);
			return lastReadLine;
		}
		catch (final SocketTimeoutException e)
		{
			logger.debug("Socket timeout; return null; exception-message={}", e.getMessage());
			return null;
		}
	}
}
