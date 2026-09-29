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
	 * The revaluation's posting date; its organization gives the time zone of the days named in the refusal messages.
	 */
	@NonNull InstantAndOrgId dateAcct;
	@NonNull CostAmount newCostPrice;
}
