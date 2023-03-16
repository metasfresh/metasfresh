/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.impexp.blockstatus.process;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.blockstatus.file.BPartnerBlockFileId;
import de.metas.bpartner.blockstatus.file.BPartnerBlockFileService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

public class RemoveAttachment extends JavaProcess implements IProcessPrecondition
{
	private final BPartnerBlockFileService bPartnerBlockFileService = SpringContextHolder.instance.getBean(BPartnerBlockFileService.class);
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final BPartnerBlockFileId bPartnerBlockFileId = BPartnerBlockFileId.ofRepoId(context.getSingleSelectedRecordId());
		if (bPartnerBlockFileService.isImported(bPartnerBlockFileId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The file was already imported!");
		}

		final boolean fileAttached = attachmentEntryService.getUniqueByReferenceRecord(bPartnerBlockFileId.toRecordRef()).isPresent();
		if (!fileAttached)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No file attached!");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final TableRecordReference tableRecordReference = getProcessInfo().getRecordRefNotNull();

		final AttachmentEntry attachmentEntry = attachmentEntryService.getUniqueByReferenceRecord(tableRecordReference)
				.orElseThrow(() -> new AdempiereException("No attachment found for BPartnerBlockFile_ID=" + tableRecordReference.getRecord_ID()));

		attachmentEntryService.unattach(tableRecordReference, attachmentEntry);

		return MSG_OK;
	}
}
