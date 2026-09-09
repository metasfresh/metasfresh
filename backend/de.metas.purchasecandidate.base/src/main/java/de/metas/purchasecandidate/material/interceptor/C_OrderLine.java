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

package de.metas.purchasecandidate.material.interceptor;

import de.metas.order.OrderLineId;
import de.metas.purchasecandidate.material.RealPurchaseCandidateCleanUpService;
import de.metas.purchasecandidate.material.SimulatedPurchaseCandidateCleanUpService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_OrderLine.class)
@Component
@RequiredArgsConstructor
public class C_OrderLine
{
	@NonNull
	private final SimulatedPurchaseCandidateCleanUpService simulatedPurchaseCandidateCleanUpService;

	@NonNull
	private final RealPurchaseCandidateCleanUpService realPurchaseCandidateCleanUpService;

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void removeSimulatedPurchaseCandidate(final I_C_OrderLine orderLine)
	{
		simulatedPurchaseCandidateCleanUpService.deleteSimulatedCandidatesFor(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
	}

	/**
	 * Cascades the delete onto real (i.e. non-simulated) purchase candidates of a sales order line, guarding
	 * against deleting a line whose candidate already produced a purchase order.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteOrGuardRealPurchaseCandidate(final I_C_OrderLine orderLine)
	{
		if (!orderLine.getC_Order().isSOTrx())
		{
			return;
		}

		realPurchaseCandidateCleanUpService.deleteRealCandidatesFor(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
	}
}
