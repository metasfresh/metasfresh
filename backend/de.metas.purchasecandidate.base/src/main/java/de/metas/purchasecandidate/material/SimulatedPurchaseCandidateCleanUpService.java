/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.purchasecandidate.material;

import de.metas.material.commons.disposition.SimulatedCleanUpService;
import de.metas.order.OrderLineId;
import de.metas.purchasecandidate.DeletePurchaseCandidateQuery;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SimulatedPurchaseCandidateCleanUpService implements SimulatedCleanUpService
{
	@NonNull
	private final PurchaseCandidateRepository purchaseCandidateRepository;

	public SimulatedPurchaseCandidateCleanUpService(final @NonNull PurchaseCandidateRepository purchaseCandidateRepository)
	{
		this.purchaseCandidateRepository = purchaseCandidateRepository;
	}

	@Override
	public void cleanUpSimulated()
	{
		final DeletePurchaseCandidateQuery deleteQuery = DeletePurchaseCandidateQuery.builder()
				.onlySimulated(true)
				.build();

		purchaseCandidateRepository.deletePurchaseCandidates(deleteQuery);
	}

	public void deleteSimulatedCandidatesFor(@NonNull final OrderLineId salesOrderLineId)
	{
		final DeletePurchaseCandidateQuery deleteQuery = DeletePurchaseCandidateQuery.builder()
				.salesOrderLineId(salesOrderLineId)
				.onlySimulated(true)
				.build();

		purchaseCandidateRepository.deletePurchaseCandidates(deleteQuery);
	}
}
