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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

import java.util.HashMap;
import java.util.Map;

@Value
@Builder
public class PPOrderAllocator
{
	@NonNull
	String headerAggKey;

	@NonNull
	PPOrderCreateRequest.PPOrderCreateRequestBuilder ppOrderCreateRequestBuilder;

	@NonNull
	Map<PPOrderCandidateId, Quantity> ppOrderCand2AllocatedQty = new HashMap<>();

	@NonNull
	Quantity capacityPerProductionCycle;

	@NonNull
	@NonFinal
	Quantity allocatedQty;

	public boolean allocate(@NonNull final PPOrderCandidateToAllocate ppOrderCandidateToAllocate)
	{
		if (!headerAggKey.equals(ppOrderCandidateToAllocate.getHeaderAggregationKey()))
		{
			return false;
		}

		if (isFullCapacity())
		{
			return false;
		}

		if (candidateCanBeFullyAllocated(ppOrderCandidateToAllocate))
		{
			allocateQuantity(ppOrderCandidateToAllocate, ppOrderCandidateToAllocate.getOpenQty());

			return true;
		}

		final Quantity partialQtyToAllocate = capacityPerProductionCycle.subtract(allocatedQty);
		allocateQuantity(ppOrderCandidateToAllocate, partialQtyToAllocate);

		return false;
	}

	@NonNull
	public PPOrderCreateRequest getPPOrderCreateRequest()
	{
		return ppOrderCreateRequestBuilder
				.qtyRequired(allocatedQty)
				.build();
	}

	private boolean isFullCapacity()
	{
		return !isInfiniteCapacity() && capacityPerProductionCycle.equals(allocatedQty);
	}

	private boolean candidateCanBeFullyAllocated(@NonNull final PPOrderCandidateToAllocate ppOrderCandidateToAllocate)
	{
		if (isInfiniteCapacity())
		{
			return true;
		}

		final Quantity remainingCapacity = capacityPerProductionCycle.subtract(allocatedQty);

		return ppOrderCandidateToAllocate.getOpenQty().compareTo(remainingCapacity) <= 0;
	}

	private void allocateQuantity(@NonNull final PPOrderCandidateToAllocate ppOrderCandidateToAllocate, @NonNull final Quantity quantityToAllocate)
	{
		allocatedQty = allocatedQty.add(quantityToAllocate);

		final PPOrderCandidateId ppOrderCandidateId = PPOrderCandidateId.ofRepoId(ppOrderCandidateToAllocate.getPpOrderCandidate().getPP_Order_Candidate_ID());

		ppOrderCand2AllocatedQty.put(ppOrderCandidateId, quantityToAllocate);

		ppOrderCandidateToAllocate.registerAllocatedQty(quantityToAllocate);
	}

	private boolean isInfiniteCapacity()
	{
		return capacityPerProductionCycle.signum() == 0;
	}
}
