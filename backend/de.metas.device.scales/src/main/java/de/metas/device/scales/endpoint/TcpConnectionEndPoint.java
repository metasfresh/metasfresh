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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class TcpConnectionEndPoint implements ITcpConnectionEndPoint
{
	private static final Logger logger = LogManager.getLogger(TcpConnectionEndPoint.class);

	/**
	 * Default connect timeout. Industrial scales (Bizerba IT3, etc.) often run minimal TCP stacks
	 * with a single listener slot; if a previous probe (e.g. an nmap SYN scan) wedges that slot,
	 * a plain {@code new Socket(host, port)} can block on the OS default (~21s Windows / ~75s Linux).
	 * 2s is long enough for a LAN scale to accept the connection and short enough to surface
	 * "scale offline" to the operator quickly.
	 */
	private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 2000;

	private String hostName;
	private int port;

	/**
	 * see {@link #setReadTimeoutMillis(int)}.
	 */
	private int readTimeoutMillis = 500;

	private int connectTimeoutMillis = DEFAULT_CONNECT_TIMEOUT_MILLIS;

	/**
	 * Opens a socked, sends the command, reads the response and closes the socked again afterwards.
	 * Note: discards everything besides the last line.
	 */
	@Override
	@Nullable
	public String sendCmd(@NonNull final String cmd)
	{
		try (final Socket clientSocket = openSocket();
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

	/**
	 * Constructs a {@link Socket}, connects with an explicit {@link #connectTimeoutMillis} and enables TCP keep-alive.
	 * If anything fails, the partially-constructed socket is closed before the exception propagates.
	 *
	 * Rationale: see {@link #DEFAULT_CONNECT_TIMEOUT_MILLIS}. TCP keep-alive is enabled so that a half-dead
	 * scale (peer never sends FIN/RST) is eventually surfaced as a read failure on our side instead of
	 * hanging the calling thread.
	 */
	@NonNull
	private Socket openSocket() throws IOException
	{
		final Socket socket = new Socket();
		try
		{
			socket.connect(new InetSocketAddress(hostName, port), connectTimeoutMillis);
			socket.setKeepAlive(true);
			return socket;
		}
		catch (final IOException e)
		{
			try
			{
				socket.close();
			}
			catch (final IOException closeEx)
			{
				e.addSuppressed(closeEx);
			}
			throw e;
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

	/**
	 * TCP connect timeout. See {@link #DEFAULT_CONNECT_TIMEOUT_MILLIS} for rationale.
	 */
	public TcpConnectionEndPoint setConnectTimeoutMillis(final int connectTimeoutMillis)
	{
		this.connectTimeoutMillis = connectTimeoutMillis;
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
