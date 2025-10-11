package de.metas.picking.job_schedule.model;

import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.quantity.Quantity;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PickingJobSchedule
{
	@NonNull PickingJobScheduleId id;
	@NonNull ShipmentScheduleId shipmentScheduleId;
	@NonNull WorkplaceId workplaceId;
	@NonNull Quantity qtyToPick;
	boolean processed;

	public ShipmentScheduleAndJobScheduleId getShipmentScheduleAndJobScheduleId()
	{
		return ShipmentScheduleAndJobScheduleId.of(shipmentScheduleId, id);
	}
}
