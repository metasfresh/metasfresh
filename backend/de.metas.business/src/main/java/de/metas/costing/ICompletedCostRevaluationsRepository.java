package de.metas.costing;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

/**
 * What the costing engine needs to know about completed cost revaluations when replaying cost details.
 */
public interface ICompletedCostRevaluationsRepository
{
	/**
	 * @return per given cost detail, its restatement by the most recent (latest posting date, then latest ID) completed cost revaluation;
	 * cost details no completed revaluation restated are not in the map.
	 */
	@NonNull
	ImmutableMap<CostDetailId, CostDetailRestatement> getLatestCompletedRestatementsByCostDetailIds(@NonNull Collection<CostDetailId> costDetailIds);

	/**
	 * @return the earliest posting date of a completed cost revaluation with a line for the given cost segment and element, posted on or after {@code date}
	 */
	@NonNull
	Optional<Instant> getFirstCompletedDateAcctOnOrAfter(@NonNull CostSegmentAndElement costSegmentAndElement, @NonNull Instant date);

	/**
	 * @return the earliest posting date of a completed cost revaluation with a line for the given cost segment and element whose accounting is not done yet
	 */
	@NonNull
	Optional<Instant> getFirstCompletedNotPostedDateAcct(@NonNull CostSegmentAndElement costSegmentAndElement);
}
