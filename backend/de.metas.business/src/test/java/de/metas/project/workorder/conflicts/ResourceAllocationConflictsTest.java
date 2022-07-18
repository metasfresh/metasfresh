package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.WOProjectAndResourceId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceAllocationConflictsTest
{

	private static final WOProjectAndResourceId P1R1 = WOProjectAndResourceId.ofRepoIds(1, 1);
	private static final WOProjectAndResourceId P1R2 = WOProjectAndResourceId.ofRepoIds(1, 2);
	private static final WOProjectAndResourceId P1R3 = WOProjectAndResourceId.ofRepoIds(1, 3);

	private static final SimulationPlanId SIMULATION1 = SimulationPlanId.ofRepoId(1);

	@SuppressWarnings("SameParameterValue")
	private static ResourceAllocationConflict conflict(
			WOProjectAndResourceId pr1,
			WOProjectAndResourceId pr2,
			ResourceAllocationConflictStatus status,
			SimulationPlanId simulationId)
	{
		return ResourceAllocationConflict.builder()
				.projectResourceIdsPair(ProjectResourceIdsPair.of(pr1, pr2))
				.simulationId(simulationId)
				.status(status)
				.build();
	}

	private ResourceAllocationConflicts conflicts(SimulationPlanId simulationId, ResourceAllocationConflict... conflicts)
	{
		return ResourceAllocationConflicts.of(simulationId, ImmutableList.copyOf(conflicts));
	}

	@Nested
	class mergeWithSimulation
	{
		@Test
		void noActualConflicts_noSimulationConflicts()
		{
			final ResourceAllocationConflicts actualConflicts = conflicts(null);
			final ResourceAllocationConflicts simulationOnlyConflicts = conflicts(SIMULATION1);
			final ResourceAllocationConflicts result = actualConflicts.mergeWithSimulation(simulationOnlyConflicts);
			assertThat(result).isEqualTo(conflicts(SIMULATION1));
		}

		@Test
		void noActualConflicts_simulationConflicts()
		{
			final ResourceAllocationConflicts actualConflicts = conflicts(null);
			final ResourceAllocationConflicts simulationOnlyConflicts = conflicts(SIMULATION1,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1));

			final ResourceAllocationConflicts result = actualConflicts.mergeWithSimulation(simulationOnlyConflicts);
			assertThat(result).isEqualTo(
					conflicts(SIMULATION1,
							conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1))
			);
		}

		@Test
		void noActualConflicts_resolvedInSimulation()
		{
			final ResourceAllocationConflicts actualConflicts = conflicts(null);
			final ResourceAllocationConflicts simulationOnlyConflicts = conflicts(SIMULATION1,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.RESOLVED, SIMULATION1));

			final ResourceAllocationConflicts result = actualConflicts.mergeWithSimulation(simulationOnlyConflicts);
			assertThat(result).isEqualTo(
					conflicts(SIMULATION1,
							conflict(P1R1, P1R2, ResourceAllocationConflictStatus.RESOLVED, SIMULATION1))
			);
		}

		@Test
		void actualConflict_noChangesInSimulation()
		{
			final ResourceAllocationConflicts actualConflicts = conflicts(null,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null));
			final ResourceAllocationConflicts simulationOnlyConflicts = conflicts(SIMULATION1);

			final ResourceAllocationConflicts result = actualConflicts.mergeWithSimulation(simulationOnlyConflicts);
			assertThat(result).isEqualTo(
					conflicts(SIMULATION1,
							conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null))
			);
		}

		@Test
		void actualConflict_simulationConflict()
		{
			final ResourceAllocationConflicts actualConflicts = conflicts(null,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null));
			final ResourceAllocationConflicts simulationOnlyConflicts = conflicts(SIMULATION1,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1));

			final ResourceAllocationConflicts result = actualConflicts.mergeWithSimulation(simulationOnlyConflicts);
			assertThat(result).isEqualTo(
					conflicts(SIMULATION1,
							conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1))
			);
		}

		@Test
		void actualConflict_resolvedInSimulation()
		{
			final ResourceAllocationConflicts actualConflicts = conflicts(null,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null));
			final ResourceAllocationConflicts simulationOnlyConflicts = conflicts(SIMULATION1,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.RESOLVED, SIMULATION1));

			final ResourceAllocationConflicts result = actualConflicts.mergeWithSimulation(simulationOnlyConflicts);
			assertThat(result).isEqualTo(
					conflicts(SIMULATION1,
							conflict(P1R1, P1R2, ResourceAllocationConflictStatus.RESOLVED, SIMULATION1))
			);
		}
	}

	@Test
	void isInConflict()
	{
		final ResourceAllocationConflicts conflicts = conflicts(null,
				conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null));

		assertThat(conflicts.isInConflict(ProjectResourceIdsPair.of(P1R1, P1R2))).isTrue();
		assertThat(conflicts.isInConflict(ProjectResourceIdsPair.of(P1R2, P1R1))).isTrue();

		assertThat(conflicts.isInConflict(ProjectResourceIdsPair.of(P1R2, P1R3))).isFalse();
	}
}