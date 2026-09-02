package de.metas.mobile.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class MobileApplicationActionId implements RepoIdAware
{
	@JsonCreator
	public static MobileApplicationActionId ofRepoId(final int repoId)
	{
		return new MobileApplicationActionId(repoId);
	}

	@Nullable
	public static MobileApplicationActionId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new MobileApplicationActionId(repoId) : null;
	}

	public static int toRepoId(@Nullable final MobileApplicationActionId MobileApplicationActionId)
	{
		return MobileApplicationActionId != null ? MobileApplicationActionId.getRepoId() : -1;
	}

	int repoId;

	private MobileApplicationActionId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "Mobile_Application_Action_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
