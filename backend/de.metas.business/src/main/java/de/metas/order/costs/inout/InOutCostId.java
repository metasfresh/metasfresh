package de.metas.order.costs.inout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class InOutCostId implements RepoIdAware
{
	@JsonCreator
	public static InOutCostId ofRepoId(final int repoId) {return new InOutCostId(repoId);}

	public static InOutCostId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static int toRepoId(@Nullable final InOutCostId id) {return id != null ? id.getRepoId() : -1;}

	int repoId;

	private InOutCostId(final int repoId) {this.repoId = Check.assumeGreaterThanZero(repoId, "M_InOut_Cost_ID");}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public static boolean equals(@Nullable final InOutCostId id1, @Nullable final InOutCostId id2) {return Objects.equals(id1, id2);}
}
