package de.metas.cost.classification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class CostClassificationCategoryId implements RepoIdAware
{
	@JsonCreator
	public static CostClassificationCategoryId ofRepoId(final int repoId)
	{
		return new CostClassificationCategoryId(repoId);
	}

	@Nullable
	public static CostClassificationCategoryId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new CostClassificationCategoryId(repoId) : null;
	}

	public static int toRepoId(@Nullable final CostClassificationCategoryId costClassificationCategoryId)
	{
		return costClassificationCategoryId != null ? costClassificationCategoryId.getRepoId() : -1;
	}

	int repoId;

	private CostClassificationCategoryId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final CostClassificationCategoryId id1, @Nullable final CostClassificationCategoryId id2) {return Objects.equals(id1, id2);}
}
