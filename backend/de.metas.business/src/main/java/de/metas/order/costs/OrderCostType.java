package de.metas.order.costs;

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
