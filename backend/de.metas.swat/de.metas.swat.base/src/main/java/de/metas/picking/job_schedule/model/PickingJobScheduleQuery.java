package de.metas.picking.job_schedule.model;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingJobScheduleQuery
{
	private static final PickingJobScheduleQuery ANY = builder().build();

	@NonNull @Singular ImmutableSet<WorkplaceId> workplaceIds;
	@NonNull @Singular ImmutableSet<PickingJobScheduleId> excludeJobScheduleIds;
	@NonNull @Singular ImmutableSet<ShipmentScheduleId> onlyShipmentScheduleIds;
	@Nullable Boolean isProcessed;

	public boolean isAny()
	{
		return this.equals(ANY);
	}
}
