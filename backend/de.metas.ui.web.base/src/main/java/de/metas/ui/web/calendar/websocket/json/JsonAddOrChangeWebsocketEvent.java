package de.metas.ui.web.calendar.websocket.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.ui.web.calendar.json.JsonCalendarEntry;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonAddOrChangeWebsocketEvent implements JsonWebsocketEvent
{
	@NonNull String type = "addOrChange";

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable SimulationPlanId simulationId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable JsonCalendarEntry entry;
}
