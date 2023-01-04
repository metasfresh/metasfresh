package de.metas.ui.web.calendar.websocket;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.i18n.Language;
import de.metas.project.ProjectId;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.websocket.WebsocketTopicName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Map;

@EqualsAndHashCode
public final class CalendarWebSocketNamingStrategy
{
	public static final CalendarWebSocketNamingStrategy DEFAULT = new CalendarWebSocketNamingStrategy(MetasfreshRestAPIConstants.WEBSOCKET_ENDPOINT_V2 + "/calendar");

	@Getter
	private final String topicNameWithoutParams;

	private CalendarWebSocketNamingStrategy(@NonNull final String topicNameWithoutParams)
	{
		this.topicNameWithoutParams = StringUtils.trimBlankToNull(topicNameWithoutParams);
		if (this.topicNameWithoutParams == null)
		{
			throw new AdempiereException("Invalid prefix: " + topicNameWithoutParams);
		}
	}

	public ParsedCalendarWebsocketTopicName parse(@NonNull final WebsocketTopicName topicName)
	{
		if (!topicName.startsWith(topicNameWithoutParams))
		{
			throw new AdempiereException("Invalid topic: " + topicName);
		}

		try
		{
			final Map<String, String> params = parseQueryParams(topicName);

			return ParsedCalendarWebsocketTopicName.builder()
					.topicName(topicName)
					.simulationId(StringUtils.trimBlankToOptional(params.get("simulationId")).map(SimulationPlanId::ofObject).orElse(null))
					.adLanguage(StringUtils.trimBlankToOptional(params.get("adLanguage")).orElseGet(Language::getBaseAD_Language))
					.onlyResourceIds(CalendarResourceId.ofCommaSeparatedString(params.get("onlyResourceIds")).orElseGet(ImmutableSet::of))
					.onlyProjectId(StringUtils.trimBlankToOptional((params.get("onlyProjectId"))).map(ProjectId::ofObject).orElse(null))
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid topic: " + topicName, ex);
		}
	}

	private static Map<String, String> parseQueryParams(final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();
		final int idx = topicNameString.indexOf("?");
		return idx > 0
				? StringUtils.parseURLQueryString(topicNameString.substring(idx + 1))
				: ImmutableMap.of();
	}
}
