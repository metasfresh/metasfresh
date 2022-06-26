package de.metas.calendar.simulation;

import lombok.NonNull;

import javax.annotation.Nullable;

public interface CalendarSimulationServiceHook
{
	void onNewSimulation(@NonNull CalendarSimulationRef simulationRef, @Nullable CalendarSimulationId copyFromSimulationId);
}
