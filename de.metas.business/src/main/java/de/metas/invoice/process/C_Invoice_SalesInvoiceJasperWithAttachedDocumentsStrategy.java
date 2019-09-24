package de.metas.invoice.process;

import java.util.List;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentEntryService.AttachmentEntryQuery;
import de.metas.attachments.AttachmentTags;
import de.metas.invoice.InvoiceId;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.ExecuteReportStrategyUtil;
import de.metas.report.ExecuteReportStrategyUtil.PdfDataProvider;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class C_Invoice_SalesInvoiceJasperWithAttachedDocumentsStrategy implements ExecuteReportStrategy
{

	private static final Logger logger = LogManager.getLogger(C_Invoice_SalesInvoiceJasperWithAttachedDocumentsStrategy.class);

	private static final String C_INVOICE_REPORT_AD_PROCESS_ID = "de.metas.invoice.C_Invoice.SalesInvoice.Report.AD_Process_ID";

	private final AttachmentEntryService attachmentEntryService;

	public C_Invoice_SalesInvoiceJasperWithAttachedDocumentsStrategy(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	@Override
	public ExecuteReportResult executeReport(
			@NonNull final ProcessInfo processInfo,
			@NonNull final OutputType outputType)
	{
		final int invoiceDocReportProcessId = Services.get(ISysConfigBL.class)
				.getIntValue(
						C_INVOICE_REPORT_AD_PROCESS_ID,
						-1,
						Env.getAD_Client_ID(),
						Env.getAD_Org_ID(Env.getCtx()));
		Check.assume(invoiceDocReportProcessId > 0, "The sysconfig with name {} needs to have a value greater than 0; AD_Client_ID={}; AD_Org_ID={}", C_INVOICE_REPORT_AD_PROCESS_ID, Env.getAD_Client_ID(), Env.getAD_Org_ID(Env.getCtx()));
		final byte[] invoiceData = ExecuteReportStrategyUtil.executeJasperProcess(invoiceDocReportProcessId, processInfo, outputType);

		final boolean isPDF = OutputType.PDF.equals(outputType);
		if (!isPDF)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("Concatenating additional PDF-Data is not supported with outputType={}; returning only the jasper data itself.", outputType);
			return new ExecuteReportResult(outputType, invoiceData);
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(processInfo.getRecord_ID());

		final AttachmentEntryQuery attachmentQuery = AttachmentEntryQuery.builder()
				.referencedRecord(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId))
				.tagSetToTrue(AttachmentTags.TAGNAME_CONCATENATE_PDF_TO_INVOICE_PDF)
				.mimeType(MimeType.TYPE_PDF)
				.build();

		final List<AttachmentEntry> attachments = attachmentEntryService.getByQuery(attachmentQuery);
		final ImmutableList<PdfDataProvider> additionalPdfData = attachments.stream()
				.map(AttachmentEntry::getId)
				.map(attachmentEntryService::retrieveData)
				.map(PdfDataProvider::forData)
				.collect(ImmutableList.toImmutableList());

		final byte[] result = ExecuteReportStrategyUtil.concatenatePDF(invoiceData, additionalPdfData);

		return new ExecuteReportResult(outputType, result);
	}

}
