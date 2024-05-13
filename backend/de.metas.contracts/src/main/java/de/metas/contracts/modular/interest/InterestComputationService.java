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

import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.money.Money;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InterestComputationService
{
	@NonNull private final ModularLogInterestRepository interestRepository;
	@NonNull private final ModularContractLogService modularContractLogService;

	public void computeInterest(@NonNull final InterestComputationRequest request)
	{
		final InterestComputationContext context = initContext(request);
		deletePreviousProgress(request);
		createInitialInterestRecords(request, context);
		computeFinalInterest(request, context);
		validateResults(context);
	}

	private void deletePreviousProgress(@NonNull final InterestComputationRequest request)
	{
		final ModularLogInterestRepository.LogInterestQuery query = ModularLogInterestRepository.LogInterestQuery.builder()
				.logSelection(request.getLogSelectionId())
				.build();

		interestRepository.deleteByQuery(query);
	}

	private void createInitialInterestRecords(
			@NonNull final InterestComputationRequest request,
			@NonNull final InterestComputationContext context)
	{
		streamShippingNotificationLogEntries(request)
				.forEach(shippingNotificationLog -> allocateShippingNotification(shippingNotificationLog, context));
	}

	private void computeFinalInterest(
			@NonNull final InterestComputationRequest request,
			@NonNull final InterestComputationContext context)
	{
		streamShippingNotificationLogEntries(request)
				.forEach(shippingNotificationLog -> computeFinalInterest(shippingNotificationLog, context));
	}

	@NonNull
	private Stream<ModularContractLogEntry> streamShippingNotificationLogEntries(@NonNull final InterestComputationRequest request)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.invoicingGroupId(request.getInvoicingGroupId())
				.billable(true)
				.processed(false)
				.lockOwner(request.getLockOwner())
				.tableName(I_M_Shipping_Notification.Table_Name)
				.build();

		return modularContractLogService.streamModularContractLogEntries(query);
	}

	private void allocateShippingNotification(@NonNull final ModularContractLogEntry shippingNotificationLog, @NonNull final InterestComputationContext context)
	{
		// todo
	}

	private void computeFinalInterest(@NonNull final ModularContractLogEntry shippingNotificationLog, @NonNull final InterestComputationContext context)
	{
		//todo
	}

	private InterestComputationContext initContext(@NonNull final InterestComputationRequest request)
	{
		return null; //todo
	}

	private void validateResults(InterestComputationContext context)
	{
		// todo
	}

	private static class InterestComputationContext
	{
		private int expectedLogCount;
		private int actualLogCount;
		private Money totalInterestScore;
	}
}
