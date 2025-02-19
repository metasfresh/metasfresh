package org.adempiere.ad.migration.logger;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Optional;
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
	private static final Logger logger = LogManager.getLogger(MigrationScriptFileLoggerHolder.class);

	private static final AtomicBoolean logMigrationScripts = new AtomicBoolean(false);
	private static final ThreadLocal<MigrationScriptFileLogger> temporaryMigrationScriptWriterHolder = new ThreadLocal<>();
	private static final MigrationScriptFileLogger _pgMigrationScriptWriter = MigrationScriptFileLogger.newForPostgresql();
	public static final String DDL_PREFIX = "/* DDL */ ";

	public static void logMigrationScript(@Nullable final String sql)
	{
		if (sql == null
				|| isDisabled()
				|| Check.isBlank(sql))
		{
			return;
		}

		logMigrationScript(Sql.ofSql(sql));
	}

	public static void logMigrationScript(@Nullable final Sql sql)
	{
		if (sql == null || isDisabled())
		{
			return;
		}
		if (isSkipLogging(sql))
		{
			return;
		}

		getWriter().appendSqlStatement(sql);
	}

	public static void logMigrationScript(@NonNull final SqlBatch sqlBatch)
	{
		if (isDisabled())
		{
			return;
		}
		if (isSkipLogging(sqlBatch.getSqlCommand()))
		{
			return;
		}

		getWriter().appendSqlStatements(sqlBatch);
	}

	public static void logComment(@Nullable final String comment)
	{
		if (comment == null
				|| isDisabled()
				|| Check.isBlank(comment))
		{
			return;
		}

		getWriter().appendSqlStatement(Sql.ofComment(comment));
	}

	public static boolean isDisabled()
	{
		return !isEnabled();
	}

	public static boolean isEnabled()
	{
		return logMigrationScripts.get()
				|| temporaryMigrationScriptWriterHolder.get() != null;
	}

	public static void setEnabled(final boolean enabled)
	{
		logMigrationScripts.set(enabled);
	}

	public static IAutoCloseable temporaryEnabledLoggingToNewFileIf(final boolean condition)
	{
		return condition ? temporaryEnabledLoggingToNewFile() : IAutoCloseable.NOP;
	}

	public static IAutoCloseable temporaryEnabledLoggingToNewFile()
	{
		final MigrationScriptFileLogger migrationScriptFileLoggerNew = MigrationScriptFileLogger.newForPostgresql();
		migrationScriptFileLoggerNew.setWatcher(Loggables.getLoggableOrLogger(logger, Level.DEBUG));
		final MigrationScriptFileLogger migrationScriptFileLoggerOld = temporaryMigrationScriptWriterHolder.get();
		temporaryMigrationScriptWriterHolder.set(migrationScriptFileLoggerNew);
		return () -> {
			temporaryMigrationScriptWriterHolder.set(migrationScriptFileLoggerOld);
			migrationScriptFileLoggerNew.close();
		};
	}

	private MigrationScriptFileLogger getWriter()
	{
		final MigrationScriptFileLogger temporaryLogger = temporaryMigrationScriptWriterHolder.get();
		return temporaryLogger != null ? temporaryLogger : _pgMigrationScriptWriter;
	}

	public static Optional<Path> getCurrentScriptPathIfPresent()
	{
		return getWriter().getFilePathIfPresent();
	}

	@NonNull
	public static Path getCurrentScriptPath()
	{
		return getCurrentScriptPathIfPresent()
				.orElseThrow(() -> new AdempiereException("No current script file found"));
	}

	public static void closeMigrationScriptFiles()
	{
		getWriter().close();
	}

	public static void setMigrationScriptDirectory(@NonNull final Path path)
	{
		MigrationScriptFileLogger.setMigrationScriptDirectory(path);
	}

	public static Path getMigrationScriptDirectory()
	{
		return MigrationScriptFileLogger.getMigrationScriptDirectory();
	}

	private static boolean isSkipLogging(@NonNull final Sql sql)
	{
		return sql.isEmpty() || isSkipLogging(sql.getSqlCommand());
	}

	private static boolean isSkipLogging(@NonNull final String sqlCommand)
	{
		// Always log DDL (flagged) commands
		if (sqlCommand.startsWith(DDL_PREFIX))
		{
			return false;
		}

		final String sqlCommandUC = sqlCommand.toUpperCase().trim();

		//
		// Don't log selects
		if (sqlCommandUC.startsWith("SELECT "))
		{
			return true;
		}

		//
		// Don't log DELETE FROM Some_Table WHERE AD_Table_ID=? AND Record_ID=?
		if (sqlCommandUC.startsWith("DELETE FROM ") && sqlCommandUC.endsWith(" WHERE AD_TABLE_ID=? AND RECORD_ID=?"))
		{
			return true;
		}

		//
		// Check that INSERT/UPDATE/DELETE statements are about our ignored tables
		final ImmutableSet<String> exceptionTablesUC = Services.get(IMigrationLogger.class).getTablesToIgnoreUC(Env.getClientIdOrSystem());
		for (final String tableNameUC : exceptionTablesUC)
		{
			if (sqlCommandUC.startsWith("INSERT INTO " + tableNameUC + " "))
				return true;
			if (sqlCommandUC.startsWith("DELETE FROM " + tableNameUC + " "))
				return true;
			if (sqlCommandUC.startsWith("DELETE " + tableNameUC + " "))
				return true;
			if (sqlCommandUC.startsWith("UPDATE " + tableNameUC + " "))
				return true;
			if (sqlCommandUC.startsWith("INSERT INTO " + tableNameUC + "("))
				return true;
		}

		return false;
	}
}
