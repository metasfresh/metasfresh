package de.metas.ui.web.session;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-webui-api
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
@EnableSpringHttpSession
@EnableConfigurationProperties(SessionProperties.class)
public class SessionConfig
{
	private static final Logger logger = LogManager.getLogger(SessionConfig.class);

	private static final String BEANNAME_SessionScheduledExecutorService = "sessionScheduledExecutorService";

	@Value("${metasfresh.session.checkExpiredSessionsRateInMinutes:10}")
	private int checkExpiredSessionsRateInMinutes;

	@Bean
	public SessionRepository<ExpiringSession> sessionRepository(
			final SessionProperties properties,
			final ApplicationEventPublisher applicationEventPublisher)
	{
		final FixedMapSessionRepository sessionRepository = FixedMapSessionRepository.builder()
				.applicationEventPublisher(applicationEventPublisher)
				.defaultMaxInactiveInterval(properties.getTimeout())
				.build();
		logger.info("Using session repository: {}", sessionRepository);

		if (checkExpiredSessionsRateInMinutes > 0)
		{
			final ScheduledExecutorService scheduledExecutor = sessionScheduledExecutorService();
			scheduledExecutor.scheduleAtFixedRate(
					sessionRepository::purgeExpiredSessionsNoFail, // command, don't fail because on failure the task won't be re-scheduled so it's game over
					checkExpiredSessionsRateInMinutes, // initialDelay
					checkExpiredSessionsRateInMinutes, // period
					TimeUnit.MINUTES // timeUnit
			);
			logger.info("Checking expired sessions each {} minutes", checkExpiredSessionsRateInMinutes);
		}

		return sessionRepository;
	}

	@Bean(BEANNAME_SessionScheduledExecutorService)
	public ScheduledExecutorService sessionScheduledExecutorService()
	{
		return Executors.newScheduledThreadPool(
				1, // corePoolSize
				CustomizableThreadFactory.builder()
						.setDaemon(true)
						.setThreadNamePrefix(SessionConfig.class.getName())
						.build());
	}
}
