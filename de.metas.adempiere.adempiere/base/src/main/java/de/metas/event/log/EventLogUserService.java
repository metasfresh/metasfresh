package de.metas.event.log;

import java.util.Collection;

import javax.annotation.Nullable;

import org.adempiere.ad.service.IErrorManager;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.model.I_AD_Issue;
import org.springframework.stereotype.Service;

import de.metas.event.Event;
import de.metas.event.log.impl.EventLogEntryCollector;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Tools that can be used by code that uses the metasfresh event framework, when either posting or handling events.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */

@Service
public class EventLogUserService
{
	public static final String PROPERTY_PROCESSED_BY_HANDLER_CLASS_NAMES = "EventStore_ProcessedByHandlerClassNames";

	public static final String PROPERTY_STORE_THIS_EVENT = "EventStore_StoreThisEvent";

	@Value
	public static class EventLogEntryRequest
	{
		boolean processed;
		boolean error;
		int adIssueId;
		String message;
		Class<?> eventHandlerClass;

		@Builder(buildMethodName = "storeEntry")
		public EventLogEntryRequest(
				final boolean processed,
				final boolean error,
				final int adIssueId,
				@Nullable final String message, Class<?> eventHandlerClass)
		{
			this.processed = processed;
			this.error = error;
			this.adIssueId = adIssueId;
			this.message = message;
			this.eventHandlerClass = eventHandlerClass;

			final EventLogEntryCollector eventLogCollector = EventLogEntryCollector.getThreadLocal();
			eventLogCollector.addEventLog(this);
		}

		public static class EventLogEntryRequestBuilder
		{
			public EventLogEntryRequestBuilder formattedMessage(final String message, final Object... params)
			{
				message(StringUtils.formatMessage(message, params));
				return this;
			}
		}
	}

	/**
	 * Before <b>posting</b> an event, you can use this method to tell the system, that the event shall be stored.
	 * <p>
	 * Note: even if an event was not prepared by this method, you can still store log messages,
	 * but there won't be event data needed to <i>replay</i> the event in question.
	 *
	 * @param eventbuilder
	 * @param adviseValue
	 * @return
	 */
	public Event.Builder addEventLogAdvise(
			@NonNull final Event.Builder eventbuilder,
			final boolean adviseValue)
	{
		return eventbuilder.putProperty(PROPERTY_STORE_THIS_EVENT, adviseValue);
	}

	public EventLogEntryRequest.EventLogEntryRequestBuilder newLogEntry(@NonNull final Class<?> handlerClass)
	{
		return EventLogEntryRequest.builder()
				.eventHandlerClass(handlerClass);
	}

	public EventLogEntryRequest.EventLogEntryRequestBuilder newErrorLogEntry(
			@NonNull final Class<?> handlerClass,
			@NonNull final Exception e)
	{
		final I_AD_Issue issue = Services.get(IErrorManager.class).createIssue(e);

		return EventLogEntryRequest.builder()
				.error(true)
				.eventHandlerClass(handlerClass)
				.message(e.getMessage())
				.adIssueId(issue.getAD_Issue_ID());
	}

	@Value
	@Builder
	public static class InvokeHandlerandLogRequest
	{
		Class<?> handlerClass;

		Runnable invokaction;

		@Default
		boolean onlyIfNotAlreadyProcessed = true;
	}

	public void invokeHandlerAndLog(@NonNull final InvokeHandlerandLogRequest request)
	{
		if (request.isOnlyIfNotAlreadyProcessed()
				&& wasEventProcessedbyHandler(request.getHandlerClass()))
		{
			return;
		}

		try
		{
			request.getInvokaction().run();

			newLogEntry(request.getHandlerClass())
					.processed(true)
					.storeEntry();
		}
		catch (final RuntimeException e)
		{
			newErrorLogEntry(request.getHandlerClass(), e)
					.storeEntry();
		}
	}

	private boolean wasEventProcessedbyHandler(@NonNull final Class<?> handlerClass)
	{
		final EventLogEntryCollector eventLogCollector = EventLogEntryCollector.getThreadLocal();

		final Collection<String> processedByHandlerClassNames = eventLogCollector.getEvent()
				.getProperty(PROPERTY_PROCESSED_BY_HANDLER_CLASS_NAMES);

		return processedByHandlerClassNames != null
				&& processedByHandlerClassNames.contains(handlerClass.getName());
	}
}
