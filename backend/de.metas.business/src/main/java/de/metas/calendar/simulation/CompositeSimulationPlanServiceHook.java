package de.metas.calendar.simulation;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@ToString(of = "list")
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
final class CompositeSimulationPlanServiceHook implements SimulationPlanServiceHook
{
	private final ImmutableList<SimulationPlanServiceHook> list;

	CompositeSimulationPlanServiceHook(@NonNull final ImmutableList<SimulationPlanServiceHook> list)
	{
		this.list = list;
	}

	public static CompositeSimulationPlanServiceHook of(@NonNull final Optional<List<SimulationPlanServiceHook>> optionalList)
	{
		final ImmutableList<SimulationPlanServiceHook> list = optionalList.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		return new CompositeSimulationPlanServiceHook(list);
	}

	@Override
	public void onNewSimulationPlan(final @NonNull SimulationPlanRef simulationRef, final @Nullable SimulationPlanId copyFromSimulationPlanId)
	{
		list.forEach(hook -> hook.onNewSimulationPlan(simulationRef, copyFromSimulationPlanId));
	}

	@Override
	public void onComplete(final @NonNull SimulationPlanRef simulationRef)
	{
		list.forEach(hook -> hook.onComplete(simulationRef));
	}

}
