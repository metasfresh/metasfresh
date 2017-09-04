package de.metas.migration.cli;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IDatabase;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

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
@AllArgsConstructor
@ToString
public class DBVersionGetter
{
	private static final transient Logger logger = LoggerFactory.getLogger(DBVersionGetter.class);

	@NonNull
	final DBConnectionMaker dbConnectionMaker;

	private static final String DB_VERSION_INITIAL_VALUE = "0.0.0-initial-value";
	private static final String CREATE_DB_VERSION_COLUMN_DDL = "ALTER TABLE public.AD_System ADD COLUMN DBVersion VARCHAR(50) DEFAULT '" + DB_VERSION_INITIAL_VALUE + "'";

	private static final String SELECT_DB_VERSION_SQL = "SELECT DBVersion FROM public.AD_System";

	public String retrieveDBVersion(
			@NonNull final Settings settings,
			@NonNull final String dbName)
	{
		final IDatabase dbConnection = dbConnectionMaker.createDummyDatabase(settings, dbName);

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
				catch (final SQLException e1)
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

	public static final class CantRetrieveDBVersionException extends RuntimeException
	{
		private static final long serialVersionUID = -5089487300354591676L;

		private CantRetrieveDBVersionException(final String msg, final Exception e)
		{
			super(msg, e);
		}
	}
}
