/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.workpackage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusService;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static de.metas.contracts.modular.workpackage.ModularLogsWorkPackageProcessor.Params.REQUESTS_TO_PROCESS;
import static org.compiere.util.Env.getCtx;

@Service
@RequiredArgsConstructor
public class ProcessModularLogsEnqueuer
{
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull
	private final ModularLogCreateStatusService createStatusService;

	public void enqueueAfterCommit(@NonNull final ProcessModularLogsEnqueuer.EnqueueRequest request)
	{
		trxManager.accumulateAndProcessAfterCommit(
				ProcessModularLogsEnqueuer.class.getSimpleName(),
				ImmutableList.of(request),
				this::enqueueAllNow);
	}

	private void enqueueAllNow(@NonNull final List<EnqueueRequest> requests)
	{
		//dev-note: a new transaction is needed as the work packages are enqueued after the next commit,
		// and we are already passed that point in the current trx
		trxManager.runInNewTrx(() -> enqueueAllInNewTrx(requests));
	}

	private void enqueueAllInNewTrx(@NonNull final List<EnqueueRequest> requests)
	{
		final IWorkPackageQueue workPackageQueue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), ModularLogsWorkPackageProcessor.class);
		final UserId userIdInCharge = requests.stream()
				.map(EnqueueRequest::userInChargeId)
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);

		final ImmutableSet<TableRecordReference> recordReferences = requests.stream()
				.map(EnqueueRequest::recordReference)
				.collect(ImmutableSet.toImmutableSet());

		final I_C_Queue_WorkPackage workPackage = workPackageQueue.newWorkPackage()
				//ensures we are only enqueueing after this trx is committed
				.bindToThreadInheritedTrx()
				.setUserInChargeId(userIdInCharge)
				.parameter(REQUESTS_TO_PROCESS.name(), JsonObjectMapperHolder.toJsonNonNull(buildProcessModularLogAggRequest(requests)))
				.setElementsLocker(createElementsLocker(recordReferences))
				.addElements(recordReferences)
				.buildAndEnqueue();

		recordReferences.forEach(recordReference -> createStatusService
				.setStatusEnqueued(QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID()), recordReference));
	}

	@NonNull
	private ILockCommand createElementsLocker(@NonNull final ImmutableSet<TableRecordReference> recordReferenceSet)
	{
		return lockManager.lock()
				.setOwner(LockOwner.newOwner(ProcessModularLogsEnqueuer.class.getSimpleName() + "_" + Instant.now()))
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.addRecordsByModel(recordReferenceSet);
	}

	@Builder
	public record EnqueueRequest(
			@NonNull TableRecordReference recordReference,
			@NonNull ModelAction action,
			@NonNull LogEntryContractType logEntryContractType,
			@NonNull FlatrateTermId flatrateTermId,
			@NonNull ComputingMethodType computingMethodType,
			@Nullable UserId userInChargeId)
	{
	}

	@NonNull
	private static ProcessModularLogRequestList buildProcessModularLogAggRequest(@NonNull final List<EnqueueRequest> enqueueRequestList)
	{
		return ProcessModularLogRequestList.builder()
				.requests(enqueueRequestList.stream()
						.map(request -> ProcessModularLogRequest.builder()
								.logEntryContractType(request.logEntryContractType())
								.flatrateTermId(request.flatrateTermId())
								.computingMethodType(request.computingMethodType())
								.recordReference(request.recordReference())
								.action(request.action())
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}