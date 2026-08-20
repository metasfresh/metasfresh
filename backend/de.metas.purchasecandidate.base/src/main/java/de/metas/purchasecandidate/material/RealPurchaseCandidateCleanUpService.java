/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderLineId;
import de.metas.purchasecandidate.DeletePurchaseCandidateQuery;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

/**
 * Cascades the delete of a sales order line onto its <b>real</b> (i.e. non-simulated) {@code C_PurchaseCandidate}
 * records: candidates that never produced a purchase order are deleted along with the line, while candidates that
 * already produced a purchase order block the delete.
 */
@Service
@RequiredArgsConstructor
public class RealPurchaseCandidateCleanUpService
{
	public static final AdMessageKey MSG_SalesOrderLine_CannotDelete_HasCompletedDocs = AdMessageKey.of("SalesOrderLine_CannotDelete_HasCompletedDocs");

	@NonNull
	private final PurchaseCandidateRepository purchaseCandidateRepository;

	public void deleteRealCandidatesFor(@NonNull final OrderLineId salesOrderLineId)
	{
		if (purchaseCandidateRepository.hasCandidateThatProducedAPurchaseOrder(salesOrderLineId))
		{
			throw new AdempiereException(MSG_SalesOrderLine_CannotDelete_HasCompletedDocs);
		}

		final DeletePurchaseCandidateQuery deleteQuery = DeletePurchaseCandidateQuery.builder()
				.salesOrderLineId(salesOrderLineId)
				.onlySimulated(false)
				.build();

		purchaseCandidateRepository.deletePurchaseCandidates(deleteQuery);
	}
}
