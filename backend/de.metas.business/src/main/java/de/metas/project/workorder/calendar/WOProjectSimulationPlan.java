package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResourceSimulation;
import de.metas.project.workorder.WOProjectStepAndResourceId;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.UnaryOperator;

class WOProjectSimulationPlan
{
	@Getter
	@NonNull
	private final CalendarSimulationId simulationPlanId;
	private final HashMap<WOProjectResourceId, WOProjectResourceSimulation> byWOProjectResourceId = new HashMap<>();

	public WOProjectSimulationPlan(@NonNull final CalendarSimulationId simulationPlanId)
	{
		this.simulationPlanId = simulationPlanId;
	}

	public synchronized Collection<WOProjectResourceSimulation> getAll()
	{
		return ImmutableList.copyOf(byWOProjectResourceId.values());
	}

	synchronized WOProjectResourceSimulation changeById(
			@NonNull WOProjectStepAndResourceId woProjectStepAndResourceId,
			@NonNull UnaryOperator<WOProjectResourceSimulation> mapper)
	{
		@Nullable
		WOProjectResourceSimulation simulation = getByProjectResourceIdOrNull(woProjectStepAndResourceId.getProjectResourceId());
		final WOProjectResourceSimulation simulationChanged = mapper.apply(simulation);
		if (Objects.equals(simulation, simulationChanged))
		{
			return simulation;
		}
		else
		{
			Check.assumeNotNull(simulationChanged, "changed simulation cannot be null");
			Check.assumeEquals(simulationChanged.getSimulationId(), simulationPlanId, "simulationId");
			Check.assumeEquals(simulationChanged.getProjectStepAndResourceId(), woProjectStepAndResourceId, "projectStepAndResourceId");

			byWOProjectResourceId.put(woProjectStepAndResourceId.getProjectResourceId(), simulationChanged);
			return simulationChanged;
		}
	}

	@Nullable
	public synchronized WOProjectResourceSimulation getByProjectResourceIdOrNull(@NonNull final WOProjectResourceId woProjectResourceId)
	{
		return byWOProjectResourceId.get(woProjectResourceId);
	}
}
