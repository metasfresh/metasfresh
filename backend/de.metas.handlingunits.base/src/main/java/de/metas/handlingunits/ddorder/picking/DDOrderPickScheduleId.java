package de.metas.handlingunits.ddorder.picking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class DDOrderPickScheduleId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static DDOrderPickScheduleId ofRepoId(final int repoId)
	{
		return new DDOrderPickScheduleId(repoId);
	}

	@Nullable
	public static DDOrderPickScheduleId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new DDOrderPickScheduleId(repoId) : null;
	}

	private DDOrderPickScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "DD_OrderLine_HU_Candidate_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
