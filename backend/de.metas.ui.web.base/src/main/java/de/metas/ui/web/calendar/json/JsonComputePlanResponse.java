package de.metas.ui.web.calendar.json;

import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonComputePlanResponse
{
	@NonNull SimulationPlanId simulationId;
	@NonNull String status; // TODO convert it to enum
}
