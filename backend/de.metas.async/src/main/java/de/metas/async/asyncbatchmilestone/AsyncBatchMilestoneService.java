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

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.validator.C_Queue_WorkPackage;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@Service
public class AsyncBatchMilestoneService
{
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	private final AsyncBatchMilestoneObserver asyncBatchMilestoneObserver;
	private final AsyncBatchMilestoneRepo asyncBatchMilestoneRepo;

	public AsyncBatchMilestoneService(
			@NonNull final AsyncBatchMilestoneObserver asyncBatchMilestoneObserver,
			@NonNull final AsyncBatchMilestoneRepo asyncBatchMilestoneRepo)
	{
		this.asyncBatchMilestoneObserver = asyncBatchMilestoneObserver;
		this.asyncBatchMilestoneRepo = asyncBatchMilestoneRepo;
	}

	@NonNull
	public List<AsyncBatchMilestone> getByQuery(@NonNull final AsyncBatchMilestoneQuery query)
	{
		return asyncBatchMilestoneRepo.getByQuery(query);
	}

	@NonNull
	public AsyncBatchMilestone save(@NonNull final AsyncBatchMilestone asyncBatchMilestone)
	{
		return asyncBatchMilestoneRepo.save(asyncBatchMilestone);
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

		if (workPackagesFinalized >= workPackages.size())
		{
			for (final AsyncBatchMilestone milestone : milestones)
			{
				final AsyncBatchMilestone finalizedAsyncBatchMilestone = milestone.toBuilder().processed(true).build();

				save(finalizedAsyncBatchMilestone);

				asyncBatchMilestoneObserver.notifyMilestoneProcessedFor(milestone.getIdNotNull(), workPackagesWithErrorCount <= 0);
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

		final AsyncBatchMilestoneId milestoneId = save(asyncBatchMilestone).getIdNotNull();

		asyncBatchMilestoneObserver.observeOn(milestoneId);

		final T result = supplier.get();

		asyncBatchMilestoneObserver.waitToBeProcessed(milestoneId);

		return result;
	}
}
