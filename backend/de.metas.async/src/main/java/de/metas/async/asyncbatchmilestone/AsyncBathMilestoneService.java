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
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class AsyncBathMilestoneService
{
	private static final Logger logger = LogManager.getLogger(AsyncBathMilestoneService.class);

	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	private final AsyncBatchMilestoneObserver asyncBatchMilestoneObserver;
	private final AsyncBatchMilestoneRepo asyncBatchMilestoneRepo;

	public AsyncBathMilestoneService(
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

				save(finalizedAsyncBatchMilestone);

				asyncBatchMilestoneObserver.notifyMilestoneProcessedFor(milestone.getIdNotNull(), workPackagesWithErrorCount <= 0);
			}
		}
	}
}
