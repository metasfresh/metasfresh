package de.metas.sales_region;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
public class SalesRegionId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static SalesRegionId ofRepoId(final int repoId) {return new SalesRegionId(repoId);}

	@Nullable
	public static SalesRegionId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new SalesRegionId(repoId) : null;}

	public static Optional<SalesRegionId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(@Nullable final SalesRegionId id) {return id != null ? id.getRepoId() : -1;}

	private SalesRegionId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_SalesRegion_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
