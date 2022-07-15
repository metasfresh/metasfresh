package de.metas.calendar.conflicts;

import de.metas.calendar.simulation.SimulationPlanId;

import javax.annotation.Nullable;

public interface CalendarConflictsService
{
	CalendarEntryConflicts query(@Nullable final SimulationPlanId simulationId);
}
