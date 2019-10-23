package org.compiere.db;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Getter;
import lombok.Setter;

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

		attrs.setAppsUserName(getSubString(attributesStr, "AppsUserName=", ","));
		attrs.setAppsPassword(getSubString(attributesStr, "AppsPassword=", ","));

		String dbType = attributesStr.substring(attributesStr.indexOf("type=") + 5, attributesStr.indexOf(",DBhost="));
		if (Check.isEmpty(dbType, true))
		{
			dbType = DEFAULT_DbType;
		}
		attrs.setDbType(dbType);
		attrs.setDbHost(attributesStr.substring(attributesStr.indexOf("DBhost=") + 7, attributesStr.indexOf(",DBport=")));
		attrs.setDbPort(attributesStr.substring(attributesStr.indexOf("DBport=") + 7, attributesStr.indexOf(",DBname=")));

		attrs.setDbName(getSubString(attributesStr, "DBname=",","));

		attrs.setDbUid(attributesStr.substring(attributesStr.indexOf("UID=") + 4, attributesStr.indexOf(",PWD=")));
		attrs.setDbPwd(attributesStr.substring(attributesStr.indexOf("PWD=") + 4, attributesStr.indexOf("]")));

		return attrs;
	}

	private static String getSubString(final String attributesStr, String before, String after)
	{
		int indexOfAppsPasswordStart = attributesStr.indexOf(before);
		int indexOfAppsPasswordEnd = attributesStr.indexOf(after, indexOfAppsPasswordStart);
		if (indexOfAppsPasswordStart >= 0 && indexOfAppsPasswordEnd >= 0)
		{
			return attributesStr.substring(indexOfAppsPasswordStart + before.length(), indexOfAppsPasswordEnd);
		}
		return null;
	}

	private static final transient Logger logger = LogManager.getLogger(CConnectionAttributes.class);

	/** Marker used to indicate that we go without an application server */
	public static final String APPS_HOST_None = "MyAppsServer";
	private static final String DEFAULT_DbType = Database.DB_POSTGRESQL;

	/** Name of Connection */
	private String name = "Standard";
	/** Application Host */
	private String appsHost = APPS_HOST_None;
	private int appsPort = CConnection.SERVER_DEFAULT_APPSERVER_PORT;

	private String appsUserName;
	private String appsPassword;

	private String dbType = DEFAULT_DbType;
	private String dbHost = "MyDBServer";
	private int dbPort = 5432;
	private String dbName = "MyDBName";
	private String dbUid;
	private String dbPwd;

	@Getter
	@Setter
	private String rabbitmqHost;
	@Getter
	@Setter
	private String rabbitmqPort;
	@Getter
	@Setter
	private String rabbitmqUsername;
	@Getter
	@Setter
	private String rabbitmqPassword;

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
				.append(",AppsUserName=").append(appsUserName)
				.append(",AppsPassword=").append(appsPassword)
				.append(",type=").append(dbType)
				.append(",DBhost=").append(dbHost)
				.append(",DBport=").append(dbPort)
				.append(",DBname=").append(dbName)
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
			logger.warn("Failed parsing Apps port: " + appsPort, e);
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
			logger.error("Error parsing db port: " + dbPortString, e);
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

	public String getAppsUserName()
	{
		return appsUserName;
	}

	public void setAppsUserName(String appsUserName)
	{
		this.appsUserName = appsUserName;
	}

	public String getAppsPassword()
	{
		return appsPassword;
	}

	public void setAppsPassword(String appsPassword)
	{
		this.appsPassword = appsPassword;
	}
}
