package de.metas.order.costs.inout;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.inout.InOutId;
import de.metas.order.costs.OrderCost;
import de.metas.order.costs.OrderCostAddReceiptRequest;
import de.metas.order.costs.OrderCostId;
import de.metas.order.costs.OrderCostRepository;
import lombok.Builder;
import lombok.NonNull;

public class InOutCostDeleteCommand
{
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final InOutCostRepository inOutCostRepository;

	@NonNull private final InOutId receiptId;

	@Builder
	private InOutCostDeleteCommand(
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull InOutCostRepository inOutCostRepository,
			//
			final @NonNull InOutId receiptId)
	{
		this.orderCostRepository = orderCostRepository;
		this.inOutCostRepository = inOutCostRepository;

		this.receiptId = receiptId;
	}

	public void execute()
	{
		final ImmutableList<InOutCost> inoutCosts = inOutCostRepository.getByReceiptId(receiptId);
		if (inoutCosts.isEmpty())
		{
			return;
		}

		final ImmutableSet<OrderCostId> orderCostIds = inoutCosts.stream().map(InOutCost::getOrderCostId).collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<OrderCostId, OrderCost> orderCostsById = Maps.uniqueIndex(orderCostRepository.getByIds(orderCostIds), OrderCost::getId);

		for (final InOutCost inoutCost : inoutCosts)
		{
			final OrderCost orderCost = orderCostsById.get(inoutCost.getOrderCostId());
			orderCost.addMaterialReceipt(
					OrderCostAddReceiptRequest.builder()
							.orderLineId(inoutCost.getOrderLineId())
							.qty(inoutCost.getQty().negate())
							.costAmount(inoutCost.getCostAmount().negate())
							.build());
		}

		inOutCostRepository.deleteAll(inoutCosts);
	}
}
