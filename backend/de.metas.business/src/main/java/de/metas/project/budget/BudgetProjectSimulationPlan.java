package de.metas.project.budget;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class BudgetProjectSimulationPlan
{
	@Getter
	@NonNull
	private final CalendarSimulationId simulationPlanId;
	private final HashMap<BudgetProjectResourceId, BudgetProjectResourceSimulation> byProjectResourceId = new HashMap<>();

	public BudgetProjectSimulationPlan(@NonNull final CalendarSimulationId simulationPlanId)
	{
		this.simulationPlanId = simulationPlanId;
	}

	public synchronized Collection<BudgetProjectResourceSimulation> getAll()
	{
		return ImmutableList.copyOf(byProjectResourceId.values());
	}

	synchronized BudgetProjectResourceSimulation changeById(
			@NonNull BudgetProjectAndResourceId budgetProjectAndResourceId,
			@NonNull UnaryOperator<BudgetProjectResourceSimulation> mapper)
	{
		@Nullable
		BudgetProjectResourceSimulation simulation = getByProjectResourceIdOrNull(budgetProjectAndResourceId.getProjectResourceId());
		final BudgetProjectResourceSimulation simulationChanged = mapper.apply(simulation);
		if (Objects.equals(simulation, simulationChanged))
		{
			return simulation;
		}
		else
		{
			Check.assumeNotNull(simulationChanged, "changed simulation cannot be null");
			Check.assumeEquals(simulationChanged.getSimulationId(), simulationPlanId, "simulationId");
			Check.assumeEquals(simulationChanged.getProjectAndResourceId(), budgetProjectAndResourceId, "projectAndResourceId");

			byProjectResourceId.put(budgetProjectAndResourceId.getProjectResourceId(), simulationChanged);
			return simulationChanged;
		}
	}

	@Nullable
	public synchronized BudgetProjectResourceSimulation getByProjectResourceIdOrNull(@NonNull final BudgetProjectResourceId projectResourceId)
	{
		return byProjectResourceId.get(projectResourceId);
	}
}
