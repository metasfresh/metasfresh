package de.metas.ui.web.calendar.websocket;

import de.metas.calendar.MultiCalendarService;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducerFactory;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CalendarWebSocketProducerFactory implements WebSocketProducerFactory
{
	private static final CalendarWebSocketNamingStrategy NAMING_STRATEGY = CalendarWebSocketNamingStrategy.DEFAULT;

	private final MultiCalendarService multiCalendarService;

	public CalendarWebSocketProducerFactory(
			@NonNull final MultiCalendarService multiCalendarService)
	{
		this.multiCalendarService = multiCalendarService;
	}

	@Override
	public String getTopicNamePrefix()
	{
		return NAMING_STRATEGY.getPrefix();
	}

	@Override
	public CalendarWebSocketProducer createProducer(final WebsocketTopicName topicName)
	{
		final ParsedCalendarWebsocketTopicName calendarTopicName = NAMING_STRATEGY.parse(topicName);
		return new CalendarWebSocketProducer(
				multiCalendarService,
				topicName,
				calendarTopicName.getSimulationId(),
				calendarTopicName.getAdLanguage());
	}
}
