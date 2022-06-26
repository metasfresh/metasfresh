package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResourceSimulation;
import de.metas.project.workorder.WOProjectStepAndResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectStepSimulation;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Resource_Simulation;
import org.compiere.model.I_C_Project_WO_Step_Simulation;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.function.UnaryOperator;

@Repository
public class WOProjectSimulationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<SimulationPlanId, WOProjectSimulationPlan> simulationPlans = CCache.<SimulationPlanId, WOProjectSimulationPlan>builder()
			.tableName(I_C_Project_WO_Step_Simulation.Table_Name)
			.additionalTableNameToResetFor(I_C_Project_WO_Resource_Simulation.Table_Name)
			.build();

	public WOProjectSimulationPlan getSimulationPlanById(@NonNull SimulationPlanId simulationPlanId)
	{
		return simulationPlans.getOrLoad(simulationPlanId, this::retrieveSimulationPlanById);
	}

	private void changeSimulationPlanById(
			@NonNull SimulationPlanId simulationPlanId,
			@NonNull UnaryOperator<WOProjectSimulationPlan> mapper)
	{
		WOProjectSimulationPlan simulationPlan = getSimulationPlanById(simulationPlanId);
		final WOProjectSimulationPlan changedSimulationPlan = mapper.apply(simulationPlan);
		if (changedSimulationPlan == null)
		{
			throw new AdempiereException("Changing simulation plan to null not allowed");
		}

		savePlan(changedSimulationPlan);
	}

	private WOProjectSimulationPlan retrieveSimulationPlanById(@NonNull SimulationPlanId simulationPlanId)
	{
		final ImmutableMap<WOProjectStepId, WOProjectStepSimulation> stepsById = queryBL
				.createQueryBuilder(I_C_Project_WO_Step_Simulation.class)
				.addEqualsFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_SimulationPlan_ID, simulationPlanId)
				.stream()
				.map(WOProjectSimulationRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(WOProjectStepSimulation::getStepId, step -> step));

		final ImmutableMap<WOProjectResourceId, WOProjectResourceSimulation> projectResourcesById = queryBL
				.createQueryBuilder(I_C_Project_WO_Resource_Simulation.class)
				.addEqualsFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_SimulationPlan_ID, simulationPlanId)
				.stream()
				.map(WOProjectSimulationRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(WOProjectResourceSimulation::getProjectResourceId, resource -> resource));

		return WOProjectSimulationPlan.builder()
				.simulationPlanId(simulationPlanId)
				.stepsById(stepsById)
				.projectResourcesById(projectResourcesById)
				.build();
	}

	private static WOProjectStepSimulation fromRecord(@NonNull final I_C_Project_WO_Step_Simulation record)
	{
		return WOProjectStepSimulation.builder()
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.stepId(WOProjectStepId.ofRepoId(record.getC_Project_WO_Step_ID()))
				.dateRange(CalendarDateRange.builder()
						.startDate(record.getDateStart().toInstant())
						.endDate(record.getDateEnd().toInstant())
						.build())
				.build();
	}

	private void updateRecord(final I_C_Project_WO_Step_Simulation record, final WOProjectStepSimulation from)
	{
		record.setC_Project_ID(from.getProjectId().getRepoId());
		record.setC_Project_WO_Step_ID(from.getStepId().getRepoId());
		//
		record.setDateStart(TimeUtil.asTimestamp(from.getDateRange().getStartDate()));
		record.setDateEnd(TimeUtil.asTimestamp(from.getDateRange().getEndDate()));
	}

	private static WOProjectResourceSimulation fromRecord(@NonNull final I_C_Project_WO_Resource_Simulation record)
	{
		return WOProjectResourceSimulation.builder()
				.projectStepAndResourceId(WOProjectStepAndResourceId.of(
						ProjectId.ofRepoId(record.getC_Project_ID()),
						WOProjectStepId.ofRepoId(record.getC_Project_WO_Step_ID()),
						WOProjectResourceId.ofRepoId(record.getC_Project_WO_Resource_ID())))
				.dateRange(CalendarDateRange.builder()
						.startDate(record.getAssignDateFrom().toInstant())
						.endDate(record.getAssignDateTo().toInstant())
						.allDay(record.isAllDay())
						.build())
				.build();
	}

	private void updateRecord(final I_C_Project_WO_Resource_Simulation record, final WOProjectResourceSimulation from)
	{
		record.setC_Project_ID(from.getProjectStepAndResourceId().getProjectId().getRepoId());
		record.setC_Project_WO_Step_ID(from.getProjectStepAndResourceId().getStepId().getRepoId());
		record.setC_Project_WO_Resource_ID(from.getProjectStepAndResourceId().getProjectResourceId().getRepoId());
		//
		record.setAssignDateFrom(TimeUtil.asTimestamp(from.getDateRange().getStartDate()));
		record.setAssignDateTo(TimeUtil.asTimestamp(from.getDateRange().getEndDate()));
		record.setIsAllDay(from.getDateRange().isAllDay());
	}

	public void savePlan(@NonNull WOProjectSimulationPlan plan)
	{
		final HashMap<WOProjectStepId, I_C_Project_WO_Step_Simulation> existingStepRecordsById = queryBL
				.createQueryBuilder(I_C_Project_WO_Step_Simulation.class)
				.addEqualsFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_SimulationPlan_ID, plan.getSimulationPlanId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> WOProjectStepId.ofRepoId(record.getC_Project_WO_Step_ID())));

		final HashMap<WOProjectResourceId, I_C_Project_WO_Resource_Simulation> existingProjectResourceRecordsById = queryBL
				.createQueryBuilder(I_C_Project_WO_Resource_Simulation.class)
				.addEqualsFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_SimulationPlan_ID, plan.getSimulationPlanId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> WOProjectResourceId.ofRepoId(record.getC_Project_WO_Resource_ID())));

		for (final WOProjectStepSimulation step : plan.getStepsById().values())
		{
			I_C_Project_WO_Step_Simulation record = existingStepRecordsById.remove(step.getStepId());
			if (record == null)
			{
				record = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Step_Simulation.class);
				record.setC_SimulationPlan_ID(plan.getSimulationPlanId().getRepoId());
			}

			updateRecord(record, step);
			InterfaceWrapperHelper.save(record);
		}

		for (final WOProjectResourceSimulation resource : plan.getProjectResourcesById().values())
		{
			I_C_Project_WO_Resource_Simulation record = existingProjectResourceRecordsById.remove(resource.getProjectResourceId());
			if (record == null)
			{
				record = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Resource_Simulation.class);
				record.setC_SimulationPlan_ID(plan.getSimulationPlanId().getRepoId());
			}

			updateRecord(record, resource);
			InterfaceWrapperHelper.save(record);
		}

		InterfaceWrapperHelper.deleteAll(existingStepRecordsById.entrySet());
		InterfaceWrapperHelper.deleteAll(existingProjectResourceRecordsById.entrySet());

		// NOTE: cache will be invalidated automatically
	}

	public void copySimulationDataTo(
			@NonNull final SimulationPlanId fromSimulationId,
			@NonNull final SimulationPlanId toSimulationId)
	{
		final WOProjectSimulationPlan fromSimulationPlan = getSimulationPlanById(fromSimulationId);
		changeSimulationPlanById(
				toSimulationId,
				toSimulation -> toSimulation.mergeFrom(fromSimulationPlan));
	}
}
