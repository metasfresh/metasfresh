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

package de.metas.printing.printingdata;

import com.google.common.collect.ImmutableList;
import com.lowagie.text.pdf.PdfReader;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.printing.OutputType;
import de.metas.printing.PrintingQueueItemId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@ToString(exclude = { "data" })
public class PrintingData
{
	// Services
	private static final Logger logger = LogManager.getLogger(PrintingData.class);

	@Getter
	private final ImmutableList<PrintingSegment> segments;

	@Getter
	private final PrintingQueueItemId printingQueueItemId;

	@Getter
	private final String documentFileName;

	// Archive's Data
	@Getter
	private transient final byte[] data;

	@Getter
	private final OrgId orgId;

	private Integer numberOfPages = null;

	@Builder
	private PrintingData(
			@Singular @NonNull final List<PrintingSegment> segments,
			@NonNull final PrintingQueueItemId printingQueueItemId,
			@NonNull final OrgId orgId,
			@Nullable final byte[] data,
			@NonNull final String documentFileName,
			@Nullable final Boolean adjustSegmentPageRanges)
	{
		this.printingQueueItemId = printingQueueItemId;
		this.data = data;
		this.orgId = orgId;
		this.documentFileName = documentFileName;
		if (CoalesceUtil.coalesce(adjustSegmentPageRanges, true))
		{
			this.segments = adjustSegmentPageRanges(this, segments);
		}
		else
		{
			this.segments = ImmutableList.copyOf(segments);
		}
	}

	private static ImmutableList<PrintingSegment> adjustSegmentPageRanges(
			@NonNull final PrintingData printingData,
			@NonNull final List<PrintingSegment> segments)
	{
		if (!printingData.hasData())
		{
			logger.info("Print Job Line's Archive has no data: {}; -> empty segment list", printingData);
			return ImmutableList.of();
		}

		// the number of pages to "divide" among our archive parts
		final int numberOfPagesAvailable = printingData.getNumberOfPages();
		// task 08958: maintain a bitmap of pages that we already assigned to archive parts, to avoid printing them more than once
		final boolean[] pagesCovered = new boolean[numberOfPagesAvailable];

		//
		// set PageFrom/PageTo for "LastPages" routings
		int lastPagesMax = 0;
		final ArrayList<PrintingSegment> lastPageSegments = new ArrayList<>();
		final HashSet<PrintingSegment> alreadyProcessed = new HashSet<>();
		for (final PrintingSegment printingSegment : segments)
		{
			if (!printingSegment.isLastPages())
			{
				continue; // we will look at last-pages-segments further down
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
				// invalid page range => skip this segment
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
			lastPageSegments.add(printingSegment);
		}

		//
		// Adjust PageFrom/PageTo for "PageRange" routings
		final ArrayList<PrintingSegment> pageRangeSegments = new ArrayList<>();
		for (final PrintingSegment printingSegment : segments)
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
			pageRangeSegments.add(printingSegment);
		}

		return ImmutableList.<PrintingSegment>builder()
				.addAll(pageRangeSegments)
				.addAll(lastPageSegments)
				.build();
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

	public boolean hasData()
	{
		return data != null;
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
			reader = new PdfReader(getData());
			numberOfPages = reader.getNumberOfPages();
			return numberOfPages;
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Cannot get number of pages for C_Printing_Queue_ID=" + printingQueueItemId.getRepoId(), e);
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

	public PrintingData onlyWithType(@NonNull final OutputType outputType)
	{
		final ImmutableList<PrintingSegment> filteredSegments = segments.stream()
				.filter(s -> Objects.equals(s.getPrinter().getOutputType(), outputType))
				.collect(ImmutableList.toImmutableList());

		return PrintingData.builder()
				.adjustSegmentPageRanges(false)
				.data(this.data)
				.documentFileName(this.documentFileName)
				.orgId(this.orgId)
				.printingQueueItemId(this.printingQueueItemId)
				.segments(filteredSegments)
				.build();
	}

	public PrintingData onlyQueuedForExternalSystems()
	{
		final ImmutableList<PrintingSegment> filteredSegments = segments.stream()
				.filter(s -> Objects.equals(s.getPrinter().getOutputType(), OutputType.Queue))
				.filter(s -> s.getPrinter().getExternalSystemParentConfigId() != null)
				.collect(ImmutableList.toImmutableList());

		return PrintingData.builder()
				.adjustSegmentPageRanges(false)
				.data(this.data)
				.documentFileName(this.documentFileName)
				.orgId(this.orgId)
				.printingQueueItemId(this.printingQueueItemId)
				.segments(filteredSegments)
				.build();
	}
}
