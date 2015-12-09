package de.metas.notification.impl;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IMsgDAO;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_User;
import org.compiere.util.EMail;
import org.compiere.util.Env;

import de.metas.notification.IMailBL;
import de.metas.notification.INotificationBL;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class NotificationBL implements INotificationBL
{

	@Override
	public void notifyUser(final I_AD_User recipient,
			final String adMessage,
			final String messageText,
			final ITableRecordReference referencedRecord
			)
	{
		notifyUser0(recipient, adMessage, messageText, referencedRecord, new HashSet<Integer>());
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
			final de.metas.adempiere.model.I_AD_User userIncharge = InterfaceWrapperHelper.create(recipient, de.metas.adempiere.model.I_AD_User.class);
			Check.errorUnless(userIds.add(userIncharge.getAD_User_ID()), "loop");
			notifyUser0(userIncharge, adMessage, messageText, referencedRecord, userIds);
		}
		if (userBL.isNotificationEMail(recipient))
		{
			sendMail(recipient, adMessage, messageText, referencedRecord);
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
		final IUserDAO userDAO = Services.get(IUserDAO.class);
		final IClientDAO clientDAO = Services.get(IClientDAO.class);
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(recipient);

		final de.metas.adempiere.model.I_AD_User sender = userDAO.retrieveUser(ctx, Env.getAD_User_ID(ctx));
		final I_AD_Client adClient = clientDAO.retriveClient(ctx, sender.getAD_Client_ID());
		final String subject = msgBL.getMsg(ctx, adMessage);

		mailBL.findMailBox(adClient, sender.getAD_Org_ID(), 0, null, sender);

		final StringBuilder mailBody = new StringBuilder();
		mailBody.append("\n" + messageText + "\n");
		mailBody.append(msgBL.parseTranslation(ctx, "@" + referencedRecord.getTableName() + "_ID@: "));
		mailBody.append(referencedRecord.getRecord_ID());

		final EMail mail = mailBL.createEMail(ctx, null, recipient.getEMail(), subject, mailBody.toString(), false);
		mailBL.send(mail);
	}

}
