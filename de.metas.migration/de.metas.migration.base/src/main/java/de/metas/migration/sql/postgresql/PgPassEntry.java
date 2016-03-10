package de.metas.migration.sql.postgresql;

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

public class PgPassEntry
{
	private final String host;
	private final String port;
	private final String dbName;
	private final String user;
	private final String password;

	/* package */PgPassEntry(final String host, final String port, final String dbName, final String user, final String password)
	{
		super();
		if (host == null || host.isEmpty())
		{
			throw new IllegalArgumentException("host is empty. Please use " + PgPassFile.ANY + " in this case");
		}
		this.host = host;

		if (port == null || port.isEmpty())
		{
			throw new IllegalArgumentException("port is empty. Please use " + PgPassFile.ANY + " in this case");
		}
		this.port = port;

		this.dbName = dbName;
		if (dbName == null || dbName.isEmpty())
		{
			throw new IllegalArgumentException("dbName is empty. Please use " + PgPassFile.ANY + " in this case");
		}

		this.user = user;
		if (user == null || user.isEmpty())
		{
			throw new IllegalArgumentException("user is empty. Please use " + PgPassFile.ANY + " in this case");
		}

		this.password = password;
		// NOTE: we allow the password to be null in case this is a lookup
	}

	@Override
	public String toString()
	{
		return "PgPassEntry [host=" + host + ", port=" + port + ", dbName=" + dbName + ", user=" + user + ", password=" + password + "]";
	}

	public String getHost()
	{
		return host;
	}

	public String getPort()
	{
		return port;
	}

	public String getDbName()
	{
		return dbName;
	}

	public String getUser()
	{
		return user;
	}

	public String getPassword()
	{
		return password;
	}
}  // PgPassEntry
