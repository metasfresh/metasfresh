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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import de.metas.logging.LogManager;
import de.metas.printing.exception.PrintingQueueAggregationException;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

public class PrintingDataToPDFWriter implements IAutoCloseable
{
	private final static Logger logger = LogManager.getLogger(PrintingDataToPDFWriter.class);

	private final PdfCopy pdfCopy;
	private final Document document;

	public PrintingDataToPDFWriter(@NonNull final OutputStream out)
	{
		document = new Document();
		try
		{
			pdfCopy = new PdfCopy(document, out);
		}
		catch (final DocumentException e)
		{
			throw new AdempiereException(e);
		}
		document.open();
	}

	public int addArchivePartToPDF(@NonNull final PrintingData data, @NonNull final PrintingSegment segment)
	{
		try
		{
			return addArchivePartToPDF0(data, segment);
		}
		catch (final Exception e)
		{
			throw new PrintingQueueAggregationException(data.getPrintingQueueItemId().getRepoId(), e);
		}
	}

	private int addArchivePartToPDF0(@NonNull final PrintingData data, @NonNull final PrintingSegment segment) throws IOException
	{
		if (!data.hasData())
		{
			logger.info("PrintingData {} does not contain any data; -> returning", data);
			return 0;
		}
		logger.debug("Adding data={}; segment={}", data, segment);

		int pagesAdded = 0;
		for (int i = 0; i < segment.getCopies(); i++)
		{
			final PdfReader reader = new PdfReader(data.getData());
			final int archivePageNums = reader.getNumberOfPages();
			
			int pageFrom = segment.getPageFrom();
			if (pageFrom <= 0)
			{
				// First page is 1 - See com.lowagie.text.pdf.PdfWriter.getImportedPage
				pageFrom = 1;
			}
			int pageTo = segment.getPageTo();
			if (pageTo > archivePageNums)
			{
				// shall not happen at this point
				logger.debug("Page to ({}) is greater then number of pages. Considering number of pages: {}", new Object[] { pageTo, archivePageNums });
				pageTo = archivePageNums;
			}
			if (pageFrom > pageTo)
			{
				// shall not happen at this point
				logger.warn("Page from ({}) is greater then Page to ({}). Skipping: {}", pageFrom, pageTo, segment);
				return 0;
			}

			logger.debug("PageFrom={}, PageTo={}, NumberOfPages={}", pageFrom, pageTo, archivePageNums);

			for (int page = pageFrom; page <= pageTo; page++)
			{
				try
				{
					pdfCopy.addPage(pdfCopy.getImportedPage(reader, page));
				}
				catch (final BadPdfFormatException e)
				{
					throw new AdempiereException("@Invalid@ " + segment + " (Page: " + page + ")", e);
				}
				pagesAdded++;
			}

			pdfCopy.freeReader(reader);
			reader.close();
		}


		logger.debug("Added {} pages", pagesAdded);
		return pagesAdded;
	}

	@Override
	public void close()
	{
		document.close();
	}
}
