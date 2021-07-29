package de.metas.attachments.process;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product_AttachmentEntry_ReferencedRecord_v;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class M_Product_AttachmentEntry_ReferencedRecord_v_Download extends JavaProcess
{
	private final transient AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected String doIt()
	{

		final I_M_Product_AttachmentEntry_ReferencedRecord_v record = getRecord();
		if (record == null)
		{
			return MSG_Error;
		}
		final AttachmentEntryId entryId = AttachmentEntryId.ofRepoId(record.getAD_AttachmentEntry_ID());

		final AttachmentEntry entry = attachmentEntryService.getById(entryId);
		final byte[] data = attachmentEntryService.retrieveData(entry.getId());

		getResult().setReportData(data, entry.getFilename(), entry.getMimeType());

		return MSG_OK;
	}

	@Nullable
	private I_M_Product_AttachmentEntry_ReferencedRecord_v getRecord()
	{
		return queryBL.createQueryBuilder(I_M_Product_AttachmentEntry_ReferencedRecord_v.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_AttachmentEntry_ReferencedRecord_v.COLUMN_M_Product_AttachmentEntry_ReferencedRecord_v_ID, getRecord_ID())
				.create()
				.first();
	}
}
