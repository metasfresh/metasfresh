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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMultimap;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.printing.HardwarePrinter;
import de.metas.printing.HardwareTray;
import de.metas.printing.OutputType;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PrintingDataToPDFFileStorer
{
	private final static Logger logger = LogManager.getLogger(PrintingDataToPDFFileStorer.class);

	@VisibleForTesting
	final static String SYSCONFIG_STORE_PDF_BASE_DIRECTORY = "de.metas.printing.StorePDFBaseDirectory";
	final static String SYSCONFIG_STORE_PDF_INCLUDE_SYSTEM_TIME_MS_IN_FILENAME = "de.metas.printing.IncludeSystemTimeMSInFileName";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public void storeInFileSystem(@NonNull final PrintingData printingData)
	{
		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(I_C_Printing_Queue.Table_Name, printingData.getPrintingQueueItemId()))
		{
			storeInFileSystem0(printingData);
		}
		catch (final RuntimeException rte)
		{
			throw AdempiereException.wrapIfNeeded(rte)
					.appendParametersToMessage()
					.setParameter("C_Queue_PrintingQueue", printingData.getPrintingQueueItemId());
		}
	}

	private void storeInFileSystem0(@NonNull final PrintingData printingData)
	{
		final String baseDirectory = getBaseDirectory(printingData);

		final ImmutableMultimap<Path, PrintingSegment> path2Segments = extractAndAssignPaths(baseDirectory, printingData);
		logger.debug("Segments of given printingData are divided into {} different files; printingData={}", path2Segments.size(), printingData);

		for (final Path path : path2Segments.keySet())
		{
			final File file = new File(path.toFile(), extractFileName(printingData));

			createDirectories(path);
			try (final PrintingDataToPDFWriter printingDataToPDFWriter = new PrintingDataToPDFWriter(new FileOutputStream(file)))
			{
				for (final PrintingSegment segment : path2Segments.get(path))
				{
					logger.debug("Going to store PrintingSegment to file={}; segment={}", file, segment);
					printingDataToPDFWriter.addArchivePartToPDF(printingData, segment);
				}
			}
			catch (final FileNotFoundException e)
			{
				throw new AdempiereException("FileNotFoundException trying to write printing data to file", e)
						.setParameter("file", file);
			}
		}
	}

	@NonNull
	private String extractFileName(final @NonNull PrintingData printingData)
	{
		final boolean includeSystemTimeMS = sysConfigBL.getBooleanValue(
				SYSCONFIG_STORE_PDF_INCLUDE_SYSTEM_TIME_MS_IN_FILENAME,
				true /*defaultValue*/,
				ClientId.METASFRESH.getRepoId(),
				printingData.getOrgId().getRepoId());

		final StringBuilder result = new StringBuilder();
		if (includeSystemTimeMS)
		{
			result
					.append(SystemTime.millis())
					.append("_");
		}
		return FileUtil.stripIllegalCharacters(result
				.append(printingData.getDocumentFileName())
				.toString());
	}

	@NonNull
	private String getBaseDirectory(final @NonNull PrintingData printingData)
	{
		final String sysconfigDirectory = sysConfigBL.getValue(SYSCONFIG_STORE_PDF_BASE_DIRECTORY, ClientId.METASFRESH.getRepoId(), printingData.getOrgId().getRepoId());
		if (Check.isNotBlank(sysconfigDirectory))
		{
			return sysconfigDirectory;
		}

		final String tempDir = System.getProperty("java.io.tmpdir");
		logger.debug("AD_SysConfig {} is not set; -> use temp-dir {} as base directory", SYSCONFIG_STORE_PDF_BASE_DIRECTORY, tempDir);
		return tempDir;
	}

	private void createDirectories(@NonNull final Path path)
	{
		try
		{
			Files.createDirectories(path);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("IOException trying to create output directory", e)
					.setParameter("path", path);
		}
	}

	private ImmutableMultimap<Path, PrintingSegment> extractAndAssignPaths(
			@NonNull final String baseDirectory,
			@NonNull final PrintingData printingData)
	{
		final ImmutableMultimap.Builder<Path, PrintingSegment> path2Segments = new ImmutableMultimap.Builder<>();

		for (final PrintingSegment segment : printingData.getSegments())
		{
			final HardwarePrinter printer = segment.getPrinter();
			if (!OutputType.Store.equals(printer.getOutputType()))
			{
				logger.debug("Printer with id={} has outputType={}; -> skipping it", printer.getId().getRepoId(), printer.getOutputType());
				continue;
			}
			final Path path;
			if (segment.getTrayId() != null)
			{
				final HardwareTray tray = printer.getTray(segment.getTrayId());
				path = Paths.get(baseDirectory,
						FileUtil.stripIllegalCharacters(printer.getName()),
						FileUtil.stripIllegalCharacters(tray.getTrayNumber() + "-" + tray.getName()));
			}
			else
			{
				path = Paths.get(baseDirectory,
						FileUtil.stripIllegalCharacters(printer.getName()));
			}

			path2Segments.put(path, segment);
		}
		return path2Segments.build();
	}
}
