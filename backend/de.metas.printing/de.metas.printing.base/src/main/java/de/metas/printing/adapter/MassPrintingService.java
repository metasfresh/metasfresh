package de.metas.printing.adapter;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.logging.LogManager;
import de.metas.printing.IMassPrintingService;
import de.metas.printing.model.I_AD_Archive;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.ExecuteReportStrategy.ExecuteReportResult;
import de.metas.report.PrintCopies;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.archive.api.ArchiveInfo;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public final class MassPrintingService implements IMassPrintingService
{
	private static final Logger logger = LogManager.getLogger(MassPrintingService.class);

	/**
	 * Exports the given data to PDF and creates an <code>AD_Archive</code> with <code>IsDirectPrint='Y'</code> (to trigger a <code>C_Printing_Queue</code> record being created) and
	 * <code>IsCreatePrintJob='Y'</code> to also trigger a direct <code>C_PrintJob</code> creation.
	 */
	@Override
	public void print(
			@NonNull final ExecuteReportResult executeReportResult,
			@NonNull final ProcessInfo processInfo)
	{
		// services
		final IArchiveBL archiveService = Services.get(IArchiveBL.class);

		final ArchiveInfo archiveInfo = extractArchiveInfo(processInfo);

		//
		// Create the archive

		// archive it even if AutoArchive says no
		final boolean forceArchiving = true;

		// task 09752: don't let the API save it,
		// because we want to first set the two flags below, and we want the model interceptor to be fired only once
		final boolean save = false;

		final I_AD_Archive archive = InterfaceWrapperHelper.create(
				archiveService.archive(
						executeReportResult.getReportData(),
						archiveInfo,
						forceArchiving,
						save,
						ITrx.TRXNAME_None),
				I_AD_Archive.class);

		Check.assumeNotNull(archive,
				"archive not null; executeReportResult={}; processInfo={}",
				executeReportResult, processInfo);

		//
		// Ask our printing service to printing it right now
		archive.setIsDirectEnqueue(true);
		archive.setIsDirectProcessQueueItem(true); // create the print job or store PDF; not only enqueue to printing queue

		// https://github.com/metasfresh/metasfresh/issues/1240
		// store the printInfos number of copies for this archive record. It doesn't make sense to persist this value,
		// but it needs to be available in case the system has to create a printing queue item for this archive
		IArchiveBL.COPIES_PER_ARCHIVE.setValue(archive, archiveInfo.getCopies());

		//
		// Save archive. This will trigger the printing...
		InterfaceWrapperHelper.save(archive);
		logger.debug("Archive: {}", archive);
	}

	private ArchiveInfo extractArchiveInfo(@NonNull final ProcessInfo pi)
	{
		// make sure that we never have zero copies. Apparently metasfresh
		// thinks of "copies" as the number of printouts _additional_ to the
		// original document while the java printing API thinks of copies as
		// the absolute number of printouts and thus doesn't accept any
		// number <=0.
		PrintCopies numberOfPrintouts = PrintCopies.ONE;
		ArchiveInfo archiveInfo = null;

		if (pi.getParameter() != null)
		{
			for (final ProcessInfoParameter param : pi.getParameter())
			{
				final String parameterName = param.getParameterName();
				final Object objParam = param.getParameter();

				if (objParam == null)
				{
					continue;
				}

				if (objParam instanceof ArchiveInfo)
				{
					archiveInfo = (ArchiveInfo)objParam;
					numberOfPrintouts = archiveInfo.getCopies();

					if (numberOfPrintouts.isZero())
					{
						logger.debug("Setting numberOfPrintouts from 0 (specified by archiveInfo) to 1");
						numberOfPrintouts = PrintCopies.ONE;
					}
					break;
				}
				else if (PARAM_PrintCopies.equals(parameterName))
				{
					numberOfPrintouts = param.getParameterAsInt() > 0
							? PrintCopies.ofInt(param.getParameterAsInt())
							: PrintCopies.ZERO;
					if (numberOfPrintouts.isZero())
					{
						logger.debug("Setting numberOfPrintouts from 0 (specified by " + PARAM_PrintCopies + ") to 1");
						numberOfPrintouts = PrintCopies.ONE;
					}
				}
			}
		}

		//
		// Do a copy of found print info, or create a new one
		if (archiveInfo == null)
		{
			archiveInfo = new ArchiveInfo(pi);
		}
		else
		{
			archiveInfo = archiveInfo.copy();
		}

		// Update archiveInfo
		archiveInfo.setCopies(numberOfPrintouts.minimumOne());

		return archiveInfo;
	}
}
