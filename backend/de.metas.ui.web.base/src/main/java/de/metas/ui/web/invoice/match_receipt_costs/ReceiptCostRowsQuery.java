package de.metas.ui.web.invoice.match_receipt_costs;

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
public class ReceiptCostRowsQuery
{
	@NonNull QueryLimit queryLimit;
	@Nullable BPartnerId bpartnerId;
	@Nullable OrderId orderId;
	@Nullable OrderCostTypeId costTypeId;
}
