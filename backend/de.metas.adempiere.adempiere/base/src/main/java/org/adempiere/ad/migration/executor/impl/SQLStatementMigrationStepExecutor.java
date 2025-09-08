package org.adempiere.ad.migration.executor.impl;

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


import java.sql.SQLException;
import java.sql.Statement;

import org.adempiere.ad.migration.executor.IMigrationExecutorContext;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.db.CConnection;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import de.metas.util.Check;

public class SQLStatementMigrationStepExecutor extends AbstractMigrationStepExecutor
{
	public SQLStatementMigrationStepExecutor(final IMigrationExecutorContext migrationCtx, final I_AD_MigrationStep step)
	{
		super(migrationCtx, step);
	}

	public ExecutionResult apply(final String trxName)
	{
		final boolean rollback = false;
		return execute(trxName, rollback);
	}

	public ExecutionResult rollback(final String trxName)
	{
		final boolean rollback = false;
		return execute(trxName, rollback);
	}

	private ExecutionResult execute(final String trxName, final boolean rollback)
	{
		final I_AD_MigrationStep step = getAD_MigrationStep();

		//
		// Check if script is suitable for our database
		final String stepDBType = step.getDBType();
		if (!stepDBType.equals(X_AD_MigrationStep.DBTYPE_AllDatabaseTypes)
				&& !(DB.isPostgreSQL() && X_AD_MigrationStep.DBTYPE_Postgres.equals(stepDBType)))
		{
			final String dbType = CConnection.get().getType();
			step.setStatusCode(null);
			log("Not suitable for current database type (Step:" + stepDBType + ", Database:" + dbType + ")", "SKIP", false);
			return ExecutionResult.Skipped;
		}

		//
		// Fetch SQL Statement
		String sql = rollback ? step.getRollbackStatement() : step.getSQLStatement();
		if (Check.isEmpty(sql, true) || sql.trim().equals(";"))
		{
			log("No " + (rollback ? "Rollback" : "SQL") + " found", "SKIP", true);
			if (rollback)
			{
				return ExecutionResult.Executed;
			}
			else
			{
				return ExecutionResult.Skipped;
			}
		}

		//
		// Fix/Prepare SQL
		sql = sql.trim();
		if (sql.endsWith(";"))
		{
			sql = sql.substring(0, sql.length() - 1);
		}

		//
		// Execute SQL Statement
		Statement stmt = null;
		try
		{
			if (X_AD_MigrationStep.DBTYPE_AllDatabaseTypes.equals(step.getDBType()))
			{
				// Execute script using our conversion layer
				DB.executeUpdateAndThrowExceptionOnFail(sql, trxName);
			}
			else
			{
				// Execute script without using the conversion layer because we expect to have it in native SQL
				stmt = Trx.get(trxName, false).getConnection().createStatement();
				stmt.execute(sql);
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(stmt);
			stmt = null;
		}

		log(null, rollback ? "Rollback" : "Applied", false);
		return ExecutionResult.Executed;
	}
}
