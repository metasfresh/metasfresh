/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.async;

import com.google.common.collect.ImmutableList;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IWorkPackageBlockBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class PPOrderCandidateEnqueuer
{
	public static final String WP_PINSTANCE_ID_PARAM = "pInstanceId";

	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	@NonNull
	public Result enqueueCandidateIds(@NonNull final ImmutableList<PPOrderCandidateId> candidateIds)
	{
		final PInstanceId pInstanceId = queryBL.createQueryBuilder(I_PP_Order_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID, candidateIds)
				.create()
				.createSelection();

		return enqueueSelection(pInstanceId, Env.getCtx());
	}

	@NonNull
	public Result enqueueSelection(
			@NonNull final PInstanceId adPInstanceId,
			@NonNull final Properties ctx)
	{
		final LockOwner lockOwner = LockOwner.newOwner(PPOrderCandidateEnqueuer.class.getSimpleName(), adPInstanceId.getRepoId());

		final ILockCommand elementsLocker = lockManager
				.lock()
				.setOwner(lockOwner)
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.setRecordsBySelection(I_PP_Order_Candidate.class, adPInstanceId);

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(ctx, GeneratePPOrderFromPPOrderCandidate.class);

		final IWorkPackageBlockBuilder blockBuilder = queue
				.newBlock();

		final I_C_Queue_WorkPackage workPackage = blockBuilder
				.newWorkpackage()
				.parameter(WP_PINSTANCE_ID_PARAM, adPInstanceId)
				.setElementsLocker(elementsLocker)
				.build();

		final Result result = new Result();
		result.addEnqueuedWorkPackageId(QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID()));

		return result;
	}

	public static class Result
	{
		private final List<QueueWorkPackageId> enqueuedWorkpackageIds = new ArrayList<>();

		public int getEnqueuedPackagesCount()
		{
			return enqueuedWorkpackageIds.size();
		}

		public ImmutableList<QueueWorkPackageId> getEnqueuedPackageIds()
		{
			return ImmutableList.copyOf(enqueuedWorkpackageIds);
		}

		private void addEnqueuedWorkPackageId(@NonNull final QueueWorkPackageId workPackageId)
		{
			enqueuedWorkpackageIds.add(workPackageId);
		}
	}
}