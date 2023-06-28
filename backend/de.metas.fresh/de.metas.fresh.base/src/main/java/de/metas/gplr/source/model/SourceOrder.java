package de.metas.gplr.source.model;

import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.order.OrderLineId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.sectionCode.SectionCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Value
@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SourceOrder
{
	@NonNull String documentNo;
	@NonNull SourceBPartnerInfo bpartner;
	@NonNull Optional<SectionCode> sectionCode;
	@NonNull CurrencyCode currencyCode;
	@NonNull Instant dateOrdered;
	@Nullable String poReference;
	@NonNull PaymentTerm paymentTerm;
	@Nullable SourceIncotermsAndLocation incotermsAndLocation;

	@NonNull List<SourceOrderLine> lines;
	@NonNull List<SourceOrderCost> orderCosts;

	public List<SourceOrderCost> getOrderCostsByLineId(@NonNull final OrderLineId orderLineId)
	{
		return orderCosts.stream()
				.filter(orderCost -> orderCost.isBasedOnOrderLineId(orderLineId))
				.collect(ImmutableList.toImmutableList());
	}

	public Amount getEstimatedOrderCostAmount()
	{
		return orderCosts.stream()
				.map(SourceOrderCost::getCostAmount)
				.reduce(Amount::add)
				.orElseGet(() -> Amount.zero(currencyCode));
	}
}
