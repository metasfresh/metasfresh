package de.metas.order.copy;

import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.OrderCostCloneMapper;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Builder
class OrderCostCloneMapperImpl implements OrderCostCloneMapper
{
	@NonNull private final OrderId fromOrderId;
	@NonNull private final OrderId targetOrderId;
	@Nullable private final ClonedOrderLinesInfo clonedOrderLinesInfo;

	@Override
	public @NonNull OrderId getTargetOrderId(@NonNull final OrderId orderId)
	{
		if (OrderId.equals(orderId, fromOrderId))
		{
			return targetOrderId;
		}
		else
		{
			// shall not happen
			throw new AdempiereException("Cannot map original order: " + orderId);
		}
	}

	@Override
	public @NonNull OrderLineId getTargetOrderLineId(@NonNull final OrderLineId originalOrderLineId)
	{
		// shall not happen because this method shall not be called in case there are no order lines
		if (clonedOrderLinesInfo == null)
		{
			throw new AdempiereException("No cloned order lines info available");
		}

		return clonedOrderLinesInfo.getTargetOrderLineId(originalOrderLineId);
	}
}
