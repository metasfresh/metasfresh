package de.metas.gplr.source.model;

import com.google.common.collect.ImmutableSet;
import de.metas.currency.Amount;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
public class SourceOrderCost
{
	@NonNull OrderId orderId;
	@NonNull String costTypeName;
	@NonNull Amount costAmountFC;
	@Nullable SourceBPartnerInfo vendor;
	@NonNull ImmutableSet<OrderLineId> basedOnOrderLineIds;
	@Nullable OrderLineId createdOrderLineId;

	public boolean isSingleOrderLine() {return basedOnOrderLineIds.size() == 1;}

	public boolean isBasedOnOrderLineId(final OrderLineId orderLineId) {return basedOnOrderLineIds.contains(orderLineId);}

	public boolean isInvoiced() {return this.createdOrderLineId != null;}

	public boolean isInvoicedBy(final OrderLineId orderLineId) {return Objects.equals(this.createdOrderLineId, orderLineId);}

}
