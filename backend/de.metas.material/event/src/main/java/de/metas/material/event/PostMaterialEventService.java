package de.metas.material.event;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;

/*
 * #%L
 * metasfresh-material-event
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

@Service
public class PostMaterialEventService
{
	private static final Logger logger = LogManager.getLogger(PostMaterialEventService.class);

	private static final String TRX_PROPERTY_EventsToEnqueueAfterCommit = PostMaterialEventService.class.getName() + ".EventsToEnqueueAfterCommit";

	private final MetasfreshEventBusService materialEventService;

	public PostMaterialEventService(@NonNull final MetasfreshEventBusService materialEventService)
	{
		this.materialEventService = materialEventService;
	}

	/**
	 * Makes sure the given {@code event} will be fired via {@link #enqueueEventsNow(Collection)} after the current trx is committed.
	 * <p>
	 * All events of one trx are collected in one list, so they are sent together, in the order they were posted, and over one RabbitMQ channel.
	 * This way the consumer gets them in that order.
	 * If there is no trx, the event is fired right away.
	 */
	public void enqueueEventAfterNextCommit(@NonNull final MaterialEvent event)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		trxManager.accumulateAndProcessAfterCommit(TRX_PROPERTY_EventsToEnqueueAfterCommit,
				ImmutableList.of(event),
				this::enqueueEventsNow);
	}

	/**
	 * Fires the given event using our (distributed) event framework.
	 * <b>Important:</b> Please make sure to only use this method if you know that all the data which this event refers to is already committed to database!
	 */
	public void enqueueEventNow(final MaterialEvent event)
	{
		materialEventService.enqueueEvent(event);
		logger.debug("Posted MaterialEvent={}", event);
	}

	/**
	 * Fires the given events in the given order, over one RabbitMQ channel, so that the consumer gets them in that order.
	 * <b>Important:</b> same as {@link #enqueueEventNow(MaterialEvent)}, all the data which these events refer to must already be committed!
	 */
	public void enqueueEventsNow(@NonNull final Collection<MaterialEvent> events)
	{
		materialEventService.enqueueEvents(events);
		logger.debug("Posted MaterialEvents={}", events);
	}
}
