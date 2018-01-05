package de.metas.migration.impl;

/*
 * #%L
 * de.metas.migration.base
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

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import de.metas.migration.IDatabase;
import de.metas.migration.IScriptsRegistry;
import de.metas.migration.sql.ISQLDatabaseDriver;
import de.metas.migration.sql.SQLDatabaseDriverFactory;

public class SQLDatabase implements IDatabase
{
	private final String dbType;
	private final String dbHostname;
	private final String dbPort;
	private final String dbName;
	private final String dbUser;
	private final String dbPassword;

	private final SQLDatabaseScriptsRegistry scriptsRegistry;

	private Connection conn;

	public SQLDatabase(final String dbType, final String dbHostname, final String dbPort, final String dbName, final String dbUser, final String dbPassword)
	{
		super();
		this.dbType = dbType;
		this.dbHostname = dbHostname;
		this.dbPort = dbPort;
		this.dbName = dbName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;

		scriptsRegistry = new SQLDatabaseScriptsRegistry(this);
	}

	public SQLDatabase(final String dbUrl, final String dbUser, final String dbPassword)
	{
		super();

		final URI url;
		try
		{
			final String urlStrFixed = new URI(dbUrl).getSchemeSpecificPart(); // URL without "jdbc:" prefix
			url = new URI(urlStrFixed);
		}
		catch (final URISyntaxException e)
		{
			throw new IllegalArgumentException("Invalid url: " + dbUrl, e);
		}

		dbType = url.getScheme();
		dbHostname = url.getHost();
		dbPort = url.getPort() == -1 ? null : String.valueOf(url.getPort());

		String dbName = url.getPath();
		if (dbName != null)
		{
			while (dbName.startsWith("/"))
			{
				dbName = dbName.substring(1);
			}
		}
		this.dbName = dbName;

		this.dbUser = dbUser;
		this.dbPassword = dbPassword;

		scriptsRegistry = new SQLDatabaseScriptsRegistry(this);
	}

	@Override
	public String toString()
	{
		return "SQLDatabase [dbType=" + dbType
				+ ", dbHostname=" + dbHostname
				+ ", dbPort=" + dbPort
				+ ", dbName=" + dbName
				+ ", dbUser=" + dbUser
				+ ", dbPassword=********" // + dbPassword
				+ "]";
	}

	@Override
	public String getDbType()
	{
		return dbType;
	}

	@Override
	public String getDbHostname()
	{
		return dbHostname;
	}

	@Override
	public String getDbPort()
	{
		return dbPort;
	}

	@Override
	public String getDbName()
	{
		return dbName;
	}

	@Override
	public String getDbUser()
	{
		return dbUser;
	}

	@Override
	public String getDbPassword()
	{
		return dbPassword;
	}

	@Override
	public IScriptsRegistry getScriptsRegistry()
	{
		return scriptsRegistry;
	}

	@Override
	public Connection getConnection()
	{
		//
		// First time => driver initialization
		if (conn == null)
		{
			final ISQLDatabaseDriver dbDriver = SQLDatabaseDriverFactory.get().getSQLDatabaseDriver(dbType);
			if (dbDriver == null)
			{
				throw new IllegalStateException("No driver found for database type: " + dbType);
			}
		}

		try
		{
			if (conn != null && !conn.isClosed())
			{
				return conn;
			}

			final ISQLDatabaseDriver dbDriver = SQLDatabaseDriverFactory.get().getSQLDatabaseDriver(dbType);
			conn = dbDriver.getConnection(dbHostname, dbPort, dbName, dbUser, dbPassword);
			conn.setAutoCommit(true);
		}
		catch (final SQLException e)
		{
			throw new RuntimeException("Failed to get a JDBC connection. Please check your config for : " + this, e);
		}

		return conn;
	}
}
