/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.rabbitmq.listener;

import de.metas.Profiles;
import de.metas.adempiere.scheduler.SchedulerServiceImpl;
import de.metas.externalsystem.rabbitmq.request.ManageSchedulerRequest;
import de.metas.logging.LogManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Scheduler;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Payload;

import static de.metas.externalsystem.rabbitmq.ExternalSystemQueueConfig.QUEUE_ManageSchedulerEvents;

@Configuration
@EnableRabbit
@Profile(Profiles.PROFILE_App)
public class SchedulerEventsListener
{
	private static final Logger logger = LogManager.getLogger(SchedulerEventsListener.class);

	private final SchedulerServiceImpl schedulerService = SpringContextHolder.instance.getBean(SchedulerServiceImpl.class);

	@RabbitListener(queues = QUEUE_ManageSchedulerEvents)
	public void onMessage(@Payload final ManageSchedulerRequest request)
	{
		try
		{
			final I_AD_Scheduler adScheduler = schedulerService.getSchedulerByProcessIdIfUnique(request.getAdProcessId())
					.orElseThrow(() -> new AdempiereException("No scheduler found for process")
							.appendParametersToMessage()
							.setParameter("adProcessId", request.getAdProcessId()));

			if (request.getEnable())
			{
				schedulerService.enableScheduler(adScheduler);
			}
			else
			{
				schedulerService.disableScheduler(adScheduler);
			}
		}
		catch (final Exception ex)
		{
			logger.error("Failed processing: {}", request, ex);
		}
	}
}
