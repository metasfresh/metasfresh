/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.adempiere.server.rpl.interfaces.I_IMP_Processor;
import org.adempiere.util.Services;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.MAcctProcessor;
import org.compiere.model.MAlertProcessor;
import org.compiere.model.MRequestProcessor;
import org.compiere.model.MScheduler;
import org.compiere.model.X_AD_Scheduler;
import org.compiere.util.Env;
import org.compiere.wf.MWorkflowProcessor;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Adempiere Server Manager
 *
 * @author Jorg Janke
 * @version $Id: AdempiereServerMgr.java,v 1.4 2006/10/09 00:23:26 jjanke Exp $
 */
public final class AdempiereServerMgr
{
	/**
	 * Get Adempiere Server Manager
	 * 
	 * @param adempiereAlreadyStarted if <code>false</code>, and the servermanager has not yet been initialized, the it will be initialized while also calling <code>Adempiere.startup()</code>
	 * @return mgr
	 */
	public static AdempiereServerMgr get()
	{
		if (m_serverMgr == null)
		{
			synchronized (AdempiereProcessor.class)
			{
				if (m_serverMgr == null)
				{
					m_serverMgr = new AdempiereServerMgr();
					m_serverMgr.startServers();
					m_serverMgr.log.info("Created and started: {}", m_serverMgr);
				}
			}
		}
		return m_serverMgr;
	}	// get

	/** Singleton */
	private static volatile AdempiereServerMgr m_serverMgr = null;
	/** Logger */
	private final Logger log = LogManager.getLogger(getClass());

	/**************************************************************************
	 * Adempiere Server Manager
	 */
	private AdempiereServerMgr()
	{
		super();
		startEnvironment();
		// m_serverMgr.startServers();
	}	// AdempiereServerMgr

	/** The Servers */
	private final List<AdempiereServer> m_servers = new ArrayList<>();
	/** Context */
	private final Properties _ctx = Env.getCtx();
	/** Start */
	private final Timestamp m_start = new Timestamp(System.currentTimeMillis());

	/**
	 * Start Environment
	 *
	 * @return true if started
	 */
	private boolean startEnvironment()
	{
		log.info("Starting environment");

		// Set Session
		final MFSession session = Services.get(ISessionBL.class).getCurrentOrCreateNewSession(getCtx());
		session.setWebSessionId("Server");
		
		return true;
	}	// startEnvironment

	/**
	 * Start Environment
	 *
	 * @return true if started
	 */
	private boolean startServers()
	{
		log.info("Starting servers");

		final Properties ctx = getCtx();

		int noServers = 0;
		// Accounting
		MAcctProcessor[] acctModels = MAcctProcessor.getActive(ctx);
		for (int i = 0; i < acctModels.length; i++)
		{
			MAcctProcessor pModel = acctModels[i];
			AdempiereServer server = AdempiereServer.create(pModel);
			server.start();
			server.setPriority(Thread.NORM_PRIORITY - 2);
			m_servers.add(server);
		}
		// Request
		MRequestProcessor[] requestModels = MRequestProcessor.getActive(ctx);
		for (int i = 0; i < requestModels.length; i++)
		{
			MRequestProcessor pModel = requestModels[i];
			AdempiereServer server = AdempiereServer.create(pModel);
			server.start();
			server.setPriority(Thread.NORM_PRIORITY - 2);
			m_servers.add(server);
		}
		// Workflow
		MWorkflowProcessor[] workflowModels = MWorkflowProcessor.getActive(ctx);
		for (int i = 0; i < workflowModels.length; i++)
		{
			MWorkflowProcessor pModel = workflowModels[i];
			AdempiereServer server = AdempiereServer.create(pModel);
			server.start();
			server.setPriority(Thread.NORM_PRIORITY - 2);
			m_servers.add(server);
		}
		// Alert
		MAlertProcessor[] alertModels = MAlertProcessor.getActive(ctx);
		for (int i = 0; i < alertModels.length; i++)
		{
			MAlertProcessor pModel = alertModels[i];
			AdempiereServer server = AdempiereServer.create(pModel);
			server.start();
			server.setPriority(Thread.NORM_PRIORITY - 2);
			m_servers.add(server);
		}
		// Scheduler
		MScheduler[] schedulerModels = MScheduler.getActive(ctx);
		for (int i = 0; i < schedulerModels.length; i++)
		{
			MScheduler pModel = schedulerModels[i];
			AdempiereServer server = AdempiereServer.create(pModel);
			server.start();
			server.setPriority(Thread.NORM_PRIORITY - 2);
			m_servers.add(server);
		}
		
		// ImportProcessor - @Trifon
		final List<I_IMP_Processor> importModels = Services.get(IIMPProcessorDAO.class).retrieveAllActive(ctx);
		for (final I_IMP_Processor importModel : importModels)
		{
			final AdempiereProcessor adempiereProcessor = Services.get(IIMPProcessorBL.class).asAdempiereProcessor(importModel);
			AdempiereServer server = AdempiereServer.create(adempiereProcessor);
			server.start();
			server.setPriority(Thread.NORM_PRIORITY - 1);
			m_servers.add(server);
		}

		log.debug("#{} servers started.", noServers);
		return startAll();
	}	// startEnvironment

	/**
	 * Get Server Context
	 *
	 * @return ctx
	 */
	private final Properties getCtx()
	{
		return _ctx;
	}	// getCtx

	// metas us1030 adding processor after server startup
	public void add(final AdempiereProcessor pModel)
	{
		if (getServer(pModel.getServerID()) != null)
		{
			// nothing to do
			return;
		}
		final AdempiereServer server = AdempiereServer.create(pModel);
		server.start();
		server.setPriority(Thread.NORM_PRIORITY - 2);
		m_servers.add(server);
	}

	// metas end

	/**
	 * Start all servers
	 *
	 * @return true if started
	 */
	public boolean startAll()
	{
		log.info("Starting all servers");
		AdempiereServer[] servers = getInActive();
		for (int i = 0; i < servers.length; i++)
		{
			AdempiereServer server = servers[i];
			try
			{
				if (server.isAlive())
				{
					continue;
				}
				
				// Wait until dead
				if (server.isInterrupted())
				{
					int maxWait = 10;	// 10 iterations = 1 sec
					while (server.isAlive())
					{
						if (maxWait-- == 0)
						{
							log.error("Wait timeout for interrupted {}", server);
							break;
						}
						try
						{
							Thread.sleep(100);		// 1/10 sec
						}
						catch (InterruptedException e)
						{
							log.error("Error while sleeping", e);
						}
					}
				}
				// Do start
				if (!server.isAlive())
				{
					// replace
					server = AdempiereServer.create(server.getModel());
					if (server == null)
						m_servers.remove(i);
					else
						m_servers.set(i, server);
					server.start();
					server.setPriority(Thread.NORM_PRIORITY - 2);
				}
			}
			catch (Exception e)
			{
				log.error("Error while starting server: {}", server, e);
			}
		}	// for all servers

		// Final Check
		int noRunning = 0;
		int noStopped = 0;
		for (int i = 0; i < servers.length; i++)
		{
			AdempiereServer server = servers[i];
			try
			{
				if (server.isAlive())
				{
					log.info("Alive: {}", server);
					noRunning++;
				}
				else
				{
					log.warn("Dead: {}", server);
					noStopped++;
				}
			}
			catch (Exception e)
			{
				log.error("Error while checking server status: {}", server, e);
				noStopped++;
			}
		}
		log.debug("All servers started: Running={}, Stopped={}", noRunning, noStopped);
		AdempiereServerGroup.get().dump();
		return noStopped == 0;
	}	// startAll

	/**
	 * Start Server if not started yet
	 * 
	 * @param serverID server ID
	 * @return true if started
	 */
	public boolean start(final String serverID)
	{
		AdempiereServer server = getServer(serverID);
		if (server == null)
			return false;
		if (server.isAlive())
			return true;

		try
		{
			// replace
			int index = m_servers.indexOf(server);
			server = AdempiereServer.create(server.getModel());
			if (server == null)
				m_servers.remove(index);
			else
				m_servers.set(index, server);
			server.start();
			server.setPriority(Thread.NORM_PRIORITY - 2);
			Thread.yield();
		}
		catch (Exception e)
		{
			log.error("Server=" + serverID, e);
			return false;
		}
		log.info(server.toString());
		AdempiereServerGroup.get().dump();
		return server.isAlive();
	}	// startIt

	/**
	 * Stop all Servers
	 *
	 * @return true if stopped
	 */
	public boolean stopAll()
	{
		log.info("");
		AdempiereServer[] servers = getActive();
		// Interrupt
		for (int i = 0; i < servers.length; i++)
		{
			AdempiereServer server = servers[i];
			try
			{
				if (server.isAlive() && !server.isInterrupted())
				{
					server.setPriority(Thread.MAX_PRIORITY - 1);
					server.interrupt();
				}
			}
			catch (Exception e)
			{
				log.error("(interrupting) - " + server, e);
			}
		}	// for all servers
		Thread.yield();

		// Wait for death
		for (int i = 0; i < servers.length; i++)
		{
			AdempiereServer server = servers[i];
			try
			{
				int maxWait = 10;	// 10 iterations = 1 sec
				while (server.isAlive())
				{
					if (maxWait-- == 0)
					{
						log.error("Wait timeout for interruped " + server);
						break;
					}
					Thread.sleep(100);		// 1/10
				}
			}
			catch (Exception e)
			{
				log.error("(waiting) - " + server, e);
			}
		}	// for all servers

		// Final Check
		int noRunning = 0;
		int noStopped = 0;
		for (int i = 0; i < servers.length; i++)
		{
			AdempiereServer server = servers[i];
			try
			{
				if (server.isAlive())
				{
					log.warn("Alive: " + server);
					noRunning++;
				}
				else
				{
					log.info("Stopped: " + server);
					noStopped++;
				}
			}
			catch (Exception e)
			{
				log.error("(checking) - " + server, e);
				noRunning++;
			}
		}
		log.debug("Running=" + noRunning + ", Stopped=" + noStopped);
		AdempiereServerGroup.get().dump();
		return noRunning == 0;
	}	// stopAll

	/**
	 * Stop Server if not stopped
	 * 
	 * @param serverID server ID
	 * @return true if interrupted
	 */
	public boolean stop(String serverID)
	{
		AdempiereServer server = getServer(serverID);
		if (server == null)
			return false;
		if (!server.isAlive())
			return true;

		try
		{
			server.interrupt();
			Thread.sleep(10);	// 1/100 sec
		}
		catch (Exception e)
		{
			log.error("stop", e);
			return false;
		}
		log.info(server.toString());
		AdempiereServerGroup.get().dump();

		// metas us1030 updating status
		// TODO declare getStatus() and setStatus() AdempiereProcessor
		final boolean stopSuccess = !server.isAlive();
		if (stopSuccess && server.getModel() instanceof MScheduler)
		{
			final MScheduler scheduler = (MScheduler)server.getModel();
			scheduler.setStatus(X_AD_Scheduler.STATUS_Stopped);
			scheduler.saveEx();
		}
		// metas end

		return !server.isAlive();
	}	// stop

	/**
	 * Get Active Servers
	 *
	 * @return array of active servers
	 */
	private AdempiereServer[] getActive()
	{
		final List<AdempiereServer> list = new ArrayList<>();
		for (int i = 0; i < m_servers.size(); i++)
		{
			final AdempiereServer server = m_servers.get(i);
			if (server != null && server.isAlive() && !server.isInterrupted())
			{
				list.add(server);
			}
		}
		AdempiereServer[] retValue = new AdempiereServer[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getActive

	/**
	 * Get InActive Servers
	 *
	 * @return array of inactive servers
	 */
	private AdempiereServer[] getInActive()
	{
		final List<AdempiereServer> list = new ArrayList<>();
		for (int i = 0; i < m_servers.size(); i++)
		{
			final AdempiereServer server = m_servers.get(i);
			if (server != null && (!server.isAlive() || !server.isInterrupted()))
			{
				list.add(server);
			}
		}
		AdempiereServer[] retValue = new AdempiereServer[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getInActive

	/**
	 * Get all Servers
	 *
	 * @return array of servers
	 */
	public AdempiereServer[] getAll()
	{
		AdempiereServer[] retValue = new AdempiereServer[m_servers.size()];
		m_servers.toArray(retValue);
		return retValue;
	}	// getAll

	/**
	 * Get Server with ID
	 *
	 * @param serverID server id
	 * @return server or null
	 */
	public AdempiereServer getServer(String serverID)
	{
		if (serverID == null)
			return null;
		for (int i = 0; i < m_servers.size(); i++)
		{
			AdempiereServer server = m_servers.get(i);
			if (serverID.equals(server.getServerID()))
				return server;
		}
		return null;
	}	// getServer

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("AdempiereServerMgr[");
		sb.append("Servers=").append(m_servers.size())
				.append(",ContextSize=").append(_ctx.size())
				.append(",Started=").append(m_start)
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Get Description
	 *
	 * @return description
	 */
	public String getDescription()
	{
		return "1.4";
	}	// getDescription

	/**
	 * Get Number Servers
	 *
	 * @return no of servers
	 */
	public String getServerCount()
	{
		int noRunning = 0;
		int noStopped = 0;
		for (int i = 0; i < m_servers.size(); i++)
		{
			AdempiereServer server = m_servers.get(i);
			if (server.isAlive())
				noRunning++;
			else
				noStopped++;
		}
		String info = String.valueOf(m_servers.size())
				+ " - Running=" + noRunning
				+ " - Stopped=" + noStopped;
		return info;
	}	// getServerCount

	/**
	 * Get start date
	 *
	 * @return start date
	 */
	public Timestamp getStartTime()
	{
		return new Timestamp(m_start.getTime());
	}	// getStartTime

}	// AdempiereServerMgr
