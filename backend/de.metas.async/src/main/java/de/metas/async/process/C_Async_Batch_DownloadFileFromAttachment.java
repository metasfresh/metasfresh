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

import de.metas.async.model.I_C_Async_Batch;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.MimeType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import javax.annotation.Nullable;
import java.util.List;

public class C_Async_Batch_DownloadFileFromAttachment extends JavaProcess implements IProcessPrecondition
{
	private final transient AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{

		final I_C_Async_Batch record = getRecord();
		if (record == null)
		{
			return MSG_Error;
		}

		final AttachmentEntryService.AttachmentEntryQuery attachmentQuery = AttachmentEntryService.AttachmentEntryQuery.builder()
				.referencedRecord(TableRecordReference.of(record))
				.mimeType(MimeType.TYPE_PDF)
				.build();

		final List<AttachmentEntry> attachments = attachmentEntryService.getByQuery(attachmentQuery);
		if (!attachments.isEmpty())
		{
			final AttachmentEntry attachment = attachments.get(0); // take first one
			final Resource data = new ByteArrayResource(attachmentEntryService.retrieveData(attachment.getId()));

			getResult().setReportData(data, attachment.getFilename(), attachment.getMimeType());
		}

		return MSG_OK;
	}

	@Nullable
	private I_C_Async_Batch getRecord()
	{
		return queryBL.createQueryBuilder(I_C_Async_Batch.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Async_Batch.COLUMN_C_Async_Batch_ID, getRecord_ID())
				.create()
				.first();
	}

}
