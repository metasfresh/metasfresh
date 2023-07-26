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

package de.metas.async.asyncbatchmilestone;

import ch.qos.logback.classic.Level;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
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
public class AsyncBatchMilestoneObserver
{
	private static final Logger logger = LogManager.getLogger(AsyncBatchMilestoneObserver.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final Map<AsyncBatchMilestoneId, CompletableFuture<Void>> asyncBatch2Completion = new ConcurrentHashMap<>();

	public void observeOn(@NonNull final AsyncBatchMilestoneId id)
	{
		asyncBatch2Completion.put(id, new CompletableFuture<>());
	}

	public void notifyMilestoneProcessedFor(@NonNull final AsyncBatchMilestoneId id, final boolean successful)
	{
		if (asyncBatch2Completion.get(id) == null)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("No observer registered to notify for asyncBatchId: " + id);
			return;
		}

		if (successful)
		{
			logger.debug("asyncBatchMilestoneId={} completed successfully", id);
			asyncBatch2Completion.get(id).complete(null);
		}
		else
		{
			asyncBatch2Completion.get(id).completeExceptionally(new AdempiereException("Workpackage completed exceptionally")
																		.appendParametersToMessage()
																		.setParameter("asyncBatchMilestoneId", id));
		}
	}

	/**
	 * Waits max. the timeout specified in {@code ÀD_SysConfig de.metas.async.AsyncBatchMilestoneObserver.WaitTimeOutMS}
	 * for the given AsyncBatchMilestoneId to be completed.
	 */
	public void waitToBeProcessed(@NonNull final AsyncBatchMilestoneId id)
	{
		if (asyncBatch2Completion.get(id) == null)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("No observer registered to be processed for asyncBatchId: " + id);
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
			final AsyncBatchId asyncBatchId = asyncBatchDAO.retrieveAsyncBatchIdByMilestone(id);

			final I_C_Async_Batch asyncBatch = asyncBatchDAO.retrieveAsyncBatchRecord(asyncBatchId);

			final List<I_C_Queue_WorkPackage> workPackages = asyncBatchDAO.retrieveWorkPackages(asyncBatch, null);

			if (workPackages.isEmpty())
			{
				Loggables.withLogger(logger, Level.WARN).addLog("No workpackages retrieved for asyncBatchId" + asyncBatchId);
				return;
			}

			throw AdempiereException.wrapIfNeeded(timeoutException);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("asyncBatchMilestoneId", id);
		}
	}

	public void removeObserver(@NonNull final AsyncBatchMilestoneId id)
	{
		if (asyncBatch2Completion.get(id) == null)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("No observer registered that can be removed for asyncBatchId: " + id);
			return;
		}

		asyncBatch2Completion.remove(id);
	}
}
