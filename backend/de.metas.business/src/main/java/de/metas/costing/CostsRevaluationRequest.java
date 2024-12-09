package de.metas.costing;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class CostsRevaluationRequest
{
	@NonNull CostSegmentAndElement costSegmentAndElement;
	@NonNull Instant evaluationStartDate;
	@NonNull CostAmount newCostPrice;
}
