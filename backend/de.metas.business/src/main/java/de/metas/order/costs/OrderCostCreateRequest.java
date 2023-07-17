package de.metas.order.costs;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.costs.calculation_methods.CostCalculationMethodParams;
import de.metas.product.ProductId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class OrderCostCreateRequest
{
	@Nullable BPartnerId bpartnerId;
	@NonNull OrderCostTypeId costTypeId;
	@Nullable CostCalculationMethodParams costCalculationMethodParams;

	@NonNull ImmutableSet<OrderAndLineId> orderAndLineIds;
	@Nullable OrderLine addOrderLine;

	@Builder
	private OrderCostCreateRequest(
			@Nullable final BPartnerId bpartnerId,
			@NonNull final OrderCostTypeId costTypeId,
			@Nullable final CostCalculationMethodParams costCalculationMethodParams,
			@NonNull final ImmutableSet<OrderAndLineId> orderAndLineIds,
			@Nullable final OrderLine addOrderLine)
	{
		this.bpartnerId = bpartnerId;
		if (orderAndLineIds.isEmpty())
		{
			throw new AdempiereException("No order lines");
		}

		this.costTypeId = costTypeId;
		this.costCalculationMethodParams = costCalculationMethodParams;
		this.orderAndLineIds = orderAndLineIds;
		this.addOrderLine = addOrderLine;
	}

	public OrderId getOrderId()
	{
		return CollectionUtils.extractSingleElement(orderAndLineIds, OrderAndLineId::getOrderId);
	}

	@Value
	@Builder
	public static class OrderLine
	{
		@NonNull ProductId productId;
	}
}
