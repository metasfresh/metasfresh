package de.metas.ui.web.view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class ViewConfiguration implements InitializingBean
{
	private static final Logger logger = LogManager.getLogger(ViewConfiguration.class);

	private static final String BEANNAME_ViewMaintenanceScheduledExecutorService = "viewMaintenanceScheduledExecutorService";

	@Value("${metasfresh.view.clearViewSelectionsRateInSeconds:1800}")
	private int clearViewSelectionsRateInSeconds;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (clearViewSelectionsRateInSeconds > 0)
		{
			final ScheduledExecutorService scheduledExecutor = viewMaintenanceScheduledExecutorService();
			scheduledExecutor.scheduleAtFixedRate(
					SqlViewSelectionToDeleteHelper::deleteScheduledSelectionsNoFail, // command, don't fail because on failure the task won't be re-scheduled so it's game over
					clearViewSelectionsRateInSeconds, // initialDelay
					clearViewSelectionsRateInSeconds, // period
					TimeUnit.SECONDS // timeUnit
			);
			logger.info("Clearing view selections each {} seconds", clearViewSelectionsRateInSeconds);
		}
	}

	@Bean(BEANNAME_ViewMaintenanceScheduledExecutorService)
	public ScheduledExecutorService viewMaintenanceScheduledExecutorService()
	{
		return Executors.newScheduledThreadPool(
				1, // corePoolSize
				CustomizableThreadFactory.builder()
						.setDaemon(true)
						.setThreadNamePrefix("webui-views-maintenance")
						.build());
	}

}
