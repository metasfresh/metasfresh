package de.metas.order.costs;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode
@ToString
@Builder
public class OrderCostDetail
{
	@Getter @NonNull private final OrderLineId orderLineId;

	@Getter @NonNull private final ProductId productId;

	@Getter @NonNull private final Quantity qtyOrdered;

	@Getter @NonNull private final Money orderLineNetAmt;

	@Getter private Money costAmount;

	public OrderCostDetail(
			@NonNull final OrderLineId orderLineId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyOrdered,
			@NonNull final Money orderLineNetAmt,
			@Nullable final Money costAmount)
	{
		Money.assertSameCurrency(orderLineNetAmt, costAmount);

		this.orderLineId = orderLineId;
		this.productId = productId;
		this.qtyOrdered = qtyOrdered;
		this.orderLineNetAmt = orderLineNetAmt;
		this.costAmount = costAmount != null ? costAmount : orderLineNetAmt.toZero();
	}

	public CurrencyId getCurrencyId()
	{
		return orderLineNetAmt.getCurrencyId();
	}

	public UomId getUomId()
	{
		return qtyOrdered.getUomId();
	}

	void setCostAmount(@NonNull final Money costAmountNew)
	{
		Money.assertSameCurrency(this.orderLineNetAmt, costAmountNew);
		this.costAmount = costAmountNew;
	}
}
