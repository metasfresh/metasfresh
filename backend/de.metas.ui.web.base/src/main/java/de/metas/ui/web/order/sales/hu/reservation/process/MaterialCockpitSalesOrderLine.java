package de.metas.ui.web.order.sales.hu.reservation.process;

import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.order.OrderAndLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class MaterialCockpitSalesOrderLine
{
	@NonNull OrderAndLineId id;
	@NonNull QtyTU qtyOrderedTU;

	public static MaterialCockpitSalesOrderLine of(final I_C_OrderLine orderLineRecord)
	{
		return MaterialCockpitSalesOrderLine.builder()
				.id(OrderAndLineId.ofRepoIds(orderLineRecord.getC_Order_ID(), orderLineRecord.getC_OrderLine_ID()))
				.qtyOrderedTU(QtyTU.ofBigDecimal(orderLineRecord.getQtyEnteredTU()))
				.build();
	}

}
