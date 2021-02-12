package de.metas.printing.process;/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwareTrayId;
import de.metas.printing.PrintOutputFacade;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.impl.PrintArchiveParameters;
import de.metas.printing.model.I_AD_Archive;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;

import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveDAO;
import org.compiere.SpringContextHolder;

import java.util.Iterator;

import java.util.stream.Stream;

public class C_Doc_Outbound_Log_PrintSelected extends JavaProcess implements IProcessPrecondition
{
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);

	private final transient IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	private final PrintOutputFacade printOutputFacade = SpringContextHolder.instance.getBean(PrintOutputFacade.class);

	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(parameterName = "AD_PrinterHW_ID")
	private HardwarePrinterId hwPrinterId;

	@Param(parameterName = "AD_PrinterHW_MediaTray_ID")
	private int hwTrayRecordId;

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_C_Doc_Outbound_Log> docOutboundLogRecords = retrieveSelectedDocOutboundLogs();
		while (docOutboundLogRecords.hasNext())
		{
			final I_C_Doc_Outbound_Log docOutboundLogRecord = docOutboundLogRecords.next();
			final I_C_Doc_Outbound_Log_Line docOutboundLogLine = retrieveDocumentLogLine(docOutboundLogRecord);
			if (docOutboundLogLine != null)
			{
				printArchive(docOutboundLogLine);
			}
		}
		return MSG_OK;
	}

	private void printArchive(final I_C_Doc_Outbound_Log_Line logLine)
	{
		if (logLine == null)
		{
			//nothing to do
			return;
		}
		final int archiveRecordId = logLine.getAD_Archive_ID();
		if (archiveRecordId <= 0)
		{
			//nothing to do
			return;
		}

		final I_AD_Archive archive = archiveDAO.retrieveArchive(ArchiveId.ofRepoId(archiveRecordId), I_AD_Archive.class);
		if (archive == null)
		{
			//nothing to do
			return;
		}

		final HardwareTrayId hwTrayId = HardwareTrayId.ofRepoIdOrNull(hwPrinterId, hwTrayRecordId);

		final PrintArchiveParameters printArchiveParameters = PrintArchiveParameters.builder()
				.archive(archive)
				.printOutputFacade(printOutputFacade)
				.hwPrinterId(hwPrinterId)
				.hwTrayId(hwTrayId)
				.enforceEnqueueToPrintQueue(true)
				.build();

		printingQueueBL.printArchive(printArchiveParameters);

	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final SelectionSize selectionSize = context.getSelectionSize();
		if (selectionSize.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	private final Iterator<I_C_Doc_Outbound_Log> retrieveSelectedDocOutboundLogs()
	{
		final IQueryFilter<I_C_Doc_Outbound_Log> filter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final Stream<I_C_Doc_Outbound_Log> stream = queryBL
				.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.filter(filter)
				.create()
				.iterateAndStream();

		return stream.iterator();
	}

	protected I_C_Doc_Outbound_Log_Line retrieveDocumentLogLine(final I_C_Doc_Outbound_Log log)
	{
		final I_C_Doc_Outbound_Log_Line logLine = docOutboundDAO.retrieveCurrentPDFArchiveLogLineOrNull(log);
		return logLine;
	}

}
