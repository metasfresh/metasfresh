package de.metas.ui.web.calendar.json;

import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonSimulationOptimizerStatus
{
	@NonNull SimulationPlanId simulationId;
	@NonNull JsonSimulationOptimizerStatusType status;
}
