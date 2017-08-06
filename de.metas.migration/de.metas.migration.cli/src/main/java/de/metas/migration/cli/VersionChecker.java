package de.metas.migration.cli;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.jknack.semver.Semver;

import de.metas.migration.IDatabase;
import lombok.Builder;
import lombok.NonNull;

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
 * Compares the DB's version from {@code AD_Systemm.DBVersion} with the rollout package's version from {@code build-info.properties}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
public class VersionChecker
{
	final boolean doNotFailIfRolloutIsGreaterThanDB;

	@NonNull
	final IDatabase dbConnection;

	@NonNull
	final String rolloutVersionStr;

	/**
	 * 
	 * @return {@code true} if there is <b>nothing</b> to do.
	 */
	public boolean dbNeedsMigration()
	{
		final String dbVersionStr = retrieveDBVersion();
		final Semver dbVersion = Semver.create(dbVersionStr);

		final Semver rolloutVersion = Semver.create(rolloutVersionStr);

		final int comp = dbVersion.compareTo(rolloutVersion);
		if (comp == 0)
		{
			// the versions are equal. Nothing to do
			return false;
		}
		else if (comp < 0)
		{
			// dbVersion is lower than rolloutVersion
			// => we need to do the migration to "elevate" it
			return true;
		}

		// dbVersion higher....uh-ooh
		if (doNotFailIfRolloutIsGreaterThanDB)
		{
			// let's ignore the problem
			return false;
		}

		throw new RuntimeException("The code has version " + rolloutVersionStr + " but the DB already has version " + dbVersionStr);
	}

	public String retrieveDBVersion()
	{
		final String selectSql = "SELECT DBVersion FROM public.AD_System";
		try (final Statement stmt = dbConnection.getConnection().createStatement())
		{
			final ResultSet resultSet = stmt.executeQuery(selectSql);
			resultSet.next();
			final String dbVersion = resultSet.getString(1);
			return dbVersion;
		}
		catch (SQLException e)
		{
			// fail
			throw new RuntimeException("Could not retrieve the DB version; selectSql=" + selectSql, e);
		}
	}
}
