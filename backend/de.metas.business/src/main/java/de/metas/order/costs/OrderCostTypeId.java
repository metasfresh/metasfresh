package de.metas.order.costs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class OrderCostTypeId implements RepoIdAware
{
	@JsonCreator
	public static OrderCostTypeId ofRepoId(final int repoId) {return new OrderCostTypeId(repoId);}

	public static OrderCostTypeId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static Optional<OrderCostTypeId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(@Nullable final OrderCostTypeId id) {return id != null ? id.getRepoId() : -1;}

	int repoId;

	private OrderCostTypeId(final int repoId) {this.repoId = Check.assumeGreaterThanZero(repoId, "C_Cost_Type_ID");}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public static boolean equals(@Nullable final OrderCostTypeId id1, @Nullable final OrderCostTypeId id2) {return Objects.equals(id1, id2);}
}
