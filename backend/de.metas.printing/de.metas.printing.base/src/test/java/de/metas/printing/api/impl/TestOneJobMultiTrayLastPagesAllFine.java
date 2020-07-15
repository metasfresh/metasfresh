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

import de.metas.printing.api.util.PdfCollator;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Tests around {@link I_AD_PrinterRouting#ROUTINGTYPE_LastPages} functionality
 * 
 * @author tsa
 * 
 * @task http://dewiki908/mediawiki/index.php/07846_QS_und_Inbetriebnahme_Massendruck_%28102912559104%29
 *
 */
public class TestOneJobMultiTrayLastPagesAllFine extends AbstractPrintingTest
{
	private final String printerName = "test03-printer01";
	private final String tray1Name = "tray01";
	private final String tray2Name = "tray02";

	private final String printerHWName = printerName + "-HW";
	private I_AD_PrinterHW printerHW;
	private final String tray1HWName = tray1Name + "-HW";
	private I_AD_PrinterHW_MediaTray tray2HW;
	private final String tray2HWName = tray2Name + "-HW";
	private I_AD_PrinterHW_MediaTray tray1HW;

	@Override
	protected void afterSetup()
	{
		// Create HW printer trays
		this.tray1HW = helper.getCreatePrinterTrayHW(printerHWName, tray1HWName,10);
		this.tray2HW = helper.getCreatePrinterTrayHW(printerHWName, tray2HWName,20);
		this.printerHW = tray1HW.getAD_PrinterHW();
		assertThat(printerHW, is(tray2HW.getAD_PrinterHW())); // guard
	}

	@Test
	public void test_50PagesDocument_print_1to3_last2pages()
	{
		// create the routings, which also will create the logical printer and trays
		// setting pageFrom=1, pageTo=3 to support documents with e.g. 3, 4, 5 pages. In all those cases, the last page shall be printed on tray02
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting(printerName, tray1Name,10, -1, 1, 3); // routing shall match pages from 1 to 3
		final I_AD_PrinterRouting routing2 = helper.createPrinterRoutingForLastPages(printerName, tray2Name,20, -1, 2); // routing shall match last 2 pages

		// PDF to print
		final byte[] binaryPdfData = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 20) // First 20 pages
				.toByteArray();
		// expected result
		final byte[] dataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 3) // First 3 pages
				.addPages(helper.getPdf("01"), 19, 20) // Last 2 pages
				.toByteArray();

		//
		// Create print job
		final I_C_Print_Job printJob = createPrintJob(
				binaryPdfData,
				Arrays.asList(routing1, routing2),
				10); // 10 copies

		//
		// Create Print Package
		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);
		assertThat(printPackage.getCopies(), is(10));

		//
		// Validate PrintPackage Infos
		final List<I_C_Print_PackageInfo> printPackageInfos = helper.getDAO().retrievePrintPackageInfo(printPackage);
		assertEquals("Invalid infos count: " + printPackageInfos, 2, printPackageInfos.size());

		final I_C_Print_PackageInfo printPackageInfo1 = printPackageInfos.get(0);
		assertEquals("Invalid PageFrom for " + printPackageInfo1, 1, printPackageInfo1.getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfo1, 3, printPackageInfo1.getPageTo());
		assertThat(printPackageInfo1.getAD_PrinterHW(), is(printerHW));
		assertThat(printPackageInfo1.getAD_PrinterHW_MediaTray(), is(tray1HW));

		final I_C_Print_PackageInfo printPackageInfo2 = printPackageInfos.get(1);
		assertEquals("Invalid PageFrom for " + printPackageInfo2, 4, printPackageInfo2.getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfo2, 5, printPackageInfo2.getPageTo());
		assertThat(printPackageInfo2.getAD_PrinterHW(), is(printerHW));
		assertThat(printPackageInfo2.getAD_PrinterHW_MediaTray(), is(tray2HW));
	}

	@Test
	public void test_4PagesDocument_print_1to3_last2pages()
	{
		// create the routings, which also will create the logical printer and trays
		// setting pageFrom=1, pageTo=3 to support documents with e.g. 3, 4, 5 pages. In all those cases, the last page shall be printed on tray02
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting(printerName, tray1Name,10, -1, 1, 3); // routing shall match pages from 1 to 3
		final I_AD_PrinterRouting routing2 = helper.createPrinterRoutingForLastPages(printerName, tray2Name,20, -1, 2); // routing shall match last 2 pages

		// PDF to print
		final byte[] binaryPdfData = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 4) // First 4 pages
				.toByteArray();
		// expected result
		final byte[] dataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 2) // First 2 pages (because 3rd page overlaps with last 2)
				.addPages(helper.getPdf("01"), 3, 4) // Last 2 pages
				.toByteArray();

		//
		// Create print job
		final I_C_Print_Job printJob = createPrintJob(
				binaryPdfData,
				Arrays.asList(routing1, routing2),
				9); // 9 copies

		//
		// Create Print Package
		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);
		assertThat(printPackage.getCopies(), is(9));

		//
		// Validate PrintPackage Infos
		final List<I_C_Print_PackageInfo> printPackageInfos = helper.getDAO().retrievePrintPackageInfo(printPackage);
		assertEquals("Invalid infos count: " + printPackageInfos, 2, printPackageInfos.size());

		final I_C_Print_PackageInfo printPackageInfo1 = printPackageInfos.get(0);
		assertEquals("Invalid PageFrom for " + printPackageInfo1, 1, printPackageInfo1.getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfo1, 2, printPackageInfo1.getPageTo());
		assertThat(printPackageInfo1.getAD_PrinterHW(), is(printerHW));
		assertThat(printPackageInfo1.getAD_PrinterHW_MediaTray(), is(tray1HW));

		final I_C_Print_PackageInfo printPackageInfo2 = printPackageInfos.get(1);
		assertEquals("Invalid PageFrom for " + printPackageInfo2, 3, printPackageInfo2.getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfo2, 4, printPackageInfo2.getPageTo());
		assertThat(printPackageInfo2.getAD_PrinterHW(), is(printerHW));
		assertThat(printPackageInfo2.getAD_PrinterHW_MediaTray(), is(tray2HW));
	}

	@Test
	public void test_3PagesDocument_print_1to3_last2pages()
	{
		// create the routings, which also will create the logical printer and trays
		// setting pageFrom=1, pageTo=3 to support documents with e.g. 3, 4, 5 pages. In all those cases, the last page shall be printed on tray02
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting(printerName, tray1Name,10, -1, 1, 3); // routing shall match pages from 1 to 3
		final I_AD_PrinterRouting routing2 = helper.createPrinterRoutingForLastPages(printerName, tray2Name,20, -1, 2); // routing shall match last 2 pages

		// PDF to print
		final byte[] binaryPdfData = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 3) // First 3 pages
				.toByteArray();
		// expected result
		final byte[] dataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 1) // First 1 pages (because 2nd and 3rd page overlaps with last 2)
				.addPages(helper.getPdf("01"), 2, 3) // Last 2 pages
				.toByteArray();

		//
		// Create print job
		final I_C_Print_Job printJob = createPrintJob(
				binaryPdfData,
				Arrays.asList(routing1, routing2), 
				8); // 8 copies

		//
		// Create Print Package
		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);
		assertThat(printPackage.getCopies(), is(8));
		
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
		assertEquals("Invalid PageTo for " + printPackageInfo2, 3, printPackageInfo2.getPageTo());
		assertThat(printPackageInfo2.getAD_PrinterHW(), is(printerHW));
		assertThat(printPackageInfo2.getAD_PrinterHW_MediaTray(), is(tray2HW));
	}

	@Test
	public void test_2PagesDocument_print_1to3_last2pages()
	{
		// create the routings, which also will create the logical printer and trays
		// setting pageFrom=1, pageTo=3 to support documents with e.g. 3, 4, 5 pages. In all those cases, the last page shall be printed on tray02
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting(printerName, tray1Name,10, -1, 1, 3); // routing shall match pages from 1 to 3
		final I_AD_PrinterRouting routing2 = helper.createPrinterRoutingForLastPages(printerName, tray2Name,20, -1, 2); // routing shall match last 2 pages

		// PDF to print
		final byte[] binaryPdfData = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 2) // First 2 pages
				.toByteArray();
		// expected result
		final byte[] dataExpected = new PdfCollator()
				// .addPages(helper.getPdf("01"), 1, 2) // First 3 pages => i.e nothing because we print last 2 pages and those get priority
				.addPages(helper.getPdf("01"), 1, 2) // Last 2 pages
				.toByteArray();

		//
		// Create print job
		final I_C_Print_Job printJob = createPrintJob(
				binaryPdfData,
				Arrays.asList(routing1, routing2),
				7); // 7 copies

		//
		// Create Print Package
		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);
		assertThat(printPackage.getCopies(), is(7));

		//
		// Validate PrintPackage Infos
		final List<I_C_Print_PackageInfo> printPackageInfos = helper.getDAO().retrievePrintPackageInfo(printPackage);
		assertEquals("Invalid infos count: " + printPackageInfos, 1, printPackageInfos.size());

		final I_C_Print_PackageInfo printPackageInfo1 = printPackageInfos.get(0);
		assertEquals("Invalid PageFrom for " + printPackageInfo1, 1, printPackageInfo1.getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfo1, 2, printPackageInfo1.getPageTo());
		assertThat(printPackageInfo1.getAD_PrinterHW(), is(printerHW));
		assertThat(printPackageInfo1.getAD_PrinterHW_MediaTray(), is(tray2HW));
	}

	@Test
	public void test_1PagesDocument_print_1to3_last2pages()
	{
		// create the routings, which also will create the logical printer and trays
		// setting pageFrom=1, pageTo=3 to support documents with e.g. 3, 4, 5 pages. In all those cases, the last page shall be printed on tray02
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting(printerName, tray1Name,10, -1, 1, 3); // routing shall match pages from 1 to 3
		final I_AD_PrinterRouting routing2 = helper.createPrinterRoutingForLastPages(printerName, tray2Name,20, -1, 2); // routing shall match last 2 pages

		// PDF to print
		final byte[] binaryPdfData = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 1) // First 1 pages
				.toByteArray();
		// expected result
		final byte[] dataExpected = new PdfCollator()
				// .addPages(helper.getPdf("01"), 1, 1) // First 3 pages => i.e nothing because we print last 2 pages and those get priority
				.addPages(helper.getPdf("01"), 1, 1) // Last 2 pages => we got only one
				.toByteArray();
		//
		// Create print job
		final I_C_Print_Job printJob = createPrintJob(
				binaryPdfData,
				Arrays.asList(routing1, routing2),
				6); // copies

		//
		// Create Print Package
		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);
		assertThat(printPackage.getCopies(), is(6));
		
		//
		// Validate PrintPackage Infos
		final List<I_C_Print_PackageInfo> printPackageInfos = helper.getDAO().retrievePrintPackageInfo(printPackage);
		assertEquals("Invalid infos count: " + printPackageInfos, 1, printPackageInfos.size());

		final I_C_Print_PackageInfo printPackageInfo1 = printPackageInfos.get(0);
		assertEquals("Invalid PageFrom for " + printPackageInfo1, 1, printPackageInfo1.getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfo1, 1, printPackageInfo1.getPageTo());
		assertThat(printPackageInfo1.getAD_PrinterHW(), is(printerHW));
		assertThat(printPackageInfo1.getAD_PrinterHW_MediaTray(), is(tray2HW));
	}

	@Test
	public void test_2PagesDocument_print_1to100_lastPage()
	{
		// create the routings, which also will create the logical printer and trays
		final int docTypeId = -1; // N/A
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting(printerName, tray1Name,10, docTypeId, 1, 100); // routing shall match pages from 1 to 100
		final I_AD_PrinterRouting routing2 = helper.createPrinterRoutingForLastPages(printerName, tray2Name,20, docTypeId, 1); // routing shall match last 1 page

		// PDF to print (2 pages)
		final byte[] binaryPdfData = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 2)
				.toByteArray();
		// expected result
		final byte[] dataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 1) // First 100 pages => we got only 1 page
				.addPages(helper.getPdf("01"), 2, 2) // Last page
				.toByteArray();

		//
		// Create print job
		final I_C_Print_Job printJob = createPrintJob(
				binaryPdfData,
				Arrays.asList(routing1, routing2),
				5); // 5 copies

		//
		// Create Print Package
		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);
		assertThat(printPackage.getCopies(), is(5));

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

	protected I_C_Print_Job createPrintJob(final byte[] binaryPdfData, final List<I_AD_PrinterRouting> routings,
			final int copies)
	{
		final I_C_Print_Job printJob = helper.createPrintJob();

		helper.createPrintJobLine(printJob,
				routings,
				binaryPdfData);

		helper.createPrintJobInstructions(printJob, copies);
		InterfaceWrapperHelper.save(printJob);

		return printJob;
	}

}
