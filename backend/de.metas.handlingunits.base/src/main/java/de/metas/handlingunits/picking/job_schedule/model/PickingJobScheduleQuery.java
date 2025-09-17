package de.metas.handlingunits.picking.job_schedule.model;

import com.google.common.collect.ImmutableSet;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class PickingJobScheduleQuery
{
	public static final PickingJobScheduleQuery ANY = builder().build();

	@NonNull @Singular ImmutableSet<WorkplaceId> workplaceIds;
	@NonNull @Singular ImmutableSet<PickingJobScheduleId> excludeJobScheduleIds;

	public boolean isAny()
	{
		return this.equals(ANY);
	}
}
