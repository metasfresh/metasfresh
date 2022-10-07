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

import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.async.OrderGenerateResult;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

public class PPOrderProducerFromCandidate
{
	private final OrderGenerateResult result;
	private final PPOrderAllocatorBuilderService ppOrderAllocatorBuilderService;
	private final PPOrderCandidateDAO ppOrderCandidatesDAO;
	private final IPPOrderBL ppOrderService;
	private final ITrxManager trxManager;

	public PPOrderProducerFromCandidate(
			@NonNull final PPOrderAllocatorBuilderService ppOrderAllocatorBuilderService,
			@NonNull final IPPOrderBL ppOrderService,
			@NonNull final ITrxManager trxManager,
			@NonNull final PPOrderCandidateDAO ppOrderCandidatesDAO)
	{
		this.ppOrderAllocatorBuilderService = ppOrderAllocatorBuilderService;
		this.ppOrderService = ppOrderService;
		this.trxManager = trxManager;
		this.ppOrderCandidatesDAO = ppOrderCandidatesDAO;

		this.result = new OrderGenerateResult();
	}

	@NonNull
	public OrderGenerateResult createOrders(@NonNull final Stream<I_PP_Order_Candidate> candidates, final boolean isDocComplete)
	{
		final ImmutableList<PPOrderCandidateToAllocate> sortedCandidates = candidates
				.filter(orderCandidate -> !orderCandidate.isProcessed())
				.map(PPOrderCandidateToAllocate::of)
				.sorted(Comparator.comparing(PPOrderCandidateToAllocate::getHeaderAggregationKey))
				.collect(ImmutableList.toImmutableList());

		if (sortedCandidates.isEmpty())
		{
			return result;
		}

		allocateCandidatesBasedOnResourceCapacity(sortedCandidates, isDocComplete);

		return result;
	}

	private void allocateCandidatesBasedOnResourceCapacity(
			@NonNull final ImmutableList<PPOrderCandidateToAllocate> ppOrderCandToAllocateSorted,
			final boolean isDocComplete)
	{
		Check.assume(!ppOrderCandToAllocateSorted.isEmpty(), "PPOrder Candidates must not be empty at this stage!");

		PPOrderAllocator allocator = null;
		for (final PPOrderCandidateToAllocate ppOrderCandidateToAllocate : ppOrderCandToAllocateSorted)
		{
			if (allocator == null)
			{
				allocator = ppOrderAllocatorBuilderService.buildAllocator(ppOrderCandidateToAllocate, isDocComplete);
			}

			boolean fullyAllocatedCandidate = false;
			while (!fullyAllocatedCandidate)
			{
				fullyAllocatedCandidate = allocator.allocate(ppOrderCandidateToAllocate);

				if (!fullyAllocatedCandidate)
				{
					createPPOrderInNewTrx(allocator);

					allocator = ppOrderAllocatorBuilderService.buildAllocator(ppOrderCandidateToAllocate, isDocComplete);
				}
			}
		}

		if (allocator != null && allocator.getAllocatedQty().signum() > 0)
		{
			createPPOrderInNewTrx(allocator);
		}
	}

	public void createPPOrderInNewTrx(@NonNull final PPOrderAllocator allocator)
	{
		trxManager.runInNewTrx(() ->
							   {
								   final I_PP_Order ppOrder = ppOrderService.createOrder(allocator.getPPOrderCreateRequest());

								   createPPOrderAllocations(ppOrder, allocator.getPpOrderCand2AllocatedQty());

								   ppOrderService.postPPOrderCreatedEvent(ppOrder);

								   result.addOrder(ppOrder);
							   });
	}

	private void createPPOrderAllocations(@NonNull final I_PP_Order ppOrder, @NonNull final Map<PPOrderCandidateId, Quantity> ppOrderCand2QtyToAllocate)
	{
		ppOrderCand2QtyToAllocate.forEach((candidateId, quantity) -> {
			ppOrderCandidatesDAO.createProductionOrderAllocation(candidateId, ppOrder, quantity);

			ppOrderCandidatesDAO.markAsProcessed(candidateId);
		});
	}
}