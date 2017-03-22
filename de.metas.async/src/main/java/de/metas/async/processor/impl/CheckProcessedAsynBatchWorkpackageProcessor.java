/**
 * 
 */
package de.metas.async.processor.impl;

/*
 * #%L
 * de.metas.async
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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;

/**
 * <ul>
 * * Checks each package if is processed If the async batch is not processed, is postpone it for a specific timeframe
 * </ul>
 * <ul>
 * * Also tries to update the process flag is some specific conditions are met
 * </ul>
 * 
 * @author cg
 *
 */
public class CheckProcessedAsynBatchWorkpackageProcessor implements IWorkpackageProcessor
{
	private static final String SYSCONFIG_WorkpackageSkipTimeoutMillis = "de.metas.async.housekeeping.spi.impl.CheckProcessedAsynBatchWorkpackageProcessor.WorkpackageSkipTimeoutMillis";
	private static final int DEFAULT_WorkpackageSkipTimeoutMillis = 1000 * 60 * 1; // 1min

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		boolean hasSkippedBatches = false;
		boolean hasError = false;
		final List<I_C_Async_Batch> batches = Services.get(IQueueDAO.class).retrieveItems(workpackage, I_C_Async_Batch.class, localTrxName);
		for (final I_C_Async_Batch asyncBatch : batches)
		{
			if (asyncBatch.isProcessed() || !asyncBatch.isActive())
			{
				// already processed => do nothing
				continue;
			}

			// check if keep alive time expired and if it has; set wp to error

			final boolean keepAliveTimeExpired = Services.get(IAsyncBatchBL.class).keepAliveTimeExpired(asyncBatch);
			if (keepAliveTimeExpired)
			{
				hasError = true;
				continue;
			}

			//
			// Try to mark it as processed now
			updateProcessed(asyncBatch);

			if (!asyncBatch.isProcessed())
			{
				hasSkippedBatches = true;
				continue;
			}

		}

		if (hasError)
		{
			throw new AdempiereException("@IAsyncBatchBL.keepAliveTimeExpired@");
		}

		if (hasSkippedBatches)
		{
			final WorkpackageSkipRequestException skipExcep = WorkpackageSkipRequestException.createWithTimeout("Not processed yet. Postponed!", getWorkpackageSkipTimeoutMillis());
			throw skipExcep;
		}

		return Result.SUCCESS;
	}

	/**
	 * Flag batch as processed if possible
	 * 
	 * @param batch
	 */
	private void updateProcessed(final I_C_Async_Batch batch)
	{
		Services.get(IAsyncBatchBL.class).updateProcessed(batch);
	}

	/**
	 * get skip timeout
	 * 
	 * @return
	 */
	private final int getWorkpackageSkipTimeoutMillis()
	{
		return Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_WorkpackageSkipTimeoutMillis, DEFAULT_WorkpackageSkipTimeoutMillis);
	}
}
