package de.metas.project.workorder.conflicts;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.ImmutableMap;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
// pimp json serialization which is needed for snapshot testing
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ResourceAllocationConflicts
{
	private static final ResourceAllocationConflicts EMPTY = new ResourceAllocationConflicts(null, ImmutableMap.of());

	@Getter
	@Nullable final SimulationPlanId simulationId;
	@NonNull private final ImmutableMap<ProjectResourceIdsPair, ResourceAllocationConflict> byIds;

	private ResourceAllocationConflicts(
			final @Nullable SimulationPlanId simulationId,
			final @NonNull ImmutableMap<ProjectResourceIdsPair, ResourceAllocationConflict> byIds)
	{
		if (simulationId == null)
		{
			if (byIds.values().stream().anyMatch(ResourceAllocationConflict::isSimulation))
			{
				throw new AdempiereException("Simulation conflicts are not allowed in actual conflicts only collection")
						.setParameter("simulationId", simulationId)
						.setParameter("byIds", byIds)
						.appendParametersToMessage();
			}
		}
		else
		{
			if (byIds.values().stream().anyMatch(conflict -> conflict.isSimulation() && !conflict.isSimulation(simulationId)))
			{
				throw new AdempiereException("Mixing conflicts from different simulations is not allowed.")
						.setParameter("simulationId", simulationId)
						.setParameter("byIds", byIds)
						.appendParametersToMessage();
			}
		}

		this.simulationId = simulationId;
		this.byIds = byIds;
	}

	public static ResourceAllocationConflicts empty()
	{
		return EMPTY;
	}

	public static ResourceAllocationConflicts of(
			final @Nullable SimulationPlanId simulationId,
			final @Nullable List<ResourceAllocationConflict> list)
	{
		final ImmutableMap<ProjectResourceIdsPair, ResourceAllocationConflict> byIds;
		if (list == null || list.isEmpty())
		{
			byIds = ImmutableMap.of();
		}
		else
		{
			byIds = list.stream().collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(ResourceAllocationConflict::getProjectResourceIdsPair));
		}

		if (simulationId == null && byIds.isEmpty())
		{
			return EMPTY;
		}

		return new ResourceAllocationConflicts(simulationId, byIds);
	}

	public static ResourceAllocationConflicts empty(final @Nullable SimulationPlanId simulationId)
	{
		return of(simulationId, null);
	}

	public static ResourceAllocationConflicts actualConflicts(final @Nullable List<ResourceAllocationConflict> list)
	{
		return of(null, list);
	}

	public boolean isInConflict(@NonNull ProjectResourceIdsPair projectResourceIdsPair)
	{
		final ResourceAllocationConflict conflict = getConflict(projectResourceIdsPair);
		return conflict != null && conflict.isConflict();
	}

	@Nullable
	private ResourceAllocationConflict getConflict(final ProjectResourceIdsPair projectResourceIdsPair)
	{
		return byIds.get(projectResourceIdsPair);
	}

	public Collection<ResourceAllocationConflict> toCollection()
	{
		return byIds.values();
	}

	public Stream<ResourceAllocationConflict> stream()
	{
		return byIds.values().stream();
	}

	private boolean isActualConflicts()
	{
		return !isSimulation();
	}

	public boolean isSimulation()
	{
		return simulationId != null;
	}

	public boolean isEmpty()
	{
		return byIds.isEmpty();
	}

	public ResourceAllocationConflicts mergeWithSimulation(final ResourceAllocationConflicts simulationConflicts)
	{
		if (!isActualConflicts())
		{
			throw new AdempiereException("Expected to be actual conflicts: " + this);
		}
		if (!simulationConflicts.isSimulation())
		{
			throw new AdempiereException("Expected to be simulation conflicts: " + this);
		}

		if (isEmpty())
		{
			return simulationConflicts;
		}

		return new ResourceAllocationConflicts(
				simulationConflicts.simulationId,
				CollectionUtils.mergeMaps(this.byIds, simulationConflicts.byIds)
		);
	}

	public boolean isSimulationConflictsApproved()
	{
		return stream()
				.filter(ResourceAllocationConflict::isSimulation)
				.filter(ResourceAllocationConflict::isConflict)
				.allMatch(ResourceAllocationConflict::isApproved);
	}
}
