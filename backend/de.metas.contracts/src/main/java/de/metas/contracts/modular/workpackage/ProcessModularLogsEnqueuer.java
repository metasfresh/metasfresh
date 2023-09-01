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
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;

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
		final EnqueueRequest request = EnqueueRequest.builder()
				.recordReference(recordReference)
				.action(action)
				.logEntryContractType(logEntryContractType)
				.userInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
				.build();

		enqueueAfterCommit(request);
	}

	private void enqueueAfterCommit(@NonNull final EnqueueRequest request)
	{
		final TableRecordReference recordReference = request.recordReference();
		final ModelAction action = request.action();
		final LogEntryContractType logEntryContractType = request.logEntryContractType();
		final UserId userInChargeId = request.userInChargeId();

		final IWorkPackageQueue workPackageQueue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), ModularLogsWorkPackageProcessor.class);

		final I_C_Queue_WorkPackage workPackage = workPackageQueue.newWorkPackage()
				//ensures we are only enqueueing after this trx is committed
				.bindToThreadInheritedTrx()
				.setUserInChargeId(userInChargeId)
				.parameter(MODEL_ACTION.name(), action.name())
				.parameter(LOG_ENTRY_CONTRACT_TYPE.name(), logEntryContractType.getCode())
				.setElementsLocker(createElementsLocker(recordReference))
				.addElement(recordReference)
				.buildAndEnqueue();

		createStatusService
				.setStatusEnqueued(QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID()), recordReference);
	}

	@NonNull
	private ILockCommand createElementsLocker(@NonNull final TableRecordReference recordReference)
	{
		return lockManager.lock()
				.setOwner(LockOwner.newOwner(ProcessModularLogsEnqueuer.class.getSimpleName() + "_" + Instant.now()))
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.setRecordByRecordReference(recordReference);
	}

	@Builder
	private record EnqueueRequest(
			@NonNull TableRecordReference recordReference,
			@NonNull ModelAction action,
			@NonNull LogEntryContractType logEntryContractType,
			@Nullable UserId userInChargeId) {}
}
