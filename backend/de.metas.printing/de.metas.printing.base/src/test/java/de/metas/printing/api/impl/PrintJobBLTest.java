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
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.commons.collections4.IteratorUtils;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_SysConfig;
import org.junit.Before;
import org.junit.Test;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.util.Services;

public class PrintJobBLTest extends AbstractPrintingTest
{

	@Before
	public final void beforeRunningTest()
	{
		final I_AD_SysConfig sysConfig = InterfaceWrapperHelper.newInstance(I_AD_SysConfig.class);
		sysConfig.setAD_Org_ID(0);
		InterfaceWrapperHelper.setValue(sysConfig, I_AD_SysConfig.COLUMNNAME_AD_Client_ID, 0);
		sysConfig.setName(PrintJobBL.SYSCONFIG_MAX_LINES_PER_JOB);
		sysConfig.setValue("2");
		InterfaceWrapperHelper.save(sysConfig);
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
		final I_AD_PrinterRouting routing11 = helper.createPrinterRouting("printer01", "tray01",10,
				c_DocType_ID, // routing has the same C_DocType_ID as the queue-item => should match
				-1, -1);
		routing11.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing11);

		final I_AD_PrinterRouting routing12 = helper.createPrinterRouting("printer01", "tray02",20,
				-1, // routing has no C_DocType_ID => should also match
				-1, -1);
		routing11.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing12);

		final I_AD_PrinterRouting otherRouting = helper.createPrinterRouting("printer23", "tray02",20,
				(c_DocType_ID + 10), // routing has not-matching C_DocType_ID => should be ignored
				-1, -1);
		otherRouting.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(otherRouting);

		helper.addToPrintQueue("01", 1, c_DocType_ID); // AD_Org_ID=1, C_DocType_ID=12

		helper.createAllPrintJobs();
		assertThat(helper.getDB().getRecords(I_C_Print_Job.class).size(), is(1));

		final I_C_Print_Job printJob = helper.getDB().getRecords(I_C_Print_Job.class).get(0);

		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
		final List<I_C_Print_Job_Line> jobLines = IteratorUtils.toList(printingDAO.retrievePrintJobLines(printJob));
		assertThat(jobLines.size(), is(1));

		final I_C_Print_Job_Line jobLine = jobLines.get(0);
		final List<I_C_Print_Job_Detail> jobDetails = printingDAO.retrievePrintJobDetails(jobLine);
		assertThat("expecting one detail per routing", jobDetails.size(), is(2));

		assertThat(jobDetails.get(0).getAD_PrinterRouting_ID(), is(routing11.getAD_PrinterRouting_ID()));
		assertThat(jobDetails.get(1).getAD_PrinterRouting_ID(), is(routing12.getAD_PrinterRouting_ID()));

		final I_C_Print_Job_Instructions instructions1 = helper.getDAO().retrievePrintJobInstructionsForPrintJob(printJob);
		assertThat(instructions1.getC_Print_Job(), is(printJob));
		assertThat(instructions1.getC_PrintJob_Line_From(), is(jobLine));
		assertThat(instructions1.getC_PrintJob_Line_To(), is(jobLine));
		assertThat("Job1 instructions - Invalid line count", getPrintJobLinesCount(printJob), is(1));
	}

	private int getPrintJobLinesCount(final I_C_Print_Job job)
	{
		final List<I_C_Print_Job_Line> list = IteratorUtils.toList(helper.getDAO().retrievePrintJobLines(job));
		return list.size();
	}


	@Test
	public void test_Splitting()
	{
		final int c_DocType_ID = 12;

		//
		// Setup routings
		final I_AD_PrinterRouting routing11 = helper.createPrinterRouting("printer01", "tray01",10,
				c_DocType_ID, // routing has the same C_DocType_ID as the queue-item => should match
				-1, -1);
		routing11.setAD_Org_ID(1);
		InterfaceWrapperHelper.save(routing11);

		helper.addToPrintQueue("01", 1, c_DocType_ID); // AD_Org_ID=1, C_DocType_ID=12
		helper.addToPrintQueue("02", 1, c_DocType_ID); // AD_Org_ID=1, C_DocType_ID=12
		helper.addToPrintQueue("03", 1, c_DocType_ID); // AD_Org_ID=1, C_DocType_ID=12
		helper.addToPrintQueue("04", 1, c_DocType_ID); // AD_Org_ID=1, C_DocType_ID=12
		helper.addToPrintQueue("05", 1, c_DocType_ID); // AD_Org_ID=1, C_DocType_ID=12

		// when
		helper.createAllPrintJobs();

		// then
		// remembers that we have SYSCONFIG_MAX_LINES_PER_JOB = 2
		final List<I_C_Print_Job> printJobs = helper.getDB().getRecords(I_C_Print_Job.class);
		Assertions.assertThat(printJobs).as("Invalid Print Jobs count").hasSize(3);
	}

}
