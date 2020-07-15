package de.metas.document.archive.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.archive.spi.IArchiveEventListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentEntryService.AttachmentEntryQuery;
import de.metas.attachments.AttachmentTags;
import de.metas.document.archive.DocOutboundUtils;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRegistry;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.email.EMailAddress;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

@Component
public class DocOutboundArchiveEventListener implements IArchiveEventListener
{
	private AttachmentEntryService attachmentEntryService;

	public DocOutboundArchiveEventListener(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	@Override
	public void onPdfUpdate(@Nullable final I_AD_Archive archive, @Nullable final UserId userId, final String action)
	{
		if (!isLoggableArchive(archive))
		{
			return;
		}

		final I_C_Doc_Outbound_Log_Line docExchangeLine = createLogLine(archive);
		docExchangeLine.setAction(action);
		if (userId != null)
		{
			docExchangeLine.setAD_User_ID(userId.getRepoId());
		}
		save(docExchangeLine);
	}

	@Override
	public void onEmailSent(
			@NonNull final I_AD_Archive archive,
			final String action,
			@Nullable final UserEMailConfig userMailConfig,
			final EMailAddress from,
			final EMailAddress to,
			final EMailAddress cc,
			final EMailAddress bcc,
			final String status)
	{
		if (!isLoggableArchive(archive))
		{
			return;
		}

		final I_C_Doc_Outbound_Log_Line docExchangeLine = createLogLine(archive);
		docExchangeLine.setAction(action);
		docExchangeLine.setEMail_From(EMailAddress.toStringOrNull(from));
		docExchangeLine.setEMail_To(EMailAddress.toStringOrNull(to));
		docExchangeLine.setEMail_Cc(EMailAddress.toStringOrNull(cc));
		docExchangeLine.setEMail_Bcc(EMailAddress.toStringOrNull(bcc));
		docExchangeLine.setStatus(status);
		if (userMailConfig != null)
		{
			docExchangeLine.setAD_User_ID(UserId.toRepoId(userMailConfig.getUserId()));
		}
		save(docExchangeLine);

		final I_C_Doc_Outbound_Log log = docExchangeLine.getC_Doc_Outbound_Log();
		log.setDateLastEMail(SystemTime.asTimestamp());
		save(log);
	}

	@Override
	public void onPrintOut(final I_AD_Archive archive,
			@Nullable final UserId userId,
			final String printerName,
			final int copies,
			final String status)
	{
		// task 05334: only assume existing archive if the status is "success"
		if (IArchiveEventManager.STATUS_Success.equals(status))
		{
			Check.assumeNotNull(archive, "archive not null");
		}
		if (!isLoggableArchive(archive))
		{
			return;
		}

		final I_C_Doc_Outbound_Log_Line docOutboundLogLineRecord = createLogLine(archive);

		docOutboundLogLineRecord.setAction(X_C_Doc_Outbound_Log_Line.ACTION_Print);
		docOutboundLogLineRecord.setPrinterName(printerName);

		// create stuff
		if (userId != null)
		{
			docOutboundLogLineRecord.setAD_User_ID(userId.getRepoId());
		}
		docOutboundLogLineRecord.setStatus(status);

		save(docOutboundLogLineRecord);
	}

	/**
	 * We don't generate logs for archives without table IDs
	 */
	private boolean isLoggableArchive(@Nullable final I_AD_Archive archive)
	{
		// task 05334: be robust against archive==null
		if (archive == null || archive.getAD_Table_ID() <= 0)
		{
			return false;
		}

		return true;
	}

	/**
	 * Creates {@link I_C_Doc_Outbound_Log_Line}.
	 *
	 * NOTE: it is not saving the created log line
	 *
	 * @param archive
	 * @return {@link I_C_Doc_Outbound_Log_Line}
	 */
	@VisibleForTesting
	I_C_Doc_Outbound_Log_Line createLogLine(@NonNull final I_AD_Archive archive)
	{
		final ArchiveId archiveId = ArchiveId.ofRepoId(archive.getAD_Archive_ID());
		I_C_Doc_Outbound_Log docOutboundLogRecord = Services.get(IDocOutboundDAO.class).retrieveLog(archiveId);

		if (docOutboundLogRecord == null)
		{
			// no log found, create a new one
			docOutboundLogRecord = createLog(archive);
		}

		final I_C_Doc_Outbound_Log_Line docOutboundLogLineRecord = DocOutboundUtils.createOutboundLogLineRecord(docOutboundLogRecord);

		// We need to use DocumentNo if possible; else fallback to archive's name
		// see http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing
		if (Check.isEmpty(docOutboundLogLineRecord.getDocumentNo(), true))
		{
			docOutboundLogLineRecord.setDocumentNo(archive.getName());
		}

		docOutboundLogLineRecord.setAD_Archive_ID(archive.getAD_Archive_ID());

		// if the AD_Org_ID of the AD_Archive and the parent C_Doc_Outbound_Log differ, go with the AD_Archive's org id.
		docOutboundLogLineRecord.setAD_Org_ID(archive.getAD_Org_ID());

		return docOutboundLogLineRecord;
	}

	/**
	 * Creates and saves {@link I_C_Doc_Outbound_Log}
	 *
	 * @return {@link I_C_Doc_Outbound_Log}
	 */
	private I_C_Doc_Outbound_Log createLog(@NonNull final I_AD_Archive archiveRecord)
	{
		// Services
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

		final TableRecordReference reference = TableRecordReference.ofReferenced(archiveRecord);

		final int adTableId = reference.getAD_Table_ID();
		final int recordId = reference.getRecord_ID();

		final Properties ctx = InterfaceWrapperHelper.getCtx(archiveRecord);

		final I_C_Doc_Outbound_Log docOutboundLogRecord = newInstance(I_C_Doc_Outbound_Log.class);
		docOutboundLogRecord.setAD_Org_ID(archiveRecord.getAD_Org_ID());
		docOutboundLogRecord.setAD_Table_ID(adTableId);
		docOutboundLogRecord.setRecord_ID(recordId);
		docOutboundLogRecord.setC_BPartner_ID(archiveRecord.getC_BPartner_ID());

		final int doctypeID = docActionBL.getC_DocType_ID(ctx, adTableId, recordId);
		docOutboundLogRecord.setC_DocType_ID(doctypeID);

		docOutboundLogRecord.setDateLastEMail(null);
		docOutboundLogRecord.setDateLastPrint(null);

		final DocStatus docStatus = docActionBL.getDocStatusOrNull(reference);
		docOutboundLogRecord.setDocStatus(DocStatus.toCodeOrNull(docStatus));

		docOutboundLogRecord.setDocumentNo(archiveRecord.getName());

		final LocalDate documentDate = CoalesceUtil.coalesce(
				docActionBL.getDocumentDate(ctx, adTableId, recordId),
				TimeUtil.asLocalDate(docOutboundLogRecord.getCreated()));

		docOutboundLogRecord.setDateDoc(TimeUtil.asTimestamp(documentDate)); // task 08905: Also set the the documentDate

		setMailRecipient(docOutboundLogRecord);

		save(docOutboundLogRecord);

		shareDocumentAttachmentsWithDocoutBoundLog(archiveRecord, docOutboundLogRecord);

		return docOutboundLogRecord;
	}

	private void setMailRecipient(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final DocOutboundLogMailRecipientRegistry docOutboundLogMailRecipientRegistry = Adempiere.getBean(DocOutboundLogMailRecipientRegistry.class);

		final Optional<DocOutBoundRecipient> mailRecipient = docOutboundLogMailRecipientRegistry.invokeProvider(docOutboundLogRecord);

		mailRecipient.ifPresent(recipient -> updateRecordWithRecipient(docOutboundLogRecord, recipient));
	}

	private void updateRecordWithRecipient(
			@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord,
			@NonNull final DocOutBoundRecipient recipient)
	{
		final String tableName = TableRecordReference.ofReferenced(docOutboundLogRecord).getTableName();
		final boolean isInvoiceEmailEnabled = I_C_Invoice.Table_Name.equals(tableName) && recipient.isInvoiceAsEmail();
		docOutboundLogRecord.setIsInvoiceEmailEnabled(isInvoiceEmailEnabled);

		docOutboundLogRecord.setCurrentEMailRecipient_ID(recipient.getId().getRepoId());
		docOutboundLogRecord.setCurrentEMailAddress(recipient.getEmailAddress());
	}

	private void shareDocumentAttachmentsWithDocoutBoundLog(
			@NonNull final I_AD_Archive archiveRecord,
			@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final ITableRecordReference from = TableRecordReference.ofReferencedOrNull(archiveRecord);
		if (from == null)
		{
			return;
		}

		final AttachmentEntryQuery query = AttachmentEntryQuery
				.builder()
				.referencedRecord(from)
				.tagSetToTrue(AttachmentTags.TAGNAME_IS_DOCUMENT)
				.build();
		final List<AttachmentEntry> attachmentsToShare = attachmentEntryService.getByQuery(query);

		attachmentEntryService.createAttachmentLinks(attachmentsToShare, ImmutableList.of(docOutboundLogRecord));
	}
}
