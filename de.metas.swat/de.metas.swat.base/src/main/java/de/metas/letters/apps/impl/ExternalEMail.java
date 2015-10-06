package de.metas.letters.apps.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.adempiere.util.Check;
import org.compiere.util.CLogMgt;
import org.compiere.util.EMail;
import org.jdesktop.jdic.desktop.Message;
import org.jdesktop.jdic.desktop.internal.MailerService;
import org.jdesktop.jdic.desktop.internal.ServiceManager;

import de.metas.letters.apps.IExternalEMail;
import de.metas.letters.model.MADBoilerPlate;

/**
 * @author teo_sarca
 */
public final class ExternalEMail implements IExternalEMail
{
	@Override
	public String send(final EMail email)
	{
		String sentMsg;
		try
		{
			send0(email);
			sentMsg = EMail.SENT_OK;
		}
		catch (final Exception e)
		{
			sentMsg = e.getLocalizedMessage();
			if (CLogMgt.isLevelFine())
			{
				e.printStackTrace();
			}
		}
		email.setSentMsg(sentMsg);
		return sentMsg;
	}

	private void send0(final EMail email) throws Exception
	{
		String body = email.getMessageCRLF();
		if (Check.isEmpty(body))
		{
			body = email.getMessageHTML();
			body = MADBoilerPlate.getPlainText(body);
		}

		final Message msg = new Message();
		msg.setToAddrs(toStringList(email.getTos()));
		msg.setBody(body);
		msg.setSubject(email.getSubject());
		msg.setAttachments(toStringList(email.getAttachments()));

		final MailerService mailerService = (MailerService)ServiceManager.getService(ServiceManager.MAILER_SERVICE);
		mailerService.open(msg);
	}

	private static List<String> toStringList(final Object[] arr)
	{
		if (arr == null)
		{
			return null;
		}
		final List<String> list = new ArrayList<String>();
		for (final Object o : arr)
		{
			if (o instanceof File)
			{
				list.add(((File)o).getAbsolutePath());
			}
			else if (o instanceof InternetAddress)
			{
				list.add(((InternetAddress)o).getAddress());
			}
			else
			{
				list.add(o.toString());
			}
		}
		return list;
	}
}
