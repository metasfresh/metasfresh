package de.metas.invoice.matchinv;

import de.metas.money.Money;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.order.costs.inout.InOutCostId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class MatchInvCostPart
{
	@NonNull InOutCostId inoutCostId;
	@NonNull OrderCostTypeId costTypeId;
	@NonNull Money costAmount;
}
