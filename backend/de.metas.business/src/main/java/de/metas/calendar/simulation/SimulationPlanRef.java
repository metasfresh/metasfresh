package de.metas.calendar.simulation;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class SimulationPlanRef
{
	@NonNull SimulationPlanId id;
	@NonNull String name;
	@NonNull UserId responsibleUserId;
	boolean processed;

	@NonNull Instant created;
}
