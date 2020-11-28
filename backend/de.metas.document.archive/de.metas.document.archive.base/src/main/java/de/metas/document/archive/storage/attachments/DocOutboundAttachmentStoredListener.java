package de.metas.document.archive.storage.attachments;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import de.metas.common.util.time.SystemTime;
import lombok.NonNull;

import java.net.URI;

import org.adempiere.archive.api.ArchiveAction;
import org.adempiere.util.lang.ITableRecordReference;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.storeattachment.AttachmentStoredListener;
import de.metas.document.archive.DocOutboundUtils;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;

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
	private final AttachmentEntryService attachmentEntryService;

	public DocOutboundAttachmentStoredListener(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	@Override
	public void attachmentWasStored(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final URI storageIdentifier)
	{
		final ImmutableList<I_C_Doc_Outbound_Log> docOutboundLogRecords = attachmentEntry
				.getLinkedRecords()
				.stream()
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

		attachmentEntryService.createAttachmentLinks(
				ImmutableList.of(attachmentEntry),
				createdLogLineRecords.build());
	}

	private boolean isDocOutBoundLogReference(ITableRecordReference ref)
	{
		return I_C_Doc_Outbound_Log.Table_Name.equals(ref.getTableName());
	}

}
