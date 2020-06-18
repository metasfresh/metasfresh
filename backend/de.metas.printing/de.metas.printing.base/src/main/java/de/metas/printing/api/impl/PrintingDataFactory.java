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
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwarePrinterTrayId;
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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Archive;

import javax.annotation.Nullable;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class PrintingDataFactory
{

	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IPrinterRoutingDAO printerRoutingDAO = Services.get(IPrinterRoutingDAO.class);
	private final IPrintJobBL printJobBL = Services.get(IPrintJobBL.class);
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

	public PrintingData createPrintingDataForQueueItem(@NonNull final I_C_Printing_Queue queueItem)
	{
		final PrintingData.PrintingDataBuilder printingData = PrintingData
				.builder()
				.archiveRecord(queueItem.getAD_Archive());

		final PrinterRoutingsQuery query = printingQueueBL.createPrinterRoutingsQueryForItem(queueItem);
		final List<I_AD_PrinterRouting> printerRoutings = InterfaceWrapperHelper.createList(printerRoutingDAO.fetchPrinterRoutings(query), I_AD_PrinterRouting.class);
		for (final I_AD_PrinterRouting printerRouting : printerRoutings)
		{
			final String hostKey = null;
			final PrintingSegment printingSegment = createPrintingSegment(printerRouting, hostKey);
			printingData.printingSegment(printingSegment);
		}
		return printingData.build();
	}

	public PrintingData createPrintingDataForPrintJobLine(
			@NonNull final I_C_Print_Job_Line jobLine,
			@Nullable final String hostKey)
	{
		final PrintingData.PrintingDataBuilder printingData = PrintingData
				.builder()
				.archiveRecord(jobLine.getC_Printing_Queue().getAD_Archive());

		final List<I_C_Print_Job_Detail> printJobDetails = printJobBL.getCreatePrintJobDetails(jobLine);
		for (final I_C_Print_Job_Detail detail : printJobDetails)
		{
			final I_AD_PrinterRouting routing = loadOutOfTrx(detail.getAD_PrinterRouting_ID(), I_AD_PrinterRouting.class);
			final PrintingSegment printingSegment = createPrintingSegment(routing, hostKey);
			printingData.printingSegment(printingSegment);
		}
		return printingData.build();
	}

	private PrintingSegment createPrintingSegment(
			@NonNull final I_AD_PrinterRouting printerRouting,
			@Nullable final String hostKey)
	{
		final I_AD_Printer_Matching printerMatchingRecord = printingDAO.retrievePrinterMatchingOrNull(hostKey/*hostKey*/, printerRouting.getAD_Printer());
		final I_AD_PrinterTray_Matching trayMatchingRecord = printingDAO.retrievePrinterTrayMatching(printerMatchingRecord, printerRouting, false);

		final HardwarePrinterId printerId = HardwarePrinterId.ofRepoId(printerMatchingRecord.getAD_PrinterHW_ID());

		return PrintingSegment.builder()
				.initialPageFrom(printerRouting.getPageFrom())
				.initialPageTo(printerRouting.getPageTo())
				.lastPages(printerRouting.getLastPages())
				.routingType(printerRouting.getRoutingType())
				.printerId(printerId)
				.trayId(HardwarePrinterTrayId.ofRepoIdOrNull(printerId, trayMatchingRecord.getAD_PrinterHW_MediaTray_ID()))
				.build();
	}
}
