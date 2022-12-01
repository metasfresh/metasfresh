package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PickingJobId implements RepoIdAware
{
	@JsonCreator
	public static PickingJobId ofRepoId(final int repoId)
	{
		return new PickingJobId(repoId);
	}

	public static PickingJobId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PickingJobId(repoId) : null;
	}

	public static int toRepoId(final PickingJobId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private PickingJobId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Picking_Job_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final PickingJobId id1, @Nullable final PickingJobId id2) {return Objects.equals(id1, id2);}
}
