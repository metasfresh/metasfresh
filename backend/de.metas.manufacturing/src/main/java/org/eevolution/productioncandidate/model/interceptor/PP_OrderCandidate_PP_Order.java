/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.productioncandidate.model.interceptor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_PP_OrderCandidate_PP_Order.class)
@RequiredArgsConstructor
public class PP_OrderCandidate_PP_Order
{
	@NonNull private final PPOrderCandidateService ppOrderCandidateService;

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	private void propagateQtyProcessed(@NonNull final I_PP_OrderCandidate_PP_Order ppOrderAllocation)
	{
		final PPOrderCandidateId ppOrderCandidateId = PPOrderCandidateId.ofRepoId(ppOrderAllocation.getPP_Order_Candidate_ID());
		ppOrderCandidateService.updateOrderCandidateBeforeCommit(ppOrderCandidateId);
	}

}
