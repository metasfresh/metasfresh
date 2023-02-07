package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode
@ToString
public class OrderCost
{
	@Getter @Setter @Nullable private OrderCostId id;
	@Getter @NonNull private final OrderId orderId;
	@Getter @NonNull private final OrgId orgId;
	@Getter @NonNull private final OrderCostTypeId costTypeId;
	@Getter @NonNull private final CostCalculationMethod calculationMethod;
	@Getter @NonNull private final CostDistributionMethod distributionMethod;

	@Getter @NonNull private final Money costAmount;

	@Getter @NonNull private final ImmutableList<OrderCostDetail> details;

	@Builder
	private OrderCost(
			final @Nullable OrderCostId id,
			final @NonNull OrderId orderId,
			final @NonNull OrgId orgId,
			final @NonNull OrderCostTypeId costTypeId,
			final @NonNull CostCalculationMethod calculationMethod,
			final @NonNull CostDistributionMethod distributionMethod,
			final @NonNull Money costAmount,
			final @NonNull ImmutableList<OrderCostDetail> details)
	{
		this.id = id;
		this.orderId = orderId;
		this.orgId = orgId;
		this.costTypeId = costTypeId;
		this.calculationMethod = calculationMethod;
		this.distributionMethod = distributionMethod;
		this.costAmount = costAmount;
		this.details = details;
	}
}
