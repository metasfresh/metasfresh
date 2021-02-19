/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.archive.process;

import com.google.common.collect.ImmutableSet;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.archive.AdArchive;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

public class MassConcatenateOutboundPdfs extends JavaProcess implements IProcessPrecondition
{
	final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_Doc_Outbound_Log> queryFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));
		final Stream<AdArchive> pdfStreams = archiveDAO.streamArchivesForFilter(queryFilter, I_C_Doc_Outbound_Log.class);
		final File reportFile = File.createTempFile("MassConcatenateOutboundPdfs_" + getPinstanceId().getRepoId(), ".pdf");
		reportFile.deleteOnExit();
		final Document document = new Document();
		final AtomicInteger errorCount = new AtomicInteger();
		final Collection<ArchiveId> printedIds = new HashSet<>();

		try (final FileOutputStream fileOutputStream = new FileOutputStream(reportFile, false))
		{
			final PdfCopy copy = new PdfCopy(document, fileOutputStream);
			document.open();
			pdfStreams.forEach(adArchive -> appendCurrentPdf(copy, adArchive, printedIds, errorCount));
			document.close();
		}
		archiveDAO.updatePrintedRecords(ImmutableSet.copyOf(printedIds), getUserId());

		getResult().setReportData(reportFile);
		return "OK/Error # " + printedIds.size() + "/" + errorCount.get();
	}

	private void appendCurrentPdf(final PdfCopy targetPdf, final AdArchive currentLog, final Collection<ArchiveId> printedIds, final AtomicInteger errorCount)
	{
		PdfReader pdfReaderToAdd = null;
		try
		{
			pdfReaderToAdd = new PdfReader(currentLog.getArchiveStream());

			for (int page = 0; page < pdfReaderToAdd.getNumberOfPages(); )
			{
				targetPdf.addPage(targetPdf.getImportedPage(pdfReaderToAdd, ++page));
			}

			targetPdf.freeReader(pdfReaderToAdd);
		}
		catch (final BadPdfFormatException |
				IOException e)
		{
			errorCount.incrementAndGet();
		}
		finally
		{
			if (pdfReaderToAdd != null)
			{
				pdfReaderToAdd.close();
			}
			printedIds.add(currentLog.getId());
		}
	}
}
