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

import de.metas.JsonObjectMapperHolder;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class InterestComputationWorkPackageProcessor extends WorkpackageProcessorAdapter
{
	private final InterestComputationService interestComputationService = SpringContextHolder.instance.getBean(InterestComputationService.class);
	private final ModularContractLogService modularContractLogService = SpringContextHolder.instance.getBean(ModularContractLogService.class);

	private final ILockManager lockManager = Services.get(ILockManager.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
	{
		final InterestBonusComputationRequest computationRequest = getRequest();
		try
		{
			interestComputationService.distributeInterestAndBonus(computationRequest);
		}
		finally
		{
			computationRequest.getInvolvedModularLogsLock().unlockAll();
		}

		return Result.SUCCESS;
	}

	@NonNull
	private InterestBonusComputationRequest getRequest()
	{
		final EnqueueInterestComputationRequest enqueueRequest = Optional.of(getParameters())
				.map(params -> params.getParameterAsString(InterestComputationEnqueuer.ENQUEUED_REQUEST_PARAM))
				.map(serializedRequest -> JsonObjectMapperHolder.fromJson(serializedRequest, EnqueueInterestComputationRequest.class))
				.orElseThrow(() -> new AdempiereException("Missing mandatory ENQUEUED_REQUEST_PARAM!"));

		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.computingMethodType(ComputingMethodType.AddValueOnInterim)
				.computingMethodType(ComputingMethodType.SubtractValueOnInterim)
				.processed(false)
				.billable(true)
				.invoicingGroupId(enqueueRequest.getInvoicingGroupId())
				.build();

		final PInstanceId selectionId = modularContractLogService.getModularContractLogEntrySelection(query);
		if (selectionId == null)
		{
			throw new AdempiereException("Nothing to process, no logs available for the selected invoicing group!")
					.markAsUserValidationError();
		}

		final ILock lock = lockManager.lock()
				.setOwner(LockOwner.newOwner(InterestComputationEnqueuer.class.getSimpleName() + "_" + Instant.now()))
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.setRecordsBySelection(I_ModCntr_Log.class, selectionId)
				.acquire();

		return InterestBonusComputationRequest.builder()
				.interestToDistribute(enqueueRequest.getInterestToDistribute())
				.billingDate(enqueueRequest.getBillingDate())
				.interimDate(enqueueRequest.getInterimDate())
				.invoicingGroupId(enqueueRequest.getInvoicingGroupId())
				.involvedModularLogsLock(lock)
				.build();
	}
}
