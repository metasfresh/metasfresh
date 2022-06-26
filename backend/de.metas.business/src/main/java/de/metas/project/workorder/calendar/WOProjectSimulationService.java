package de.metas.project.workorder.calendar;

import de.metas.calendar.simulation.CalendarSimulationId;
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

	public WOProjectSimulationPlan getSimulationPlanById(final CalendarSimulationId simulationId)
	{
		return woProjectSimulationRepository.getSimulationPlanById(simulationId);
	}

	public void savePlan(final WOProjectSimulationPlan plan)
	{
		woProjectSimulationRepository.changeSimulationPlan(plan);
	}

	public void copySimulationDataTo(
			@NonNull final CalendarSimulationId fromSimulationId,
			@NonNull final CalendarSimulationId toSimulationId)
	{
		woProjectSimulationRepository.copySimulationDataTo(fromSimulationId, toSimulationId);
	}
}
