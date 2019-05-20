package de.metas.migration.impl;

/*
 * #%L
 * de.metas.migration.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IDatabase;
import de.metas.migration.applier.IScriptsApplier;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.applier.IScriptsProvider;
import de.metas.migration.applier.impl.ScriptsApplier;
import de.metas.migration.exception.ScriptException;
import de.metas.migration.executor.IScriptExecutorFactory;
import de.metas.migration.executor.impl.DefaultScriptExecutorFactory;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.scanner.impl.ScriptScannerFactory;
import de.metas.migration.scanner.impl.ScriptScannerProviderWrapper;

public abstract class AbstractScriptsApplierTemplate implements Runnable
{
	private Logger _logger = LoggerFactory.getLogger(getClass());

	protected abstract IScriptFactory createScriptFactory();

	protected abstract IScriptScanner createScriptScanner(final IScriptScannerFactory scriptScannerFactory);

	protected abstract void configureScriptExecutorFactory(final IScriptExecutorFactory scriptExecutorFactory);

	protected abstract IDatabase createDatabase();

	protected abstract IScriptsApplierListener createScriptsApplierListener();

	public void setLogger(final Logger logger)
	{
		_logger = logger;
	}

	public Logger getLogger()
	{
		if (_logger == null)
		{
			_logger = LoggerFactory.getLogger(getClass());
		}
		return _logger;
	}

	@Override
	public void run()
	{
		final Logger logger = getLogger();

		//
		// Setup script scanner factory
		final IScriptScannerFactory scriptScannerFactory = new ScriptScannerFactory();
		scriptScannerFactory.setScriptFactory(createScriptFactory());

		//
		// Setup script executor
		final IScriptExecutorFactory scriptExecutorFactory = createScriptExecutorFactory();
		configureScriptExecutorFactory(scriptExecutorFactory);

		//
		// Register Executor to scanner factory, all supported file types
		// NOTE: if there are no file types registered, script scanner will return nothing
		scriptScannerFactory.registerScriptScannerClassesFor(scriptExecutorFactory);

		//
		// Create script scanner
		final IScriptScanner scriptScanner = createScriptScanner(scriptScannerFactory);

		//
		// Convert scriptScanner to scriptProvider
		final IScriptsProvider scriptsProvider = new ScriptScannerProviderWrapper(scriptScanner);

		//
		// Setup Script Applier
		final IDatabase database = createDatabase();
		final IScriptsApplier scriptsApplier = createScriptApplier(database);
		scriptsApplier.setScriptExecutorFactory(scriptExecutorFactory);
		scriptsApplier.setListener(createScriptsApplierListener());

		//
		// Execute
		ScriptException error = null;
		try
		{
			scriptsApplier.apply(scriptsProvider);
		}
		catch (final ScriptException e)
		{
			error = e;

			// let it fail
			throw e;
		}
		finally
		{
			logger.info("Evaluated " + scriptsApplier.getCountAll() + " scripts");
			logger.info("Applied " + scriptsApplier.getCountApplied() + " scripts");
			logger.info("Ignored " + scriptsApplier.getCountIgnored() + " scripts");

			if (error != null)
			{
				logger.info("================================================================================================================");
				logger.info(error.getLocalizedMessage(), error);
			}
		}
	}

	protected IScriptExecutorFactory createScriptExecutorFactory()
	{
		return new DefaultScriptExecutorFactory();
	}

	/**
	 * Create and returns a new {@link ScriptsApplier} instance. Can be overridden in order to e.g. have no-op Scripts applier.
	 */
	protected ScriptsApplier createScriptApplier(final IDatabase database)
	{
		return new ScriptsApplier(database);
	}

}
