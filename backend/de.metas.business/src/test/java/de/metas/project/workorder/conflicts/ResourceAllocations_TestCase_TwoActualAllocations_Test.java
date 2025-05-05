package de.metas.project.workorder.conflicts;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.product.ResourceId;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResourceId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static de.metas.project.workorder.conflicts.ResourceAllocationsTestUtils.allDay;

@ExtendWith(SnapshotExtension.class)
public class ResourceAllocations_TestCase_TwoActualAllocations_Test
{
	@SuppressWarnings("unused") private Expect expect;

	ResourceAllocationConflicts setup(final CalendarDateRange dateRange1, final CalendarDateRange dateRange2)
	{
		final ResourceAllocations allocations = ResourceAllocations.of(
				null,
				ImmutableList.of(
						ResourceAllocation.builder()
								.resourceId(ResourceIdAndType.machine(ResourceId.ofRepoId(1)))
								.projectResourceId(WOProjectResourceId.ofRepoId(1, 1))
								.dateRange(dateRange1)
								.build(),
						ResourceAllocation.builder()
								.resourceId(ResourceIdAndType.machine(ResourceId.ofRepoId(1)))
								.projectResourceId(WOProjectResourceId.ofRepoId(1, 2))
								.dateRange(dateRange2)
								.build()
				)
		);

		final ResourceAllocationConflicts conflicts = allocations.findActualConflicts();
		System.out.println(conflicts);
		return conflicts;
	}

	@Test
	void notIntersecting() {expect.serializer("orderedJson").toMatchSnapshot(setup(allDay(2, 3), allDay(4, 5)));}

	@Test
	void adjacent() {expect.serializer("orderedJson").toMatchSnapshot(setup(allDay(2, 3), allDay(3, 4)));}

	@Test
	void intersecting() {expect.serializer("orderedJson").toMatchSnapshot(setup(allDay(2, 4), allDay(3, 5)));}

	@Test
	void including() {expect.serializer("orderedJson").toMatchSnapshot(setup(allDay(2, 10), allDay(3, 5)));}

	@Test
	void included() {expect.serializer("orderedJson").toMatchSnapshot(setup(allDay(2, 10), allDay(1, 11)));}
}
