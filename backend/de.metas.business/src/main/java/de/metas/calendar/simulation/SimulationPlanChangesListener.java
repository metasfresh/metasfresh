package de.metas.calendar.simulation;

import lombok.NonNull;

public interface SimulationPlanChangesListener
{
	void onSimulationPlanAfterComplete(@NonNull SimulationPlanRef simulationRef);
}
