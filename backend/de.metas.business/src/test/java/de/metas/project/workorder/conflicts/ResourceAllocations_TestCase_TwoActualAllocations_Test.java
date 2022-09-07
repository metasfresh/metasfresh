package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.product.ResourceId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.test.SnapshotFunctionFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static de.metas.project.workorder.conflicts.ResourceAllocationsTestUtils.allDay;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

public class ResourceAllocations_TestCase_TwoActualAllocations_Test
{
	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, SnapshotFunctionFactory.newFunction());
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	ResourceAllocationConflicts setup(CalendarDateRange dateRange1, CalendarDateRange dateRange2)
	{
		final ResourceAllocations allocations = ResourceAllocations.of(
				null,
				ImmutableList.of(
						ResourceAllocation.builder()
								.resourceId(ResourceId.ofRepoId(1))
								.projectResourceId(WOProjectResourceId.ofRepoId(1, 1))
								.dateRange(dateRange1)
								.build(),
						ResourceAllocation.builder()
								.resourceId(ResourceId.ofRepoId(1))
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
	void notIntersecting() {expect(setup(allDay(2, 3), allDay(4, 5))).toMatchSnapshot();}

	@Test
	void adjacent() {expect(setup(allDay(2, 3), allDay(3, 4))).toMatchSnapshot();}

	@Test
	void intersecting() {expect(setup(allDay(2, 4), allDay(3, 5))).toMatchSnapshot();}

	@Test
	void including() {expect(setup(allDay(2, 10), allDay(3, 5))).toMatchSnapshot();}

	@Test
	void included() {expect(setup(allDay(2, 10), allDay(1, 11))).toMatchSnapshot();}
}
