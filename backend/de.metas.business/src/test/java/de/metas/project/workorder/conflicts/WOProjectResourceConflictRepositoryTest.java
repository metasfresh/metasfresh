package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.util.InSetPredicate;
import de.metas.util.OptionalBoolean;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(AdempiereTestWatcher.class)
class WOProjectResourceConflictRepositoryTest
{
	private static final ProjectId P1 = ProjectId.ofRepoId(1);
	private static final WOProjectResourceId P1R1 = WOProjectResourceId.ofRepoId(P1, 1);
	private static final WOProjectResourceId P1R2 = WOProjectResourceId.ofRepoId(P1, 2);
	private static final WOProjectResourceId P1R3 = WOProjectResourceId.ofRepoId(P1, 3);
	private static final WOProjectResourceId P1R4 = WOProjectResourceId.ofRepoId(P1, 4);

	final ImmutableSet<WOProjectResourceId> PRs1_to_4 = ImmutableSet.of(P1R1, P1R2, P1R3, P1R4);

	private static final SimulationPlanId SIMULATION1 = SimulationPlanId.ofRepoId(1);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@SuppressWarnings("SameParameterValue")
	private static ResourceAllocationConflict conflict(
			WOProjectResourceId pr1,
			WOProjectResourceId pr2,
			ResourceAllocationConflictStatus status,
			SimulationPlanId simulationId)
	{
		return ResourceAllocationConflict.builder()
				.projectResourceIdsPair(ProjectResourceIdsPair.of(pr1, pr2))
				.simulationId(simulationId)
				.status(status)
				.approved(OptionalBoolean.FALSE)
				.build();
	}

	private ResourceAllocationConflicts conflicts(SimulationPlanId simulationId, ResourceAllocationConflict... conflicts)
	{
		return ResourceAllocationConflicts.of(simulationId, ImmutableList.copyOf(conflicts));
	}

	@Test
	void save_and_getActualAndSimulation_and_getActualConflicts()
	{
		final WOProjectResourceConflictRepository repo = new WOProjectResourceConflictRepository();

		final ResourceAllocationConflicts actualConflicts = conflicts(null,
				conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null),
				conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null)
		);
		repo.save(actualConflicts, PRs1_to_4);
		assertThat(repo.getActualAndSimulation(null, InSetPredicate.only(P1), InSetPredicate.any())).isEqualTo(actualConflicts);
		assertThat(repo.getActualConflicts(PRs1_to_4)).isEqualTo(actualConflicts);
		assertThat(repo.getActualAndSimulation(SIMULATION1, InSetPredicate.only(P1), InSetPredicate.any())).isEqualTo(
				conflicts(SIMULATION1,
						conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null),
						conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null)
				));

		final ResourceAllocationConflicts simulationOnlyConflicts = conflicts(SIMULATION1,
				conflict(P1R1, P1R2, ResourceAllocationConflictStatus.RESOLVED, SIMULATION1),
				conflict(P1R1, P1R4, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1)
		);
		repo.save(simulationOnlyConflicts, PRs1_to_4);
		assertThat(repo.getActualAndSimulation(SIMULATION1, InSetPredicate.only(P1), InSetPredicate.any())).isEqualTo(
				conflicts(SIMULATION1,
						conflict(P1R1, P1R2, ResourceAllocationConflictStatus.RESOLVED, SIMULATION1),
						conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null),
						conflict(P1R1, P1R4, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1)
				));
	}

	@Test
	void save_actualAndSimulation_expect_onlySimulationIsSaved()
	{
		final ResourceAllocationConflicts actualAndSimulationConflicts = conflicts(SIMULATION1,
				conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null),
				conflict(P1R1, P1R4, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1)
		);

		final WOProjectResourceConflictRepository repo = new WOProjectResourceConflictRepository();
		repo.save(actualAndSimulationConflicts, PRs1_to_4);
		// NOTE: expect some warnings logged and only simulation conflicts saved

		assertThat(repo.getActualAndSimulation(SIMULATION1, InSetPredicate.only(P1), InSetPredicate.any()))
				.isEqualTo(
						conflicts(SIMULATION1,
								// conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null), // this one shall NOT be saved
								conflict(P1R1, P1R4, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1)
						)
				);
	}

	@Test
	void save_and_saveAgain_actualConflicts()
	{
		final WOProjectResourceConflictRepository repo = new WOProjectResourceConflictRepository();

		{
			ResourceAllocationConflicts actualConflicts = conflicts(null,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null),
					conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null)
			);
			repo.save(actualConflicts, PRs1_to_4);
			assertThat(repo.getActualConflicts(PRs1_to_4)).isEqualTo(actualConflicts);
		}

		{
			ResourceAllocationConflicts actualConflicts = conflicts(null,
					//conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null),
					conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null)
			);
			repo.save(actualConflicts, PRs1_to_4);
			assertThat(repo.getActualConflicts(PRs1_to_4)).isEqualTo(actualConflicts);
		}

		{
			ResourceAllocationConflicts actualConflicts = conflicts(null);
			repo.save(actualConflicts, PRs1_to_4);
			assertThat(repo.getActualConflicts(PRs1_to_4)).isEqualTo(actualConflicts);
		}

	}

	@Test
	void save_and_saveAgain_simulationConflicts()
	{
		final WOProjectResourceConflictRepository repo = new WOProjectResourceConflictRepository();

		{
			ResourceAllocationConflicts actualConflicts = conflicts(null,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null),
					conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null)
			);
			repo.save(actualConflicts, PRs1_to_4);
			assertThat(repo.getActualConflicts(PRs1_to_4)).isEqualTo(actualConflicts);
			assertThat(repo.getActualAndSimulation(SIMULATION1, InSetPredicate.only(P1), InSetPredicate.any())).isEqualTo(
					conflicts(SIMULATION1,
							conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null),
							conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null)
					));
		}

		{
			ResourceAllocationConflicts simOnlyConflicts = conflicts(SIMULATION1,
					conflict(P1R1, P1R2, ResourceAllocationConflictStatus.RESOLVED, SIMULATION1),
					conflict(P1R3, P1R4, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1)
			);
			repo.save(simOnlyConflicts, PRs1_to_4);
			assertThat(repo.getActualAndSimulation(SIMULATION1, InSetPredicate.only(P1), InSetPredicate.any())).isEqualTo(
					conflicts(SIMULATION1,
							conflict(P1R1, P1R2, ResourceAllocationConflictStatus.RESOLVED, SIMULATION1),
							conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null),
							conflict(P1R3, P1R4, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1)
					));
		}

		{
			ResourceAllocationConflicts simOnlyConflicts = conflicts(SIMULATION1,
					//conflict(P1R1, P1R2, ResourceAllocationConflictStatus.RESOLVED, SIMULATION1),
					conflict(P1R3, P1R4, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1)
			);
			repo.save(simOnlyConflicts, PRs1_to_4);
			assertThat(repo.getActualAndSimulation(SIMULATION1, InSetPredicate.only(P1), InSetPredicate.any())).isEqualTo(
					conflicts(SIMULATION1,
							conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null),
							conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null),
							conflict(P1R3, P1R4, ResourceAllocationConflictStatus.CONFLICT, SIMULATION1)
					));
		}

		{
			ResourceAllocationConflicts simOnlyConflicts = conflicts(SIMULATION1);
			repo.save(simOnlyConflicts, PRs1_to_4);
			assertThat(repo.getActualAndSimulation(SIMULATION1, InSetPredicate.only(P1), InSetPredicate.any())).isEqualTo(
					conflicts(SIMULATION1,
							conflict(P1R1, P1R2, ResourceAllocationConflictStatus.CONFLICT, null),
							conflict(P1R2, P1R3, ResourceAllocationConflictStatus.CONFLICT, null)
					));
		}

	}

}