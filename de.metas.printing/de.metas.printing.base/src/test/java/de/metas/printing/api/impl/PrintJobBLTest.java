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
import static org.junit.Assert.*;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Assert;
import org.junit.Test;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.spi.impl.UserConfirmationPrintJobBatchMonitor;
import de.metas.printing.spi.impl.UserConfirmationPrintJobMonitor;

public class PrintJobBLTest extends AbstractPrintingTest
{
	@Test
	public void test_createJobsWithUserConfirmation_OneJob()
	{
		//
		// Setup routings
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting("printer01", "tray01", -1, -1, -1);
		routing1.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing1);

		//
		// Create printing queue
		helper.addToPrintQueue("01", 1, -1); // AD_Org_ID=1, C_DocType_ID=N/A

		final FixedAskCallback answersPool = new FixedAskCallback()
				.setDefaultExpectedAD_Message(UserConfirmationPrintJobBatchMonitor.MSG_CREATE_NEXT_JOB_0P)
				.addResponse(true) // this question shall never be asked because we have only one print job
		;
		helper.getClientUI().setAskCallback(answersPool);

		//
		// Expect 1 print jobs to be created
		final int printJobsCountActual = helper.createAllPrintJobs(new UserConfirmationPrintJobMonitor());
		Assert.assertEquals("Invalid Print Jobs count", 1, printJobsCountActual);

		// Assume no questions were asked because only one print job was created, and that one was automatically confirmed
		answersPool.assertNoQuestionsAsked();

		final List<I_C_Print_Job> printJobs = helper.getDB().getRecords(I_C_Print_Job.class);
		Assert.assertEquals("Invalid Print Jobs count in database", 1, printJobs.size());

		//
		// Validate Job1
		final I_C_Print_Job printJob1 = printJobs.get(0);
		final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob1);
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// Assert.assertEquals("Job1 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions1.getHostKey());
		Assert.assertEquals("Job1 instructions - Invalid UserOK", true, instructions1.isUserOK());
	}

	@Test
	public void test_createJobsWithUserConfirmation()
	{
		//
		// Setup routings
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting("printer01", "tray01", -1, -1, -1);
		routing1.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing1);

		final I_AD_PrinterRouting routing2 = helper.createPrinterRouting("printer01", "tray01", -1, -1, -1);
		routing1.setAD_Org_ID(2);
		InterfaceWrapperHelper.save(routing2);

		//
		// Create printing queue
		helper.addToPrintQueue("01", 1, -1); // AD_Org_ID=1, C_DocType_ID=N/A
		helper.addToPrintQueue("02", 2, -1); // AD_Org_ID=2, C_DocType_ID=N/A

		helper.getClientUI().setAskCallback(new FixedAskCallback()
				.setDefaultExpectedAD_Message(UserConfirmationPrintJobBatchMonitor.MSG_CREATE_NEXT_JOB_0P)
				.addResponse(false) // Job1 - fail
				.addResponse(true) // Job2 - success ... this question will never be asked because first job fails
				);

		//
		// Expect 1 print jobs to be created
		final int printJobsCountActual = helper.createAllPrintJobs(new UserConfirmationPrintJobMonitor());
		Assert.assertEquals("Invalid Print Jobs count", 1, printJobsCountActual);

		final List<I_C_Print_Job> printJobs = helper.getDB().getRecords(I_C_Print_Job.class);
		Assert.assertEquals("Invalid Print Jobs count in database", 1, printJobs.size());

		//
		// Validate Job1
		final I_C_Print_Job printJob1 = printJobs.get(0);
		final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob1);
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// Assert.assertEquals("Job1 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions1.getHostKey());
		Assert.assertEquals("Job1 instructions - Invalid UserOK", false, instructions1.isUserOK());
	}

	@Test
	public void test_createJobsWithUserConfirmationAndDecline()
	{
		//
		// Setup routings
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting("printer01", "tray01", -1, -1, -1);
		routing1.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing1);

		final I_AD_PrinterRouting routing2 = helper.createPrinterRouting("printer01", "tray01", -1, -1, -1);
		routing1.setAD_Org_ID(2);
		InterfaceWrapperHelper.save(routing2);

		//
		// Create printing queue
		helper.addToPrintQueue("01", 1, -1); // AD_Org_ID=2, C_DocType_ID=N/A
		helper.addToPrintQueue("02", 2, -1); // AD_Org_ID=1, C_DocType_ID=N/A

		final FixedAskCallback answersPool = new FixedAskCallback()
				.setDefaultExpectedAD_Message(UserConfirmationPrintJobBatchMonitor.MSG_CREATE_NEXT_JOB_0P)
				.addResponse(true) // job1 - success
				.addResponse(false) // job2 - fail
		;
		helper.getClientUI().setAskCallback(answersPool);

		//
		// Expect 2 print jobs to be created
		final int printJobsCountActual = helper.createAllPrintJobs(new UserConfirmationPrintJobMonitor());
		Assert.assertEquals("Invalid Print Jobs count", 2, printJobsCountActual);

		final List<I_C_Print_Job> printJobs = helper.getDB().getRecords(I_C_Print_Job.class);
		Assert.assertEquals("Invalid Print Jobs count in database", 2, printJobs.size());

		//
		// Validate Job1
		final I_C_Print_Job printJob1 = printJobs.get(0);
		final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob1);
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// Assert.assertEquals("Job1 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions1.getHostKey());
		Assert.assertEquals("Job1 instructions - Invalid UserOK", true, instructions1.isUserOK());

		//
		// Validate Job2
		final I_C_Print_Job printJob2 = printJobs.get(1);
		final I_C_Print_Job_Instructions instructions2 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob2);
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// Assert.assertEquals("Job2 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions2.getHostKey());
		Assert.assertEquals("Job2 instructions - Invalid UserOK", false, instructions2.isUserOK());
	}

	@Test
	public void test_createJobsWithUserConfirmationTwice()
	{
		//
		// Setup routings
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting("printer01", "tray01", -1, -1, -1);
		routing1.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing1);

		final I_AD_PrinterRouting routing2 = helper.createPrinterRouting("printer01", "tray01", -1, -1, -1);
		routing2.setAD_Org_ID(2);
		InterfaceWrapperHelper.save(routing2);

		//
		// Create printing queue
		helper.addToPrintQueue("01", 1, -1); // AD_Org_ID=1, C_DocType_ID=N/A
		helper.addToPrintQueue("02", 2, -1); // AD_Org_ID=2, C_DocType_ID=N/A

		final FixedAskCallback answersPool = new FixedAskCallback()
				.setDefaultExpectedAD_Message(UserConfirmationPrintJobBatchMonitor.MSG_CREATE_NEXT_JOB_0P)
				.addResponse(true) // job1 - success
				.addResponse(true) // job2 - success
		;
		helper.getClientUI().setAskCallback(answersPool);

		//
		// Expect 2 print jobs to be created
		final int printJobsCountActual = helper.createAllPrintJobs(new UserConfirmationPrintJobMonitor());
		Assert.assertEquals("Invalid Print Jobs count", 2, printJobsCountActual);

		final List<I_C_Print_Job> printJobs = helper.getDB().getRecords(I_C_Print_Job.class);
		Assert.assertEquals("Invalid Print Jobs count in database", 2, printJobs.size());

		//
		// Validate Job1
		final I_C_Print_Job printJob1 = printJobs.get(0);
		final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob1);
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// Assert.assertEquals("Job1 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions1.getHostKey());
		Assert.assertEquals("Job1 instructions - Invalid UserOK", true, instructions1.isUserOK());

		//
		// Validate Job2
		final I_C_Print_Job printJob2 = printJobs.get(1);
		final I_C_Print_Job_Instructions instructions2 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob2);
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// Assert.assertEquals("Job2 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions2.getHostKey());
		Assert.assertEquals("Job2 instructions - Invalid UserOK", true, instructions2.isUserOK());
	}

	@Test
	public void test_createJobsWithUserConfirmation_TruncatedJobs()
	{
		//
		// Setup routings
		final I_AD_PrinterRouting routing1 = helper.createPrinterRouting("printer01", "tray01", -1, -1, -1);
		routing1.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing1);

		final I_AD_PrinterRouting routing2 = helper.createPrinterRouting("printer01", "tray01", -1, -1, -1);
		routing1.setAD_Org_ID(2);
		InterfaceWrapperHelper.save(routing2);

		final FixedAskCallback answersPool = new FixedAskCallback()
				.setDefaultExpectedAD_Message(UserConfirmationPrintJobBatchMonitor.MSG_CREATE_NEXT_JOB_0P)
				.addResponse(true)
				.addResponse(true)
				.addResponse(false);
		helper.getClientUI().setAskCallback(answersPool);

		//
		// Setup Printing Queue
		final int maxLinesPerJob = 500;
		printJobBL.setMaxLinesPerJob(maxLinesPerJob);
		// Org1 (500 + 1 lines)
		for (int i = 1; i <= maxLinesPerJob + 1; i++)
		{
			helper.addToPrintQueue("01", 1, -1); // AD_Org_ID=1, C_DocType_ID=N/A
		}
		// Org2 (500 + 1 lines)
		for (int i = 1; i <= maxLinesPerJob + 1; i++)
		{
			helper.addToPrintQueue("02", 2, -1); // AD_Org_ID=2, C_DocType_ID=N/A
		}

		//
		// Expect 3(2 for Org1 and 1 for Org2) print jobs to be created
		final int printJobsCountActual = helper.createAllPrintJobs(new UserConfirmationPrintJobMonitor());
		Assert.assertEquals("Invalid Print Jobs count", 3, printJobsCountActual);

		final List<I_C_Print_Job> printJobs = helper.getDB().getRecords(I_C_Print_Job.class);
		Assert.assertEquals("Invalid Print Jobs count in database", 3, printJobs.size());

		//
		// Validate Job1
		final I_C_Print_Job printJob1 = printJobs.get(0);
		final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob1);
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// Assert.assertEquals("Job1 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions1.getHostKey());
		Assert.assertEquals("Job1 instructions - Invalid UserOK", true, instructions1.isUserOK());
		Assert.assertEquals("Job1 instructions - Invalid line count", maxLinesPerJob, getPrintJobLinesCount(printJob1));

		//
		// Validate Job2
		final I_C_Print_Job printJob2 = printJobs.get(1);
		final I_C_Print_Job_Instructions instructions2 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob2);
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// Assert.assertEquals("Job2 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions2.getHostKey());
		Assert.assertEquals("Job2 instructions - Invalid UserOK", true, instructions2.isUserOK());
		Assert.assertEquals("Job2 instructions - Invalid line count", 1, getPrintJobLinesCount(printJob2));

		//
		// Validate Job3
		final I_C_Print_Job printJob3 = printJobs.get(2);
		final I_C_Print_Job_Instructions instructions3 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob3);
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// Assert.assertEquals("Job3 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions3.getHostKey());
		Assert.assertEquals("Job3 instructions - Invalid UserOK", false, instructions3.isUserOK());
		Assert.assertEquals("Job3 instructions - Invalid line count", maxLinesPerJob, getPrintJobLinesCount(printJob3));
	}

	private int getPrintJobLinesCount(final I_C_Print_Job job)
	{
		final List<I_C_Print_Job_Line> list = IteratorUtils.toList(helper.getDAO().retrievePrintJobLines(job));
		return list.size();
	}

	/**
	 * Tests that if there are many matching routings, then we needs as many printJobDetails..i.e. don'T ignore all but the first one.
	 */
	@Test
	public void test_createMultiTrayJob()
	{
		final int c_DocType_ID = 12;

		//
		// Setup routings
		final I_AD_PrinterRouting routing11 = helper.createPrinterRouting("printer01", "tray01",
				c_DocType_ID, // routing has the same C_DocType_ID as the queue-item => should match
				-1, -1);
		routing11.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing11);

		final I_AD_PrinterRouting routing12 = helper.createPrinterRouting("printer01", "tray02",
				-1, // routing has no C_DocType_ID => should also match
				-1, -1);
		routing11.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing12);

		final I_AD_PrinterRouting otherRouting = helper.createPrinterRouting("printer23", "tray02",
				(c_DocType_ID + 10), // routing has not-matching C_DocType_ID => should be ignored
				-1, -1);
		otherRouting.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(otherRouting);

		helper.addToPrintQueue("01", 1, c_DocType_ID); // AD_Org_ID=1, C_DocType_ID=12

		final int printJobsCountActual = helper.createAllPrintJobs(new UserConfirmationPrintJobMonitor());
		assertThat(printJobsCountActual, is(1));

		final I_C_Print_Job printJob = helper.getDB().getRecords(I_C_Print_Job.class).get(0);

		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
		final List<I_C_Print_Job_Line> jobLines = IteratorUtils.toList(printingDAO.retrievePrintJobLines(printJob));
		assertThat(jobLines.size(), is(1));

		final I_C_Print_Job_Line jobLine = jobLines.get(0);
		final List<I_C_Print_Job_Detail> jobDetails = printingDAO.retrievePrintJobDetails(jobLine);
		assertThat("expecting one detail per routing", jobDetails.size(), is(2));

		assertThat(jobDetails.get(0).getAD_PrinterRouting(), is(routing11));
		assertThat(jobDetails.get(1).getAD_PrinterRouting(), is(routing12));

		final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob);
		assertThat(instructions1.getC_Print_Job(), is(printJob));
		assertThat(instructions1.getC_PrintJob_Line_From(), is(jobLine));
		assertThat(instructions1.getC_PrintJob_Line_To(), is(jobLine));
		assertThat("Job1 instructions - Invalid line count", getPrintJobLinesCount(printJob), is(1));
		// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
		// assertEquals("Job1 instructions - Invalid HostKey", helper.getSessionHostKey(), instructions1.getHostKey());
		assertEquals("Job1 instructions - Invalid UserOK", true, instructions1.isUserOK());
	}
}
