package de.metas.calendar.simulation;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@ToString(of = "list")
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
final class CompositeCalendarSimulationServiceHook implements CalendarSimulationServiceHook
{
	private final ImmutableList<CalendarSimulationServiceHook> list;

	CompositeCalendarSimulationServiceHook(@NonNull final ImmutableList<CalendarSimulationServiceHook> list)
	{
		this.list = list;
	}

	public static CompositeCalendarSimulationServiceHook of(@NonNull final Optional<List<CalendarSimulationServiceHook>> optionalList)
	{
		final ImmutableList<CalendarSimulationServiceHook> list = optionalList.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		return new CompositeCalendarSimulationServiceHook(list);
	}

	@Override
	public void onNewSimulation(final @NonNull CalendarSimulationRef simulationRef, final @Nullable CalendarSimulationId copyFromSimulationId)
	{
		list.forEach(hook -> hook.onNewSimulation(simulationRef, copyFromSimulationId));
	}
}
