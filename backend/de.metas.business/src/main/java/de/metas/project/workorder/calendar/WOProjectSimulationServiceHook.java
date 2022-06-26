package de.metas.project.workorder.calendar;

import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.calendar.simulation.CalendarSimulationRef;
import de.metas.calendar.simulation.CalendarSimulationServiceHook;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class WOProjectSimulationServiceHook implements CalendarSimulationServiceHook
{
	private final WOProjectSimulationService woProjectSimulationService;

	public WOProjectSimulationServiceHook(@NonNull final WOProjectSimulationService woProjectSimulationService) {this.woProjectSimulationService = woProjectSimulationService;}

	@Override
	public void onNewSimulation(
			final @NonNull CalendarSimulationRef simulationRef,
			final @Nullable CalendarSimulationId copyFromSimulationId)
	{
		if (copyFromSimulationId != null)
		{
			woProjectSimulationService.copySimulationDataTo(copyFromSimulationId, simulationRef.getId());
		}
	}
}
