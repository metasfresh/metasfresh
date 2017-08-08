package de.metas.migration.cli;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IDatabase;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.executor.IScriptExecutorFactory;
import de.metas.migration.impl.AbstractScriptsApplierTemplate;
import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.scanner.impl.FileRef;
import de.metas.migration.scanner.impl.GloballyOrderedScannerDecorator;
import lombok.Builder;
import lombok.Builder.Default;
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
@Builder
public class MigrationScriptApplier
{
	private static final transient Logger logger = LoggerFactory.getLogger(MigrationScriptApplier.class);

	@NonNull
	private final IDatabase db;

	@NonNull
	private final IScriptsApplierListener listener;

	@Default
	private final boolean justMarkScriptAsExecuted = false;

	@NonNull
	private final String rolloutDirName;
	
	@Default
	private final String scriptFileName = null;
	
	@NonNull
	private DirectoryChecker directoryChecker;
	

	
	public void applyMigrationScripts()
	{
		logger.info("Just mark the script as executed: " + this.justMarkScriptAsExecuted);
		logger.info("Script file: " + this.scriptFileName);

		final File sqlDir = constructSqlDir();
		
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
				scriptExecutorFactory.setDryRunMode(justMarkScriptAsExecuted);
			}

			@Override
			protected IScriptScanner createScriptScanner(final IScriptScannerFactory scriptScannerFactory)
			{
				final String fileName;
				if (scriptFileName != null && !scriptFileName.isEmpty())
				{
					if (new File(scriptFileName).exists())
					{
						fileName = scriptFileName;
					}
					else
					{
						fileName = sqlDir.getAbsolutePath() + File.separator + scriptFileName;
					}
				}
				else
				{
					fileName = sqlDir.getAbsolutePath();
				}

				final IFileRef fileRef = new FileRef(new File(fileName));
				final IScriptScanner scriptScanner = scriptScannerFactory.createScriptScanner(fileRef);

				final IScriptScanner result = new GloballyOrderedScannerDecorator(scriptScanner);
				return result;
			}

			@Override
			protected IScriptsApplierListener createScriptsApplierListener()
			{
				return listener;
			}

			@Override
			protected IDatabase createDatabase()
			{
				return db;
			}
		};
		scriptApplier.run();
	}

	private File constructSqlDir()
	{
		if (rolloutDirName == null || rolloutDirName.trim().isEmpty())
		{
			throw new IllegalArgumentException("Rollout directory not specified");
		}

		File rolloutDir = directoryChecker.checkDirectory("Rollout directory", rolloutDirName);
		logger.info("Rollout directory: " + rolloutDir);

		File sqlDir = directoryChecker.checkDirectory("SQL Directory", new File(rolloutDir, "sql"));
		logger.info("SQL directory: " + sqlDir);

		return sqlDir;
	}
}
