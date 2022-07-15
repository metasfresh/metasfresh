package de.metas.ui.web.calendar.websocket;

import de.metas.calendar.MultiCalendarService;
import de.metas.calendar.conflicts.CalendarConflictEventsDispatcher;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducerFactory;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CalendarWebSocketProducerFactory implements WebSocketProducerFactory
{
	private static final CalendarWebSocketNamingStrategy NAMING_STRATEGY = CalendarWebSocketNamingStrategy.DEFAULT;

	private final MultiCalendarService multiCalendarService;
	private final CalendarConflictEventsDispatcher calendarConflictEventsDispatcher;

	public CalendarWebSocketProducerFactory(
			@NonNull final MultiCalendarService multiCalendarService,
			@NonNull final CalendarConflictEventsDispatcher calendarConflictEventsDispatcher)
	{
		this.multiCalendarService = multiCalendarService;
		this.calendarConflictEventsDispatcher = calendarConflictEventsDispatcher;
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
				calendarConflictEventsDispatcher,
				topicName,
				calendarTopicName.getSimulationId(),
				calendarTopicName.getAdLanguage());
	}
}
