package de.metas.calendar;

import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.util.Objects;

@Value(staticConstructor = "of")
public class CalendarEntryAndSimulationId
{
	@NonNull CalendarEntryId entryId;

	@With
	@Nullable SimulationPlanId simulationId;

	public static boolean equals(@Nullable CalendarEntryAndSimulationId id1, @Nullable CalendarEntryAndSimulationId id2)
	{
		return Objects.equals(id1, id2);
	}
}
