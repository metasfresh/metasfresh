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

import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.printing.PrintOutputFacade;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.api.impl.SingletonPrintingQueueSource;
import de.metas.printing.model.I_AD_Archive;
import de.metas.printing.model.I_C_Doc_Outbound_Config;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Archive.class)
@Component
public class AD_Archive
{
	private final transient Logger logger = LogManager.getLogger(AD_Archive.class);

	private final PrintOutputFacade printOutputFacade;
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);

	public AD_Archive(@NonNull final PrintOutputFacade printOutputFacade)
	{
		this.printOutputFacade = printOutputFacade;
	}

	/**
	 * Check if the archive references a docOutBoundConfig, and if yes, copy its settings (possibly overriding previous settings).
	 * <p>
	 * Note: if the config id is changed to <code>null</code>, then do nothing.
	 * <p>
	 * task http://dewiki908/mediawiki/index.php/09417_Massendruck_-_Sofort-Druckjob_via_Ausgehende-Belege_konfig_einstellbar_%28101934367465%29
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
		archive.setIsDirectProcessQueueItem(config.isDirectProcessQueueItem());
	}

	/**
	 * If direct print is required for given {@link AD_Archive} then this method enqueues the archive to printing queue.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_AD_Archive.COLUMNNAME_IsDirectProcessQueueItem,
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

		final boolean enqueueToPrintQueue = isIsCreatePrintingQueueItem(archive);
		if (enqueueToPrintQueue)
		{
			item = printingQueueBL.enqueue(archive);
		}

		final boolean createPrintJob = isProcessQueueItem(archive);
		if (item != null && createPrintJob)
		{
			forwardToJob(item);
		}
	}

	/**
	 * Directly create the print job. That means it will be printed now.
	 */
	private void forwardToJob(@NonNull final I_C_Printing_Queue printingQueue)
	{
		final UserId adUserPrintJobId = UserId.ofRepoId(CoalesceUtil.firstGreaterThanZero(printingQueue.getAD_User_ID(), printingQueue.getCreatedBy()));
		final IPrintingQueueSource source = new SingletonPrintingQueueSource(printingQueue, adUserPrintJobId);

		printOutputFacade.print(source);
	}

	private boolean isIsCreatePrintingQueueItem(@NonNull final I_AD_Archive archive)
	{
		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(archive))
		{
			// If we need to create a print job, then we shall enqueue to printing queue first
			if (isProcessQueueItem(archive))
			{
				logger.debug("IsCreatePrintingQueueItem - IsProcessQueueItem returned true; -> return true");
				return true;
			}

			if (archive.isDirectEnqueue())
			{
				logger.debug("IsCreatePrintingQueueItem - AD_Archive.IsDirectEnqueue=true; -> return true");
				return true;
			}

			// task 09417: also check if the archive references a docOutBoundConfig, and if yes, use its settings.
			if (archive.getC_Doc_Outbound_Config_ID() > 0)
			{
				final I_C_Doc_Outbound_Config config = InterfaceWrapperHelper.create(archive.getC_Doc_Outbound_Config(),
						I_C_Doc_Outbound_Config.class);
				if (config.isDirectEnqueue() || config.isDirectProcessQueueItem())
				{
					logger.debug("IsCreatePrintingQueueItem - AD_Archive has C_Doc_Outbound_Config_ID={} "
									+ "which has IsDirectEnqueue={} and DirectProcessQueueItem={}; -> return true",
							archive.getC_Doc_Outbound_Config_ID(), config.isDirectEnqueue(), config.isDirectProcessQueueItem());
					return true;
				}
			}
			return false;
		}
	}

	private boolean isProcessQueueItem(@NonNull final I_AD_Archive archive)
	{
		// If we are explicitly asked to create a print job, then do it
		if (archive.isDirectProcessQueueItem())
		{
			logger.debug("IsProcessQueueItem - AD_Archive.IsDirectProcessQueueItem=true; -> return true");
			return true;
		}

		if (archive.getC_Doc_Outbound_Config_ID() > 0)
		{
			final I_C_Doc_Outbound_Config config = InterfaceWrapperHelper.create(archive.getC_Doc_Outbound_Config(),
					I_C_Doc_Outbound_Config.class);
			if (config.isDirectProcessQueueItem())
			{
				logger.debug("IsProcessQueueItem - AD_Archive has C_Doc_Outbound_Config_ID={} which has IsDirectProcessQueueItem=true; -> return true", archive.getC_Doc_Outbound_Config_ID());
				return true;
			}
		}

		// Backward compatibility: If this is a generic archive we are always creating a print job directly.
		// This is because old code rely on this logic (at that time there was no IsCreatePrintJob flag).
		if (isGenericArchive(archive))
		{
			logger.debug("IsProcessQueueItem - AD_Archive is a generic archive without record reference; -> return true");
			return true;
		}

		logger.debug("IsProcessQueueItem - none of the conditions applied -> return false");
		return false;
	}

	private boolean isGenericArchive(final I_AD_Archive archive)
	{
		return (archive.getAD_Table_ID() <= 0 && archive.getRecord_ID() <= 0);
	}
}
