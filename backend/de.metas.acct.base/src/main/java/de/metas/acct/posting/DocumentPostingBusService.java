package de.metas.acct.posting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.posting.log.DocumentPostingLogService;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.log.EventLogUserService;
import de.metas.event.log.EventLogUserService.InvokeHandlerAndLogRequest;
import de.metas.event.remote.rabbitmq.queues.accounting.AccountingQueueConfiguration;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
@DependsOn(Adempiere.BEAN_NAME) // needs the database up
public class DocumentPostingBusService
{
	private static final Topic TOPIC = AccountingQueueConfiguration.EVENTBUS_TOPIC;

	// services
	@NonNull private static final Logger logger = LogManager.getLogger(DocumentPostingBusService.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final EventConverter eventConverter = new EventConverter();
	@NonNull private final IEventBusFactory eventBusFactory;
	@NonNull private final DocumentPostingLogService documentPostingLogService;

	public DocumentPostingBusService(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final EventLogUserService eventLogUserService,
			@NonNull final DocumentPostingLogService documentPostingLogService,
			@NonNull final Optional<List<DocumentPostRequestHandler>> optionalHandlers)
	{
		this.eventBusFactory = eventBusFactory;
		this.documentPostingLogService = documentPostingLogService;

		final List<DocumentPostRequestHandler> handlers = optionalHandlers.orElseGet(ImmutableList::of);
		if (!handlers.isEmpty())
		{
			final IEventBus eventBus = eventBusFactory.getEventBus(TOPIC);

			handlers.forEach(handler -> {
				eventBus.subscribe(DocumentPostRequestHandlerAsEventListener.builder()
						.handler(handler)
						.eventConverter(eventConverter)
						.eventLogUserService(eventLogUserService)
						.build());

				logger.info("Registered handler: {}", handler);
			});
		}
		else
		{
			logger.info("No handlers registered");
		}
	}

	public void sendAfterCommit(@NonNull final DocumentPostMultiRequest requests)
	{
		trxManager.accumulateAndProcessAfterCommit(
				"DocumentPostRequests.toSendToEventBus",
				requests.toSet(),
				this::sendNow
		);
	}

	private void sendNow(@NonNull final Collection<DocumentPostRequest> requests)
	{
		if (requests.isEmpty()) {return;}

		logger.debug("Enqueueing to be posted on server server: {}", requests);

		final IEventBus eventBus = eventBus();

		for (final DocumentPostRequest request : requests)
		{
			final Event event = eventConverter.createEventFromRequest(request);
			eventBus.enqueueEvent(event);
		}

		documentPostingLogService.logEnqueued(requests);
	}

	private IEventBus eventBus() {return eventBusFactory.getEventBus(TOPIC);}

	//
	//
	// -------------------------------------------------------------------------
	//
	//

	private static class EventConverter
	{
		private static final String EVENTNAME = "DocumentPostRequest";
		private static final String EVENT_PROPERTY_DocumentPostRequest = "DocumentPostRequest";

		@NonNull private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		@NonNull
		public DocumentPostRequest extractDocumentPostRequest(@NonNull final Event event)
		{
			final String requestStr = event.getProperty(EVENT_PROPERTY_DocumentPostRequest);
			return fromJson(requestStr);
		}

		@NonNull
		public Event createEventFromRequest(@NonNull final DocumentPostRequest request)
		{
			return Event.builder()
					.putProperty(EVENT_PROPERTY_DocumentPostRequest, toJsonString(request))
					.setSourceRecordReference(request.getRecord())
					.setEventName(EVENTNAME)
					.shallBeLogged()
					.build();
		}

		@NonNull
		private String toJsonString(@NonNull final DocumentPostRequest request)
		{
			try
			{
				return jsonObjectMapper.writeValueAsString(request);
			}
			catch (Exception ex)
			{
				throw new AdempiereException("Failed converting to JSON: " + request, ex);
			}
		}

		@NonNull
		private DocumentPostRequest fromJson(@NonNull final String json)
		{
			try
			{
				return jsonObjectMapper.readValue(json, DocumentPostRequest.class);
			}
			catch (JsonProcessingException e)
			{
				throw new AdempiereException("Failed converting from JSON: " + json, e);
			}
		}
	}

	//
	//
	// -------------------------------------------------------------------------
	//
	//

	@lombok.Builder
	@lombok.ToString
	private static final class DocumentPostRequestHandlerAsEventListener implements IEventListener
	{
		@NonNull private final DocumentPostRequestHandler handler;
		@NonNull private final EventConverter eventConverter;
		@NonNull private final EventLogUserService eventLogUserService;

		@Override
		public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
		{
			final DocumentPostRequest request = eventConverter.extractDocumentPostRequest(event);

			try (final IAutoCloseable ignored = switchCtx(request);
					final MDCCloseable ignored1 = TableRecordMDC.putTableRecordReference(request.getRecord());
					final MDCCloseable ignored2 = MDC.putCloseable("eventHandler.className", handler.getClass().getName()))
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
