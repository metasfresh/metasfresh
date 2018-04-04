package de.metas.notification.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.PlainContextAware;
import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.NotificationGroupName;
import org.adempiere.user.api.UserNotificationsConfig;
import org.adempiere.user.api.UserNotificationsGroup;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.apache.ecs.ClearElement;
import org.apache.ecs.xhtml.body;
import org.apache.ecs.xhtml.br;
import org.apache.ecs.xhtml.html;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocumentBL;
import de.metas.email.EMail;
import de.metas.email.IMailBL;
import de.metas.email.Mailbox;
import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
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

	private static final String SYSCONFIG_WEBUI_FRONTEND_URL = "webui.frontend.url";
	private static final String SYSCONFIG_WEBUI_FRONTEND_DOCUMENT_PATH = "webui.frontend.documentPath";
	private static final String DEFAULT_WEBUI_FRONTEND_DOCUMENT_PATH = "/window/{windowId}/{recordId}";

	private final CompositeNotificationCtxProvider ctxProviders = new CompositeNotificationCtxProvider();

	@Override
	public void notifyUserAfterCommit(@NonNull final UserNotificationRequest request)
	{
		notifyUserAfterCommit(ImmutableList.of(request));
	}

	@Override
	public void notifyUserAfterCommit(@NonNull final List<UserNotificationRequest> requests)
	{
		if (requests.isEmpty())
		{
			return;
		}

		final ImmutableList<UserNotificationRequest> requestsEffective = ImmutableList.copyOf(requests);

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(true)
				.registerHandlingMethod(innerTrx -> notifyUser(requestsEffective));
	}

	public void notifyUser(@NonNull final List<UserNotificationRequest> requests)
	{
		requests.forEach(this::notifyUser);
	}

	@Override
	public void notifyUser(@NonNull final UserNotificationRequest request)
	{
		final UserNotificationRequest requestEffective = request.toBuilder()
				.targetRecordDisplayText(resolveTargetRecordDisplayText(request))
				.targetADWindowId(resolveTargetWindowId(request))
				.build();

		final List<UserNotificationsConfig> notificationsConfigs = extractEffectiveNotificationsConfigs(requestEffective);
		notificationsConfigs.forEach(notificationsConfig -> notifyUser0(requestEffective.deriveByNotificationsConfig(notificationsConfig)));
	}

	private String resolveTargetRecordDisplayText(final UserNotificationRequest request)
	{
		final ITableRecordReference targetRecord = request.getTargetRecord();
		if (targetRecord == null)
		{
			return null;
		}

		//
		if (!Check.isEmpty(request.getTargetRecordDisplayText()))
		{
			return request.getTargetRecordDisplayText();
		}

		// Provide more specific information to the user, in case there exists a notification context provider (task 09833)
		final String targetRecordDisplayText = ctxProviders.getTextMessageIfApplies(targetRecord).orNull();
		if (!Check.isEmpty(targetRecordDisplayText, true))
		{
			return targetRecordDisplayText;
		}

		//
		try
		{
			final Object targetRecordModel = targetRecord.getModel(PlainContextAware.createUsingOutOfTransaction());
			final String documentNo = Services.get(IDocumentBL.class).getDocumentNo(targetRecordModel);
			return documentNo;
		}
		catch (final Exception e)
		{
			logger.info("Failed retrieving record for " + targetRecord, e);
		}

		//
		return "#" + targetRecord.getRecord_ID();
	}

	private int resolveTargetWindowId(final UserNotificationRequest request)
	{
		if (request.getTargetADWindowId() > 0)
		{
			return request.getTargetADWindowId();
		}
		if (request.getTargetRecord() == null)
		{
			return -1;
		}

		final RecordZoomWindowFinder recordWindowFinder = RecordZoomWindowFinder.newInstance(request.getTargetRecord());
		return recordWindowFinder.findAD_Window_ID();
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

	private String extractContentText(final UserNotificationRequest request, final boolean html)
	{
		if (!Check.isEmpty(request.getContentPlain()))
		{
			return request.getContentPlain();
		}

		if (!Check.isEmpty(request.getContentADMessage()))
		{
			return prepareMessageFormatter(request)
					.html(html)
					.format(request.getContentADMessage(), request.getContentADMessageParams());
		}

		return "";
	}

	private void notifyUser0(final UserNotificationRequest request)
	{
		final UserNotificationsConfig notificationsConfig = request.getNotificationsConfig();
		final UserNotificationsGroup notificationsGroup = notificationsConfig.getGroupByName(request.getNotificationGroupName());
		if (notificationsGroup.isNotifyByEMail())
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

		if (notificationsGroup.isNotifyByInternalMessage())
		{
			createNotice(request);
		}
	}

	private List<UserNotificationsConfig> extractEffectiveNotificationsConfigs(@NonNull final UserNotificationRequest request)
	{
		final UserNotificationsConfig notificationsConfig;
		if (request.getNotificationsConfig() != null)
		{
			notificationsConfig = request.getNotificationsConfig();
		}
		else
		{
			final IUserBL userBL = Services.get(IUserBL.class);
			notificationsConfig = userBL.getUserNotificationsConfig(request.getRecipientUserId());
		}

		final NotificationGroupName groupName = request.getNotificationGroupName();
		return extractEffectiveNotificationsConfigs(notificationsConfig, groupName);
	}

	private List<UserNotificationsConfig> extractEffectiveNotificationsConfigs(
			@NonNull final UserNotificationsConfig notificationsConfig,
			@NonNull final NotificationGroupName groupName)
	{
		final List<UserNotificationsConfig> result = new ArrayList<>();

		final IUserBL userBL = Services.get(IUserBL.class);
		final LinkedHashSet<Integer> seenUserIds = new LinkedHashSet<>();
		UserNotificationsConfig currentNotificationsConfig = notificationsConfig;

		while (currentNotificationsConfig != null)
		{
			if (!seenUserIds.add(currentNotificationsConfig.getUserId()))
			{
				logger.warn("Detected a cycle in the AD_User.AD_User_InCharge_IDs. The AD_User_IDs in question are {}, initialConfig={}, groupName={}", seenUserIds, notificationsConfig, groupName);
				break;
			}

			final UserNotificationsGroup currentNotificationsGroup = currentNotificationsConfig.getGroupByName(groupName);
			if (currentNotificationsGroup.hasAnyNotificationTypeExceptUserInCharge())
			{
				result.add(currentNotificationsConfig);
			}

			if (!currentNotificationsGroup.isNotifyUserInCharge())
			{
				currentNotificationsConfig = null;
				break;
			}

			if (!currentNotificationsGroup.isUserInChargeSet())
			{
				currentNotificationsConfig = null;
				break;
			}

			currentNotificationsConfig = userBL.getUserNotificationsConfig(currentNotificationsGroup.getUserInChargeId());
		}

		return result;
	}

	private void createNotice(final UserNotificationRequest request)
	{
		try
		{
			final Event event = Event.builder()
					.setSummary(extractSubjectText(request))
					.setDetailADMessage(request.getContentADMessage(), request.getContentADMessageParams())
					.addRecipient_User_ID(request.getRecipientUserId())
					.setRecord(request.getTargetRecord())
					.setSuggestedWindowId(request.getTargetADWindowId())
					.build();

			Services.get(IEventBusFactory.class)
					.getEventBus(request.getTopic())
					.postEvent(event);
		}
		catch (Exception ex)
		{
			logger.warn("Failed sending notification: {}", request, ex);
		}
	}

	private void sendMail(final UserNotificationRequest request)
	{
		final UserNotificationsConfig notificationsConfig = request.getNotificationsConfig();
		final Mailbox mailbox = findMailbox(notificationsConfig);

		final boolean html = true;
		final String content = extractMailContent(request);

		String subject = extractSubjectText(request);
		if (Check.isEmpty(subject, true) && content != null)
		{
			subject = extractContentText(request, /* html */false);
			if (subject.length() > 100)
			{
				subject = subject.substring(0, 100 - 3) + "...";
			}
		}

		final IMailBL mailBL = Services.get(IMailBL.class);
		final EMail mail = mailBL.createEMail(Env.getCtx(),
				mailbox,
				notificationsConfig.getEmail(),
				subject,
				content,
				html);
		mailBL.send(mail);
	}

	private Mailbox findMailbox(@NonNull final UserNotificationsConfig notificationsConfig)
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
		final body htmlBody = new body();
		final String htmlBodyString = extractContentText(request, /* html */true);
		if (!Check.isEmpty(htmlBodyString))
		{
			Splitter.on("\n")
					.splitToList(htmlBodyString.trim())
					.forEach(htmlLine -> htmlBody.addElement(new ClearElement(htmlLine)).addElement(new br()));
		}

		return new html().addElement(htmlBody).toString();
	}

	private NotificationMessageFormatter prepareMessageFormatter(final UserNotificationRequest request)
	{
		final NotificationMessageFormatter formatter = NotificationMessageFormatter.newInstance()
				.webuiDocumentUrl(getDocumentUrl());

		final ITableRecordReference targetRecord = request.getTargetRecord();
		if (targetRecord != null)
		{
			final String targetRecordDisplayText = request.getTargetRecordDisplayText();
			if (!Check.isEmpty(targetRecordDisplayText))
			{
				formatter.recordDisplayText(targetRecord, targetRecordDisplayText);
			}

			final int targetWindowId = request.getTargetADWindowId();
			if (targetWindowId > 0)
			{
				formatter.recordWindowId(targetRecord, targetWindowId);
			}
		}

		return formatter;
	}

	private String getDocumentUrl()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final String url = sysConfigBL.getValue(SYSCONFIG_WEBUI_FRONTEND_URL, "");
		if (Check.isEmpty(url, true) || "-".equals(url))
		{
			return null;
		}

		final String documentPath = sysConfigBL.getValue(SYSCONFIG_WEBUI_FRONTEND_DOCUMENT_PATH, DEFAULT_WEBUI_FRONTEND_DOCUMENT_PATH);

		return url + documentPath;
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
