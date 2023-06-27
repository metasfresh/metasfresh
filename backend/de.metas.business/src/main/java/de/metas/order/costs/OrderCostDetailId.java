package de.metas.order.costs;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@RepoIdAwares.SkipTest
public class OrderCostDetailId implements RepoIdAware
{
	@NonNull OrderCostId orderCostId;
	int repoId;

	public static OrderCostDetailId ofRepoId(final int orderCostId, final int orderCostDetailId)
	{
		return new OrderCostDetailId(OrderCostId.ofRepoId(orderCostId), orderCostDetailId);
	}

	public static OrderCostDetailId ofRepoId(@NonNull final OrderCostId orderCostId, final int orderCostDetailId)
	{
		return new OrderCostDetailId(orderCostId, orderCostDetailId);
	}

	private OrderCostDetailId(
			@NonNull final OrderCostId orderCostId,
			final int orderCostDetailId)
	{
		this.orderCostId = orderCostId;
		this.repoId = Check.assumeGreaterThanZero(orderCostDetailId, "C_Order_Cost_Detail_ID");
	}

	public static boolean equals(@Nullable final OrderCostDetailId id1, @Nullable final OrderCostDetailId id2) {return Objects.equals(id1, id2);}
}
