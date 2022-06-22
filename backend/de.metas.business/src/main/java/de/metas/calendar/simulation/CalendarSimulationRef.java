package de.metas.calendar.simulation;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class CalendarSimulationRef
{
	@NonNull CalendarSimulationId id;
	@NonNull String name;
	boolean processed;

	@NonNull Instant created;
	@NonNull UserId createdByUserId;
}
