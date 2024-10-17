package de.metas.material.planning.ddorder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class DistributionNetworkLineId implements RepoIdAware
{
	int repoId;

	private DistributionNetworkLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "DD_NetworkDistributionLine_ID");
	}

	@JsonCreator
	public static DistributionNetworkLineId ofRepoId(final int repoId)
	{
		return new DistributionNetworkLineId(repoId);
	}

	public static DistributionNetworkLineId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DistributionNetworkLineId(repoId) : null;
	}

	public static Optional<DistributionNetworkLineId> optionalOfRepoId(@Nullable final Integer repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(final DistributionNetworkLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(final DistributionNetworkLineId o1, final DistributionNetworkLineId o2)
	{
		return Objects.equals(o1, o2);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
