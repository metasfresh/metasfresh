package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

@Value
public class PickingJobLineId implements RepoIdAware
{
	@JsonCreator
	public static PickingJobLineId ofRepoId(final int repoId)
	{
		return new PickingJobLineId(repoId);
	}

	public static PickingJobLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PickingJobLineId(repoId) : null;
	}

	public static int toRepoId(final PickingJobLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private PickingJobLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Picking_Job_Line_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
