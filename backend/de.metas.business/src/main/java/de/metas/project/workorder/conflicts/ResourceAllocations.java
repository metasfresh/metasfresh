package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

class ResourceAllocations
{
	private static final ResourceAllocations EMPTY = new ResourceAllocations(null, null);

	@Nullable final SimulationPlanId simulationId;
	private final ImmutableList<ResourceAllocation> resourceAllocations;

	private ResourceAllocations(
			@Nullable final SimulationPlanId simulationId,
			@Nullable final List<ResourceAllocation> resourceAllocations)
	{
		this.simulationId = simulationId;
		this.resourceAllocations = resourceAllocations != null ? ImmutableList.copyOf(resourceAllocations) : ImmutableList.of();
	}

	public static ResourceAllocations of(
			@Nullable final SimulationPlanId simulationId,
			@NonNull final List<ResourceAllocation> list)
	{
		if (simulationId == null && list.isEmpty())
		{
			return EMPTY;
		}

		return new ResourceAllocations(simulationId, list);
	}

	private boolean isSimulation()
	{
		return simulationId != null;
	}

	public ResourceAllocationConflicts findActualConflicts()
	{
		if (isSimulation())
		{
			throw new AdempiereException("Cannot find actual conflicts when working in simulation");
		}

		return findConflicts0(ResourceAllocationConflicts.empty());
	}

	public ResourceAllocationConflicts findSimulationOnlyConflicts(@NonNull final ResourceAllocationConflicts actualConflicts)
	{
		if (!isSimulation())
		{
			throw new AdempiereException("Cannot find simulation only conflicts when working actual allocations");
		}
		if (actualConflicts.isSimulation())
		{
			throw new AdempiereException("actualConflicts shall not be simulation: " + actualConflicts);
		}

		return findConflicts0(actualConflicts);
	}

	private ResourceAllocationConflicts findConflicts0(@NonNull final ResourceAllocationConflicts actualConflicts)
	{
		if (resourceAllocations.isEmpty())
		{
			return ResourceAllocationConflicts.empty(simulationId);
		}

		final ArrayList<ResourceAllocationConflict> conflicts = new ArrayList<>();
		for (int i1 = 0, size = resourceAllocations.size(); i1 < size; i1++)
		{
			final ResourceAllocation resourceAllocation1 = resourceAllocations.get(i1);

			for (int i2 = i1 + 1; i2 < size; i2++)
			{
				final ResourceAllocation resourceAllocation2 = resourceAllocations.get(i2);

				//
				// Case: we are checking the conflicts from simulation but both resource allocations that we are checking are not changed in simulation
				if (simulationId != null
						&& resourceAllocation1.getAppliedSimulationId() == null
						&& resourceAllocation2.getAppliedSimulationId() == null)
				{
					// => do nothing, we expect, if there is conflict, that shall be in actualConflicts
					continue;
				}

				//
				// Case: we have a conflict
				final ProjectResourceIdsPair projectResourceIdsPair = ProjectResourceIdsPair.of(
						resourceAllocation1.getProjectResourceId(),
						resourceAllocation2.getProjectResourceId());
				if (resourceAllocation1.isInConflictWith(resourceAllocation2))
				{
					conflicts.add(ResourceAllocationConflict.builder()
							.projectResourceIdsPair(projectResourceIdsPair)
							.simulationId(simulationId)
							.status(ResourceAllocationConflictStatus.CONFLICT)
							.build());
				}
				//
				// Case: we have a conflict in actual allocations but that conflict was solved in current simulation
				else if (actualConflicts.isInConflict(projectResourceIdsPair))
				{
					conflicts.add(ResourceAllocationConflict.builder()
							.projectResourceIdsPair(projectResourceIdsPair)
							.simulationId(simulationId)
							.status(ResourceAllocationConflictStatus.RESOLVED)
							.build());
				}
				//
				// Case: no conflict in current simulation nor in actual allocations
				// => nothing to record
				//else {}
			}
		}

		return ResourceAllocationConflicts.of(simulationId, conflicts);
	}

}
