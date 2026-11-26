package de.metas.organization;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class FiscalRepresentationId implements RepoIdAware
{
	int repoId;

	@NonNull
	public static FiscalRepresentationId ofRepoId(final int repoId)
	{
		return new FiscalRepresentationId(repoId);
	}

	@Nullable
	public static FiscalRepresentationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new FiscalRepresentationId(repoId) : null;
	}

	private FiscalRepresentationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Fiscal_Representation_ID");
	}

	@Override
	public int getRepoId()
	{
		return repoId;
	}
}
