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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;

public class TcpConnectionEndPoint implements ITcpConnectionEndPoint
{
	private static final transient Logger logger = LogManager.getLogger(TcpConnectionEndPoint.class);

	private String hostName;
	private int port;
	private boolean returnLastLine = false;

	/**
	 * Opens a socked, sends the command, reads the response and closes the socked again afterwards.
	 * Note: discards everything besides the last line.
	 */
	@Override
	public String sendCmd(final String cmd)
	{

		try (final Socket clientSocket = new Socket(hostName, port);
				final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				final BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));)
		{
			osw.write(cmd);
			osw.newLine();
			osw.flush();

			String result = null;
			String lastReadLine = in.readLine();
			if (returnLastLine)
			{
				while (lastReadLine != null)
				{
					result = lastReadLine;
					lastReadLine = in.readLine();
					logger.debug("Read line from the socket: {}", lastReadLine);
				}
				logger.debug("Result (last line) as read from the socket: {}", result);
			}
			else
			{
				result = lastReadLine;
				logger.debug("Result (first line) as read from the socket: {}", result);
			}
			return result;
			// return in.readLine();
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
	 * If <code>false</code>, then the endpoint will return the first line coming out of the socket.
	 * If <code>true</code>, if will discard all lines until there is nothing more coming out of the socket, and then return the last line if got.
	 *
	 * @param returnLastLine
	 * @return
	 */
	public TcpConnectionEndPoint setReturnLastLine(final boolean returnLastLine)
	{
		this.returnLastLine = returnLastLine;
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
