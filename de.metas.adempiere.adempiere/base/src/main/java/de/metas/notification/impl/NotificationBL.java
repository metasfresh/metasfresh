package de.metas.notification.impl;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.user.api.IUserBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IMsgDAO;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_User;

import de.metas.email.EMail;
import de.metas.email.IMailBL;
import de.metas.email.Mailbox;
import de.metas.notification.INotificationBL;
import de.metas.notification.spi.INotificationCtxProvider;
import de.metas.notification.spi.impl.CompositePrintingNotificationCtxProvider;

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
	private final CompositePrintingNotificationCtxProvider ctxProviders = new CompositePrintingNotificationCtxProvider();

	@Override
	public void notifyUser(final I_AD_User recipient,
			final String adMessage,
			final String messageText,
			final ITableRecordReference referencedRecord)
	{
		// task 09833
		// Provide more specific information to the user, in case there exists a notification context provider
		final String specificInfo = ctxProviders.getTextMessageIfApplies(referencedRecord).orNull();
		final StringBuilder detailedMsgText = new StringBuilder();
		detailedMsgText.append(messageText);
		if (specificInfo != null)
		{
			if (!messageText.isEmpty())
			{
				detailedMsgText.append(" ");
			}
			detailedMsgText.append(specificInfo);
		}

		final String messageToUse = detailedMsgText.toString();

		notifyUser0(recipient, adMessage, messageToUse, referencedRecord, new HashSet<Integer>());
	}

	private void notifyUser0(final I_AD_User recipient,
			final String adMessage,
			final String messageText,
			final ITableRecordReference referencedRecord,
			final Set<Integer> userIds)
	{
		final IUserBL userBL = Services.get(IUserBL.class);

		if (userBL.isNotifyUserIncharge(recipient))
		{
			final I_AD_User userIncharge = InterfaceWrapperHelper.create(recipient, de.metas.adempiere.model.I_AD_User.class)
					.getAD_User_InCharge();
			Check.errorUnless(userIds.add(userIncharge.getAD_User_ID()), "Detected a cycle in the AD_User.AD_User_InCharge_IDs. The AD_User_IDs in question are {}", userIds);
			notifyUser0(userIncharge, adMessage, messageText, referencedRecord, userIds);
		}
		if (userBL.isNotificationEMail(recipient))
		{
			try
			{
				sendMail(recipient, adMessage, messageText, referencedRecord);
			}
			catch (Exception e)
			{
				final String messageText2 = "An attempt to mail the following text failed with " + e.getClass() + ": " + e.getLocalizedMessage() + ":\n"
						+ messageText;
				createNotice(recipient, adMessage, messageText2, referencedRecord);
			}
		}
		if (userBL.isNotificationNote(recipient))
		{
			createNotice(recipient, adMessage, messageText, referencedRecord);
		}
	}

	private void createNotice(final I_AD_User recipient,
			final String adMessage,
			final String messageText,
			final ITableRecordReference referencedRecord)
	{
		final IMsgDAO msgDAO = Services.get(IMsgDAO.class);

		final int adMessageID = msgDAO.retrieveMessageId(adMessage);

		final I_AD_Note note = InterfaceWrapperHelper.newInstance(I_AD_Note.class, recipient);
		note.setAD_User(recipient);
		note.setAD_Message_ID(adMessageID);
		note.setAD_Org_ID(recipient.getAD_Org_ID());
		note.setTextMsg(messageText);
		note.setAD_Table_ID(referencedRecord.getAD_Table_ID());
		note.setRecord_ID(referencedRecord.getRecord_ID());

		InterfaceWrapperHelper.save(note);
	}

	private void sendMail(final I_AD_User recipient,
			final String adMessage,
			final String messageText,
			final ITableRecordReference referencedRecord)
	{
		final IMailBL mailBL = Services.get(IMailBL.class);
		final IClientDAO clientDAO = Services.get(IClientDAO.class);
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(recipient);

		final String subject = msgBL.getMsg(ctx, adMessage);

		// final de.metas.adempiere.model.I_AD_User sender = userDAO.retrieveUser(ctx, Env.getAD_User_ID(ctx));
		final I_AD_Client adClient = clientDAO.retriveClient(ctx);

		final Mailbox mailBox = mailBL.findMailBox(
				adClient,
				recipient.getAD_Org_ID(),
				0,  // AD_Process_ID
				null,  // C_DocType - Task FRESH-203 this shall work as before
				null,  // customType
				null); // sender
		Check.assumeNotNull(mailBox, "IMailbox for adClient={}, AD_Org_ID={}", adClient, recipient.getAD_Org_ID());

		final StringBuilder mailBody = new StringBuilder();
		mailBody.append("\n" + messageText + "\n");
		mailBody.append(msgBL.parseTranslation(ctx, "@" + referencedRecord.getTableName() + "_ID@: "));
		mailBody.append(referencedRecord.getRecord_ID());

		final EMail mail = mailBL.createEMail(ctx, mailBox, recipient.getEMail(), subject, mailBody.toString(), false);
		mailBL.send(mail);
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
