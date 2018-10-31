package de.metas.payment.esr.process;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.payment.esr.dataimporter.ESRImportEnqueuer;
import de.metas.payment.esr.dataimporter.ESRImportEnqueuerDataSource;
import de.metas.payment.esr.dataimporter.ESRImportEnqueuerDuplicateFilePolicy;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;

/*
 * #%L
 * de.metas.payment.esr
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

public class ESR_Import_LoadFromAttachmentEntry
		extends JavaProcess
{
	// services
	private final transient AttachmentEntryService attachmentEntryService = Adempiere.getBean(AttachmentEntryService.class);

	@Param(mandatory = true, parameterName = "AD_AttachmentEntry_ID")
	private int p_AD_AttachmentEntry_ID;

	@Param(mandatory = true, parameterName = I_C_Async_Batch.COLUMNNAME_Name)
	private final String p_AsyncBatchName = "ESR Import";

	@Param(mandatory = true, parameterName = I_C_Async_Batch.COLUMNNAME_Description)
	private final String p_AsyncBatchDesc = "ESR Import process";

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final AttachmentEntryId attachmentEntryId = AttachmentEntryId.ofRepoId(p_AD_AttachmentEntry_ID);
		final AttachmentEntry fromAttachmentEntry = attachmentEntryService.getById(attachmentEntryId);

		final I_ESR_Import esrImport = getRecord(I_ESR_Import.class);
		ESRImportEnqueuer.newInstance()
				.esrImport(esrImport)
				.fromDataSource(ESRImportEnqueuerDataSource.builder()
						.filename(fromAttachmentEntry.getFilename())
						.content(attachmentEntryService.retrieveData(attachmentEntryId))
						.attachmentEntryId(fromAttachmentEntry.getId())
						.build())
				//
				.asyncBatchName(p_AsyncBatchName)
				.asyncBatchDesc(p_AsyncBatchDesc)
				.pinstanceId(getPinstanceId())
				//
				.loggable(this)
				//
				.duplicateFilePolicy(ESRImportEnqueuerDuplicateFilePolicy.NEVER)
				//
				.execute();

		getResult().setRecordToRefreshAfterExecution(TableRecordReference.of(esrImport));

		return MSG_OK;
	}

}
