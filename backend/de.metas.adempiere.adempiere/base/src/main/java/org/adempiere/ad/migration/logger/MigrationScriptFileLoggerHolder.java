package org.adempiere.ad.migration.logger;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

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
	private static final AtomicBoolean logMigrationScripts = new AtomicBoolean(false);
	private static final ThreadLocal<Boolean> logMigrationScriptsTemporaryEnabled = new ThreadLocal<>();
	private static final MigrationScriptFileLogger pgMigrationScriptWriter = MigrationScriptFileLogger.of("postgresql");
	public static final String DDL_PREFIX = "/* DDL */ ";

	public static void logMigrationScript(@Nullable final String sql)
	{
		if (sql == null
				|| isDisabled()
				|| Check.isBlank(sql))
		{
			return;
		}

		if (dontLog(sql))
		{
			return;
		}

		pgMigrationScriptWriter.appendSqlStatement(Sql.ofSql(sql));
	}

	public static void logComment(@Nullable final String comment)
	{
		if (comment == null
				|| isDisabled()
				|| Check.isBlank(comment))
		{
			return;
		}

		pgMigrationScriptWriter.appendSqlStatement(Sql.ofComment(comment));
	}

	public static boolean isDisabled()
	{
		return !isEnabled();
	}

	public static boolean isEnabled()
	{
		final Boolean temporaryEnabled = logMigrationScriptsTemporaryEnabled.get();
		return (temporaryEnabled != null && temporaryEnabled)
				|| logMigrationScripts.get();
	}

	public static void setEnabled(final boolean enabled)
	{
		logMigrationScripts.set(enabled);
	}

	public static IAutoCloseable temporaryEnableMigrationScriptsLoggingIf(final boolean condition)
	{
		return condition ? temporaryEnableMigrationScriptsLogging() : IAutoCloseable.NOP;
	}

	public static IAutoCloseable temporaryEnableMigrationScriptsLogging()
	{
		final Boolean logMigrationScriptsTemporaryEnabled_Backup = logMigrationScriptsTemporaryEnabled.get();
		logMigrationScriptsTemporaryEnabled.set(Boolean.TRUE);
		return () -> logMigrationScriptsTemporaryEnabled.set(logMigrationScriptsTemporaryEnabled_Backup);
	}

	@Nullable
	public static Path getCurrentScriptPathOrNull()
	{
		return pgMigrationScriptWriter.getFilePathOrNull();
	}

	public static void closeMigrationScriptFiles()
	{
		pgMigrationScriptWriter.close();
	}

	public static void setMigrationScriptDirectory(@NonNull final Path path)
	{
		MigrationScriptFileLogger.setMigrationScriptDirectory(path);
	}

	public static Path getMigrationScriptDirectory()
	{
		return MigrationScriptFileLogger.getMigrationScriptDirectory();
	}

	private static boolean dontLog(@NonNull final String statement)
	{
		// Always log DDL (flagged) commands
		if (statement.startsWith(DDL_PREFIX))
		{
			return false;
		}

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
		final ImmutableSet<String> exceptionTablesUC = Services.get(IMigrationLogger.class).getTablesToIgnoreUC(Env.getClientIdOrSystem());
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
