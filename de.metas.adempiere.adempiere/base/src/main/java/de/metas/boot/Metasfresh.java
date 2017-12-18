package de.metas.boot;

import java.util.Set;

import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

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

@Configuration(Metasfresh.BEANNAME)
public class Metasfresh
{
	public static final String BEANNAME = "metasfresh";

	public static final String DEPENDS_ON_SERVICES_INITIALIZED = MetasfreshPhase0Configuration.BEANNAME;
	public static final String DEPENDS_ON_DATABASE_INITIALIZED = MetasfreshDatabaseConfiguration.BEANNAME;
	public static final String DEPENDS_ON_MODEL_INTERCEPTORS_SYSTEM_INITIALIZED = MetasfreshModelInterceptorsConfiguration.BEANNAME;

	private static final Logger logger = LogManager.getLogger(Metasfresh.class);

	private final MetasfreshBootConfiguration metasfreshConfiguration;

	public Metasfresh(
			final MetasfreshBootConfiguration metasfreshConfiguration,
			final MetasfreshPhase0Configuration initialConfig,
			final MetasfreshDatabaseConfiguration dbConfig,
			final MetasfreshModelInterceptorsConfiguration modelInterceptorsConfig,
			final MetasfreshServerEnvironmentConfiguration serverEnvConfig)
	{
		this.metasfreshConfiguration = metasfreshConfiguration;
		init();
	}

	private void init()
	{
		final Set<String> tableNamesToIgnoreFromMigrationScriptsLogging = metasfreshConfiguration.getTableNamesToIgnoreFromMigrationScriptsLogging();
		if (!tableNamesToIgnoreFromMigrationScriptsLogging.isEmpty())
		{
			final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
			tableNamesToIgnoreFromMigrationScriptsLogging.forEach(migrationLogger::addTableToIgnoreList);
		}

		logger.info("Init done");
	}

	@Deprecated
	public static final void startupWithoutSpring(@NonNull final RunMode runMode)
	{
		final ApplicationContext applicationContext = null; // N/A

		final MetasfreshBootConfiguration metasfreshConfiguration = new MetasfreshBootConfiguration()
		{
			@Override
			public RunMode getRunMode()
			{
				return runMode;
			}
		};

		final MetasfreshPhase0Configuration initialConfig = new MetasfreshPhase0Configuration(applicationContext, metasfreshConfiguration);
		final MetasfreshDatabaseConfiguration dbConfig = new MetasfreshDatabaseConfiguration(initialConfig);
		final MetasfreshModelInterceptorsConfiguration modelInterceptorsConfig = new MetasfreshModelInterceptorsConfiguration(dbConfig);
		final MetasfreshServerEnvironmentConfiguration serverEnvConfig = new MetasfreshServerEnvironmentConfiguration(metasfreshConfiguration, modelInterceptorsConfig);

		new Metasfresh(metasfreshConfiguration, initialConfig, dbConfig, modelInterceptorsConfig, serverEnvConfig);
	}
}
