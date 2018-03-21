package de.metas;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.adempiere.ad.dao.impl.QuerySelectionToDeleteHelper;
import org.adempiere.ad.dao.model.I_T_Query_Selection;
import org.adempiere.ad.housekeeping.IHouseKeepingBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.logging.LogManager;
import de.metas.server.housekeep.MissingTranslationHouseKeepingTask;
import de.metas.server.housekeep.RoleAccessUpdateHouseKeepingTask;
import de.metas.server.housekeep.SequenceCheckHouseKeepingTask;
import de.metas.server.housekeep.SignDatabaseBuildHouseKeepingTask;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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
 * metasfresh server boot.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@SpringBootApplication(scanBasePackages = { "de.metas", "org.adempiere" })
@ServletComponentScan(value = { "de.metas", "org.adempiere" })
@Profile(Profiles.PROFILE_App)
public class ServerBoot implements InitializingBean
{
	/**
	 * By default, we run in headless mode. But using this system property, we can also run with headless=false.
	 * The only known use of that is that metasfresh can open the initial license & connection dialog to store the initial properties file.
	 */
	public static final String SYSTEM_PROPERTY_HEADLESS = "app-server-run-headless";

	private static final Logger logger = LogManager.getLogger(ServerBoot.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${metasfresh.query.clearQuerySelectionRateInSeconds:60}")
	private int clearQuerySelectionsRateInSeconds;

	public static void main(final String[] args)
	{
		// important because in Ini, there is a org.springframework.context.annotation.Condition that userwise wouldn't e.g. let the jasper servlet start
		Ini.setRunMode(RunMode.BACKEND);

		final String headless = System.getProperty(SYSTEM_PROPERTY_HEADLESS, Boolean.toString(true));

		new SpringApplicationBuilder(ServerBoot.class)
				.headless(StringUtils.toBoolean(headless)) // we need headless=false for initial connection setup popup (if any), usually this only applies on dev workstations.
				.web(true)
				// consider removing the jasper profile
				// if we did that, then to also have jasper within the backend, we would start it with -Dspring.profiles.active=metasfresh-jasper-server
				// same goes for PrintService
				.profiles(
						Profiles.PROFILE_App,
						Profiles.PROFILE_JasperService,
						Profiles.PROFILE_PrintService)
				.run(args);
	}

	@Configuration
	public static class StaticResourceConfiguration extends WebMvcConfigurerAdapter
	{
		private static final Logger LOG = LogManager.getLogger(StaticResourceConfiguration.class);

		@Value("${metasfresh.serverRoot.downloads:}")
		private String downloadsPath;

		@Override
		public void addResourceHandlers(final ResourceHandlerRegistry registry)
		{
			if (Check.isEmpty(downloadsPath, true))
			{
				downloadsPath = defaultDownloadsPath();
			}

			// Make sure the path ends with separator
			// see https://jira.spring.io/browse/SPR-14063
			if (downloadsPath != null && !downloadsPath.endsWith(File.separator))
			{
				downloadsPath += File.separator;
			}

			if (!Check.isEmpty(downloadsPath, true))
			{
				LOG.info("Serving static content from " + downloadsPath);
				registry.addResourceHandler("/download/**").addResourceLocations("file:" + downloadsPath);

				// the "binaries" download path is about to be removed soon!
				registry.addResourceHandler("/binaries/**").addResourceLocations("file:" + downloadsPath);
			}
		}

		private String defaultDownloadsPath()
		{
			try
			{
				final File cwd = new File(".").getCanonicalFile();
				final File downloadsFile = new File(cwd, "download");
				return downloadsFile.getCanonicalPath();
			}
			catch (final IOException e)
			{
				LOG.warn("Failed finding the default downloads path", e);
				return null;
			}
		}
	}

	@Bean(Adempiere.BEAN_NAME)
	public Adempiere adempiere()
	{
		final IHouseKeepingBL houseKeepingRegistry = Services.get(IHouseKeepingBL.class);
		houseKeepingRegistry.registerStartupHouseKeepingTask(new SignDatabaseBuildHouseKeepingTask());
		houseKeepingRegistry.registerStartupHouseKeepingTask(new SequenceCheckHouseKeepingTask());
		houseKeepingRegistry.registerStartupHouseKeepingTask(new RoleAccessUpdateHouseKeepingTask());
		houseKeepingRegistry.registerStartupHouseKeepingTask(new MissingTranslationHouseKeepingTask());

		final Adempiere adempiere = Env.getSingleAdempiereInstance(applicationContext);
		adempiere.startup(RunMode.BACKEND);
		return adempiere;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (clearQuerySelectionsRateInSeconds > 0)
		{
			final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(
					1, // corePoolSize
					CustomizableThreadFactory.builder()
							.setDaemon(true)
							.setThreadNamePrefix("cleanup-" + I_T_Query_Selection.Table_Name)
							.build());

			scheduledExecutor.scheduleAtFixedRate(
					QuerySelectionToDeleteHelper::deleteScheduledSelectionsNoFail, // command, don't fail because on failure the task won't be re-scheduled so it's game over
					clearQuerySelectionsRateInSeconds, // initialDelay
					clearQuerySelectionsRateInSeconds, // period
					TimeUnit.SECONDS // timeUnit
			);
			logger.info("Clearing query selection table each {} seconds", clearQuerySelectionsRateInSeconds);
		}
	}

	@Bean
	public ObjectMapper objectMapper()
	{
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		return objectMapper;
	}
}
