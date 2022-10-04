package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.conflicts.CalendarConflictChangesEvent;
import de.metas.calendar.conflicts.CalendarEntryConflict;
import de.metas.calendar.conflicts.CalendarEntryConflicts;
import de.metas.project.workorder.calendar.BudgetAndWOCalendarEntryIdConverters;
import de.metas.project.workorder.resource.WOProjectResourceId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
class WOProjectCalendarConflictsConverters
{
	public static CalendarEntryConflicts toCalendarEntryConflicts(final ResourceAllocationConflicts from)
	{
		if (from.isEmpty())
		{
			return CalendarEntryConflicts.EMPTY;
		}

		return CalendarEntryConflicts.ofList(
				from.stream()
						.map(WOProjectCalendarConflictsConverters::toCalendarEntryConflict)
						.collect(ImmutableList.toImmutableList())
		);
	}

	public static CalendarEntryConflict toCalendarEntryConflict(final ResourceAllocationConflict conflict)
	{
		return CalendarEntryConflict.builder()
				.entryId1(BudgetAndWOCalendarEntryIdConverters.from(conflict.getProjectResourceIdsPair().getProjectResourceId1()))
				.entryId2(BudgetAndWOCalendarEntryIdConverters.from(conflict.getProjectResourceIdsPair().getProjectResourceId2()))
				.simulationId(conflict.getSimulationId())
				.status(conflict.getStatus().toCalendarEntryConflictStatus())
				.build();
	}

	public static CalendarConflictChangesEvent toEvent(
			@NonNull final ResourceAllocationConflicts conflicts,
			@NonNull final ImmutableSet<WOProjectResourceId> projectResourceIds)
	{
		return CalendarConflictChangesEvent.builder()
				.simulationId(conflicts.getSimulationId())
				.affectedEntryIds(toCalendarEntryIds(projectResourceIds))
				.conflicts(toCalendarEntryConflicts(conflicts))
				.build();
	}

	private static ImmutableSet<CalendarEntryId> toCalendarEntryIds(final @NonNull Set<WOProjectResourceId> projectResourceIds)
	{
		return projectResourceIds.stream()
				.map(BudgetAndWOCalendarEntryIdConverters::from)
				.collect(ImmutableSet.toImmutableSet());
	}

}
