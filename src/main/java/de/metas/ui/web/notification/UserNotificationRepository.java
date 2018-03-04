package de.metas.ui.web.notification;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Note;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.event.Event;
import de.metas.i18n.IADMessageDAO;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.notification.UserNotification.TargetType;
import de.metas.ui.web.notification.UserNotification.UserNotificationBuilder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

@Component
public class UserNotificationRepository
{
	private static final transient Logger logger = LogManager.getLogger(UserNotificationRepository.class);

	public static final String EVENT_PARAM_Important = "important";

	/** AD_Message to be used when there was no AD_Message provided */
	private static final String DEFAULT_AD_MESSAGE = "webui.window.notification.caption";

	@Autowired
	private ObjectMapper jsonMapper;

	public List<UserNotification> getByUser(final int adUserId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Note.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Note.COLUMN_AD_User_ID, adUserId)
				.orderBy().addColumn(I_AD_Note.COLUMN_AD_Note_ID).endOrderBy()
				.create()
				.stream(I_AD_Note.class)
				.map(this::toUserNotification)
				.collect(ImmutableList.toImmutableList());
	}

	public List<UserNotification> save(final Event event)
	{
		final Collection<Integer> recipientUserIds;
		if (event.isAllRecipients())
		{
			recipientUserIds = Services.get(IUserDAO.class).retrieveSystemUserIds();
		}
		else
		{
			recipientUserIds = event.getRecipientUserIds();
		}

		return recipientUserIds.stream()
				.map(recipientUserId -> {
					try
					{
						final UserNotification notification = save(event, recipientUserId);
						return notification;
					}
					catch (final Exception ex)
					{
						logger.warn("Failed converting event to notification for recipientUserId={}: {}", recipientUserId, event, ex);
						return null;
					}
				})
				.filter(notification -> notification != null)
				.collect(ImmutableList.toImmutableList());
	}

	private UserNotification save(final Event event, final int recipientUserId)
	{
		final I_AD_Note notificationPO = InterfaceWrapperHelper.newInstance(I_AD_Note.class);

		//
		// Important
		final boolean important = DisplayType.toBoolean(event.getProperty(EVENT_PARAM_Important), false);
		notificationPO.setAD_User_ID(recipientUserId);
		notificationPO.setIsImportant(important);

		//
		// detailADMessage -> AD_Message
		int adMessageId = -1;
		final String detailADMessage = event.getDetailADMessage();
		if (!Check.isEmpty(detailADMessage, true))
		{
			adMessageId = Services.get(IADMessageDAO.class).retrieveIdByValue(detailADMessage);
		}
		if (adMessageId <= 0)
		{
			adMessageId = Services.get(IADMessageDAO.class).retrieveIdByValue(DEFAULT_AD_MESSAGE);
		}
		notificationPO.setAD_Message_ID(adMessageId);

		//
		// detailADMessageParams
		final Map<String, Object> detailADMessageParams = event.getProperties();
		try
		{
			final String detailADMessageParamsJSON = jsonMapper.writeValueAsString(detailADMessageParams);
			notificationPO.setAD_Message_ParamsJSON(detailADMessageParamsJSON);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException(e);
		}

		//
		// detailPlain
		final String detailPlain = event.getDetailPlain();
		notificationPO.setTextMsg(detailPlain);

		//
		// Target: window (from document record)
		final ITableRecordReference targetRecord = event.getRecord();
		final TargetType targetType;
		final String targetTableName;
		final int targetADTableId;
		final int targetRecordId;
		final int targetADWindowId;
		if (targetRecord != null)
		{
			targetType = TargetType.Window;
			final Object suggestedWindowIdObj = event.getProperty(Event.PROPERTY_SuggestedWindowId);
			final int suggestedWindowId = suggestedWindowIdObj instanceof Number ? ((Number)suggestedWindowIdObj).intValue() : -1;
			if (suggestedWindowId > 0)
			{
				targetADWindowId = suggestedWindowId;
				targetADTableId = targetRecord.getAD_Table_ID();
				targetTableName = targetRecord.getTableName();
				targetRecordId = targetRecord.getRecord_ID();
			}
			else
			{
				final RecordZoomWindowFinder recordWindowFinder = RecordZoomWindowFinder.newInstance(targetRecord);
				targetADWindowId = recordWindowFinder.findAD_Window_ID();
				targetADTableId = recordWindowFinder.getAD_Table_ID();
				targetTableName = recordWindowFinder.getTableName();
				targetRecordId = recordWindowFinder.getRecord_ID();
			}
		}
		else
		{
			targetType = TargetType.None;
			targetADWindowId = -1;
			targetTableName = null;
			targetADTableId = -1;
			targetRecordId = -1;
		}
		notificationPO.setAD_Table_ID(targetADTableId);
		notificationPO.setRecord_ID(targetRecordId);
		notificationPO.setAD_Window_ID(targetADWindowId);

		//
		//
		InterfaceWrapperHelper.save(notificationPO);

		return UserNotification.builder()
				.id(String.valueOf(notificationPO.getAD_Note_ID()))
				.timestamp(notificationPO.getCreated().getTime())
				.important(important)
				.recipientUserId(recipientUserId)
				//
				.detailADMessage(detailADMessage)
				.detailADMessageParams(detailADMessageParams)
				.detailPlain(detailPlain)
				//
				.targetType(targetType)
				.targetTableName(targetTableName)
				.targetRecordId(targetRecordId)
				.targetADWindowId(targetADWindowId)
				//
				.build();

	}

	public UserNotification toUserNotification(@NonNull final I_AD_Note notificationPO)
	{
		final UserNotificationBuilder builder = UserNotification.builder()
				.id(String.valueOf(notificationPO.getAD_Note_ID()))
				.timestamp(notificationPO.getCreated().getTime())
				.important(notificationPO.isImportant())
				.recipientUserId(notificationPO.getAD_User_ID())
				.read(notificationPO.isProcessed());

		//
		// detailADMessage
		final int detailADMessageId = notificationPO.getAD_Message_ID();
		final String detailADMessage = Services.get(IADMessageDAO.class).retrieveValueById(detailADMessageId);
		builder.detailADMessage(detailADMessage);

		//
		// detailADMessageParams
		final String detailADMessageParamsJSON = notificationPO.getAD_Message_ParamsJSON();
		final Map<String, Object> detailADMessageParams;
		if (!Check.isEmpty(detailADMessageParamsJSON, true))
		{
			try
			{
				detailADMessageParams = jsonMapper.readValue(detailADMessageParamsJSON, Map.class);
			}
			catch (final Exception ex)
			{
				throw AdempiereException.wrapIfNeeded(ex);
			}
		}
		else
		{
			detailADMessageParams = ImmutableMap.of();
		}
		builder.detailADMessageParams(detailADMessageParams);

		//
		// detailPlain
		builder.detailPlain(notificationPO.getTextMsg());

		//
		// Target record
		final int adTableId = notificationPO.getAD_Table_ID();
		if (adTableId > 0)
		{
			final String targetTableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
			final int targetRecordId = notificationPO.getRecord_ID();
			final int targetADWindowId = notificationPO.getAD_Window_ID();
			builder.targetType(TargetType.Window)
					.targetTableName(targetTableName)
					.targetRecordId(targetRecordId)
					.targetADWindowId(targetADWindowId);
		}
		else
		{
			builder.targetType(TargetType.None)
					.targetTableName(null)
					.targetRecordId(-1)
					.targetADWindowId(-1);
		}

		return builder.build();
	}

	public boolean markAsRead(final UserNotification notification)
	{
		final boolean alreadyRead = notification.setRead(true);
		if (alreadyRead)
		{
			logger.trace("Skip marking notification as read because it's already read: {}", notification);
			return false;
		}

		final I_AD_Note notificationPO = retrieveAD_Note(notification);
		notificationPO.setProcessed(true);
		InterfaceWrapperHelper.save(notificationPO);

		logger.trace("Marked notification as read on {}: {}", notification); // NOTE: log after updating unreadCount

		return true;
	}

	public void delete(final String notificationId)
	{
		final I_AD_Note notificationPO = retrieveAD_Note(notificationId);
		if (notificationPO == null)
		{
			return;
		}

		notificationPO.setProcessed(false);
		InterfaceWrapperHelper.delete(notificationPO);
	}

	private I_AD_Note retrieveAD_Note(final UserNotification notification)
	{
		return retrieveAD_Note(notification.getId());
	}
	
	private I_AD_Note retrieveAD_Note(final String notificationId)
	{
		final int adNoteId = extractAD_Note_ID(notificationId);
		if (adNoteId <= 0)
		{
			throw new IllegalArgumentException("No AD_Note_ID found for " + notificationId);
		}

		return InterfaceWrapperHelper.load(adNoteId, I_AD_Note.class);
	}

	private static final int extractAD_Note_ID(final String notificationId)
	{
		return Integer.parseInt(notificationId);
	}
}
