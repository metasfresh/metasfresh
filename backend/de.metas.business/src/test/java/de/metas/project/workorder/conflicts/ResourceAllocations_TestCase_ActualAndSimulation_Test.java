package de.metas.project.workorder.conflicts;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.product.ResourceId;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResourceId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SnapshotExtension.class)
public class ResourceAllocations_TestCase_ActualAndSimulation_Test
{
	@SuppressWarnings("unused") private Expect expect;

	private static final SimulationPlanId SIMULATION1 = SimulationPlanId.ofRepoId(1);
	private static final WOProjectResourceId PR1 = WOProjectResourceId.ofRepoId(1, 1);
	private static final WOProjectResourceId PR2 = WOProjectResourceId.ofRepoId(1, 2);

	private static final Instant refInstant = LocalDate.parse("2022-06-01").atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();

	static Instant instant(final int day) {return refInstant.plus(day - 1, ChronoUnit.DAYS);}

	static CalendarDateRange allDay(final int startDay, final int endDay)
	{
		return CalendarDateRange.builder()
				.startDate(instant(startDay))
				.endDate(instant(endDay))
				.allDay(true)
				.build();
	}

	ResourceAllocation alloc(final int startDay, final int endDay, @Nullable final SimulationPlanId simulationId, final WOProjectResourceId projectResourceId)
	{
		return ResourceAllocation.builder()
				.resourceId(ResourceIdAndType.machine(ResourceId.ofRepoId(1)))
				.projectResourceId(projectResourceId)
				.appliedSimulationId(simulationId)
				.dateRange(allDay(startDay, endDay))
				.build();
	}

	ResourceAllocations allocations(
			@Nullable final SimulationPlanId simulationId,
			final ResourceAllocation... allocations)
	{
		return ResourceAllocations.of(simulationId, ImmutableList.copyOf(allocations));
	}

	@Test
	void okInActualAllocs_changedButNoConflictInSimulation()
	{
		final ResourceAllocationConflicts actualConflicts = allocations(null,
				alloc(1, 3, null, PR1),
				alloc(4, 5, null, PR2))
				.findActualConflicts();
		System.out.println("actualConflicts: " + actualConflicts);
		assertThat(actualConflicts.isEmpty()).isTrue();

		final ResourceAllocationConflicts simulationOnlyConflicts = allocations(SIMULATION1,
				alloc(1, 3, null, PR1),
				alloc(4, 7, SIMULATION1, PR2))
				.findSimulationOnlyConflicts(actualConflicts);
		System.out.println("simulationOnlyConflicts: " + simulationOnlyConflicts);
		assertThat(actualConflicts.isEmpty()).isTrue();
	}

	@Test
	void okInActualAllocs_conflictInSimulation()
	{
		final ResourceAllocationConflicts actualConflicts = allocations(null,
				alloc(1, 3, null, PR1),
				alloc(4, 5, null, PR2))
				.findActualConflicts();
		System.out.println("actualConflicts: " + actualConflicts);
		assertThat(actualConflicts.isEmpty()).isTrue();

		final ResourceAllocationConflicts simulationOnlyConflicts = allocations(SIMULATION1,
				alloc(1, 3, null, PR1),
				alloc(2, 5, SIMULATION1, PR2))
				.findSimulationOnlyConflicts(actualConflicts);
		System.out.println("simulationOnlyConflicts: " + simulationOnlyConflicts);
		expect.serializer("orderedJson").toMatchSnapshot(simulationOnlyConflicts);
	}

	@Test
	void conflictInActualAllocs_fixedInSimulation()
	{
		final ResourceAllocationConflicts actualConflicts = allocations(null,
				alloc(1, 3, null, PR1),
				alloc(2, 5, null, PR2))
				.findActualConflicts();
		System.out.println("actualConflicts: " + actualConflicts);

		final ResourceAllocationConflicts simulationOnlyConflicts = allocations(SIMULATION1,
				alloc(1, 3, null, PR1),
				alloc(4, 5, SIMULATION1, PR2))
				.findSimulationOnlyConflicts(actualConflicts);
		System.out.println("simulationOnlyConflicts: " + simulationOnlyConflicts);
		expect.serializer("orderedJson").toMatchSnapshot(simulationOnlyConflicts);
	}

	@Test
	void conflictInActualAllocs_changedButStillConflictInSimulation()
	{
		final ResourceAllocationConflicts actualConflicts = allocations(null,
				alloc(1, 3, null, PR1),
				alloc(2, 5, null, PR2))
				.findActualConflicts();
		System.out.println("actualConflicts: " + actualConflicts);

		final ResourceAllocationConflicts simulationOnlyConflicts = allocations(SIMULATION1,
				alloc(1, 3, null, PR1),
				alloc(2, 4, SIMULATION1, PR2))
				.findSimulationOnlyConflicts(actualConflicts);
		System.out.println("simulationOnlyConflicts: " + simulationOnlyConflicts);
		expect.serializer("orderedJson").toMatchSnapshot(simulationOnlyConflicts);
	}

	@Test
	void conflictInActualAllocs_noChangesInSimulation()
	{
		final ResourceAllocationConflicts actualConflicts = allocations(null,
				alloc(1, 3, null, PR1),
				alloc(2, 5, null, PR2))
				.findActualConflicts();
		System.out.println("actualConflicts: " + actualConflicts);

		final ResourceAllocationConflicts simulationOnlyConflicts = allocations(SIMULATION1)
				.findSimulationOnlyConflicts(actualConflicts);

		System.out.println("simulationOnlyConflicts: " + simulationOnlyConflicts);
		assertThat(simulationOnlyConflicts.isEmpty()).isTrue();
		//expect(simulationOnlyConflicts).toMatchSnapshot();
	}

}
