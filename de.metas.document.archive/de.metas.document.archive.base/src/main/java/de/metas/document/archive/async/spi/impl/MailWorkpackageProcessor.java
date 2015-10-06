package de.metas.document.archive.async.spi.impl;

/*
 * #%L
 * de.metas.document.archive.base
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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ProcessUtil;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_User;
import org.compiere.process.DocAction;
import org.compiere.util.EMail;

import de.metas.adempiere.service.IMailBL;
import de.metas.adempiere.service.IMailBL.IMailbox;
import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;
import de.metas.interfaces.I_C_BPartner;

/**
 * Workpackage processor for mails
 *
 * @author al
 */
public class MailWorkpackageProcessor implements IWorkpackageProcessor
{
	private static final int DEFAULT_SkipTimeoutOnConnectionError = 1000 * 60 * 5; // 5min

	private static final String STATUS_MESSAGE_SENT = "MessageSent";
	private static final String STATUS_MESSAGE_NOT_SENT = "MessageNotSent";

	private static final String MSG_EmailSubject = "MailWorkpackageProcessor.EmailSubject";
	private static final String MSG_EmailMessage = "MailWorkpackageProcessor.EmailMessage";

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		//
		// Services
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IMailBL mailBL = Services.get(IMailBL.class);
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);

		final String action = X_C_Doc_Outbound_Log_Line.ACTION_EMail;

		final List<I_C_Doc_Outbound_Log_Line> logLines = queueDAO.retrieveItems(workpackage, I_C_Doc_Outbound_Log_Line.class, localTrxName);
		for (final I_C_Doc_Outbound_Log_Line logLine : logLines)
		{
			final I_AD_Archive archive = logLine.getAD_Archive();
			Check.assumeNotNull(archive, "archive not null for C_Doc_Outbound_Log_Line={0}", logLine);

			final I_C_Doc_Outbound_Log log = logLine.getC_Doc_Outbound_Log();

			sendEMail(mailBL, msgBL, archiveEventManager, action, log, archive, localTrxName);
		}

		return Result.SUCCESS;
	}

	private void sendEMail(final IMailBL mailBL,
			final IMsgBL msgBL,
			final IArchiveEventManager archiveEventManager,
			final String action,
			final I_C_Doc_Outbound_Log log,
			final I_AD_Archive archive,
			final String trxName)
	{
		try
		{
			sendEMail0(mailBL, msgBL, archiveEventManager, action, log, archive, trxName);
		}
		catch (final Exception e)
		{
			if (mailBL.isConnectionError(e))
			{
				throw WorkpackageSkipRequestException.createWithTimeoutAndThrowable(e.getLocalizedMessage(), DEFAULT_SkipTimeoutOnConnectionError, e);
			}
			else if (e instanceof RuntimeException)
			{
				throw (RuntimeException)e;
			}
			else
			{
				throw new AdempiereException(e);
			}
		}
	}

	private void sendEMail0(final IMailBL mailBL,
			final IMsgBL msgBL,
			final IArchiveEventManager archiveEventManager,
			final String action,
			final I_C_Doc_Outbound_Log log,
			final I_AD_Archive archive,
			final String trxName) throws Exception
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);

		final I_C_BPartner partner = InterfaceWrapperHelper.create(log.getC_BPartner(), I_C_BPartner.class);
		Check.assumeNotNull(partner, "partner not null for {0}", log);

		final I_AD_Client client = InterfaceWrapperHelper.create(ctx, partner.getAD_Client_ID(), I_AD_Client.class, trxName);

		final String mailCustomType = null;

		final IMailbox mailbox = mailBL.findMailBox(client, ProcessUtil.getCurrentOrgId(), ProcessUtil.getCurrentProcessId(), mailCustomType, null); // user=null
		final I_AD_User userFrom = null; // no user - this mailbox is the AD_Client's mailbox

		final I_AD_User userTo = Services.get(IBPartnerBL.class).retrieveBillContact(ctx, partner.getC_BPartner_ID(), trxName);
		Check.assumeNotNull(userTo, "userTo not null for {0}", log);

		final String mailTo = userTo.getEMail();
		Check.assumeNotEmpty(mailTo, "email not empty for {0}", log);

		//
		// Create and send email
		final String status;
		{
			final String subject = msgBL.getMsg(ctx, MSG_EmailSubject);
			final String message = msgBL.getMsg(ctx, MSG_EmailMessage);

			final boolean html = true; // can use HTML

			final EMail email = mailBL.createEMail(ctx, mailbox, mailTo, subject, message, html);

			final File attachment = getDocumentAttachment(ctx, archive, trxName);
			if (attachment == null)
			{
				status = STATUS_MESSAGE_NOT_SENT; // TODO log or do something; do NOT send blank mails without an attachment
			}
			else
			{
				email.addAttachment(attachment);
				mailBL.send(email);

				status = STATUS_MESSAGE_SENT;
			}
		}

		//
		// Create doc outbound log entry
		{
			final String from = userTo.getName();
			final String cc = null;
			final String bcc = null;

			archiveEventManager.fireEmailSent(archive, action, userFrom, from, mailTo, cc, bcc, status);
		}
	}

	private File getDocumentAttachment(final Properties ctx, final I_AD_Archive archive, final String trxName)
	{
		final int tableId = archive.getAD_Table_ID();
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(tableId);

		final int recordId = archive.getRecord_ID();
		final DocAction doc = InterfaceWrapperHelper.create(ctx, tableName, recordId, DocAction.class, trxName);

		final File attachment = doc.createPDF();
		return attachment;
	}
}
