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
	@NonNull private final I_M_InOutLine receiptLine;
	@NonNull private MatchInvCostPart receiptCost;
	@NonNull private final StockQtyAndUOMQty receiptQty;

	public Money getCostAmountReceived() {return receiptCost.getCostAmountReceived();}

	public Money getCostAmountInvoiced() {return receiptCost.getCostAmountInvoiced();}

	public void setCostAmountInvoiced(@NonNull final Money costAmountInvoiced)
	{
		this.receiptCost = this.receiptCost.withCostAmountInvoiced(costAmountInvoiced);
	}

	public Money getInvoicedAmountDiff() {return this.receiptCost.getInvoicedAmountDiff();}

}
