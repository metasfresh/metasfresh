package de.metas.payment.esr.process;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.attachments.AttachmentEntryId;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.RunESRImportRequest;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;

import static de.metas.payment.esr.ESRConstants.ESR_ASYNC_BATCH_DESC;
import static de.metas.payment.esr.ESRConstants.ESR_ASYNC_BATCH_NAME;

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
	@Param(mandatory = true, parameterName = "AD_AttachmentEntry_ID")
	private int p_AD_AttachmentEntry_ID;

	@Param(mandatory = true, parameterName = I_C_Async_Batch.COLUMNNAME_Name)
	private final String p_AsyncBatchName = ESR_ASYNC_BATCH_NAME;

	@Param(mandatory = true, parameterName = I_C_Async_Batch.COLUMNNAME_Description)
	private final String p_AsyncBatchDesc = ESR_ASYNC_BATCH_DESC;

	private final IESRImportBL esrImportBL = Services.get(IESRImportBL.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final AttachmentEntryId attachmentEntryId = AttachmentEntryId.ofRepoId(p_AD_AttachmentEntry_ID);
		final I_ESR_Import esrImport = getRecord(I_ESR_Import.class);

		final RunESRImportRequest runESRImportRequest = RunESRImportRequest.builder()
				.esrImport(esrImport)
				.attachmentEntryId(attachmentEntryId)
				.asyncBatchDescription(p_AsyncBatchDesc)
				.asyncBatchName(p_AsyncBatchName)
				.loggable(this)
				.build();

		esrImportBL.scheduleESRImportFor(runESRImportRequest);

		getResult().setRecordToRefreshAfterExecution(TableRecordReference.of(esrImport));

		return MSG_OK;
	}

}
