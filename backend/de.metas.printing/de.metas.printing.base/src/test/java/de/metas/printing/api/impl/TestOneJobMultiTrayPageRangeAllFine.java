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


import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.printing.api.util.PdfCollator;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job;

public class TestOneJobMultiTrayPageRangeAllFine extends AbstractPrintJobLinesAggregatorPageRangeTestBase
{
	@Override
	protected byte[] step10_CreatePdfDataToPrint()
	{
		final byte[] binaryPdfData = new PdfCollator()
				.addPages(helper.getPdf("01"), 1, 1)
				.addPages(helper.getPdf("01"), 2, 2)
				.toByteArray();
		return binaryPdfData;
	}

	@Override
	protected I_C_Print_Job step20_createPrintJob(final byte[] binaryPdfData, final List<I_AD_PrinterRouting> routings)
	{
		final I_C_Print_Job printJob = helper.createPrintJob();

		helper.createPrintJobLine(printJob,
				routings,
				binaryPdfData);

		helper.createPrintJobInstructions(printJob);
		InterfaceWrapperHelper.save(printJob);

		return printJob;
	}
}
