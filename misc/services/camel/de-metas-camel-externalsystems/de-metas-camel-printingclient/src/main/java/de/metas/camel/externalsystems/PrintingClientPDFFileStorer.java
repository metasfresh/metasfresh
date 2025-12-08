/*
 * #%L
 * de-metas-camel-printingclient
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems;

import com.google.common.collect.ImmutableMultimap;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import de.metas.common.rest_api.v2.printing.response.JsonPrinterHW;
import de.metas.common.rest_api.v2.printing.response.JsonPrinterTray;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingData;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingDataResponse;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingSegment;
import de.metas.common.util.FileUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
public class PrintingClientPDFFileStorer
{
	private static final String OUTPUTTYPE_Queue = "Queue";

	public void storeInFileSystem(@NonNull final JsonPrintingDataResponse printingDataResponse, @NonNull final String baseDirectory) throws PrintingException
	{
		for(final JsonPrintingData printingData : printingDataResponse.getJsonPrintingDataList())
		{
			final ImmutableMultimap<Path, JsonPrintingSegment> path2Segments = extractAndAssignPaths(printingData, baseDirectory);

			for (final Path path : path2Segments.keySet())
			{
				final File file = new File(path.toFile(), printingData.getDocumentFileName());


				createDirectories(path);

				final FileOutputStream out;
				try
				{
					out = new FileOutputStream(file);
				}
				catch (final FileNotFoundException e)
				{
					throw new PrintingException("FileNotFoundException trying to write printing data to file: " + file, e);
				}

				final Document document = new Document();
				final PdfCopy pdfCopy;
				try
				{
					pdfCopy = new PdfCopy(document, out);
				}
				catch (final DocumentException e)
				{
					throw new PrintingException("DocumentException", e);
				}

				document.open();
				for (final JsonPrintingSegment segment : path2Segments.get(path))
				{
					for (int i = 0; i < segment.getCopies(); i++)
					{
						final byte[] data = Base64.getDecoder().decode(printingData.getBase64Data());
						final PdfReader reader;
						try
						{
							reader = new PdfReader(data);
						}
						catch (final IOException e)
						{
							throw new PrintingException("IOException in PDFReader", e);
						}

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
							pageTo = archivePageNums;
						}

						if (pageFrom > pageTo)
						{
							// shall not happen at this point
							return;
						}

						for (int page = pageFrom; page <= pageTo; page++)
						{
							try
							{
								pdfCopy.addPage(pdfCopy.getImportedPage(reader, page));
								pdfCopy.freeReader(reader);
							}
							catch (final BadPdfFormatException e)
							{
								throw new PrintingException("BadPdfFormatException " + segment + " (Page: " + page + ")", e);
							}
							catch (final IOException e)
							{
								throw new PrintingException("IOException in PdfReader", e);
							}
						}
						reader.close();
					}
					document.close();

					try
					{
						out.close();
					}
					catch (final IOException e)
					{
						throw new PrintingException("IOException on file output stream close", e);
					}
				}
			}
		}
	}

	private void createDirectories(@NonNull final Path path) throws PrintingException
	{
		try
		{
			Files.createDirectories(path);
		}
		catch (final IOException e)
		{
			throw new PrintingException("IOException trying to create output directory: " + path, e);
		}
	}

	private ImmutableMultimap<Path, JsonPrintingSegment> extractAndAssignPaths(
			@NonNull final JsonPrintingData printingData,
			@NonNull final String baseDirectory)
	{
		final ImmutableMultimap.Builder<Path, JsonPrintingSegment> path2Segments = new ImmutableMultimap.Builder<>();

		for (final JsonPrintingSegment segment : printingData.getSegments())
		{
			final JsonPrinterHW printer = segment.getPrinterHW();
			if (!OUTPUTTYPE_Queue.equals(printer.getOutputType()))
			{
				continue;
			}

			Path path = null;
			final int trayId = segment.getTrayId();

			if (trayId > 0)
			{
				final List<JsonPrinterTray> trays = printer.getTrays();
				for (final JsonPrinterTray tray : trays)
				{
					if(tray.getTrayId() == trayId)
					{
						path = Paths.get(baseDirectory,
										 FileUtil.stripIllegalCharacters(printer.getName()),
										 FileUtil.stripIllegalCharacters(tray.getName())) // don't use the number for the path, because we want to control it entirely with the tray name
						;
						break;
					}
				}
				if(path == null)
				{
					throw new PrintingException("Shouldn't happen. Segment has TrayId, that doesn't exist in Trays of PrinterHW");
				}
			}
			else
			{
				path = Paths.get(baseDirectory, FileUtil.stripIllegalCharacters(printer.getName()));
			}

			path2Segments.put(path, segment);
		}
		return path2Segments.build();
	}
}
