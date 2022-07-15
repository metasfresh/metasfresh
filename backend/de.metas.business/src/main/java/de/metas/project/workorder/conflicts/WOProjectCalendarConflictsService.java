package de.metas.project.workorder.conflicts;

import de.metas.calendar.conflicts.CalendarConflictsService;
import de.metas.calendar.conflicts.CalendarEntryConflicts;
import de.metas.calendar.simulation.SimulationPlanId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import static de.metas.project.workorder.conflicts.WOProjectCalendarConflictsConverters.toCalendarEntryConflicts;

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
}
