package de.metas.order.costs;

import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class OrderCostDetail
{
	@NonNull OrderLineId orderLineId;

	@NonNull ProductId productId;

	@NonNull Money orderLineNetAmt;
	@NonNull Quantity qtyOrdered;

	public UomId getUomId()
	{
		return qtyOrdered.getUomId();
	}
}
