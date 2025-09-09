package de.metas.handlingunits.picking.job_schedule.model;

import de.metas.inout.ShipmentScheduleId;
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
}
