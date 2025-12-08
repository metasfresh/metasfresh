package de.metas.manufacturing.order.weighting.run;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class PPOrderWeightingRunId implements RepoIdAware
{
	@JsonCreator
	public static PPOrderWeightingRunId ofRepoId(final int repoId)
	{
		return new PPOrderWeightingRunId(repoId);
	}

	public static PPOrderWeightingRunId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PPOrderWeightingRunId(repoId) : null;
	}

	public static Optional<PPOrderWeightingRunId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final PPOrderWeightingRunId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private PPOrderWeightingRunId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Order_Weighting_Run_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
