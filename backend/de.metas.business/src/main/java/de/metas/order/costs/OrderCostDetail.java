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

	@Getter @NonNull private OrderCostDetailOrderLinePart orderLineInfo;
	@Getter private Money costAmount;
	@Getter @NonNull Quantity inoutQty;
	@Getter @NonNull Money inoutCostAmount;

	@Builder(toBuilder = true)
	private OrderCostDetail(
			@Nullable final OrderCostDetailId id,
			@NonNull final OrderCostDetailOrderLinePart orderLineInfo,
			@Nullable final Money costAmount,
			@Nullable final Quantity inoutQty,
			@Nullable Money inoutCostAmount)
	{
		final Money orderLineNetAmt = orderLineInfo.getOrderLineNetAmt();
		final Quantity qtyOrdered = orderLineInfo.getQtyOrdered();

		Money.assertSameCurrency(orderLineNetAmt, costAmount, inoutCostAmount);
		Quantity.assertSameUOM(qtyOrdered, inoutQty);

		this.id = id;
		this.orderLineInfo = orderLineInfo;
		this.costAmount = costAmount != null ? costAmount : orderLineNetAmt.toZero();
		this.inoutQty = inoutQty != null ? inoutQty : qtyOrdered.toZero();
		this.inoutCostAmount = inoutCostAmount != null ? inoutCostAmount : orderLineNetAmt.toZero();
	}

	public OrderLineId getOrderLineId() {return orderLineInfo.getOrderLineId();}

	public Money getOrderLineNetAmt() {return orderLineInfo.getOrderLineNetAmt();}

	public Quantity getQtyOrdered() {return orderLineInfo.getQtyOrdered();}

	public ProductId getProductId() {return orderLineInfo.getProductId();}

	public CurrencyId getCurrencyId()
	{
		return orderLineInfo.getCurrencyId();
	}

	public UomId getUomId()
	{
		return orderLineInfo.getUomId();
	}

	public void setOrderLineInfo(@NonNull final OrderCostDetailOrderLinePart orderLineInfo)
	{
		this.costAmount.assertCurrencyId(orderLineInfo.getCurrencyId());
		Quantity.assertSameUOM(this.inoutQty, orderLineInfo.getQtyOrdered());
		
		this.orderLineInfo = orderLineInfo;
	}

	void setCostAmount(@NonNull final Money costAmountNew)
	{
		costAmountNew.assertCurrencyId(orderLineInfo.getCurrencyId());
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
				.orderLineInfo(orderLineInfo.withOrderLineId(mapper.getTargetOrderLineId(orderLineInfo.getOrderLineId())))
				.build();
	}
}
