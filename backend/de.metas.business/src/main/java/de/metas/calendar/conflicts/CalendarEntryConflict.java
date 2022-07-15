package de.metas.calendar.conflicts;

import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class CalendarEntryConflict
{
	@NonNull CalendarEntryId entryId1;
	@NonNull CalendarEntryId entryId2;
	@Nullable SimulationPlanId simulationId;
	@NonNull CalendarEntryConflictStatus status;

	public boolean isConflict() {return status.isConflict();}
}
