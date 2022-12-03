package de.metas.device.scales.endpoint;

/*
 * #%L
 * de.metas.device.scales
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

import com.google.common.base.MoreObjects;
import de.metas.device.scales.impl.ICmd;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class TcpConnectionEndPoint implements ITcpConnectionEndPoint
{
	private static final Logger logger = LogManager.getLogger(TcpConnectionEndPoint.class);

	private String hostName;
	private int port;

	/**
	 * see {@link #setReadTimeoutMillis(int)}.
	 */
	private int readTimeoutMillis = 500;

	/**
	 * Opens a socked, sends the command, reads the response and closes the socked again afterwards.
	 * Note: discards everything besides the last line.
	 */
	@Override
	@Nullable
	public String sendCmd(@NonNull final String cmd)
	{
		try (final Socket clientSocket = new Socket(hostName, port);
				final OutputStream out = clientSocket.getOutputStream();)
		{
			clientSocket.setSoTimeout(readTimeoutMillis);

			logger.debug("Writing cmd to the socket: {}", cmd);
			out.write(cmd.getBytes(ICmd.DEFAULT_CMD_CHARSET));
			out.flush();

			return readSocketResponse(clientSocket.getInputStream());
		}
		catch (final UnknownHostException e)
		{
			throw new EndPointException("Caught UnknownHostException: " + e.getLocalizedMessage(), e);
		}
		catch (final IOException e)
		{
			throw new EndPointException("Caught IOException: " + e.getLocalizedMessage(), e);
		}
	}

	@Nullable
	String readSocketResponse(@NonNull final InputStream in) throws IOException
	{
		final StringBuilder sb = new StringBuilder();
		int i;
		try
		{
			while ((i = in.read()) != -1)
			{
				sb.append((char)i);
			}
		}
		catch (final SocketTimeoutException e)
		{
			// if the device doesn't send "EOF", then there is nothing we can do here
			// ..because at this place here we don't know how the response is terminated.
			// so we just wait for the respective timeout
		}
		return sb.toString();
	}

	public TcpConnectionEndPoint setHost(final String hostName)
	{
		this.hostName = hostName;
		return this;
	}

	public TcpConnectionEndPoint setPort(final int port)
	{
		this.port = port;
		return this;
	}

	/**
	 * Timeout for this endpoint for each read, before considering the result to be <code>null</code>. The default is 500ms.
	 */
	public TcpConnectionEndPoint setReadTimeoutMillis(final int readTimeoutMillis)
	{
		this.readTimeoutMillis = readTimeoutMillis;
		return this;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("hostName", hostName)
				.add("port", port)
				.toString();
	}
}
