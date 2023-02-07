package de.metas.order.costs;

import com.google.common.collect.ImmutableSet;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class OrderCostCreateRequest
{
	@NonNull OrderCostTypeId costTypeId;
	@Nullable Money fixedAmount;
	@Nullable Percent percentageOfAmount;

	@NonNull ImmutableSet<OrderAndLineId> orderAndLineIds;



	public OrderId getOrderId()
	{
		return CollectionUtils.extractSingleElement(orderAndLineIds, OrderAndLineId::getOrderId);
	}
}
