package de.metas.notification.impl;

import java.util.LinkedHashSet;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.UserNotificationsConfig;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Note;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.email.EMail;
import de.metas.email.IMailBL;
import de.metas.email.Mailbox;
import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.i18n.IADMessageDAO;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.spi.INotificationCtxProvider;
import de.metas.notification.spi.impl.CompositeNotificationCtxProvider;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public class NotificationBL implements INotificationBL
{
	private static final Logger logger = LogManager.getLogger(NotificationBL.class);

	private final CompositeNotificationCtxProvider ctxProviders = new CompositeNotificationCtxProvider();

	/** AD_Message to be used when there was no AD_Message provided */
	private static final String MSG_DEFAULT_NOTICE_SUBJECT = "webui.window.notification.caption";

	@Override
	public void notifyUser(@NonNull final UserNotificationRequest request)
	{
		final UserNotificationRequest requestEffective = request.toBuilder()
				.notificationsConfig(resolveUserNotificationsConfig(request))
				.targetRecordDisplayText(resolveTargetRecordDisplayText(request))
				.build();

		notifyUser0(requestEffective);

		final UserNotificationsConfig notificationsConfig = requestEffective.getNotificationsConfig();
		if (notificationsConfig.isNotifyUserInCharge() && notificationsConfig.isUserInChargeSet())
		{
			final UserNotificationsConfig userInChargeNotificationsConfig = findEffectiveUserInChargeConfig(notificationsConfig);
			if (notificationsConfig.getAdUserId() != userInChargeNotificationsConfig.getAdUserId())
			{
				notifyUser0(requestEffective.toBuilder()
						.notificationsConfig(userInChargeNotificationsConfig)
						.build());
			}
		}
	}

	private UserNotificationsConfig resolveUserNotificationsConfig(final UserNotificationRequest request)
	{
		if (request.getNotificationsConfig() != null)
		{
			return request.getNotificationsConfig();
		}

		final IUserBL userBL = Services.get(IUserBL.class);
		return userBL.getUserNotificationsConfig(request.getRecipientUserId());
	}

	private String resolveTargetRecordDisplayText(final UserNotificationRequest request)
	{
		if (request.getTargetRecord() == null)
		{
			return null;
		}
		if (!Check.isEmpty(request.getTargetRecordDisplayText()))
		{
			return request.getTargetRecordDisplayText();
		}

		// Provide more specific information to the user, in case there exists a notification context provider (task 09833)
		final String targetRecordDisplayText = ctxProviders.getTextMessageIfApplies(request.getTargetRecord()).orNull();
		return Check.isEmpty(targetRecordDisplayText, true) ? null : targetRecordDisplayText;
	}

	private String extractSubjectText(final UserNotificationRequest request)
	{
		if (!Check.isEmpty(request.getSubjectPlain()))
		{
			return request.getSubjectPlain();
		}

		if (!Check.isEmpty(request.getSubjectADMessage()))
		{
			return Services.get(IMsgBL.class).getMsg(request.getSubjectADMessage(), request.getSubjectADMessageParams());
		}

		return "";
	}

	private String extractContentText(final UserNotificationRequest request)
	{
		if (!Check.isEmpty(request.getContentPlain()))
		{
			return request.getContentPlain();
		}

		if (!Check.isEmpty(request.getContentADMessage()))
		{
			return Services.get(IMsgBL.class).getMsg(request.getContentADMessage(), request.getContentADMessageParams());
		}

		return "";
	}

	private void notifyUser0(final UserNotificationRequest request)
	{
		final UserNotificationsConfig notificationsConfig = request.getNotificationsConfig();
		if (notificationsConfig.isNotifyByEMail())
		{
			try
			{
				sendMail(request);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed sending email for {}. Trying to Send user notification instead.", request, ex);
				createNotice(request);
			}
		}

		if (notificationsConfig.isNotifyByInternalMessage())
		{
			createNotice(request);
		}
	}

	private UserNotificationsConfig findEffectiveUserInChargeConfig(@NonNull final UserNotificationsConfig userNotificationsConfig)
	{
		final IUserBL userBL = Services.get(IUserBL.class);

		final LinkedHashSet<Integer> seenUserIds = new LinkedHashSet<>();
		UserNotificationsConfig currentNotificationsConfig = userNotificationsConfig;
		while (currentNotificationsConfig.isNotifyUserInCharge() && currentNotificationsConfig.isUserInChargeSet())
		{
			if (!seenUserIds.add(userNotificationsConfig.getAdUserId()))
			{
				throw new AdempiereException("Detected a cycle in the AD_User.AD_User_InCharge_IDs. The AD_User_IDs in question are: " + seenUserIds);
			}
			currentNotificationsConfig = userBL.getUserNotificationsConfig(currentNotificationsConfig.getUserInChargeId());
		}

		return currentNotificationsConfig;
	}

	private void createNotice(final UserNotificationRequest request)
	{
		final Event event = Event.builder()
				.setSummary(extractSubjectText(request))
				.setDetailADMessage(request.getContentADMessage(), request.getContentADMessageParams())
				.addRecipient_User_ID(request.getRecipientUserId())
				.setRecord(request.getTargetRecord())
				.setSuggestedWindowId(request.getTargetADWindowId())
				.build();

		final Topic topic = null; // TODO: extract topic from request
		
		Services.get(IEventBusFactory.class)
				.getEventBus(topic)
				.postEvent(event);
	}

	private void createNotice_OLD(final UserNotificationRequest request)
	{
		final IADMessageDAO msgDAO = Services.get(IADMessageDAO.class);
		final int adMessageId = msgDAO.retrieveIdByValue(Env.getCtx(), request.getSubjectADMessageOr(MSG_DEFAULT_NOTICE_SUBJECT));

		final UserNotificationsConfig notificationsConfig = request.getNotificationsConfig();

		final I_AD_Note note = InterfaceWrapperHelper.newInstance(I_AD_Note.class);
		note.setAD_User_ID(notificationsConfig.getAdUserId());
		note.setAD_Message_ID(adMessageId);
		note.setAD_Org_ID(notificationsConfig.getAdOrgId());
		note.setTextMsg(extractContentText(request));

		final ITableRecordReference targetRecord = request.getTargetRecord();
		if (targetRecord != null)
		{
			note.setAD_Table_ID(targetRecord.getAD_Table_ID());
			note.setRecord_ID(targetRecord.getRecord_ID());
			final int targetADWindowId = request.getTargetADWindowId();
			if (targetADWindowId > 0)
			{
				note.setAD_Window_ID(targetADWindowId);
			}
		}

		InterfaceWrapperHelper.save(note);
	}

	private void sendMail(final UserNotificationRequest request)
	{
		final UserNotificationsConfig notificationsConfig = request.getNotificationsConfig();
		final Mailbox mailbox = resolveMailbox(notificationsConfig);

		final String subject = extractSubjectText(request);

		final boolean html = false;
		final String content = extractMailContent(request);

		final IMailBL mailBL = Services.get(IMailBL.class);
		final EMail mail = mailBL.createEMail(Env.getCtx(),
				mailbox,
				notificationsConfig.getEmail(),
				subject,
				content,
				html);
		mailBL.send(mail);
	}

	private Mailbox resolveMailbox(@NonNull final UserNotificationsConfig notificationsConfig)
	{
		final IMailBL mailBL = Services.get(IMailBL.class);
		final IClientDAO clientDAO = Services.get(IClientDAO.class);
		final I_AD_Client adClient = clientDAO.retriveClient(Env.getCtx(), notificationsConfig.getAdClientId());
		final Mailbox mailbox = mailBL.findMailBox(
				adClient,
				notificationsConfig.getAdOrgId(),
				0,  // AD_Process_ID
				null,  // C_DocType - Task FRESH-203 this shall work as before
				null,  // customType
				null); // sender
		Check.assumeNotNull(mailbox, "IMailbox for adClient={}, AD_Org_ID={}", adClient, notificationsConfig.getAdOrgId());
		return mailbox;
	}

	private String extractMailContent(final UserNotificationRequest request)
	{
		final StringBuilder mailBody = new StringBuilder();

		final String messageText = extractContentText(request);
		if (!Check.isEmpty(messageText))
		{
			mailBody.append(messageText);
		}

		final String targetRecordDisplayText = resolveTargetRecordDisplayText(request);
		if (!Check.isEmpty(targetRecordDisplayText))
		{
			if (mailBody.length() > 0)
			{
				mailBody.append("\n");
			}
			mailBody.append(targetRecordDisplayText);
		}

		return mailBody.toString();
	}

	@Override
	public void addCtxProvider(final INotificationCtxProvider ctxProvider)
	{
		ctxProviders.addCtxProvider(ctxProvider);
	}

	@Override
	public void setDefaultCtxProvider(final INotificationCtxProvider defaultCtxProvider)
	{
		ctxProviders.setDefaultCtxProvider(defaultCtxProvider);
	}
}
