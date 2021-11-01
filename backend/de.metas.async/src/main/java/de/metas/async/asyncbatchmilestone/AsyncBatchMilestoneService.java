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
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.asyncbatchmilestone.eventbus.AsyncMilestoneEventBusService;
import de.metas.async.asyncbatchmilestone.eventbus.AsyncMilestoneNotifyRequest;
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
public class AsyncBatchMilestoneService
{
	private static final Logger logger = LogManager.getLogger(AsyncBatchMilestoneService.class);

	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final AsyncBatchMilestoneObserver asyncBatchMilestoneObserver;
	private final AsyncBatchMilestoneRepo asyncBatchMilestoneRepo;
	private final AsyncMilestoneEventBusService asyncMilestoneEventBusService;

	public AsyncBatchMilestoneService(
			@NonNull final AsyncBatchMilestoneObserver asyncBatchMilestoneObserver,
			@NonNull final AsyncBatchMilestoneRepo asyncBatchMilestoneRepo,
			@NonNull final AsyncMilestoneEventBusService asyncMilestoneEventBusService)
	{
		this.asyncBatchMilestoneObserver = asyncBatchMilestoneObserver;
		this.asyncBatchMilestoneRepo = asyncBatchMilestoneRepo;
		this.asyncMilestoneEventBusService = asyncMilestoneEventBusService;
	}

	@NonNull
	public List<AsyncBatchMilestone> getByQuery(@NonNull final AsyncBatchMilestoneQuery query)
	{
		return asyncBatchMilestoneRepo.getByQuery(query);
	}

	@NonNull
	public AsyncBatchMilestone saveInNewTrx(@NonNull final AsyncBatchMilestone asyncBatchMilestone)
	{
		return trxManager.callInNewTrx(() -> asyncBatchMilestoneRepo.save(asyncBatchMilestone));
	}

	public void processAsyncBatchMilestone(
			@NonNull final AsyncBatchId asyncBatchId,
			@NonNull final List<AsyncBatchMilestone> milestones,
			@Nullable final String trxName)
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

		Loggables.withLogger(logger, Level.INFO).addLog("*** processAsyncBatchMilestone for: asyncBatchID: " + asyncBatch.getC_Async_Batch_ID() +
																" allWPSize: " + workPackages.size() +
																" processedWPSize: " + workPackagesProcessedCount +
																" erroredWPSize: " + workPackagesWithErrorCount +
																" milestoneIdsToProcess:" + milestones.stream().map(AsyncBatchMilestone::getIdNotNull).collect(ImmutableSet.toImmutableSet()));

		if (workPackagesFinalized >= workPackages.size())
		{
			for (final AsyncBatchMilestone milestone : milestones)
			{
				final AsyncBatchMilestone finalizedAsyncBatchMilestone = milestone.toBuilder().processed(true).build();

				saveInNewTrx(finalizedAsyncBatchMilestone);

				final AsyncMilestoneNotifyRequest asyncMilestoneRequest = AsyncMilestoneNotifyRequest.builder()
						.clientId(Env.getClientId())
						.milestoneId(milestone.getIdNotNull().getRepoId())
						.asyncBatchId(milestone.getIdNotNull().getAsyncBatchId().getRepoId())
						.success(workPackagesWithErrorCount <= 0)
						.build();

				asyncMilestoneEventBusService.postRequest(asyncMilestoneRequest);
			}
		}
	}

	/**
	 * Enqueues and waits for the workpackages to finish, successfully or exceptionally.
	 * It's mandatory for the given Supplier<> to enqueue workpackages previously assigned to the given async batch.
	 *
	 * @param supplier      Supplier<>
	 * @param asyncBatchId  C_Async_Batch_ID
	 * @param milestoneName C_Async_Batch_Milestone.Name
	 * @param <T>           model type
	 * @return model type of supplier
	 * @see C_Queue_WorkPackage#processMilestoneFromWP(de.metas.async.model.I_C_Queue_WorkPackage)
	 */
	public <T> T executeMilestone(
			@NonNull final Supplier<T> supplier,
			@NonNull final AsyncBatchId asyncBatchId,
			@NonNull final MilestoneName milestoneName)
	{
		final AsyncBatchMilestone asyncBatchMilestone = AsyncBatchMilestone.builder()
				.asyncBatchId(asyncBatchId)
				.orgId(Env.getOrgId())
				.milestoneName(milestoneName)
				.build();

		final AsyncBatchMilestoneId milestoneId = saveInNewTrx(asyncBatchMilestone).getIdNotNull();

		asyncBatchMilestoneObserver.observeOn(milestoneId);

		final T result = supplier.get();

		asyncBatchMilestoneObserver.waitToBeProcessed(milestoneId);

		return result;
	}
}
