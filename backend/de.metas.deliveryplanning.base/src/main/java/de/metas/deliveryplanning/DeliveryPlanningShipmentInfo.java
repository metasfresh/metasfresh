package de.metas.deliveryplanning;

import de.metas.bpartner.BPartnerId;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.util.ColorId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class DeliveryPlanningShipmentInfo
{
	@NonNull private final DeliveryPlanningId deliveryPlanningId;
	@Nullable private final OrderAndLineId salesOrderAndLineId;
	@NonNull private final ShipmentScheduleId shipmentScheduleId;
	@NonNull private final BPartnerId customerId;
	@Nullable private InOutId shipmentId;

	@Nullable private ColorId shippedStatusColorId;

	@Nullable
	public OrderId getSalesOrderId() {return salesOrderAndLineId != null ? salesOrderAndLineId.getOrderId() : null;}

	public boolean isShipped()
	{
		return getShipmentId() != null;
	}

	public void updateShippedStatusColor(@NonNull final DeliveryStatusColorPalette colorPalette)
	{
		setShippedStatusColorId(isShipped() ? colorPalette.getDeliveredColorId() : colorPalette.getNotDeliveredColorId());
	}

}
