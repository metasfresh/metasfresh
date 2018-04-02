package de.metas.vertical.pharma.msv3.server.peer.metasfresh;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
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
@EnableAsync(mode = AdviceMode.PROXY, proxyTargetClass = true)
public class MSV3ServerPeerMetasfreshConfiguration
{
	public static final String ENTITY_TYPE = "de.metas.vertical.pharma.msv3.server";

	public static final String EVENTS_PUBLISHER_TASKEXECUTOR_BEAN_NAME = "eventsPublisherTaskExecutor";
	@Bean(EVENTS_PUBLISHER_TASKEXECUTOR_BEAN_NAME)
	public TaskExecutor eventsPublisherTaskExecutor()
	{
		return new ThreadPoolTaskExecutor();
	}
}
