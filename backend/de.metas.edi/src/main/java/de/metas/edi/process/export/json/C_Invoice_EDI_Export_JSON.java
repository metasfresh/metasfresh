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
import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_EDI_Document;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.postgrest.process.PostgRESTProcessExecutor;
import de.metas.process.Param;
import de.metas.process.ProcessCalledFrom;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

/**
 * Exports one particular invoice to JSON.
 * It directs {@link PostgRESTProcessExecutor} to store the result to disk if not called via API.
 * It also attaches the resulting JSON file to the invoice and sets the invoice's {@code EDI_ExportStatus} to "Sent".
 */
public class C_Invoice_EDI_Export_JSON extends PostgRESTProcessExecutor
{
	public static final String PARAM_C_INVOICE_ID = "C_Invoice_ID";
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	@Param(parameterName = PARAM_C_INVOICE_ID, mandatory = true)
	private int c_invoice_id;

	private I_C_Invoice invoiceRecord;

	/**
	 * Sets invoice's EDI_ExportStatus and tell metasfresh to store the result to disk, unless we are called via API.
	 */
	@Override
	protected PostgRESTProcessExecutor.CustomPostgRESTParameters beforePostgRESTCall()
	{
		invoiceRecord = invoiceDAO.getByIdOutOfTrx(InvoiceId.ofRepoId(c_invoice_id), I_C_Invoice.class);
		invoiceRecord.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_SendingStarted);
		invoiceDAO.save(invoiceRecord);

		final boolean calledViaAPI = ProcessCalledFrom.API.equals(getProcessInfo().getProcessCalledFrom());

		return CustomPostgRESTParameters.builder().storeJsonFile(!calledViaAPI).build();
	}

	@Override
	protected String afterPostgRESTCall()
	{
		final ReportResultData reportData = Check.assumeNotNull(getResult().getReportData(), "reportData shall not be null after successful invocation");

		attachmentEntryService.createNewAttachment(
				invoiceRecord,
				reportData.getReportFilename(),
				reportData.getReportDataByteArray());

		// note that a possible C_Doc_Outbound_Log's status is updated via modelinterceptor
		invoiceRecord.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Sent);
		invoiceDAO.save(invoiceRecord);

		return MSG_OK;
	}

	@Override
	protected Exception handleException(final Exception e)
	{
		invoiceRecord.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Error);
		invoiceRecord.setEDIErrorMsg(e.getLocalizedMessage());
		invoiceDAO.save(invoiceRecord);

		throw AdempiereException.wrapIfNeeded(e);
	}
}
