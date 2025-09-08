package de.metas.order.costs.inout;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.order.costs.OrderCost;
import de.metas.order.costs.OrderCostAddInOutRequest;
import de.metas.order.costs.OrderCostId;
import de.metas.order.costs.OrderCostRepository;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

public class InOutCostReverseCommand
{
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final IInOutBL inoutBL;
	@NonNull private final InOutCostRepository inOutCostRepository;

	@NonNull private final InOutId inoutId;
	@NonNull private final InOutId initialReversalId;

	@Builder
	private InOutCostReverseCommand(
			final @NonNull IInOutBL inoutBL,
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull InOutCostRepository inOutCostRepository,
			//
			final @NonNull InOutId inoutId,
			final @NonNull InOutId initialReversalId)
	{
		if (InOutId.equals(inoutId, initialReversalId))
		{
			throw new AdempiereException("InOut and reversal cannot have the same ID: " + inoutId);
		}

		this.inoutBL = inoutBL;
		this.orderCostRepository = orderCostRepository;
		this.inOutCostRepository = inOutCostRepository;

		this.inoutId = inoutId;
		this.initialReversalId = initialReversalId;
	}

	public void execute()
	{
		final ImmutableMap<InOutAndLineId, InOutAndLineId> initialReversalLineId2lineId = inoutBL.getLines(inoutId)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						inoutLine -> InOutAndLineId.ofRepoId(initialReversalId, inoutLine.getReversalLine_ID()),
						inoutLine -> InOutAndLineId.ofRepoId(inoutLine.getM_InOut_ID(), inoutLine.getM_InOutLine_ID())
				));

		final ImmutableList<InOutCost> initialCosts = inOutCostRepository.getByInOutId(initialReversalId);
		if (initialCosts.isEmpty())
		{
			return;
		}

		final ImmutableSet<OrderCostId> orderCostIds = initialCosts.stream().map(InOutCost::getOrderCostId).collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<OrderCostId, OrderCost> orderCostsById = Maps.uniqueIndex(orderCostRepository.getByIds(orderCostIds), OrderCost::getId);

		for (final InOutCost initialCost : initialCosts)
		{
			final InOutAndLineId initialReversalLineId = initialCost.getInoutAndLineId();
			final InOutAndLineId lineId = initialReversalLineId2lineId.get(initialReversalLineId);
			if (lineId == null)
			{
				continue;
			}

			final InOutCost reversalCost = inOutCostRepository.create(InOutCostCreateRequest.builder()
					.reversalId(initialCost.getReversalId())
					.orgId(initialCost.getOrgId())
					.orderCostDetailId(initialCost.getOrderCostDetailId())
					.orderAndLineId(initialCost.getOrderAndLineId())
					.soTrx(initialCost.getSoTrx())
					.inoutAndLineId(lineId)
					.bpartnerId(initialCost.getBpartnerId())
					.costTypeId(initialCost.getCostTypeId())
					.costElementId(initialCost.getCostElementId())
					.qty(initialCost.getQty().negate())
					.costAmount(initialCost.getCostAmount().negate())
					.build());

			initialCost.setReversalId(reversalCost.getId());

			final OrderCost orderCost = orderCostsById.get(reversalCost.getOrderCostId());
			orderCost.addInOutCost(
					OrderCostAddInOutRequest.builder()
							.orderLineId(reversalCost.getOrderLineId())
							.qty(reversalCost.getQty())
							.costAmount(reversalCost.getCostAmount())
							.build());
		}

		inOutCostRepository.saveAll(initialCosts); // so have the reversal link
		orderCostRepository.saveAll(orderCostsById.values());
	}
}
