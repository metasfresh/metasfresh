package de.metas.ui.web.calendar.websocket;

import de.metas.calendar.MultiCalendarService;
import de.metas.calendar.conflicts.CalendarConflictEventsDispatcher;
import de.metas.calendar.plan_optimizer.SimulationOptimizerStatusDispatcher;
import de.metas.calendar.simulation.SimulationPlanService;
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
	private final SimulationPlanService simulationPlanService;
	private final SimulationOptimizerStatusDispatcher simulationOptimizerStatusDispatcher;

	public CalendarWebSocketProducerFactory(
			final @NonNull MultiCalendarService multiCalendarService,
			final @NonNull CalendarConflictEventsDispatcher calendarConflictEventsDispatcher,
			final @NonNull SimulationPlanService simulationPlanService,
			final @NonNull SimulationOptimizerStatusDispatcher simulationOptimizerStatusDispatcher)
	{
		this.multiCalendarService = multiCalendarService;
		this.calendarConflictEventsDispatcher = calendarConflictEventsDispatcher;
		this.simulationPlanService = simulationPlanService;
		this.simulationOptimizerStatusDispatcher = simulationOptimizerStatusDispatcher;
	}

	@Override
	public String getTopicNamePrefix() {return NAMING_STRATEGY.getTopicNameWithoutParams();}

	@Override
	public boolean isDestroyIfNoActiveSubscriptions() {return true;}

	@Override
	public CalendarWebSocketProducer createProducer(final WebsocketTopicName topicName)
	{
		final ParsedCalendarWebsocketTopicName calendarTopicName = NAMING_STRATEGY.parse(topicName);
		return CalendarWebSocketProducer.builder()
				.multiCalendarService(multiCalendarService)
				.calendarConflictEventsDispatcher(calendarConflictEventsDispatcher)
				.simulationPlanService(simulationPlanService)
				.simulationOptimizerStatusDispatcher(simulationOptimizerStatusDispatcher)
				.calendarTopicName(calendarTopicName)
				.build();
	}
}
