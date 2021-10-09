package de.metas.migration.cli.rollout_migrate;

import java.util.HashMap;
import java.util.Properties;

import com.google.common.annotations.VisibleForTesting;

import de.metas.migration.IDatabase;
import lombok.AllArgsConstructor;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * This class wraps the settings {@link Properties} and provides getters for the different parameters.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@AllArgsConstructor
public class DBConnectionSettingProperties
{
	private final Properties properties ;

	@VisibleForTesting
	static final String PROP_DB_NAME = "METASFRESH_DB_NAME";

	@VisibleForTesting
	static final String PROP_DB_PASSWORD = "METASFRESH_DB_PASSWORD";

	@VisibleForTesting
	static final String PROP_DB_USER = "METASFRESH_DB_USER";

	@VisibleForTesting
	static final String PROP_DB_PORT = "METASFRESH_DB_PORT";

	@VisibleForTesting
	static final String PROP_DB_SERVER = "METASFRESH_DB_SERVER";

	@VisibleForTesting
	static final String PROP_DB_TYPE = "METASFRESH_DB_TYPE";

	private String getProperty(final String propertyName, final String defaultValue)
	{
		if (properties == null)
		{
			throw new IllegalStateException("Settings were not configured");
		}

		return properties.getProperty(propertyName, defaultValue);
	}

	public String getDbName()
	{
		return getProperty(PROP_DB_NAME, "metasfresh");
	}

	public String getDbUser()
	{
		return getProperty(PROP_DB_USER, "metasfresh");
	}

	public String getDbType()
	{
		return getProperty(PROP_DB_TYPE, "postgresql");
	}

	public String getDbHostname()
	{
		return getProperty(PROP_DB_SERVER, "localhost");
	}

	public String getDbPort()
	{
		return getProperty(PROP_DB_PORT, "5432");
	}

	public String getDbPassword()
	{
		return getProperty(PROP_DB_PASSWORD,
				// Default value is null because in case is not configured we shall use other auth methods
				IDatabase.PASSWORD_NA);
	}

	@Override
	public String toString()
	{
		final HashMap<Object, Object> result = new HashMap<>(properties);
		result.put(PROP_DB_PASSWORD, "******");
		return result.toString();
	}

	public DBConnectionSettings toDBConnectionSettings()
	{
		return DBConnectionSettings.builder()
				.dbHostname(getDbHostname())
				.dbPort(getDbPort())
				.dbName(getDbName())
				.dbUser(getDbUser())
				.dbPassword(getDbPassword())
				.build();
	}
}
