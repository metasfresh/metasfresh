package de.metas.project.workorder.calendar;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanServiceHook;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class WOProjectSimulationPlanServiceHook implements SimulationPlanServiceHook
{
	private final WOProjectSimulationService woProjectSimulationService;

	public WOProjectSimulationPlanServiceHook(@NonNull final WOProjectSimulationService woProjectSimulationService) {this.woProjectSimulationService = woProjectSimulationService;}

	@Override
	public void onNewSimulationPlan(
			final @NonNull SimulationPlanRef simulationRef,
			final @Nullable SimulationPlanId copyFromSimulationPlanId)
	{
		if (copyFromSimulationPlanId != null)
		{
			woProjectSimulationService.copySimulationDataTo(copyFromSimulationPlanId, simulationRef.getId());
		}
	}

	@Override
	public void onComplete(@NonNull final SimulationPlanRef simulationRef)
	{
		woProjectSimulationService.completeSimulation(simulationRef.getId());
	}
}
