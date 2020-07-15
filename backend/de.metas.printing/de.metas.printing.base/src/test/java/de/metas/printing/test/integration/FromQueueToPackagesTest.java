package de.metas.printing.test.integration;

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

import de.metas.printing.api.impl.AbstractPrintingTest;
import de.metas.printing.api.util.PdfCollator;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FromQueueToPackagesTest extends AbstractPrintingTest
{
	@Test
	// this test calls other methods to execute the checkings
	public void test_generic01()
	{
		//
		// Setup routings
		helper.createPrinterRouting("printer01", "tray01", 10, -1, -1, -1);

		//
		// Create printing queue
		helper.addToPrintQueue("01", -1, -1); // AD_Org_ID=N/A, C_DocType_ID=N/A
		helper.addToPrintQueue("02", -1, -1); // AD_Org_ID=N/A, C_DocType_ID=N/A
		helper.addToPrintQueue("03", -1, -1); // AD_Org_ID=N/A, C_DocType_ID=N/A

		//
		// Setup expected result
		final byte[] pdfDataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 20)
				.addPages(helper.getPdf("02"), 1, 20)
				.addPages(helper.getPdf("03"), 1, 20)
				.toByteArray();
		final int printJobsCountExpected = 1;

		//
		// Execute Test
		executeGenericTest(printJobsCountExpected, pdfDataExpected);
	}

	@Test
	// this test calls other methods to execute the checkings
	public void test_generic02()
	{
		//
		// Setup routings:
		final I_AD_PrinterRouting printerRouting01 = helper.createPrinterRouting("printer01", "tray01", 10, -1, -1, -1);
		printerRouting01.setAD_Org_ID(1);
		// printerRouting01.setAD_User_ID(1);
		printerRouting01.setC_DocType_ID(1);
		helper.getDB().save(printerRouting01);

		final I_AD_PrinterRouting printerRouting02 = helper.createPrinterRouting("printer01", "tray01", 10, -1, 10, 15);
		printerRouting02.setAD_Org_ID(1);
		// printerRouting02.setAD_User_ID(1);
		printerRouting02.setC_DocType_ID(2);
		helper.getDB().save(printerRouting02);

		//
		// Create printing queue
		helper.addToPrintQueue("01", 1, 1); // AD_Org_ID=1, C_DocType_ID=1
		helper.addToPrintQueue("02", 1, 2); // AD_Org_ID=1, C_DocType_ID=2
		helper.addToPrintQueue("03", 1, 1); // AD_Org_ID=1, C_DocType_ID=1

		//
		// Setup expected result
		final byte[] pdfDataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 20)
				.addPages(helper.getPdf("02"), 10, 15)
				.addPages(helper.getPdf("03"), 1, 20)
				.toByteArray();

		final int printJobsCountExpected = 1;

		//
		// Execute Test
		executeGenericTest(printJobsCountExpected, pdfDataExpected);
	}

	@Test
	// this test calls other methods to execute the checkings
	public void test_generic03()
	{
		//
		// Setup routings:
		final I_AD_PrinterRouting printerRouting01 = helper.createPrinterRouting("test03-printer01", "tray01", 10, -1, 1, 2);
		printerRouting01.setC_DocType_ID(1);
		helper.getDB().save(printerRouting01);

		final I_AD_PrinterRouting printerRouting02 = helper.createPrinterRouting("test03-printer02", "tray01", 10, -1, 3, 4);
		printerRouting02.setC_DocType_ID(2);
		helper.getDB().save(printerRouting02);

		final I_AD_PrinterRouting printerRouting03 = helper.createPrinterRouting("test03-printer01", "tray01", 10, -1, 5, 6);
		printerRouting03.setC_DocType_ID(3);
		helper.getDB().save(printerRouting03);

		//
		// Create printing queue
		helper.addToPrintQueue("01", 1, 1); // AD_Org_ID=1, C_DocType_ID=1
		helper.addToPrintQueue("02", 1, 2); // AD_Org_ID=1, C_DocType_ID=2
		helper.addToPrintQueue("03", 1, 3); // AD_Org_ID=1, C_DocType_ID=3

		//
		// Setup expected result
		// task 08958: we want the paged in the order of out print job line. i.e. two lines shall *not* be aggregated into one, just because they match.
		// that is because we need a straight forward ordering and that is more important than minimizing the number of print jobs.
		final byte[] pdfDataExpected = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 2)
				.addPages(helper.getPdf("02"), 3, 4)
				.addPages(helper.getPdf("03"), 5, 6)
				.toByteArray();

		final int printJobsCountExpected = 1;

		//
		// Execute Test
		executeGenericTest(printJobsCountExpected, pdfDataExpected);
	}

	private void executeGenericTest(final int printJobsCountExpected, final byte[] pdfDataExpected)
	{
		helper.createAllPrintJobs();

		final int printJobsCountActual = POJOLookupMap.get().getRecords(I_C_Print_Job.class).size();
		Assert.assertEquals("Invalid Print Jobs count", printJobsCountExpected, printJobsCountActual);
		assumePrintJobInstructions(X_C_Print_Job_Instructions.STATUS_Pending);

		final I_C_Print_Job printJobExpected = null; // we don't know what print job(s) were created
		helper.createNextPrintPackageAndTest(printJobExpected, pdfDataExpected);

		assumePrintQueueProcessed();
		assumePrintJobsProcessed();
		assumePrintJobInstructions(X_C_Print_Job_Instructions.STATUS_Send);
		helper.assumeNothingLocked();
	}

	@Test
	public void test_TwoPrintJobsCreated()
	{
		//
		// Setup routings
		helper.createPrinterRouting("printer01", "tray01", 10, -1, -1, -1);

		//
		// Create printing queue
		helper.addToPrintQueue("01", 1, -1); // AD_Org_ID=1, C_DocType_ID=N/A
		helper.addToPrintQueue("02", 2, -1); // AD_Org_ID=2, C_DocType_ID=N/A

		//
		// Setup expected PDFs
		final List<byte[]> pdfDataExpected = new ArrayList<>();
		pdfDataExpected.add(new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 20)
				.toByteArray()
		);
		pdfDataExpected.add(new PdfCollator()
				.addPages(helper.getPdf("02"), 1, 20)
				.toByteArray()
		);

		// when
		helper.createAllPrintJobs();

		// then
		final List<I_C_Print_Job> printJobs = helper.getDB().getRecords(I_C_Print_Job.class);
		assertThat(printJobs).as("Invalid Print Jobs count").hasSize(2);

		for (int i = 0; i < printJobs.size(); i++)
		{
			helper.createNextPrintPackageAndTest(printJobs.get(i), pdfDataExpected.get(i));
		}
	}

	@Test
	public void test_TwoPrintJobs_SecondOneIsUsedBecauseFirstOneFails()
	{
		//
		// Setup routings
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting("printer01", "tray01", 10, -1, -1, -1);
		routing1.setAD_Org_ID(1);
		helper.getDB().save(routing1);
		final I_AD_PrinterRouting routing2 = helper.createPrinterRouting("printer02", "tray01", 10, -1, -1, -1);
		routing2.setAD_Org_ID(2);
		helper.getDB().save(routing2);

		//
		// Create printing queue
		helper.addToPrintQueue("01", 1, -1); // AD_Org_ID=1, C_DocType_ID=N/A
		helper.addToPrintQueue("02", 2, -1); // AD_Org_ID=2, C_DocType_ID=N/A

		// when
		helper.createAllPrintJobs();

		// then - Expect 2 print jobs to be created
		final List<I_C_Print_Job> printJobs = helper.getDB().getRecords(I_C_Print_Job.class);
		assertThat(printJobs).as("Invalid Print Jobs count").hasSize(2);

		// Validate PrintJob 1
		final I_C_Print_Job printJob1 = printJobs.get(0);
		{
			final List<I_C_Print_Job_Line> lines = IteratorUtils.toList(helper.getDAO().retrievePrintJobLines(printJob1));
			Assert.assertEquals("Job1 - Only one line was expected for " + printJob1, 1, lines.size());

			final I_C_Print_Job_Line line = lines.get(0);
			final I_C_Print_Job_Detail detail = helper.getDAO().retrievePrintJobDetails(line).get(0);
			Assert.assertEquals("Job1 - Invalid routing used", routing1.getAD_PrinterRouting_ID(), detail.getAD_PrinterRouting_ID());
		}
		// Validate PrintJob 2
		final I_C_Print_Job printJob2 = printJobs.get(1);
		{
			final List<I_C_Print_Job_Line> lines = IteratorUtils.toList(helper.getDAO().retrievePrintJobLines(printJob2));
			Assert.assertEquals("Job2 - Only one line was expected for " + printJob2, 1, lines.size());

			final I_C_Print_Job_Line line = lines.get(0);
			final I_C_Print_Job_Detail detail = helper.getDAO().retrievePrintJobDetails(line).get(0);
			Assert.assertEquals("Job2 - Invalid routing used", routing2.getAD_PrinterRouting_ID(), detail.getAD_PrinterRouting_ID());
		}

		//
		// Everything is fine.... now drop the matching for printer01
		// ... this shall produce errors when trying to generate the package for first print job
		{
			final I_AD_Printer_Matching matching = helper.getDAO().retrievePrinterMatching(helper.getSessionHostKey(), null, routing1);
			helper.getDB().delete(matching);
		}

		//
		// Creating next package
		// Expectations: printJob2 is used
		helper.createNextPrintPackageAndTest(printJob2, new PdfCollator()
				.addPages(helper.getPdf("02"), 1, 20)
				.toByteArray()
		);

		//
		// Validate PrintJob1 Instructions
		{
			final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob1);
			// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged inAssert.assertEquals("Job1 instructions - Invalid HostKey",
			// helper.getSessionHostKey(), instructions1.getHostKey());
			// Assert.assertEquals("Job1 instructions - Invalid status", X_C_Print_Job_Instructions.STATUS_Error, instructions1.getStatus());
			Assert.assertNotNull("Job1 instructions - Missing error message", instructions1.getErrorMsg());
		}

		//
		// Validate PrintJob2 Instructions
		{
			final I_C_Print_Job_Instructions instructions2 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob2);
			// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
			// Assert.assertEquals("Job2 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions2.getHostKey());
			Assert.assertEquals("Job2 instructions - Invalid status", X_C_Print_Job_Instructions.STATUS_Send, instructions2.getStatus());
			Assert.assertNull("Job2 instructions - Missing error message", instructions2.getErrorMsg());
		}

		//
		// Recreate back the HW matching for printer01
		helper.createPrinterConfigAndMatching(helper.getSessionHostKey(), "printer01-hw-again", "tray01-hw-again", 10, "printer01", "tray01");

		//
		// Job1 instructions, prepare it again
		{
			final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob1);
			instructions1.setStatus(X_C_Print_Job_Instructions.STATUS_Pending);
			instructions1.setErrorMsg(null);
			InterfaceWrapperHelper.save(instructions1);
		}

		//
		// Creating next package
		// Expectations: printJob1 is used
		{
			helper.createNextPrintPackageAndTest(printJob1, new PdfCollator()
					.addPages(helper.getPdf("01"), 1, 20)
					.toByteArray()
			);
		}

		//
		// Validate PrintJob2 Instructions
		{
			final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob1);
			Assert.assertEquals("Job1 instructions - Invalid status", X_C_Print_Job_Instructions.STATUS_Send, instructions1.getStatus());
			Assert.assertNull("Job1 instructions - Missing error message", instructions1.getErrorMsg());
		}

		//
		// Final assumptions
		assumePrintQueueProcessed();
		assumePrintJobInstructions(X_C_Print_Job_Instructions.STATUS_Send); // everything is Send
		assumePrintJobsProcessed();
		helper.assumeNothingLocked();
	}

	public void assumePrintQueueProcessed()
	{
		for (final I_C_Printing_Queue pq : helper.getDB().getRecords(I_C_Printing_Queue.class))
		{
			Assert.assertTrue("Record " + pq + " shall be processed", pq.isProcessed());
		}
	}

	public void assumePrintJobsProcessed()
	{
		for (final I_C_Print_Job printJob : helper.getDB().getRecords(I_C_Print_Job.class))
		{
			Assert.assertTrue("Record " + printJob + " shall be processed", printJob.isProcessed());
		}
	}

	public void assumePrintJobInstructions(final String expectedStatus)
	{
		for (final I_C_Print_Job_Instructions printJobInstructions : helper.getDB().getRecords(I_C_Print_Job_Instructions.class))
		{
			Assert.assertEquals("Invalid status for " + printJobInstructions, expectedStatus, printJobInstructions.getStatus());
		}
	}
}
