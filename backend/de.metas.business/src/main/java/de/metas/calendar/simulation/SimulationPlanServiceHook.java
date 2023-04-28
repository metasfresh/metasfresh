package de.metas.calendar.simulation;

import lombok.NonNull;

import javax.annotation.Nullable;

public interface SimulationPlanServiceHook
{
	void onNewSimulationPlan(@NonNull SimulationPlanRef simulationRef, @Nullable SimulationPlanId copyFromSimulationPlanId);

	void onComplete(@NonNull SimulationPlanRef simulationRef);
}
