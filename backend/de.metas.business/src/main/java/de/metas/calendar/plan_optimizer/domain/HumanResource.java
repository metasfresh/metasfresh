package de.metas.calendar.plan_optimizer.domain;

import de.metas.resource.ResourceWeeklyAvailability;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HumanResource
{
	@NonNull HumanResourceId id;
	@NonNull @Builder.Default ResourceWeeklyAvailability availability = ResourceWeeklyAvailability.ALWAYS_AVAILABLE;
}
