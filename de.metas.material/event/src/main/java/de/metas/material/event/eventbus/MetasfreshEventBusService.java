package de.metas.material.event.eventbus;

import org.adempiere.util.Services;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventConfiguration;
import de.metas.material.event.impl.MaterialEventBus;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-manufacturing-event-api
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
@Service(MaterialEventConfiguration.BEAN_NAME)
public final class MetasfreshEventBusService
{
	private static final Logger logger = LogManager.getLogger(MetasfreshEventBusService.class);

	private final Topic eventBusTopic;

	public static MetasfreshEventBusService createLocalServiceThatIsReadyToUse()
	{
		logger.info("Creating MaterialEventBusService for local-only event dispatching");
		return new MetasfreshEventBusService(Type.LOCAL);
	}

	/**
	 * Also see {@link #subscribeToEventBus()}.
	 *
	 * @return
	 */
	public static MetasfreshEventBusService createDistributedServiceThatNeedsToSubscribe()
	{
		logger.info("Creating MaterialEventBusService for distributed event dispatching");
		return new MetasfreshEventBusService(Type.REMOTE);
	}

	/**
	 * Can be called to create a local-only event service which will not try to set up or connect to a message broker. Useful for testing.
	 * <p>
	 * This constructor is supposed to be invoked by {@link MaterialEventConfiguration}.
	 *
	 * @param eventType
	 */
	private MetasfreshEventBusService(@NonNull final Type eventType)
	{
		eventBusTopic = Topic.builder()
				.setName(MaterialEventBus.EVENTBUS_TOPIC_NAME)
				.setType(eventType)
				.build();
	}

	public IEventBus getEventBus()
	{
		final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);

		final IEventBus eventBus = eventBusFactory.getEventBus(eventBusTopic);
		return eventBus;
	}

	public void postEvent(@NonNull final MaterialEvent event)
	{
		final Event realEvent = MaterialEventConverter.fromMaterialEvent(event);
		getEventBus().postEvent(realEvent);
	}

	public void subscribe(@NonNull final IEventListener internalListener)
	{
		getEventBus().subscribe(internalListener);
	}
}
