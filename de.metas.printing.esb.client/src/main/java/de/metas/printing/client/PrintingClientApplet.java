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

import java.util.logging.Logger;

import javax.swing.JApplet;

import de.metas.printing.client.ui.SwingUserInterface;

public class PrintingClientApplet extends JApplet
{
	private final transient Logger log = Logger.getLogger(getClass().getName());

	/**
	 *
	 */
	private static final long serialVersionUID = -2736983326392018988L;

	private final IContext appletContextAdapter = new IContext()
	{

		@Override
		public String getProperty(final String name)
		{
			return getParameter(name);
		}
	};

	/**
	 * The actual printing client. Will be initialized on {@link #init()}
	 */
	private PrintingClient client;

	@Override
	public void init()
	{
		final Context ctx = Context.getContext();
		ctx.addSource(appletContextAdapter);
		ctx.setProperty(Context.CTX_UserInterface, new SwingUserInterface(this));
		log.config("Context: " + ctx);

		client = new PrintingClient();
	}

	@Override
	public void start()
	{
		// nothing
		// we are not starting/stopping the daemon when the browser applet says it (e.g. browser decides to stop the applet when is not the current active Tab)
		// client.start();
	}

	@Override
	public void stop()
	{
		// nothing
		// we are not starting/stopping the daemon when the browser applet says it (e.g. browser decides to stop the applet when is not the current active Tab)
		// client.stop();
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

}
