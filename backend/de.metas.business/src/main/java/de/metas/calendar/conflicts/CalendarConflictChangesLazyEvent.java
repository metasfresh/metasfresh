package de.metas.calendar.conflicts;

import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CalendarConflictChangesLazyEvent
{
	@Getter
	@Nullable private final SimulationPlanId simulationId;
	@NonNull private final Supplier<CalendarConflictChangesEvent> eventProducer;
	private CalendarConflictChangesEvent event; // lazy

	private CalendarConflictChangesLazyEvent(
			final @Nullable SimulationPlanId simulationId,
			final @NonNull Supplier<CalendarConflictChangesEvent> eventProducer)
	{
		this.simulationId = simulationId;
		this.eventProducer = eventProducer;
	}

	public static CalendarConflictChangesLazyEvent of(
			final @Nullable SimulationPlanId simulationId,
			final @NonNull Supplier<CalendarConflictChangesEvent> eventProducer)
	{
		return new CalendarConflictChangesLazyEvent(simulationId, eventProducer);
	}

	public CalendarConflictChangesEvent toEvent()
	{
		CalendarConflictChangesEvent event = this.event;
		if (event == null)
		{
			event = this.event = eventProducer.get();
		}
		return event;
	}

}
