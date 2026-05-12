package de.metas.acct.tax;

import de.metas.util.lang.RepoIdAware;
import lombok.Value;

@Value
public class TaxDeclarationId implements RepoIdAware
{
	int repoId;

	public static TaxDeclarationId ofRepoId(final int repoId)
	{
		return new TaxDeclarationId(repoId);
	}

	public static TaxDeclarationId ofRepoId(final Integer repoId)
	{
		return ofRepoId((int) repoId);
	}
}
