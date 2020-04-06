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

import java.util.HashMap;
import java.util.Map;

import de.metas.migration.sql.postgresql.PgSQLDatabaseDriver;

public class SQLDatabaseDriverFactory
{
	public static final SQLDatabaseDriverFactory instance = new SQLDatabaseDriverFactory();

	public static SQLDatabaseDriverFactory get()
	{
		return instance;
	}

	private final Map<String, ISQLDatabaseDriver> dbType2driver = new HashMap<String, ISQLDatabaseDriver>();

	private SQLDatabaseDriverFactory()
	{
		super();

		// Register defaults
		dbType2driver.put(PgSQLDatabaseDriver.DBTYPE, new PgSQLDatabaseDriver());
	}

	public ISQLDatabaseDriver getSQLDatabaseDriver(final String dbType)
	{
		if (dbType == null || dbType.trim().isEmpty())
		{
			throw new IllegalArgumentException("dbType shall not be empty");
		}

		final ISQLDatabaseDriver driver = dbType2driver.get(dbType);
		if (driver == null)
		{
			throw new IllegalStateException("No database driver was found for database type: " + dbType);
		}

		return driver;
	}
}
