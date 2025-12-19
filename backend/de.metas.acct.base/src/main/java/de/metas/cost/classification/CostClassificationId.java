package de.metas.cost.classification;

import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

import javax.annotation.Nullable;

public class CostClassificationId implements RepoIdAware
{
	@NonNull
	public static CostClassificationId ofRepoId(final int repoId)
	{
		// TODO
	}

	@Nullable
	public static CostClassificationId ofRepoIdOrNull(final int repoId)
	{
		// TODO
	}

	public static int toRepoId(final CostClassificationId id)
	{
		// TODO
	}
}
