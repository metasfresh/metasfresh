package de.metas.order.costs;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode
@ToString
public class OrderCostDetail
{
	@Setter(AccessLevel.PACKAGE)
	@Getter @Nullable OrderCostDetailId id;

	@Getter @NonNull private final OrderLineId orderLineId;
	@Getter @NonNull private final ProductId productId;
	@Getter @NonNull private final Quantity qtyOrdered;
	@Getter @NonNull private final Money orderLineNetAmt;
	@Getter private Money costAmount;

	@Getter @NonNull Quantity inoutQty;
	@Getter @NonNull Money inoutCostAmount;

	@Builder(toBuilder = true)
	private OrderCostDetail(
			@Nullable final OrderCostDetailId id,
			@NonNull final OrderLineId orderLineId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyOrdered,
			@NonNull final Money orderLineNetAmt,
			@Nullable final Money costAmount,
			@Nullable final Quantity inoutQty,
			@Nullable Money inoutCostAmount)
	{
		Money.assertSameCurrency(orderLineNetAmt, costAmount, inoutCostAmount);
		Quantity.assertSameUOM(qtyOrdered, inoutQty);

		this.id = id;
		this.orderLineId = orderLineId;
		this.productId = productId;
		this.qtyOrdered = qtyOrdered;
		this.orderLineNetAmt = orderLineNetAmt;
		this.costAmount = costAmount != null ? costAmount : orderLineNetAmt.toZero();
		this.inoutQty = inoutQty != null ? inoutQty : qtyOrdered.toZero();
		this.inoutCostAmount = inoutCostAmount != null ? inoutCostAmount : orderLineNetAmt.toZero();
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

	void addInOutCost(@NonNull Money amt, @NonNull Quantity qty)
	{
		this.inoutCostAmount = this.inoutCostAmount.add(amt);
		this.inoutQty = this.inoutQty.add(qty);
	}

	public OrderCostDetail copy(@NonNull final OrderCostCloneMapper mapper)
	{
		return toBuilder()
				.id(null)
				.orderLineId(mapper.getTargetOrderLineId(orderLineId))
				.build();
	}
}
