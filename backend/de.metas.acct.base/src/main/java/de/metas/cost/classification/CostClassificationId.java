package de.metas.cost.classification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class CostClassificationId implements RepoIdAware
{
	@JsonCreator
	public static CostClassificationId ofRepoId(final int repoId)
	{
		return new CostClassificationId(repoId);
	}

	@Nullable
	public static CostClassificationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new CostClassificationId(repoId) : null;
	}

	public static int toRepoId(@Nullable final CostClassificationId costClassificationId)
	{
		return costClassificationId != null ? costClassificationId.getRepoId() : -1;
	}

	int repoId;

	private CostClassificationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final CostClassificationId id1, @Nullable final CostClassificationId id2) {return Objects.equals(id1, id2);}
}
