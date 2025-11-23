package de.metas.document.archive.storage.attachments;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentReference;
import de.metas.attachments.AttachmentService;
import de.metas.attachments.storeattachment.AttachmentStoredListener;
import de.metas.common.util.time.SystemTime;
import de.metas.document.archive.DocOutboundUtils;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import lombok.NonNull;
import org.adempiere.archive.api.ArchiveAction;
import org.adempiere.util.lang.ITableRecordReference;
import org.springframework.stereotype.Component;

import java.net.URI;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class DocOutboundAttachmentStoredListener implements AttachmentStoredListener
{
	private final AttachmentService attachmentService;

	public DocOutboundAttachmentStoredListener(@NonNull final AttachmentService attachmentService)
	{
		this.attachmentService = attachmentService;
	}

	@Override
	public void attachmentWasStored(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final URI storageIdentifier)
	{
		final ImmutableList<AttachmentReference> attachmentReferences = attachmentService.retrieveAttachmentReferences(attachmentEntry);
		
		final ImmutableList<I_C_Doc_Outbound_Log> docOutboundLogRecords = attachmentReferences
				.stream()
				.map(AttachmentReference::getRecordRef)
				.filter(this::isDocOutBoundLogReference)
				.map(ref -> ref.getModel(I_C_Doc_Outbound_Log.class))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList.Builder<I_C_Doc_Outbound_Log_Line> createdLogLineRecords = ImmutableList.builder();
		for (final I_C_Doc_Outbound_Log docOutboundLogRecord : docOutboundLogRecords)
		{
			final I_C_Doc_Outbound_Log_Line docOutboundLogLineRecord = DocOutboundUtils.createOutboundLogLineRecord(docOutboundLogRecord);
			docOutboundLogLineRecord.setAction(ArchiveAction.ATTACHMENT_STORED.getCode());
			docOutboundLogLineRecord.setStoreURI(storageIdentifier.toString());

			saveRecord(docOutboundLogLineRecord);
			createdLogLineRecords.add(docOutboundLogLineRecord);

			docOutboundLogRecord.setDateLastStore(SystemTime.asTimestamp());
			saveRecord(docOutboundLogRecord);
		}

		attachmentService.createAttachmentLinks(
				ImmutableList.of(attachmentEntry),
				createdLogLineRecords.build());
	}

	private boolean isDocOutBoundLogReference(@NonNull final ITableRecordReference ref)
	{
		return I_C_Doc_Outbound_Log.Table_Name.equals(ref.getTableName());
	}

}
