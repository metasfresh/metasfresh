package de.metas.order.costs.inout;

import de.metas.bpartner.BPartnerId;
import de.metas.costing.CostElementId;
import de.metas.inout.InOutAndLineId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.costs.OrderCostDetailId;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class InOutCostCreateRequest
{
	@Nullable InOutCostId reversalId;
	@NonNull OrgId orgId;
	@NonNull OrderCostDetailId orderCostDetailId;
	@NonNull OrderAndLineId orderAndLineId;
	@NonNull SOTrx soTrx;
	@NonNull InOutAndLineId inoutAndLineId;

	@Nullable BPartnerId bpartnerId;
	@NonNull OrderCostTypeId costTypeId;
	@NonNull CostElementId costElementId;

	@NonNull Quantity qty;
	@NonNull Money costAmount;
}
