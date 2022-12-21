package de.metas.ui.web.calendar.json;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonSimulationRef
{
	@NonNull SimulationPlanId id;
	@NonNull String name;
	boolean processed;
	boolean isMainSimulation;

	public static JsonSimulationRef of(@NonNull final SimulationPlanRef simulationRef)
	{
		return builder()
				.id(simulationRef.getId())
				.name(simulationRef.getName())
				.processed(simulationRef.isProcessed())
				.isMainSimulation(simulationRef.isMainSimulation())
				.build();
	}
}
