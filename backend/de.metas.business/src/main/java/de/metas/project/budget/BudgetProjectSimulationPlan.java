package de.metas.project.budget;

import com.google.common.collect.ImmutableMap;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class BudgetProjectSimulationPlan
{
	@Getter
	@NonNull
	private final SimulationPlanId simulationPlanId;
	private final ImmutableMap<BudgetProjectResourceId, BudgetProjectResourceSimulation> projectResourcesById;

	@Builder(toBuilder = true)
	private BudgetProjectSimulationPlan(
			@NonNull final SimulationPlanId simulationPlanId,
			@Nullable final Map<BudgetProjectResourceId, BudgetProjectResourceSimulation> projectResourcesById)
	{
		this.simulationPlanId = simulationPlanId;
		this.projectResourcesById = projectResourcesById != null ? ImmutableMap.copyOf(projectResourcesById) : ImmutableMap.of();
	}

	public Collection<BudgetProjectResourceSimulation> getAll()
	{
		return projectResourcesById.values();
	}

	BudgetProjectSimulationPlan mapByProjectResourceId(
			@NonNull BudgetProjectResourceId projectResourceId,
			@NonNull UnaryOperator<BudgetProjectResourceSimulation> mapper)
	{
		@Nullable
		BudgetProjectResourceSimulation simulation = getByProjectResourceIdOrNull(projectResourceId);
		final BudgetProjectResourceSimulation simulationChanged = mapper.apply(simulation);
		if (Objects.equals(simulation, simulationChanged))
		{
			// no changes
			return this;
		}
		else
		{
			Check.assumeNotNull(simulationChanged, "changed simulation cannot be null");
			Check.assumeEquals(simulationChanged.getProjectResourceId(), projectResourceId, "projectStepAndResourceId");

			return toBuilder()
					.projectResourcesById(CollectionUtils.mergeElementToMap(projectResourcesById, simulationChanged, BudgetProjectResourceSimulation::getProjectResourceId))
					.build();
		}
	}

	@Nullable
	public BudgetProjectResourceSimulation getByProjectResourceIdOrNull(@NonNull final BudgetProjectResourceId projectResourceId)
	{
		return projectResourcesById.get(projectResourceId);
	}

	public BudgetProjectResourceSimulation getByProjectResourceId(@NonNull final BudgetProjectResourceId projectResourceId)
	{
		final BudgetProjectResourceSimulation projectResource = getByProjectResourceIdOrNull(projectResourceId);
		if (projectResource == null)
		{
			throw new AdempiereException("No simulation found for " + projectResourceId);
		}
		return projectResource;
	}

	public BudgetProjectResource applyOn(@NonNull BudgetProjectResource projectResource)
	{
		final BudgetProjectResourceSimulation simulation = getByProjectResourceIdOrNull(projectResource.getId());
		return simulation != null ? simulation.applyOn(projectResource) : projectResource;
	}

	public BudgetProjectSimulationPlan mergeFrom(final BudgetProjectSimulationPlan fromSimulationPlan)
	{
		return toBuilder()
				.projectResourcesById(CollectionUtils.mergeMaps(this.projectResourcesById, fromSimulationPlan.projectResourcesById))
				.build();
	}
}
