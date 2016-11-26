package de.metas.elasticsearch.impl;

import java.util.function.Consumer;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.elasticsearch.IESSystem;
import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;
import de.metas.elasticsearch.scheduler.IESModelIndexingScheduler;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.elasticsearch
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ESSystem implements IESSystem
{
	private static final Logger logger = LogManager.getLogger(ESSystem.class);

	@VisibleForTesting
	public static final String ESServer_Classname = "de.metas.elasticsearch.ESServer";

	@Override
	public boolean isEnabled()
	{
		if (Adempiere.isUnitTestMode())
		{
			return false;
		}

		return true;
	}

	private final void assertEnabled()
	{
		Check.assume(isEnabled(), "Elasticsearch system is enabled");
	}

	@Override
	public ESModelIndexerConfigBuilder newModelIndexerConfig(final String indexName, final Class<?> modelClass)
	{
		final Consumer<ESModelIndexerConfigBuilder> configInstaller = this::installConfig;
		final String modelTableName = InterfaceWrapperHelper.getTableName(modelClass);
		return new ESModelIndexerConfigBuilder(configInstaller, indexName, modelTableName);
	}

	private void installConfig(final ESModelIndexerConfigBuilder config)
	{
		assertEnabled();

		//
		// Add configuration on server too (if exists)
		final IESServer esServer = getESServer();
		if (esServer != null)
		{
			esServer.installConfig(config);
		}

		//
		// Install model indexer's triggers
		for (final IESModelIndexerTrigger trigger : config.getTriggers())
		{
			trigger.install();
			logger.info("Installed trigger: {}", trigger);
		}
	}

	private final Supplier<IESServer> serverSupplier = Suppliers.memoize(() -> findESServer());

	private IESServer getESServer()
	{
		return serverSupplier.get();
	}

	private final IESServer findESServer()
	{
		try
		{
			final IESServer esServer = (IESServer)Thread.currentThread()
					.getContextClassLoader()
					.loadClass(ESServer_Classname)
					.newInstance();
			
			logger.info("Found ESServer: {}", esServer);
			return esServer;
		}
		catch (final ClassNotFoundException e)
		{
			logger.info("ESServer class was not found: {}", ESServer_Classname);
			return null;
		}
		catch (final Exception e)
		{
			logger.warn("Failed instantiating ESServer for {}", ESServer_Classname, e);
			return null;
		}
	}

	@Override
	public IESModelIndexingScheduler scheduler()
	{
		return Services.get(IESModelIndexingScheduler.class);
	}
}
