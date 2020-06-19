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

import com.google.common.collect.ImmutableMultimap;
import de.metas.logging.LogManager;
import de.metas.printing.HardwarePrinter;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.HardwareTray;
import de.metas.printing.OutputType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PrintingDataToPDFFileStorer
{
	private final static transient Logger logger = LogManager.getLogger(PrintingDataToPDFFileStorer.class);

	private final HardwarePrinterRepository hardwarePrinterRepository;

	public PrintingDataToPDFFileStorer(@NonNull final HardwarePrinterRepository hardwarePrinterRepository)
	{
		this.hardwarePrinterRepository = hardwarePrinterRepository;
	}

	public void storeInFileSystem(@NonNull final PrintingData printingData)
	{
		final String baseDirectory = Services.get(ISysConfigBL.class).getValue("de.metas.printing.StorePDFBaseDirectory", ClientId.METASFRESH.getRepoId(), printingData.getOrgId().getRepoId());
		final ImmutableMultimap<Path, PrintingSegment> path2Segments = extractAndAssignPaths(baseDirectory, printingData);

		for (final Path path : path2Segments.keySet())
		{
			final File file = new File(path.toFile(), printingData.getDocumentName() + ".pdf");

			try (final PrintingDataToPDFWriter printingDataToPDFWriter = new PrintingDataToPDFWriter(new FileOutputStream(file)))
			{
				for (final PrintingSegment segment : path2Segments.get(path))
				{
					printingDataToPDFWriter.addArchivePartToPDF(printingData, segment);
				}
			}
			catch (final FileNotFoundException fnfe)
			{
				throw new AdempiereException("FileNotFoundException trying to write printing data to file", fnfe)
						.appendParametersToMessage()
						.setParameter("file", file)
						.setParameter("C_Queue_PrintingQueue", printingData.getPrintingQueueItemId());
			}
		}

	}

	private ImmutableMultimap<Path, PrintingSegment> extractAndAssignPaths(
			@NonNull final String baseDirectory,
			@NonNull final PrintingData printingData)
	{
		final ImmutableMultimap.Builder<Path, PrintingSegment> path2Segments = new ImmutableMultimap.Builder<>();

		for (final PrintingSegment segment : printingData.getSegments())
		{
			final HardwarePrinterId printerId = segment.getPrinterId();
			final HardwarePrinter printer = hardwarePrinterRepository.getById(printerId);
			if (!OutputType.Store.equals(printer.getOutputType()))
			{
				logger.debug("Printer with id={} has outputType={}; -> skipping it", printerId.getRepoId(), printer.getOutputType());
				continue;
			}
			final Path path;
			if (segment.getTrayId() != null)
			{
				final HardwareTray tray = printer.getTray(segment.getTrayId());
				path = Paths.get(baseDirectory, printer.getName(), tray.getName());
			}
			else
			{
				path = Paths.get(baseDirectory, printer.getName());
			}

			path2Segments.put(path, segment);
		}
		return path2Segments.build();
	}
}
