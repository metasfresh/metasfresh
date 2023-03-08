package de.metas.order.costs;

import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class OrderCostAddInOutRequest
{
	@NonNull OrderLineId orderLineId;
	@NonNull Quantity qty;
	@NonNull Money costAmount;
}
