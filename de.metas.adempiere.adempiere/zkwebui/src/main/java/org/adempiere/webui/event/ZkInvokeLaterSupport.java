package org.adempiere.webui.event;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.adempiere.webui.desktop.IDesktop;
import org.adempiere.webui.session.SessionManager;
import org.compiere.util.CLogger;
import org.zkoss.zk.au.out.AuEcho;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;

/**
 * Alternative for SwingUtils.invokeLater helper method
 * @author tsa
 *
 */
public class ZkInvokeLaterSupport
{
	private static final CLogger logger = CLogger.getCLogger(ZkInvokeLaterSupport.class);

	private final ZkInvokeLaterComponent component;

	/**
	 * Map RunnableID -> Runnable. We need this on ZK3 because method
	 * {@link Events#echoEvent(String, Component, String)} accepts only string as data parameter. When we port on ZK5 we
	 * can drop this.
	 */
	private Map<String, Runnable> runnables = new HashMap<String, Runnable>();

	/**
	 * Helper method to trigger an invoke later on a particular window
	 * 
	 * @param windowNo
	 * @param runnable
	 */
	public static void invokeLater(int windowNo, Runnable runnable)
	{
		IDesktop desktop = SessionManager.getAppDesktop();
		if (desktop == null)
		{
			return;
		}

		Object win = desktop.findWindow(windowNo);
		if (win instanceof ZkInvokeLaterComponent)
		{
			((ZkInvokeLaterComponent)win).getInvokeLaterSupport().invokeLater(runnable);
		}
		else
		{
			if (logger.isLoggable(Level.FINE))
				logger.fine("No invokeLater support for "+win+". Invoking it now");
			runnable.run();
		}
	}

	public ZkInvokeLaterSupport(ZkInvokeLaterComponent component)
	{
		this.component = component;
	}

	/**
	 * Schedule a later invocation
	 * @param runnable
	 */
	public void invokeLater(Runnable runnable)
	{
		if (runnable == null)
			return;

		String runnableId = "invokeLater_" + UUID.randomUUID();
		runnables.put(runnableId, runnable);
		if (logger.isLoggable(Level.FINE))
		{
			logger.fine("Enqueued " + runnableId + " for " + runnable);
		}
		
		// We need to use an unique ID for each AuResponse that we send, because else the ZK will get only the last event
		//Events.echoEvent("onInvokeLater", component, runnableId);
		Clients.response(runnableId, new AuEcho(component, "onInvokeLater", runnableId));
	}

	public Component getComponent()
	{
		return this.component;
	}

	/**
	 * InvokeLater event handler.
	 * @param event
	 */
	public void onInvokeLater(Event event)
	{
		final Object data = event.getData();
		if (data instanceof String)
		{
			String runnableId = (String)data;
			Runnable runnable = runnables.remove(runnableId);
			if (runnable == null)
			{
				logger.warning("No runnable found for " + runnableId);
				return;
			}
			executeRunnable(runnable);
		}
		else
		{
			logger.warning("Not supported data: " + data);
		}
	}

	private void executeRunnable(Runnable runnable)
	{
		runnable.run();
	}
}
