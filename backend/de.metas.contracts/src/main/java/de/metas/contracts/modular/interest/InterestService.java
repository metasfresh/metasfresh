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

import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.interest.run.InterestRunRepository;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.currency.ICurrencyBL;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.money.MoneyService;
import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static de.metas.contracts.modular.ComputingMethodType.INTEREST_SPECIFIC_METHODS;

@Service
@RequiredArgsConstructor
public class InterestService
{
	@NonNull private final ModularLogInterestRepository interestRepository;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final MoneyService moneyService;
	@NonNull private final ModularContractService modularContractService;
	@NonNull private final InterestRunRepository interestRunRepository;
	@NonNull private final InterestComputationNotificationsProducer interestComputationNotificationsProducer;

	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);

	public void distributeInterestAndBonus(@NonNull final EnqueueInterestComputationRequest enqueueRequest)
	{
		final ILock lock = lockModularContractLogsForInvoicingGroup(enqueueRequest.getInvoicingGroupId());
		try
		{
			distributeInterestAndBonus(
					InterestBonusComputationRequest.builder()
							.interestToDistribute(enqueueRequest.getInterestToDistribute())
							.billingDate(enqueueRequest.getBillingDate())
							.interimDate(enqueueRequest.getInterimDate())
							.invoicingGroupId(enqueueRequest.getInvoicingGroupId())
							.involvedModularLogsLock(lock)
							.userId(enqueueRequest.getUserId())
							.build()
			);
		}
		finally
		{
			lock.unlockAll();
		}
	}

	private void distributeInterestAndBonus(@NonNull final InterestBonusComputationRequest request)
	{
		InterestComputationCommand.builder()
				.modularContractService(modularContractService)
				.interestRepository(interestRepository)
				.interestRunRepository(interestRunRepository)
				.moneyService(moneyService)
				.modularContractLogService(modularContractLogService)
				.notificationsProducer(interestComputationNotificationsProducer)
				.currencyBL(currencyBL)
				.orgDAO(orgDAO)
				.queryBL(queryBL)
				.build()
				.distributeInterestAndBonus(request);
	}

	private ILock lockModularContractLogsForInvoicingGroup(@NonNull final InvoicingGroupId invoicingGroupId)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.computingMethodTypes(INTEREST_SPECIFIC_METHODS)
				.processed(false)
				.billable(true)
				.invoicingGroupId(invoicingGroupId)
				.build();

		return lockManager.lock()
				.setOwner(LockOwner.newOwner(InterestComputationWorkPackageProcessor.class.getSimpleName() + "_" + Instant.now()))
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.setSetRecordsByFilter(I_ModCntr_Log.class, modularContractLogService.getModularContractLogEntryFilter(query))
				.acquire();
	}
}
