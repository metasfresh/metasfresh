package de.metas.order.split;

import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class OrderSplitRequest
{
	@NonNull OrderId orderId;
}
