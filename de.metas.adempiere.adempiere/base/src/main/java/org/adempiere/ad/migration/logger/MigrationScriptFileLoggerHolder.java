package org.adempiere.ad.migration.logger;

import java.util.Set;

import org.adempiere.util.Services;
import org.compiere.util.Ini;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@UtilityClass
public class MigrationScriptFileLoggerHolder
{
	private static final MigrationScriptFileLogger pgMigrationScriptWriter = MigrationScriptFileLogger.of("postgresql");
	public static final String DDL_PREFIX = "/* DDL */ ";

	public static void logMigrationScript(final String sql)
	{
		if(sql == null)
		{
			return;
		}
		
		// Check AdempiereSys
		// check property Log migration script
		final boolean logMigrationScript = Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT);
		if (!logMigrationScript)
		{
			return;
		}

		if (dontLog(sql))
		{
			return;
		}

		pgMigrationScriptWriter.appendSqlStatement(sql);
	}

	public static final void closeMigrationScriptFiles()
	{
		pgMigrationScriptWriter.close();
	}

	private static boolean dontLog(String statement)
	{
		// Always log DDL (flagged) commands
		if (statement.startsWith(DDL_PREFIX))
		{
			return false;
		}

		// metas: teo_sarca: end
		final String uppStmt = statement.toUpperCase().trim();

		//
		// Don't log selects
		if (uppStmt.startsWith("SELECT "))
		{
			return true;
		}

		//
		// Don't log DELETE FROM Some_Table WHERE AD_Table_ID=? AND Record_ID=?
		if (uppStmt.startsWith("DELETE FROM ") && uppStmt.endsWith(" WHERE AD_TABLE_ID=? AND RECORD_ID=?"))
		{
			return true;
		}

		//
		// Check that INSERT/UPDATE/DELETE statements are about our ignored tables
		final Set<String> exceptionTablesUC = Services.get(IMigrationLogger.class).getTablesToIgnoreUC();
		for (final String tableNameUC : exceptionTablesUC)
		{
			if (uppStmt.startsWith("INSERT INTO " + tableNameUC + " "))
				return true;
			if (uppStmt.startsWith("DELETE FROM " + tableNameUC + " "))
				return true;
			if (uppStmt.startsWith("DELETE " + tableNameUC + " "))
				return true;
			if (uppStmt.startsWith("UPDATE " + tableNameUC + " "))
				return true;
			if (uppStmt.startsWith("INSERT INTO " + tableNameUC + "("))
				return true;
		}

		return false;
	}
}
