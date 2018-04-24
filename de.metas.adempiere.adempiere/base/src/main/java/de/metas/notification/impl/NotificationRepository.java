package de.metas.notification.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Note;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.attachments.IAttachmentBL;
import de.metas.i18n.IADMessageDAO;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationRepository;
import de.metas.notification.UserNotification;
import de.metas.notification.UserNotification.UserNotificationBuilder;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationTargetType;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class NotificationRepository implements INotificationRepository
{
	private static final Logger logger = LogManager.getLogger(NotificationRepository.class);

	/** AD_Message to be used when there was no AD_Message provided */
	private static final String DEFAULT_AD_MESSAGE = "webui.window.notification.caption";

	private final ObjectMapper jsonMapper;

	public NotificationRepository()
	{
		jsonMapper = new ObjectMapper();
		jsonMapper.findAndRegisterModules();
	}

	@Override
	public UserNotification save(@NonNull final UserNotificationRequest request)
	{
		final I_AD_Note notificationPO = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_Note.class);
		notificationPO.setAD_User_ID(request.getRecipientUserId());
		notificationPO.setIsImportant(request.isImportant());

		//
		// contentADMessage -> AD_Message
		int adMessageId = -1;
		final String detailADMessage = request.getContentADMessage();
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
		// contentADMessageParams
		final List<Object> detailADMessageParams = request.getContentADMessageParams();
		if (detailADMessageParams != null && !detailADMessageParams.isEmpty())
		{
			try
			{
				final String detailADMessageParamsJSON = jsonMapper.writeValueAsString(detailADMessageParams);
				notificationPO.setAD_Message_ParamsJSON(detailADMessageParamsJSON);
			}
			catch (final JsonProcessingException e)
			{
				throw new AdempiereException(e);
			}
		}

		//
		// contentPlain
		final String detailPlain = request.getContentPlain();
		notificationPO.setTextMsg(detailPlain);

		//
		// Target: window (from document record)
		final ITableRecordReference targetRecord = request.getTargetRecord();
		if (targetRecord != null)
		{
			notificationPO.setAD_Table_ID(targetRecord.getAD_Table_ID());
			notificationPO.setRecord_ID(targetRecord.getRecord_ID());
			notificationPO.setAD_Window_ID(request.getTargetADWindowId());
		}

		//
		InterfaceWrapperHelper.save(notificationPO);

		final List<Resource> attachments = request.getAttachments();
		if (!attachments.isEmpty())
		{
			final IAttachmentBL attachmentBL = Services.get(IAttachmentBL.class);
			attachmentBL.addEntriesFromResources(notificationPO, attachments);
		}

		return toUserNotification(notificationPO);
	}

	private UserNotification toUserNotificationNoFail(@NonNull final I_AD_Note notificationPO)
	{
		try
		{
			return toUserNotification(notificationPO);
		}
		catch (Exception ex)
		{
			logger.warn("Failed creating user notification object from {}", notificationPO, ex);
			return null;
		}
	}

	private UserNotification toUserNotification(@NonNull final I_AD_Note notificationPO)
	{
		final UserNotificationBuilder builder = UserNotification.builder()
				.id(notificationPO.getAD_Note_ID())
				.timestamp(notificationPO.getCreated().getTime())
				.important(notificationPO.isImportant())
				.recipientUserId(notificationPO.getAD_User_ID())
				.read(notificationPO.isProcessed());

		//
		// detailADMessage
		final int detailADMessageId = notificationPO.getAD_Message_ID();
		if (detailADMessageId > 0)
		{
			final String detailADMessage = Services.get(IADMessageDAO.class).retrieveValueById(detailADMessageId);
			builder.detailADMessage(detailADMessage);
		}
		else
		{
			builder.detailADMessage(DEFAULT_AD_MESSAGE);
		}

		//
		// detailADMessageParams
		final String detailADMessageParamsJSON = notificationPO.getAD_Message_ParamsJSON();
		if (!Check.isEmpty(detailADMessageParamsJSON, true))
		{
			try
			{
				@SuppressWarnings("unchecked")
				final List<Object> detailADMessageParams = jsonMapper.readValue(detailADMessageParamsJSON, List.class);
				builder.detailADMessageParams(detailADMessageParams);
			}
			catch (final Exception ex)
			{
				throw AdempiereException.wrapIfNeeded(ex)
						.setParameter("json", detailADMessageParamsJSON);
			}
		}

		//
		// detailPlain
		builder.detailPlain(notificationPO.getTextMsg());

		//
		// Target record
		final int adTableId = notificationPO.getAD_Table_ID();
		if (adTableId > 0)
		{
			builder.targetType(UserNotificationTargetType.Window)
					.targetRecord(TableRecordReference.of(adTableId, notificationPO.getRecord_ID()))
					.targetWindowId(notificationPO.getAD_Window_ID());
		}
		else
		{
			builder.targetType(UserNotificationTargetType.None);
		}

		return builder.build();
	}

	private IQueryBuilder<I_AD_Note> retrieveNotesByUserId(final int adUserId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Note.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Note.COLUMN_AD_User_ID, adUserId);
	}

	@Override
	public List<UserNotification> getByUserId(final int adUserId, final int limit)
	{
		return retrieveNotesByUserId(adUserId)
				.orderByDescending(I_AD_Note.COLUMNNAME_AD_Note_ID)
				.setLimit(limit)
				.create()
				.stream(I_AD_Note.class)
				.map(this::toUserNotificationNoFail)
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());
	}

	public boolean markAsRead(@NonNull final UserNotification notification)
	{
		final boolean alreadyRead = notification.setRead(true);
		if (alreadyRead)
		{
			logger.trace("Skip marking notification as read because it's already read: {}", notification);
			return false;
		}

		return markAsReadById(notification.getId());
	}

	@Override
	public boolean markAsReadById(final int notificationId)
	{
		final I_AD_Note notificationPO = retrieveAD_Note(notificationId);
		if (notificationPO == null)
		{
			return false;
		}

		return markAsRead(notificationPO);
	}

	private boolean markAsRead(@NonNull final I_AD_Note notificationPO)
	{
		if (notificationPO.isProcessed())
		{
			return false;
		}

		if (!markAsReadNoSave(notificationPO))
		{
			return false;
		}

		InterfaceWrapperHelper.save(notificationPO);
		logger.trace("Marked notification read: {}", notificationPO);
		return true;
	}

	private boolean markAsReadNoSave(@NonNull final I_AD_Note notificationPO)
	{
		if (notificationPO.isProcessed())
		{
			return false;
		}
		notificationPO.setProcessed(true);
		return true;
	}

	@Override
	public void markAllAsReadByUserId(final int adUserId)
	{
		retrieveNotesByUserId(adUserId)
				.create()
				.update(this::markAsReadNoSave);
	}

	private I_AD_Note retrieveAD_Note(final int adNoteId)
	{
		Check.assumeGreaterThanZero(adNoteId, "adNoteId");
		return InterfaceWrapperHelper.loadOutOfTrx(adNoteId, I_AD_Note.class);
	}

	@Override
	public boolean deleteById(final int notificationId)
	{
		final I_AD_Note notificationPO = retrieveAD_Note(notificationId);
		if (notificationPO == null)
		{
			return false;
		}

		notificationPO.setProcessed(false);
		InterfaceWrapperHelper.delete(notificationPO);
		return true;
	}

	@Override
	public int getUnreadCountByUserId(final int adUserId)
	{
		return retrieveNotesByUserId(adUserId)
				.addEqualsFilter(I_AD_Note.COLUMN_Processed, false)
				.create()
				.count();
	}

	@Override
	public int getTotalCountByUserId(final int adUserId)
	{
		return retrieveNotesByUserId(adUserId)
				.create()
				.count();
	}
}
