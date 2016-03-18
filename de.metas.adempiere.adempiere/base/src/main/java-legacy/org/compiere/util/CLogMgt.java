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
package org.compiere.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.adempiere.service.IClientDAO;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_Client;

/**
 *	Adempiere Log Management.
 *	
 *  @author Jorg Janke
 *  @version $Id: CLogMgt.java,v 1.4 2006/07/30 00:54:36 jjanke Exp $
 */
public final class CLogMgt
{
	/**
	 * 	Initialize Logging
	 * 
	 * 	@param isClient client
	 */
	public static void initialize(final boolean isClient)
	{
		if (s_handlers != null)
		{
			return;
		}

		//
		// Load client configuration from properties
		if (isClient)
		{
			LogManager mgr = LogManager.getLogManager();
			try 
			{	//	Load Logging config from org.compiere.util.*properties
				String fileName = "logClient.properties";
				InputStream in = CLogMgt.class.getResourceAsStream(fileName);
				BufferedInputStream bin = new BufferedInputStream(in);
				mgr.readConfiguration(bin);
				in.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		//
		//	Create Handler List and populate it with current registered JUL loggers
		s_handlers = new CopyOnWriteArrayList<>();
		try
		{
			final Logger rootLogger = getRootLogger();
			for (final Handler handler : rootLogger.getHandlers())
			{
				s_handlers.addIfAbsent(handler);
			}
			/**
			 * Enumeration en = mgr.getLoggerNames();
			 * while (en.hasMoreElements())
			 * {
			 * Logger lll = Logger.getLogger(en.nextElement().toString());
			 * System.out.println(lll.getName() + " (" + lll + ")");
			 * // System.out.println("- level=" + lll.getLevel());
			 * // System.out.println("- parent=" + lll.getParent() + " - UseParentHandlers=" + lll.getUseParentHandlers());
			 * // System.out.println("- filter=" + lll.getFilter());
			 * handlers = lll.getHandlers();
			 * // System.out.println("- handlers=" + handlers.length);
			 * for (int i = 0; i < handlers.length; i ++)
			 * {
			 * System.out.println("  > " + handlers[i]);
			 * if (!s_handlers.contains(handlers[i]))
			 * s_handlers.add(handlers[i]);
			 * }
			 * // System.out.println();
			 * }
			 * /**
			 **/
		}
		catch (Exception e)
		{
			if (e instanceof ClassNotFoundException)	//	WebStart
				;
			/**
			 * Can't load log handler "org.compiere.util.CLogConsole"
			 * java.lang.ClassNotFoundException: org.compiere.util.CLogConsole
			 * java.lang.ClassNotFoundException: org.compiere.util.CLogConsole
			 * at java.net.URLClassLoader$1.run(Unknown Source)
			 * at java.security.AccessController.doPrivileged(Native Method)
			 * at java.net.URLClassLoader.findClass(Unknown Source)
			 * at java.lang.ClassLoader.loadClass(Unknown Source)
			 * at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
			 * at java.lang.ClassLoader.loadClass(Unknown Source)
			 * at java.util.logging.LogManager$7.run(Unknown Source)
			 * at java.security.AccessController.doPrivileged(Native Method)
			 * at java.util.logging.LogManager.initializeGlobalHandlers(Unknown Source)
			 * at java.util.logging.LogManager.access$900(Unknown Source)
			 * at java.util.logging.LogManager$RootLogger.getHandlers(Unknown Source)
			 * at org.compiere.util.CLogMgt.initialize(CLogMgt.java:67)
			 * at org.compiere.Adempiere.startup(Adempiere.java:389)
			 * at org.compiere.Adempiere.main(Adempiere.java:500)
			 * at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
			 * at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
			 * at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
			 * at java.lang.reflect.Method.invoke(Unknown Source)
			 * at com.sun.javaws.Launcher.executeApplication(Unknown Source)
			 * at com.sun.javaws.Launcher.executeMainClass(Unknown Source)
			 * at com.sun.javaws.Launcher.continueLaunch(Unknown Source)
			 * at com.sun.javaws.Launcher.handleApplicationDesc(Unknown Source)
			 * at com.sun.javaws.Launcher.handleLaunchFile(Unknown Source)
			 * at com.sun.javaws.Launcher.run(Unknown Source)
			 * at java.lang.Thread.run(Unknown Source)
			**/
			else
				System.err.println(e.toString());
		}
		
		//	Check Loggers
		addHandler(CLogErrorBuffer.get());
		
		if (CLogConsole.get(false) == null)
		{
			addHandler(CLogConsole.get(true));
		}
		
		// File logger
		addHandler(CLogFile.get());
		
		setFormatter(CLogFormatter.get());
		setFilter(CLogFilter.get());
	//	setLevel(s_currentLevel);
	//	setLoggerLevel(Level.ALL, null);
		//
		CLogMgtLog4J.initialize(isClient);
	//	System.out.println("Handlers=" + s_handlers.size() + ", Level=" + s_currentLevel);
	}	//	initialize

	/** Handlers			*/
	private static CopyOnWriteArrayList<Handler> s_handlers = null;
	/** Current Log Level	*/
	private static Level		s_currentLevel = Level.INFO;

	/** Logger				*/
	private static Logger		log = Logger.getAnonymousLogger();
	/** LOG Levels			*/
	public static final Level[]	LEVELS = new Level[]
	{ Level.OFF, Level.SEVERE, Level.WARNING, Level.INFO,
			Level.CONFIG, Level.FINE, Level.FINER, Level.FINEST, Level.ALL };
	
	/** New Line			*/
	private static final String NL = System.getProperty("line.separator");
	
	private static final Logger getRootLogger()
	{
		return Logger.getLogger("");
	}

	/**
	 * 	Add Handler (to root logger)
	 *
	 *	@param handler new Handler
	 */
	public static void addHandler(final Handler handler)
	{
		if (handler == null)
			return;
		
		final Logger rootLogger = getRootLogger();
		rootLogger.removeHandler(handler); // remove it just to make sure we are not adding it twice
		rootLogger.addHandler(handler);
		//
		s_handlers.addIfAbsent(handler);
		log.log(Level.CONFIG, "Handler={0}", handler);
	}	//	addHandler
	
	/**
	 * 	Set Formatter for all handlers
	 *
	 *	@param formatter formatter
	 */
	private static void setFormatter(java.util.logging.Formatter formatter)
	{
		for (final Handler handler : s_handlers)
		{
			handler.setFormatter(formatter);
		}
		log.log(Level.CONFIG, "Formatter=" + formatter);
	}	//	setFormatter

	/**
	 * 	Set Filter for all handlers
	 *
	 *	@param filter filter
	 */
	private static void setFilter(final Filter filter)
	{
		for (final Handler handler : s_handlers)
		{
			handler.setFilter(filter);
		}
		log.log(Level.CONFIG, "Filter=" + filter);
	}	//	setFilter

	/**
	 * 	Set Level for all Loggers
	 *
	 *	@param level log level
	 *	@param loggerNamePart optional partial class/logger name
	 */
	public static void setLoggerLevel(final Level level, final String loggerNamePart)
	{
		if (level == null)
		{
			return;
		}
		
		final LogManager mgr = LogManager.getLogManager();
		final Enumeration<String> en = mgr.getLoggerNames();
		while (en.hasMoreElements())
		{
			final String name = en.nextElement().toString();
			if (loggerNamePart == null  || name.indexOf(loggerNamePart) != -1)
			{
				final Logger logger = Logger.getLogger(name);
				logger.setLevel(level);
			}
		}
	}	//	setLoggerLevel

	/**
	 * 	Set Level for all handlers
	 *
	 *	@param level log level
	 */
	public static void setLevel(final Level level)
	{
		if (level == null)
			return;
		
		if (s_handlers == null)
			initialize(true);
		//
		for (final Handler handler : s_handlers)
		{
			handler.setLevel(level);
		}
		//	JDBC if ALL
		setJDBCDebug(s_currentLevel.intValue() == Level.ALL.intValue());
		//
		if (level.intValue() != s_currentLevel.intValue())
		{
			setLoggerLevel(level, null);
			log.config(level.toString());
		}
		s_currentLevel = level;
	}	//	setHandlerLevel

	/**
	 * 	Set Level
	 *
	 *	@param intLevel integer value of level
	 */
	public static void setLevel(final int intLevel)
	{
		setLevel(String.valueOf(intLevel));
	}	//	setLevel
	
	/**
	 * 	Set Level
	 *
	 *	@param levelString string representation of level
	 */
	public static void setLevel(final String levelString)
	{
		if (levelString == null)
			return;
		//
		for (final Level level : LEVELS) 
		{
		    if (level.getName().equals(levelString)) 
		    {
		    	setLevel(level);
		    	return;
		    }
		}
		log.log(Level.CONFIG, "Ignored: " + levelString);
	}	//	setLevel

	/**
	 * 	Set JDBC Debug
	 *
	 *	@param enable
	 */
	public static void setJDBCDebug(boolean enable)
	{
		if (enable)
			DriverManager.setLogWriter(new PrintWriter(System.err));
		else
			DriverManager.setLogWriter(null);
	}	//	setJDBCDebug
	
	/**
	 * 	Get logging Level of handlers
	 *
	 *	@return logging level
	 */
	public static Level getLevel()
	{
		return s_currentLevel;
	}	//	getLevel
	
	/**
	 * 	Get logging Level of handlers
	 *
	 *	@return logging level
	 */
	public static int getLevelAsInt()
	{
		return s_currentLevel.intValue();
	}	//	getLevel
	
	/**
	 * 	Is Logging Level logged
	 *
	 *	@param level level
	 *	@return true if it is logged
	 */
	public static boolean isLevel(Level level)
	{
		if (level == null)
			return false;
		return level.intValue() >= s_currentLevel.intValue(); 
	}	//	isLevel
	
	/**
	 * 	Is Logging Level FINEST logged
	 *
	 *	@return true if it is logged
	 */
	public static boolean isLevelAll()
	{
		return Level.ALL.intValue() == s_currentLevel.intValue(); 
	}	//	isLevelFinest

	/**
	 * 	Is Logging Level FINEST logged
	 *
	 *	@return true if it is logged
	 */
	public static boolean isLevelFinest()
	{
		return Level.FINEST.intValue() >= s_currentLevel.intValue(); 
	}	//	isLevelFinest
	
	/**
	 * 	Is Logging Level FINER logged
	 *
	 *	@return true if it is logged
	 */
	public static boolean isLevelFiner()
	{
		return Level.FINER.intValue() >= s_currentLevel.intValue(); 
	}	//	isLevelFiner
	
	/**
	 * 	Is Logging Level FINE logged
	 *
	 *	@return true if it is logged
	 */
	public static boolean isLevelFine()
	{
		return Level.FINE.intValue() >= s_currentLevel.intValue(); 
	}	//	isLevelFine

	/**
	 * 	Is Logging Level INFO logged
	 *
	 *	@return true if it is logged
	 */
	public static boolean isLevelInfo()
	{
		return Level.INFO.intValue() >= s_currentLevel.intValue(); 
	}	//	isLevelFine

	/**
	 * 	Enable/Disable logging (of handlers)
	 *
	 *	@param enableLogging true if logging enabled
	 */
	public static void enable(boolean enableLogging)
	{
		if (enableLogging)
			setLevel(s_currentLevel);
		else
		{
			Level level = s_currentLevel;
			setLevel(Level.OFF);
			s_currentLevel = level;
		}
	}	//	enable
	
	/**
	 * 	Shutdown Logging system
	 */
	public static void shutdown()
	{
		LogManager mgr = LogManager.getLogManager();
		mgr.reset();
	}	//	shutdown
	
	/**
	 *  Get Adempiere System Info
	 * 
	 *  @param sb buffer to append or null
	 *  @return Info as multiple Line String
	 */
	public static StringBuffer getInfo(StringBuffer sb)
	{
		if (sb == null)
			sb = new StringBuffer();
		final String eq = " = ";
		sb.append(getMsg("Host")).append(eq).append(getServerInfo()).append(NL);
		sb.append(getMsg("Database")).append(eq).append(getDatabaseInfo()).append(NL);
		sb.append(getMsg("Schema")).append(eq).append(CConnection.get().getDbUid()).append(NL);
		//
		sb.append(getMsg("AD_User_ID")).append(eq).append(Env.getContext(Env.getCtx(), "#AD_User_Name")).append(NL);
		sb.append(getMsg("AD_Role_ID")).append(eq).append(Env.getContext(Env.getCtx(), "#AD_Role_Name")).append(NL);
		//
		sb.append(getMsg("AD_Client_ID")).append(eq).append(Env.getContext(Env.getCtx(), "#AD_Client_Name")).append(NL);
		sb.append(getMsg("AD_Org_ID")).append(eq).append(Env.getContext(Env.getCtx(), "#AD_Org_Name")).append(NL);
		//
		sb.append(getMsg("Date")).append(eq).append(Env.getContext(Env.getCtx(), Env.CTXNAME_Date)).append(NL);
		sb.append(getMsg("Printer")).append(eq).append(Env.getContext(Env.getCtx(), Env.CTXNAME_Printer)).append(NL);
		//
		// Show Implementation Vendor / Version - teo_sarca, [ 1622855 ]
		sb.append(getMsg("ImplementationVendor")).append(eq).append(org.compiere.Adempiere.getImplementationVendor()).append(NL);
		sb.append(getMsg("ImplementationVersion")).append(eq).append(org.compiere.Adempiere.getImplementationVersion()).append(NL);
		//
		sb.append("AdempiereHome = ").append(Adempiere.getAdempiereHome()).append(NL);
		sb.append("AdempiereProperties = ").append(Ini.getPropertyFileName()).append(NL);
		sb.append(Env.getLanguage(Env.getCtx())).append(NL);
		final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(Env.getCtx());
		sb.append(client).append(NL);
		sb.append(getMsg("IsMultiLingualDocument")).append(eq).append(client.isMultiLingualDocument()).append(NL);
		sb.append("BaseLanguage = ").append(Env.isBaseLanguage(Env.getCtx(), "AD_Window")).append("/").append(Env.isBaseLanguage(Env.getCtx(), "C_UOM")).append(NL);
		sb.append("JVM=").append(Adempiere.getJavaInfo()).append(NL);
		sb.append("java.io.tmpdir=" + System.getProperty("java.io.tmpdir")).append(NL);
		sb.append("OS Info=").append(Adempiere.getOSInfo()).append(NL);
		sb.append("OS User=").append(System.getProperty("user.name")).append(NL);

		//
		// Show JVM info
		try
		{
			final RuntimeMXBean runtimeMXBean = java.lang.management.ManagementFactory.getRuntimeMXBean();
			
			sb.append(NL);
			sb.append("=== JVM Runtime Info ===").append(NL);
			final String jvmName = runtimeMXBean.getName(); // JVM runtime name (e.g. 21744@NBRO04)
			sb.append("JVM Runtime Name=").append(jvmName).append(NL);
			sb.append("StartTime=").append(new Date(runtimeMXBean.getStartTime())).append(NL);
			sb.append("UpTime=").append(runtimeMXBean.getUptime()).append("ms").append(NL);
			sb.append("Java Home=").append(System.getProperty("java.home", "?")).append(NL);
			sb.append("BootClassPath=").append(runtimeMXBean.getBootClassPath()).append(NL);
			sb.append("ClassPath=").append(runtimeMXBean.getClassPath()).append(NL);
			sb.append("LibraryPath=").append(runtimeMXBean.getLibraryPath()).append(NL);

			//
			final List<String> jvmArguments = runtimeMXBean.getInputArguments();
			sb.append(NL);
			sb.append("=== JVM Runtime Arguments ===").append(NL);
			for (final String jvmArg : jvmArguments)
			{
				sb.append(jvmArg).append(NL);
			}
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, "Error while getting JVM runtime info", e);
		}
		
		//
		return sb;
	}   //  getInfo

	/**
	 *  Create System Info
	 * 
	 *  @param sb Optional string buffer
	 *  @param ctx Environment
	 *  @return System Info
	 */
	public static StringBuffer getInfoDetail(StringBuffer sb, Properties ctx)
	{
		if (sb == null)
			sb = new StringBuffer();
		if (ctx == null)
			ctx = Env.getCtx();
		//  Envoronment
		CConnection cc = CConnection.get();
		sb.append(NL)
			.append("=== Environment === ").append(NL)
			.append(Adempiere.getCheckSum()).append(NL)
			.append(Adempiere.getSummaryAscii()).append(NL)
			.append(getLocalHost()).append(NL)
			.append(cc.getName() + " " + cc.getDbUid() + "@" + cc.getConnectionURL()).append(NL)
			.append(cc.getInfo()).append(NL);
		//  Context
		sb.append(NL)
			.append("=== Context ===").append(NL);
		String[] context = Env.getEntireContext(ctx);
		Arrays.sort(context);
		for (int i = 0; i < context.length; i++)
			sb.append(context[i]).append(NL);
		//  System
		sb.append(NL)
			.append("=== System ===").append(NL);
		Object[] pp = System.getProperties().keySet().toArray();
		Arrays.sort(pp);
		for (int i = 0; i < pp.length; i++)
		{
			String key = pp[i].toString();
			String value = System.getProperty(key);
			sb.append(key).append("=").append(value).append(NL);
		}
		return sb;
	}   //  getInfoDetail

	/**
	 *  Get translated Message, if DB connection exists
	 * 
	 *  @param msg AD_Message
	 *  @return translated msg if connected
	 */
	private static String getMsg(String msg)
	{
		if (DB.isConnected())
			return Services.get(IMsgBL.class).translate(Env.getCtx(), msg);
		return msg;
	}   //  getMsg

	/**
	 *  Get Server Info.
	 * 
	 *  @return host : port (NotActive) via CMhost : port
	 */
	private static String getServerInfo()
	{
		final StringBuilder sb = new StringBuilder();
		final CConnection cc = CConnection.get();
		if (cc == null)
		{
			return "unknown";
		}
		
		//  Host
		sb.append(cc.getAppsHost()).append(" : ")
			.append(cc.getAppsPort())
			.append(" (");

		//  Server
		if (cc.isAppsServerOK(false))
		{
			final String serverVersion = cc.getServerVersionInfo();
			sb.append("version ").append(serverVersion);
		}
		else
		{
			sb.append(getMsg("NotActive"));
		}
		//
		sb.append(")");
		//
		return sb.toString();
	}   //  getServerInfo

	/**
	 *  Get Database Info
	 * 
	 *  @return host : port : sid
	 */
	private static String getDatabaseInfo()
	{
		final StringBuilder sb = new StringBuilder();
		final CConnection cc = CConnection.get();
		sb.append(cc.getDbHost()).append(" : ")
			.append(cc.getDbPort()).append(" / ")
			.append(cc.getDbName());
		//  Connection Manager
		
		return sb.toString();
	}   //  getDatabaseInfo
	
	/**
	 *  Get Localhost
	 * 
	 *  @return local host
	 */
	private static String getLocalHost()
	{
		try
		{
			InetAddress id = InetAddress.getLocalHost();
			return id.toString();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "getLocalHost", e);
		}
		return "-no local host info -";
	}   //  getLocalHost
	
	public static CLogErrorBuffer getErrorBuffer()
	{
		return CLogErrorBuffer.get();
	}

	public static CLogFile getFileLogger()
	{
		return CLogFile.get();
	}
	
	/**************************************************************************
	 * 	CLogMgt
	 */
	private CLogMgt()
	{
		super();
	}
}	//	CLogMgt
