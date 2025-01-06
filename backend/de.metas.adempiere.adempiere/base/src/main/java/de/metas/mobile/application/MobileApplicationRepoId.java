package de.metas.mobile.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class MobileApplicationRepoId implements RepoIdAware
{
	@JsonCreator
	public static MobileApplicationRepoId ofRepoId(final int repoId)
	{
		return new MobileApplicationRepoId(repoId);
	}

	@Nullable
	public static MobileApplicationRepoId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new MobileApplicationRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final MobileApplicationRepoId MobileApplicationRepoId)
	{
		return MobileApplicationRepoId != null ? MobileApplicationRepoId.getRepoId() : -1;
	}

	int repoId;

	private MobileApplicationRepoId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "Mobile_Application_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
