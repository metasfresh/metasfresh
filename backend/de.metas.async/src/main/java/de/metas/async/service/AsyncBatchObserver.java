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
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.eventbus.AsyncBatchNotifyRequest;
import de.metas.async.eventbus.AsyncBatchNotifyRequestHandler;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static de.metas.async.Async_Constants.SYS_Config_WaitTimeOutMS;
import static de.metas.async.Async_Constants.SYS_Config_WaitTimeOutMS_DEFAULT_VALUE;

@Service
public class AsyncBatchObserver implements AsyncBatchNotifyRequestHandler
{
	private static final Logger logger = LogManager.getLogger(AsyncBatchObserver.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final Map<AsyncBatchId, CompletableFuture<Void>> asyncBatch2Completion = new ConcurrentHashMap<>();

	@Override
	public void handleRequest(@NonNull final AsyncBatchNotifyRequest request)
	{
		Loggables.withLogger(logger, Level.INFO).addLog("Batch notified as finished; AsyncBatchId: {}", request.getAsyncBatchId());

		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(request.getAsyncBatchId());

		this.notifyBatchProcessedFor(asyncBatchId, request.getSuccess());
	}

	public void observeOn(@NonNull final AsyncBatchId id)
	{
		Loggables.withLogger(logger, Level.INFO).addLog("Observer registered for asyncBatchId: " + id.getRepoId());
		asyncBatch2Completion.put(id, new CompletableFuture<>());
	}

	/**
	 * Waits max. the timeout specified in {@code AD_SysConfig de.metas.async.AsyncBatchObserver.WaitTimeOutMS}
	 * for the given AsyncBatchId to be completed.
	 */
	public void waitToBeProcessed(@NonNull final AsyncBatchId id)
	{
		if (asyncBatch2Completion.get(id) == null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("No observer registered to be processed for asyncBatchId: {}", id.getRepoId());
			return;
		}

		try
		{
			final CompletableFuture<Void> completableFuture = asyncBatch2Completion.get(id);

			final int timeoutMS = sysConfigBL.getIntValue(SYS_Config_WaitTimeOutMS, SYS_Config_WaitTimeOutMS_DEFAULT_VALUE);

			completableFuture.get(timeoutMS, TimeUnit.MILLISECONDS);

			removeObserver(id);
		}
		catch (final TimeoutException timeoutException)
		{
			final I_C_Async_Batch asyncBatch = asyncBatchDAO.retrieveAsyncBatchRecord(id);

			final List<I_C_Queue_WorkPackage> workPackages = asyncBatchDAO.retrieveWorkPackages(asyncBatch, ITrx.TRXNAME_None);

			if (workPackages.isEmpty())
			{
				Loggables.withLogger(logger, Level.INFO).addLog("No workpackages retrieved for asyncBatchId: {}", id.getRepoId());
				return;
			}

			throw AdempiereException.wrapIfNeeded(timeoutException);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("AsyncBatchId", id);
		}
	}

	public boolean isAsyncBatchObserved(@NonNull final AsyncBatchId id)
	{
		return asyncBatch2Completion.get(id) != null;
	}

	private void removeObserver(@NonNull final AsyncBatchId id)
	{
		if (asyncBatch2Completion.get(id) == null)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("No observer registered that can be removed for asyncBatchId: {}", id.getRepoId());
			return;
		}

		asyncBatch2Completion.remove(id);

		Loggables.withLogger(logger, Level.INFO).addLog("Observer removed for asyncBatchId: {}", id.getRepoId());
	}

	private void notifyBatchProcessedFor(@NonNull final AsyncBatchId id, final boolean successful)
	{
		if (asyncBatch2Completion.get(id) == null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("No observer registered to notify for asyncBatchId: {}" , id.getRepoId());
			return;
		}

		if (successful)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("AsyncBatchId={} completed successfully. " , id.getRepoId());
			asyncBatch2Completion.get(id).complete(null);
		}
		else
		{
			asyncBatch2Completion.get(id).completeExceptionally(new AdempiereException("A Workpackage completed with an exception")
																		.appendParametersToMessage()
																		.setParameter("AsyncBatchId" , id.getRepoId()));
		}
	}
}
