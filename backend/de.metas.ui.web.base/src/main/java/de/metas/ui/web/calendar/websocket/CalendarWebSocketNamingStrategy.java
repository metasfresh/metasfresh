package de.metas.ui.web.calendar.websocket;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.websocket.WebsocketTopicName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode
@ToString(of = "prefix")
public final class CalendarWebSocketNamingStrategy
{
	public static final CalendarWebSocketNamingStrategy DEFAULT = new CalendarWebSocketNamingStrategy(MetasfreshRestAPIConstants.WEBSOCKET_ENDPOINT_V2 + "/calendar");

	@Getter
	private final String prefix;
	private final String prefixAndSlash;

	private static final String NO_SIMULATION_ID_STRING = "actual";

	public CalendarWebSocketNamingStrategy(@NonNull final String prefix)
	{
		this.prefix = StringUtils.trimBlankToNull(prefix);
		if (this.prefix == null)
		{
			throw new AdempiereException("Invalid prefix: " + prefix);
		}

		this.prefixAndSlash = prefix + "/";
	}

	public boolean matches(final WebsocketTopicName topicName)
	{
		return topicName != null && topicName.startsWith(prefixAndSlash);
	}

	public ParsedCalendarWebsocketTopicName parse(@NonNull final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();
		if (!topicNameString.startsWith(prefixAndSlash))
		{
			throw new AdempiereException("Invalid topic: " + topicName);
		}

		try
		{
			final String[] parts = topicNameString.substring(prefixAndSlash.length())
					.split("/");

			return ParsedCalendarWebsocketTopicName.builder()
					.simulationId(parts.length >= 1 ? parseSimulationIdFromString(parts[0]) : null)
					.adLanguage(parts.length >= 2 ? StringUtils.trimBlankToNull(parts[1]) : null)
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid topic: " + topicName, ex);
		}
	}

	@Nullable
	private static SimulationPlanId parseSimulationIdFromString(final String simulationIdStr)
	{
		if (Check.isBlank(simulationIdStr))
		{
			return null;
		}
		else if (NO_SIMULATION_ID_STRING.equals(simulationIdStr))
		{
			return null;
		}
		else
		{
			return SimulationPlanId.ofObject(simulationIdStr);
		}
	}

}
