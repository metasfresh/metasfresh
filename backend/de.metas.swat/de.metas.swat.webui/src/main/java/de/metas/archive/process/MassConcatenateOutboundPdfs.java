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
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.archive.AdArchive;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class MassConcatenateOutboundPdfs extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);

	private static final int ALL_ITEMS_LIMIT = 5000;

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
		archiveDAO.updatePrintedRecords(ImmutableSet.copyOf(printedIds), getUserId());

		getResult().setReportData(reportFile);
		return "OK/Error # " + printedIds.size() + "/" + errorCount.get();
	}

	@NonNull
	private Stream<AdArchive> streamArchivesForFilter()
	{
		final IQueryBuilder<I_C_Doc_Outbound_Log> queryBuilder = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter();

		final ImmutableList<DocOutboundLogId> ids;
		if (getSelectedRowIds().isAll())
		{
			ids = getView().getLastOrderedSelectionIds(0, ALL_ITEMS_LIMIT)
					.stream()
					.map(id -> id.toId(DocOutboundLogId::ofRepoId))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			ids = getSelectedRowIds().toListIds(DocOutboundLogId::ofRepoId);
		}

		final Map<DocOutboundLogId, Integer> orderMap = new HashMap<>();
		for (int i = 0; i < ids.size(); i++)
		{
			orderMap.put(ids.get(i), i);
		}

		return queryBuilder
				.addInArrayFilter(I_C_Doc_Outbound_Log.COLUMNNAME_C_Doc_Outbound_Log_ID, ids)
				.create()
				.iterateAndStream()
				.sorted(Comparator.comparingInt(o -> orderMap.getOrDefault(DocOutboundLogId.ofRepoId(o.getC_Doc_Outbound_Log_ID()), Integer.MAX_VALUE)))
				.map(this::getLastArchive)
				.filter(Optional::isPresent)
				.map(Optional::get);
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
