package de.metas.printing.rpl.requesthandler;

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


import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.printing.api.impl.AbstractPrintingTest;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Printing_Queue;

public class CreatePrintPackageRequestHandlerTest extends AbstractPrintingTest
{
	private CreatePrintPackageRequestHandler createPrintPackageRequestHandler;
	private I_AD_PrinterRouting printerRouting;

	@Before
	public void createMasterdata()
	{
		this.createPrintPackageRequestHandler = new CreatePrintPackageRequestHandler();

		this.printerRouting = helper.createPrinterRouting("printer01", "tray01", 10,-1, -1, -1);

		helper.createPrinterHWCalibration("printer01-HW", "iso-a4", "tray01-HW", 10, 10, 20); // 03733

	}

	@Test
	public void test_createResponse()
	{
		// Setup & create PrintJob
		final I_C_Print_Job printJob = helper.createPrintJob();
		helper.createPrintJobLine(printJob, printerRouting, "01");
		helper.createPrintJobInstructions(printJob);

		final I_C_Print_Package printPackageRequest = helper.createPrintPackageRequest();
		final I_C_Print_Package printPackageResponse = createPrintPackageRequestHandler.createResponse(printPackageRequest);
		Assert.assertNotNull("Print Package response shall be created", printPackageResponse);
	}

	@Test
	public void test_createResponse_missing_archive()
	{
		// Setup & create PrintJob
		final I_C_Print_Job printJob = helper.createPrintJob();
		final I_C_Print_Job_Line printJobLine = helper.createPrintJobLine(printJob, printerRouting, "01");

		// setting archive to null... this shall stop print package creation
		{
			final I_C_Printing_Queue pq = printJobLine.getC_Printing_Queue();
			pq.setAD_Archive(null);
			InterfaceWrapperHelper.save(pq);
		}

		helper.createPrintJobInstructions(printJob);

		final I_C_Print_Package printPackageRequest = helper.createPrintPackageRequest();
		final I_C_Print_Package printPackageResponse = createPrintPackageRequestHandler.createResponse(printPackageRequest);
		Assert.assertNull("Print Package response shall NOT be created", printPackageResponse);

		final List<I_C_Print_Package> existingPrintPackages = POJOLookupMap.get().getRecords(I_C_Print_Package.class);
		Assert.assertTrue("No print packages shall exist in database", existingPrintPackages.isEmpty());
	}

}
