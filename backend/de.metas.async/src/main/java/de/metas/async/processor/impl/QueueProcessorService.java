/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.async.processor.impl;

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.impl.planner.AsyncProcessorPlanner;
import de.metas.async.service.AsyncBatchObserver;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_Default;

@Service
public class QueueProcessorService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	@NonNull
	private final AsyncBatchObserver asyncBatchObserver;

	public QueueProcessorService(final @NonNull AsyncBatchObserver asyncBatchObserver)
	{
		this.asyncBatchObserver = asyncBatchObserver;
	}

	public void runAndWait(@NonNull final IQueueProcessor queueProcessor)
	{
		final AsyncBatchId asyncBatchId = trxManager.callInNewTrx(() -> {
			final AsyncBatchId batchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_Default);

			final int nrOfWorkPackages = queueProcessor.getQueue().assignAsyncBatchForProcessing(batchId);

			if (nrOfWorkPackages > 0)
			{
				return batchId;
			}

			return null;
		});

		if (asyncBatchId == null)
		{
			return;
		}

		final AsyncProcessorPlanner asyncProcessorPlanner = new AsyncProcessorPlanner();
		try
		{
			asyncProcessorPlanner.addQueueProcessor(queueProcessor);
			asyncProcessorPlanner.start();

			asyncBatchObserver.observeOn(asyncBatchId);
			asyncBatchObserver.waitToBeProcessed(asyncBatchId);
		}
		finally
		{
			asyncProcessorPlanner.shutdown();
		}
	}
}
