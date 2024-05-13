/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.interest;

import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import static org.compiere.util.Env.getCtx;

@Service
@RequiredArgsConstructor
public class InterestComputationEnqueuer
{
	public static final String ENQUEUED_REQUEST_PARAM = "ENQUEUED_REQUEST_PARAM";

	@NonNull private final ModularContractLogService contractLogService;

	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);

	public void enqueueNow(
			@NonNull final EnqueueInterestComputationRequest request,
			@NonNull final UserId userId)
	{
		trxManager.runInThreadInheritedTrx(() -> enqueueInTrx(request, userId));
	}

	private void enqueueInTrx(
			@NonNull final EnqueueInterestComputationRequest request,
			@NonNull final UserId userId)
	{
		final TableRecordReference invGroupReference = TableRecordReference.of(I_ModCntr_InvoicingGroup.Table_Name, request.getInvoicingGroupId());

		workPackageQueueFactory.getQueueForEnqueuing(getCtx(), InterestComputationWorkPackageProcessor.class)
				.newWorkPackage()
				// ensures we are only enqueueing after this trx is committed
				.bindToThreadInheritedTrx()
				.parameters(getParameters(request))
				.addElement(invGroupReference)
				.setUserInChargeId(userId)
				.setElementsLocker(createElementsLocker(request.getInvoicingGroupId()))
				.buildAndEnqueue();
	}

	@NonNull
	private Map<String,String> getParameters(@NonNull final EnqueueInterestComputationRequest request)
	{
		final String serializedRequest = JsonObjectMapperHolder.toJson(request);
		return ImmutableMap.of(ENQUEUED_REQUEST_PARAM, Objects.requireNonNull(serializedRequest));
	}

	@NonNull
	private ILockCommand createElementsLocker(@NonNull final InvoicingGroupId invoicingGroupId)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.processed(false)
				.billable(true)
				.invoicingGroupId(invoicingGroupId)
				.build();

		final PInstanceId selectionId = contractLogService.getModularContractLogEntrySelection(query);

		if (selectionId == null)
		{
			throw new AdempiereException("Nothing to enqueue, no logs available for the selected invoicing group!")
					.markAsUserValidationError();
		}

		return lockManager.lock()
				.setOwner(LockOwner.newOwner(InterestComputationEnqueuer.class.getSimpleName() + "_" + Instant.now()))
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.setRecordsBySelection(I_ModCntr_Log.class, selectionId);
	}
}
