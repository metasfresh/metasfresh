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
	 * The revaluation's posting date. The revaluation refuses to replay any cost-changing stock movement posted on a later day,
	 * because it would book that movement's restatement before the movement itself.
	 */
	@NonNull InstantAndOrgId dateAcct;
	@NonNull CostAmount newCostPrice;
}
