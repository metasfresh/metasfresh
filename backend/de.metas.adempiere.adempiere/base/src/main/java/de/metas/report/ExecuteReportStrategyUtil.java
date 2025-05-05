package de.metas.report;

import com.google.common.collect.ImmutableList;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import de.metas.printing.IMassPrintingService;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportConstants;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
	public Resource executeJasperProcess(
			final int jasperProcessId,
			@NonNull final ProcessInfo processInfo,
			@NonNull final OutputType outputType)
	{
		final ProcessExecutor processExecutor = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(jasperProcessId)
				.setRecord(processInfo.getTable_ID(), processInfo.getRecord_ID())
				.addParameter(ReportConstants.REPORT_PARAM_BARCODE_URL, DocumentReportService.getBarcodeServlet(processInfo.getClientId(), processInfo.getOrgId()))
				.addParameter(IMassPrintingService.PARAM_PrintCopies, PrintCopies.ONE.toInt())
				.setArchiveReportData(false) // don't archive it! just give us the PDF data
				.setPrintPreview(false)

				// important; event though printPreview(false), we might want JasperPrint, because the result shall be shown in the jasper-viewer
				.setJRDesiredOutputType(outputType)

				.buildAndPrepareExecution()
				.onErrorThrowException(true)
				.executeSync();

		return processExecutor.getResult().getReportDataResource();
	}

	/**
	 * @deprecated Please use {@link #concatenatePDFs(ImmutableList)}.
	 */
	@Deprecated
	public Resource concatenatePDF(
			@NonNull final Resource documentPdfData,
			@NonNull final List<PdfDataProvider> pdfDataToConcatenate)
	{
		if (pdfDataToConcatenate.isEmpty())
		{
			return documentPdfData;
		}

		final PdfDataProvider pdfData = PdfDataProvider.forData(documentPdfData);

		final ImmutableList<PdfDataProvider> allPdfDataToConcatenate = ImmutableList.<PdfDataProvider>builder()
				.add(pdfData)
				.addAll(pdfDataToConcatenate)
				.build();

		return concatenatePDFs(allPdfDataToConcatenate);
	}

	/**
	 * The more sane version of {@link #concatenatePDF(Resource, List)}.
	 */
	@NonNull
	public Resource concatenatePDFs(@NonNull final ImmutableList<PdfDataProvider> pdfDataToConcatenate)
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		concatenatePDFsToOutputStream(out, pdfDataToConcatenate);

		return new ByteArrayResource(out.toByteArray());
	}

	private void concatenatePDFsToOutputStream(
			@NonNull final OutputStream outputStream,
			@NonNull final ImmutableList<PdfDataProvider> pdfDataToConcatenate)
	{
		final Document document = new Document();

		try
		{
			final PdfCopy copyDestination = new PdfCopy(document, outputStream);
			document.open();

			for (final PdfDataProvider pdfData : pdfDataToConcatenate)
			{
				appendPdfPages(copyDestination, pdfData);
			}
			document.close();
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private static void appendPdfPages(@NonNull final PdfCopy copyDestination, @NonNull final PdfDataProvider pdfData) throws IOException, BadPdfFormatException
	{
		final Resource data = pdfData.getPdfData();

		final PdfReader pdfReader = new PdfReader(data.getInputStream());

		for (int page = 0; page < pdfReader.getNumberOfPages(); )
		{
			copyDestination.addPage(copyDestination.getImportedPage(pdfReader, ++page));
		}
		copyDestination.freeReader(pdfReader);
		pdfReader.close();
	}

	@Value
	public static class PdfDataProvider
	{
		public static PdfDataProvider forData(@NonNull final Resource pdfData)
		{
			return new PdfDataProvider(pdfData);
		}

		Resource pdfData;

		private PdfDataProvider(@NonNull final Resource pdfData)
		{
			this.pdfData = pdfData;
		}
	}
}
