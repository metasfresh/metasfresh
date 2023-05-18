package de.metas.calendar.plan_optimizer.persistance;

import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;

public interface PlanLoaderAndSaver
{
	Plan getPlan(@NonNull SimulationPlanId simulationId);

	void saveSolution(Plan solution);
}
