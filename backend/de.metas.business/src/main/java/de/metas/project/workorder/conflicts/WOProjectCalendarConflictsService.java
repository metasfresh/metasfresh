package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.conflicts.CalendarConflictsService;
import de.metas.calendar.conflicts.CalendarEntryConflict;
import de.metas.calendar.conflicts.CalendarEntryConflicts;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.calendar.BudgetAndWOCalendarEntryIdConverters;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class WOProjectCalendarConflictsService implements CalendarConflictsService
{
	private final WOProjectConflictService woProjectConflictService;

	public WOProjectCalendarConflictsService(final WOProjectConflictService woProjectConflictService) {this.woProjectConflictService = woProjectConflictService;}

	@Override
	public CalendarEntryConflicts query(@Nullable final SimulationPlanId simulationId)
	{
		return toCalendarEntryConflicts(woProjectConflictService.getActualAndSimulation(simulationId));
	}

	private static CalendarEntryConflicts toCalendarEntryConflicts(final ResourceAllocationConflicts from)
	{
		if (from.isEmpty())
		{
			return CalendarEntryConflicts.EMPTY;
		}

		return CalendarEntryConflicts.ofList(
				from.stream()
						.map(WOProjectCalendarConflictsService::toCalendarEntryConflict)
						.collect(ImmutableList.toImmutableList())
		);
	}

	private static CalendarEntryConflict toCalendarEntryConflict(final ResourceAllocationConflict conflict)
	{
		return CalendarEntryConflict.builder()
				.entryId1(BudgetAndWOCalendarEntryIdConverters.from(conflict.getProjectResourceIdsPair().getProjectResourceId1()))
				.entryId2(BudgetAndWOCalendarEntryIdConverters.from(conflict.getProjectResourceIdsPair().getProjectResourceId2()))
				.simulationId(conflict.getSimulationId())
				.status(conflict.getStatus().toCalendarEntryConflictStatus())
				.build();
	}

}
