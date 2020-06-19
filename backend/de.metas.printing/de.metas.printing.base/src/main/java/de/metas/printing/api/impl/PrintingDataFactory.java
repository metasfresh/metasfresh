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

import de.metas.adempiere.service.IPrinterRoutingDAO;
import de.metas.adempiere.service.PrinterRoutingsQuery;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwareTrayId;
import de.metas.printing.PrinterRoutingId;
import de.metas.printing.PrintingQueueItemId;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Archive;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class PrintingDataFactory
{
	private final static transient Logger logger = LogManager.getLogger(PrintingDataFactory.class);

	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IPrinterRoutingDAO printerRoutingDAO = Services.get(IPrinterRoutingDAO.class);
	private final IPrintJobBL printJobBL = Services.get(IPrintJobBL.class);
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
	private final transient IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	public PrintingData createPrintingDataForQueueItem(@NonNull final I_C_Printing_Queue queueItem)
	{
		final I_AD_Archive archiveRecord = queueItem.getAD_Archive();
		final PrintingData.PrintingDataBuilder printingData = PrintingData
				.builder()
				.printingQueueItemId(PrintingQueueItemId.ofRepoId(queueItem.getC_Printing_Queue_ID()))
				.orgId(OrgId.ofRepoId(archiveRecord.getAD_Org_ID()))
				.documentName(archiveRecord.getName())
				.data(loadArchiveData(archiveRecord));

		final PrinterRoutingsQuery query = printingQueueBL.createPrinterRoutingsQueryForItem(queueItem);
		final List<I_AD_PrinterRouting> printerRoutings = InterfaceWrapperHelper.createList(printerRoutingDAO.fetchPrinterRoutings(query), I_AD_PrinterRouting.class);
		for (final I_AD_PrinterRouting printerRouting : printerRoutings)
		{
			final String hostKey = null;
			final PrintingSegment printingSegment = createPrintingSegment(printerRouting, hostKey);
			printingData.segment(printingSegment);
		}
		return printingData.build();
	}

	public PrintingData createPrintingDataForPrintJobLine(
			@NonNull final I_C_Print_Job_Line jobLine,
			@Nullable final String hostKey)
	{

		final I_AD_Archive archiveRecord = jobLine.getC_Printing_Queue().getAD_Archive();

		final PrintingData.PrintingDataBuilder printingData = PrintingData
				.builder()
				.printingQueueItemId(PrintingQueueItemId.ofRepoId(jobLine.getC_Printing_Queue_ID()))
				.orgId(OrgId.ofRepoId(archiveRecord.getAD_Org_ID()))
				.documentName(archiveRecord.getName())
				.data(loadArchiveData(archiveRecord));

		final List<I_C_Print_Job_Detail> printJobDetails = printJobBL.getCreatePrintJobDetails(jobLine);
		for (final I_C_Print_Job_Detail detail : printJobDetails)
		{
			final I_AD_PrinterRouting routing = loadOutOfTrx(detail.getAD_PrinterRouting_ID(), I_AD_PrinterRouting.class);
			final PrintingSegment printingSegment = createPrintingSegment(routing, hostKey);
			printingData.segment(printingSegment);
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

	private PrintingSegment createPrintingSegment(
			@NonNull final I_AD_PrinterRouting printerRouting,
			@Nullable final String hostKey)
	{
		final I_AD_Printer_Matching printerMatchingRecord = printingDAO.retrievePrinterMatchingOrNull(hostKey/*hostKey*/, printerRouting.getAD_Printer());
		final I_AD_PrinterTray_Matching trayMatchingRecord = printingDAO.retrievePrinterTrayMatching(printerMatchingRecord, printerRouting, false);

		final HardwarePrinterId printerId = HardwarePrinterId.ofRepoId(printerMatchingRecord.getAD_PrinterHW_ID());

		return PrintingSegment.builder()
				.printerRoutingId(PrinterRoutingId.ofRepoId(printerRouting.getAD_PrinterRouting_ID()))
				.initialPageFrom(printerRouting.getPageFrom())
				.initialPageTo(printerRouting.getPageTo())
				.lastPages(printerRouting.getLastPages())
				.routingType(printerRouting.getRoutingType())
				.printerId(printerId)
				.trayId(HardwareTrayId.ofRepoIdOrNull(printerId, trayMatchingRecord.getAD_PrinterHW_MediaTray_ID()))
				.build();
	}
}
