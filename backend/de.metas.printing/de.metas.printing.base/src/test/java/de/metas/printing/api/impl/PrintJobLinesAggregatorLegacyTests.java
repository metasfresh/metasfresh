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

import de.metas.document.archive.api.ArchiveFileNameService;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.api.IPrintPackageCtx;
import de.metas.printing.api.util.PdfCollator;
import de.metas.printing.exception.PrintingQueueAggregationException;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;
import de.metas.printing.printingdata.PrintingDataFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.api.impl.ArchiveStorageFactory;
import org.adempiere.archive.spi.impl.DBArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Note: all tests are still valid, it's just the architecture that is legacy.
 */
public class PrintJobLinesAggregatorLegacyTests extends AbstractPrintingTest
{
	// Services
	private ArchiveStorageFactory archiveStorageFactory;
	private IPrintPackageBL printPackageBL;
	private PrintingDataFactory printingDataFactory;

	@Override
	protected void afterSetup()
	{
		MockedDBArchiveStorage.reset();

		POJOWrapper.setDefaultStrictValues(false);

		archiveStorageFactory = (ArchiveStorageFactory)Services.get(IArchiveStorageFactory.class);
		printPackageBL = Services.get(IPrintPackageBL.class);
	}

	@Before
	public void setUp() throws Exception
	{
		printingDataFactory = new PrintingDataFactory(new HardwarePrinterRepository(), new ArchiveFileNameService());
	}

	@Test
	public void test01_cal()
	{
		final I_AD_PrinterRouting printerRouting = helper.createPrinterRouting("printer01", "tray01",10, -1, -1, -1);

		helper.createPrinterHWCalibration("printer01-HW",
				"iso-a7", // task 08458: printer doesn't have A4, but the system shall create it on the fly
				"tray01-HW",10, 10, 20); // 03733

		//
		// Setup PrintJob
		final I_C_Print_Job printJob = helper.createPrintJob();
		helper.createPrintJobLine(printJob, printerRouting, "01");
		helper.createPrintJobLine(printJob, printerRouting, "02");
		helper.createPrintJobLine(printJob, printerRouting, "03");
		helper.createPrintJobInstructions(printJob);
		InterfaceWrapperHelper.save(printJob);

		//
		// Setup expected result
		final byte[] dataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 20)
				.addPages(helper.getPdf("02"), 1, 20)
				.addPages(helper.getPdf("03"), 1, 20)
				.toByteArray();

		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);

		//
		// Validate PrintPackage Infos
		final List<I_C_Print_PackageInfo> printPackageInfos = helper.getDAO().retrievePrintPackageInfo(printPackage);
		Assert.assertEquals("Invalid infos count: " + printPackageInfos, 1, printPackageInfos.size());
		Assert.assertEquals("Invalid PageFrom for " + printPackageInfos.get(0), 1, printPackageInfos.get(0).getPageFrom());
		Assert.assertEquals("Invalid PageTo for " + printPackageInfos.get(0), 60, printPackageInfos.get(0).getPageTo());

		// 03733 begin
		Assert.assertEquals("Invalid CalX for " + printPackageInfos.get(0), 10, printPackageInfos.get(0).getCalX());
		Assert.assertEquals("Invalid CalY for " + printPackageInfos.get(0), 20, printPackageInfos.get(0).getCalY());
		// 03733 end
	}

	@Test
	public void test01_noCal()
	{
		//
		// Setup PrintJob
		final I_AD_PrinterRouting printerRouting = helper.createPrinterRouting("printer01", "tray01",10, -1, -1, -1);

		final I_C_Print_Job printJob = helper.createPrintJob();
		helper.createPrintJobLine(printJob, printerRouting, "01");
		helper.createPrintJobLine(printJob, printerRouting, "02");
		helper.createPrintJobLine(printJob, printerRouting, "03");
		helper.createPrintJobInstructions(printJob);
		InterfaceWrapperHelper.save(printJob);

		//
		// Setup expected result
		final byte[] dataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 20)
				.addPages(helper.getPdf("02"), 1, 20)
				.addPages(helper.getPdf("03"), 1, 20)
				.toByteArray();

		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);

		//
		// Validate PrintPackage Infos
		final List<I_C_Print_PackageInfo> printPackageInfos = helper.getDAO().retrievePrintPackageInfo(printPackage);
		Assert.assertEquals("Invalid infos count: " + printPackageInfos, 1, printPackageInfos.size());
		Assert.assertEquals("Invalid PageFrom for " + printPackageInfos.get(0), 1, printPackageInfos.get(0).getPageFrom());
		Assert.assertEquals("Invalid PageTo for " + printPackageInfos.get(0), 60, printPackageInfos.get(0).getPageTo());

		// 03733 begin
		Assert.assertEquals("Invalid CalX for " + printPackageInfos.get(0), 0, printPackageInfos.get(0).getCalX());
		Assert.assertEquals("Invalid CalY for " + printPackageInfos.get(0), 0, printPackageInfos.get(0).getCalY());
		// 03733 end
	}

	/**
	 * 3 print jobs, shall be aggregated into one print package info, because the printer routings are equivalent.
	 */
	@Test
	public void test02()
	{
		//
		// Setup PrintJob
		final I_AD_PrinterRouting printerRouting = helper.createPrinterRouting("printer01", "tray01",10, -1, -1, -1);
		final I_C_Print_Job printJob = helper.createPrintJob();
		helper.createPrintJobLine(printJob,
				printerRouting,
				"01");
		helper.createPrintJobLine(printJob,
				helper.createPrinterRouting("printer01", "tray01",10, -1, 10, 15),
				"02");
		helper.createPrintJobLine(printJob,
				printerRouting,
				"03");
		helper.createPrintJobInstructions(printJob);
		InterfaceWrapperHelper.save(printJob);

		//
		// Setup expected result
		// Total pages: 20 + 6 + 20 = 46
		final byte[] dataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 20)
				.addPages(helper.getPdf("02"), 10, 15)
				.addPages(helper.getPdf("03"), 1, 20)
				.toByteArray();

		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);

		//
		// Validate PrintPackage Infos
		final List<I_C_Print_PackageInfo> printPackageInfos = helper.getDAO().retrievePrintPackageInfo(printPackage);
		Assert.assertEquals("Invalid infos count: " + printPackageInfos, 1, printPackageInfos.size());
		Assert.assertEquals("Invalid PageFrom for " + printPackageInfos.get(0), 1, printPackageInfos.get(0).getPageFrom());
		Assert.assertEquals("Invalid PageTo for " + printPackageInfos.get(0), 20 + 6 + 20, printPackageInfos.get(0).getPageTo());
	}

	@Test
	public void test03()
	{
		//
		// Setup PrintJob
		final I_C_Print_Job printJob = helper.createPrintJob();
		helper.createPrintJobLine(printJob,
				helper.createPrinterRouting("test03-printer01", "tray01",10, -1, 1, 2),
				"01");
		helper.createPrintJobLine(printJob,
				helper.createPrinterRouting("test03-printer02", "tray01",10, -1, 3, 4),
				"02");
		helper.createPrintJobLine(printJob,
				helper.createPrinterRouting("test03-printer01", "tray01",10, -1, 5, 6),
				"03");
		helper.createPrintJobInstructions(printJob);
		InterfaceWrapperHelper.save(printJob);

		//
		// Setup expected result
		// Total pages: 2 + 2 + 2 = 6
		// task 08958: we want the paged in the order of out print job line. i.e. two lines shall *not* be aggregated into one, just because they match.
		// that is because we need a straight forward ordering and that is more important than minimizing the number of print jobs.
		final byte[] dataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 2)
				.addPages(helper.getPdf("02"), 3, 4)
				.addPages(helper.getPdf("03"), 5, 6)
				.toByteArray();

		final I_C_Print_Package printPackage = helper.createNextPrintPackageAndTest(printJob, dataExpected);

		//
		// Validate PrintPackage Infos
		final List<I_C_Print_PackageInfo> printPackageInfos = helper.getDAO().retrievePrintPackageInfo(printPackage);
		Assert.assertEquals("Invalid infos count: " + printPackageInfos, 3, printPackageInfos.size());

		assertEquals("Invalid PageFrom for " + printPackageInfos.get(0), 1, printPackageInfos.get(0).getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfos.get(0), 2, printPackageInfos.get(0).getPageTo());
		assertEquals("Invalid PageFrom for " + printPackageInfos.get(1), 3, printPackageInfos.get(1).getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfos.get(1), 4, printPackageInfos.get(1).getPageTo());
		assertEquals("Invalid PageFrom for " + printPackageInfos.get(2), 5, printPackageInfos.get(2).getPageFrom());
		assertEquals("Invalid PageTo for " + printPackageInfos.get(2), 6, printPackageInfos.get(2).getPageTo());
	}

	public static class MockedDBArchiveStorage extends DBArchiveStorage
	{
		private static final Set<Integer> archiveIdsToFail = new HashSet<Integer>();

		public static void reset()
		{
			archiveIdsToFail.clear();
		}

		@Override
		public byte[] getBinaryData(final I_AD_Archive archive)
		{
			final int archiveId = archive.getAD_Archive_ID();
			if (archiveIdsToFail.contains(archiveId))
			{
				throw new RuntimeException("Failing on request for " + archive);
			}
			return super.getBinaryData(archive);
		}

		public static void setFailGetBinaryData(@NonNull final I_AD_Archive archive)
		{
			Check.assumeNotNull(archive, "archive not null");
			final int archiveId = archive.getAD_Archive_ID();
			Check.assume(archiveId > 0, "archive is saved: {}", archive);
			archiveIdsToFail.add(archiveId);
		}
	}

	/**
	 * Case: in case retrieving binary data for one archive fails then {@link PrintingQueueAggregationException} shall be thrown
	 *
	 * @task http://dewiki908/mediawiki/index.php/05034_Fehler_bei_Massendruck_%28103300146225%29
	 */
	@Test(expected = PrintingQueueAggregationException.class)
	public void test_createPrintPackage_ArchiveWithError()
	{
		//
		// Setup Mocked Archive Storage
		archiveStorageFactory.removeAllArchiveStorages(); // make sure there are no other storages registered
		archiveStorageFactory.registerArchiveStorage(
				IArchiveStorageFactory.STORAGETYPE_Database,
				IArchiveStorageFactory.AccessMode.ALL, // match for Client and Server, to not bother about Ini.setClient (there are other tests which are checking this)
				MockedDBArchiveStorage.class);

		//
		// Setup Routing
		final I_AD_PrinterRouting printerRouting = helper.createPrinterRouting("printer01", "tray01",10, -1, -1, -1);
		helper.createPrinterHWCalibration("printer01-HW", "iso-a4", "tray01-HW",10, 10, 20); // 03733

		//
		// Setup PrintJob
		final I_C_Print_Job printJob = helper.createPrintJob();
		final I_C_Print_Job_Line line1 = helper.createPrintJobLine(printJob, printerRouting, "01");
		final I_C_Print_Job_Line line2 = helper.createPrintJobLine(printJob, printerRouting, "02");
		final I_C_Print_Job_Line line3 = helper.createPrintJobLine(printJob, printerRouting, "03");
		final I_C_Print_Job_Instructions printJobInstructions = helper.createPrintJobInstructions(printJob);

		// Fail getting binary data for line2
		MockedDBArchiveStorage.setFailGetBinaryData(line2.getC_Printing_Queue().getAD_Archive());

		final IPrintPackageCtx printCtx = printPackageBL.createInitialCtx(helper.getCtx());
		final PrintJobLinesAggregator aggregator = new PrintJobLinesAggregator(printingDataFactory, printCtx, printJobInstructions);

		final Mutable<ArrayKey> preceedingKey = new Mutable<Util.ArrayKey>(null);
		aggregator.add(line1, preceedingKey);
		aggregator.add(line2, preceedingKey);
		aggregator.add(line3, preceedingKey);

		// Here we expect to throw exception
		aggregator.createPrintPackage();
	}
}
