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
import lombok.NonNull;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class EventBusMonitoringService
{
	private static final String PROP_TRACE_INFO_PREFIX = "traceInfo.";

	@NonNull
	private final PerformanceMonitoringService perfMonService;

	public EventBusMonitoringService(final @NonNull PerformanceMonitoringService perfMonService)
	{
		this.perfMonService = perfMonService;
	}

	public void addInfosAndMonitorSpan(
			@NonNull final Event event,
			@NonNull final Topic topic,
			@NonNull final Consumer<Event> enqueueEvent)
	{
		final Event.Builder eventToSendBuilder = event.toBuilder();
		final PerformanceMonitoringService.SpanMetadata request = PerformanceMonitoringService.SpanMetadata.builder()
				.type(de.metas.monitoring.adapter.PerformanceMonitoringService.Type.EVENTBUS_REMOTE_ENDPOINT.getCode())
				.subType(PerformanceMonitoringService.SubType.EVENT_SEND.getCode())
				.name("Enqueue distributed-event on topic " + topic.getName())
				.label("de.metas.event.distributed-event.senderId", event.getSenderId())
				.label("de.metas.event.distributed-event.topicName", topic.getName())
				// allow perfMonService to inject properties into the event which enable distributed tracing
				.distributedHeadersInjector((name, value) -> eventToSendBuilder.putProperty(PROP_TRACE_INFO_PREFIX + name, value))
				.build();

		perfMonService.monitorSpan(
				() -> enqueueEvent.accept(eventToSendBuilder.build()),
				request);
	}

	public void extractInfosAndMonitor(
			@NonNull final Event event,
			@NonNull final Topic topic,
			@NonNull final Runnable processEvent)
	{
		// extract remote tracing infos from the event (if there are any) and create a (distributed) monitoring transaction.

		final PerformanceMonitoringService.TransactionMetadata.TransactionMetadataBuilder transactionMetadata = PerformanceMonitoringService.TransactionMetadata.builder();
		for (final Map.Entry<String, Object> entry : event.getProperties().entrySet())
		{
			final String key = entry.getKey();
			if (key.startsWith(PROP_TRACE_INFO_PREFIX))
			{
				transactionMetadata.distributedTransactionHeader(
						key.substring(PROP_TRACE_INFO_PREFIX.length()),
						Objects.toString(entry.getValue()));
			}
		}
		transactionMetadata
				.name("Process remote-event; topic=" + topic.getName())
				.type(de.metas.monitoring.adapter.PerformanceMonitoringService.Type.EVENTBUS_REMOTE_ENDPOINT)
				.label("de.metas.event.remote-event.senderId", event.getSenderId())
				.label("de.metas.event.remote-event.topicName", topic.getName())
				.label("de.metas.event.remote-event.endpointImpl", this.getClass().getSimpleName());

		perfMonService.monitorTransaction(
				processEvent,
				transactionMetadata.build());
	}
}
