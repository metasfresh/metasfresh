package de.metas.calendar.conflicts;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class CalendarConflictChangesEvent
{
	@Nullable SimulationPlanId simulationId;

	@NonNull ImmutableSet<CalendarEntryId> affectedEntryIds;
	@NonNull CalendarEntryConflicts conflicts;
}
