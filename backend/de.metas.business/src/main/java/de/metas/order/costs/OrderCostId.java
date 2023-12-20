package de.metas.order.costs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class OrderCostId implements RepoIdAware
{
	@JsonCreator
	public static OrderCostId ofRepoId(final int repoId) {return new OrderCostId(repoId);}

	public static OrderCostId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static int toRepoId(final OrderCostId id) {return id != null ? id.getRepoId() : -1;}

	int repoId;

	private OrderCostId(final int repoId) {this.repoId = Check.assumeGreaterThanZero(repoId, "C_Order_Cost_ID");}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public static boolean equals(@Nullable final OrderCostId id1, @Nullable final OrderCostId id2) {return Objects.equals(id1, id2);}
}
