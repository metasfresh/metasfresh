package de.metas.attachments.process;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.process.JavaProcess;
import de.metas.util.Optionals;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_AttachmentEntry_ReferencedRecord_v;
import org.springframework.core.io.ByteArrayResource;

import java.util.Comparator;
import java.util.Optional;

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

public class AD_AttachmentEntry_ReferencedRecord_v_Download extends JavaProcess
{
	private final transient AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	@Override
	protected String doIt()
	{
		Optionals.firstPresentOfSuppliers(
						this::getAttachmentEntryFromHeader,
						this::getFirstSelectedLine
				)
				.ifPresent(this::openAttachmentEntry);

		return MSG_OK;
	}

	private Optional<AttachmentEntryId> getAttachmentEntryFromHeader()
	{
		return getProcessInfo()
				.getRecordIfApplies(I_AD_AttachmentEntry_ReferencedRecord_v.class, ITrx.TRXNAME_ThreadInherited)
				.map(AD_AttachmentEntry_ReferencedRecord_v_Download::extractAttachmentEntryId);
	}

	private Optional<AttachmentEntryId> getFirstSelectedLine()
	{
		return getSelectedIncludedRecords(I_AD_AttachmentEntry_ReferencedRecord_v.class)
				.stream()
				.sorted(Comparator.comparing(I_AD_AttachmentEntry_ReferencedRecord_v::getCreated))
				.map(AD_AttachmentEntry_ReferencedRecord_v_Download::extractAttachmentEntryId)
				.findFirst();
	}

	@NonNull
	private static AttachmentEntryId extractAttachmentEntryId(@NonNull final I_AD_AttachmentEntry_ReferencedRecord_v record)
	{
		return AttachmentEntryId.ofRepoId(record.getAD_AttachmentEntry_ID());
	}

	private void openAttachmentEntry(final AttachmentEntryId entryId)
	{
		final AttachmentEntry entry = attachmentEntryService.getById(entryId);
		final byte[] data = attachmentEntryService.retrieveData(entry.getId());
		getResult().setReportData(new ByteArrayResource(data), entry.getFilename(), entry.getMimeType());
	}
}
