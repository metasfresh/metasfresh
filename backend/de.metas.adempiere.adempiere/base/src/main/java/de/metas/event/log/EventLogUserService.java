package de.metas.event.log;

import java.util.Collection;

import javax.annotation.Nullable;

import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
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
	@VisibleForTesting
	static final String PROPERTY_PROCESSED_BY_HANDLER_CLASS_NAMES = "EventStore_ProcessedByHandlerClassNames";

	@Value
	public static class EventLogEntryRequest
	{
		boolean processed;
		boolean error;
		AdIssueId adIssueId;
		String message;
		Class<?> eventHandlerClass;

		private int clientId;
		private int orgId;

		@Builder(buildMethodName = "createAndStore")
		public EventLogEntryRequest(
				final boolean processed,
				final boolean error,
				final AdIssueId adIssueId,
				@Nullable final String message, Class<?> eventHandlerClass)
		{
			this.processed = processed;
			this.error = error;
			this.adIssueId = adIssueId;
			this.message = message;
			this.eventHandlerClass = eventHandlerClass;

			this.clientId = Env.getAD_Client_ID(Env.getCtx());
			this.orgId = Env.getAD_Org_ID(Env.getCtx());

			final EventLogEntryCollector eventLogCollector = EventLogEntryCollector.getThreadLocal();
			eventLogCollector.addEventLog(this);
		}

		public static class EventLogEntryRequestBuilder
		{
			public EventLogEntryRequestBuilder formattedMessage(
					@NonNull final String message,
					@Nullable final Object... params)
			{
				message(StringUtils.formatMessage(message, params));
				return this;
			}
		}
	}

	/**
	 * Creates a builder to log a message to the current event processing log.
	 * <p>
	 * Note: as your current code was most probably invoked via {@link #invokeHandlerAndLog(InvokeHandlerAndLogRequest)},
	 * it might be more convenient to use {@link Loggables#get()}.
	 *
	 * @param handlerClass the class to be logged in the record.
	 */
	private EventLogEntryRequest.EventLogEntryRequestBuilder newLogEntry(@NonNull final Class<?> handlerClass)
	{
		return EventLogEntryRequest.builder()
				.eventHandlerClass(handlerClass);
	}

	/**
	 * Creates a builder to log a an error to the current event processing log.
	 * <p>
	 * Note: as your current code was most probably invoked via {@link #invokeHandlerAndLog(InvokeHandlerAndLogRequest)},
	 * it might be more convenient just throw a runtime exception.
	 *
	 * @param handlerClass the class to be logged in the record.
	 */
	public EventLogEntryRequest.EventLogEntryRequestBuilder newErrorLogEntry(
			@NonNull final Class<?> handlerClass,
			@NonNull final Exception e)
	{
		final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(e);

		return EventLogEntryRequest.builder()
				.error(true)
				.eventHandlerClass(handlerClass)
				.message(e.getMessage())
				.adIssueId(issueId);
	}

	@Value
	@Builder
	public static class InvokeHandlerAndLogRequest
	{
		@NonNull
		Class<?> handlerClass;

		@NonNull
		Runnable invokaction;

		@Default
		boolean onlyIfNotAlreadyProcessed = true;
	}

	/**
	 * Invokes the given {@code request}'s runnable and sets up a threadlocal {@link ILoggable}.
	 */
	public void invokeHandlerAndLog(@NonNull final InvokeHandlerAndLogRequest request)
	{
		if (request.isOnlyIfNotAlreadyProcessed()
				&& wasEventProcessedByHandler(request.getHandlerClass()))
		{
			return;
		}

		try (final IAutoCloseable loggable = EventLogLoggable.createAndRegisterThreadLocal(request.getHandlerClass()))
		{
			request.getInvokaction().run();

			newLogEntry(request.getHandlerClass())
					.formattedMessage("this handler is done")
					.processed(true)
					.createAndStore();
		}
		catch (final RuntimeException e)
		{
			// e.printStackTrace();
			newErrorLogEntry(
					request.getHandlerClass(), e)
							.createAndStore();
		}
	}

	private boolean wasEventProcessedByHandler(@NonNull final Class<?> handlerClass)
	{
		final EventLogEntryCollector eventLogCollector = EventLogEntryCollector.getThreadLocal();

		final Collection<String> processedByHandlerClassNames = eventLogCollector.getEvent()
				.getProperty(PROPERTY_PROCESSED_BY_HANDLER_CLASS_NAMES);

		return processedByHandlerClassNames != null
				&& processedByHandlerClassNames.contains(handlerClass.getName());
	}
}
