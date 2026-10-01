package de.metas.costing;

import de.metas.organization.InstantAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CostsRevaluationRequest
{
	@NonNull CostSegmentAndElement costSegmentAndElement;
	@NonNull InstantAndOrgId dateAcct;
	@NonNull CostAmount newCostPrice;
}
