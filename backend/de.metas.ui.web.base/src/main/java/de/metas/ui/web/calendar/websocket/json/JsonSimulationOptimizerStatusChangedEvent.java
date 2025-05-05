package de.metas.ui.web.calendar.websocket.json;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.ui.web.calendar.json.JsonSimulationOptimizerStatusType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonSimulationOptimizerStatusChangedEvent implements JsonWebsocketEvent
{
	@NonNull String type = "simulationOptimizerStatusChanged";

	@NonNull SimulationPlanId simulationId;
	@NonNull JsonSimulationOptimizerStatusType status;
	boolean simulationPlanChanged;

	//
	// Troubleshoot info:
	@Nullable String score;
	@Nullable String scoreExplanation;
	@Nullable Boolean isFinalSolution;
	@Nullable String timeSpent;
}
