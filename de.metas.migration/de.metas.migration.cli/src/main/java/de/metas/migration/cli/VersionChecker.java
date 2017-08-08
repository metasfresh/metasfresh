package de.metas.migration.cli;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.zafarkhaja.semver.Version;

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

	private static final String DB_VERSION_INITIAL_VALUE = "0.0.0-initial-value";
	private static final String CREATE_DB_VERSION_COLUMN_DDL = "ALTER TABLE public.AD_System ADD COLUMN DBVersion VARCHAR(50) DEFAULT '" + DB_VERSION_INITIAL_VALUE + "'";

	private static final String SELECT_DB_VERSION_SQL = "SELECT DBVersion FROM public.AD_System";

	private static final transient Logger logger = LoggerFactory.getLogger(VersionChecker.class);

	final boolean failIfRolloutIsGreaterThanDB;

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
		final Version dbVersion = Version.valueOf(dbVersionStr);

		logger.info("AD_System.DBVersion={}", dbVersion);

		final Version rolloutVersion = Version.valueOf(rolloutVersionStr);
		logger.info("Our own version={}", rolloutVersion);

		final int comp = dbVersion.compareTo(rolloutVersion);
		if (comp == 0)
		{
			logger.info("AD_System.DBVersion is equal to our version. Nothing to do.");
			return false;
		}
		else if (comp < 0)
		{
			// dbVersion is lower than rolloutVersion
			// => we need to do the migration to "elevate" it
			logger.info("The DB version is lower than our own version. Going to migrate");
			return true;
		}

		// dbVersion higher....uh-ooh
		final String msg = "The code has version " + rolloutVersionStr + " but the DB already has version " + dbVersionStr;

		if (!failIfRolloutIsGreaterThanDB)
		{
			// let's ignore the problem
			logger.info(msg + ". Going on as set via command line parameter; but not going to attempt migration either.");
			return false;
		}

		throw new InconsistentVersionsException(msg);
	}

	public String retrieveDBVersion()
	{
		try (final Connection connection = dbConnection.getConnection();
				final Statement stmt = connection.createStatement())
		{
			return retrieveDBVersion0(stmt);
		}
		catch (final SQLException e)
		{
			
			logger.info("Could not retrieve the DB version");
			if ("42703".equals(e.getSQLState())) // 42703 => undefined_column, see https://www.postgresql.org/docs/9.5/static/errcodes-appendix.html
			{
				// we are a migration tool, so it might well be that the column is not yet there
				logger.info("Trying to create the DBVersion column now");
				try (final Connection connection = dbConnection.getConnection();
						final Statement stmt = connection.createStatement())
				{
					stmt.execute(CREATE_DB_VERSION_COLUMN_DDL);
					logger.info("Created the column with the initial value {}", DB_VERSION_INITIAL_VALUE);
					return DB_VERSION_INITIAL_VALUE;
				}
				catch (SQLException e1)
				{
					throw new CantRetrieveDBVersionException("Could not create the missing column AD_System.DBVersion; Sql=" + CREATE_DB_VERSION_COLUMN_DDL, e);
				}
			}
			// fail
			throw new CantRetrieveDBVersionException("Could not retrieve the DB version; selectSql=" + SELECT_DB_VERSION_SQL, e);
		}
	}

	private String retrieveDBVersion0(final Statement stmt) throws SQLException
	{
		final ResultSet resultSet = stmt.executeQuery(SELECT_DB_VERSION_SQL);
		resultSet.next();
		final String dbVersion = resultSet.getString(1);

		return dbVersion;
	}

	public static final class InconsistentVersionsException extends RuntimeException
	{
		private static final long serialVersionUID = -5089487300354591676L;

		private InconsistentVersionsException(final String msg)
		{
			super(msg);
		}
	}

	public static final class CantRetrieveDBVersionException extends RuntimeException
	{
		private static final long serialVersionUID = -5089487300354591676L;

		private CantRetrieveDBVersionException(final String msg, final Exception e)
		{
			super(msg, e);
		}
	}

}
