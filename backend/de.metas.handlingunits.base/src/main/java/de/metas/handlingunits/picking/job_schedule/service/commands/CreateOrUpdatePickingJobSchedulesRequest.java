package de.metas.handlingunits.picking.job_schedule.service.commands;

import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CreateOrUpdatePickingJobSchedulesRequest
{
	@NonNull ShipmentScheduleAndJobScheduleIdSet shipmentScheduleAndJobScheduleIds;
	@NonNull WorkplaceId workplaceId;
	@NonNull BigDecimal qtyToPickBD;
}
