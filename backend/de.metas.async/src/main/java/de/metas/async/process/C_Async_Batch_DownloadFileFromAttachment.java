/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.async.process;

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.MimeType;
import org.springframework.core.io.Resource;

import java.util.List;

public class C_Async_Batch_DownloadFileFromAttachment extends JavaProcess implements IProcessPrecondition
{
	private final transient AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_C_Async_Batch record = asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(AsyncBatchId.ofRepoId(context.getSingleSelectedRecordId()));
		if (!hasAttachments(record))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No attachments found");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{

		final I_C_Async_Batch record = asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(AsyncBatchId.ofRepoId(getRecord_ID()));

		final List<AttachmentEntry> attachments = getAttachmentEntries(record);
		if (!attachments.isEmpty())
		{
			final AttachmentEntry attachment = attachments.get(0); // take first one
			final Resource data = attachmentEntryService.retrieveDataResource(attachment.getId());

			getResult().setReportData(data, attachment.getFilename(), attachment.getMimeType());
		}

		return MSG_OK;
	}

	private boolean hasAttachments(@NonNull final I_C_Async_Batch record)
	{
		final List<AttachmentEntry> attachments = getAttachmentEntries(record);

		return !attachments.isEmpty();
	}

	private List<AttachmentEntry> getAttachmentEntries(@NonNull final I_C_Async_Batch record)
	{
		final AttachmentEntryService.AttachmentEntryQuery attachmentQuery = AttachmentEntryService.AttachmentEntryQuery.builder()
				.referencedRecord(TableRecordReference.of(record))
				.mimeType(MimeType.TYPE_PDF)
				.build();

		return attachmentEntryService.getByQuery(attachmentQuery);
	}
}
