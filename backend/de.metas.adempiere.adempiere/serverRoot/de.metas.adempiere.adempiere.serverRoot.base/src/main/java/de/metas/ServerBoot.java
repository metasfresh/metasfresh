package de.metas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import de.metas.CommandLineParser.CommandLineOptions;
import de.metas.dao.selection.QuerySelectionToDeleteHelper;
import de.metas.dao.selection.model.I_T_Query_Selection;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.ConnectionUtil;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Getter;
import org.adempiere.ad.housekeeping.HouseKeepingService;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Ini.IfMissingMetasfreshProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

	private static final String SYSCONFIG_PREFIX_APP_SPRING_PROFILES_ACTIVE = "de.metas.spring.profiles.active";

	@Autowired
	private ApplicationContext applicationContext;

	@Getter
	private CommandLineOptions commandLineOptions;

	@Value("${metasfresh.query.clearQuerySelectionRateInSeconds:60}")
	private int clearQuerySelectionsRateInSeconds;

	public static void main(final String[] args)
	{
		logger.info("Begin of {} main-method ", ServerBoot.class);

		final Stopwatch stopwatch = Stopwatch.createStarted();

		logger.info("Parse command line arguments (if any!)");
		final CommandLineOptions commandLineOptions = CommandLineParser.parse(args);

		final ConnectionUtil.ConfigureConnectionsResult configureConnectionsResult = ConnectionUtil.configureConnectionsIfArgsProvided(commandLineOptions);

		try (final IAutoCloseable ignore = ModelValidationEngine.postponeInit())
		{
			// important because in Ini, there is a org.springframework.context.annotation.Condition that otherwise wouldn't e.g. let the jasper servlet start
			Ini.setRunMode(RunMode.BACKEND);
			Ini.setIfMissingMetasfreshProperties(configureConnectionsResult.isCconnectionConfigured() ? IfMissingMetasfreshProperties.IGNORE : IfMissingMetasfreshProperties.SHOW_DIALOG);

			Adempiere.instance.startup(RunMode.BACKEND);

			final ArrayList<String> activeProfiles = retrieveActiveProfilesFromSysConfig();
			activeProfiles.add(Profiles.PROFILE_App);
			activeProfiles.add(Profiles.PROFILE_ReportService);
			activeProfiles.add(Profiles.PROFILE_PrintService);
			activeProfiles.add(Profiles.PROFILE_AccountingService);

			final String headless = System.getProperty(SYSTEM_PROPERTY_HEADLESS, Boolean.toString(true));
			new SpringApplicationBuilder(ServerBoot.class)
					.headless(StringUtils.toBoolean(headless)) // we need headless=false for initial connection setup popup (if any), usually this only applies on dev workstations.
					.web(WebApplicationType.SERVLET)
					// consider removing the jasper profile
					// if we did that, then to also have jasper within the backend, we would start it with -Dspring.profiles.active=metasfresh-jasper-server
					.profiles(activeProfiles.toArray(new String[0]))
					.beanNameGenerator(new MetasfreshBeanNameGenerator())
					.run(args);
		}
		SpringContextHolder.instance.getBean(ServerBoot.class).commandLineOptions = commandLineOptions;

		// now init the model validation engine
		ModelValidationEngine.get();

		//
		final HouseKeepingService houseKeepingService = SpringContextHolder.instance.getBean(HouseKeepingService.class);
		houseKeepingService.runStartupHouseKeepingTasks();

		logger.info("Metasfresh Server started in {}", stopwatch);
		logger.info("End of {} main-method ", ServerBoot.class);
	}

	private static ArrayList<String> retrieveActiveProfilesFromSysConfig()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		return new ArrayList<>(sysConfigBL
				.getValuesForPrefix(SYSCONFIG_PREFIX_APP_SPRING_PROFILES_ACTIVE, ClientAndOrgId.SYSTEM)
				.values());
	}

	@Bean
	@Primary
	public ObjectMapper jsonObjectMapper()
	{
		return JsonObjectMapperHolder.sharedJsonObjectMapper();
	}

	@Bean(Adempiere.BEAN_NAME)
	public Adempiere adempiere()
	{
		// when this is done, Adempiere.getBean(...) is ready to use
		return Env.getSingleAdempiereInstance(applicationContext);
	}

	@Override
	public void afterPropertiesSet()
	{
		if (clearQuerySelectionsRateInSeconds > 0)
		{
			final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(
					1, // corePoolSize
					CustomizableThreadFactory.builder()
							.setDaemon(true)
							.setThreadNamePrefix("cleanup-" + I_T_Query_Selection.Table_Name)
							.build());

			// note: "If any execution of this task takes longer than its period, then subsequent executions may start late, but will not concurrently execute."
			scheduledExecutor.scheduleAtFixedRate(
					QuerySelectionToDeleteHelper::deleteScheduledSelectionsNoFail, // command, don't fail because on failure the task won't be re-scheduled so it's game over
					clearQuerySelectionsRateInSeconds, // initialDelay
					clearQuerySelectionsRateInSeconds, // period
					TimeUnit.SECONDS // timeUnit
			);
			logger.info("Clearing query selection tables each {} seconds", clearQuerySelectionsRateInSeconds);
		}
	}
}
