/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * Copyright (C) 2008 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.webui.AdempiereWebUI;
import org.adempiere.webui.desktop.IDesktop;
import org.adempiere.webui.session.ServerContext;
import org.adempiere.webui.session.SessionContextListener;
import org.adempiere.webui.util.ServerPushTemplate;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.event.Events;

/**
 *
 * @author hengsin
 * @author Cristina Ghita, www.arhipac.ro BF [2871741] Error at start
 * @see https://sourceforge.net/tracker/?func=detail&atid=955896&aid=2871741&group_id=176962
 */
public class DashboardRunnable implements Runnable, Serializable
{
	
	private static final long serialVersionUID = 5995227773511788894L;
	
	private Desktop desktop;
	private boolean stop = false;
	private List<DashboardPanel> dashboardPanels;
	private IDesktop appDesktop;
	private Locale locale;

	private static final CLogger logger = CLogger.getCLogger(DashboardRunnable.class);

	private final static String ZK_DASHBOARD_REFRESH_INTERVAL = "ZK_DASHBOARD_REFRESH_INTERVAL";

	/**
	 *
	 * @param desktop zk desktop interface
	 * @param appDesktop adempiere desktop interface
	 */
	public DashboardRunnable(Desktop desktop, IDesktop appDesktop) {
		this.desktop = desktop;
		this.appDesktop = appDesktop;

		dashboardPanels = new ArrayList<DashboardPanel>();
		locale = Locales.getCurrent();
	}

	public DashboardRunnable(DashboardRunnable tmp, Desktop desktop,
			IDesktop appDesktop) {
		this(desktop, appDesktop);
		this.dashboardPanels = tmp.dashboardPanels;
	}

	public void run()
	{
		// default Update every one minutes
		int interval = MSysConfig.getIntValue(ZK_DASHBOARD_REFRESH_INTERVAL, 60000);
		int cumulativeFailure = 0;
		while(!stop) {
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e1) {
				if (stop) break;
			}

			if (desktop.isAlive()) {
				Locales.setThreadLocal(locale);
				try {
					refreshDashboard();
					cumulativeFailure = 0;
				} catch (DesktopUnavailableException de) {
					cumulativeFailure++;
				} catch (Exception e) {
					logger.log(Level.INFO, e.getLocalizedMessage(), (e.getCause() != null ? e.getCause() : e));
					cumulativeFailure++;
				}
				if (cumulativeFailure > 3)
					break;
			} else {
				logger.log(Level.INFO, "Desktop destroy, will kill session.");
				killSession();
				break;
			}
		}
	}

	private void killSession() {
		if (desktop.getSession() != null && desktop.getSession().getNativeSession() != null)
		{
			//differentiate between real destroy and refresh
			try
			{
				Thread.sleep(90000);
			}
			catch (InterruptedException e)
			{
				try
				{
					desktop.getSession().getAttributes().clear();
					desktop.getSession().invalidate();
				}
				catch (Exception e1) {}
				return;
			}

			try
			{
				Object sessionObj = desktop.getSession().getAttribute(AdempiereWebUI.ZK_DESKTOP_SESSION_KEY);
				if (sessionObj != null && sessionObj instanceof Desktop)
				{
					Desktop sessionDesktop = (Desktop) sessionObj;

					//don't destroy session if it have been attached to another desktop ( refresh will do that )
					if (sessionDesktop == desktop)
					{
						desktop.getSession().getAttributes().clear();
						desktop.getSession().invalidate();
					}
				}
				else
				{
					desktop.getSession().getAttributes().clear();
					desktop.getSession().invalidate();
				}
			}
			catch (Exception e1) {}
		}
	}
	
	/**
	 * Refresh dashboard content
	 */
	public void refreshDashboard()
	{
		final ServerPushTemplate template = new ServerPushTemplate(desktop);
		
		// set thread local context if not in event thread
		Properties ctx = null;
		boolean isEventThread = Events.inEventListener();
		if (!isEventThread)
		{
			ctx = (Properties)template.getDesktop().getSession().getAttribute(SessionContextListener.SESSION_CTX);
			if (ctx == null)
			{
				return;
			}
		}

		try
		{
			if (!isEventThread)
            {
				ServerContext.setCurrentInstance(ctx);
            }
			
	    	for(int i = 0; i < dashboardPanels.size(); i++)
	    	{
	    		dashboardPanels.get(i).refresh(template);
	    	}
	    	
	    	appDesktop.onServerPush(template);
		}
		finally
		{
			if (!isEventThread)
			{
				ServerContext.dispose();
			}
		}
	}

	public void stop() {
		stop = true;
	}

	/**
	 * Add DashboardPanel to the auto refresh list
	 * @param dashboardPanel
	 */
	public void add(DashboardPanel dashboardPanel) {
		dashboardPanels.add(dashboardPanel);
	}

// metas: begin
	/**
	 * Build and return the thread name for this DashboardRunnable.
	 * The thread name contains logged in user id and session id which is very useful for debugging and audit.
	 */
	public String getThreadName()
	{
		if (threadName == null)
		{
			int userId = Env.getAD_User_ID(Env.getCtx());
			int sessionId = Env.getContextAsInt(Env.getCtx(), Env.CTXNAME_AD_Session_ID);
			threadName = "UpdateInfo_U"+userId+"_S"+sessionId;
		}
		return threadName;
	}
	private String threadName = null;
// metas: end
}
