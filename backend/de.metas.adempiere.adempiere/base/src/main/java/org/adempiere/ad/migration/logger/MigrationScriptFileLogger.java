package org.adempiere.ad.migration.logger;

import com.google.common.base.MoreObjects;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.spi.TrxOnCommitCollectorFactory;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_MigrationScript;
import org.compiere.model.MSequence;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * SQL migration script file logger.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MigrationScriptFileLogger
{
	public static MigrationScriptFileLogger of(final String dbType)
	{
		return new MigrationScriptFileLogger(dbType);
	}

	private static final Logger logger = LogManager.getLogger(MigrationScriptFileLogger.class);

	private static final Charset CHARSET = StandardCharsets.UTF_8;
	private static final DateTimeFormatter FORMATTER_ScriptFilenameTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final String dbType;
	@Nullable private Path _path;
	@Nullable private static Path _migrationScriptsDirectory;

	private static final String COLLECTOR_TRXPROPERTYNAME = MigrationScriptFileLogger.class.getName() + ".collectorFactory";
	private final TrxOnCommitCollectorFactory<StringBuilder, Sql> collectorFactory = new TrxOnCommitCollectorFactory<StringBuilder, Sql>()
	{

		@Override
		protected String getTrxProperyName()
		{
			return COLLECTOR_TRXPROPERTYNAME;
		}

		@Override
		protected String extractTrxNameFromItem(final Sql sqlStatement)
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		protected StringBuilder newCollector(final Sql sqlStatement)
		{
			return new StringBuilder();
		}

		@Override
		protected void collectItem(final StringBuilder collector, final Sql sqlStatement)
		{
			if (sqlStatement != null)
			{
				collector.append(sqlStatement.toSql());
			}
		}

		@Override
		protected void processCollector(final StringBuilder collector)
		{
			final String sqlStatements = collector.toString();
			appendNow(sqlStatements);
		}

		@Override
		protected void discardCollector(final StringBuilder collector)
		{
			final String sqlStatements = collector.toString();
			if (sqlStatements.isEmpty())
			{
				return;
			}

			System.out.println("\n");
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("Discarding following SQL statements, due to transaction rollback:\n");
			System.out.println(sqlStatements);
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("\n");
			System.out.flush();
		}
	};

	private MigrationScriptFileLogger(@NonNull final String dbType)
	{
		this.dbType = dbType;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("dbType", dbType)
				.add("path", _path)
				.toString();
	}

	@Override
	protected void finalize() throws Throwable
	{
		close();
	}

	private static Path createPath(@NonNull final String dbType)
	{
		final int adClientId = 0;
		final int scriptId = MSequence.getNextID(adClientId, I_AD_MigrationScript.Table_Name) * 10;

		final String filename = scriptId + "_migration_" + FORMATTER_ScriptFilenameTimestamp.format(LocalDate.now()) + "_" + dbType + ".sql";

		return getMigrationScriptDirectory().resolve(filename);
	}

	private synchronized Path getCreateFilePath()
	{
		if (_path == null || !Files.exists(_path))
		{
			final Path path = createPath(dbType);
			System.out.println("Using scripts path: " + path);

			//
			// Make sure the directories exist
			try
			{
				Files.createDirectories(path.getParent());
			}
			catch (final IOException ex)
			{
				throw AdempiereException.wrapIfNeeded(ex);
			}

			_path = path;
		}
		return _path;
	}

	@Nullable
	public final synchronized Path getFilePathOrNull()
	{
		return _path;
	}

	public static Path getMigrationScriptDirectory()
	{
		final Path migrationScriptsDirectory = _migrationScriptsDirectory;
		return migrationScriptsDirectory != null ? migrationScriptsDirectory : getDefaultMigrationScriptDirectory();
	}

	private static Path getDefaultMigrationScriptDirectory()
	{
		return Paths.get(Ini.getMetasfreshHome(), "migration_scripts");
	}

	public static void setMigrationScriptDirectory(@NonNull final Path path)
	{
		_migrationScriptsDirectory = path;
		logger.info("Set migration scripts directory: {}", path);
	}

	/**
	 * Appends given SQL statement.
	 * <p>
	 * If this method is called within a database transaction then the SQL statement will not be written to file directly,
	 * but it will be collected and written when the transaction is committed.
	 */
	public synchronized void appendSqlStatement(@NonNull final Sql sqlStatement)
	{
		collectorFactory.collect(sqlStatement);
	}

	private synchronized void appendNow(final String sqlStatements)
	{
		if (sqlStatements == null || sqlStatements.isEmpty())
		{
			return;
		}

		try
		{
			final Path path = getCreateFilePath();
			final byte[] bytes = sqlStatements.getBytes(CHARSET);
			Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		}
		catch (final IOException ex)
		{
			logger.error("Failed writting to {}:\n {}", this, sqlStatements, ex);
			close(); // better to close the file ... so, next time a new one will be created
		}
	}

	/**
	 * Close underling file.
	 * <p>
	 * Next time, {@link #appendSqlStatement(Sql)} will be called, a new file will be created, so it's safe to call this method as many times as needed.
	 */
	public synchronized void close()
	{
		System.out.println("Closed migration script: " + _path);
		_path = null;
	}
}
