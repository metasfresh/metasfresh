package de.metas.inoutcandidate.split;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class ShipmentScheduleSplitId implements RepoIdAware
{
	int repoId;

	public ShipmentScheduleSplitId(final int repoId)
	{
		Check.assumeGreaterThanZero(repoId, "M_ShipmentSchedule_Split_ID");
		this.repoId = repoId;
	}

	@JsonCreator
	public static ShipmentScheduleSplitId ofRepoId(final int repoId) {return new ShipmentScheduleSplitId(repoId);}

	@Nullable
	public static ShipmentScheduleSplitId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static int toRepoId(@Nullable ShipmentScheduleSplitId id) {return id != null ? id.repoId : -1;}

	public static boolean equals(@Nullable ShipmentScheduleSplitId id1, @Nullable ShipmentScheduleSplitId id2) {return Objects.equals(id1, id2);}

	@JsonValue
	public int getRepoId() {return repoId;}
}
