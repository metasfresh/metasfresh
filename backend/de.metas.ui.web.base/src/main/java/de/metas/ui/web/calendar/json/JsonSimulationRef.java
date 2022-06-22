package de.metas.ui.web.calendar.json;

import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.calendar.simulation.CalendarSimulationRef;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonSimulationRef
{
	@NonNull CalendarSimulationId id;
	@NonNull String name;
	boolean processed;

	public static JsonSimulationRef of(@NonNull final CalendarSimulationRef simulationRef)
	{
		return builder()
				.id(simulationRef.getId())
				.name(simulationRef.getName())
				.processed(simulationRef.isProcessed())
				.build();
	}
}
