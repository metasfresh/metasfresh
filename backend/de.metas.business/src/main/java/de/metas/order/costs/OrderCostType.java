package de.metas.order.costs;

import de.metas.order.costs.calculation_methods.CostCalculationMethod;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class OrderCostType
{
	@NonNull OrderCostTypeId id;
	@NonNull CostDistributionMethod distributionMethod;
	@NonNull CostCalculationMethod calculationMethod;
}
