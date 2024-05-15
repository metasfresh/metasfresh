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
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.ClientAndOrgId;
import de.metas.printing.HardwarePrinter;
import de.metas.printing.HardwareTray;
import de.metas.printing.OutputType;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.PInstanceId;
import de.metas.report.PrintCopies;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.io.CompositeOutputStream;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
public class PrintingDataToPDFFileStorer
{
	private final static Logger logger = LogManager.getLogger(PrintingDataToPDFFileStorer.class);

	@VisibleForTesting final static String SYSCONFIG_STORE_PDF_BASE_DIRECTORY = "de.metas.printing.StorePDFBaseDirectory";
	final static String SYSCONFIG_STORE_PDF_INCLUDE_SYSTEM_TIME_MS_IN_FILENAME = "de.metas.printing.IncludeSystemTimeMSInFileName";

	final static String SYSCONFIG_STORE_PDF_INCLUDE_AD_PInstance_ID_IN_FILENAME = "de.metas.printing.IncludePInstanceIdInFileName";

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
			createDirectories(path);

			try (final PrintingDataToPDFWriter printingDataToPDFWriter = new PrintingDataToPDFWriter(createOutputStream(path, printingData)))
			{
				for (final PrintingSegment segment : path2Segments.get(path))
				{
					printingDataToPDFWriter.addArchivePartToPDF(printingData, segment);
				}
			}
			catch (final IOException e)
			{
				throw new AdempiereException("Failed writing files", e)
						.setParameter("path", path)
						.setParameter("printingData", printingData);
			}
		}
	}

	@NonNull
	private OutputStream createOutputStream(@NonNull final Path directory, final @NonNull PrintingData printingData) throws IOException
	{
		// IMPORTANT: in case of copies, we have to write all the files in parallel.
		// We cannot write one file and the copy because there is NO guarantee that after writing a file, 
		// the file won't be removed by some external system.

		final Set<String> filenames = extractFileNames(printingData);
		return CompositeOutputStream.ofFilenames(directory, filenames);
	}

	@NonNull
	private Set<String> extractFileNames(final @NonNull PrintingData printingData)
	{
		final ImmutableSet.Builder<String> result = ImmutableSet.builder();

		final String originalFileName = extractOriginalFileName(printingData);
		result.add(originalFileName);

		final PrintCopies additionalCopies = printingData.getAdditionalCopies();
		if (additionalCopies.isGreaterThanOne())
		{
			final String fileBaseName = FileUtil.getFileBaseName(originalFileName);
			final String fileExtension = FileUtil.getFileExtension(originalFileName);

			for (int copy = 1; copy <= additionalCopies.toInt(); copy++)
			{
				result.add(fileBaseName + "_" + (copy + 1) + "." + fileExtension);
			}
		}

		return result.build();
	}

	@NonNull
	private String extractOriginalFileName(final @NonNull PrintingData printingData)
	{
		final StringBuilder result = new StringBuilder();

		if (printingData.getPInstanceId() != null && isIncludePInstanceIdIntoFilename(printingData))
		{
			result.append(PInstanceId.toRepoId(printingData.getPInstanceId())).append("_");
		}

		if (isIncludeSystemTimeIntoFilename(printingData))
		{
			result.append(SystemTime.millis()).append("_");
		}

		result.append(printingData.getDocumentFileName());

		return FileUtil.stripIllegalCharacters(result.toString());
	}

	private boolean isIncludePInstanceIdIntoFilename(final @NonNull PrintingData printingData)
	{
		return sysConfigBL.getBooleanValue(
				SYSCONFIG_STORE_PDF_INCLUDE_AD_PInstance_ID_IN_FILENAME,
				false /*defaultValue*/,
				ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, printingData.getOrgId()));
	}

	private boolean isIncludeSystemTimeIntoFilename(final @NonNull PrintingData printingData)
	{
		return sysConfigBL.getBooleanValue(
				SYSCONFIG_STORE_PDF_INCLUDE_SYSTEM_TIME_MS_IN_FILENAME,
				true /*defaultValue*/,
				ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, printingData.getOrgId()));
	}

	@NonNull
	private String getBaseDirectory(final @NonNull PrintingData printingData)
	{
		final String sysconfigDirectory = StringUtils.trimBlankToNull(sysConfigBL.getValue(SYSCONFIG_STORE_PDF_BASE_DIRECTORY, ClientId.METASFRESH.getRepoId(), printingData.getOrgId().getRepoId()));
		if (sysconfigDirectory != null && !"-".equals(sysconfigDirectory))
		{
			return sysconfigDirectory;
		}

		final String tempDir = FileUtil.getTempDir();
		logger.debug("AD_SysConfig {} is not set; -> use temp-dir {} as base directory", SYSCONFIG_STORE_PDF_BASE_DIRECTORY, tempDir);
		return tempDir;
	}

	private static void createDirectories(@NonNull final Path path)
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
