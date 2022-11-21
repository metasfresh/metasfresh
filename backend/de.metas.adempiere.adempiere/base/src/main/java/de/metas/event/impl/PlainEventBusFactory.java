package de.metas.event.impl;

import com.google.common.collect.ImmutableList;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogsRepository;
import de.metas.monitoring.adapter.MicrometerPerformanceMonitoringService;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.compiere.Adempiere;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Empty dummy factory for unit testing.
 */
public class PlainEventBusFactory implements IEventBusFactory
{
	public static PlainEventBusFactory newInstance()
	{
		return new PlainEventBusFactory();
	}

	private final HashMap<Topic, EventBus> eventBuses = new HashMap<>();

	public PlainEventBusFactory()
	{
		assertJUnitTestMode();
	}

	private static void assertJUnitTestMode()
	{
		if (!Adempiere.isUnitTestMode())
		{
			throw new IllegalStateException(PlainEventBusFactory.class.getName() + " shall be used only in JUnit test mode");
		}
	}

	@Override
	public IEventBus getEventBus(final Topic topic)
	{
		assertJUnitTestMode();
		return eventBuses.computeIfAbsent(topic, this::createEventBus);
	}

	private EventBus createEventBus(final Topic topic)
	{
		final MicrometerEventBusStatsCollector micrometerEventBusStatsCollector = EventBusFactory.createMicrometerEventBusStatsCollector(topic, new SimpleMeterRegistry());
		final EventBusMonitoringService eventBusMonitoringService = new EventBusMonitoringService(new MicrometerPerformanceMonitoringService(new SimpleMeterRegistry()));

		final ExecutorService executor = null;
		return new EventBus(topic, executor, micrometerEventBusStatsCollector, new PlainEventEnqueuer(), eventBusMonitoringService, new EventLogService(new EventLogsRepository()));
	}

	@Override
	public IEventBus getEventBusIfExists(final Topic topic)
	{
		assertJUnitTestMode();
		return eventBuses.get(topic);
	}

	@Override
	public List<IEventBus> getAllEventBusInstances()
	{
		assertJUnitTestMode();
		return ImmutableList.copyOf(eventBuses.values());
	}

	@Override
	public void initEventBussesWithGlobalListeners()
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void destroyAllEventBusses()
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void registerGlobalEventListener(final Topic topic, final IEventListener listener)
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void addAvailableUserNotificationsTopic(final Topic topic)
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void registerUserNotificationsListener(final IEventListener listener)
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void unregisterUserNotificationsListener(IEventListener listener)
	{
		assertJUnitTestMode();
	}

	@Override
	public boolean checkRemoteEndpointStatus()
	{
		assertJUnitTestMode();
		return false;
	}
}
