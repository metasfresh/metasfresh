package de.metas.acct.posting;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.SimpleObjectSerializer;
import de.metas.event.Topic;
import de.metas.event.log.EventLogUserService;
import de.metas.event.log.EventLogUserService.InvokeHandlerAndLogRequest;
import de.metas.event.remote.RabbitMQEventBusConfiguration;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
@DependsOn(Adempiere.BEAN_NAME) // needs database
public class DocumentPostingBusService
{
	private static final Topic TOPIC = RabbitMQEventBusConfiguration.AccountingQueueConfiguration.EVENTBUS_TOPIC;
	private static final String PROPERTY_DocumentPostRequest = "DocumentPostRequest";

	// services
	private static final Logger logger = LogManager.getLogger(DocumentPostingBusService.class);
	private final IEventBusFactory eventBusFactory;
	private final EventLogUserService eventLogUserService;

	public DocumentPostingBusService(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final EventLogUserService eventLogUserService)
	{
		this.eventBusFactory = eventBusFactory;
		this.eventLogUserService = eventLogUserService;
	}

	public void postRequest(@NonNull final DocumentPostRequest request)
	{
		final Event event = createEventFromRequest(request);
		getEventBus().enqueueEvent(event);
	}

	private IEventBus getEventBus()
	{
		return eventBusFactory.getEventBus(TOPIC);
	}

	private Event createEventFromRequest(@NonNull final DocumentPostRequest request)
	{
		final String requestStr = SimpleObjectSerializer.get().serialize(request);

		return Event.builder()
				.putProperty(PROPERTY_DocumentPostRequest, requestStr)
				.shallBeLogged()
				.build();
	}

	private static DocumentPostRequest extractDocumentPostRequest(@NonNull final Event event)
	{
		final String requestStr = event.getProperty(PROPERTY_DocumentPostRequest);

		final DocumentPostRequest request = SimpleObjectSerializer.get()
				.deserialize(requestStr, DocumentPostRequest.class);
		return request;
	}

	@Autowired
	public void registerHandlers(@NonNull final Optional<List<DocumentPostRequestHandler>> handlers)
	{
		if (handlers.isPresent())
		{
			handlers.get().forEach(this::registerHandler);
		}
	}

	public void registerHandler(@NonNull final DocumentPostRequestHandler handler)
	{
		getEventBus().subscribe(DocumentPostRequestHandlerAsEventListener.builder()
				.handler(handler)
				.eventLogUserService(eventLogUserService)
				.build());

		logger.info("Registered handler: {}", handler);
	}

	@lombok.ToString
	private static final class DocumentPostRequestHandlerAsEventListener implements IEventListener
	{
		private final EventLogUserService eventLogUserService;
		private final DocumentPostRequestHandler handler;

		@lombok.Builder
		private DocumentPostRequestHandlerAsEventListener(
				@NonNull final DocumentPostRequestHandler handler,
				@NonNull final EventLogUserService eventLogUserService)
		{
			this.handler = handler;
			this.eventLogUserService = eventLogUserService;
		}

		@Override
		public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
		{
			final DocumentPostRequest request = extractDocumentPostRequest(event);

			try (final IAutoCloseable ctx = switchCtx(request);
					final MDCCloseable requestRecordMDC = TableRecordMDC.putTableRecordReference(request.getRecord());
					final MDCCloseable eventHandlerMDC = MDC.putCloseable("eventHandler.className", handler.getClass().getName());)
			{
				eventLogUserService.invokeHandlerAndLog(InvokeHandlerAndLogRequest.builder()
						.handlerClass(handler.getClass())
						.invokaction(() -> handleRequest(request))
						.build());
			}
		}

		private void handleRequest(@NonNull final DocumentPostRequest request)
		{
			handler.handleRequest(request);
		}

		private IAutoCloseable switchCtx(@NonNull final DocumentPostRequest request)
		{
			final Properties ctx = createCtx(request);
			return Env.switchContext(ctx);
		}

		private Properties createCtx(@NonNull final DocumentPostRequest request)
		{
			final Properties ctx = Env.newTemporaryCtx();
			Env.setClientId(ctx, request.getClientId());
			return ctx;
		}
	}
}
