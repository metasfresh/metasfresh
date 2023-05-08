package de.metas.calendar.continuous_query;

import de.metas.calendar.CalendarEntryAndSimulationId;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value(staticConstructor = "of")
public class EntryDeletedEvent implements Event
{
	@NonNull CalendarEntryAndSimulationId entryAndSimulationId;

	public CalendarEntryId getEntryId() {return getEntryAndSimulationId().getEntryId();}

	public SimulationPlanId getSimulationId() {return getEntryAndSimulationId().getSimulationId();}

	public EntryDeletedEvent withSimulationId(@Nullable final SimulationPlanId simulationId)
	{
		return !SimulationPlanId.equals(entryAndSimulationId.getSimulationId(), simulationId)
				? of(entryAndSimulationId.withSimulationId(simulationId))
				: this;
	}
}
