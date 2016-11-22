package org.compiere.util;

import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.service.IClientDAO;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_Client;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Helper class to provide technical support informations.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class SupportInfo
{
	/** New Line */
	private static final String NL = System.getProperty("line.separator");
	private static final Logger logger = LogManager.getLogger(SupportInfo.class);

	/**
	 * Get System Info
	 *
	 * @return Info as multiple Line String
	 */
	public static String getInfo()
	{
		return getInfo(null).toString();
	}

	/**
	 * Get System Info
	 *
	 * @param sb buffer to append or null
	 * @return Info as multiple Line String
	 */
	public static StringBuilder getInfo(StringBuilder sb)
	{
		if (sb == null)
		{
			sb = new StringBuilder();
		}
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
		catch (final Exception e)
		{
			logger.warn("Error while getting JVM runtime info", e);
		}

		//
		return sb;
	}   // getInfo

	/**
	 * Create System Info
	 *
	 * @param sb Optional string buffer
	 * @param ctx Environment
	 * @return System Info
	 */
	public static StringBuilder getInfoDetail(StringBuilder sb, Properties ctx)
	{
		if (sb == null)
		{
			sb = new StringBuilder();
		}
		if (ctx == null)
		{
			ctx = Env.getCtx();
		}
		// Envoronment
		final CConnection cc = CConnection.get();
		sb.append(NL)
				.append("=== Environment === ").append(NL)
				.append(Adempiere.getCheckSum()).append(NL)
				.append(Adempiere.getSummaryAscii()).append(NL)
				.append(getLocalHost()).append(NL)
				.append(cc.getName() + " " + cc.getDbUid() + "@" + cc.getConnectionURL()).append(NL)
				.append(cc.getInfo()).append(NL);
		// Context
		sb.append(NL)
				.append("=== Context ===").append(NL);
		final String[] context = Env.getEntireContext(ctx);
		Arrays.sort(context);
		for (int i = 0; i < context.length; i++)
		{
			sb.append(context[i]).append(NL);
		}
		// System
		sb.append(NL)
				.append("=== System ===").append(NL);
		final Object[] pp = System.getProperties().keySet().toArray();
		Arrays.sort(pp);
		for (int i = 0; i < pp.length; i++)
		{
			final String key = pp[i].toString();
			final String value = System.getProperty(key);
			sb.append(key).append("=").append(value).append(NL);
		}
		return sb;
	}   // getInfoDetail

	/**
	 * Get Server Info.
	 *
	 * @return host : port (NotActive) via CMhost : port
	 */
	private static String getServerInfo()
	{
		final StringBuilder sb = new StringBuilder();
		final CConnection cc = CConnection.get();
		if (cc == null)
		{
			return "unknown";
		}

		// Host
		sb.append(cc.getAppsHost()).append(" : ")
				.append(cc.getAppsPort())
				.append(" (");

		// Server
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
	}   // getServerInfo

	/**
	 * Get Database Info
	 *
	 * @return host : port : sid
	 */
	private static String getDatabaseInfo()
	{
		final StringBuilder sb = new StringBuilder();
		final CConnection cc = CConnection.get();
		sb.append(cc.getDbHost()).append(" : ")
				.append(cc.getDbPort()).append(" / ")
				.append(cc.getDbName());
		// Connection Manager

		return sb.toString();
	}   // getDatabaseInfo

	/**
	 * Get Localhost
	 *
	 * @return local host
	 */
	private static String getLocalHost()
	{
		try
		{
			final InetAddress id = InetAddress.getLocalHost();
			return id.toString();
		}
		catch (final Exception e)
		{
			logger.error("getLocalHost", e);
		}
		return "-no local host info -";
	}   // getLocalHost

	/**
	 * Get translated Message, if DB connection exists
	 *
	 * @param msg AD_Message
	 * @return translated msg if connected
	 */
	private static String getMsg(final String msg)
	{
		if (DB.isConnected())
		{
			return Services.get(IMsgBL.class).translate(Env.getCtx(), msg);
		}
		return msg;
	}   // getMsg

}
