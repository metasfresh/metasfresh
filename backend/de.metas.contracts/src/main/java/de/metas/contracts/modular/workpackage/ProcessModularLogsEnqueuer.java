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
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.IModularContractTypeHandler;
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
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static de.metas.contracts.modular.workpackage.ModularLogsWorkPackageProcessor.Params.CONTRACT_INFO_LIST;
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
			@NonNull final IModularContractTypeHandler<?> handler,
			@NonNull final TableRecordReference recordReference,
			@NonNull final ModelAction action,
			@NonNull final FlatrateTermId flatrateTermId,
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
								.flatrateTermId(flatrateTermId)
								.handlerClassname(handler.getClass().getName())
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
		aggregateRequests(requests).forEach(this::enqueueRequest);
	}

	private void enqueueRequest(@NonNull final AggregatedEnqueueRequest request)
	{
		final IWorkPackageQueue workPackageQueue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), ModularLogsWorkPackageProcessor.class);

		final I_C_Queue_WorkPackage workPackage = workPackageQueue.newWorkPackage()
				//ensures we are only enqueueing after this trx is committed
				.bindToThreadInheritedTrx()
				.setUserInChargeId(request.userInChargeId())
				.parameter(MODEL_ACTION.name(), request.action().name())
				.parameter(CONTRACT_INFO_LIST.name(), JsonObjectMapperHolder.toJsonNonNull(request.contractInfoParameter()))
				.setElementsLocker(createElementsLocker(request.recordReferenceSet()))
				.addElements(request.recordReferenceSet().getRecordRefs())
				.buildAndEnqueue();

		request.recordReferenceSet()
				.streamReferences()
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
	private static ImmutableList<AggregatedEnqueueRequest> aggregateRequests(@NonNull final List<EnqueueRequest> enqueueRequests)
	{
		final List<AggregatedEnqueueRequest> aggregatedEnqueueRequests = new ArrayList<>();

		RequestAggregator requestAggregator = null;

		for (final EnqueueRequest enqueueRequest : enqueueRequests)
		{
			if (requestAggregator == null)
			{
				requestAggregator = RequestAggregator.init(enqueueRequest);
			}
			else
			{
				final boolean isAggregated = requestAggregator.aggregate(enqueueRequest);

				if (!isAggregated)
				{
					aggregatedEnqueueRequests.add(requestAggregator.build());
					requestAggregator = RequestAggregator.init(enqueueRequest);
				}
			}
		}

		if (requestAggregator != null)
		{
			aggregatedEnqueueRequests.add(requestAggregator.build());
		}

		return ImmutableList.copyOf(aggregatedEnqueueRequests);
	}

	@Builder
	private record EnqueueRequest(
			@NonNull TableRecordReference recordReference,
			@NonNull ModelAction action,
			@NonNull LogEntryContractType logEntryContractType,
			@NonNull FlatrateTermId flatrateTermId,
			@NonNull String handlerClassname,
			@Nullable UserId userInChargeId)
	{
	}

	@Builder
	private record AggregatedEnqueueRequest(
			@NonNull TableRecordReferenceSet recordReferenceSet,
			@NonNull ModelAction action,
			@NonNull ContractInfoParameter contractInfoParameter,
			@Nullable UserId userInChargeId)
	{
	}

	@Value
	@Builder
	private static class RequestAggregator
	{
		@NonNull ArrayList<TableRecordReference> recordReferenceList;
		@NonNull HashMap<Integer, List<ContractInfoParameter.ContractProcessInfo>> recordId2ContractInfo;
		@NonNull ModelAction modelAction;
		@Nullable
		UserId userInChargeId;

		@NonNull
		static RequestAggregator init(@NonNull final EnqueueRequest enqueueRequest)
		{
			final ArrayList<ContractInfoParameter.ContractProcessInfo> contractInfos = new ArrayList<>();
			contractInfos.add(buildContractInfoParameter(enqueueRequest));

			return RequestAggregator.builder()
					.recordReferenceList(new ArrayList<>()
					{{
						add(enqueueRequest.recordReference());
					}})
					.recordId2ContractInfo(new HashMap<>()
					{{
						put(enqueueRequest.recordReference().getRecord_ID(), contractInfos);
					}})
					.modelAction(enqueueRequest.action())
					.userInChargeId(enqueueRequest.userInChargeId())
					.build();

		}

		boolean aggregate(@NonNull final EnqueueRequest enqueueRequest)
		{
			if (!enqueueRequest.recordReference().getTableName().equals(recordReferenceList.get(0).getTableName()))
			{
				return false;
			}

			if (enqueueRequest.action() != modelAction)
			{
				return false;
			}

			recordReferenceList.add(enqueueRequest.recordReference());
			final ArrayList<ContractInfoParameter.ContractProcessInfo> contractProcessInfos = new ArrayList<>();
			contractProcessInfos.add(buildContractInfoParameter(enqueueRequest));
			recordId2ContractInfo.merge(enqueueRequest.recordReference().getRecord_ID(), contractProcessInfos, (oldList, newList) -> {
				oldList.addAll(newList);
				return oldList;
			});

			return true;
		}

		@NonNull
		AggregatedEnqueueRequest build()
		{
			final TableRecordReferenceSet referenceSet = TableRecordReferenceSet.of(recordReferenceList);

			return AggregatedEnqueueRequest.builder()
					.action(modelAction)
					.recordReferenceSet(referenceSet)
					.contractInfoParameter(ContractInfoParameter.builder()
												  .recordId2ContractInfo(recordId2ContractInfo)
												  .build())
					.userInChargeId(userInChargeId)
					.build();
		}

		@NonNull
		private static ContractInfoParameter.ContractProcessInfo buildContractInfoParameter(@NonNull final EnqueueRequest enqueueRequest)
		{
			return ContractInfoParameter.ContractProcessInfo.builder()
					.contractType(enqueueRequest.logEntryContractType())
					.flatrateTermId(enqueueRequest.flatrateTermId())
					.handlerClassName(enqueueRequest.handlerClassname())
					.build();
		}
	}
}