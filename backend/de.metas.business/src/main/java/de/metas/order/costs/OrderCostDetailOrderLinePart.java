package de.metas.order.costs;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.compiere.model.I_C_OrderLine;

@Value
@Builder
public class OrderCostDetailOrderLinePart
{
	@With @NonNull OrderLineId orderLineId;
	@NonNull ProductId productId;
	@NonNull Quantity qtyOrdered;
	@NonNull Money orderLineNetAmt;

	public static OrderCostDetailOrderLinePart ofOrderLine(final I_C_OrderLine orderLine)
	{
		return builder()
				.orderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.orderLineNetAmt(IOrderBL.extractLineNetAmt(orderLine))
				.qtyOrdered(IOrderBL.extractQtyEntered(orderLine))
				.build();
	}

	public CurrencyId getCurrencyId()
	{
		return orderLineNetAmt.getCurrencyId();
	}

	public UomId getUomId()
	{
		return qtyOrdered.getUomId();
	}
}
