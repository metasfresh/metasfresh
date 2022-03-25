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

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Interceptor(I_PP_OrderCandidate_PP_Order.class)
public class PP_OrderCandidate_PP_Order
{
	@NonNull
	private final PPOrderCandidateDAO ppOrderCandidatesDAO;

	public PP_OrderCandidate_PP_Order(final @NonNull PPOrderCandidateDAO ppOrderCandidatesDAO)
	{
		this.ppOrderCandidatesDAO = ppOrderCandidatesDAO;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	private void propagateQtyProcessed(@NonNull final I_PP_OrderCandidate_PP_Order ppOrderAllocation)
	{
		final PPOrderCandidateId ppOrderCandidateId = PPOrderCandidateId.ofRepoId(ppOrderAllocation.getPP_Order_Candidate_ID());

		final I_PP_Order_Candidate ppOrderCandidate = ppOrderCandidatesDAO.getById(ppOrderCandidateId);

		final ImmutableList<I_PP_OrderCandidate_PP_Order> orderAllocations = ppOrderCandidatesDAO.getOrderAllocations(ppOrderCandidateId);

		final BigDecimal qtyProcessed = orderAllocations.stream()
				.peek(allocation -> {
					if (ppOrderCandidate.getC_UOM_ID() != ppOrderAllocation.getC_UOM_ID())
					{
						//dev-note: this should never happen.. just a guard
						throw new AdempiereException("PP_Order_Candidate.C_UOM_ID != I_PP_OrderCandidate_PP_Order.C_UOM_ID!")
								.setParameter("PP_Order_Candidate.PP_Order_Candidate_ID", ppOrderCandidate.getPP_Order_Candidate_ID())
								.setParameter("PP_Order_Candidate.C_UOM_ID", ppOrderCandidate.getC_UOM_ID())
								.setParameter("I_PP_OrderCandidate_PP_Order.C_UOM_ID", allocation.getC_UOM_ID());
					}
				})
				.map(I_PP_OrderCandidate_PP_Order::getQtyEntered)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		//will be recalculated
		ppOrderCandidate.setQtyToProcess(BigDecimal.ZERO);

		ppOrderCandidate.setQtyProcessed(qtyProcessed);
		ppOrderCandidatesDAO.save(ppOrderCandidate);
	}
}
