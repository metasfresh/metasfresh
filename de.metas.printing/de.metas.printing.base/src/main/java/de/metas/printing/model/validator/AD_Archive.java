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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.api.impl.SingletonPrintingQueueSource;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.spi.IPrintJobMonitor;

@Validator(I_AD_Archive.class)
public class AD_Archive
{

	/**
	 * If direct print is required for given {@link AD_Archive} then this method enqueues the archive to printing queue.
	 * 
	 * @param archive
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void printArchive(final I_AD_Archive archive)
	{
		// Do nothing if module is diabled.
		if (!Printing_Constants.isEnabled())
		{
			return;
		}
		
		if (!archive.isActive())
		{
			return;
		}

		I_C_Printing_Queue item = null;

		final boolean enqueueToPrintQueue = isEnqueToPrintingQueue(archive);
		if (enqueueToPrintQueue)
		{
			item = Services.get(IPrintingQueueBL.class).enqueue(archive);
		}

		final boolean createPrintJob = isCreatePrintJob(archive);
		if (item != null && createPrintJob)
		{
			forwardToJob(item);
		}
	}

	/**
	 * Directly create the print job. That means it will be printed now.
	 * 
	 * @param printingQueue
	 */
	private void forwardToJob(final I_C_Printing_Queue printingQueue)
	{
		final IPrintingQueueSource source = new SingletonPrintingQueueSource(printingQueue, printingQueue.getCreatedBy());

		// NOTE we cannot use UserConfirmationPrintJobMonitor because it's expecting to run out of transaction
		// final IPrintJobMonitor monitor = new UserConfirmationPrintJobMonitor();
		final IPrintJobMonitor monitor = IPrintJobMonitor.NULL;

		Services.get(IPrintJobBL.class).createPrintJobs(source, monitor);
	}
	
	private final boolean isEnqueToPrintingQueue(final I_AD_Archive archive)
	{
		// If we need to create a print job, then we shall enqueue to printing queue first
		if (isCreatePrintJob(archive))
		{
			return true;
		}
		
		if (archive.isDirectPrint())
		{
			return true;
		}
		
		return false;
	}
	
	private final boolean isCreatePrintJob(final I_AD_Archive archive)
	{
		// If we are explicitly ask to create a print job, then do it
		if (archive.isCreatePrintJob())
		{
			return true;
		}

		// Backward compatibility: If this is a generic archive we are always creating a print job directly.
		// This is because old code rely on this logic (at that time there was no IsCreatePrintJob flag).
		if (isGenericArchive(archive))
		{
			return true;
		}
		
		return false;
	}
	
	private final boolean isGenericArchive(final I_AD_Archive archive)
	{
		final boolean genericArchive = (archive.getAD_Table_ID() <= 0 && archive.getRecord_ID() <= 0);
		return genericArchive;
	}
}
