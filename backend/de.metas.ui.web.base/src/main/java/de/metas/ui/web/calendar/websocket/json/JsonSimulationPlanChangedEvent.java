package de.metas.ui.web.calendar.websocket.json;

import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonSimulationPlanChangedEvent implements JsonWebsocketEvent
{
	@NonNull String type = "simulationPlanChanged";

	@NonNull SimulationPlanId simulationId;

	boolean processed;
}
