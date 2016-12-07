package org.adempiere.process;

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

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.migration.executor.IMigrationExecutor;
import org.adempiere.ad.migration.executor.IMigrationExecutorContext;
import org.adempiere.ad.migration.executor.IMigrationExecutorProvider;
import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.xml.XMLLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;

public class MigrationLoader
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	public void load(Properties ctx)
	{
		scanClasspath();
		scanMetasfreshHome();
	}

	private void scanClasspath()
	{
		final String resourceName = "/org/adempiere/migration/Migration.xml";
		final InputStream inputStream = getClass().getResourceAsStream(resourceName);
		if (inputStream == null)
		{
			logger.info("Resource name not found: " + resourceName + ". Skip migration.");
			return;
		}

		final XMLLoader loader = new XMLLoader(inputStream);
		load(loader);

	}

	private void scanMetasfreshHome()
	{
		final File home = new File(Adempiere.getMetasfreshHome() + File.separator + "migration");
		if (!home.exists() && !home.isDirectory())
		{
			logger.warn("No migration directory found (" + home + ")");
			return;
		}

		logger.info("Processing migration files in directory: " + home.getAbsolutePath());

		final File[] migrationFiles = home.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(final File dir, final String name)
			{
				return name.endsWith(".xml");
			}
		});

		for (final File migrationFile : migrationFiles)
		{
			final XMLLoader loader = new XMLLoader(migrationFile.getAbsolutePath());
			load(loader);
		}
	}

	private void load(final XMLLoader loader)
	{
		loader.load(ITrx.TRXNAME_None);

		for (Object o : loader.getObjects())
		{
			if (o instanceof I_AD_Migration)
			{
				final I_AD_Migration migration = (I_AD_Migration)o;
				execute(migration);
			}
			else
			{
				logger.warn("Unhandled type " + o + " [SKIP]");
			}
		}
	}

	private void execute(I_AD_Migration migration)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(migration);
		final int migrationId = migration.getAD_Migration_ID();

		final IMigrationExecutorProvider executorProvider = Services.get(IMigrationExecutorProvider.class);
		final IMigrationExecutorContext migrationCtx = executorProvider.createInitialContext(ctx);
		migrationCtx.setFailOnFirstError(true);

		final IMigrationExecutor executor = executorProvider.newMigrationExecutor(migrationCtx, migrationId);
		executor.setCommitLevel(IMigrationExecutor.CommitLevel.Batch);
		executor.execute(IMigrationExecutor.Action.Apply);
	}
}
