package de.metas.dunning.process;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;
import org.compiere.print.ReportEngine;
import org.compiere.util.Env;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import de.metas.adempiere.report.jasper.JasperConstants;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.invoice.DunningService;
import de.metas.print.IPrintService;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.dunning
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public class C_DunningDoc_JasperWithInvoicePDFsStrategy implements ExecuteReportStrategy
{
	private final transient  IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final transient IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	private final transient int dunningDocJasperProcessId;

	public C_DunningDoc_JasperWithInvoicePDFsStrategy(final int dunningDocJasperProcessId)
	{
		this.dunningDocJasperProcessId = dunningDocJasperProcessId;
	}

	@Override
	public ExecuteReportResult executeReport(
			@NonNull final ProcessInfo processInfo,
			@NonNull final OutputType outputType)
	{
		final DunningDocId dunningDocId = DunningDocId.ofRepoId(processInfo.getRecord_ID());

		final ProcessExecutor processExecutor = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(dunningDocJasperProcessId)
				.setRecord(processInfo.getTable_ID(), processInfo.getRecord_ID())
				.addParameter(JasperConstants.REPORT_PARAM_BARCODE_URL, ReportEngine.getBarcodeServlet(Env.getCtx()))
				.addParameter(IPrintService.PARAM_PrintCopies, 1)
				.setPrintPreview(true) // don't archive it! just give us the PDF data
				.buildAndPrepareExecution()
				.onErrorThrowException(true)
				.executeSync();

		final byte[] dunningDocPdfData = processExecutor.getResult().getReportData();

		final DunningService dunningService = Adempiere.getBean(DunningService.class);
		final List<I_C_Invoice> dunnedInvoices = dunningService.retrieveDunnedInvoices(dunningDocId);
		final byte[] data = concatenate(dunningDocPdfData, dunnedInvoices);

		return new ExecuteReportResult(outputType, data);
	}

	private byte[] concatenate(
			@NonNull final byte[] dunningDocPdfData,
			@NonNull final List<I_C_Invoice> dunnedInvoices)
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		print(out, dunningDocPdfData, dunnedInvoices);
		return out.toByteArray();
	}

	private void print(
			@NonNull final OutputStream bos,
			@NonNull final byte[] dunningDocPdfData,
			@NonNull final List<I_C_Invoice> dunnedInvoices)
	{
		final Document document = new Document();

		try
		{
			final PdfCopy copy = new PdfCopy(document, bos);
			document.open();

			final PdfReader dunningDocDataReader = new PdfReader(dunningDocPdfData);

			for (int page = 0; page < dunningDocDataReader.getNumberOfPages();)
			{
				copy.addPage(copy.getImportedPage(dunningDocDataReader, ++page));
			}
			copy.freeReader(dunningDocDataReader);
			dunningDocDataReader.close();

			for (final I_C_Invoice invoice : dunnedInvoices)
			{
				final List<I_AD_Archive> invoiceArchives = archiveDAO.retrieveLastArchives(Env.getCtx(), TableRecordReference.of(invoice), 1);
				if (invoiceArchives.isEmpty())
				{
					continue;
				}

				final byte[] data = archiveBL.getBinaryData(invoiceArchives.get(0));

				final PdfReader invoiceDocDataReader = new PdfReader(data);

				for (int page = 0; page < invoiceDocDataReader.getNumberOfPages();)
				{
					copy.addPage(copy.getImportedPage(invoiceDocDataReader, ++page));
				}
				copy.freeReader(invoiceDocDataReader);
				invoiceDocDataReader.close();
			}
			document.close();

		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}