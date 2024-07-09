package de.metas.document.archive.async.spi.impl;

import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.archive.api.ArchiveFileNameService;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.email.MailService;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.letter.BoilerPlate;
import de.metas.letter.BoilerPlateId;
import de.metas.letter.BoilerPlateRepository;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutor;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.archive.api.ArchiveEmailSentStatus;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

import static de.metas.attachments.AttachmentTags.TAGNAME_SEND_VIA_EMAIL;

/**
 * Async processor that sends the PDFs of {@link I_C_Doc_Outbound_Log_Line}s' {@link I_AD_Archive}s as Email.
 * The recipient's email address is taken from {@link I_C_Doc_Outbound_Log#getCurrentEMailAddress()}.
 * Where this column is empty, no mail is send.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class MailWorkpackageProcessor implements IWorkpackageProcessor
{
	//
	// Services
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IClientDAO clientsDAO = Services.get(IClientDAO.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);
	private final transient IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private final transient ArchiveFileNameService archiveFileNameService = SpringContextHolder.instance.getBean(ArchiveFileNameService.class);
	private final transient MailService mailService = SpringContextHolder.instance.getBean(MailService.class);
	private final transient BoilerPlateRepository boilerPlateRepository = SpringContextHolder.instance.getBean(BoilerPlateRepository.class);
	private final transient DocOutBoundRecipientRepository docOutBoundRecipientRepository = SpringContextHolder.instance.getBean(DocOutBoundRecipientRepository.class);
	private final transient AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	private static final int DEFAULT_SkipTimeoutOnConnectionError = 1000 * 60 * 5; // 5min

	private static final AdMessageKey MSG_EmailSubject = AdMessageKey.of("MailWorkpackageProcessor.EmailSubject");
	private static final AdMessageKey MSG_EmailMessage = AdMessageKey.of("MailWorkpackageProcessor.EmailMessage");
	private IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final List<I_C_Doc_Outbound_Log_Line> logLines = queueDAO.retrieveAllItems(workpackage, I_C_Doc_Outbound_Log_Line.class);
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

			sendEMail(docOutboundLogRecord, archive, workpackage.getAD_PInstance());
		}

		return Result.SUCCESS;
	}

	private void sendEMail(
			@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord,
			@NonNull final I_AD_Archive archive,
			@Nullable final I_AD_PInstance pInstance)
	{
		try
		{
			sendEMail0(docOutboundLogRecord, archive, pInstance);
		}
		catch (final Exception e)
		{
			if (mailService.isConnectionError(e))
			{
				throw WorkpackageSkipRequestException.createWithTimeoutAndThrowable(e.getLocalizedMessage(), DEFAULT_SkipTimeoutOnConnectionError, e);
			}
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private void sendEMail0(
			@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord,
			@NonNull final I_AD_Archive archive,
			@Nullable final I_AD_PInstance pInstance)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);

		// task FRESH-218
		// set the archive language in the mailing context. This ensures us that the mail will be sent in this language.
		final String archiveLanguage = archive.getAD_Language();
		if (archiveLanguage != null)
		{
			ctx.setProperty(Env.CTXNAME_AD_Language, archiveLanguage);
		}

		final AdProcessId processId = pInstance != null
				? AdProcessId.ofRepoIdOrNull(pInstance.getAD_Process_ID())
				: ProcessExecutor.getCurrentProcessIdOrNull();

		final ClientId adClientId = ClientId.ofRepoId(docOutboundLogRecord.getAD_Client_ID());
		final ClientEMailConfig tenantEmailConfig = clientsDAO.getEMailConfigById(adClientId);
		final DocBaseAndSubType docBaseAndSubType = extractDocBaseAndSubType(docOutboundLogRecord);
		final Mailbox mailbox = mailService.findMailBox(
				tenantEmailConfig,
				OrgId.ofRepoId(docOutboundLogRecord.getAD_Org_ID()),
				processId,
				docBaseAndSubType,
				(EMailCustomType)null); // mailCustomType

		// note that we verified this earlier
		final EMailAddress mailTo = EMailAddress.ofNullableString(docOutboundLogRecord.getCurrentEMailAddress());
		Check.assumeNotNull(
				docOutboundLogRecord.getCurrentEMailAddress(),
				"C_Doc_Outbound_Log needs to have a non-empty CurrentEMailAddress value; C_Doc_Outbound_Log={}", docOutboundLogRecord);

		// Create and send email
		final ArchiveEmailSentStatus status;
		{
			final EmailParams emailParams = extractEmailParams(docOutboundLogRecord);

			final EMail email = mailService.createEMail(
					mailbox,
					mailTo,
					emailParams.getSubject(),
					emailParams.getMessage(),
					isHTMLMessage(emailParams.getMessage()));

			final TableRecordReference recordRef = TableRecordReference.ofReferenced(docOutboundLogRecord);

			final long addedAttachments = attachmentEntryService.streamEmailAttachments(recordRef, TAGNAME_SEND_VIA_EMAIL)
					.map(attachment -> email.addAttachment(attachment.getFilename(), attachment.getAttachmentDataSupplier().get()))
					.filter(Boolean::booleanValue)
					.count();

			final byte[] attachment = archiveBL.getBinaryData(archive);
			if (attachment == null && addedAttachments <= 0)
			{
				status = ArchiveEmailSentStatus.MESSAGE_NOT_SENT;
				Loggables.addLog("No documents to attach on email for C_Doc_Outbound_Log_ID={}; -> not sending mail", docOutboundLogRecord.getC_Doc_Outbound_Log_ID());
			}
			else
			{
				final String pdfFileName = archiveFileNameService.computePdfFileName(docOutboundLogRecord);
				email.addAttachment(pdfFileName, attachment);

				mailService.send(email);
				status = ArchiveEmailSentStatus.MESSAGE_SENT;
			}
		}

		//
		// Create doc outbound log entry
		{
			final EMailAddress from = mailbox.getEmail();
			final EMailAddress cc = null;
			final EMailAddress bcc = null;

			archiveEventManager.fireEmailSent(
					archive,
					(UserEMailConfig)null,
					from,
					mailTo,
					cc,
					bcc,
					status);
		}
	}

	@Nullable
	private DocBaseAndSubType extractDocBaseAndSubType(final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(docOutboundLogRecord.getC_DocType_ID());
		if (docTypeId == null)
		{
			return null;
		}

		final I_C_DocType docType = Services.get(IDocTypeDAO.class).getRecordById(docTypeId);
		return DocBaseAndSubType.of(docType.getDocBaseType(), docType.getDocSubType());
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

	private EmailParams extractEmailParams(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final Language language = extractLanguage(docOutboundLogRecord);
		final Evaluatee evalCtx = createEvaluationContext(docOutboundLogRecord);

		if (docOutboundLogRecord.getC_DocType_ID() > 0)
		{
			final I_C_DocType docTypeRecord = docTypeDAO.getRecordById(docOutboundLogRecord.getC_DocType_ID());
			if (docTypeRecord.getAD_BoilerPlate_ID() > 0)
			{
				final BoilerPlateId boilerPlateId = BoilerPlateId.ofRepoId(docTypeRecord.getAD_BoilerPlate_ID());
				final BoilerPlate boilerPlate = boilerPlateRepository.getByBoilerPlateId(boilerPlateId, language);

				Loggables.addLog("createEmailParams - Using the boilerPlate with boilerPlateId={} of the C_Doc_Outbound_Log's C_DocType", boilerPlateId);

				return EmailParams
						.builder()
						.subject(boilerPlate.evaluateSubject(evalCtx))
						.message(boilerPlate.evaluateTextSnippet(evalCtx))
						.build();
			}
		}

		Loggables.addLog("createEmailParams - AD_Messages with values {} and {}", MSG_EmailSubject, MSG_EmailMessage);

		final IStringExpression subject = StringExpressionCompiler.instance.compile(msgBL.getMsg(language.getAD_Language(), MSG_EmailSubject));
		final IStringExpression message = StringExpressionCompiler.instance.compile(msgBL.getMsg(language.getAD_Language(), MSG_EmailMessage));

		return EmailParams
				.builder()
				.subject(subject.evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Preserve))
				.message(message.evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Preserve))
				.build();
	}

	private Language extractLanguage(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		DocOutBoundRecipient recipient = null;
		if (docOutboundLogRecord.getCurrentEMailRecipient_ID() > 0)
		{
			recipient = docOutBoundRecipientRepository.getById(DocOutBoundRecipientId.ofRepoId(docOutboundLogRecord.getCurrentEMailRecipient_ID()));
			final Language userLanguage = recipient.getUserLanguage();
			if (userLanguage != null)
			{
				Loggables.addLog(
						"extractLanguage - Using the userLanguage={} of the C_Doc_Outbound_Log's CurrentEMailRecipient_ID={}",
						userLanguage.getAD_Language(), docOutboundLogRecord.getCurrentEMailRecipient_ID());
				return userLanguage;

			}
		}

		if (docOutboundLogRecord.getC_BPartner_ID() > 0)
		{
			final Language bPartnerLanguage = Services.get(IBPartnerBL.class).getLanguageForModel(docOutboundLogRecord).orElse(null);
			if (bPartnerLanguage != null)
			{
				Loggables.addLog(
						"extractLanguage - Using language={} of the C_Doc_Outbound_Log'sC_BPartner_ID={}",
						bPartnerLanguage.getAD_Language(), docOutboundLogRecord.getC_BPartner_ID());
				return bPartnerLanguage;
			}
		}

		if (recipient != null && recipient.getBPartnerLanguage() != null)
		{
			final Language bPartnerLanguage = recipient.getBPartnerLanguage();
			Loggables.addLog(
					"extractLanguage - Using the bPartnerLanguage={} of the C_Doc_Outbound_Log's CurrentEMailRecipient_ID={}",
					bPartnerLanguage.getAD_Language(), docOutboundLogRecord.getCurrentEMailRecipient_ID());
			return bPartnerLanguage;
		}

		final Language language = Language.getLanguage(Env.getADLanguageOrBaseLanguage());
		Loggables.addLog("extractLanguage - Using the language={} returned by Env.getADLanguageOrBaseLanguage()", language);
		return language;
	}

	private Evaluatee createEvaluationContext(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final TableRecordReference modelRef = TableRecordReference.ofOrNull(docOutboundLogRecord.getAD_Table_ID(), docOutboundLogRecord.getRecord_ID());
		final Evaluatee modelCtx = modelRef != null
				? TableModelLoader.instance.getPO(modelRef)
				: Evaluatees.empty();
		final String orgName = orgDAO.retrieveOrgName(OrgId.ofRepoId(docOutboundLogRecord.getAD_Org_ID()));
		final Evaluatee orgEvaluatee = Evaluatees.mapBuilder()
				.put("AD_Org.Name", orgName)
				.build();
		return orgEvaluatee.andComposeWith(modelCtx)
				.andComposeWith(InterfaceWrapperHelper.getEvaluatee(docOutboundLogRecord));
	}

	@Value
	@Builder
	private static class EmailParams
	{
		String subject;
		String message;
	}
}
