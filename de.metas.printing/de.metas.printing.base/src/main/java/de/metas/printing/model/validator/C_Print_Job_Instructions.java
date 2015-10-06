package de.metas.printing.model.validator;

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


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidator;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job_Instructions;

@Validator(I_C_Print_Job_Instructions.class)
public class C_Print_Job_Instructions
{
	/**
	 * Create Document Outbound only if Status column just changed to Done
	 * 
	 * @param jobInstructions
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_CHANGE_REPLICATION }
			, ifColumnsChanged = I_C_Print_Job_Instructions.COLUMNNAME_Status)
	public void logDocOutbound(final I_C_Print_Job_Instructions jobInstructions)
	{
		// We log the doc outbound only when Status changed to Done
		if (!X_C_Print_Job_Instructions.STATUS_Done.equals(jobInstructions.getStatus()))
		{
			return;
		}

		final I_AD_User userToPrint = jobInstructions.getAD_User_ToPrint();
		final Iterator<I_C_Print_Job_Line> lines = Services.get(IPrintingDAO.class).retrievePrintJobLines(jobInstructions);
		for (final I_C_Print_Job_Line line : IteratorUtils.asIterable(lines))
		{
			logDocOutbound(line, userToPrint);
		}
	}

	private void logDocOutbound(final I_C_Print_Job_Line line, final I_AD_User userToPrint)
	{
		final Set<String> printerNames = new HashSet<String>();
		for (final I_C_Print_Job_Detail detail : Services.get(IPrintingDAO.class).retrievePrintJobDetails(line))
		{
			final I_AD_PrinterRouting routing = detail.getAD_PrinterRouting();
			final String printerName = routing.getAD_Printer().getPrinterName();

			if (!printerNames.add(printerName))
			{
				continue; // we already added a log record for this printer name
			}

			final I_C_Printing_Queue queueItem = line.getC_Printing_Queue();
			final I_AD_Archive archive = queueItem.getAD_Archive();

			Services.get(IArchiveEventManager.class).firePrintOut(archive,
					userToPrint,
					printerName,
					IArchiveEventManager.COPIES_ONE,
					IArchiveEventManager.STATUS_Success
					);
		}
	}
}
