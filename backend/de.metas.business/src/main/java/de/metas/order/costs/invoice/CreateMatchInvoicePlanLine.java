package de.metas.order.costs.invoice;

import de.metas.invoice.matchinv.MatchInvCostPart;
import de.metas.money.Money;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.I_M_InOutLine;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class CreateMatchInvoicePlanLine
{
	@NonNull private final I_M_InOutLine inoutLine;
	@NonNull private MatchInvCostPart inoutCost;
	@NonNull private final StockQtyAndUOMQty qty;

	public Money getCostAmountInOut() {return inoutCost.getCostAmountInOut();}

	public Money getCostAmountInvoiced() {return inoutCost.getCostAmountInvoiced();}

	public void setCostAmountInvoiced(@NonNull final Money costAmountInvoiced)
	{
		this.inoutCost = this.inoutCost.withCostAmountInvoiced(costAmountInvoiced);
	}

	public Money getInvoicedAmountDiff() {return this.inoutCost.getInvoicedAmountDiff();}

}
