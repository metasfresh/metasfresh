package de.metas.document.archive.storage.attachments.process;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentEntryService.AttachmentEntryQuery;
import de.metas.attachments.storeattachment.StoreAttachmentService;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class C_Doc_Outbound_Log_StoreAttachments
		extends JavaProcess
		implements IProcessPrecondition
{
	private static final AdMessageKey MSG_EMPTY_SELECTION = AdMessageKey.of("C_Doc_Outbound_Log_StoreAttachments.No_DocOutboundLog_Selection");

	private final transient AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final transient StoreAttachmentService storeAttachmentService = SpringContextHolder.instance.getBean(StoreAttachmentService.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final SelectionSize selectionSize = context.getSelectionSize();
		if (selectionSize.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (selectionSize.isAllSelected() || selectionSize.getSize() > 500)
		{
			// Checking is too expensive; just assume that some selected records have an attachment that shall be stored
			return ProcessPreconditionsResolution.accept();
		}

		final boolean atLeastOneRecordAttachmentsToStore = context
				.streamSelectedModels(I_C_Doc_Outbound_Log.class)
				.anyMatch(this::hasAttachmentToStore);
		if (!atLeastOneRecordAttachmentsToStore)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_EMPTY_SELECTION));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_C_Doc_Outbound_Log> docOutboundLogRecords = retrieveSelectedDocOutboundLogs();
		while (docOutboundLogRecords.hasNext())
		{
			final I_C_Doc_Outbound_Log docOutboundLogRecord = docOutboundLogRecords.next();
			for (final AttachmentEntry attachmentEntry : retrieveAllAttachments(docOutboundLogRecord))
			{
				storeAttachmentService.storeAttachment(attachmentEntry);
			}
		}
		return MSG_OK;
	}

	private final Iterator<I_C_Doc_Outbound_Log> retrieveSelectedDocOutboundLogs()
	{
		final IQueryFilter<I_C_Doc_Outbound_Log> filter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final Stream<I_C_Doc_Outbound_Log> stream = queryBL
				.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.filter(filter)
				.create()
				.iterateAndStream()
				.filter(this::hasAttachmentToStore);

		return stream.iterator();
	}

	private boolean hasAttachmentToStore(@NonNull final I_C_Doc_Outbound_Log docoutBoundLogRecord)
	{
		final List<AttachmentEntry> attachmentEntries = retrieveAllAttachments(docoutBoundLogRecord);

		return attachmentEntries
				.stream()
				.anyMatch(this::isStorable);
	}

	private List<AttachmentEntry> retrieveAllAttachments(@NonNull final I_C_Doc_Outbound_Log docoutBoundLogRecord)
	{
		final AttachmentEntryQuery query = AttachmentEntryQuery
				.builder()
				.referencedRecord(docoutBoundLogRecord)
				.build();
		return attachmentEntryService.getByQuery(query);
	}

	private boolean isStorable(@NonNull final AttachmentEntry attachmentEntry)
	{
		return storeAttachmentService.isAttachmentStorable(attachmentEntry);
	}
}
