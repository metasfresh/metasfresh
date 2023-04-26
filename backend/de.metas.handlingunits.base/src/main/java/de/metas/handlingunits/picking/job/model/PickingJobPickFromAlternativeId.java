package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PickingJobPickFromAlternativeId implements RepoIdAware
{
	int repoId;

	private PickingJobPickFromAlternativeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Picking_Job_HUAlternative_ID");
	}

	@JsonCreator
	public static PickingJobPickFromAlternativeId ofRepoId(final int repoId) {return new PickingJobPickFromAlternativeId(repoId);}

	public static PickingJobPickFromAlternativeId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new PickingJobPickFromAlternativeId(repoId) : null;}

	public static int toRepoId(final PickingJobPickFromAlternativeId id) {return id != null ? id.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public String getAsString() {return String.valueOf(getRepoId());}

	public static boolean equals(@Nullable final PickingJobPickFromAlternativeId id1, @Nullable final PickingJobPickFromAlternativeId id2) {return Objects.equals(id1, id2);}
}
