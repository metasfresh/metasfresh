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

package de.metas.scheduler.eventbus;

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
public class SchedulerEventBusService
{
	private static final Topic TOPIC = RabbitMQEventBusConfiguration.ManageSchedulerQueueConfiguration.EVENTBUS_TOPIC;
	private static final String PROPERTY_ManageSchedulerRequest = "ManageSchedulerRequest";

	// services
	private static final Logger logger = LogManager.getLogger(SchedulerEventBusService.class);
	private final IEventBusFactory eventBusFactory;
	private final EventLogUserService eventLogUserService;

	public SchedulerEventBusService(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final EventLogUserService eventLogUserService)
	{
		this.eventBusFactory = eventBusFactory;
		this.eventLogUserService = eventLogUserService;
	}

	@Autowired
	public void registerHandlers(@NonNull final Optional<List<ManageSchedulerRequestHandler>> handlers)
	{
		handlers.ifPresent(manageSchedulerRequestHandlers -> manageSchedulerRequestHandlers.forEach(this::registerHandler));
	}

	public void postRequest(@NonNull final ManageSchedulerRequest request)
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
	private static Event createEventFromRequest(@NonNull final ManageSchedulerRequest request)
	{
		final String requestStr;
		try
		{
			requestStr = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(request);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Exception serializing manageSchedulerRequest", e)
					.appendParametersToMessage()
					.setParameter("manageSchedulerRequest", request);
		}

		return Event.builder()
				.putProperty(PROPERTY_ManageSchedulerRequest, requestStr)
				.shallBeLogged()
				.build();
	}

	@NonNull
	private static ManageSchedulerRequest extractManageSchedulerRequest(@NonNull final Event event)
	{
		final String requestStr = event.getProperty(PROPERTY_ManageSchedulerRequest);

		final ManageSchedulerRequest request;
		try
		{
			request = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(requestStr, ManageSchedulerRequest.class);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Exception deserializing manageSchedulerRequest", e)
					.appendParametersToMessage()
					.setParameter("manageSchedulerRequest", requestStr);
		}

		return request;
	}

	private void registerHandler(@NonNull final ManageSchedulerRequestHandler handler)
	{
		getEventBus().subscribe(SchedulerEventBusService.ManageSchedulerRequestHandlerAsEventListener.builder()
										.handler(handler)
										.eventLogUserService(eventLogUserService)
										.build());

		logger.info("Registered handler: {}", handler);
	}

	@lombok.ToString
	private static final class ManageSchedulerRequestHandlerAsEventListener implements IEventListener
	{
		private final EventLogUserService eventLogUserService;
		private final ManageSchedulerRequestHandler handler;

		@lombok.Builder
		private ManageSchedulerRequestHandlerAsEventListener(
				@NonNull final ManageSchedulerRequestHandler handler,
				@NonNull final EventLogUserService eventLogUserService)
		{
			this.handler = handler;
			this.eventLogUserService = eventLogUserService;
		}

		@Override
		public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
		{
			final ManageSchedulerRequest request = extractManageSchedulerRequest(event);

			try (final IAutoCloseable ctx = switchCtx(request);
					final MDC.MDCCloseable eventHandlerMDC = MDC.putCloseable("eventHandler.className", handler.getClass().getName()))
			{
				eventLogUserService.invokeHandlerAndLog(EventLogUserService.InvokeHandlerAndLogRequest.builder()
																.handlerClass(handler.getClass())
																.invokaction(() -> handleRequest(request))
																.build());
			}
		}

		private void handleRequest(@NonNull final ManageSchedulerRequest request)
		{
			handler.handleRequest(request);
		}

		private IAutoCloseable switchCtx(@NonNull final ManageSchedulerRequest request)
		{
			final Properties ctx = createCtx(request);
			return Env.switchContext(ctx);
		}

		private Properties createCtx(@NonNull final ManageSchedulerRequest request)
		{
			final Properties ctx = Env.newTemporaryCtx();
			Env.setClientId(ctx, request.getClientId());
			return ctx;
		}
	}
}
