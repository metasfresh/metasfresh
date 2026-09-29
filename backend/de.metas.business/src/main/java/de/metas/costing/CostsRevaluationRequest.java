package de.metas.costing;

import de.metas.organization.InstantAndOrgId;
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
	/**
	 * The revaluation's posting date.
	 */
	@NonNull InstantAndOrgId dateAcct;
	@NonNull CostAmount newCostPrice;
}
