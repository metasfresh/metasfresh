package de.metas.bpartner.product.stats;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.metas.Profiles;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
@Profile(Profiles.PROFILE_App)
public class BPartnerProductStatsEventListener implements IEventListener
{
	private static final Logger logger = LogManager.getLogger(BPartnerProductStatsEventListener.class);

	private final IEventBusFactory eventBusRegistry;
	private final BPartnerProductStatsService statsService;

	public BPartnerProductStatsEventListener(
			@NonNull final IEventBusFactory eventBusRegistry,
			@NonNull final BPartnerProductStatsService statsService)
	{
		this.eventBusRegistry = eventBusRegistry;
		this.statsService = statsService;
	}

	@PostConstruct
	public void registerListener()
	{
		eventBusRegistry.registerGlobalEventListener(BPartnerProductStatsEventSender.TOPIC_InOut, this);
		eventBusRegistry.registerGlobalEventListener(BPartnerProductStatsEventSender.TOPIC_Invoice, this);
	}

	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		final String topicName = eventBus.getTopicName();
		if (BPartnerProductStatsEventSender.TOPIC_InOut.getName().equals(topicName))
		{
			statsService.handleInOutChangedEvent(BPartnerProductStatsEventSender.extractInOutChangedEvent(event));
		}
		else if (BPartnerProductStatsEventSender.TOPIC_Invoice.getName().equals(topicName))
		{
			statsService.handleInvoiceChangedEvent(BPartnerProductStatsEventSender.extractInvoiceChangedEvent(event));
		}
		else
		{
			logger.warn("Ignore unknow event {} got on topic={}", event, topicName);
		}
	}
}
