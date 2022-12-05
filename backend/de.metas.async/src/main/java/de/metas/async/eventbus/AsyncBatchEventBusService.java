/*
 * #%L
 * de.metas.async
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

package de.metas.async.eventbus;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.log.EventLogUserService;
import de.metas.event.remote.RabbitMQEventBusConfiguration;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class AsyncBatchEventBusService
{
	private static final Topic TOPIC = RabbitMQEventBusConfiguration.AsyncBatchQueueConfiguration.EVENTBUS_TOPIC;
	private static final String PROPERTY_AsyncBatchNotifyRequest = "AsyncBatchNotifyRequest";

	// services
	private static final Logger logger = LogManager.getLogger(AsyncBatchEventBusService.class);
	private final IEventBusFactory eventBusFactory;
	private final EventLogUserService eventLogUserService;

	public AsyncBatchEventBusService(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final EventLogUserService eventLogUserService)
	{
		this.eventBusFactory = eventBusFactory;
		this.eventLogUserService = eventLogUserService;
	}

	@Autowired
	public void registerHandlers(@NonNull final Optional<List<AsyncBatchNotifyRequestHandler>> handlers)
	{
		handlers.ifPresent(manageAsyncBatchRequestHandlers -> manageAsyncBatchRequestHandlers.forEach(this::registerHandler));
	}

	public void postRequest(@NonNull final AsyncBatchNotifyRequest request)
	{
		final Event event = createEventFromRequest(request);

		getEventBus().enqueueEvent(event);
	}

	@NonNull
	private IEventBus getEventBus()
	{
		return eventBusFactory.getEventBus(TOPIC);
	}

	@NonNull
	private static Event createEventFromRequest(@NonNull final AsyncBatchNotifyRequest request)
	{
		final String requestStr;
		try
		{
			requestStr = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(request);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Exception serializing AsyncBatchNotifyRequest", e)
					.appendParametersToMessage()
					.setParameter("AsyncBatchNotifyRequest", request);
		}

		return Event.builder()
				.putProperty(PROPERTY_AsyncBatchNotifyRequest, requestStr)
				.shallBeLogged()
				.build();
	}

	@NonNull
	private static AsyncBatchNotifyRequest extractAsyncBatchNotifyRequest(@NonNull final Event event)
	{
		final String requestStr = event.getProperty(PROPERTY_AsyncBatchNotifyRequest);

		final AsyncBatchNotifyRequest request;
		try
		{
			request = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(requestStr, AsyncBatchNotifyRequest.class);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Exception deserializing AsyncBatchNotifyRequest", e)
					.appendParametersToMessage()
					.setParameter("AsyncBatchNotifyRequest", requestStr);
		}

		return request;
	}

	private void registerHandler(@NonNull final AsyncBatchNotifyRequestHandler handler)
	{
		getEventBus().subscribe(ManageAsyncBatchRequestHandlerAsEventListener.builder()
										.handler(handler)
										.eventLogUserService(eventLogUserService)
										.build());

		logger.info("Registered handler: {}", handler);
	}

	@lombok.ToString
	private static final class ManageAsyncBatchRequestHandlerAsEventListener implements IEventListener
	{
		private final EventLogUserService eventLogUserService;
		private final AsyncBatchNotifyRequestHandler handler;

		@lombok.Builder
		private ManageAsyncBatchRequestHandlerAsEventListener(
				@NonNull final AsyncBatchNotifyRequestHandler handler,
				@NonNull final EventLogUserService eventLogUserService)
		{
			this.handler = handler;
			this.eventLogUserService = eventLogUserService;
		}

		@Override
		public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
		{
			final AsyncBatchNotifyRequest request = extractAsyncBatchNotifyRequest(event);

			try (final IAutoCloseable ctx = switchCtx(request);
					final MDC.MDCCloseable eventHandlerMDC = MDC.putCloseable("eventHandler.className", handler.getClass().getName()))
			{
				eventLogUserService.invokeHandlerAndLog(EventLogUserService.InvokeHandlerAndLogRequest.builder()
																.handlerClass(handler.getClass())
																.invokaction(() -> handleRequest(request))
																.build());
			}
		}

		private void handleRequest(@NonNull final AsyncBatchNotifyRequest request)
		{
			handler.handleRequest(request);
		}

		private IAutoCloseable switchCtx(@NonNull final AsyncBatchNotifyRequest request)
		{
			final Properties ctx = createCtx(request);
			return Env.switchContext(ctx);
		}

		private Properties createCtx(@NonNull final AsyncBatchNotifyRequest request)
		{
			final Properties ctx = Env.newTemporaryCtx();
			Env.setClientId(ctx, request.getClientId());
			return ctx;
		}
	}
}
