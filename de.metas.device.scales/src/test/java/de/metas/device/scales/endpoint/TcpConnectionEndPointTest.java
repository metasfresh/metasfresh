package de.metas.device.scales.endpoint;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

public class TcpConnectionEndPointTest
{
	private static volatile int weight = 100;
	boolean exitServerSocketThread = false;

	private static TcpConnectionEndPoint tcpConnectionEndPoint;

	@BeforeClass
	public static void setupEP()
	{
		tcpConnectionEndPoint = new TcpConnectionEndPoint();
		tcpConnectionEndPoint.setHost("localhost");
		tcpConnectionEndPoint.setReturnLastLine(true);
	}

	/**
	 * Create a server socket to emulate the scale.
	 * It picks a port each time it is run, in order to prevent issued with ports that are already in use.
	 *
	 * @throws InterruptedException
	 */
	@Before
	public void setUpServer() throws InterruptedException
	{
		exitServerSocketThread = false;

		final Thread serverSocketThread = new Thread()
		{
			@Override
			public void run()
			{
				try (ServerSocket myServer = new ServerSocket(0))
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
								final DataInputStream dis = new DataInputStream(myServerSocket.getInputStream());
								final DataOutputStream dos = new DataOutputStream(myServerSocket.getOutputStream());)
						{
							final byte[] bytes = new byte[10];

							final int read = dis.read(bytes);
							if (read > 0)
							{
								final String string = new String(bytes);
								System.out.println("TcpConnectionEndPointTest" + ": server socked received: '" + string + "'; weight=" + weight);

								// returning CRLF, thx http://stackoverflow.com/questions/13821578/crlf-into-java-string#13821601
								// first sending a wrong result. the client EP is supposed to only take the last line.
								final String wrongServerReturnString = MockedEndpoint.createWeightString(new BigDecimal(weight - 10)) + "\r\n";
								dos.writeBytes(wrongServerReturnString);
								System.out.println("TcpConnectionEndPointTest" + ": server socked replied with wrongServerReturnString=" + wrongServerReturnString);

								final String serverReturnString = MockedEndpoint.createWeightString(new BigDecimal(weight)) + "\r\n";
								dos.writeBytes(serverReturnString);
								System.out.println("TcpConnectionEndPointTest" + ": server socked replied with serverReturnString=" + serverReturnString);

								dos.flush();
							}
						}
					}
				}
				catch (final IOException e)
				{
					e.printStackTrace();
					fail("Caught " + e);
				}
			}
		};
		serverSocketThread.start();

		// this "junit" thread needs to wait until 'serverSocketThread' found a port and set it to 'tcpConnectionEndPoint'
		// htx to http://stackoverflow.com/questions/7126550/java-wait-and-notify-illegalmonitorstateexception
		synchronized (tcpConnectionEndPoint)
		{
			tcpConnectionEndPoint.wait(60 * 1000); // wait one minute max, in order not to hang the whole build process.
		}
	}

	@Test
	public void test1()
	{
		weight = 300;

		final String result = tcpConnectionEndPoint.sendCmd("S");
		final String expectedResult = MockedEndpoint.createWeightString(new BigDecimal(weight));

		assertThat(result, is(expectedResult));
	}

	@Test
	public void test2()
	{
		weight = 200;

		String result = tcpConnectionEndPoint.sendCmd("S");
		String expectedResult = MockedEndpoint.createWeightString(new BigDecimal(weight));
		assertThat(result, is(expectedResult));

		weight = 220;
		result = tcpConnectionEndPoint.sendCmd("S");
		expectedResult = MockedEndpoint.createWeightString(new BigDecimal(weight));
		assertThat(result, is(expectedResult));
	}

	@After
	public void tearDown()
	{
		exitServerSocketThread = true;
	}
}
