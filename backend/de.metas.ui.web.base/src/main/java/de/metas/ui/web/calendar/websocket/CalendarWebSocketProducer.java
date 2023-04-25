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
import de.metas.calendar.plan_optimizer.SimulationOptimizerStatusDispatcher;
import de.metas.calendar.plan_optimizer.SimulationOptimizerStatusListener;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.simulation.SimulationPlanChangesListener;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.common.util.time.SystemTime;
import de.metas.ui.web.calendar.json.JsonCalendarConflict;
import de.metas.ui.web.calendar.json.JsonCalendarEntry;
import de.metas.ui.web.calendar.json.JsonSimulationOptimizerStatusType;
import de.metas.ui.web.calendar.websocket.json.JsonAddOrChangeWebsocketEvent;
import de.metas.ui.web.calendar.websocket.json.JsonConflictsChangedWebsocketEvent;
import de.metas.ui.web.calendar.websocket.json.JsonRemoveWebsocketEvent;
import de.metas.ui.web.calendar.websocket.json.JsonSimulationOptimizerStatusChangedEvent;
import de.metas.ui.web.calendar.websocket.json.JsonSimulationPlanChangedEvent;
import de.metas.ui.web.calendar.websocket.json.JsonWebsocketEvent;
import de.metas.ui.web.calendar.websocket.json.JsonWebsocketEventsList;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducer;
import de.metas.websocket.sender.WebsocketSender;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * When the websocket client connects to our topic then
 * <ul>
 *     <li>this producer will listen to {@link CalendarContinuousQuery} changes and will forward them to websocket topic.</li>
 *     <li>this producer will listen to {@link CalendarConflictChangesEvent}s and will forward them to websocket topic.</li>
 * </ul>
 */
class CalendarWebSocketProducer
		implements
		WebSocketProducer,
		CalendarContinuousQueryListener,
		CalendarConflictEventsListener,
		SimulationPlanChangesListener,
		SimulationOptimizerStatusListener
{
	// services:
	@NonNull private final MultiCalendarService multiCalendarService;
	@NonNull private final CalendarConflictEventsDispatcher calendarConflictEventsDispatcher;
	@NonNull private final SimulationPlanService simulationPlanService;
	@NonNull private final SimulationOptimizerStatusDispatcher simulationOptimizerStatusDispatcher;

	// params:
	@NonNull private final WebsocketTopicName topicName;
	@NonNull private final String adLanguage;
	@NonNull private final CalendarQuery calendarQuery;
	private WebsocketSender websocketSender;

	// state:
	private CalendarContinuousQuery continuousQuery;

	@Builder
	private CalendarWebSocketProducer(
			final @NonNull MultiCalendarService multiCalendarService,
			final @NonNull CalendarConflictEventsDispatcher calendarConflictEventsDispatcher,
			final @NonNull SimulationPlanService simulationPlanService,
			final @NonNull SimulationOptimizerStatusDispatcher simulationOptimizerStatusDispatcher,
			//
			final @NonNull ParsedCalendarWebsocketTopicName calendarTopicName)
	{
		this.multiCalendarService = multiCalendarService;
		this.calendarConflictEventsDispatcher = calendarConflictEventsDispatcher;
		this.simulationPlanService = simulationPlanService;
		this.simulationOptimizerStatusDispatcher = simulationOptimizerStatusDispatcher;

		this.topicName = calendarTopicName.getTopicName();
		this.adLanguage = calendarTopicName.getAdLanguage();

		this.calendarQuery = CalendarQuery.builder()
				.simulationId(calendarTopicName.getSimulationId())
				.resourceIds(calendarTopicName.getResourceIdsPredicate())
				.onlyProjectId(calendarTopicName.getOnlyProjectId())
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

		final SimulationPlanId simulationId = calendarQuery.getSimulationId();
		calendarConflictEventsDispatcher.subscribe(simulationId, this);

		if (simulationId != null)
		{
			simulationPlanService.subscribe(simulationId, this);
			simulationOptimizerStatusDispatcher.subscribe(simulationId, this);
		}
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

		final SimulationPlanId simulationId = calendarQuery.getSimulationId();
		calendarConflictEventsDispatcher.unsubscribe(simulationId, this);

		if (simulationId != null)
		{
			simulationPlanService.unsubscribe(simulationId, this);
		}
	}

	@Override
	public void onCalendarContinuousQueryEvents(final @NonNull ImmutableList<Event> events) {forwardQueryEventsToWebsocket(events);}

	@Override
	public void onCalendarConflictChangesEvents(final @NonNull ImmutableList<CalendarConflictChangesEvent> events) {forwardConflictChangesEventToWebsocket(events);}

	@Override
	public void onSimulationPlanAfterComplete(@NonNull final SimulationPlanRef simulationRef)
	{
		sendToWebsocket(JsonWebsocketEventsList.ofEvent(JsonSimulationPlanChangedEvent.builder()
				.simulationId(simulationRef.getId())
				.processed(simulationRef.isProcessed())
				.build()));
	}

	@Override
	public void onSimulationOptimizerStarted(@NonNull final SimulationPlanId simulationId)
	{
		sendToWebsocket(JsonWebsocketEventsList.ofEvent(JsonSimulationOptimizerStatusChangedEvent.builder()
				.simulationId(simulationId)
				.status(JsonSimulationOptimizerStatusType.STARTED)
				.build()));
	}

	@Override
	public void onSimulationOptimizerProgress(@NonNull final Plan solution)
	{
		sendToWebsocket(JsonWebsocketEventsList.ofEvent(JsonSimulationOptimizerStatusChangedEvent.builder()
				.simulationId(solution.getSimulationId())
				.status(JsonSimulationOptimizerStatusType.STARTED)
				.simulationPlanChanged(true)
				.build()));
	}

	@Override
	public void onSimulationOptimizerStopped(@NonNull final SimulationPlanId simulationId)
	{
		sendToWebsocket(JsonWebsocketEventsList.ofEvent(JsonSimulationOptimizerStatusChangedEvent.builder()
				.simulationId(simulationId)
				.status(JsonSimulationOptimizerStatusType.STOPPED)
				.build()));
	}

	private void forwardConflictChangesEventToWebsocket(final @NonNull ImmutableList<CalendarConflictChangesEvent> events)
	{
		sendToWebsocket(JsonWebsocketEventsList.ofList(events.stream().map(this::toJsonWebsocketEvent).collect(ImmutableList.toImmutableList())));
	}

	private void forwardQueryEventsToWebsocket(final @NonNull List<Event> events)
	{
		sendToWebsocket(JsonWebsocketEventsList.ofList(events.stream().map(this::toJsonWebsocketEvent).collect(ImmutableList.toImmutableList())));
	}

	private void sendToWebsocket(@NonNull final JsonWebsocketEventsList events)
	{
		websocketSender.convertAndSend(topicName, events);
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
