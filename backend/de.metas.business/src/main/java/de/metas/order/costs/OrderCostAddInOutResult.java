package de.metas.order.costs;

import de.metas.money.Money;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class OrderCostAddInOutResult
{
	@NonNull OrderCostDetailId orderCostDetailId;
	@NonNull Money costAmount;
	@NonNull Quantity qty;
}
