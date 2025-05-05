package de.metas.requisition.order_aggregation;

import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MOrderLine;

@Value
@Builder
class OrderLineAggregator
{
	@NonNull OrderLineKey key;
	@NonNull MOrderLine orderLine;

	public OrderLineId getOrderLineId() {return OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID());}

	public boolean isMatching(@NonNull final OrderLineKey key) {return this.key.equals(key);}

	public void addQty(@NonNull final Quantity qty)
	{
		final Quantity qtyEntered = getQtyEntered();
		final Quantity qtyEnteredNew = !qtyEntered.isZero()
				? qtyEntered.add(qty)
				: qty;

		setQtyEntered(qtyEnteredNew);
	}

	private Quantity getQtyEntered() {return Quantitys.of(orderLine.getQtyEntered(), UomId.ofRepoId(orderLine.getC_UOM_ID()));}

	private void setQtyEntered(Quantity qtyEntered)
	{
		orderLine.setQtyEntered(qtyEntered.toBigDecimal());
		orderLine.setC_UOM_ID(qtyEntered.getUomId().getRepoId());
	}

	public void save()
	{
		InterfaceWrapperHelper.saveRecord(orderLine);
	}
}
