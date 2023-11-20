/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workflow.process;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_AttachmentEntry_ReferencedRecord_v;
import org.springframework.core.io.ByteArrayResource;

import javax.annotation.Nullable;
import java.util.Comparator;

public class AD_WF_Approval_Request_Download_File extends JavaProcess
{
	private final transient AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	@Override
	protected String doIt()
	{
		final I_AD_AttachmentEntry_ReferencedRecord_v firstSelectedLine = getFirstSelectedLine();
		if (firstSelectedLine != null)
		{
			final AttachmentEntryId entryId = AttachmentEntryId.ofRepoId(firstSelectedLine.getAD_AttachmentEntry_ID());

			final AttachmentEntry entry = attachmentEntryService.getById(entryId);
			final byte[] data = attachmentEntryService.retrieveData(entry.getId());

			getResult().setReportData(new ByteArrayResource(data), entry.getFilename(), entry.getMimeType());
		}

		return MSG_OK;
	}

	@Nullable
	protected final I_AD_AttachmentEntry_ReferencedRecord_v getFirstSelectedLine()
	{
		return getSelectedIncludedRecords(I_AD_AttachmentEntry_ReferencedRecord_v.class)
				.stream()
				.sorted(Comparator.comparing(I_AD_AttachmentEntry_ReferencedRecord_v::getCreated))
				.findFirst().orElse(null);
	}

}
