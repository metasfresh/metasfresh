package de.metas.deliveryplanning;

import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.ColorId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class DeliveryPlanningShipmentInfo
{
	@NonNull private final ShipmentScheduleId shipmentScheduleId;
	@Nullable private InOutId shipmentId;

	@Nullable private ColorId shippedStatusColorId;

	public boolean isShipped()
	{
		return getShipmentId() != null;
	}
}
