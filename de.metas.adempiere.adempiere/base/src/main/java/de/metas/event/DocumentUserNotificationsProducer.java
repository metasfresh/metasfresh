package de.metas.event;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import lombok.Builder;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * An convenient user notification producer which allows APIs to easily notify about documents.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <ModelType>
 */
public final class DocumentUserNotificationsProducer<ModelType>
{
	// services
	private static final Logger loggerDefault = LogManager.getLogger(DocumentUserNotificationsProducer.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	// params
	private final Logger logger;
	private final Topic topic;
	private final String eventAD_Message;
	private final Function<ModelType, List<Object>> eventAD_MessageParamsExtractor;

	@Builder
	private DocumentUserNotificationsProducer(
			final Logger logger,
			@NonNull final Topic topic,
			@NonNull String eventAD_Message,
			@NonNull Function<ModelType, List<Object>> eventAD_MessageParamsExtractor)
	{
		this.logger = logger != null ? logger : loggerDefault;
		this.topic = topic;
		this.eventAD_Message = eventAD_Message;
		this.eventAD_MessageParamsExtractor = eventAD_MessageParamsExtractor;
	}

	/**
	 * Post events about given document.
	 *
	 * @param document
	 * @param recipientUserId
	 */
	public DocumentUserNotificationsProducer<ModelType> notify(final ModelType document, final int recipientUserId)
	{
		if (document == null)
		{
			return this;
		}

		try
		{
			postNotification(createUserNotification(document, recipientUserId));
		}
		catch (final Exception e)
		{
			logger.warn("Failed creating event for {}. Ignored.", document, e);
		}

		return this;
	}

	private final UserNotificationRequest createUserNotification(@NonNull final ModelType document, final int recipientUserId)
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
		final List<Object> adMessageParams = extractEventMessageParams(document);

		//
		// Create an return the user notification
		return newUserNotificationRequest()
				.recipientUserId(recipientUserIdToUse)
				.contentADMessage(eventAD_Message)
				.contentADMessageParams(adMessageParams)
				.targetRecord(TableRecordReference.of(document))
				.build();
	}

	private final UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(topic);
	}

	private List<Object> extractEventMessageParams(final ModelType document)
	{
		List<Object> params = eventAD_MessageParamsExtractor.apply(document);
		return params != null ? params : ImmutableList.of();
	}

	private int extractRecipientUser(final ModelType document)
	{
		final Integer createdBy = InterfaceWrapperHelper.getValueOrNull(document, "CreatedBy");
		return createdBy == null ? -1 : createdBy;
	}

	private void postNotification(final UserNotificationRequest notification)
	{
		notificationBL.notifyRecipientAfterCommit(notification);
	}
}
