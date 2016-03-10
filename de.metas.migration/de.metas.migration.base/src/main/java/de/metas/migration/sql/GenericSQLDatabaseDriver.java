package de.metas.migration.sql;

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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericSQLDatabaseDriver implements ISQLDatabaseDriver
{
	private final String _dbType;
	private final String _jdbcDriverClassname;

	private boolean initialized;

	public GenericSQLDatabaseDriver(final String dbType, final String jdbcDriverClassname)
	{
		super();

		if (dbType == null || dbType.isEmpty())
		{
			throw new IllegalArgumentException("dbType not allowed to be empty");
		}
		_dbType = dbType;

		if (jdbcDriverClassname == null || jdbcDriverClassname.isEmpty())
		{
			throw new IllegalArgumentException("jdbcDriverClassname not allowed to be empty");
		}
		_jdbcDriverClassname = jdbcDriverClassname;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["

				+ "dbType=" + _dbType
				+ ", jdbcDriverClassname=" + _jdbcDriverClassname
				+ ", initialized=" + initialized
				+ "]";
	}

	protected final boolean isInitialized()
	{
		return initialized;
	}

	protected final String getDbType()
	{
		return _dbType;
	}

	protected final String getJdbcDriverClassname()
	{
		return _jdbcDriverClassname;
	}

	private final void init()
	{
		if (initialized)
		{
			return;
		}

		try
		{
			final String jdbcDriverClassname = getJdbcDriverClassname();
			Class.forName(jdbcDriverClassname);
		}
		catch (final ClassNotFoundException e)
		{
			throw new RuntimeException("Cannot initialize postgresql database driver", e);
		}

		initialized = true;
	}

	@Override
	public final Connection getConnection(final String hostname, final String port, final String dbName, final String user, final String password) throws SQLException
	{
		init();

		final String passwordToUse = getPasswordToUse(hostname, port, dbName, user, password);
		final String dbType = getDbType();

		final StringBuilder url = new StringBuilder();
		url.append("jdbc:").append(dbType).append("://").append(hostname);

		if (port != null && port.trim().length() > 0)
		{
			url.append(":").append(port.trim());
		}

		url.append("/").append(dbName);

		return DriverManager.getConnection(url.toString(), user, passwordToUse);
	}

	protected String getPasswordToUse(final String hostname, final String port, final String dbName, final String user, final String password) throws SQLException
	{
		return password;
	}
}
