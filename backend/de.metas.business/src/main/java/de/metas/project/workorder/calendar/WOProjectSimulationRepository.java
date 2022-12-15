package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceSimulation;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepSimulation;
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

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Repository
public class WOProjectSimulationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<SimulationPlanId, WOProjectSimulationPlan> cacheById = CCache.<SimulationPlanId, WOProjectSimulationPlan>builder()
			.tableName(I_C_Project_WO_Step_Simulation.Table_Name)
			.additionalTableNameToResetFor(I_C_Project_WO_Resource_Simulation.Table_Name)
			.build();

	public WOProjectSimulationPlan getById(@NonNull final SimulationPlanId simulationPlanId)
	{
		final Collection<WOProjectSimulationPlan> simulationPlans = cacheById.getAllOrLoad(ImmutableSet.of(simulationPlanId), this::retrieveSimulationPlansByIds);
		if (simulationPlans.size() != 1)
		{
			throw new AdempiereException("No simulation plan found for " + simulationPlanId);
		}
		else
		{
			return simulationPlans.iterator().next();
		}
	}

	public Collection<WOProjectSimulationPlan> getByResourceIdsAndSimulationIds(
			@NonNull final Set<WOProjectResourceId> projectResourceIds,
			@NonNull final Set<SimulationPlanId> simulationIds)
	{
		if (projectResourceIds.isEmpty()
				|| simulationIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList<SimulationPlanId> matchingSimulationIds = queryBL.createQueryBuilder(I_C_Project_WO_Resource_Simulation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Project_WO_Resource_Simulation.COLUMNNAME_C_Project_WO_Resource_ID, projectResourceIds)
				.addInArrayFilter(I_C_Project_WO_Resource_Simulation.COLUMNNAME_C_SimulationPlan_ID, simulationIds)
				.create()
				.listDistinct(I_C_Project_WO_Resource_Simulation.COLUMNNAME_C_SimulationPlan_ID, SimulationPlanId.class);
		if (matchingSimulationIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return cacheById.getAllOrLoad(matchingSimulationIds, this::retrieveSimulationPlansByIds);
	}

	private WOProjectSimulationPlan changeSimulationPlanById(
			@NonNull SimulationPlanId simulationPlanId,
			@NonNull UnaryOperator<WOProjectSimulationPlan> mapper)
	{
		WOProjectSimulationPlan simulationPlan = getById(simulationPlanId);
		final WOProjectSimulationPlan changedSimulationPlan = mapper.apply(simulationPlan);
		if (changedSimulationPlan == null)
		{
			throw new AdempiereException("Changing simulation plan to null not allowed");
		}

		savePlan(changedSimulationPlan);

		return changedSimulationPlan;
	}

	private Map<SimulationPlanId, WOProjectSimulationPlan> retrieveSimulationPlansByIds(@NonNull Set<SimulationPlanId> simulationPlanIds)
	{
		if (simulationPlanIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<SimulationPlanId, ImmutableMap<WOProjectStepId, WOProjectStepSimulation>> steps = queryBL
				.createQueryBuilder(I_C_Project_WO_Step_Simulation.class)
				.addInArrayFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_SimulationPlan_ID, simulationPlanIds)
				.stream()
				.collect(Collectors.groupingBy(
						record -> SimulationPlanId.ofRepoId(record.getC_SimulationPlan_ID()),
						ImmutableMap.toImmutableMap(
								WOProjectSimulationRepository::extractWOProjectStepId,
								WOProjectSimulationRepository::fromRecord)
				));

		final Map<SimulationPlanId, ImmutableMap<WOProjectResourceId, WOProjectResourceSimulation>> projectResources = queryBL
				.createQueryBuilder(I_C_Project_WO_Resource_Simulation.class)
				.addInArrayFilter(I_C_Project_WO_Resource_Simulation.COLUMNNAME_C_SimulationPlan_ID, simulationPlanIds)
				.stream()
				.collect(Collectors.groupingBy(
						record -> SimulationPlanId.ofRepoId(record.getC_SimulationPlan_ID()),
						ImmutableMap.toImmutableMap(
								WOProjectSimulationRepository::extractWOProjectResourceId,
								WOProjectSimulationRepository::fromRecord)
				));

		final ImmutableMap.Builder<SimulationPlanId, WOProjectSimulationPlan> result = ImmutableMap.builder();
		for (final SimulationPlanId simulationPlanId : simulationPlanIds)
		{
			result.put(
					simulationPlanId,
					WOProjectSimulationPlan.builder()
							.simulationPlanId(simulationPlanId)
							.stepsById(steps.get(simulationPlanId))
							.projectResourcesById(projectResources.get(simulationPlanId))
							.build());
		}

		return result.build();
	}

	@NonNull
	private static WOProjectResourceId extractWOProjectResourceId(final I_C_Project_WO_Resource_Simulation record)
	{
		return WOProjectResourceId.ofRepoId(record.getC_Project_ID(), record.getC_Project_WO_Resource_ID());
	}

	@NonNull
	private static WOProjectStepId extractWOProjectStepId(final I_C_Project_WO_Step_Simulation record)
	{
		return WOProjectStepId.ofRepoId(ProjectId.ofRepoId(record.getC_Project_ID()), record.getC_Project_WO_Step_ID());
	}

	private static WOProjectStepSimulation fromRecord(@NonNull final I_C_Project_WO_Step_Simulation record)
	{
		return WOProjectStepSimulation.builder()
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.stepId(extractWOProjectStepId(record))
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
				.projectResourceId(extractWOProjectResourceId(record))
				.dateRange(CalendarDateRange.builder()
						.startDate(record.getAssignDateFrom().toInstant())
						.endDate(record.getAssignDateTo().toInstant())
						.allDay(record.isAllDay())
						.build())
				.isAppliedOnActualData(record.isProcessed())
				.dateRangeBeforeApplying(extractDateRangeBeforeApplying(record))
				.build();
	}

	@Nullable
	private static CalendarDateRange extractDateRangeBeforeApplying(final I_C_Project_WO_Resource_Simulation record)
	{
		final Timestamp assignDateFrom_prev = record.getAssignDateFrom_Prev();
		final Timestamp assignDateTo_prev = record.getAssignDateTo_Prev();
		if (assignDateFrom_prev == null || assignDateTo_prev == null)
		{
			return null;
		}

		return CalendarDateRange.builder()
				.startDate(assignDateFrom_prev.toInstant())
				.endDate(assignDateTo_prev.toInstant())
				.allDay(record.isAllDay_Prev())
				.build();
	}

	private void updateRecord(final I_C_Project_WO_Resource_Simulation record, final WOProjectResourceSimulation from)
	{
		record.setC_Project_ID(from.getProjectResourceId().getProjectId().getRepoId());
		record.setC_Project_WO_Resource_ID(from.getProjectResourceId().getRepoId());

		record.setAssignDateFrom(Timestamp.from(from.getDateRange().getStartDate()));
		record.setAssignDateTo(Timestamp.from(from.getDateRange().getEndDate()));
		record.setIsAllDay(from.getDateRange().isAllDay());

		record.setProcessed(from.isAppliedOnActualData());
		record.setAssignDateFrom_Prev(from.getDateRangeBeforeApplying() != null ? Timestamp.from(from.getDateRangeBeforeApplying().getStartDate()) : null);
		record.setAssignDateTo_Prev(from.getDateRangeBeforeApplying() != null ? Timestamp.from(from.getDateRangeBeforeApplying().getEndDate()) : null);
		record.setIsAllDay_Prev(from.getDateRangeBeforeApplying() != null && from.getDateRangeBeforeApplying().isAllDay());
	}

	public void savePlan(@NonNull final WOProjectSimulationPlan plan)
	{
		final HashMap<WOProjectStepId, I_C_Project_WO_Step_Simulation> existingStepRecordsById = queryBL
				.createQueryBuilder(I_C_Project_WO_Step_Simulation.class)
				.addEqualsFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_SimulationPlan_ID, plan.getSimulationPlanId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(WOProjectSimulationRepository::extractWOProjectStepId));

		final HashMap<WOProjectResourceId, I_C_Project_WO_Resource_Simulation> existingProjectResourceRecordsById = queryBL
				.createQueryBuilder(I_C_Project_WO_Resource_Simulation.class)
				.addEqualsFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_SimulationPlan_ID, plan.getSimulationPlanId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(WOProjectSimulationRepository::extractWOProjectResourceId));

		for (final WOProjectStepSimulation step : plan.getSteps())
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

		for (final WOProjectResourceSimulation resource : plan.getProjectResources())
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

		// NOTE: cache will be invalidated automatically after trx commit
		//cacheById.remove(plan.getSimulationPlanId());
	}

	public WOProjectSimulationPlan copySimulationDataTo(
			@NonNull final SimulationPlanId fromSimulationId,
			@NonNull final SimulationPlanId toSimulationId)
	{
		final WOProjectSimulationPlan fromSimulationPlan = getById(fromSimulationId);
		return changeSimulationPlanById(
				toSimulationId,
				toSimulation -> toSimulation.mergeFrom(fromSimulationPlan));
	}
}
