package de.metas.calendar.plan_optimizer;

import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;

public interface SimulationOptimizerStatusListener
{
	void onSimulationOptimizerStarted(@NonNull SimulationPlanId simulationId);

	void onSimulationOptimizerProgress(@NonNull Plan solution);

	void onSimulationOptimizerStopped(@NonNull SimulationPlanId simulationId);
}
