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
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_DocType;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;
import de.metas.email.EMail;
import de.metas.email.IMailBL;
import de.metas.email.Mailbox;
import de.metas.interfaces.I_C_BPartner;
import de.metas.process.ProcessExecutor;

/**
 * Workpackage processor for mails
 *
 * @author al
 */
public class MailWorkpackageProcessor implements IWorkpackageProcessor
{
	//
	// Services
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IMailBL mailBL = Services.get(IMailBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);
	private final transient IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final transient IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private static final int DEFAULT_SkipTimeoutOnConnectionError = 1000 * 60 * 5; // 5min

	private static final String STATUS_MESSAGE_SENT = "MessageSent";
	private static final String STATUS_MESSAGE_NOT_SENT = "MessageNotSent";

	private static final String MSG_EmailSubject = "MailWorkpackageProcessor.EmailSubject";
	private static final String MSG_EmailMessage = "MailWorkpackageProcessor.EmailMessage";

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final String action = X_C_Doc_Outbound_Log_Line.ACTION_EMail;

		final List<I_C_Doc_Outbound_Log_Line> logLines = queueDAO.retrieveItems(workpackage, I_C_Doc_Outbound_Log_Line.class, localTrxName);
		for (final I_C_Doc_Outbound_Log_Line logLine : logLines)
		{
			final I_AD_Archive archive = logLine.getAD_Archive();
			Check.assumeNotNull(archive, "archive not null for C_Doc_Outbound_Log_Line={}", logLine);

			final I_C_Doc_Outbound_Log log = logLine.getC_Doc_Outbound_Log();

			sendEMail(action, log, archive, workpackage.getAD_PInstance(), localTrxName);
		}

		return Result.SUCCESS;
	}

	private void sendEMail(
			final String action,
			final I_C_Doc_Outbound_Log log,
			final I_AD_Archive archive,
			final I_AD_PInstance pInstance,
			final String trxName)
	{
		try
		{
			sendEMail0(action, log, archive, pInstance, trxName);
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

	private void sendEMail0(
			final String action,
			final I_C_Doc_Outbound_Log log,
			final I_AD_Archive archive,
			final I_AD_PInstance pInstance,
			final String trxName) throws Exception
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);

		// task FRESH-218
		// set the archive language in the mailing context. This ensures us that the mail will be sent in this language.
		final String archiveLanguage = archive.getAD_Language();
		if(archiveLanguage != null)
		{
			ctx.setProperty(Env.CTXNAME_AD_Language, archiveLanguage);
		}

		final I_C_BPartner partner = InterfaceWrapperHelper.create(log.getC_BPartner(), I_C_BPartner.class);
		Check.assumeNotNull(partner, "partner not null for {}", log);

		final I_AD_Client client = InterfaceWrapperHelper.create(ctx, partner.getAD_Client_ID(), I_AD_Client.class, trxName);

		final String mailCustomType = null;

		final int processID = pInstance == null ? ProcessExecutor.getCurrentProcessId() : pInstance.getAD_Process_ID();
		final int orgID = pInstance == null ? ProcessExecutor.getCurrentOrgId() : pInstance.getAD_Org_ID();
		final I_AD_User userFrom = null; // no user - this mailbox is the AD_Client's mailbox

		final I_C_DocType docType = log.getC_DocType();

		final Mailbox mailbox = mailBL.findMailBox(client, orgID, processID, docType,  mailCustomType, userFrom);

		I_AD_User userTo = null;
		
		// check if the column for the user is specified
		if (!Check.isEmpty(mailbox.getColumnUserTo(), true))
		{
			final String tableName = adTableDAO.retrieveTableName(log.getAD_Table_ID());
			
			// chekc if the column exists
			final boolean existsColumn = adTableDAO.hasColumnName(tableName, mailbox.getColumnUserTo());
			if (existsColumn)
			{
				// load the column content
				final Object po = InterfaceWrapperHelper.create(ctx, tableName, log.getRecord_ID(), Object.class, trxName);
				final Integer userToID = InterfaceWrapperHelper.getValueOrNull(po, mailbox.getColumnUserTo());
				if (userToID != null)
				{
					userTo = InterfaceWrapperHelper.create(ctx, I_AD_User.Table_Name, userToID, I_AD_User.class, trxName);
				}
			}
		}
		
		//
		// fallback to old logic
		if (userTo == null)
		{
			userTo = bpartnerBL.retrieveBillContact(ctx, partner.getC_BPartner_ID(), trxName);
			Check.assumeNotNull(userTo, "userTo not null for {}", log);
		}

		final String mailTo = userTo.getEMail();
		Check.assumeNotEmpty(mailTo, "email not empty for {}", log);

		//
		// Create and send email
		final String status;
		{
			final String subject = msgBL.getMsg(ctx, MSG_EmailSubject);
			final String message = msgBL.getMsg(ctx, MSG_EmailMessage);

			// FRESH-203: HTML mails don't work for not-HTML texts, which are the majority or (even all?) among out AD_Messages
			// setting this to false to avoid the formatting from being lost and non-ASCII-chars from being printed wrongly.
			final boolean html = false;

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
			final String from = mailbox.getEmail();
			final String cc = null;
			final String bcc = null;

			final String statusText = msgBL.getMsg(ctx, status);

			archiveEventManager.fireEmailSent(archive, action, userFrom, from, mailTo, cc, bcc, statusText);
		}
	}

	private File getDocumentAttachment(final Properties ctx, final I_AD_Archive archive, final String trxName)
	{
		final int tableId = archive.getAD_Table_ID();
		final String tableName = adTableDAO.retrieveTableName(tableId);

		final int recordId = archive.getRecord_ID();
		final DocAction doc = InterfaceWrapperHelper.create(ctx, tableName, recordId, DocAction.class, trxName);

		final File attachment = doc.createPDF();
		return attachment;
	}
}
