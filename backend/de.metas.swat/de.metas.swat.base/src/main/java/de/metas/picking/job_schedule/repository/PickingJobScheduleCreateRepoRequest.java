package de.metas.picking.job_schedule.repository;

import de.metas.inout.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PickingJobScheduleCreateRepoRequest
{
	@NonNull ShipmentScheduleId shipmentScheduleId;
	@NonNull WorkplaceId workplaceId;
	@NonNull Quantity qtyToPick;
}
