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
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanningId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.productioncandidate.async.OrderGenerateResult;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PPOrderProducerFromCandidate
{
	private final OrderGenerateResult result;
	private final PPOrderAllocatorService ppOrderAllocatorBuilderService;
	private final PPOrderCandidateDAO ppOrderCandidatesDAO;
	private final IPPOrderBL ppOrderService;
	private final ITrxManager trxManager;
	private final IProductPlanningDAO productPlanningsRepo;
	private final Map<ProductPlanningId, I_PP_Product_Planning> productPlanningCache;
	private final boolean createEachPPOrderInOwnTrx;

	@Builder
	public PPOrderProducerFromCandidate(
			@NonNull final PPOrderAllocatorService ppOrderAllocatorBuilderService,
			@NonNull final IPPOrderBL ppOrderService,
			@NonNull final ITrxManager trxManager,
			@NonNull final PPOrderCandidateDAO ppOrderCandidatesDAO,
			@NonNull final IProductPlanningDAO productPlanningsRepo,
			final boolean createEachPPOrderInOwnTrx)
	{
		this.ppOrderAllocatorBuilderService = ppOrderAllocatorBuilderService;
		this.ppOrderService = ppOrderService;
		this.trxManager = trxManager;
		this.ppOrderCandidatesDAO = ppOrderCandidatesDAO;
		this.productPlanningsRepo = productPlanningsRepo;
		this.createEachPPOrderInOwnTrx = createEachPPOrderInOwnTrx;

		this.result = new OrderGenerateResult();
		this.productPlanningCache = new ConcurrentHashMap<>();
	}

	@NonNull
	public OrderGenerateResult createOrders(@NonNull final Stream<I_PP_Order_Candidate> candidates, @Nullable final Boolean isDocComplete)
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

		processPPOrderCandidates(sortedCandidates, isDocComplete);

		return result;
	}

	private void processPPOrderCandidates(
			@NonNull final ImmutableList<PPOrderCandidateToAllocate> ppOrderCandToAllocateSorted,
			@Nullable final Boolean isDocComplete)
	{
		PPOrderAllocator allocator = null;
		for (final PPOrderCandidateToAllocate ppOrderCandidateToAllocate : ppOrderCandToAllocateSorted)
		{
			if (allocator == null)
			{
				allocator = ppOrderAllocatorBuilderService.buildAllocator(ppOrderCandidateToAllocate);
			}

			int infiniteLoopGuard = 2000;
			while (ppOrderCandidateToAllocate.getOpenQty().signum() > 0)
			{
				final Quantity allocatedQty = allocator.allocate(ppOrderCandidateToAllocate);

				//note: if nothing could be allocated it's time to create the PPOrder and initiate a new allocator
				if (allocatedQty.isZero())
				{
					createPPOrderInTrx(allocator, isDocComplete);

					allocator = ppOrderAllocatorBuilderService.buildAllocator(ppOrderCandidateToAllocate);
				}

				ppOrderCandidateToAllocate.subtractAllocatedQty(allocatedQty);

				infiniteLoopGuard--;

				if (infiniteLoopGuard <= 0)
				{
					throw new AdempiereException("Infinite loop guard reached!")
							.appendParametersToMessage()
							.setParameter("PP_Order_Candidate_ID", ppOrderCandidateToAllocate.getPpOrderCandidate().getPP_Order_Candidate_ID());
				}
			}
		}

		if (allocator != null && allocator.getAllocatedQty().signum() > 0)
		{
			createPPOrderInTrx(allocator, isDocComplete);
		}
	}

	private void createPPOrderInTrx(@NonNull final PPOrderAllocator allocator, @Nullable final Boolean completeDoc)
	{
		final Consumer<Runnable> runInTrx = createEachPPOrderInOwnTrx
				? trxManager::runInNewTrx
				: trxManager::runInThreadInheritedTrx;

		runInTrx.accept(() ->
						{
							final PPOrderCreateRequest request = allocator.getPPOrderCreateRequest();

							final I_PP_Order ppOrder = ppOrderService.createOrder(request);

							createPPOrderAllocations(ppOrder, allocator.getPpOrderCand2AllocatedQty());

							ppOrderService.postPPOrderCreatedEvent(ppOrder);

							if (shouldCompletePPOrder(request.getProductPlanningId(), completeDoc))
							{
								ppOrderService.completeDocument(ppOrder);
							}

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

	private boolean shouldCompletePPOrder(@Nullable final ProductPlanningId planningId, @Nullable final Boolean completeDocOverride)
	{
		if (completeDocOverride != null)
		{
			return completeDocOverride;
		}

		if (planningId == null)
		{
			return false;
		}

		return productPlanningCache.computeIfAbsent(planningId, productPlanningsRepo::getById)
				.isDocComplete();
	}
}