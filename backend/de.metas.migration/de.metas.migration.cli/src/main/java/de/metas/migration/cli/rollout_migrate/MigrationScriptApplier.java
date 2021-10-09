package de.metas.migration.cli.rollout_migrate;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IDatabase;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.executor.IScriptExecutorFactory;
import de.metas.migration.impl.AbstractScriptsApplierTemplate;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.scanner.impl.CompositeScriptScanner;
import de.metas.migration.scanner.impl.GloballyOrderedScannerDecorator;
import lombok.AllArgsConstructor;
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
 * This class does the "main" thing of the tool, i.e. it runns the migration scripts.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@AllArgsConstructor
class MigrationScriptApplier
{
	private static final transient Logger logger = LoggerFactory.getLogger(MigrationScriptApplier.class);

	@NonNull
	private final DirectoryChecker directoryChecker;

	@NonNull
	final DBConnectionMaker dbconnectionMaker;

	public void applyMigrationScripts(
			@NonNull final RolloutMigrationConfig config,
			@NonNull final DBConnectionSettings dbConnectionSettings,
			@NonNull final String dbName)
	{
		logger.info("Just mark the script as executed: " + config.isJustMarkScriptAsExecuted());
		logger.info("Script file: " + config.getScriptFileName());

		final AbstractScriptsApplierTemplate scriptApplier = new AbstractScriptsApplierTemplate()
		{
			@Override
			protected IScriptFactory createScriptFactory()
			{
				return new RolloutDirScriptFactory();
			}

			@Override
			protected void configureScriptExecutorFactory(final IScriptExecutorFactory scriptExecutorFactory)
			{
				scriptExecutorFactory.setDryRunMode(config.isJustMarkScriptAsExecuted());
			}

			@Override
			protected IScriptScanner createScriptScanner(final IScriptScannerFactory scriptScannerFactory)
			{
				final CompositeScriptScanner scriptScanners = new CompositeScriptScanner();

				final File sqlDir = constructSqlDir(config.getRolloutDirName());
				if (config.getScriptFileName() != null && !config.getScriptFileName().isEmpty())
				{
					if (new File(config.getScriptFileName()).exists())
					{
						final String filename = config.getScriptFileName();
						final IScriptScanner scriptScanner = scriptScannerFactory.createScriptScannerByFilename(filename);
						scriptScanners.addScriptScanner(scriptScanner);
					}
					else
					{
						final String filename = sqlDir.getAbsolutePath() + File.separator + config.getScriptFileName();
						final IScriptScanner scriptScanner = scriptScannerFactory.createScriptScannerByFilename(filename);
						scriptScanners.addScriptScanner(scriptScanner);
					}
				}
				else
				{
					final String filename = sqlDir.getAbsolutePath();
					final IScriptScanner scriptScanner = scriptScannerFactory.createScriptScannerByFilename(filename);
					scriptScanners.addScriptScanner(scriptScanner);
				}

				//
				config.getAdditionalSqlDirs()
						.stream()
						.forEach(file -> {
							final IScriptScanner scriptScanner = scriptScannerFactory.createScriptScanner(file);
							if (scriptScanner == null)
							{
								throw new RuntimeException("Cannot create script scanner from " + file);
							}
							logger.info("Additional SQL source: {}", file);
							scriptScanners.addScriptScanner(scriptScanner);
						});

				return new GloballyOrderedScannerDecorator(scriptScanners);
			}

			@Override
			protected IScriptsApplierListener createScriptsApplierListener()
			{
				return config.getScriptsApplierListener();
			}

			@Override
			protected IDatabase createDatabase()
			{
				return dbconnectionMaker.createDb(dbConnectionSettings, dbName);
			}
		};
		scriptApplier.run();
	}

	private File constructSqlDir(final String rolloutDirName)
	{
		if (rolloutDirName == null || rolloutDirName.trim().isEmpty())
		{
			throw new IllegalArgumentException("Rollout directory not specified");
		}

		final File rolloutDir = directoryChecker.checkDirectory("Rollout directory", rolloutDirName);
		logger.info("Rollout directory: " + rolloutDir);

		final File sqlDir = directoryChecker.checkDirectory("SQL Directory", new File(rolloutDir, "sql"));
		logger.info("SQL directory: {}", sqlDir);

		return sqlDir;
	}
}
