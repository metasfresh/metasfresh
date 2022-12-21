package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.util.time.SystemTime;
import de.metas.product.ResourceId;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectSteps;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

import static de.metas.project.ProjectConstants.DEFAULT_DURATION;

@Service
public class WOProjectSimulationService
{
	private final WOProjectService woProjectService;

	private final WOProjectSimulationRepository woProjectSimulationRepository;
	private final WOProjectConflictService woProjectConflictService;

	public WOProjectSimulationService(
			final @NonNull WOProjectService woProjectService,
			final @NonNull WOProjectSimulationRepository woProjectSimulationRepository,
			final @NonNull WOProjectConflictService woProjectConflictService)
	{
		this.woProjectService = woProjectService;
		this.woProjectSimulationRepository = woProjectSimulationRepository;
		this.woProjectConflictService = woProjectConflictService;
	}

	public void initializeSimulationForResource(
			@NonNull final WOProjectResource woProjectResource,
			@NonNull final SimulationPlanRef masterSimulationPlan)
	{
		final WOProjectSimulationPlan woProjectSimulationPlan = getSimulationPlanById(masterSimulationPlan.getId());
		if (woProjectSimulationPlan.getProjectResourceByIdOrNull(woProjectResource.getWoProjectResourceId()) != null)
		{
			return;
		}

		final WOProjectResourceId projectResourceId = woProjectResource.getWoProjectResourceId();
		final WOProjectSteps woProjectSteps = woProjectService.getStepsByProjectId(projectResourceId.getProjectId());
		
		final WOProjectSimulationPlanEditor simulationPlanEditor = WOProjectSimulationPlanEditor.builder()
				.project(woProjectService.getById(projectResourceId.getProjectId()))
				.steps(woProjectSteps)
				.projectResources(woProjectService.getResourcesByProjectId(projectResourceId.getProjectId()))
				.currentSimulationPlan(woProjectSimulationPlan)
				.build();

		simulationPlanEditor.changeResourceDateRangeAndShiftSteps(projectResourceId,
																  getSimulationDateRange(woProjectSteps.getById(woProjectResource.getWoProjectStepId()),
																						 simulationPlanEditor.getProject()),
																  woProjectResource.getWoProjectStepId());

		final WOProjectSimulationPlan changedSimulation = simulationPlanEditor.toNewSimulationPlan();
		savePlan(changedSimulation);

		woProjectConflictService.checkSimulationConflicts(changedSimulation, simulationPlanEditor.getAffectedResourceIds());
	}

	public WOProjectSimulationPlan getSimulationPlanById(@NonNull final SimulationPlanId simulationId)
	{
		return woProjectSimulationRepository.getById(simulationId);
	}

	public void savePlan(final WOProjectSimulationPlan plan)
	{
		woProjectSimulationRepository.savePlan(plan);
	}

	public WOProjectSimulationPlan copySimulationDataTo(
			@NonNull final SimulationPlanId fromSimulationId,
			@NonNull final SimulationPlanId toSimulationId)
	{
		return woProjectSimulationRepository.copySimulationDataTo(fromSimulationId, toSimulationId);
	}

	public void completeSimulation(@NonNull final SimulationPlanId simulationId)
	{
		WOProjectSimulationCompleteCommand.builder()
				.woProjectService(woProjectService)
				.woProjectSimulationRepository(woProjectSimulationRepository)
				.woProjectConflictService(woProjectConflictService)
				.simulationId(simulationId)
				.build()
				.execute();
	}

	public void checkSimulationConflicts(@NonNull final WOProjectSimulationPlan simulationPlan)
	{
		final ImmutableSet<ResourceId> resourceIds = woProjectService.getResourceIdsByProjectResourceIds(simulationPlan.getProjectResourceIds());
		if (resourceIds.isEmpty())
		{
			return;
		}

		woProjectConflictService.checkSimulationConflicts(simulationPlan, resourceIds);
	}

	@NonNull
	private CalendarDateRange getSimulationDateRange(
			@NonNull final WOProjectStep woProjectStep,
			@NonNull final WOProject woProject)
	{
		return Optional.ofNullable(woProjectStep.getDateRange())
				.orElseGet(() -> woProject.getCalendarDateRange()
						.orElseGet(() -> {
							final Instant defaultStartDate = SystemTime.asInstant();

							return CalendarDateRange.builder()
									.startDate(defaultStartDate)
									.endDate(defaultStartDate.plus(DEFAULT_DURATION))
									.allDay(false)
									.build();
						}));
	}
}
