package de.metas.invoice.matchinv;

import de.metas.costing.CostElementId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.order.costs.inout.InOutCostId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;

@Value
public class MatchInvCostPart
{
	@NonNull InOutCostId inoutCostId;
	@NonNull OrderCostTypeId costTypeId;
	@NonNull CostElementId costElementId;
	@NonNull Money costAmountInOut;
	@With @NonNull Money costAmountInvoiced;

	@Builder
	private MatchInvCostPart(
			@NonNull final InOutCostId inoutCostId,
			@NonNull final OrderCostTypeId costTypeId,
			@NonNull final CostElementId costElementId,
			@NonNull final Money costAmountInOut,
			@Nullable final Money costAmountInvoiced)
	{
		Money.assertSameCurrency(costAmountInOut, costAmountInvoiced);
		this.inoutCostId = inoutCostId;
		this.costTypeId = costTypeId;
		this.costElementId = costElementId;
		this.costAmountInOut = costAmountInOut;
		this.costAmountInvoiced = costAmountInvoiced != null ? costAmountInvoiced : costAmountInOut;
	}

	public CurrencyId getCurrencyId() {return this.costAmountInOut.getCurrencyId();}

	public Money getInvoicedAmountDiff()
	{
		return costAmountInvoiced.subtract(costAmountInOut);
	}
}
