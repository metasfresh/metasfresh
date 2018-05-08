package de.metas.notification;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.user.api.NotificationGroupName;
import org.adempiere.user.api.RoleNotificationsConfig;
import org.adempiere.user.api.UserNotificationsConfig;
import org.adempiere.user.api.UserNotificationsGroup;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.apache.commons.lang3.StringUtils;
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
import de.metas.event.IEventBusFactory;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.notification.spi.IRecordTextProvider;
import de.metas.notification.spi.impl.NullRecordTextProvider;
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

public class NotificationSenderTemplate
{
	// services
	private static final Logger logger = LogManager.getLogger(NotificationSenderTemplate.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IUserDAO usersRepo = Services.get(IUserDAO.class);
	private final IUserBL userBL = Services.get(IUserBL.class);
	private final IRoleDAO rolesRepo = Services.get(IRoleDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final INotificationRepository notificationsRepo = Services.get(INotificationRepository.class);
	private final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);
	private final IMailBL mailBL = Services.get(IMailBL.class);
	private final IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_WEBUI_FRONTEND_URL = "webui.frontend.url";
	private static final String SYSCONFIG_WEBUI_FRONTEND_DOCUMENT_PATH = "webui.frontend.documentPath";
	private static final String DEFAULT_WEBUI_FRONTEND_DOCUMENT_PATH = "/window/{windowId}/{recordId}";

	private IRecordTextProvider recordTextProvider = NullRecordTextProvider.instance;

	public void setRecordTextProvider(@NonNull final IRecordTextProvider recordTextProvider)
	{
		this.recordTextProvider = recordTextProvider;
	}

	public void sendAfterCommit(@NonNull final UserNotificationRequest request)
	{
		sendAfterCommit(ImmutableList.of(request));
	}

	public void sendAfterCommit(@NonNull final List<UserNotificationRequest> requests)
	{
		if (requests.isEmpty())
		{
			return;
		}

		final ImmutableList<UserNotificationRequest> requestsEffective = ImmutableList.copyOf(requests);

		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(true)
				.registerHandlingMethod(innerTrx -> send(requestsEffective));
	}

	private void send(@NonNull final List<UserNotificationRequest> requests)
	{
		requests.forEach(this::send);
	}

	public void send(@NonNull final UserNotificationRequest request)
	{
		logger.trace("Prepare sending notification: {}", request);
		Stream.of(resolve(request))
				.flatMap(this::explodeByUser)
				.flatMap(this::explodeByEffectiveNotificationsConfigs)
				.forEach(this::send0);
	}

	private void send0(final UserNotificationRequest request)
	{
		logger.trace("Sending notification: {}", request);

		final UserNotificationsConfig notificationsConfig = request.getNotificationsConfig();
		final UserNotificationsGroup notificationsGroup = notificationsConfig.getGroupByName(request.getNotificationGroupName());
		boolean notifyByInternalMessage = notificationsGroup.isNotifyByInternalMessage();

		if (notificationsGroup.isNotifyByEMail() && !request.isNoEmail())
		{
			try
			{
				sendMail(request);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed sending email for {}. Trying to Send user notification instead.", request, ex);
				notifyByInternalMessage = true;
			}
		}

		if (notifyByInternalMessage)
		{
			sendNotice(request);
		}
	}

	private UserNotificationRequest resolve(@NonNull final UserNotificationRequest request)
	{
		return request.toBuilder()
				.targetRecordDisplayText(resolveTargetRecordDisplayText(request))
				.targetADWindowId(resolveTargetWindowId(request))
				.build();
	}

	private Stream<UserNotificationRequest> explodeByUser(final UserNotificationRequest request)
	{
		return explodeRecipients(request.getRecipient())
				.map(request::deriveByRecipient);
	}

	private Stream<Recipient> explodeRecipients(final Recipient recipient)
	{
		if (recipient.isAllUsers())
		{
			return usersRepo.retrieveSystemUserIds()
					.stream()
					.map(Recipient::user);
		}
		else if (recipient.isUser())
		{
			return Stream.of(recipient);
		}
		else if (recipient.isRole())
		{
			final int roleId = recipient.getRoleId();
			return rolesRepo.retrieveUserIdsForRoleId(roleId)
					.stream()
					.map(userId -> Recipient.userAndRole(userId, roleId));
		}
		else
		{
			throw new AdempiereException("Recipient type not supported: " + recipient);
		}
	}

	private Stream<UserNotificationRequest> explodeByEffectiveNotificationsConfigs(final UserNotificationRequest request)
	{
		return extractEffectiveNotificationsConfigs(request)
				.stream()
				.map(request::deriveByNotificationsConfig);
	}

	private String resolveTargetRecordDisplayText(@NonNull final UserNotificationRequest request)
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
		final String targetRecordDisplayText = recordTextProvider.getTextMessageIfApplies(targetRecord).orNull();
		if (!Check.isEmpty(targetRecordDisplayText, true))
		{
			return targetRecordDisplayText;
		}

		//
		try
		{
			final Object targetRecordModel = targetRecord.getModel(PlainContextAware.createUsingOutOfTransaction());
			final String documentNo = documentBL.getDocumentNo(targetRecordModel);
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
			final String adLanguage = request.getADLanguageOrGet(Env::getADLanguageOrBaseLanguage);
			return msgBL.getMsg(adLanguage, request.getSubjectADMessage(), request.getSubjectADMessageParams());
		}

		return "";
	}

	private static String extractSubjectFromContent(final String content)
	{
		if (Check.isEmpty(content, true))
		{
			return "";
		}

		String subject = content.trim();
		final int idx = subject.indexOf("\n");
		if (idx > 0)
		{
			subject = subject.substring(0, idx).trim();
		}

		return StringUtils.abbreviate(subject, 100);
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
					.adLanguage(request.getADLanguageOrGet(Env::getADLanguageOrBaseLanguage))
					.format(request.getContentADMessage(), request.getContentADMessageParams());
		}

		return "";
	}

	private List<UserNotificationsConfig> extractEffectiveNotificationsConfigs(@NonNull final UserNotificationRequest request)
	{
		final Recipient recipient = request.getRecipient();
		Check.assume(!recipient.isAllUsers(), "request shall not have broadcast flag set: {}", request);

		UserNotificationsConfig notificationsConfig;
		if (request.getNotificationsConfig() != null)
		{
			notificationsConfig = request.getNotificationsConfig();
		}
		else if (recipient.isUser())
		{

			notificationsConfig = userBL.getUserNotificationsConfig(recipient.getUserId());

			if (recipient.isRoleIdSet())
			{
				final RoleNotificationsConfig roleNotificationsConfig = userBL.getRoleNotificationsConfig(recipient.getRoleId());
				notificationsConfig = notificationsConfig.deriveWithNotificationGroups(roleNotificationsConfig.getNotificationGroups());
			}
		}
		else
		{
			throw new AdempiereException("Recipient not supported: " + recipient);
		}

		final NotificationGroupName groupName = request.getNotificationGroupName();
		return extractEffectiveNotificationsConfigs(notificationsConfig, groupName);
	}

	private List<UserNotificationsConfig> extractEffectiveNotificationsConfigs(
			@NonNull final UserNotificationsConfig notificationsConfig,
			@NonNull final NotificationGroupName groupName)
	{
		final List<UserNotificationsConfig> result = new ArrayList<>();

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
			if (currentNotificationsGroup.hasAnyNotificationTypesExceptUserInCharge())
			{
				result.add(currentNotificationsConfig);
			}

			if (!currentNotificationsGroup.isNotifyUserInCharge())
			{
				currentNotificationsConfig = null;
				break;
			}

			if (!currentNotificationsConfig.isUserInChargeSet())
			{
				currentNotificationsConfig = null;
				break;
			}

			currentNotificationsConfig = userBL.getUserNotificationsConfig(currentNotificationsConfig.getUserInChargeId());
		}

		return result;
	}

	private void sendNotice(final UserNotificationRequest request)
	{
		try
		{
			final UserNotification notification = notificationsRepo.save(request);

			eventBusFactory
					.getEventBus(request.getTopic())
					.postEvent(UserNotificationUtils.toEvent(notification));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed saving and sending notification: {}", request, ex);
		}
	}

	private void sendMail(final UserNotificationRequest request)
	{
		final UserNotificationsConfig notificationsConfig = request.getNotificationsConfig();
		final Mailbox mailbox = findMailbox(notificationsConfig);

		final boolean html = true;
		final String content = extractMailContent(request);

		String subject = extractSubjectText(request);
		if (Check.isEmpty(subject, true))
		{
			subject = extractSubjectFromContent(extractContentText(request, /* html */false));
		}

		final EMail mail = mailBL.createEMail(Env.getCtx(),
				mailbox,
				notificationsConfig.getEmail(),
				subject,
				content,
				html);
		request.getAttachments().forEach(mail::addAttachment);
		mailBL.send(mail);
	}

	private Mailbox findMailbox(@NonNull final UserNotificationsConfig notificationsConfig)
	{
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
		final String url = sysConfigBL.getValue(SYSCONFIG_WEBUI_FRONTEND_URL, "");
		if (Check.isEmpty(url, true) || "-".equals(url))
		{
			return null;
		}

		final String documentPath = sysConfigBL.getValue(SYSCONFIG_WEBUI_FRONTEND_DOCUMENT_PATH, DEFAULT_WEBUI_FRONTEND_DOCUMENT_PATH);

		return url + documentPath;
	}

}
