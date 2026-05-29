/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.event.impl;

import de.metas.event.Event;
import de.metas.event.Topic;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ISysConfigBL;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class EventBusMonitoringService
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final PerformanceMonitoringService perfMonService;

	public boolean isMonitorIncomingEvents()
	{
		return sysConfigBL.getBooleanValue("de.metas.event.MonitorIncomingEvents", false);
	}

	public void addInfosAndMonitorSpan(
			@NonNull final Event event,
			@NonNull final Topic topic,
			@NonNull final Consumer<Event> enqueueEvent)
	{
		final Event.Builder eventToSendBuilder = event.toBuilder();
		final PerformanceMonitoringService.Metadata request = PerformanceMonitoringService.Metadata.builder()
				.type(de.metas.monitoring.adapter.PerformanceMonitoringService.Type.EVENTBUS_REMOTE_ENDPOINT)
				.className("EventBus")
				.functionName("enqueueEvent")
				.label("de.metas.event.distributed-event.senderId", event.getSenderId())
				.label("de.metas.event.distributed-event.topicName", topic.getName())
				.build();

		perfMonService.monitor(
				() -> enqueueEvent.accept(eventToSendBuilder.build()),
				request);
	}

	public void extractInfosAndMonitor(
			@NonNull final Event event,
			@NonNull final Topic topic,
			@NonNull final Runnable processEvent)
	{
		final PerformanceMonitoringService.Metadata metadata = PerformanceMonitoringService.Metadata.builder()
				.className("RabbitMQEventBusRemoteEndpoint")
				.functionName("onEvent")
				.type(de.metas.monitoring.adapter.PerformanceMonitoringService.Type.EVENTBUS_REMOTE_ENDPOINT)
				.label("de.metas.event.remote-event.senderId", event.getSenderId())
				.label("de.metas.event.remote-event.topicName", topic.getName())
				.build();

		perfMonService.monitor(
				processEvent,
				metadata);
	}
}
