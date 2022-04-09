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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.AsyncBatchId;
import de.metas.async.api.AsyncBatchType;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import java.time.Duration;
import java.util.List;

/**
 * <ul>
 * * Checks each package if is processed If the async batch is not processed, is postpone it for a specific timeframe
 * </ul>
 * <ul>
 * * Also tries to update the process flag is some specific conditions are met
 * </ul>
 */
public class CheckProcessedAsynBatchWorkpackageProcessor implements IWorkpackageProcessor
{
	private static final String SYSCONFIG_WorkpackageSkipTimeoutMillis = "de.metas.async.housekeeping.spi.impl.CheckProcessedAsynBatchWorkpackageProcessor.WorkpackageSkipTimeoutMillis";
	private static final int DEFAULT_WorkpackageSkipTimeoutMillis = 1000 * 60 * 1; // 1min

	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final List<I_C_Async_Batch> batches = queueDAO.retrieveAllItems(workpackage, I_C_Async_Batch.class);

		if (batches == null || batches.size() != 1)
		{
			throw new AdempiereException("There should always be just one asyncBatch enqueued for each 'CheckProcessedAsynBatchWorkpackageProcessor' instance!")
					.appendParametersToMessage()
					.setParameter("C_Queue_WorkPackage_ID", workpackage.getC_Queue_WorkPackage_ID());
		}

		final I_C_Async_Batch asyncBatch = batches.get(0);

		if (asyncBatch.isProcessed() || !asyncBatch.isActive())
		{
			// already processed => do nothing
			return Result.SUCCESS;
		}

		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(asyncBatch.getC_Async_Batch_ID());

		//
		// check if we need to wait for a bit before trying to set the processed status
		final Duration delayUntilCheckingProcessedState = asyncBatchBL.getTimeUntilProcessedRecheck(asyncBatch);

		if (delayUntilCheckingProcessedState.toMillis() > 0)
		{
			throw WorkpackageSkipRequestException.createWithTimeout("AsyncBatch not ready for processed status check. Postponed!", Math.toIntExact(delayUntilCheckingProcessedState.toMillis()));
		}

		//
		// try to set the processed status
		final boolean batchIsProcessed = asyncBatchBL.updateProcessedOutOfTrx(asyncBatchId);
		if (batchIsProcessed)
		{
			return Result.SUCCESS;
		}

		//
		// check if keep alive time expired and if it has; set wp to error
		final boolean keepAliveTimeExpired = asyncBatchBL.keepAliveTimeExpired(asyncBatchId);
		if (keepAliveTimeExpired)
		{
			throw new AdempiereException("@IAsyncBatchBL.keepAliveTimeExpired@");
		}

		throw WorkpackageSkipRequestException.createWithTimeout("Not processed yet. Postponed!", getWorkpackageSkipTimeoutMillis(asyncBatch));
	}

	private int getWorkpackageSkipTimeoutMillis(@NonNull final I_C_Async_Batch asyncBatch)
	{
		if (asyncBatch.getC_Async_Batch_Type_ID() > 0)
		{
			final long skipTimeoutMillis = asyncBatchBL.getAsyncBatchType(asyncBatch).map(AsyncBatchType::getSkipTimeout).orElse(Duration.ZERO).toMillis();
			if (skipTimeoutMillis > 0)
			{
				return (int)skipTimeoutMillis;
			}
		}
		return sysConfigBL.getIntValue(SYSCONFIG_WorkpackageSkipTimeoutMillis, DEFAULT_WorkpackageSkipTimeoutMillis);
	}
}
