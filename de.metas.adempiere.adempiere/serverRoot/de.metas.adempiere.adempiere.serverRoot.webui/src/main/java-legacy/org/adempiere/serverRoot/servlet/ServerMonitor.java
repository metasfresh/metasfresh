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
package org.adempiere.serverRoot.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.apache.ecs.Element;
import org.apache.ecs.HtmlColor;
import org.apache.ecs.xhtml.a;
import org.apache.ecs.xhtml.b;
import org.apache.ecs.xhtml.body;
import org.apache.ecs.xhtml.br;
import org.apache.ecs.xhtml.font;
import org.apache.ecs.xhtml.form;
import org.apache.ecs.xhtml.h2;
import org.apache.ecs.xhtml.hr;
import org.apache.ecs.xhtml.input;
import org.apache.ecs.xhtml.label;
import org.apache.ecs.xhtml.option;
import org.apache.ecs.xhtml.p;
import org.apache.ecs.xhtml.select;
import org.apache.ecs.xhtml.strong;
import org.apache.ecs.xhtml.table;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.th;
import org.apache.ecs.xhtml.tr;
import org.compiere.Adempiere;
import org.compiere.db.AdempiereDatabase;
import org.compiere.db.CConnection;
import org.compiere.model.AdempiereProcessorLog;
import org.compiere.model.MClient;
import org.compiere.model.MStore;
import org.compiere.model.MSystem;
import org.compiere.server.AdempiereServer;
import org.compiere.server.AdempiereServerMgr;
import org.compiere.util.CLogFile;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.CMemoryUsage;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.TimeUtil;
import org.compiere.util.WebDoc;
import org.compiere.util.WebEnv;
import org.compiere.util.WebUtil;

/**
 * Server Monitor
 *
 * @author Jorg Janke
 * @author tsa
 */
public class ServerMonitor extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2640663091564185714L;

	/** Logger */
	private static final CLogger log = CLogger.getCLogger(ServerMonitor.class);

	private static final String NAME = "serverMonitor";
	
	/**
	 * Shall we allow cache reset options?
	 * 
	 * Default: false, because we had so many problems with this.
	 */
	private static final boolean ALLOW_CacheReset = false;

	/** The Server */
	private AdempiereServerMgr m_serverMgr = null;
	/** Message */
	private p m_message = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		m_message = null;
		if (processLogParameter(request, response))
		{
			return;
		}
		if (processTraceParameter(request, response))
		{
			return;
		}
		if (processEMailParameter(request, response))
		{
			return;
		}
		if (processCacheParameter(request, response))
		{
			return;
		}
		//
		if (!processRunNowParameter(request))
		{
			processActionParameter(request);
		}

		createSummaryPage(request, response);
	}	// doGet

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}	// doPost
	
	private boolean isAllowCacheReset()
	{
		return ALLOW_CacheReset;
	}

	/**
	 * Process Log Parameter and return log page
	 *
	 * @param request request
	 * @param response response
	 * @return true if it was a log request
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processLogParameter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		final String serverID = WebUtil.getParameter(request, "Log");
		if (serverID == null || serverID.length() == 0)
		{
			return false;
		}
		log.info("ServerID=" + serverID);
		final AdempiereServer server = m_serverMgr.getServer(serverID);
		if (server == null)
		{
			m_message = new p();
			m_message.addElement(new strong("Server not found: "));
			m_message.addElement(serverID);
			return false;
		}

		final WebDoc doc = WebDoc.create("Server Monitor Log");
		// Body
		final body b = doc.getBody();
		//
		final p para = new p();
		a link = new a(NAME + "#" + serverID, "Return");
		para.addElement(link);
		b.addElement(para);
		//
		b.addElement(new h2(server.getName()));
		//
		final table table = newPropertiesTable();

		// Header
		tr line = new tr();
		line.addElement(new th().addElement("Created"));
		line.addElement(new th().addElement("Summary"));
		// line.addElement(new th().addElement("Error"));
		line.addElement(new th().addElement("Reference"));
		line.addElement(new th().addElement("TextMsg"));
		// line.addElement(new th().addElement("Description"));
		table.addElement(line);

		final AdempiereProcessorLog[] logs = server.getLogs();
		for (int i = 0; i < logs.length; i++)
		{
			final AdempiereProcessorLog pLog = logs[i];
			line = new tr();
			line.addElement(new td().addElement(WebEnv.getCellContent(pLog.getCreated())));
			line.addElement(new td().addElement(WebEnv.getCellContent(pLog.getSummary())));
			line.addElement(new td().addElement(WebEnv.getCellContent(pLog.getReference())));
			line.addElement(new td().addElement(WebEnv.getCellContent(pLog.getTextMsg())));
			table.addElement(line);
		}
		//
		b.addElement(table);
		link = new a("#top", "Top");
		b.addElement(link);

		// fini
		WebUtil.createResponse(request, response, this, null, doc, false);
		return true;
	}	// processLogParameter

	/**
	 * Process Run Parameter
	 *
	 * @param request request
	 * @return true if it was a Run request
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processRunNowParameter(HttpServletRequest request)
			throws ServletException, IOException
	{
		final String serverID = WebUtil.getParameter(request, "RunNow");
		if (serverID == null || serverID.length() == 0)
		{
			return false;
		}
		log.info("ServerID=" + serverID);
		final AdempiereServer server = m_serverMgr.getServer(serverID);
		if (server == null)
		{
			m_message = new p();
			m_message.addElement(new strong("Server not found: "));
			m_message.addElement(serverID);
			return false;
		}
		//
		server.runNow();
		//
		return true;
	}	// processRunParameter

	/**
	 * Process Action Parameter
	 *
	 * @param request request
	 */
	private void processActionParameter(HttpServletRequest request)
	{
		final String action = WebUtil.getParameter(request, "Action");
		if (action == null || action.length() == 0)
		{
			return;
		}
		log.info("Action=" + action);
		try
		{
			final boolean start = action.startsWith("Start");
			m_message = new p();
			final String msg = (start ? "Started" : "Stopped") + ": ";
			m_message.addElement(new strong(msg));
			//
			final String serverID = action.substring(action.indexOf('_') + 1);
			boolean ok = false;
			if ("All".equals(serverID))
			{
				if (start)
				{
					ok = m_serverMgr.startAll();
				}
				else
				{
					ok = m_serverMgr.stopAll();
				}
				m_message.addElement("All");
			}
			else
			{
				final AdempiereServer server = m_serverMgr.getServer(serverID);
				if (server == null)
				{
					m_message = new p();
					m_message.addElement(new strong("Server not found: "));
					m_message.addElement(serverID);
					return;
				}
				else
				{
					if (start)
					{
						ok = m_serverMgr.start(serverID);
					}
					else
					{
						ok = m_serverMgr.stop(serverID);
					}
					m_message.addElement(server.getName());
				}
			}
			m_message.addElement(ok ? " - OK" : " - Error!");
		}
		catch (Exception e)
		{
			m_message = new p();
			m_message.addElement(new strong("Error processing parameter: " + action));
			m_message.addElement(new br());
			m_message.addElement(e.toString());
		}
	}	// processActionParameter

	/**
	 * Process Trace Parameter
	 *
	 * @param request request
	 * @param response response
	 * @return true if it was a trace request with output
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processTraceParameter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		final String traceCmd = WebUtil.getParameter(request, "Trace");
		final String traceLevel = WebUtil.getParameter(request, "TraceLevel");
		if (traceLevel != null && traceLevel.length() > 0)
		{
			log.info("New Level: " + traceLevel);
			CLogMgt.setLevel(traceLevel);
			Ini.setProperty(Ini.P_TRACELEVEL, traceLevel);
			Ini.saveProperties();
			return false;
		}

		if (traceCmd == null || traceCmd.length() == 0)
			return false;

		log.info("Command: " + traceCmd);
		//
		final CLogFile fileHandler = CLogMgt.getFileLogger();
		if ("ROTATE".equals(traceCmd))
		{
			fileHandler.rotateLog();
			return false;	// re-display
		}
		else if (traceCmd.equals("DELETE"))
		{
			final File logDir = fileHandler.getLogDirectory();
			if (logDir != null && logDir.isDirectory())
			{
				final File[] logs = logDir.listFiles();
				for (int i = 0; i < logs.length; i++)
				{
					final String fileName = logs[i].getAbsolutePath();
					if (fileName.equals(fileHandler.getFileName().orNull()))
					{
						continue;
					}
					if (logs[i].delete())
					{
						log.warning("Deleted: " + fileName);
					}
					else
					{
						log.warning("Not Deleted: " + fileName);
					}
				}
			}
			return false;	// re-display
		}

		//
		// Display current log File
		if (fileHandler != null && fileHandler.getFileName().equals(traceCmd))
		{
			fileHandler.flush();
		}

		//
		// Spool File
		{
			String logFileName = traceCmd;
			if (logFileName.contains(".."))
			{
				log.warning("Invalid log file name: " + logFileName);
				return false;
			}

			final String logDirectoryName = fileHandler.getLogDirectory().getAbsolutePath();

			final File file = new File(logFileName);
			logFileName = file.getAbsolutePath();
			if (!logFileName.startsWith(logDirectoryName))
			{
				log.warning("Log file " + logFileName + " not in log folder: " + logDirectoryName);
				return false;
			}

			if (!file.exists())
			{
				log.warning("Did not find File: " + logFileName);
				return false;
			}
			if (file.length() == 0)
			{
				log.warning("File Length=0: " + logFileName);
				return false;
			}

			// Stream Log
			log.info("Streaming: " + logFileName);
			try
			{
				long time = System.currentTimeMillis();		// timer start
				final int fileLength = (int)file.length();
				final int bufferSize = 2048; // 2k Buffer
				final byte[] buffer = new byte[bufferSize];
				//
				response.setContentType("text/plain");
				response.setBufferSize(bufferSize);
				response.setContentLength(fileLength);
				//
				final FileInputStream fis = new FileInputStream(file);
				final ServletOutputStream out = response.getOutputStream();
				int read = 0;
				while ((read = fis.read(buffer)) > 0)
					out.write(buffer, 0, read);
				out.flush();
				out.close();
				fis.close();
				//
				time = System.currentTimeMillis() - time;
				final double speed = (fileLength / 1024) / ((double)time / 1000);
				log.info("length="
						+ fileLength + " - "
						+ time + " ms - "
						+ speed + " kB/sec");
			}
			catch (IOException ex)
			{
				log.log(Level.SEVERE, "stream" + ex);
			}
		}

		return true;
	}	// processTraceParameter

	/**
	 * Process EMail Parameter
	 *
	 * @param request request
	 * @param response response
	 * @return true if it was a email request with output
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processEMailParameter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		final String email = WebUtil.getParameter(request, "EMail");
		if (email == null || email.length() == 0)
		{
			return false;
		}

		int AD_Client_ID = -1;
		try
		{
			AD_Client_ID = Integer.parseInt(email);
		}
		catch (Exception e)
		{
			log.warning("Parsing: " + email + " - " + e.toString());
		}
		if (AD_Client_ID < 0)
		{
			m_message = new p();
			m_message.addElement("No EMail: " + email);
			return false;
		}

		// log.info ("Test EMail: " + AD_Client_ID);
		final Properties ctx = Env.newTemporaryCtx();
		final MClient client = MClient.get(ctx, AD_Client_ID);
		log.info("Test: " + client);

		m_message = new p();
		m_message.addElement(client.getName() + ": " + client.testEMail());
		return false;
	}	// processEMailParameter

	/**
	 * Process Cache Parameter
	 *
	 * @param request request
	 * @param response response
	 * @return true if it was a email request with output
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean processCacheParameter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		if (!isAllowCacheReset())
		{
			return false;
		}
		
		final String cmd = WebUtil.getParameter(request, "CacheReset");
		if (cmd == null || cmd.length() == 0)
		{
			return false;
		}
		final String tableName = WebUtil.getParameter(request, "CacheTableName");
		final String record_ID = WebUtil.getParameter(request, "CacheRecord_ID");

		m_message = new p();
		try
		{
			if (tableName == null || tableName.length() == 0)
			{
				CacheMgt.get().reset();
				m_message.addElement("Cache Reset: All");
			}
			else if (record_ID == null || record_ID.length() == 0)
			{
				CacheMgt.get().reset(tableName);
				m_message.addElement("Cache Reset: " + tableName);
			}
			else
			{
				CacheMgt.get().reset(tableName, Integer.parseInt(record_ID));
				m_message.addElement("Cache Reset: " + tableName + ", Record_ID=" + record_ID);
			}
		}
		catch (Exception e)
		{
			log.severe(e.toString());
			m_message.addElement("Error: " + e.toString());
		}
		return false;	// continue
	}	// processEMailParameter
	
	private final table newPropertiesTable()
	{
		table table = new table();
		table.setClass("propertiesTable");
		table.setCellSpacing(2);
		table.setCellPadding(2);
		return table;
	}

	/**************************************************************************
	 * Create & Return Summary Page
	 *
	 * @param request request
	 * @param response response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void createSummaryPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		final WebDoc doc = WebDoc.create("Server Monitor");

		// Body
		final body bb = doc.getBody();
		// Message
		if (m_message != null)
		{
			bb.addElement(new hr());
			bb.addElement(m_message);
			bb.addElement(new hr());
		}

		// Summary
		table table = newPropertiesTable();
		//
		tr line = new tr();
		line.addElement(new th().addElement(Adempiere.getName()));
		line.addElement(new td().addElement(Adempiere.getVersion()));
		table.addElement(line);
		line = new tr();
		line.addElement(new th().addElement(Adempiere.getImplementationVendor()));
		line.addElement(new td().addElement(Adempiere.getImplementationVersion()));
		table.addElement(line);
		line = new tr();
		line.addElement(new th().addElement("Manager"));
		line.addElement(new td().addElement(WebEnv.getCellContent(m_serverMgr.getDescription())));
		table.addElement(line);
		line = new tr();
		line.addElement(new th().addElement("Start - Elapsed"));
		line.addElement(new td().addElement(WebEnv.getCellContent(m_serverMgr.getStartTime()) + " - " + TimeUtil.formatElapsed(m_serverMgr.getStartTime())));
		table.addElement(line);
		line = new tr();
		line.addElement(new th().addElement("Servers"));
		line.addElement(new td().addElement(WebEnv.getCellContent(m_serverMgr.getServerCount())));
		table.addElement(line);
		line = new tr();
		line.addElement(new th().addElement("Last Updated"));
		line.addElement(new td().addElement(new Timestamp(System.currentTimeMillis()).toString()));
		table.addElement(line);
		bb.addElement(table);
		//
		p para = new p();
		a link = new a(NAME + "?Action=Start_All", "Start All");
		para.addElement(link);
		para.addElement(" - ");
		link = new a(NAME + "?Action=Stop_All", "Stop All");
		para.addElement(link);
		para.addElement(" - ");
		link = new a(NAME, "Refresh");
		para.addElement(link);
		bb.addElement(para);

		// ***** Server Links *****
		bb.addElement(new hr());
		para = new p();
		final AdempiereServer[] servers = m_serverMgr.getAll();
		for (int i = 0; i < servers.length; i++)
		{
			if (i > 0)
			{
				para.addElement(new br());
			}
			final AdempiereServer server = servers[i];
			link = new a("#" + server.getServerID(), server.getName());
			para.addElement(link);
			font status = null;
			if (server.isAlive())
			{
				status = new font().setColor(HtmlColor.GREEN).addElement(" (Running)");
			}
			else
			{
				status = new font().setColor(HtmlColor.RED).addElement(" (Stopped)");
			}
			para.addElement(status);
		}
		bb.addElement(para);

		// **** Log Management ****
		createLogMgtPage(bb);

		// ***** Server Details *****
		for (int i = 0; i < servers.length; i++)
		{
			final AdempiereServer server = servers[i];
			bb.addElement(new hr());
			bb.addElement(new a().setName(server.getServerID()));
			bb.addElement(new h2(server.getName()));
			//
			table = newPropertiesTable();
			// Status
			line = new tr();
			if (server.isAlive())
			{
				String msg = "Stop";
				if (server.isInterrupted())
				{
					msg += " (Interrupted)";
				}
				link = new a(NAME + "?Action=Stop_" + server.getServerID(), msg);
				if (server.isSleeping())
				{
					line.addElement(new th().addElement("Sleeping"));
					line.addElement(new td().addElement(link));
				}
				else
				{
					line.addElement(new th().addElement("Running"));
					line.addElement(new td().addElement(link));
				}
				table.addElement(line);
				line = new tr();
				line.addElement(new th().addElement("Start - Elapsed"));
				line.addElement(new td().addElement(WebEnv.getCellContent(server.getStartTime())
						+ " - " + TimeUtil.formatElapsed(server.getStartTime())));
			}
			else
			{
				String msg = "Start";
				if (server.isInterrupted())
				{
					msg += " (Interrupted)";
				}
				line.addElement(new th().addElement("Not Started"));
				link = new a(NAME + "?Action=Start_" + server.getServerID(), msg);
				line.addElement(new td().addElement(link));
			}
			table.addElement(line);
			//
			line = new tr();
			line.addElement(new th().addElement("Description"));
			line.addElement(new td().addElement(WebEnv.getCellContent(server.getDescription())));
			table.addElement(line);
			//
			line = new tr();
			line.addElement(new th().addElement("Last Run"));
			line.addElement(new td().addElement(WebEnv.getCellContent(server.getDateLastRun())));
			table.addElement(line);
			line = new tr();
			line.addElement(new th().addElement("Info"));
			line.addElement(new td().addElement(WebEnv.getCellContent(server.getServerInfo())));
			table.addElement(line);
			//
			line = new tr();
			line.addElement(new th().addElement("Next Run"));
			final td td = new td();
			td.addElement(WebEnv.getCellContent(server.getDateNextRun(false)));
			td.addElement(" - ");
			link = new a(NAME + "?RunNow=" + server.getServerID(), "(Run Now)");
			td.addElement(link);
			line.addElement(td);
			table.addElement(line);
			//
			line = new tr();
			line.addElement(new th().addElement("Statistics"));
			line.addElement(new td().addElement(server.getStatistics()));
			table.addElement(line);
			//

			// Add table to Body
			bb.addElement(table);
			link = new a("#top", "Top");
			bb.addElement(link);
			bb.addElement(" - ");
			link = new a(NAME + "?Log=" + server.getServerID(), "Log");
			bb.addElement(link);
			bb.addElement(" - ");
			link = new a(NAME, "Refresh");
			bb.addElement(link);
		}

		// fini
		WebUtil.createResponse(request, response, this, null, doc, false);
	}	// createSummaryPage

	/**
	 * Add Log Management to page
	 *
	 * @param body body
	 */
	private void createLogMgtPage(final body body)
	{
		final Properties ctx = Env.newTemporaryCtx();

		body.addElement(new hr());

		{
			// Ini Parameters
			final table table = newPropertiesTable();
			body.addElement(table);

			//
			final MSystem system = MSystem.get(ctx);
			{
				tr line = new tr();
				line.addElement(new th().addElement(system.getDBAddress()));
				line.addElement(new td().addElement(Ini.getAdempiereHome()));
				table.addElement(line);
			}

			// OS + Name
			{
				tr line = new tr();
				String info = System.getProperty("os.name")
						+ " " + System.getProperty("os.version");
				final String s = System.getProperty("sun.os.patch.level");
				if (s != null && s.length() > 0)
				{
					info += " (" + s + ")";
				}
				line.addElement(new th().addElement(info));
				info = system.getName();
				if (system.getCustomPrefix() != null)
				{
					info += " (" + system.getCustomPrefix() + ")";
				}
				line.addElement(new td().addElement(info));
				table.addElement(line);
			}

			// Java + email
			{
				tr line = new tr();
				String info = System.getProperty("java.vm.name")
						+ " " + System.getProperty("java.vm.version");
				line.addElement(new th().addElement(info));
				line.addElement(new td().addElement(system.getUserName()));
				table.addElement(line);
			}

			// DB + Instance
			{
				tr line = new tr();
				final CConnection cc = CConnection.get();
				final AdempiereDatabase db = cc.getDatabase();
				String info = db.getDescription();
				line.addElement(new th().addElement(info));
				line.addElement(new td().addElement(cc.getConnectionURL()));
				// line.addElement(new td().addElement(system.getDBInstance()));
				table.addElement(line);
				// Processors/Support
				line = new tr();
				line.addElement(new th().addElement("Processor/Support"));
				line.addElement(new td().addElement(system.getNoProcessors() + "/" + system.getSupportUnits()));
				table.addElement(line);
			}

			// Memory
			{
				tr line = new tr();
				final MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
				line.addElement(new th().addElement("VM Memory"));
				line.addElement(new td().addElement(new CMemoryUsage(memory.getNonHeapMemoryUsage()).toString()));
				table.addElement(line);
				line = new tr();
				line.addElement(new th().addElement("Heap Memory"));
				line.addElement(new td().addElement(new CMemoryUsage(memory.getHeapMemoryUsage()).toString()));
				table.addElement(line);
			}

			// Runtime
			{
				tr line = new tr();
				final RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
				line.addElement(new th().addElement("Runtime " + rt.getName()));
				line.addElement(new td().addElement(TimeUtil.formatElapsed(rt.getUptime())));
				table.addElement(line);
			}

			// Threads
			{
				tr line = new tr();
				final ThreadMXBean th = ManagementFactory.getThreadMXBean();
				line.addElement(new th().addElement("Threads " + th.getThreadCount()));
				line.addElement(new td().addElement("Peak=" + th.getPeakThreadCount()
						+ ", Demons=" + th.getDaemonThreadCount()
						+ ", Total=" + th.getTotalStartedThreadCount()));
				table.addElement(line);
			}

			// Transactions
			final ITrx[] trxs = Services.get(ITrxManager.class).getActiveTransactions();
			for (ITrx trx : trxs)
			{
				if (trx != null && trx.isActive())
				{
					tr line = new tr();
					line.addElement(new th().addElement("Active Transaction "));
					line.addElement(new td().addElement("Name=" + trx.getTrxName()
							+ ", StartTime=" + trx.getStartTime()));
					table.addElement(line);
				}
			}

			// Cache Reset
			createLogMgtPage_CacheReset(table);

			// Trace Level
			{
				tr line = new tr();
				line.addElement(new th().addElement(new label("TraceLevel").addElement("Trace Log Level")));
				final form myForm = new form(NAME, form.METHOD_POST, form.ENC_DEFAULT);
				// LogLevel Selection
				option[] options = new option[CLogMgt.LEVELS.length];
				for (int i = 0; i < options.length; i++)
				{
					options[i] = new option(CLogMgt.LEVELS[i].getName());
					options[i].addElement(CLogMgt.LEVELS[i].getName());
					if (CLogMgt.LEVELS[i] == CLogMgt.getLevel())
						options[i].setSelected(true);
				}
				final select sel = new select("TraceLevel", options);
				myForm.addElement(sel);
				myForm.addElement(new input(input.TYPE_SUBMIT, "Set", "Set"));
				line.addElement(new td().addElement(myForm));
				table.addElement(line);
			}

			// Current Log File
			final CLogFile fileHandler = CLogMgt.getFileLogger();
			final String logFileName = fileHandler.getFileName().orNull();
			if (logFileName != null)
			{
				tr line = new tr();
				line.addElement(new th().addElement("Trace File"));
				final a href = new a(NAME + "?Trace=" + logFileName, "Current");
				href.setTarget("_blank");
				line.addElement(new td().addElement(href));
				table.addElement(line);
			}

			// Log File options (rotate, delete all)
			{
				tr line = new tr();
				line.addElement(new td().addElement(new a(NAME + "?Trace=ROTATE", "Rotate Trace Log")));
				line.addElement(new td().addElement(new a(NAME + "?Trace=DELETE", "Delete all Trace Logs")));
				table.addElement(line);
			}
		}

		// List Log Files
		body.addElement(createLogMgtPage_LogFiles());

		// Clients and Web Stores
		{
			table table = newPropertiesTable();
			//
			tr line = new tr();
			final MClient[] clients = MClient.getAll(ctx);
			line.addElement(new th().addElement("Client #" + clients.length + " - EMail Test:"));
			p p = new p();
			for (int i = 0; i < clients.length; i++)
			{
				final MClient client = clients[i];
				if (i > 0)
				{
					p.addElement(" - ");
				}
				p.addElement(new a(NAME + "?EMail=" + client.getAD_Client_ID(), client.getName()));
			}
			if (clients.length == 0)
			{
				p.addElement("&nbsp;");
			}
			line.addElement(new td().addElement(p));
			table.addElement(line);
			//
			line = new tr();
			final MStore[] wstores = MStore.getActive();
			line.addElement(new th().addElement("Active Web Stores #" + wstores.length));
			p = new p();
			for (int i = 0; i < wstores.length; i++)
			{
				final MStore store = wstores[i];
				if (i > 0)
				{
					p.addElement(" - ");
				}
				a a = new a(store.getWebContext(), store.getName());
				a.setTarget("t" + i);
				p.addElement(a);
			}
			if (wstores.length == 0)
			{
				p.addElement("&nbsp;");
			}
			line.addElement(new td().addElement(p));
			table.addElement(line);
			//
			body.addElement(table);
		}
	}	// createLogMgtPage
	
	private void createLogMgtPage_CacheReset(final table table)
	{
		if (!isAllowCacheReset())
		{
			return;
		}
		
		tr line = new tr();
		line.addElement(new th().addElement(CacheMgt.get().toStringX()));
		line.addElement(new td().addElement(new a(NAME + "?CacheReset=Yes", "Reset Cache")));
		table.addElement(line);
		
	}

	private Element createLogMgtPage_LogFiles()
	{
		final CLogFile fileHandler = CLogMgt.getFileLogger();

		final p p = new p();
		p.addElement(new b("All Log Files: "));
		// All in dir
		final File logDir = fileHandler.getLogDirectory();
		if (logDir != null && logDir.isDirectory())
		{
			final File[] logs = logDir.listFiles();
			for (int i = 0; i < logs.length; i++)
			{
				// Skip if is not a file - teo_sarca [ 1726066 ]
				if (!logs[i].isFile())
				{
					continue;
				}
				if (i != 0)
				{
					p.addElement(" - ");
				}
				final String fileName = logs[i].getAbsolutePath();
				final a link = new a(NAME + "?Trace=" + fileName, logs[i].getName());
				link.setTarget("_blank");
				link.setTitle(fileName);
				p.addElement(link);
				final int size = (int)(logs[i].length() / 1024);
				if (size < 1024)
				{
					p.addElement(" (" + size + "k)");
				}
				else
				{
					p.addElement(" (" + size / 1024 + "M)");
				}
			}
		}

		return p;
	}

	/**************************************************************************
	 * Init
	 *
	 * @param config config
	 * @throws javax.servlet.ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		final boolean adempiereStarted = WebEnv.initWeb(config);
		log.info("");
		m_serverMgr = AdempiereServerMgr.get(adempiereStarted);
	}	// init

	/**
	 * Destroy
	 */
	@Override
	public void destroy()
	{
		log.info("destroy");
		m_serverMgr = null;
	}	// destroy

	/**
	 * Log error/warning
	 *
	 * @param message message
	 * @param e exception
	 */
	@Override
	public void log(String message, Throwable e)
	{
		if (e == null)
		{
			log.warning(message);
		}
		log.log(Level.SEVERE, message, e);
	}	// log

	/**
	 * Log debug
	 *
	 * @param message message
	 */
	@Override
	public void log(String message)
	{
		log.fine(message);
	}	// log

	@Override
	public String getServletName()
	{
		return NAME;
	}

	@Override
	public String getServletInfo()
	{
		return "Server Monitor";
	}
}
