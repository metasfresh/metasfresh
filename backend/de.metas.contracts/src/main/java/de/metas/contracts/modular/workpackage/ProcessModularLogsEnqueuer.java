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
import de.metas.JsonObjectMapperHolder;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusService;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static de.metas.contracts.modular.workpackage.ModularLogsWorkPackageProcessor.Params.LOG_ENTRY_CONTRACT_TYPE;
import static de.metas.contracts.modular.workpackage.ModularLogsWorkPackageProcessor.Params.MODEL_ACTION;
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

	public void enqueueAfterCommit(
			@NonNull final TableRecordReference recordReference,
			@NonNull final ModelAction action,
			@NonNull final LogEntryContractType logEntryContractType)
	{
		trxManager.accumulateAndProcessAfterCommit(
				ProcessModularLogsEnqueuer.class.getSimpleName(),
				ImmutableList.of(
						EnqueueRequest.builder()
								.recordReference(recordReference)
								.action(action)
								.logEntryContractType(logEntryContractType)
								.userInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
								.build()
				),
				this::enqueueAllNow);
	}

	private void enqueueAllNow(@NonNull final List<EnqueueRequest> requests)
	{
		//dev-note: a new transaction is needed as the work packages are enqueued after the next commit
		// and we are already passed that point in the current trx
		trxManager.runInNewTrx(() -> enqueueAllInNewTrx(requests));
	}

	private void enqueueAllInNewTrx(@NonNull final List<EnqueueRequest> requests)
	{
		final TableRecordReferenceSet referenceSet = getTableRecordReferenceSet(requests);
		final ModelAction action = getSingleModelAction(requests);
		final ContractTypeParameter  logEntryContractTypeMap = buildContractTypeMap(requests);
		final UserId userInChargeId = requests.get(0).userInChargeId();

		final IWorkPackageQueue workPackageQueue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), ModularLogsWorkPackageProcessor.class);

		final I_C_Queue_WorkPackage workPackage = workPackageQueue.newWorkPackage()
				//ensures we are only enqueueing after this trx is committed
				.bindToThreadInheritedTrx()
				.setUserInChargeId(userInChargeId)
				.parameter(MODEL_ACTION.name(), action.name())
				.parameter(LOG_ENTRY_CONTRACT_TYPE.name(), JsonObjectMapperHolder.toJsonNonNull(logEntryContractTypeMap))
				.setElementsLocker(createElementsLocker(referenceSet))
				.addElements(referenceSet.getRecordRefs())
				.buildAndEnqueue();

		referenceSet.streamReferences()
				.forEach(recordReference -> createStatusService
						.setStatusEnqueued(QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID()), recordReference));
	}

	@NonNull
	private ILockCommand createElementsLocker(@NonNull final TableRecordReferenceSet recordReferenceSet)
	{
		return lockManager.lock()
				.setOwner(LockOwner.newOwner(ProcessModularLogsEnqueuer.class.getSimpleName() + "_" + Instant.now()))
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.addRecordsByModel(recordReferenceSet.getRecordRefs());
	}

	@NonNull
	private static ContractTypeParameter buildContractTypeMap(@NonNull final List<EnqueueRequest> requests)
	{
		final Map<Integer, List<LogEntryContractType>> logEntryContractTypeMap = requests.stream()
				.collect(Collectors.groupingBy(
						request -> request.recordReference().getRecord_ID(),
						Collectors.mapping(EnqueueRequest::logEntryContractType, Collectors.toList())
				));

		return ContractTypeParameter.builder()
				.recordId2ContractType(logEntryContractTypeMap)
				.build();
	}

	@NonNull
	private static ModelAction getSingleModelAction(@NonNull final List<EnqueueRequest> requests)
	{
		final Set<ModelAction> actions = requests.stream()
				.map(EnqueueRequest::action)
				.collect(Collectors.toSet());

		return CollectionUtils.singleElement(actions);
	}

	@NonNull
	private static TableRecordReferenceSet getTableRecordReferenceSet(@NonNull final List<EnqueueRequest> requests)
	{
		final TableRecordReferenceSet referenceSet = requests.stream()
				.map(EnqueueRequest::recordReference)
				.collect(TableRecordReferenceSet.collect());

		referenceSet.assertSingleTableName();

		return referenceSet;
	}

	@Builder
	private record EnqueueRequest(
			@NonNull TableRecordReference recordReference,
			@NonNull ModelAction action,
			@NonNull LogEntryContractType logEntryContractType,
			@Nullable UserId userInChargeId)
	{
	}
}
