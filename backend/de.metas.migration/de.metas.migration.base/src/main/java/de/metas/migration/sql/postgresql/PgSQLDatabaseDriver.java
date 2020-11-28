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

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IDatabase;
import de.metas.migration.sql.GenericSQLDatabaseDriver;

public class PgSQLDatabaseDriver extends GenericSQLDatabaseDriver
{
	public static final String DBTYPE = "postgresql";

	private static final transient Logger logger = LoggerFactory.getLogger(PgSQLDatabaseDriver.class);

	private final Map<Object, String> configKey2password = new HashMap<Object, String>();

	public PgSQLDatabaseDriver()
	{
		super(DBTYPE, "org.postgresql.Driver");
	}

	@Override
	protected String getPasswordToUse(final String hostname, final String port, final String dbName, final String user, final String password) throws SQLException
	{
		String passwordToUse = password;

		final Object configKey = mkKey(hostname, port, dbName, user);

		//
		// Password not available: check cached configuration
		if (IDatabase.PASSWORD_NA == passwordToUse)
		{
			if (configKey2password.containsKey(configKey))
			{
				passwordToUse = configKey2password.get(configKey);
				if (passwordToUse == IDatabase.PASSWORD_NA)
				{
					throw new SQLException("No password was found for request: "
							+ "hostname=" + hostname
							+ ", port=" + port
							+ ", dbName=" + dbName
							+ ", user=" + user);
				}

				return passwordToUse;
			}
		}

		//
		// Password not available: check "pgpass" file if available
		if (IDatabase.PASSWORD_NA == passwordToUse)
		{
			final PgPassFile pgPassFile = PgPassFile.instance;
			passwordToUse = pgPassFile.getPassword(hostname, port, dbName, user);

			logger.info("Using config from " + pgPassFile.getConfigFile());
		}

		configKey2password.put(configKey, passwordToUse);

		if (IDatabase.PASSWORD_NA == passwordToUse)
		{
			throw new SQLException("No password was found for request: "
					+ "hostname=" + hostname
					+ ", port=" + port
					+ ", dbName=" + dbName
					+ ", user=" + user);
		}

		return passwordToUse;
	}

	private Object mkKey(final String hostname, final String port, final String dbName, final String user)
	{
		return Arrays.asList(hostname, port, dbName, user);
	}

}
