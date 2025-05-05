package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.CalendarConflictsQuery;
import de.metas.calendar.conflicts.CalendarConflictsService;
import de.metas.calendar.conflicts.CalendarEntryConflicts;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.calendar.WOProjectsCalendarQueryExecutor;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static de.metas.project.workorder.conflicts.WOProjectCalendarConflictsConverters.toCalendarEntryConflicts;

@Component
@RequiredArgsConstructor
public class WOProjectCalendarConflictsService implements CalendarConflictsService
{
	@NonNull private final ResourceService resourceService;
	@NonNull private final WOProjectConflictService woProjectConflictService;

	@Override
	public CalendarEntryConflicts query(@NonNull final CalendarConflictsQuery query)
	{
		final InSetPredicate<ResourceIdAndType> resourceIdAndTypes = WOProjectsCalendarQueryExecutor.getResourceIdsPredicate(query.getResourceIds(), resourceService);
		if (resourceIdAndTypes.isNone())
		{
			return CalendarEntryConflicts.EMPTY;
		}

		final SimulationPlanId simulationId = query.getSimulationId();

		return toCalendarEntryConflicts(woProjectConflictService.getActualAndSimulation(simulationId, resourceIdAndTypes));
	}

	@Override
	public void checkAllConflicts()
	{
		final ImmutableSet<ResourceIdAndType> resourceIds = resourceService.getAllActiveResources()
				.stream()
				.map(Resource::getResourceId)
				.flatMap(ResourceIdAndType::allTypes)
				.collect(ImmutableSet.toImmutableSet());

		woProjectConflictService.checkAllConflicts(resourceIds);
	}
}
