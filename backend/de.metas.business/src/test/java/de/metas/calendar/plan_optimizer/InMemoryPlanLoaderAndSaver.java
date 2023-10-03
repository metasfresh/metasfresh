package de.metas.calendar.plan_optimizer;

import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.persistance.PlanLoaderAndSaver;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;

@ToString
class InMemoryPlanLoaderAndSaver implements PlanLoaderAndSaver
{
	private final HashMap<SimulationPlanId, Plan> map = new HashMap<>();

	@Override
	public Plan getPlan(@NonNull final SimulationPlanId simulationId)
	{
		final Plan plan = map.get(simulationId);
		if (plan == null)
		{
			throw new AdempiereException("No plan found for " + simulationId);
		}
		return plan;
	}

	@Override
	public void saveSolution(final Plan solution)
	{
		map.put(solution.getSimulationId(), solution);
	}
}
