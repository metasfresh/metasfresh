/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.contracts.commission.commissioninstance.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocument;
import de.metas.logging.LogManager;
import lombok.NonNull;

@Service
public class CommissionInstanceService
{
	private static final Logger logger = LogManager.getLogger(CommissionInstanceService.class);

	private final CommissionInstanceRequestFactory commissionInstanceRequestFactory;

	private final CommissionAlgorithmInvoker commissionAlgorithmInvoker;

	public CommissionInstanceService(
			@NonNull final CommissionInstanceRequestFactory commissionInstanceRequestFactory,
			@NonNull final CommissionAlgorithmInvoker commissionAlgorithmInvoker)
	{
		this.commissionInstanceRequestFactory = commissionInstanceRequestFactory;
		this.commissionAlgorithmInvoker = commissionAlgorithmInvoker;
	}

	public Optional<CommissionInstance> createCommissionInstance(@NonNull final CommissionTriggerDocument commissionTriggerDocument)
	{
		// request might be not present, if there are no matching contracts and/or settings
		final Optional<CreateCommissionSharesRequest> request = commissionInstanceRequestFactory.createRequestFor(commissionTriggerDocument);

		if (request.isPresent())
		{
			final CommissionInstance result = CommissionInstance
					.builder()
					.currentTriggerData(request.get().getTrigger().getCommissionTriggerData())
					.shares(commissionAlgorithmInvoker.createCommissionShares(request.get()))
					.build();

			return Optional.of(result);
		}
		return Optional.empty();
	}

	public void createAndAddMissingShares(
			@NonNull final CommissionInstance instance,
			@NonNull final CommissionTriggerDocument commissionTriggerDocument)
	{
		final Optional<CreateCommissionSharesRequest> request = commissionInstanceRequestFactory.createRequestFor(commissionTriggerDocument);
		if (!request.isPresent())
		{
			return;
		}

		final ImmutableSet<CommissionConfig> existingConfigs = instance.getShares()
				.stream()
				.map(SalesCommissionShare::getConfig)
				.collect(ImmutableSet.toImmutableSet());

		final CreateCommissionSharesRequest sparsedOutRequest = request.get().withoutConfigs(existingConfigs);
		if (sparsedOutRequest.getConfigs().isEmpty())
		{
			logger.debug("There are no CommissionConfigs that were not already applied to the commision instance");
			return;
		}

		final ImmutableList<SalesCommissionShare> additionalShares = commissionAlgorithmInvoker.createCommissionShares(sparsedOutRequest);
		instance.addShares(additionalShares);
		logger.debug("Added {} additional salesCommissionShares to instance", additionalShares.size());
	}
}
