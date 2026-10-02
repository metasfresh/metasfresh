package de.metas.costing;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CostsRevaluationRequest
{
	@NonNull CostSegmentAndElement costSegmentAndElement;
	@NonNull CostAmount newCostPrice;
}
