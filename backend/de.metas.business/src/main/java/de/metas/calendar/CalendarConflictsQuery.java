package de.metas.calendar;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class CalendarConflictsQuery
{
	@Nullable SimulationPlanId simulationId;
	@NonNull @Builder.Default InSetPredicate<CalendarResourceId> resourceIds = InSetPredicate.any();
}
