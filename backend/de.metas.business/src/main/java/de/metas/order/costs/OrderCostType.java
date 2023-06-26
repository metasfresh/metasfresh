package de.metas.order.costs;

import de.metas.costing.CostElementId;
import de.metas.order.costs.calculation_methods.CostCalculationMethod;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class OrderCostType
{
	@NonNull OrderCostTypeId id;
	@NonNull String name;
	@NonNull CostDistributionMethod distributionMethod;
	@NonNull CostCalculationMethod calculationMethod;
	@NonNull CostElementId costElementId;

	@Nullable ProductId invoiceableProductId;

	public boolean isAllowInvoicing() {return invoiceableProductId != null;}
}
