package de.metas.distribution.ddorder.movement.schedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class DDOrderMoveScheduleId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static DDOrderMoveScheduleId ofRepoId(final int repoId)
	{
		return new DDOrderMoveScheduleId(repoId);
	}

	@Nullable
	public static DDOrderMoveScheduleId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DDOrderMoveScheduleId(repoId) : null;
	}

	private DDOrderMoveScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "DD_OrderLine_HU_Candidate_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
