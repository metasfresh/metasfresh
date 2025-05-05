/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.eventbus;

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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
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
public class EffortControlEventBusService
{
	private static final Topic TOPIC = RabbitMQEventBusConfiguration.EffortControlQueueConfiguration.EVENTBUS_TOPIC;
	private static final String PROPERTY_EffortControlEventRequest = "EffortControlEventRequest";

	private static final Logger logger = LogManager.getLogger(EffortControlEventBusService.class);

	private final IEventBusFactory eventBusFactory;
	private final EventLogUserService eventLogUserService;

	public EffortControlEventBusService(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final EventLogUserService eventLogUserService)
	{
		this.eventBusFactory = eventBusFactory;
		this.eventLogUserService = eventLogUserService;
	}

	@Autowired
	public void registerHandlers(@NonNull final Optional<List<EffortControlEventHandler>> handlers)
	{
		handlers.ifPresent(manageEffortControlRequestHandlers -> manageEffortControlRequestHandlers.forEach(this::registerHandler));
	}

	public void postRequestAfterCommit(@NonNull final EffortControlEventRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.runAfterCommit(() -> postRequest(request));
	}

	private void postRequest(@NonNull final EffortControlEventRequest request)
	{
		final Event event = createEventFromRequest(request);

		getEventBus().enqueueEvent(event);
	}

	private void registerHandler(@NonNull final EffortControlEventHandler handler)
	{
		getEventBus().subscribe(ManageEffortControlEventRequestHandlerAsEventListener.builder()
										.handler(handler)
										.eventLogUserService(eventLogUserService)
										.build());

		logger.info("Registered handler: {}", handler);
	}

	@NonNull
	private IEventBus getEventBus()
	{
		return eventBusFactory.getEventBus(TOPIC);
	}

	@NonNull
	private static Event createEventFromRequest(@NonNull final EffortControlEventRequest request)
	{
		try
		{
			final String requestStr = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(request);

			return Event.builder()
					.putProperty(PROPERTY_EffortControlEventRequest, requestStr)
					.shallBeLogged()
					.build();
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Exception serializing EffortControlEventRequest", e)
					.appendParametersToMessage()
					.setParameter("request", request);
		}
	}

	@NonNull
	private static EffortControlEventRequest extractEffortControlEventRequest(@NonNull final Event event)
	{
		final String requestStr = event.getProperty(PROPERTY_EffortControlEventRequest);

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(requestStr, EffortControlEventRequest.class);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Exception deserializing effortControlRequest", e)
					.appendParametersToMessage()
					.setParameter("EffortControlRequest", requestStr);
		}
	}

	@lombok.ToString
	private static final class ManageEffortControlEventRequestHandlerAsEventListener implements IEventListener
	{
		private final EventLogUserService eventLogUserService;
		private final EffortControlEventHandler handler;

		@lombok.Builder
		private ManageEffortControlEventRequestHandlerAsEventListener(
				@NonNull final EffortControlEventHandler handler,
				@NonNull final EventLogUserService eventLogUserService)
		{
			this.handler = handler;
			this.eventLogUserService = eventLogUserService;
		}

		@Override
		public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
		{
			final EffortControlEventRequest request = extractEffortControlEventRequest(event);

			try (final IAutoCloseable ctx = switchCtx(request);
					final MDC.MDCCloseable eventHandlerMDC = MDC.putCloseable("eventHandler.className", handler.getClass().getName()))
			{
				eventLogUserService.invokeHandlerAndLog(EventLogUserService.InvokeHandlerAndLogRequest.builder()
																.handlerClass(handler.getClass())
																.invokaction(() -> handleRequest(request))
																.build());
			}
		}

		private void handleRequest(@NonNull final EffortControlEventRequest request)
		{
			handler.handleRequest(request);
		}

		private IAutoCloseable switchCtx(@NonNull final EffortControlEventRequest request)
		{
			final Properties ctx = createCtx(request);
			return Env.switchContext(ctx);
		}

		private Properties createCtx(@NonNull final EffortControlEventRequest request)
		{
			final Properties ctx = Env.newTemporaryCtx();
			Env.setClientId(ctx, request.getClientAndOrgId().getClientId());
			return ctx;
		}
	}
}
