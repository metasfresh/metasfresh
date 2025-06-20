package de.metas.manufacturing.order.weighting.run;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
@RepoIdAwares.SkipTest
public class PPOrderWeightingRunCheckId implements RepoIdAware
{
	@NonNull PPOrderWeightingRunId runId;
	int repoId;

	private PPOrderWeightingRunCheckId(
			final @NonNull PPOrderWeightingRunId runId,
			final int repoId)
	{
		this.runId = runId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Order_Weighting_RunCheck_ID");
	}

	public static PPOrderWeightingRunCheckId ofRepoId(@NonNull final PPOrderWeightingRunId runId, final int repoId)
	{
		return new PPOrderWeightingRunCheckId(runId, repoId);
	}

	public static PPOrderWeightingRunCheckId ofRepoId(final int runId, final int repoId)
	{
		return new PPOrderWeightingRunCheckId(PPOrderWeightingRunId.ofRepoId(runId), repoId);
	}

	public static int toRepoId(@Nullable final PPOrderWeightingRunCheckId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
