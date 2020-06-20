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

import de.metas.organization.OrgId;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.HardwareTrayId;
import de.metas.printing.OutputType;
import de.metas.printing.PrinterRoutingId;
import de.metas.printing.PrintingQueueItemId;
import de.metas.printing.api.util.PdfCollator;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterRouting;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.X_AD_SysConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;

class PrintingDataToPDFFileStorerTest
{
	private Helper helper;

	@BeforeEach
	void beforeEach(TestInfo testInfo)
	{
		helper = new Helper(testInfo);
		helper.setup();
	}

	@Test
	void storeInFileSystem()
	{
		// given
		// PDF to print
		final byte[] binaryPdfData = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 3) // First 3 pages
				.toByteArray();
		// expected result
		final byte[] dataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 1) // First 1 pages (because 2nd and 3rd page overlaps with last 2)
				.addPages(helper.getPdf("01"), 2, 3) // Last 2 pages
				.toByteArray();

		final I_AD_SysConfig sysConfigRecord = InterfaceWrapperHelper.newInstance(I_AD_SysConfig.class);
		sysConfigRecord.setName(PrintingDataToPDFFileStorer.SYSCONFIG_STORE_PDF_BASE_DIRECTORY);
		sysConfigRecord.setValue(".");
		sysConfigRecord.setConfigurationLevel(X_AD_SysConfig.CONFIGURATIONLEVEL_Organization);
		InterfaceWrapperHelper.saveRecord(sysConfigRecord);

		final I_AD_PrinterHW hwPrinter = helper.getCreatePrinterHW("hwPrinter", OutputType.Store);
		final HardwarePrinterId printerId = HardwarePrinterId.ofRepoId(hwPrinter.getAD_PrinterHW_ID());
		final I_AD_PrinterHW_MediaTray hwTray1 = helper.getCreatePrinterTrayHW("hwPrinter", "hwTray1");
		final I_AD_PrinterHW_MediaTray hwTray2 = helper.getCreatePrinterTrayHW("hwPrinter", "hwTray2");
		final HardwareTrayId tray1Id = HardwareTrayId.ofRepoId(printerId, hwTray1.getAD_PrinterHW_MediaTray_ID());
		final HardwareTrayId tray2Id = HardwareTrayId.ofRepoId(printerId, hwTray2.getAD_PrinterHW_MediaTray_ID());

		final PrintingData printingData = PrintingData.builder()
				.documentName("test")
				.orgId(OrgId.ofRepoId(10))
				.printingQueueItemId(PrintingQueueItemId.ofRepoId(20))
				.data(binaryPdfData)
				.segment(PrintingSegment.builder()
						.printerRoutingId(PrinterRoutingId.ofRepoId(401))
						.routingType(I_AD_PrinterRouting.ROUTINGTYPE_PageRange)
						.initialPageFrom(1)
						.initialPageTo(3)
						.printerId(printerId)
						.trayId(tray1Id).build())
				.segment(PrintingSegment.builder()
						.printerRoutingId(PrinterRoutingId.ofRepoId(402))
						.routingType(I_AD_PrinterRouting.ROUTINGTYPE_LastPages)
						.lastPages(2)
						.printerId(printerId)
						.trayId(tray2Id).build())
				.build();

		// when
		new PrintingDataToPDFFileStorer(new HardwarePrinterRepository()).storeInFileSystem(printingData);

		// then
		// TODO
	}
}