package de.metas.report;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.print.ReportEngine;
import org.compiere.util.Env;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import de.metas.print.IPrintService;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.report.server.ReportConstants;
import de.metas.report.server.OutputType;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@UtilityClass
public class ExecuteReportStrategyUtil
{
	public byte[] executeJasperProcess(
			final int jasperProcessId,
			@NonNull final ProcessInfo processInfo,
			@NonNull final OutputType outputType)
	{
		final ProcessExecutor processExecutor = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(jasperProcessId)
				.setRecord(processInfo.getTable_ID(), processInfo.getRecord_ID())
				.addParameter(ReportConstants.REPORT_PARAM_BARCODE_URL, ReportEngine.getBarcodeServlet(Env.getCtx()))
				.addParameter(IPrintService.PARAM_PrintCopies, 1)
				.setArchiveReportData(false) // don't archive it! just give us the PDF data
				.setPrintPreview(false) 
				
				// important; event though printPreview(false), we might want JasperPrint, because the result shall be shown in the jasper-viewer
				.setJRDesiredOutputType(outputType)
				
				.buildAndPrepareExecution()
				.onErrorThrowException(true)
				.executeSync();

		final byte[] processPdfData = processExecutor.getResult().getReportData();
		return processPdfData;
	}

	public byte[] concatenatePDF(
			@NonNull final byte[] documentPdfData,
			@NonNull final List<PdfDataProvider> additionalDataItemsToAttach)
	{
		if(additionalDataItemsToAttach.isEmpty())
		{
			return documentPdfData;
		}
		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		print(out, documentPdfData, additionalDataItemsToAttach);

		return out.toByteArray();
	}

	private void print(
			@NonNull final OutputStream bos,
			@NonNull final byte[] documentPdfData,
			@NonNull final List<PdfDataProvider> additionalDataItemsToAttach)
	{
		final Document document = new Document();

		try
		{
			final PdfCopy copy = new PdfCopy(document, bos);
			document.open();

			final PdfReader dunningDocDataReader = new PdfReader(documentPdfData);

			for (int page = 0; page < dunningDocDataReader.getNumberOfPages();)
			{
				copy.addPage(copy.getImportedPage(dunningDocDataReader, ++page));
			}
			copy.freeReader(dunningDocDataReader);
			dunningDocDataReader.close();

			for (final PdfDataProvider additionalDataItemToAttach : additionalDataItemsToAttach)
			{
				final byte[] data = additionalDataItemToAttach.getPdfData();

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

	@Value
	public static class PdfDataProvider
	{
		public static PdfDataProvider forData(@NonNull final byte[] pdfData)
		{
			return new PdfDataProvider(pdfData);
		}

		byte[] pdfData;

		private PdfDataProvider(@NonNull final byte[] pdfData)
		{
			this.pdfData = pdfData;
		}
	}
}
