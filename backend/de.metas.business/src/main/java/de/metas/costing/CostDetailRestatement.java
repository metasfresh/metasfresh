package de.metas.costing;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * How a completed cost revaluation restated one cost detail: the amount the cost detail stands at after it, and the
 * cost price in effect right before that cost detail.
 */
@Value
@Builder
public class CostDetailRestatement
{
	@NonNull CostDetailId costDetailId;
	@NonNull CostAmount newAmount;
	@NonNull CostAmount newCostPrice;
}
