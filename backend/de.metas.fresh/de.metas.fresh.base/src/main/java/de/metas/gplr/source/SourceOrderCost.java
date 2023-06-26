package de.metas.gplr.source;

import com.google.common.collect.ImmutableSet;
import de.metas.currency.Amount;
import de.metas.order.OrderLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceOrderCost
{
	@NonNull String costTypeName;
	@NonNull Amount costAmount;
	@Nullable SourceBPartnerInfo vendor;
	@NonNull ImmutableSet<OrderLineId> basedOnOrderLineIds;

	public boolean isSingleOrderLine() {return basedOnOrderLineIds.size() == 1;}

	public boolean isBasedOnOrderLineId(final OrderLineId orderLineId) {return basedOnOrderLineIds.contains(orderLineId);}

}
