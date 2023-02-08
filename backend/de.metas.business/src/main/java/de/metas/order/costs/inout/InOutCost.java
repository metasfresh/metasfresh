package de.metas.order.costs.inout;

import de.metas.inout.InOutAndLineId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.OrderCostDetailId;
import de.metas.order.costs.OrderCostId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class InOutCost
{
	@NonNull private final InOutCostId id;

	@Nullable InOutCostId reversalId;

	@NonNull private final OrgId orgId;
	@NonNull private final OrderCostDetailId orderCostDetailId;
	@NonNull private final OrderAndLineId orderAndLineId;
	@NonNull private final InOutAndLineId receiptAndLineId;

	@NonNull private final Quantity qty;
	@NonNull private final Money costAmount;

	public OrderCostId getOrderCostId() {return orderCostDetailId.getOrderCostId();}

	public OrderLineId getOrderLineId() {return orderAndLineId.getOrderLineId();}
}
