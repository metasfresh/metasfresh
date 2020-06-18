/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.printing.api.impl;

import com.google.common.collect.ImmutableList;
import com.lowagie.text.pdf.PdfReader;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_AD_Archive;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class PrintingData
{
	// Services
	private static final transient Logger logger = LogManager.getLogger(PrintingData.class);

	private final transient IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	// Parameters
	@Getter
	private final I_AD_Archive archiveRecord;

	@Getter
	private final ImmutableList<PrintingSegment> printingSegments;

	// Archive's Data
	private boolean dataLoaded;
	private transient byte[] data;
	private Integer numberOfPages = null;
	private final HashSet<Integer> processedPrintingArchiveParts = new HashSet<>();

	@Builder
	private PrintingData(
			@NonNull final I_AD_Archive archiveRecord,
			@Singular @NonNull final List<PrintingSegment> printingSegments)
	{
		this.archiveRecord = archiveRecord;
		this.printingSegments = ImmutableList.copyOf(printingSegments);

		adjustSegmentPageRanges(this);
	}

	private static void adjustSegmentPageRanges(@NonNull final PrintingData archiveData)
	{
		if (!archiveData.hasData())
		{
			logger.info("Print Job Line's Archive has no data: {}. Skipping it", archiveData);
			return;
		}

		// the number of pages to "divide" among our archive parts
		final int numberOfPagesAvailable = archiveData.getNumberOfPages();
		// task 08958: maintain a bitmap of pages that we already assigned to archive parts, to avoid printing them more than once
		final boolean[] pagesCovered = new boolean[numberOfPagesAvailable];

		//
		// set PageFrom/PageTo for "LastPages" routings
		int lastPagesMax = 0;
		HashSet<PrintingSegment> alreadyProcessed = new HashSet<>();
		for (final PrintingSegment printingSegment : archiveData.getPrintingSegments())
		{
			if (!printingSegment.isLastPages())
			{
				continue;
			}

			int lastPages = printingSegment.getLastPages();

			// Make sure lastPages is not bigger than document's number of pages
			if (lastPages > numberOfPagesAvailable)
			{
				lastPages = numberOfPagesAvailable;
			}

			if (lastPages > lastPagesMax)
			{
				lastPagesMax = lastPages;
			}

			// Calculate PageFrom/PageTo
			final int pageTo = numberOfPagesAvailable;
			final int pageFrom = numberOfPagesAvailable - lastPages + 1;
			if (pageFrom > pageTo)
			{
				// invalid page range => skip this archive part
				alreadyProcessed.add(printingSegment);
				continue;
			}

			// 08958: compute the final range by by skipping forward (pageFrom) and backward (pageTo) to avoid page range overlap
			final int pageFromFinal = skipForward(pagesCovered, pageFrom, pageTo);
			final int pageToFinal = skipBackward(pagesCovered, pageTo, pageFrom);
			if (pageFromFinal > pageToFinal)
			{
				alreadyProcessed.add(printingSegment);
				continue;
			}
			printingSegment.setPageFrom(pageFromFinal);
			printingSegment.setPageTo(pageToFinal);
			markCovered(pagesCovered, pageFromFinal, pageToFinal);
		}

		//
		// Adjust PageFrom/PageTo for "PageRange" routings
		for (final PrintingSegment printingSegment : archiveData.getPrintingSegments())
		{
			if (alreadyProcessed.contains(printingSegment))
			{
				continue;
			}
			if (!printingSegment.isPageRange())
			{
				continue;
			}

			int pageFrom = printingSegment.getPageFrom();
			if (pageFrom <= 0)
			{
				// First page is 1 - See com.lowagie.text.pdf.PdfWriter.getImportedPage
				pageFrom = 1;
			}
			int pageTo = printingSegment.getPageTo();
			if (pageTo <= 0)
			{
				pageTo = numberOfPagesAvailable;
			}
			if (pageTo > numberOfPagesAvailable)
			{
				logger.debug("PageTo={} is greater than number of pages available; -> set pageTo to numberOfPagesAvailable={}", pageTo, numberOfPagesAvailable);
				pageTo = numberOfPagesAvailable;
			}
			if (pageFrom > pageTo)
			{
				// invalid page range => skip this archive part
				alreadyProcessed.add(printingSegment);
				continue;
			}
			final int pageFromFinal = skipForward(pagesCovered, pageFrom, pageTo);
			final int pageToFinal = skipBackward(pagesCovered, pageTo, pageFrom);
			if (pageFromFinal > pageToFinal)
			{
				alreadyProcessed.add(printingSegment);
				continue;
			}

			printingSegment.setPageFrom(pageFromFinal);
			printingSegment.setPageTo(pageToFinal);
			markCovered(pagesCovered, pageFromFinal, pageToFinal);
		}
	}

	private static int skipBackward(final boolean[] pagesCovered,
			final int pageTo,
			final int limit)
	{
		int pageToFinal = pageTo;
		for (int i = pageTo; i >= limit; i--)
		{
			if (!pagesCovered[i - 1])
			{
				break;
			}
			pageToFinal = i - 1;
		}
		return pageToFinal;
	}

	private static int skipForward(final boolean[] pagesCovered,
			final int pageFrom,
			final int limit)
	{
		final int limitToUse = Math.min(limit, pagesCovered.length);

		int pageFromFinal = pageFrom;
		for (int i = pageFrom; i <= limitToUse; i++)
		{
			if (!pagesCovered[i - 1])
			{
				break;
			}
			pageFromFinal = i + 1;
		}
		return pageFromFinal;
	}

	private static void markCovered(final boolean[] pagesCovered,
			final int pageFrom,
			final int pageTo)
	{
		for (int i = pageFrom; i <= pageTo; i++)
		{
			pagesCovered[i - 1] = true;
		}
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public byte[] getData()
	{
		if (dataLoaded)
		{
			return data;
		}

		data = archiveBL.getBinaryData(archiveRecord);
		dataLoaded = true;
		if (data == null || data.length == 0)
		{
			logger.info("Archive {} does not contain any data. Skip", archiveRecord);
			data = null;
		}
		return data;
	}

	public boolean hasData()
	{
		return getData() != null;
	}

	public PdfReader createPdfReader() throws IOException
	{
		final PdfReader reader = new PdfReader(getData());
		return reader;
	}

	public int getNumberOfPages()
	{
		if (numberOfPages != null)
		{
			return numberOfPages;
		}

		if (!hasData())
		{
			return 0;
		}

		PdfReader reader = null;
		try
		{
			reader = createPdfReader();
			numberOfPages = reader.getNumberOfPages();
			return numberOfPages;
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Cannot get number of pages for archive " + archiveRecord, e);
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (final Exception e)
				{
				}
			}
		}
	}

	// private void markProcessed(@NonNull final PrintingSegment printingArchivePart)
	// {
	// 	processedPrintingArchiveParts.add(printingArchivePart.getAD_PrinterRouting().getAD_PrinterRouting_ID());
	// }
	//
	// private ImmutableList<PrintingSegment> getUnprocessedArchiveParts()
	// {
	// 	return printingSegments
	// 			.stream()
	// 			.filter(part ->
	// 					!processedPrintingArchiveParts.contains(part.getAD_PrinterRouting().getAD_PrinterRouting_ID()))
	// 			.collect(ImmutableList.toImmutableList());
	//
	// }
}
