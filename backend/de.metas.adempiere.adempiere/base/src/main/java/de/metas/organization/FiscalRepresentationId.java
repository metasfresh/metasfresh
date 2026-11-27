package de.metas.organization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class FiscalRepresentationId implements RepoIdAware
{
	int repoId;

	@JsonCreator
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
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
