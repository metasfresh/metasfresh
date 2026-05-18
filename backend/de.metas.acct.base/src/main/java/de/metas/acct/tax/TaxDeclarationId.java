package de.metas.acct.tax;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class TaxDeclarationId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static TaxDeclarationId ofRepoId(final int repoId)
	{
		return new TaxDeclarationId(repoId);
	}

	@Nullable
	public static TaxDeclarationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new TaxDeclarationId(repoId) : null;
	}

	@Nullable
	public static TaxDeclarationId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new TaxDeclarationId(repoId) : null;
	}

	public static int toRepoId(@Nullable final TaxDeclarationId taxDeclarationId)
	{
		return taxDeclarationId != null ? taxDeclarationId.repoId : -1;
	}

	private TaxDeclarationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_TaxDeclaration_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
