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

import com.google.common.collect.ImmutableMap;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.blockfile.BPartnerBlockFile;
import de.metas.bpartner.blockfile.BPartnerBlockFileId;
import de.metas.bpartner.blockfile.BPartnerBlockFileService;
import de.metas.impexp.process.AttachmentImportCommand;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_I_BPartner_BlockStatus;

public class ProcessAttachment extends JavaProcess implements IProcessPrecondition
{
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final BPartnerBlockFileService bPartnerBlockFileService = SpringContextHolder.instance.getBean(BPartnerBlockFileService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final BPartnerBlockFile blockFile = bPartnerBlockFileService.getById(BPartnerBlockFileId.ofRepoId(context.getSingleSelectedRecordId()));
		if (blockFile.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The file was already processed!");
		}

		final boolean fileAttached = attachmentEntryService.atLeastOnAttachmentForRecordReference(blockFile.getId().toRecordRef());

		if (!fileAttached)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No file attached!");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx // dataImportService comes with its own trx-management
	protected String doIt()
	{
		final BPartnerBlockFileId blockFileId = BPartnerBlockFileId.ofRepoId(getProcessInfo().getRecord_ID());
		final BPartnerBlockFile blockFile = bPartnerBlockFileService.getById(blockFileId);

		final TableRecordReference tableRecordReference = getProcessInfo().getRecordRefNotNull();

		final AttachmentEntry attachmentEntry = attachmentEntryService.getUniqueByReferenceRecord(tableRecordReference);

		AttachmentImportCommand.builder()
				.attachmentEntryId(attachmentEntry.getId())
				.dataImportConfigId(blockFile.getDataImportConfigId())
				.clientId(getClientId())
				.orgId(getOrgId())
				.userId(getUserId())
				.additionalParameters(computeAdditionalParams())
				.build()
				.execute();

		attachmentEntryService.unattach(blockFileId.toRecordRef(), attachmentEntry);

		return MSG_OK;
	}

	@NonNull
	private IParams computeAdditionalParams()
	{
		final ImmutableMap<String, Object> paramsMap = ImmutableMap.<String, Object>builder()
				.put(I_I_BPartner_BlockStatus.COLUMNNAME_C_BPartner_Block_File_ID, getProcessInfo().getRecord_ID())
				.build();

		return Params.ofMap(paramsMap);
	}
}
