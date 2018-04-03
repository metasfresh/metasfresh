package de.metas.notification.impl;

import java.util.LinkedHashSet;
import java.util.Properties;

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

import com.google.common.base.Joiner;

import de.metas.email.EMail;
import de.metas.email.IMailBL;
import de.metas.email.Mailbox;
import de.metas.i18n.IADMessageDAO;
import de.metas.i18n.IMsgBL;
import de.metas.notification.INotificationBL;
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
	private final CompositeNotificationCtxProvider ctxProviders = new CompositeNotificationCtxProvider();

	@Override
	public void notifyUser(
			final int recipientUserId,
			final String adMessage,
			final String messageText,
			final ITableRecordReference referencedRecord)
	{
		final IUserBL userBL = Services.get(IUserBL.class);
		final UserNotificationsConfig notificationsConfig = userBL.getUserNotificationsConfig(recipientUserId);

		final String messageToUse = Joiner.on(" ")
				.skipNulls()
				.join(Check.isEmpty(messageText, true) ? null : messageText.trim(),
						getTextMessageOrNull(referencedRecord));

		notifyUser0(notificationsConfig, adMessage, messageToUse, referencedRecord);

		if (notificationsConfig.isNotifyUserInCharge() && notificationsConfig.isUserInChargeSet())
		{
			final UserNotificationsConfig userInChargeNotificationsConfig = findEffectiveUserInChargeConfig(notificationsConfig);
			if (notificationsConfig.getAdUserId() != userInChargeNotificationsConfig.getAdUserId())
			{
				notifyUser0(userInChargeNotificationsConfig, adMessage, messageText, referencedRecord);
			}
		}
	}

	private void notifyUser0(
			final UserNotificationsConfig notificationsConfig,
			final String adMessage,
			final String messageText,
			final ITableRecordReference referencedRecord)
	{
		if (notificationsConfig.isNotifyByEMail())
		{
			try
			{
				sendMail(notificationsConfig, adMessage, messageText, referencedRecord);
			}
			catch (final Exception e)
			{
				final String messageText2 = "An attempt to mail the following text failed with " + e.getClass() + ": " + e.getLocalizedMessage() + ":\n"
						+ messageText;
				createNotice(notificationsConfig, adMessage, messageText2, referencedRecord);
			}
		}
		if (notificationsConfig.isNotifyByInternalMessage())
		{
			createNotice(notificationsConfig, adMessage, messageText, referencedRecord);
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

	private void createNotice(
			final UserNotificationsConfig userNotificationsConfig,
			final String adMessage,
			final String messageText,
			final ITableRecordReference referencedRecord)
	{
		final IADMessageDAO msgDAO = Services.get(IADMessageDAO.class);
		final int adMessageId = msgDAO.retrieveIdByValue(Env.getCtx(), adMessage);

		final I_AD_Note note = InterfaceWrapperHelper.newInstance(I_AD_Note.class);
		note.setAD_User_ID(userNotificationsConfig.getAdUserId());
		note.setAD_Message_ID(adMessageId);
		note.setAD_Org_ID(userNotificationsConfig.getAdOrgId());
		note.setTextMsg(messageText);

		if (referencedRecord != null)
		{
			note.setAD_Table_ID(referencedRecord.getAD_Table_ID());
			note.setRecord_ID(referencedRecord.getRecord_ID());
		}

		InterfaceWrapperHelper.save(note);
	}

	private void sendMail(
			final UserNotificationsConfig userNotificationsConfig,
			final String adMessage,
			final String messageText,
			final ITableRecordReference referencedRecord)
	{
		final IMailBL mailBL = Services.get(IMailBL.class);
		final IClientDAO clientDAO = Services.get(IClientDAO.class);
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final Properties ctx = Env.getCtx();
		final String subject = msgBL.getMsg(ctx, adMessage);

		final I_AD_Client adClient = clientDAO.retriveClient(ctx, userNotificationsConfig.getAdClientId());
		final Mailbox mailBox = mailBL.findMailBox(
				adClient,
				userNotificationsConfig.getAdOrgId(),
				0,  // AD_Process_ID
				null,  // C_DocType - Task FRESH-203 this shall work as before
				null,  // customType
				null); // sender
		Check.assumeNotNull(mailBox, "IMailbox for adClient={}, AD_Org_ID={}", adClient, userNotificationsConfig.getAdOrgId());

		final StringBuilder mailBody = new StringBuilder();
		mailBody.append("\n" + messageText + "\n");

		if (referencedRecord != null)
		{
			mailBody.append(msgBL.parseTranslation(ctx, "@" + referencedRecord.getTableName() + "_ID@: "));
			mailBody.append(referencedRecord.getRecord_ID());
		}

		final EMail mail = mailBL.createEMail(ctx, mailBox, userNotificationsConfig.getEmail(), subject, mailBody.toString(), false);
		mailBL.send(mail);
	}

	private String getTextMessageOrNull(final ITableRecordReference referencedRecord)
	{
		if (referencedRecord == null)
		{
			return null;
		}

		// Provide more specific information to the user, in case there exists a notification context provider (task 09833)
		final String referencedRecordAsString = ctxProviders.getTextMessageIfApplies(referencedRecord).orNull();
		return Check.isEmpty(referencedRecordAsString, true) ? null : referencedRecordAsString;
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
