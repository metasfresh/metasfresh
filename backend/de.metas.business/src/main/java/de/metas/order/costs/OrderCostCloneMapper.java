package de.metas.order.costs;

import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import lombok.NonNull;

public interface OrderCostCloneMapper
{
	@NonNull OrderId getTargetOrderId(@NonNull OrderId orderId);

	@NonNull OrderLineId getTargetOrderLineId(@NonNull OrderLineId originalOrderLineId);
}
