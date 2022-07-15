package de.metas.ui.web.calendar.websocket;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.MultiCalendarService;
import de.metas.calendar.conflicts.CalendarConflictChangesEvent;
import de.metas.calendar.conflicts.CalendarConflictEventsDispatcher;
import de.metas.calendar.conflicts.CalendarConflictEventsListener;
import de.metas.calendar.continuous_query.CalendarContinuousQuery;
import de.metas.calendar.continuous_query.CalendarContinuousQueryListener;
import de.metas.calendar.continuous_query.EntryChangedEvent;
import de.metas.calendar.continuous_query.EntryDeletedEvent;
import de.metas.calendar.continuous_query.Event;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.Language;
import de.metas.ui.web.calendar.json.JsonCalendarConflict;
import de.metas.ui.web.calendar.json.JsonCalendarEntry;
import de.metas.ui.web.calendar.websocket.json.JsonAddOrChangeWebsocketEvent;
import de.metas.ui.web.calendar.websocket.json.JsonConflictsChangedWebsocketEvent;
import de.metas.ui.web.calendar.websocket.json.JsonRemoveWebsocketEvent;
import de.metas.ui.web.calendar.websocket.json.JsonWebsocketEvent;
import de.metas.ui.web.calendar.websocket.json.JsonWebsocketEventsList;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducer;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * When the websocket client connects to our topic then
 * <ul>
 *     <li>this producer will listen to {@link CalendarContinuousQuery} changes and will forward them to websocket topic.</li>
 *     <li>this producer will listen to {@link CalendarConflictChangesEvent}s and will forward them to websocket topic.</li>
 * </ul>
 */
class CalendarWebSocketProducer implements WebSocketProducer, CalendarContinuousQueryListener, CalendarConflictEventsListener
{
	@NonNull private final MultiCalendarService multiCalendarService;
	@NonNull private final CalendarConflictEventsDispatcher calendarConflictEventsDispatcher;
	@NonNull final WebsocketTopicName topicName;
	@NonNull private final String adLanguage;
	@NonNull private final CalendarQuery calendarQuery;

	private WebsocketSender websocketSender;
	private CalendarContinuousQuery continuousQuery;

	CalendarWebSocketProducer(
			@NonNull final MultiCalendarService multiCalendarService,
			@NonNull final CalendarConflictEventsDispatcher calendarConflictEventsDispatcher,
			@NonNull final WebsocketTopicName topicName,
			@Nullable final SimulationPlanId simulationId,
			@Nullable final String adLanguage)
	{
		this.multiCalendarService = multiCalendarService;
		this.calendarConflictEventsDispatcher = calendarConflictEventsDispatcher;
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

		calendarConflictEventsDispatcher.subscribe(calendarQuery.getSimulationId(), this);
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

		calendarConflictEventsDispatcher.unsubscribe(calendarQuery.getSimulationId(), this);
	}

	@Override
	public void onCalendarContinuousQueryEvents(final @NonNull ImmutableList<Event> events) {forwardQueryEventsToWebsocket(events);}

	@Override
	public void onCalendarConflictChangesEvents(final @NonNull ImmutableList<CalendarConflictChangesEvent> events) {forwardConflictChangesEventToWebsocket(events);}

	private void forwardConflictChangesEventToWebsocket(final @NonNull ImmutableList<CalendarConflictChangesEvent> events)
	{
		websocketSender.convertAndSend(
				topicName,
				JsonWebsocketEventsList.builder()
						.events(events.stream()
								.map(this::toJsonWebsocketEvent)
								.collect(ImmutableList.toImmutableList()))
						.build());
	}

	private void forwardQueryEventsToWebsocket(final @NonNull List<Event> events)
	{
		websocketSender.convertAndSend(
				topicName,
				JsonWebsocketEventsList.builder()
						.events(events.stream()
								.map(this::toJsonWebsocketEvent)
								.collect(ImmutableList.toImmutableList()))
						.build());
	}

	private JsonWebsocketEvent toJsonWebsocketEvent(final Event event)
	{
		if (event instanceof EntryChangedEvent)
		{
			final CalendarEntry entry = ((EntryChangedEvent)event).getEntry();
			return JsonAddOrChangeWebsocketEvent.builder()
					.entry(JsonCalendarEntry.of(entry, SystemTime.zoneId(), adLanguage))
					.build();
		}
		else if (event instanceof EntryDeletedEvent)
		{
			final EntryDeletedEvent entryDeletedEvent = (EntryDeletedEvent)event;
			return JsonRemoveWebsocketEvent.builder()
					.simulationId(entryDeletedEvent.getSimulationId())
					.entryId(entryDeletedEvent.getEntryId())
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown event: " + event);
		}
	}

	private JsonWebsocketEvent toJsonWebsocketEvent(final CalendarConflictChangesEvent event)
	{
		return JsonConflictsChangedWebsocketEvent.builder()
				.simulationId(event.getSimulationId())
				.affectedEntryIds(event.getAffectedEntryIds())
				.conflicts(event.getConflicts()
						.stream()
						.map(JsonCalendarConflict::of)
						.collect(Collectors.toList()))
				.build();
	}

}
