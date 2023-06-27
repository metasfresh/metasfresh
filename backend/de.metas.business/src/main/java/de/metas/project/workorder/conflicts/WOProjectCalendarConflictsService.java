package de.metas.project.workorder.conflicts;

import de.metas.calendar.CalendarConflictsQuery;
import de.metas.calendar.conflicts.CalendarConflictsService;
import de.metas.calendar.conflicts.CalendarEntryConflicts;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import de.metas.project.workorder.calendar.WOProjectCalendarService;
import de.metas.project.workorder.calendar.WOProjectsCalendarQueryExecutor;
import de.metas.resource.ResourceService;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import static de.metas.project.workorder.conflicts.WOProjectCalendarConflictsConverters.toCalendarEntryConflicts;

@Component
public class WOProjectCalendarConflictsService implements CalendarConflictsService
{
	private final ResourceService resourceService;
	private final ProjectRepository genericProjectRepository;
	private final WOProjectConflictService woProjectConflictService;

	public WOProjectCalendarConflictsService(
			final @NonNull ResourceService resourceService,
			final @NonNull ProjectRepository genericProjectRepository,
			final @NonNull WOProjectConflictService woProjectConflictService)
	{
		this.resourceService = resourceService;
		this.genericProjectRepository = genericProjectRepository;
		this.woProjectConflictService = woProjectConflictService;
	}

	@Override
	public CalendarEntryConflicts query(@NonNull final CalendarConflictsQuery query)
	{
		final InSetPredicate<ResourceId> resourceIds = WOProjectsCalendarQueryExecutor.getResourceIdsPredicate(query.getResourceIds(), resourceService);
		if (resourceIds.isNone())
		{
			return CalendarEntryConflicts.EMPTY;
		}

		final SimulationPlanId simulationId = query.getSimulationId();

		return toCalendarEntryConflicts(woProjectConflictService.getActualAndSimulation(simulationId, resourceIds));
	}
}
