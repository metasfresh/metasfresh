package de.metas.ui.web.calendar.json;

import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonSimulationCreateRequest
{
	@Nullable String name;
	@Nullable SimulationPlanId copyFromSimulationId;
}
