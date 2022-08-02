package de.metas.material.event.eventbus;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventConfiguration;
import de.metas.material.event.MaterialEventObserver;
import lombok.NonNull;
import org.slf4j.Logger;

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

public final class MetasfreshEventBusService
{
	private static final Logger logger = LogManager.getLogger(MetasfreshEventBusService.class);

	private final Topic eventBusTopic;

	private final MaterialEventConverter materialEventConverter;

	private final IEventBusFactory eventBusFactory;

	private final MaterialEventObserver materialEventObserver;

	public static MetasfreshEventBusService createLocalServiceThatIsReadyToUse(
			@NonNull final MaterialEventConverter materialEventConverter,
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final MaterialEventObserver materialEventObserver)
	{
		logger.info("Creating MaterialEventBusService for local-only event dispatching");
		return new MetasfreshEventBusService(Type.LOCAL, materialEventConverter, eventBusFactory, materialEventObserver);
	}

	/**
	 * Also see {@link #subscribeToEventBus()}.
	 *
	 * @return
	 */
	public static MetasfreshEventBusService createDistributedServiceThatNeedsToSubscribe(
			@NonNull final MaterialEventConverter materialEventConverter,
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final MaterialEventObserver materialEventObserver)
	{
		logger.info("Creating MaterialEventBusService for distributed event dispatching");
		return new MetasfreshEventBusService(Type.DISTRIBUTED, materialEventConverter, eventBusFactory, materialEventObserver);
	}

	/**
	 * Can be called to create a local-only event service which will not try to set up or connect to a message broker. Useful for testing.
	 * <p>
	 * This constructor is supposed to be invoked by {@link MaterialEventConfiguration}.
	 */
	private MetasfreshEventBusService(
			@NonNull final Type eventType,
			@NonNull final MaterialEventConverter materialEventConverter,
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final MaterialEventObserver materialEventObserver)
	{
		this.eventBusTopic = Topic.builder()
				.name("de.metas.material")
				.type(eventType)
				.build();

		this.materialEventConverter = materialEventConverter;
		this.eventBusFactory = eventBusFactory;
		this.materialEventObserver = materialEventObserver;
	}

	private IEventBus getEventBus()
	{
		return eventBusFactory.getEventBus(eventBusTopic);
	}

	public void enqueueEvent(@NonNull final MaterialEvent event)
	{
		materialEventObserver.reportEventEnqueued(event);

		final Event realEvent = materialEventConverter.fromMaterialEvent(event);
		getEventBus().enqueueEvent(realEvent);
	}

	public void subscribe(@NonNull final IEventListener internalListener)
	{
		getEventBus().subscribe(internalListener);
	}
}
