/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.edi.process.export.json;

import de.metas.attachments.AttachmentEntryService;
import de.metas.common.util.Check;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.postgrest.process.PostgRESTProcessExecutor;
import de.metas.process.ProcessCalledFrom;
import de.metas.report.ReportResultData;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

public abstract class EDI_Export_JSON extends PostgRESTProcessExecutor
{
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	protected abstract I_EDI_Document_Extension loadRecordOutOfTrx();

	protected abstract void saveRecord(I_EDI_Document_Extension record);

	/**
	 * Sets invoice's EDI_ExportStatus and tell metasfresh to store the result to disk, unless we are called via API.
	 */
	@Override
	protected final CustomPostgRESTParameters beforePostgRESTCall()
	{
		final I_EDI_Document_Extension record = loadRecordOutOfTrx();
		record.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_SendingStarted);
		saveRecord(record);

		final boolean calledViaAPI = isCalledViaAPI();

		return CustomPostgRESTParameters.builder()
				.storeJsonFile(!calledViaAPI)
				.expectSingleResult(true) // because we export exactly one record, we don't want the JSON to be an array
				.build();
	}

	private boolean isCalledViaAPI()
	{
		return ProcessCalledFrom.API.equals(getProcessInfo().getProcessCalledFrom());
	}

	@Override
	protected final String afterPostgRESTCall()
	{
		final ReportResultData reportData = Check.assumeNotNull(getResult().getReportData(), "reportData shall not be null after successful invocation");

		final I_EDI_Document_Extension record = loadRecordOutOfTrx();
		// note that if it was called via API, then the result will also be in API_Response_Audit, but there it will be removed after some time
		final TableRecordReference recordReference = TableRecordReference.of(record);

		addLog("Attaching result with filename {} to the {}-record with ID {}",
				reportData.getReportFilename(),
				recordReference.getTableName(),
				recordReference.getRecord_ID()
		);
		attachmentEntryService.createNewAttachment(
				record,
				reportData.getReportFilename(),
				reportData.getReportDataByteArray());

		// note that a possible C_Doc_Outbound_Log's status is updated via modelinterceptor
		record.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Sent);
		saveRecord(record);

		return MSG_OK;
	}

	@Override
	protected Exception handleException(@NonNull final Exception e)
	{
		final I_EDI_Document_Extension record = loadRecordOutOfTrx();
		
		record.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Error);
		record.setEDIErrorMsg(e.getLocalizedMessage());
		saveRecord(record);

		return AdempiereException.wrapIfNeeded(e);
	}
}

