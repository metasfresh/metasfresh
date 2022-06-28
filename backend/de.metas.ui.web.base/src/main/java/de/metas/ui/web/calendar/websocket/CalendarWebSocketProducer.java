package de.metas.ui.web.calendar.websocket;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarContinuousQuery;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.MultiCalendarService;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.Language;
import de.metas.ui.web.calendar.json.JsonCalendarEntry;
import de.metas.ui.web.calendar.json.JsonWebsocketEvent;
import de.metas.ui.web.calendar.json.JsonWebsocketEventsList;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducer;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;

class CalendarWebSocketProducer implements WebSocketProducer, CalendarContinuousQuery.Listener
{
	private final MultiCalendarService multiCalendarService;
	@NonNull final WebsocketTopicName topicName;
	@NonNull private final String adLanguage;
	@NonNull private final CalendarQuery calendarQuery;

	private WebsocketSender websocketSender;
	private CalendarContinuousQuery continuousQuery;

	CalendarWebSocketProducer(
			@NonNull final MultiCalendarService multiCalendarService,
			@NonNull final WebsocketTopicName topicName,
			@Nullable final SimulationPlanId simulationId,
			@Nullable final String adLanguage)
	{
		this.multiCalendarService = multiCalendarService;
		this.topicName = topicName;
		this.adLanguage = adLanguage != null ? adLanguage : Language.getBaseAD_Language();
		this.calendarQuery = CalendarQuery.builder()
				.simulationId(simulationId)
				.build();

	}

	@Override
	public void setWebsocketSender(@NonNull final WebsocketSender websocketSender)
	{
		this.websocketSender = websocketSender;
	}

	@Override
	public void onStart()
	{
		CalendarContinuousQuery continuousQuery = this.continuousQuery;
		if (continuousQuery == null)
		{
			continuousQuery = this.continuousQuery = multiCalendarService.continuousQuery(calendarQuery);
		}
		continuousQuery.subscribe(this);
	}

	@Override
	public void onStop()
	{
		final CalendarContinuousQuery continuousQuery = this.continuousQuery;
		this.continuousQuery = null;

		if (continuousQuery != null)
		{
			continuousQuery.unsubscribe(this);
		}
	}

	@Override
	public void onEvents(final @NonNull List<CalendarContinuousQuery.Event> events)
	{
		final JsonWebsocketEventsList wsEvents = JsonWebsocketEventsList.builder()
				.events(events.stream()
						.map(this::toJsonWebsocketEvent)
						.collect(ImmutableList.toImmutableList()))
				.build();

		websocketSender.convertAndSend(topicName, wsEvents);
	}

	private JsonWebsocketEvent toJsonWebsocketEvent(final CalendarContinuousQuery.Event event)
	{
		if (event instanceof CalendarContinuousQuery.EntryChangedEvent)
		{
			final CalendarEntry entry = ((CalendarContinuousQuery.EntryChangedEvent)event).getEntry();
			return JsonWebsocketEvent.builder()
					.type(JsonWebsocketEvent.Type.addOrChange)
					.entry(JsonCalendarEntry.of(entry, SystemTime.zoneId(), adLanguage))
					.build();
		}
		else if (event instanceof CalendarContinuousQuery.EntryDeletedEvent)
		{
			final CalendarContinuousQuery.EntryDeletedEvent entryDeletedEvent = (CalendarContinuousQuery.EntryDeletedEvent)event;
			return JsonWebsocketEvent.builder()
					.type(JsonWebsocketEvent.Type.remove)
					.entryId(entryDeletedEvent.getEntryId())
					.simulationId(entryDeletedEvent.getSimulationId())
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown event: " + event);
		}
	}
}
