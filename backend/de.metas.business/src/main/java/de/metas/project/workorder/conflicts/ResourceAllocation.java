package de.metas.project.workorder.conflicts;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResourceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
class ResourceAllocation
{
	@NonNull ResourceIdAndType resourceId;
	@NonNull WOProjectResourceId projectResourceId;
	@Nullable SimulationPlanId appliedSimulationId;
	@NonNull CalendarDateRange dateRange;

	public boolean isInConflictWith(@NonNull final ResourceAllocation other)
	{
		return ResourceIdAndType.equals(this.resourceId, other.resourceId)
				&& this.dateRange.isOverlappingWith(other.dateRange);
	}
}
