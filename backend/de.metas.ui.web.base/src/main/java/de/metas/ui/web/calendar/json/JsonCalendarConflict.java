package de.metas.ui.web.calendar.json;

import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.conflicts.CalendarEntryConflict;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonCalendarConflict
{
	@NonNull CalendarEntryId entryId1;
	@NonNull CalendarEntryId entryId2;
	@Nullable SimulationPlanId simulationId;
	@NonNull String status;

	public static JsonCalendarConflict of(final CalendarEntryConflict conflict)
	{
		return builder()
				.entryId1(conflict.getEntryId1())
				.entryId2(conflict.getEntryId2())
				.simulationId(conflict.getSimulationId())
				.status(conflict.getStatus().toJson())
				.build();
	}
}
