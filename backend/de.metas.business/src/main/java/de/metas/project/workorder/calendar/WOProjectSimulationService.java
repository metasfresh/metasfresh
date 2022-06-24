package de.metas.project.workorder.calendar;

import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.project.workorder.WOProjectResourceSimulation;
import de.metas.project.workorder.WOProjectStepSimulation;
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

	public WOProjectResourceSimulation createOrUpdate(final WOProjectResourceSimulation.UpdateRequest request)
	{
		return woProjectSimulationRepository.createOrUpdate(request);
	}

	public void createOrUpdate(final WOProjectStepSimulation.UpdateRequest request)
	{
		woProjectSimulationRepository.createOrUpdate(request);
	}

	public void savePlan(final WOProjectSimulationPlan plan)
	{
		woProjectSimulationRepository.changeSimulationPlan(plan);
	}
}
