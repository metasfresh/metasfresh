package de.metas.ui.web.calendar.json;

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
public class JsonWebsocketEvent
{
	public enum Type
	{
		addOrChange, remove
	}

	@NonNull Type type;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable JsonCalendarEntry entry;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable CalendarEntryId entryId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable SimulationPlanId simulationId;
}
