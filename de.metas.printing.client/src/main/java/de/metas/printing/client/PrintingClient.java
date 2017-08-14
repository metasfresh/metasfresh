package de.metas.printing.client;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import de.metas.printing.client.engine.PrintingClientDaemon;

public class PrintingClient
{
	private final transient Logger log = Logger.getLogger(getClass().getName());

	private Thread clientDaemonThread;
	private PrintingClientDaemon clientDaemon;

	private final ReentrantLock threadCtrlLock = new ReentrantLock();

	/**
	 * NOTE: automatically start client thread.
	 */
	public PrintingClient()
	{
		this(true);
	}

	/**
	 *
	 * @param start start client thread
	 */
	public PrintingClient(final boolean start)
	{
		if (start)
		{
			start();
		}
	}

	public void start()
	{
		threadCtrlLock.lock();
		try
		{
			if (isStarted())
			{
				// already started, nothing to do
				return;
			}

			clientDaemon = new PrintingClientDaemon();
			final Thread thread = getCreateDaemonThread(clientDaemon);
			thread.start();
			log.config("Printing client daemon started: " + thread.getName());
		}
		finally
		{
			threadCtrlLock.unlock();
		}
	}

	public void stop()
	{
		threadCtrlLock.lock();
		try
		{
			if (clientDaemon != null)
			{
				clientDaemon.stop(); // make sure the demon will leave its endless while loop the next time it passes by
			}

			if (clientDaemonThread != null && clientDaemonThread.isAlive())
			{
				clientDaemonThread.interrupt(); // interrupt the thread in case it is sleeping or waiting
			}

			if (clientDaemonThread != null)
			{
				log.config("Printing client daemon '" + clientDaemonThread.getName() + "' already stopped: " + clientDaemonThread.isAlive());
			}

			clientDaemon = null;
			clientDaemonThread = null;
		}
		finally
		{
			threadCtrlLock.unlock();
		}
	}

	public void destroy()
	{
		stop();
	}

	public boolean isStarted()
	{
		return clientDaemonThread != null && clientDaemonThread.isAlive();
	}

	private Thread getCreateDaemonThread(final PrintingClientDaemon clientDaemon)
	{
		if (clientDaemonThread != null)
		{
			return clientDaemonThread;
		}

		clientDaemonThread = new Thread(clientDaemon, clientDaemon.getName());
		clientDaemonThread.setDaemon(true);
		return clientDaemonThread;
	}

	public Thread getDaemonThread()
	{
		return clientDaemonThread;
	}

}
