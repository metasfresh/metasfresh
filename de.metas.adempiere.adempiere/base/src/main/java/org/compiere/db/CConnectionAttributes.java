package org.compiere.db;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.logging.Level;

import org.adempiere.as.ASFactory;
import org.adempiere.util.Check;
import org.compiere.util.CLogger;

/**
 * {@link CConnection}'s attributes.
 * 
 * @author tsa
 *
 */
public final class CConnectionAttributes
{
	/**
	 * Creates connection attributes object based on given attributes string.
	 * 
	 * NOTE: it is assumed that the connection attributes string was produced by {@link #toString()} method.
	 * 
	 * @param attributesStr
	 */
	public static CConnectionAttributes of(final String attributesStr)
	{
		// NOTE: keep in sync with toString()

		final CConnectionAttributes attrs = new CConnectionAttributes();
		attrs.setName(attributesStr.substring(attributesStr.indexOf("name=") + 5, attributesStr.indexOf(",AppsHost=")));
		attrs.setAppsHost(attributesStr.substring(attributesStr.indexOf("AppsHost=") + 9, attributesStr.indexOf(",AppsPort=")));
		final int index = attributesStr.indexOf("AppsPort=");
		attrs.setAppsPort(attributesStr.substring(index + 9, attributesStr.indexOf(",", index)));
		//
		String dbType = attributesStr.substring(attributesStr.indexOf("type=") + 5, attributesStr.indexOf(",DBhost="));
		if (Check.isEmpty(dbType, true))
		{
			dbType = DEFAULT_DbType;
		}
		attrs.setDbType(dbType);
		attrs.setDbHost(attributesStr.substring(attributesStr.indexOf("DBhost=") + 7, attributesStr.indexOf(",DBport=")));
		attrs.setDbPort(attributesStr.substring(attributesStr.indexOf("DBport=") + 7, attributesStr.indexOf(",DBname=")));
		attrs.setDbName(attributesStr.substring(attributesStr.indexOf("DBname=") + 7, attributesStr.indexOf(",BQ=")));
		//
		attrs.setBequeath(attributesStr.substring(attributesStr.indexOf("BQ=") + 3, attributesStr.indexOf(",FW=")));
		attrs.setViaFirewall(attributesStr.substring(attributesStr.indexOf("FW=") + 3, attributesStr.indexOf(",FWhost=")));
		attrs.setFwHost(attributesStr.substring(attributesStr.indexOf("FWhost=") + 7, attributesStr.indexOf(",FWport=")));
		attrs.setFwPort(attributesStr.substring(attributesStr.indexOf("FWport=") + 7, attributesStr.indexOf(",UID=")));
		//
		attrs.setDbUid(attributesStr.substring(attributesStr.indexOf("UID=") + 4, attributesStr.indexOf(",PWD=")));
		attrs.setDbPwd(attributesStr.substring(attributesStr.indexOf("PWD=") + 4, attributesStr.indexOf("]")));

		return attrs;
	}

	private static final transient CLogger logger = CLogger.getCLogger(CConnectionAttributes.class);

	/** Marker used to indicate that we go without an application server */
	private static final String APPS_HOST_None = "MyAppsServer";
	private static final String DEFAULT_DbType = Database.DB_POSTGRESQL;

	/** Name of Connection */
	private String name = "Standard";
	/** Application Host */
	private String appsHost = APPS_HOST_None;
	private int appsPort = ASFactory.getApplicationServer().getDefaultNamingServicePort();
	private String dbType = DEFAULT_DbType;
	private String dbHost = "MyDBServer";
	private int dbPort = 5432;
	private String dbName = "MyDBName";
	private String dbUid;
	private String dbPwd;

	/** In Memory connection */
	private boolean bequeath = false;
	/** Connection uses Firewall */
	private boolean viaFirewall = false;
	/** Firewall host */
	private String fwHost = "";
	/** Firewall port */
	private int fwPort = 0;

	public CConnectionAttributes()
	{
		super();
	}

	/**
	 * Builds connection attributes string representation.
	 * 
	 * This string can be parsed back by using {@link #of(String)}.
	 * 
	 * @return connection attributes string representation
	 */
	@Override
	public String toString()
	{
		// NOTE: keep in sync with the parser!!!

		final StringBuilder sb = new StringBuilder("CConnection[");
		sb.append("name=").append(name)
				.append(",AppsHost=").append(appsHost)
				.append(",AppsPort=").append(appsPort)
				.append(",type=").append(dbType)
				.append(",DBhost=").append(dbHost)
				.append(",DBport=").append(dbPort)
				.append(",DBname=").append(dbName)
				.append(",BQ=").append(bequeath)
				.append(",FW=").append(viaFirewall)
				.append(",FWhost=").append(fwHost)
				.append(",FWport=").append(fwPort)
				.append(",UID=").append(dbUid)
				.append(",PWD=").append(dbPwd);
		sb.append("]");
		return sb.toString();
	}	// toStringLong

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getAppsHost()
	{
		return appsHost;
	}

	public void setAppsHost(final String appsHost)
	{
		this.appsHost = appsHost;
	}

	public boolean isNoAppsHost()
	{
		return appsHost == null || appsHost.equals(APPS_HOST_None);
	}

	public int getAppsPort()
	{
		return appsPort;
	}

	public void setAppsPort(final int appsPort)
	{
		this.appsPort = appsPort;
	}

	void setAppsPort(final String appsPort)
	{
		try
		{
			if (appsPort == null || appsPort.length() == 0)
			{
				;
			}
			else
			{
				setAppsPort(Integer.parseInt(appsPort));
			}
		}
		catch (final Exception e)
		{
			logger.log(Level.WARNING, "Failed parsing Apps port: " + appsPort, e);
		}
	} 	// setAppsPort

	public String getDbType()
	{
		return dbType;
	}

	public void setDbType(final String type)
	{
		Check.assumeNotEmpty(dbType, "dbType not empty");
		this.dbType = type;
	}

	public String getDbHost()
	{
		return dbHost;
	}

	public void setDbHost(final String dbHost)
	{
		this.dbHost = dbHost;
	}

	public int getDbPort()
	{
		return dbPort;
	}

	public void setDbPort(final int dbPort)
	{
		this.dbPort = dbPort;
	}

	void setDbPort(final String dbPortString)
	{
		try
		{
			if (dbPortString == null || dbPortString.length() == 0)
			{
				;
			}
			else
			{
				setDbPort(Integer.parseInt(dbPortString));
			}
		}
		catch (final Exception e)
		{
			logger.log(Level.SEVERE, "Error parsing db port: " + dbPortString, e);
		}
	} 	// setDbPort

	public String getDbName()
	{
		return dbName;
	}

	public void setDbName(final String dbName)
	{
		this.dbName = dbName;
	}

	public String getDbUid()
	{
		return dbUid;
	}

	public void setDbUid(final String dbUid)
	{
		this.dbUid = dbUid;
	}

	public String getDbPwd()
	{
		return dbPwd;
	}

	public void setDbPwd(final String dbPwd)
	{
		this.dbPwd = dbPwd;
	}

	public boolean isBequeath()
	{
		return bequeath;
	}

	public void setBequeath(final boolean bequeath)
	{
		this.bequeath = bequeath;
	}

	public void setBequeath(final String bequeathString)
	{
		try
		{
			setBequeath(Boolean.valueOf(bequeathString).booleanValue());
		}
		catch (final Exception e)
		{
			logger.log(Level.WARNING, "Failed parsing bequeath: " + bequeathString, e);
		}
	}

	public boolean isViaFirewall()
	{
		return viaFirewall;
	}

	public void setViaFirewall(final boolean viaFirewall)
	{
		this.viaFirewall = viaFirewall;
	}

	public void setViaFirewall(final String viaFirewallString)
	{
		try
		{
			setViaFirewall(Boolean.valueOf(viaFirewallString).booleanValue());
		}
		catch (final Exception e)
		{
			logger.log(Level.WARNING, "Failed parsing viaFirewall: " + viaFirewallString, e);
		}
	}

	public String getFwHost()
	{
		return fwHost;
	}

	public void setFwHost(final String fwHost)
	{
		this.fwHost = fwHost;
	}

	public int getFwPort()
	{
		return fwPort;
	}

	public void setFwPort(final int fwPort)
	{
		this.fwPort = fwPort;
	}

	public void setFwPort(final String fwPortStr)
	{
		try
		{
			if (fwPortStr == null || fwPortStr.length() == 0)
			{
				;
			}
			else
			{
				setFwPort(Integer.parseInt(fwPortStr));
			}
		}
		catch (final Exception e)
		{
			logger.log(Level.WARNING, "Failed parsing FW port: " + fwPortStr, e);
		}
	}

}
