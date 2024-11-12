/*
 * #%L
 * de.metas.swat.webui
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.archive.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import de.metas.util.StreamUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.archive.AdArchive;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class MassConcatenateOutboundPdfs extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);

	private static final int CHUNK_SIZE = 500;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Stream<AdArchive> pdfStreams = streamArchivesForFilter();
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
		archiveBL.updatePrintedRecords(ImmutableSet.copyOf(printedIds), getUserId());

		getResult().setReportData(reportFile);
		return "OK/Error # " + printedIds.size() + "/" + errorCount.get();
	}

	@NonNull
	private Stream<AdArchive> streamArchivesForFilter()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			// shall not happen
			return Stream.empty();
		}
		else if (selectedRowIds.isAll())
		{
			final Stream<? extends IViewRow> rowIdsStream = getView().streamByIds(selectedRowIds, getViewOrderBys(), QueryLimit.NO_LIMIT);
			return StreamUtils.dice(rowIdsStream, CHUNK_SIZE)
					.flatMap(this::streamArchivesForRows);
		}
		else
		{
			final ImmutableList<DocOutboundLogId> ids = selectedRowIds.toListIds(DocOutboundLogId::ofRepoId);
			return streamArchivesForLogIds(ids);
		}
	}

	private Stream<AdArchive> streamArchivesForRows(final List<? extends IViewRow> rows)
	{
		final ImmutableList<DocOutboundLogId> logIds = rows.stream()
				.map(row -> row.getId().toId(DocOutboundLogId::ofRepoId))
				.collect(ImmutableList.toImmutableList());

		return streamArchivesForLogIds(logIds);
	}

	private Stream<AdArchive> streamArchivesForLogIds(final ImmutableList<DocOutboundLogId> logIds)
	{
		return docOutboundDAO.streamByIdsInOrder(logIds)
				.filter(I_C_Doc_Outbound_Log::isActive)
				.map(log -> getLastArchive(log).orElse(null))
				.filter(Objects::nonNull);
	}

	@NonNull
	private Optional<AdArchive> getLastArchive(final I_C_Doc_Outbound_Log log)
	{
		return archiveBL.getLastArchive(TableRecordReference.ofReferenced(log));
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
