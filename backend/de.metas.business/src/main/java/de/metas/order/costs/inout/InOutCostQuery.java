package de.metas.order.costs.inout;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderId;
import de.metas.order.costs.OrderCostTypeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;

@Value
@Builder
public class InOutCostQuery
{
	@NonNull @Builder.Default QueryLimit limit = QueryLimit.ONE_HUNDRED;
	@Nullable BPartnerId bpartnerId;
	@Nullable OrderId orderId;
	@Nullable OrderCostTypeId costTypeId;
	boolean includeReversed;
	boolean onlyWithOpenAmountToInvoice;
}
