package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.error.IErrorManager;
import de.metas.product.ResourceId;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepSimulation;
import de.metas.project.workorder.step.WOProjectSteps;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WOProjectSimulationService
{
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

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

		final WOProjectStepId currentStepId = woProjectResource.getWoProjectStepId();

		simulationPlanEditor.changeResourceDateRangeAndShiftSteps(projectResourceId,
																  computeCalendarDateRange(simulationPlanEditor, currentStepId),
																  currentStepId);

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

	public void deleteResourceForStepSimulation(@NonNull final WOProjectStepSimulation stepSimulation, @NonNull final SimulationPlanId simulationPlanId)
	{
		final Set<WOProjectResourceId> resourceIds = woProjectService.getResourcesByProjectId(stepSimulation.getProjectId())
				.stream()
				.filter(woProjectResource -> woProjectResource.getWoProjectStepId().equals(stepSimulation.getStepId()))
				.map(WOProjectResource::getWoProjectResourceId)
				.collect(ImmutableSet.toImmutableSet());

		final WOProjectSimulationPlan woProjectSimulationPlan = woProjectSimulationRepository.getById(simulationPlanId);

		final WOProjectSimulationPlan updatedWoProjectSimulationPlan = woProjectSimulationPlan.removeResourceSimulation(resourceIds);

		woProjectSimulationRepository.savePlan(updatedWoProjectSimulationPlan);
	}

	@NonNull
	public CalendarDateRange computeCalendarDateRange(@NonNull final WOProjectSimulationPlanEditor simulationPlanEditor, @NonNull final WOProjectStepId woProjectStepId)
	{
		try
		{
			return simulationPlanEditor.computeCalendarDateRange(woProjectStepId);
		}
		catch (final AdempiereException e)
		{
			final TableRecordReference recordReference = TableRecordReference.of(I_C_Project.Table_Name, woProjectStepId.getProjectId());
			e.setRecord(recordReference);

			errorManager.createIssue(e);
			throw e;
		}
	}
}
