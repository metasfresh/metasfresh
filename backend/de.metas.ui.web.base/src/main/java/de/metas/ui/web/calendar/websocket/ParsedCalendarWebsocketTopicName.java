package de.metas.ui.web.calendar.websocket;

import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.time.ZoneId;

@Value
@Builder
public class ParsedCalendarWebsocketTopicName
{
	@Nullable SimulationPlanId simulationId;
	@NotNull String adLanguage;
}
