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
import de.metas.contracts.modular.log.status.ModularLogCreateStatus;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusService;
import de.metas.contracts.modular.log.status.ProcessingStatus;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

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
		trxManager.runAfterCommit(() -> enqueue(recordReference, action, logEntryContractType));
	}

	private void enqueue(
			@NonNull final TableRecordReference recordReference,
			@NonNull final ModelAction action,
			@NonNull final LogEntryContractType logEntryContractType)
	{
		final IWorkPackageQueue workPackageQueue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), ModularLogsWorkPackageProcessor.class);

		final I_C_Queue_WorkPackage workPackage = workPackageQueue.newWorkPackage()
				.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
				.parameter(MODEL_ACTION.name(), action.name())
				.parameter(LOG_ENTRY_CONTRACT_TYPE.name(), logEntryContractType.getCode())
				.setElementsLocker(getLock(recordReference))
				.addElement(recordReference)
				.buildAndEnqueue();

		final ModularLogCreateStatus createStatus = ModularLogCreateStatus.builder()
				.workPackageId(QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID()))
				.recordReference(recordReference)
				.status(ProcessingStatus.ENQUEUED)
				.build();

		createStatusService.save(createStatus);
	}

	@NonNull
	private ILockCommand getLock(@NonNull final TableRecordReference recordReference)
	{
		final LockOwner lockOwner = LockOwner.newOwner(ProcessModularLogsEnqueuer.class.getSimpleName() + "_" + Instant.now());

		return lockManager
				.lock()
				.setOwner(lockOwner)
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.setRecordByRecordReference(recordReference);
	}
}
