package de.metas.boot;

import javax.annotation.Nullable;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.service.impl.DeveloperModeBL;
import org.adempiere.context.ContextProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.processing.service.IProcessingService;
import org.adempiere.processing.service.impl.ProcessingService;
import org.adempiere.util.Check;
import org.adempiere.util.DefaultServiceNamePolicy;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.adempiere.warehouse.spi.impl.WarehouseAdvisor;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Login;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.addon.IAddonStarter;
import de.metas.adempiere.addon.impl.AddonStarter;
import de.metas.adempiere.util.cache.CacheInterceptor;
import de.metas.logging.LogManager;
import lombok.NonNull;

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

@Configuration(MetasfreshPhase0Configuration.BEANNAME)
public class MetasfreshPhase0Configuration
{
	static final String BEANNAME = "metasfreshPhase0Configuration";
	private static final Logger logger = LogManager.getLogger(MetasfreshPhase0Configuration.class);

	// accept to be nullable in case we are starting it outside of Spring context (like legacy tools etc)
	private final ApplicationContext applicationContext;
	private final MetasfreshBootConfiguration metasfreshConfiguration;

	public MetasfreshPhase0Configuration(
			@Nullable final ApplicationContext applicationContext,
			@NonNull MetasfreshBootConfiguration metasfreshConfiguration)
	{
		this.applicationContext = applicationContext;
		this.metasfreshConfiguration = metasfreshConfiguration;

		init();
	}

	private RunMode getRunMode()
	{
		return metasfreshConfiguration.getRunMode();
	}

	private void init()
	{
		// First thing, set the applicationContext to Adempiere instance
		Adempiere.get().setApplicationContext(applicationContext);

		final RunMode runMode = getRunMode();
		final boolean runmodeClient = runMode == RunMode.SWING_CLIENT;

		Ini.setRunMode(runMode);
		Check.setDefaultExClass(AdempiereException.class);

		setupContextProvider();
		setupServices();

		LogManager.initialize(runmodeClient);
		logger.info("Starting {}", Adempiere.getSummaryAscii()); // greeting

		metasfreshConfiguration.getInterfaceWrapperHelpers().forEach(InterfaceWrapperHelper::registerHelper);

		startAddOnsIfNeeded();

		// Check Version
		if (runmodeClient && !Login.isJavaOK(runmodeClient))
		{
			System.exit(1);
		}

		// System properties
		Ini.loadProperties();

		//
		// Update logging configuration from Ini file (applies only if we are running the swing client)
		if (runmodeClient)
		{
			LogManager.updateConfigurationFromIni();
		}
		else
		{
			LogManager.setLevel(Level.WARN);
		}

		// Set UI
		if (runmodeClient)
		{
			AdempierePLAF.setPLAF(); // metas: load plaf from last session
		}

		logger.info("Init done");
	}

	private void setupContextProvider()
	{
		final ContextProvider contextProvider = metasfreshConfiguration.createContextProvider();
		Check.assumeNotNull(contextProvider, "contextProvider is not null");
		Env.setContextProvider(contextProvider);
		
		Env.initContextProvider();
	}

	private void setupServices()
	{
		Services.setServiceNameAutoDetectPolicy(new DefaultServiceNamePolicy());
		Services.getInterceptor().registerInterceptor(Cached.class, new CacheInterceptor()); // task 06952

		// NOTE: this method is called more then one, which leads to multiple Services instances.
		// If those services contains internal variables (for state) we will have different service implementations with different states
		// which will lead us to weird behavior
		// So, instead of manually instantiating and registering here the services, it's much more safer to use AutodetectServices.
		Services.setAutodetectServices(true);
		Services.registerService(IDeveloperModeBL.class, DeveloperModeBL.instance); // we need this during AIT
		Services.registerService(IProcessingService.class, ProcessingService.get());
		Services.registerService(IWarehouseAdvisor.class, new WarehouseAdvisor());
	}

	private void startAddOnsIfNeeded()
	{
		final RunMode runMode = getRunMode();
		if (runMode != RunMode.SWING_CLIENT)
		{
			return;
		}

		final IAddonStarter addonStarter = new AddonStarter();
		addonStarter.startAddons();
	}

}
