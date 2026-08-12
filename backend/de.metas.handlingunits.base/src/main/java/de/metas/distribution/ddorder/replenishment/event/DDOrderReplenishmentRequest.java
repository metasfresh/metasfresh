package de.metas.distribution.ddorder.replenishment.event;

import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DDOrderReplenishmentRequest
{
	@NonNull PickingJobScheduleId pickingJobScheduleId;
	@NonNull ClientAndOrgId clientAndOrgId;

	public static DDOrderReplenishmentRequest of(@NonNull final PickingJobSchedule pickingJobSchedule)
	{
		return builder()
				.pickingJobScheduleId(pickingJobSchedule.getId())
				.clientAndOrgId(pickingJobSchedule.getClientAndOrgId())
				.build();
	}
}
