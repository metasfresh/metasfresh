package de.metas.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

@Value
public class MatchPOId implements RepoIdAware
{
	@JsonCreator
	public static MatchPOId ofRepoId(final int repoId)
	{
		return new MatchPOId(repoId);
	}

	public static MatchPOId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new MatchPOId(repoId) : null;
	}

	int repoId;

	private MatchPOId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_MatchPO_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(final MatchPOId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
