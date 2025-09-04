package de.metas.handlingunits.picking.job_schedule.model;

import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value(staticConstructor = "of")
public class ShipmentScheduleAndJobScheduleId
{
	@NonNull ShipmentScheduleId shipmentScheduleId;
	@Nullable PickingJobScheduleId jobScheduleId;
}
