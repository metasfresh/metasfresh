package de.metas.ui.web.calendar.websocket;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.util.InSetPredicate;
import de.metas.websocket.WebsocketTopicName;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class ParsedCalendarWebsocketTopicName
{
	@NonNull WebsocketTopicName topicName;

	@Nullable SimulationPlanId simulationId;
	@NotNull String adLanguage;
	@NonNull ImmutableSet<CalendarResourceId> onlyResourceIds;
	@Nullable ProjectId onlyProjectId;

	public InSetPredicate<CalendarResourceId> getResourceIdsPredicate()
	{
		return !onlyResourceIds.isEmpty()
				? InSetPredicate.only(onlyResourceIds)
				: InSetPredicate.any();
	}
}
