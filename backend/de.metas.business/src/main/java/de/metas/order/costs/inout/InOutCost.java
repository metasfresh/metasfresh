package de.metas.order.costs.inout;

import de.metas.bpartner.BPartnerId;
import de.metas.costing.CostElementId;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.OrderCostDetailId;
import de.metas.order.costs.OrderCostId;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

@Getter
@EqualsAndHashCode
@ToString
public class InOutCost
{
	@NonNull private final InOutCostId id;
	@Setter @Nullable InOutCostId reversalId;

	@NonNull private final OrgId orgId;
	@NonNull private final OrderCostDetailId orderCostDetailId;
	@NonNull private final OrderAndLineId orderAndLineId;
	@NonNull private final SOTrx soTrx;
	@NonNull private final InOutAndLineId inoutAndLineId;

	@Nullable private final BPartnerId bpartnerId;
	@NonNull private final OrderCostTypeId costTypeId;
	@NonNull private final CostElementId costElementId;

	@NonNull private final Quantity qty;
	@NonNull private final Money costAmount;
	@NonNull private Money costAmountInvoiced;
	private boolean isInvoiced;

	@Builder
	private InOutCost(
			@NonNull final InOutCostId id,
			@Nullable final InOutCostId reversalId,
			@NonNull final OrgId orgId,
			@NonNull final OrderCostDetailId orderCostDetailId,
			@NonNull final OrderAndLineId orderAndLineId,
			@NonNull final SOTrx soTrx,
			@NonNull final InOutAndLineId inoutAndLineId,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final OrderCostTypeId costTypeId,
			@NonNull final CostElementId costElementId,
			@NonNull final Quantity qty,
			@NonNull final Money costAmount,
			@NonNull final Money costAmountInvoiced,
			boolean isInvoiced)
	{
		this.soTrx = soTrx;
		Money.assertSameCurrency(costAmount, costAmountInvoiced);

		this.id = id;
		this.reversalId = reversalId;
		this.orgId = orgId;
		this.orderCostDetailId = orderCostDetailId;
		this.orderAndLineId = orderAndLineId;
		this.inoutAndLineId = inoutAndLineId;
		this.bpartnerId = bpartnerId;
		this.costTypeId = costTypeId;
		this.costElementId = costElementId;
		this.qty = qty;
		this.costAmount = costAmount;
		this.costAmountInvoiced = costAmountInvoiced;
		this.isInvoiced = isInvoiced;
	}

	public OrderCostId getOrderCostId() {return orderCostDetailId.getOrderCostId();}

	public OrderId getOrderId() {return orderAndLineId.getOrderId();}

	public OrderLineId getOrderLineId() {return orderAndLineId.getOrderLineId();}

	public InOutId getInOutId() {return inoutAndLineId.getInOutId();}

	public InOutLineId getInOutLineId() {return inoutAndLineId.getInOutLineId();}

	public void addCostAmountInvoiced(@NonNull final Money amtToAdd)
	{
		this.costAmountInvoiced = this.costAmountInvoiced.add(amtToAdd);
		this.isInvoiced = this.costAmount.isEqualByComparingTo(this.costAmountInvoiced);
	}

	public Money getCostAmountToInvoice()
	{
		return costAmount.subtract(costAmountInvoiced);
	}

	public Money getCostAmountForQty(final Quantity qty, CurrencyPrecision precision)
	{
		if (qty.equalsIgnoreSource(this.qty))
		{
			return costAmount;
		}
		else if (qty.isZero())
		{
			return costAmount.toZero();
		}
		else
		{
			final Money price = costAmount.divide(this.qty.toBigDecimal(), CurrencyPrecision.ofInt(12));
			return price.multiply(qty.toBigDecimal()).round(precision);
		}
	}
}
