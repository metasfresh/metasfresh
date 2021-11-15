/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.async.service;

import ch.qos.logback.classic.Level;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.eventbus.AsyncBatchEventBusService;
import de.metas.async.eventbus.AsyncBatchNotifyRequest;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.validator.C_Queue_WorkPackage;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@Service
public class AsyncBatchService
{
	private static final Logger logger = LogManager.getLogger(AsyncBatchService.class);

	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final AsyncBatchObserver asyncBatchObserver;
	private final AsyncBatchEventBusService asyncBatchEventBusService;

	public AsyncBatchService(
			@NonNull final AsyncBatchObserver asyncBatchObserver,
			@NonNull final AsyncBatchEventBusService asyncBatchEventBusService)
	{
		this.asyncBatchObserver = asyncBatchObserver;
		this.asyncBatchEventBusService = asyncBatchEventBusService;
	}

	public void checkProcessed(@NonNull final AsyncBatchId asyncBatchId, @Nullable final String trxName)
	{
		final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchById(asyncBatchId);

		final List<I_C_Queue_WorkPackage> workPackages = asyncBatchDAO.retrieveWorkPackages(asyncBatch, trxName);

		final int workPackagesProcessedCount = (int)workPackages.stream()
				.filter(I_C_Queue_WorkPackage::isProcessed)
				.count();

		final int workPackagesWithErrorCount = (int)workPackages.stream()
				.filter(I_C_Queue_WorkPackage::isError)
				.count();

		final int workPackagesFinalized = workPackagesProcessedCount + workPackagesWithErrorCount;

		Loggables.withLogger(logger, Level.INFO).addLog("*** processAsyncBatch for: asyncBatchID: " + asyncBatch.getC_Async_Batch_ID() +
																" allWPSize: " + workPackages.size() +
																" processedWPSize: " + workPackagesProcessedCount +
																" erroredWPSize: " + workPackagesWithErrorCount);

		if (workPackagesFinalized >= workPackages.size())
		{
			final AsyncBatchNotifyRequest request = AsyncBatchNotifyRequest.builder()
					.clientId(Env.getClientId())
					.asyncBatchId(AsyncBatchId.toRepoId(asyncBatchId))
					.success(workPackagesWithErrorCount <= 0)
					.build();

			asyncBatchEventBusService.postRequest(request);
		}
	}

	/**
	 * Enqueues and waits for the workpackages to finish, successfully or exceptionally.
	 * It's mandatory for the given Supplier<> to enqueue workpackages previously assigned to the given async batch.
	 *
	 * @param supplier     Supplier<>
	 * @param asyncBatchId C_Async_Batch_ID
	 * @param <T>          model type
	 * @return model type of supplier
	 * @see C_Queue_WorkPackage#processBatchFromWP(de.metas.async.model.I_C_Queue_WorkPackage)
	 */
	public <T> T executeBatch(@NonNull final Supplier<T> supplier, @NonNull final AsyncBatchId asyncBatchId)
	{
		asyncBatchObserver.observeOn(asyncBatchId);

		final T result = trxManager.callInNewTrx(supplier::get);

		asyncBatchObserver.waitToBeProcessed(asyncBatchId);

		return result;
	}
}
