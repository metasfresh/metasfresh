package de.metas.costrevaluation;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * M_CostRevaluation_Detail_ID
 */
@Value
public class CostRevaluationDetailId implements RepoIdAware
{
	@NonNull CostRevaluationId costRevaluationId;
	int repoId;

	public static CostRevaluationDetailId ofRepoId(@NonNull final CostRevaluationId costRevaluationId, final int costRevaluationDetailId)
	{
		return new CostRevaluationDetailId(costRevaluationId, costRevaluationDetailId);
	}

	public static CostRevaluationDetailId ofRepoId(final int costRevaluationId, final int costRevaluationDetailId)
	{
		return new CostRevaluationDetailId(CostRevaluationId.ofRepoId(costRevaluationId), costRevaluationDetailId);
	}

	public static CostRevaluationDetailId ofRepoIdOrNull(
			@Nullable final CostRevaluationId costRevaluationId,
			final int costRevaluationDetailId)
	{
		return costRevaluationId != null && costRevaluationDetailId > 0 ? ofRepoId(costRevaluationId, costRevaluationDetailId) : null;
	}

	private CostRevaluationDetailId(@NonNull final CostRevaluationId costRevaluationId, final int costRevaluationDetailId)
	{
		this.repoId = Check.assumeGreaterThanZero(costRevaluationDetailId, "M_CostRevaluation_Detail_ID");
		this.costRevaluationId = costRevaluationId;
	}
}
