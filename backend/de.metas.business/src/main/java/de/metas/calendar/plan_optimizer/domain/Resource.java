package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceWeeklyAvailability;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class Resource
{
	@PlanningId @NonNull ResourceId id;
	@NonNull String name;

	@NonNull @Builder.Default ResourceWeeklyAvailability availability = ResourceWeeklyAvailability.ALWAYS_AVAILABLE;

	@NonNull @Builder.Default ResourceWeeklyAvailability humanResourceAvailability = ResourceWeeklyAvailability.ALWAYS_AVAILABLE;
	@Nullable HumanResourceCapacity humanResourceCapacity;

	@Override
	public String toString() {return name;}
}
