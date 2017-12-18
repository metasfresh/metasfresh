package de.metas.boot;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.housekeeping.IHouseKeepingBL;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.I_AD_System;
import org.compiere.util.Env;
import org.compiere.util.SecureEngine;
import org.compiere.util.SecureInterface;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

import de.metas.logging.LogManager;

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

@Configuration
public class MetasfreshServerEnvironmentConfiguration
{
	private static final Logger logger = LogManager.getLogger(MetasfreshServerEnvironmentConfiguration.class);

	private final MetasfreshBootConfiguration config;
	private final RunMode runMode;

	/**
	 * 
	 * @param config
	 * @param modelInterceptorsConfig_NOTUSED this configurator shall be started after model interceptors were initialized because they might register {@link IStartupHouseKeepingTask}
	 */
	public MetasfreshServerEnvironmentConfiguration(
			final MetasfreshBootConfiguration config,
			final MetasfreshModelInterceptorsConfiguration modelInterceptorsConfig_NOTUSED)
	{
		this.config = config;
		runMode = config.getRunMode();

		init();
	}

	private void init()
	{
		if (runMode == RunMode.SWING_CLIENT)
		{
			return;
		}

		setupSecureEngine();
		cacheWarmUp();
		runHouseKeepingTasksIfNeeded();
		
		logger.info("Init done");
	}

	private void setupSecureEngine()
	{
		final I_AD_System system = Services.get(ISystemBL.class).get(Env.getCtx());	// Initializes Base Context too
		if (system == null)
		{
			throw new AdempiereException("No AD_System record found. Please make sure your database was correcty imported.");
		}

		try
		{
			String className = system.getEncryptionKey();
			if (className == null || className.length() == 0)
			{
				className = System.getProperty(SecureInterface.METASFRESH_SECURE);
				if (className != null && className.length() > 0
						&& !className.equals(SecureInterface.METASFRESH_SECURE_DEFAULT))
				{
					SecureEngine.init(className);	// test it
					system.setEncryptionKey(className);
					save(system);
				}
			}
			SecureEngine.init(className);
		}
		catch (Exception ex)
		{
			logger.warn("Failed initializing the SecureEngine. Ignored.", ex);
		}
	}

	private void cacheWarmUp()
	{
		try
		{
			if (runMode == RunMode.SWING_CLIENT)
			{
				// Login Client loaded later
				Services.get(IClientDAO.class).retriveClient(Env.getCtx(), IClientDAO.SYSTEM_CLIENT_ID);
			}
			else
			{
				// Load all clients (cache warm up)
				Services.get(IClientDAO.class).retrieveAllClients(Env.getCtx());
			}
		}
		catch (Exception ex)
		{
			logger.warn("Cache warm up failed. Ignored", ex);
		}
	}

	/**
	 * Execute house keeping tasks
	 * 
	 * IMPORTANT ASSUMPTION: by now the model validation engine has been initialized and therefore model validators had the chance to register their own housekeeping tasks.
	 */
	private void runHouseKeepingTasksIfNeeded()
	{
		// Only the metasfresh server shall run the housekeeping tasks
		if (runMode != RunMode.BACKEND)
		{
			return;
		}

		// Register the house keeping tasks provided in configuration, if any
		final IHouseKeepingBL houseKeepingBL = Services.get(IHouseKeepingBL.class);
		config.getStartupHouseKeepingTasks().forEach(houseKeepingBL::registerStartupHouseKeepingTask);

		houseKeepingBL.runStartupHouseKeepingTasks();
	}

}
