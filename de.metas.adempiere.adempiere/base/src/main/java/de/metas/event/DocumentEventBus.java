package de.metas.event;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import com.google.common.base.Function;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * An convenient event bus implementation which allows APIs to easily notify about documents.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <ModelType>
 */
public class DocumentEventBus<ModelType> extends QueueableForwardingEventBus
{
	public static <ModelType> Builder<ModelType> builder()
	{
		return new Builder<>();
	}

	private static final Logger loggerDefault = LogManager.getLogger(DocumentEventBus.class);
	private final Logger logger;

	private final String eventAD_Message;
	private final Function<ModelType, Object[]> eventAD_MessageParamsExtractor;

	private DocumentEventBus(final Builder<ModelType> builder)
	{
		super(builder.createEventBus());
		this.logger = builder.getLogger(loggerDefault);
		this.eventAD_Message = builder.getEventAD_Message();
		this.eventAD_MessageParamsExtractor = builder.getEventAD_MessageParamsExtractor();

		if (builder.queueEventsUntilCurrentTrxCommit)
		{
			queueEventsUntilCurrentTrxCommit();
		}
	}

	@Override
	public DocumentEventBus<ModelType> queueEvents()
	{
		super.queueEvents();
		return this;
	}

	@Override
	public DocumentEventBus<ModelType> queueEventsUntilTrxCommit(final String trxName)
	{
		super.queueEventsUntilTrxCommit(trxName);
		return this;
	}

	@Override
	public DocumentEventBus<ModelType> queueEventsUntilCurrentTrxCommit()
	{
		super.queueEventsUntilCurrentTrxCommit();
		return this;
	}

	/**
	 * Post events about given document.
	 *
	 * @param document
	 * @param recipientUserId
	 */
	public DocumentEventBus<ModelType> notify(final ModelType document, final int recipientUserId)
	{
		if (document == null)
		{
			return this;
		}

		try
		{
			final Event event = createEvent(document, recipientUserId);
			postEvent(event);
		}
		catch (final Exception e)
		{
			logger.warn("Failed creating event for " + document + ". Ignored.", e);
		}

		return this;
	}

	private final Event createEvent(final ModelType document, final int recipientUserId)
	{
		Check.assumeNotNull(document, "document not null");

		//
		// Get the recipient
		final int recipientUserIdToUse;
		if (recipientUserId > 0)
		{
			recipientUserIdToUse = recipientUserId;
		}
		else
		{
			recipientUserIdToUse = extractRecipientUser(document);
			if (recipientUserIdToUse < 0)
			{
				throw new AdempiereException("No recipient found for " + document);
			}
		}

		//
		// Extract event message parameters
		final Object[] adMessageParams = extractEventMessageParams(document);

		//
		// Create an return the event
		final Event event = Event.builder()
				.setDetailADMessage(eventAD_Message, adMessageParams)
				.addRecipient_User_ID(recipientUserIdToUse)
				.setRecord(TableRecordReference.of(document))
				.build();
		return event;
	}

	private Object[] extractEventMessageParams(final ModelType document)
	{
		return eventAD_MessageParamsExtractor.apply(document);
	}

	private int extractRecipientUser(final ModelType document)
	{
		final Integer createdBy = InterfaceWrapperHelper.getValueOrNull(document, "CreatedBy");
		return createdBy == null ? -1 : createdBy;
	}

	public static final class Builder<ModelType>
	{
		private Topic topic;
		private Logger logger;
		private String eventAD_Message;
		private Function<ModelType, Object[]> eventAD_MessageParamsExtractor;
		private boolean queueEventsUntilCurrentTrxCommit;

		private Builder()
		{
			super();
		}

		public DocumentEventBus<ModelType> build()
		{
			return new DocumentEventBus<>(this);
		}

		public Builder<ModelType> setTopic(final Topic topic)
		{
			this.topic = topic;
			return this;
		}

		private final IEventBus createEventBus()
		{
			Check.assumeNotNull(topic, "topic not null");
			final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(topic);
			return eventBus;
		}

		public Builder<ModelType> setLogger(final Logger logger)
		{
			this.logger = logger;
			return this;
		}

		private final Logger getLogger(final Logger defaultLogger)
		{
			return logger == null ? defaultLogger : logger;
		}

		public Builder<ModelType> setEventAD_Message(final String eventAD_Message)
		{
			this.eventAD_Message = eventAD_Message;
			return this;
		}

		private final String getEventAD_Message()
		{
			Check.assumeNotEmpty(eventAD_Message, "eventAD_Message not empty");
			return eventAD_Message;
		}

		public Builder<ModelType> setEventAD_MessageParamsExtractor(final Function<ModelType, Object[]> eventAD_MessageParamsExtractor)
		{
			this.eventAD_MessageParamsExtractor = eventAD_MessageParamsExtractor;
			return this;
		}

		private final Function<ModelType, Object[]> getEventAD_MessageParamsExtractor()
		{
			Check.assumeNotNull(eventAD_MessageParamsExtractor, "eventAD_MessageParamsExtractor not null");
			return eventAD_MessageParamsExtractor;
		}

		public final Builder<ModelType> queueEventsUntilCurrentTrxCommit()
		{
			this.queueEventsUntilCurrentTrxCommit = true;
			return this;
		}
	}
}
