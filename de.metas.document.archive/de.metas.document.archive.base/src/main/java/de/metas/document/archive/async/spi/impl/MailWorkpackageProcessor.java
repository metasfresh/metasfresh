package de.metas.document.archive.async.spi.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_User;
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
import de.metas.i18n.IMsgBL;
import de.metas.process.ProcessExecutor;
import lombok.NonNull;

/**
 * Async processor that sends the PDFs of {@link I_C_Doc_Outbound_Log_Line}s' {@link I_AD_Archive}s as Email.
 * The recipient's email address is taken from {@link I_C_Doc_Outbound_Log#getCurrentEMailAddress()}.
 * Where this column is empty, no mail is send.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MailWorkpackageProcessor implements IWorkpackageProcessor
{
	//
	// Services
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IMailBL mailBL = Services.get(IMailBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);
	private final transient IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final transient IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private static final int DEFAULT_SkipTimeoutOnConnectionError = 1000 * 60 * 5; // 5min

	private static final String MSG_EmailSubject = "MailWorkpackageProcessor.EmailSubject";
	private static final String MSG_EmailMessage = "MailWorkpackageProcessor.EmailMessage";

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final List<I_C_Doc_Outbound_Log_Line> logLines = queueDAO.retrieveItems(workpackage, I_C_Doc_Outbound_Log_Line.class, localTrxName);
		for (final I_C_Doc_Outbound_Log_Line logLine : logLines)
		{
			final I_AD_Archive archive = logLine.getAD_Archive();
			Check.assumeNotNull(archive, "archive not null for C_Doc_Outbound_Log_Line={}", logLine);

			final I_C_Doc_Outbound_Log docOutboundLogRecord = logLine.getC_Doc_Outbound_Log();
			if (Check.isEmpty(docOutboundLogRecord.getCurrentEMailAddress(), true))
			{
				// maybe this was changed since the WP's enqueuing
				Loggables.get()
						.addLog("Skip C_Doc_Outbound_Log_Line_ID={} which has a C_Doc_Outbound_Log with an empty CurrentEMailAddress value; C_Doc_Outbound_Log={} ",
								logLine.getC_Doc_Outbound_Log_Line_ID(), docOutboundLogRecord);
			}

			sendEMail(docOutboundLogRecord, archive, workpackage.getAD_PInstance(), localTrxName);
		}

		return Result.SUCCESS;
	}

	private void sendEMail(
			@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord,
			@NonNull final I_AD_Archive archive,
			@Nullable final I_AD_PInstance pInstance,
			@Nullable final String trxName)
	{
		try
		{
			sendEMail0(docOutboundLogRecord, archive, pInstance, trxName);
		}
		catch (final Exception e)
		{
			if (mailBL.isConnectionError(e))
			{
				throw WorkpackageSkipRequestException.createWithTimeoutAndThrowable(e.getLocalizedMessage(), DEFAULT_SkipTimeoutOnConnectionError, e);
			}
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private void sendEMail0(
			@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord,
			@NonNull final I_AD_Archive archive,
			@Nullable final I_AD_PInstance pInstance,
			@Nullable final String trxName) throws Exception
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);

		// task FRESH-218
		// set the archive language in the mailing context. This ensures us that the mail will be sent in this language.
		final String archiveLanguage = archive.getAD_Language();
		if (archiveLanguage != null)
		{
			ctx.setProperty(Env.CTXNAME_AD_Language, archiveLanguage);
		}

		final int processID = pInstance == null ? ProcessExecutor.getCurrentProcessId() : pInstance.getAD_Process_ID();

		final I_AD_User userFrom = null; // no user - this mailbox is the AD_Client's mailbox

		final Mailbox mailbox = mailBL.findMailBox(
				docOutboundLogRecord.getAD_Client(),
				docOutboundLogRecord.getAD_Org_ID(),
				processID,
				docOutboundLogRecord.getC_DocType(),
				null, // mailCustomType
				userFrom);

		// note that we verified this earlier
		final String mailTo = Check.assumeNotEmpty(
				docOutboundLogRecord.getCurrentEMailAddress(),
				"C_Doc_Outbound_Log needs to have a non-empty CurrentEMailAddress value; C_Doc_Outbound_Log={}", docOutboundLogRecord);

		// Create and send email
		final String status;
		{
			final String subject = msgBL.getMsg(ctx, MSG_EmailSubject);
			final String message = msgBL.getMsg(ctx, MSG_EmailMessage);

			// FRESH-203: HTML mails don't work for not-HTML texts, which are the majority or (even all?) among out AD_Messages
			// setting this to false to avoid the formatting from being lost and non-ASCII-chars from being printed wrongly.
			final boolean html = isHTMLMessage(message);

			final EMail email = mailBL.createEMail(ctx, mailbox, mailTo, subject, message, html);

			final byte[] attachment = archiveBL.getBinaryData(archive);
			if (attachment == null)
			{
				status = IArchiveEventManager.STATUS_MESSAGE_NOT_SENT; // TODO log or do something; do NOT send blank mails without an attachment
			}
			else
			{
				final String tableName = adTableDAO.retrieveTableName(docOutboundLogRecord.getAD_Table_ID());
				email.addAttachment(tableName + "_" + docOutboundLogRecord.getRecord_ID() + ".pdf", attachment);

				mailBL.send(email);
				status = IArchiveEventManager.STATUS_MESSAGE_SENT;
			}
		}

		//
		// Create doc outbound log entry
		{
			final String from = mailbox.getEmail();
			final String cc = null;
			final String bcc = null;

			final String statusText = msgBL.getMsg(ctx, status);

			archiveEventManager.fireEmailSent(
					archive,
					X_C_Doc_Outbound_Log_Line.ACTION_EMail,
					userFrom,
					from,
					mailTo,
					cc,
					bcc,
					statusText);
		}
	}

	private boolean isHTMLMessage(final String message)
	{
		if (Check.isEmpty(message))
		{
			// no message => no html
			return false;
		}

		return message.toLowerCase().indexOf("<html>") >= 0;
	}
}
