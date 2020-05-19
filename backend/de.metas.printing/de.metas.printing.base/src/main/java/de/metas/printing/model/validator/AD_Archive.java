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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.api.impl.SingletonPrintingQueueSource;
import de.metas.printing.model.I_AD_Archive;
import de.metas.printing.model.I_C_Doc_Outbound_Config;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Services;

@Validator(I_AD_Archive.class)
public class AD_Archive
{

	/**
	 * Check if the archive references a docOutBoundConfig, and if yes, copy its settings (possibly overriding previous settings).
	 * 
	 * Note: if the config id is changed to <code>null</code>, then do nothing.
	 * 
	 * @task http://dewiki908/mediawiki/index.php/09417_Massendruck_-_Sofort-Druckjob_via_Ausgehende-Belege_konfig_einstellbar_%28101934367465%29
	 * @param archive
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_AD_Archive.COLUMNNAME_C_Doc_Outbound_Config_ID)
	public void updateArchiveFlags(final I_AD_Archive archive)
	{
		if (archive.getC_Doc_Outbound_Config_ID() <= 0)
		{
			return; // nothing to do
		}

		// task 09417: also check if the archive references a docOutBoundConfig, and if yes, use its settings.
		final I_C_Doc_Outbound_Config config = InterfaceWrapperHelper.create(archive.getC_Doc_Outbound_Config(),
				I_C_Doc_Outbound_Config.class);
		archive.setIsDirectEnqueue(config.isDirectEnqueue());
		archive.setIsCreatePrintJob(config.isCreatePrintJob());
	}

	/**
	 * If direct print is required for given {@link AD_Archive} then this method enqueues the archive to printing queue.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_AD_Archive.COLUMNNAME_IsCreatePrintJob,
					I_AD_Archive.COLUMNNAME_IsDirectEnqueue,
					I_AD_Archive.COLUMNNAME_C_Doc_Outbound_Config_ID,
					I_AD_Archive.COLUMNNAME_IsActive })
	public void printArchive(final I_AD_Archive archive)
	{
		if (!archive.isActive())
		{
			return;
		}

		I_C_Printing_Queue item = null;

		final boolean enqueueToPrintQueue = isEnqueueToPrintingQueue(archive);
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
	 */
	private void forwardToJob(final I_C_Printing_Queue printingQueue)
	{
		final IPrintingQueueSource source = new SingletonPrintingQueueSource(printingQueue, printingQueue.getCreatedBy());

		Services.get(IPrintJobBL.class).createPrintJobs(source);
	}

	private final boolean isEnqueueToPrintingQueue(final I_AD_Archive archive)
	{
		// If we need to create a print job, then we shall enqueue to printing queue first
		if (isCreatePrintJob(archive))
		{
			return true;
		}

		if (archive.isDirectEnqueue())
		{
			return true;
		}

		// task 09417: also check if the archive references a docOutBoundConfig, and if yes, use its settings.
		if (archive.getC_Doc_Outbound_Config_ID() > 0)
		{
			final I_C_Doc_Outbound_Config config = InterfaceWrapperHelper.create(archive.getC_Doc_Outbound_Config(),
					I_C_Doc_Outbound_Config.class);
			if (config.isDirectEnqueue() || config.isCreatePrintJob())
			{
				return true;
			}
		}
		return false;
	}

	private final boolean isCreatePrintJob(final I_AD_Archive archive)
	{
		// If we are explicitly asked to create a print job, then do it
		if (archive.isCreatePrintJob())
		{
			return true;
		}

		if (archive.getC_Doc_Outbound_Config_ID() > 0)
		{
			final I_C_Doc_Outbound_Config config = InterfaceWrapperHelper.create(archive.getC_Doc_Outbound_Config(),
					I_C_Doc_Outbound_Config.class);
			if (config.isCreatePrintJob())
			{
				return true;
			}
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
