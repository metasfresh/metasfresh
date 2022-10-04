package de.metas.ui.web.calendar.websocket.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonRemoveWebsocketEvent implements JsonWebsocketEvent
{
	@NonNull String type = "remove";

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable SimulationPlanId simulationId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable CalendarEntryId entryId;
}
