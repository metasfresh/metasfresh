package org.adempiere.mmovement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class MovementId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static MovementId ofRepoId(final int repoId)
	{
		return new MovementId(repoId);
	}

	public static MovementId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	private MovementId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Movement_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final MovementId id) {return id != null ? id.getRepoId() : -1;}
}
