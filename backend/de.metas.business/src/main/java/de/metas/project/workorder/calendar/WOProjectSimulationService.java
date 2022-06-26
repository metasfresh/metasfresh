package de.metas.project.workorder.calendar;

import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class WOProjectSimulationService
{
	@NonNull private final WOProjectSimulationRepository woProjectSimulationRepository;

	public WOProjectSimulationService(
			final @NonNull WOProjectSimulationRepository woProjectSimulationRepository)
	{
		this.woProjectSimulationRepository = woProjectSimulationRepository;
	}

	public WOProjectSimulationPlan getSimulationPlanById(final SimulationPlanId simulationId)
	{
		return woProjectSimulationRepository.getSimulationPlanById(simulationId);
	}

	public void savePlan(final WOProjectSimulationPlan plan)
	{
		woProjectSimulationRepository.savePlan(plan);
	}

	public void copySimulationDataTo(
			@NonNull final SimulationPlanId fromSimulationId,
			@NonNull final SimulationPlanId toSimulationId)
	{
		woProjectSimulationRepository.copySimulationDataTo(fromSimulationId, toSimulationId);
	}
}
