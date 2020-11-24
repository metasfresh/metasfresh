package de.metas.printing.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;

/**
 * To test additional things, create subclasses and override the setup and eval methods. Assertions run under static page range.
 *
 * @author ts
 */
public abstract class AbstractPrintJobLinesAggregatorPageRangeTestBase extends AbstractPrintingTest
{
	// TODO: test the case that not all pages of the document are covered by routings!!

	// TODO extract further methods for setup and evaluation, and rename to sth like "standardScenario" or something.
	@Test
	public void testOneJobLineMultiTray()
	{
		final String printerName = "test03-printer01";
		final String tray1Name = "tray01";
		final String tray2Name = "tray02";

		final String printerHWName = printerName + "-HW";
		final String tray1HWName = tray1Name + "-HW";
		final String tray2HWName = tray2Name + "-HW";

		final I_AD_PrinterHW_MediaTray tray1HW = helper.getCreatePrinterTrayHW(printerHWName, tray1HWName, 10);
		final I_AD_PrinterHW_MediaTray tray2HW = helper.getCreatePrinterTrayHW(printerHWName, tray2HWName, 20);
		final I_AD_PrinterHW printerHW = tray1HW.getAD_PrinterHW();
		assertThat(printerHW, is(tray2HW.getAD_PrinterHW())); // guard

		// create the routings, which also will create the logical printer and trays
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting(printerName, tray1Name, 10, -1, 1, 1);
		final I_AD_PrinterRouting routing2 = helper.createPrinterRouting(printerName, tray2Name, 10, -1, 2, 3); // routing shall match pages 2,3, but note that we will only have a 2-paged PDF.

		// helper.autoCreateHWPrinters=false;
		// final I_AD_PrinterRouting routing3 = helper.createPrinterRouting(printerName, "someOtherTrayWithNoHW", -1, 3, 3);
		// helper.autoCreateHWPrinters=true;

		// the matchings were already created. this would create duplicate matchings, leading to a MoreThenOneRecordFoundException in the test
		// helper.createPrinterMatching(Helper.HOSTKEY_Host01, printerHWName, tray1HWName, printerName, tray1Name);
		// helper.createPrinterMatching(Helper.HOSTKEY_Host01, printerHWName, tray2HWName, printerName, tray2Name);

		// Total pages: one document with two sides
		final byte[] binaryPdfData = step10_CreatePdfDataToPrint();

		final I_C_Print_Job printJob = step20_createPrintJob(
				binaryPdfData,
				Arrays.asList(
						routing1,
						routing2
						// ,routing3
				));

		//
		// expected result
		final byte[] dataExpected = binaryPdfData;

		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);

		//
		// Validate PrintPackage Infos
		final List<I_C_Print_PackageInfo> printPackageInfos = helper.getDAO().retrievePrintPackageInfo(printPackage);
		assertEquals("Invalid infos count: " + printPackageInfos, 2, printPackageInfos.size());

		final I_C_Print_PackageInfo printPackageInfo1 = printPackageInfos.get(0);
		assertEquals("Invalid PageFrom for " + printPackageInfo1, 1, printPackageInfo1.getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfo1, 1, printPackageInfo1.getPageTo());
		assertThat(printPackageInfo1.getAD_PrinterHW(), is(printerHW));
		assertThat(printPackageInfo1.getAD_PrinterHW_MediaTray(), is(tray1HW));

		final I_C_Print_PackageInfo printPackageInfo2 = printPackageInfos.get(1);
		assertEquals("Invalid PageFrom for " + printPackageInfo2, 2, printPackageInfo2.getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfo2, 2, printPackageInfo2.getPageTo());
		assertThat(printPackageInfo2.getAD_PrinterHW(), is(printerHW));
		assertThat(printPackageInfo2.getAD_PrinterHW_MediaTray(), is(tray2HW));
	}

	protected abstract byte[] step10_CreatePdfDataToPrint();

	protected abstract I_C_Print_Job step20_createPrintJob(byte[] binaryPdfData, List<I_AD_PrinterRouting> routings);
}
