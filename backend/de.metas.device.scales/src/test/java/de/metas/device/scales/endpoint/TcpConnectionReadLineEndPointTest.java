package de.metas.device.scales.endpoint;

import de.metas.device.scales.impl.ICmd;
import de.metas.device.scales.impl.sics.ISiscCmd;
import de.metas.device.scales.impl.sics.SicsWeighCmdS;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/*
 * #%L
 * de.metas.device.scales
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

/** Deactivating it because it's too unstable when run ion jenkins; TODO rewrite or incorporate into e2e. */
public class TcpConnectionReadLineEndPointTest
{
	private static volatile int weight = 100;
	private volatile boolean exitServerSocketThread = false;

	private static TcpConnectionReadLineEndPoint tcpConnectionEndPoint;

	private static List<String> serverSocketReceived = new ArrayList<>();

	private static final int readTimeoutMillis = 100;

	private static Thread serverSocketThread;

	@BeforeClass
	public static void setupEP()
	{
		tcpConnectionEndPoint = new TcpConnectionReadLineEndPoint();
		tcpConnectionEndPoint.setHost("localhost");
		tcpConnectionEndPoint.setReturnLastLine(true);

		tcpConnectionEndPoint.setReadTimeoutMillis(readTimeoutMillis);
	}

	/**
	 * Create a server socket to emulate the scale.
	 * It picks a port each time it is run, in order to prevent issued with ports that are already in use.
	 */
	@Before
	public void setUpServer() throws InterruptedException
	{
		exitServerSocketThread = false;
		serverSocketReceived.clear();

		serverSocketThread = new Thread()
		{
			@Override
			public void run()
			{
				try (final ServerSocket myServer = new ServerSocket(0))
				{
					final int port = myServer.getLocalPort();
					System.out.println("TcpConnectionEndPointTest" + ": server socked listening on port " + port);

					tcpConnectionEndPoint.setPort(port);

					// now we can notify the actual junit thread
					synchronized (tcpConnectionEndPoint)
					{
						tcpConnectionEndPoint.notify();
					}

					while (!exitServerSocketThread)
					{
						try (final Socket myServerSocket = myServer.accept();
								final InputStream in = myServerSocket.getInputStream();
								final OutputStream out = myServerSocket.getOutputStream();)
						{
							final byte[] bytes = new byte[10]; // the index shall be big enough to get everything in one read.

							final int read = in.read(bytes);
							if (read > 0)
							{
								final String string = new String(bytes, 0, read, ICmd.DEFAULT_CMD_CHARSET);
								System.out.println("TcpConnectionEndPointTest" + ": server-socked-thread received: '" + string + "'; weight=" + weight);
								serverSocketReceived.add(string);

								// returning CRLF, thx http://stackoverflow.com/questions/13821578/crlf-into-java-string#13821601
								// first sending a wrong result. the client EP is supposed to only take the last line.
								final String wrongServerReturnString = MockedEndpoint.createWeightString(new BigDecimal(weight - 10)) + ISiscCmd.SICS_CMD_TERMINATOR;
								out.write(wrongServerReturnString.getBytes(ICmd.DEFAULT_CMD_CHARSET));
								System.out.println("TcpConnectionEndPointTest" + ": server-socked-thread replied with wrongServerReturnString=" + wrongServerReturnString);

								final String serverReturnString = MockedEndpoint.createWeightString(new BigDecimal(weight)) + ISiscCmd.SICS_CMD_TERMINATOR;
								out.write(serverReturnString.getBytes(ICmd.DEFAULT_CMD_CHARSET));
								System.out.println("TcpConnectionEndPointTest" + ": server-socked-thread replied with serverReturnString=" + serverReturnString);

								// before sending out the 3rd message, which is once again wrong, we wait longer than the endpoint's timeout.
								// therefore we expect this message to be ignored
								Thread.sleep(readTimeoutMillis + 50);
								final String anotherWrongServerReturnString = MockedEndpoint.createWeightString(new BigDecimal(weight + 10)) + ISiscCmd.SICS_CMD_TERMINATOR;
								out.write(anotherWrongServerReturnString.getBytes(ICmd.DEFAULT_CMD_CHARSET));
								System.out.println("TcpConnectionEndPointTest" + ": server-socked-thread replied with anotherWrongServerReturnString=" + anotherWrongServerReturnString);
							}
						}
						System.out.println("TcpConnectionEndPointTest" + ": server-socked-thread sees exitServerSocketThread=" + exitServerSocketThread);
					}
				}
				catch (final IOException | InterruptedException e)
				{
					e.printStackTrace();
					fail("Caught " + e);
				}
			}
		};
		serverSocketThread.start();

		// this "junit" thread needs to wait until 'serverSocketThread' found a port and set it to 'tcpConnectionEndPoint'
		// thx to http://stackoverflow.com/questions/7126550/java-wait-and-notify-illegalmonitorstateexception
		synchronized (tcpConnectionEndPoint)
		{
			tcpConnectionEndPoint.wait(60 * 1000); // wait one minute max, in order not to hang the whole build process.
		}
	}

	@Test
	@Ignore
	public void test1()
	{
		weight = 300;
		final String cmd = SicsWeighCmdS.getInstance().getCmd();

		final String result = tcpConnectionEndPoint.sendCmd(cmd);
		final String expectedResult = MockedEndpoint.createWeightString(new BigDecimal(weight));

		assertThat(result, is(expectedResult));
		assertThat(serverSocketReceived.size(), is(1));
		assertThat(serverSocketReceived.get(0), is(cmd));
	}

	@Test
	@Ignore
	public void test2()
	{
		weight = 200;
		final String cmd = SicsWeighCmdS.getInstance().getCmd();

		String result = tcpConnectionEndPoint.sendCmd(cmd);
		String expectedResult = MockedEndpoint.createWeightString(new BigDecimal(weight));
		assertThat(result, is(expectedResult));
		assertThat(serverSocketReceived.size(), is(1));
		assertThat(serverSocketReceived.get(0), is(cmd));

		weight = 220;
		result = tcpConnectionEndPoint.sendCmd(cmd);
		expectedResult = MockedEndpoint.createWeightString(new BigDecimal(weight));
		assertThat(result, is(expectedResult));
		assertThat(serverSocketReceived.size(), is(2));
		assertThat(serverSocketReceived.get(1), is(cmd));
	}

	/**
	 * Makes sure that the server socked thread has finished.
	 */
	@After
	public void tearDown() throws InterruptedException
	{
		exitServerSocketThread = true;

		System.out.println("TcpConnectionEndPointTest" + ": tearDown method sets exitServerSocketThread=true for serverSocketThread to stop");

		assertThat(serverSocketThread, notNullValue());

		serverSocketThread.join(3000); // waiting for just three seconds, we don't want the whole build to stall

		// it's weird..the jenkins console output seems to imply that the following assertThat is reached after 300ms, and not after 3000.
		// ..let's just wait another 3secs
		Thread.sleep(3000);
		assertThat("TcpConnectionEndPointTest" + ": serverSocketThread did not stop within 3 seconds; serverSocketThread=" + serverSocketThread.toString(), serverSocketThread.isAlive(), is(false));
	}
}
