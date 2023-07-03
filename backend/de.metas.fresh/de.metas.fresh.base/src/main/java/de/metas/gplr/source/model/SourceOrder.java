package de.metas.gplr.source.model;

import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.order.OrderLineId;
import de.metas.payment.paymentinstructions.PaymentInstructions;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.sectionCode.SectionCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Value
@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SourceOrder
{
	@NonNull String documentNo;
	@NonNull SourceBPartnerInfo bpartner;
	@Nullable String frameContractNo;
	@NonNull Optional<SectionCode> sectionCode;
	@NonNull SourceCurrencyInfo currencyInfo;
	@NonNull Instant dateOrdered;
	@Nullable String poReference;
	@NonNull PaymentTerm paymentTerm;
	@Nullable PaymentInstructions paymentInstructions;
	@Nullable SourceIncotermsAndLocation incotermsAndLocation;

	@NonNull List<SourceOrderLine> lines;
	@NonNull List<SourceOrderCost> orderCosts;

	public List<SourceOrderCost> getOrderCostsByLineId(@NonNull final OrderLineId orderLineId)
	{
		return orderCosts.stream()
				.filter(orderCost -> orderCost.isBasedOnOrderLineId(orderLineId))
				.collect(ImmutableList.toImmutableList());
	}

	public Amount getEstimatedOrderCostAmountFC()
	{
		return orderCosts.stream()
				.map(SourceOrderCost::getCostAmountFC)
				.reduce(Amount::add)
				.orElseGet(() -> Amount.zero(currencyInfo.getForeignCurrencyCode()));
	}

	public Amount getCOGS_LC()
	{
		return lines.stream()
				.map(SourceOrderLine::getCogsLC)
				.filter(Objects::nonNull)
				.reduce(Amount::add)
				.orElseGet(() -> Amount.zero(currencyInfo.getLocalCurrencyCode()));
	}

	public Amount getInvoicedCostsFC()
	{
		return lines.stream()
				.filter(this::isLineCreatedFromOrderCosts)
				.map(SourceOrderLine::getLineNetAmtFC)
				.reduce(Amount::add)
				.orElseGet(() -> Amount.zero(currencyInfo.getForeignCurrencyCode()));
	}

	private boolean isLineCreatedFromOrderCosts(final SourceOrderLine line)
	{
		return orderCosts.stream().anyMatch(orderCost -> OrderLineId.equals(orderCost.getCreatedOrderLineId(), line.getId()));
	}
}
