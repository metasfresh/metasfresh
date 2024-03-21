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
import de.metas.adempiere.service.IPrinterRoutingDAO;
import de.metas.adempiere.service.PrinterRoutingsQuery;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.archive.api.ArchiveFileNameService;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.api.impl.DocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.printing.HardwarePrinter;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.HardwareTrayId;
import de.metas.printing.PrinterRoutingId;
import de.metas.printing.PrintingQueueItemId;
import de.metas.printing.api.IPrintClientsBL;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.PrintingQueueProcessingInfo;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

@Service
public class PrintingDataFactory
{
	private final static transient Logger logger = LogManager.getLogger(PrintingDataFactory.class);

	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IPrinterRoutingDAO printerRoutingDAO = Services.get(IPrinterRoutingDAO.class);
	private final IPrintJobBL printJobBL = Services.get(IPrintJobBL.class);
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final IDocOutboundDAO outboundDAO = Services.get(IDocOutboundDAO.class);
	private final IPrintClientsBL printClientsBL = Services.get(IPrintClientsBL.class);

	private final HardwarePrinterRepository hardwarePrinterRepository;
	private final ArchiveFileNameService archiveFileNameService;

	public PrintingDataFactory(
			@NonNull final HardwarePrinterRepository hardwarePrinterRepository,
			@NonNull final ArchiveFileNameService archiveFileNameService)
	{
		this.hardwarePrinterRepository = hardwarePrinterRepository;
		this.archiveFileNameService = archiveFileNameService;
	}

	public ImmutableList<PrintingData> createPrintingDataForQueueItem(@NonNull final I_C_Printing_Queue queueItem)
	{
		final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);

		final ArchiveId archiveId = ArchiveId.ofRepoId(queueItem.getAD_Archive_ID());
		final I_AD_Archive archiveRecord = archiveDAO.getArchiveRecordById(archiveId);
		final I_C_Doc_Outbound_Log outboundLogRecord = outboundDAO.retrieveLog(DocOutboundDAO.extractRecordRef(archiveRecord));

		final String pdfFileName;
		if (outboundLogRecord != null)
		{
			pdfFileName = archiveFileNameService.computePdfFileName(outboundLogRecord);
		}
		else
		{
			pdfFileName = archiveFileNameService.computePdfFileName(archiveRecord);
		}

		final ImmutableList.Builder<PrintingData> result = ImmutableList.builder();

		final PrintingQueueProcessingInfo printingQueueProcessingInfo = printingQueueBL.createPrintingQueueProcessingInfo(queueItem);
		for (final UserId printRecipient : printingQueueProcessingInfo.getAD_User_ToPrint_IDs())
		{
			final PrintingData printingData = createSinglePrintingData(printRecipient, printingQueueProcessingInfo, queueItem, archiveRecord, pdfFileName);
			result.add(printingData);
		}
		return result.build();
	}

	private PrintingData createSinglePrintingData(
			@NonNull final UserId printRecipient,
			@NonNull final PrintingQueueProcessingInfo printingQueueProcessingInfo,
			@NonNull final I_C_Printing_Queue queueItem,
			@NonNull final I_AD_Archive archiveRecord,
			@NonNull final String pdfFileName)
	{
		final PrintingData.PrintingDataBuilder printingData = PrintingData
				.builder()
				.printingQueueItemId(PrintingQueueItemId.ofRepoId(queueItem.getC_Printing_Queue_ID()))
				.orgId(OrgId.ofRepoId(queueItem.getAD_Org_ID()))
				.documentFileName(pdfFileName)
				.data(loadArchiveData(archiveRecord));

		final int copies = CoalesceUtil.firstGreaterThanZero(queueItem.getCopies(), 1);
		
		if (queueItem.getAD_PrinterHW_ID() <= 0)
		{
			final PrinterRoutingsQuery query = printingQueueBL.createPrinterRoutingsQueryForItem(queueItem);
			final List<I_AD_PrinterRouting> printerRoutings = InterfaceWrapperHelper.createList(printerRoutingDAO.fetchPrinterRoutings(query), I_AD_PrinterRouting.class);

			final String hostKey = printingQueueProcessingInfo.isCreateWithSpecificHostKey() ?
					printClientsBL.getHostKeyOrNull(Env.getCtx()) : null;

			for (final I_AD_PrinterRouting printerRouting : printerRoutings)
			{

				final PrintingSegment printingSegment = createPrintingSegment(printerRouting, printRecipient, hostKey);
				if (printingSegment != null)
				{
					addSegmentToData(printingData, printingSegment, copies);
				}
			}
		}
		else
		{
			final PrintingSegment printingSegment = createPrintingSegmentForQueueItem(queueItem);
			addSegmentToData(printingData, printingSegment, copies);

		}
		return printingData.build();
	}

	private static void addSegmentToData(
			@NonNull final PrintingData.PrintingDataBuilder printingData, 
			@NonNull final PrintingSegment printingSegment, 
			final int copies)
	{
		printingData.segment(printingSegment);
		
		for (int i = 1; i < copies; i++)
		{
			printingData.segment(printingSegment.copy());
		}
	}

	public PrintingData createPrintingDataForPrintJobLine(
			@NonNull final I_C_Print_Job_Line jobLine,
			@Nullable final UserId userToPrintId,
			@Nullable final String hostKey)
	{
		final I_AD_Archive archiveRecord = jobLine.getC_Printing_Queue().getAD_Archive();

		final PrintingData.PrintingDataBuilder printingData = PrintingData
				.builder()
				.printingQueueItemId(PrintingQueueItemId.ofRepoId(jobLine.getC_Printing_Queue_ID()))
				.orgId(OrgId.ofRepoId(archiveRecord.getAD_Org_ID()))
				.documentFileName(archiveRecord.getName() + ".pdf");

		final List<I_C_Print_Job_Detail> printJobDetails = printJobBL.getCreatePrintJobDetails(jobLine);
		boolean atLeastOneSegmentCreated = false;
		for (final I_C_Print_Job_Detail detail : printJobDetails)
		{
			final I_AD_PrinterRouting routing = loadOutOfTrx(detail.getAD_PrinterRouting_ID(), I_AD_PrinterRouting.class);
			final PrintingSegment printingSegment = createPrintingSegment(routing, userToPrintId, hostKey);
			if (printingSegment != null)
			{
				printingData.segment(printingSegment);
				atLeastOneSegmentCreated = true;
			}
		}

		if (atLeastOneSegmentCreated)
		{
			printingData.data(loadArchiveData(archiveRecord)); // if no segment was created we don't need to bother loading the data
		}
		return printingData.build();
	}

	private byte[] loadArchiveData(@NonNull final I_AD_Archive archiveRecord)
	{

		byte[] data = archiveBL.getBinaryData(archiveRecord);
		if (data == null || data.length == 0)
		{
			logger.info("AD_Archive {} does not contain any data. Skip", archiveRecord);
			data = null;
		}
		return data;
	}

	@NonNull
	private PrintingSegment createPrintingSegmentForQueueItem(
			@NonNull final I_C_Printing_Queue printingQueue)
	{

		final int trayRepoId = printingQueue.getAD_PrinterHW_MediaTray_ID();

		final HardwarePrinterId printerId = HardwarePrinterId.ofRepoId(printingQueue.getAD_PrinterHW_ID());
		final HardwareTrayId trayId = HardwareTrayId.ofRepoIdOrNull(printerId, trayRepoId);

		final HardwarePrinter hardwarePrinter = hardwarePrinterRepository.getById(printerId);

		return PrintingSegment.builder()
				.printer(hardwarePrinter)
				.trayId(trayId)
				.routingType(I_AD_PrinterRouting.ROUTINGTYPE_PageRange)
				.build();
	}

	private PrintingSegment createPrintingSegment(
			@NonNull final I_AD_PrinterRouting printerRouting,
			@Nullable final UserId userToPrintId,
			@Nullable final String hostKey)
	{
		final I_AD_Printer_Matching printerMatchingRecord = printingDAO.retrievePrinterMatchingOrNull(hostKey/*hostKey*/, userToPrintId, printerRouting.getAD_Printer());
		if (printerMatchingRecord == null)
		{
			logger.debug("Found no AD_Printer_Matching record for AD_PrinterRouting_ID={}, AD_User_PrinterMatchingConfig_ID={} and hostKey={}; -> creating no PrintingSegment for routing",
						 printerRouting, UserId.toRepoId(userToPrintId), hostKey);
			return null;
		}

		final I_AD_PrinterTray_Matching trayMatchingRecord = printingDAO.retrievePrinterTrayMatching(printerMatchingRecord, printerRouting, false);
		final int trayRepoId = trayMatchingRecord == null ? -1 : trayMatchingRecord.getAD_PrinterHW_MediaTray_ID();

		final HardwarePrinterId printerId = HardwarePrinterId.ofRepoId(printerMatchingRecord.getAD_PrinterHW_ID());
		final HardwareTrayId trayId = HardwareTrayId.ofRepoIdOrNull(printerId, trayRepoId);

		final HardwarePrinter hardwarePrinter = hardwarePrinterRepository.getById(printerId);

		return PrintingSegment.builder()
				.printerRoutingId(PrinterRoutingId.ofRepoId(printerRouting.getAD_PrinterRouting_ID()))
				.initialPageFrom(printerRouting.getPageFrom())
				.initialPageTo(printerRouting.getPageTo())
				.lastPages(printerRouting.getLastPages())
				.routingType(printerRouting.getRoutingType())
				.printer(hardwarePrinter)
				.trayId(trayId)
				.build();
	}
}
