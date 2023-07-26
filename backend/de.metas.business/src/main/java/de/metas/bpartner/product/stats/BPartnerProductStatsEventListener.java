package de.metas.bpartner.product.stats;

import de.metas.Profiles;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.locks.ReentrantLock;

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

	/**
	 * Introduce this lock to get rid of
	 * <pre>
	 *     ERROR: duplicate key value violates unique constraint "c_bpartner_product_stats_uq"
	 * </pre>
	 * ..I suspect that the reason for the problem is that this listener is invoked concurrently, e.g. from async processors
	 */
	private final ReentrantLock statsRepoAccessLock = new ReentrantLock();

	private final IEventBusFactory eventBusRegistry;
	private final BPartnerProductStatsEventHandler statsEventHandler;

	public BPartnerProductStatsEventListener(
			@NonNull final IEventBusFactory eventBusRegistry,
			@NonNull final BPartnerProductStatsEventHandler statsEventHandler)
	{
		this.eventBusRegistry = eventBusRegistry;
		this.statsEventHandler = statsEventHandler;
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
		statsRepoAccessLock.lock();
		try
		{
			if (BPartnerProductStatsEventSender.TOPIC_InOut.getName().equals(topicName))
			{
				statsEventHandler.handleInOutChangedEvent(BPartnerProductStatsEventSender.extractInOutChangedEvent(event));
			}
			else if (BPartnerProductStatsEventSender.TOPIC_Invoice.getName().equals(topicName))
			{
				statsEventHandler.handleInvoiceChangedEvent(BPartnerProductStatsEventSender.extractInvoiceChangedEvent(event));
			}
			else
			{
				logger.warn("Ignore unknown event {} got on topic={}", event, topicName);
			}
		}
		finally
		{
			statsRepoAccessLock.unlock();
		}
	}
}
