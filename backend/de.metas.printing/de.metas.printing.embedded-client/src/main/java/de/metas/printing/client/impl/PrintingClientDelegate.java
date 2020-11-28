package de.metas.printing.client.impl;

import org.slf4j.Logger;

/*
 * #%L
 * de.metas.printing.embedded-client
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


import de.metas.logging.LogManager;
import de.metas.printing.client.Context;
import de.metas.printing.client.IPrintingClientDelegate;
import de.metas.printing.client.PrintingClient;
import de.metas.printing.client.endpoint.LoopbackPrintConnectionEndpoint;

/**
 * Swing Printing client manager.
 *
 * @author tsa
 *
 */
public class PrintingClientDelegate implements IPrintingClientDelegate
{
	private static final transient Logger logger = LogManager.getLogger(PrintingClientDelegate.class);

	/**
	 * The actual printing client. Will be initialized in constructor
	 */
	private PrintingClient client;

	public PrintingClientDelegate()
	{
		client = createPrintingClient();
	}

	private PrintingClient createPrintingClient()
	{
		final Context ctx = Context.getContext();
		ctx.addSource(new EnvContext());
		ctx.addSource(new SysConfigContext());

		// the swing client can always use the loopback endpoint, as  it has direct (DB-)access to the server
		ctx.setProperty(Context.CTX_PrintConnectionEndpoint, LoopbackPrintConnectionEndpoint.class.getName());

		// ctx.setProperty(Context.CTX_UserInterface, new SwingUserInterface(this));
		logger.info("Context: " + ctx);

		//
		// Create printing client, but don't start it by default
		final boolean start = false;
		final PrintingClient client = new PrintingClient(start);
		return client;
	}

	@Override
	public boolean isStarted()
	{
		return client.isStarted();
	}

	@Override
	public void start()
	{
		client.start();
	}

	@Override
	public void stop()
	{
		client.stop();
	}

	@Override
	public void destroy()
	{
		if (client != null)
		{
			client.destroy();
		}
		client = null;
	}

	@Override
	public void restart()
	{
		stop();
		start();
	}

}
