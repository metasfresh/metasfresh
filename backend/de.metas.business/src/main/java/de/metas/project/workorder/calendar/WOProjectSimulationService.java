package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.error.IErrorManager;
import de.metas.product.ResourceId;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectSteps;
import de.metas.project.workorder.step.WOStepResources;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;

@Service
public class WOProjectSimulationService
{
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	private final WOProjectService woProjectService;
	private final WOProjectSimulationRepository woProjectSimulationRepository;
	private final WOProjectConflictService woProjectConflictService;
	private final SimulationPlanService simulationService;

	public WOProjectSimulationService(
			final @NonNull WOProjectService woProjectService,
			final @NonNull WOProjectSimulationRepository woProjectSimulationRepository,
			final @NonNull WOProjectConflictService woProjectConflictService,
			final @NonNull SimulationPlanService simulationService)
	{
		this.woProjectService = woProjectService;
		this.woProjectSimulationRepository = woProjectSimulationRepository;
		this.woProjectConflictService = woProjectConflictService;
		this.simulationService = simulationService;
	}

	public void initializeSimulationForResources(
			@NonNull final UserId simulationResponsibleUserId,
			@NonNull final ImmutableList<WOProjectResource> projectResourceList)
	{
		final ImmutableSet<WOProjectStepId> stepIds = projectResourceList
				.stream()
				.map(WOProjectResource::getWoProjectStepId)
				.collect(ImmutableSet.toImmutableSet());

		final Map<WOProjectStepId, WOProjectStep> stepById = Maps.uniqueIndex(woProjectService.getStepsByIds(stepIds), WOProjectStep::getWoProjectStepId);

		projectResourceList
				.stream()
				.filter(resource -> resource.getDateRange() == null)
				.sorted(Comparator.comparingInt(resource -> stepById.get(resource.getWoProjectStepId()).getSeqNo()))
				.forEachOrdered(resource -> initializeSimulationForResource(simulationResponsibleUserId, resource));
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

	public void deleteStepAndResourceFromSimulation(@NonNull final WOProjectStepId stepId, @NonNull final SimulationPlanId simulationPlanId)
	{
		final WOStepResources woStepResources = woProjectService.getWOStepResources(stepId);

		final WOProjectSimulationPlan woProjectSimulationPlan = woProjectSimulationRepository.getById(simulationPlanId);

		woProjectSimulationRepository.savePlan(woProjectSimulationPlan.removeSimulationForStepAndResources(woStepResources));
	}

	@NonNull
	public ImmutableList<WOProjectSimulationPlan> getSimulationPlansForStep(@NonNull final WOProjectStepId stepId)
	{
		return woProjectSimulationRepository.getSimulationPlansForStep(stepId);
	}

	@NonNull
	private CalendarDateRange suggestSimulatedDateRange(
			@NonNull final WOProjectSimulationPlanEditor simulationPlanEditor,
			@NonNull final WOProjectStepId woProjectStepId)
	{
		try
		{
			return simulationPlanEditor.suggestDateRange(woProjectStepId);
		}
		catch (final AdempiereException e)
		{
			final TableRecordReference recordReference = TableRecordReference.of(I_C_Project.Table_Name, woProjectStepId.getProjectId());
			e.setRecord(recordReference);

			errorManager.createIssue(e);
			throw e;
		}
	}

	private void initializeSimulationForResource(
			@NonNull final UserId simulationResponsibleUserId,
			@NonNull final WOProjectResource woProjectResource)
	{
		final SimulationPlanRef masterSimulationPlan = simulationService.getOrCreateMainSimulationPlan(simulationResponsibleUserId,
																									   woProjectResource.getOrgId());

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
																  suggestSimulatedDateRange(simulationPlanEditor, woProjectResource.getWoProjectStepId()),
																  woProjectResource.getWoProjectStepId());

		final WOProjectSimulationPlan changedSimulation = simulationPlanEditor.toNewSimulationPlan();
		savePlan(changedSimulation);

		woProjectConflictService.checkSimulationConflicts(changedSimulation, simulationPlanEditor.getAffectedResourceIds());
	}
}
