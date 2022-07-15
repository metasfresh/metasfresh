package de.metas.ui.web.calendar.websocket.json;

import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.ui.web.calendar.json.JsonCalendarConflict;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonConflictsChangedWebsocketEvent implements JsonWebsocketEvent
{
	@NonNull String type = "conflictsChanged";

	@Nullable SimulationPlanId simulationId;
	@NonNull Set<CalendarEntryId> affectedEntryIds;
	@NonNull List<JsonCalendarConflict> conflicts;
}
