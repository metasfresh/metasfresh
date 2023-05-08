/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.service.produce;

import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.service.PPOrderCandidateHeaderAggregationKeyHelper;

@Value
public class PPOrderCandidateToAllocate
{
	@NonNull
	I_PP_Order_Candidate ppOrderCandidate;

	@NonNull
	String headerAggregationKey;

	@NonFinal
	@NonNull
	Quantity openQty;

	@NonNull
	public static PPOrderCandidateToAllocate of(@NonNull final I_PP_Order_Candidate candidate)
	{
		final UomId uomId = UomId.ofRepoId(candidate.getC_UOM_ID());

		final Quantity openQty = Quantitys.create(candidate.getQtyToProcess(), uomId);

		final String headerAggKey = PPOrderCandidateHeaderAggregationKeyHelper.generateHeaderAggregationKey(candidate);

		return new PPOrderCandidateToAllocate(candidate, headerAggKey, openQty);
	}

	public void subtractAllocatedQty(@NonNull final Quantity allocatedQty)
	{
		openQty = openQty.subtract(allocatedQty);

		if (openQty.signum() < 0)
		{
			throw new AdempiereException("This is a development error! allocatedQty cannot go beyond openQty!")
					.appendParametersToMessage()
					.setParameter(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidate.getPP_Order_Candidate_ID())
					.setParameter("OpenQty", openQty)
					.setParameter("AllocatedQty", allocatedQty);
		}
	}
}
