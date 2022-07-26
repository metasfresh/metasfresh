package de.metas.project.budget;

import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.OldAndNewValues;
import org.compiere.model.I_C_Project_Resource_Budget_Simulation;
import org.compiere.model.I_C_Project_WO_Resource_Simulation;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.function.UnaryOperator;

@Service
public class BudgetProjectSimulationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<SimulationPlanId, BudgetProjectSimulationPlan> cache = CCache.<SimulationPlanId, BudgetProjectSimulationPlan>builder()
			.tableName(I_C_Project_WO_Resource_Simulation.Table_Name)
			.build();

	public BudgetProjectSimulationPlan getSimulationPlanById(@NonNull SimulationPlanId simulationPlanId)
	{
		return cache.getOrLoad(simulationPlanId, this::retrieveSimulationPlanById);
	}

	private void changeSimulationPlanById(
			@NonNull SimulationPlanId simulationPlanId,
			@NonNull UnaryOperator<BudgetProjectSimulationPlan> mapper)
	{
		BudgetProjectSimulationPlan simulationPlan = getSimulationPlanById(simulationPlanId);
		final BudgetProjectSimulationPlan changedSimulationPlan = mapper.apply(simulationPlan);
		if (changedSimulationPlan == null)
		{
			throw new AdempiereException("Changing simulation plan to null not allowed");
		}

		savePlan(changedSimulationPlan);
	}

	private BudgetProjectSimulationPlan retrieveSimulationPlanById(@NonNull SimulationPlanId simulationPlanId)
	{
		final ImmutableMap<BudgetProjectResourceId, BudgetProjectResourceSimulation> projectResourcesById = queryBL
				.createQueryBuilder(I_C_Project_Resource_Budget_Simulation.class)
				.addEqualsFilter(I_C_Project_Resource_Budget_Simulation.COLUMNNAME_C_SimulationPlan_ID, simulationPlanId)
				.addOnlyActiveRecordsFilter()
				.stream()
				.map(BudgetProjectSimulationRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(BudgetProjectResourceSimulation::getProjectResourceId, projectResource -> projectResource));

		return BudgetProjectSimulationPlan.builder()
				.simulationPlanId(simulationPlanId)
				.projectResourcesById(projectResourcesById)
				.build();
	}

	private static BudgetProjectResourceSimulation fromRecord(@NonNull final I_C_Project_Resource_Budget_Simulation record)
	{
		return BudgetProjectResourceSimulation.builder()
				.projectResourceId(extractBudgetProjectResourceId(record))
				.dateRange(CalendarDateRange.builder()
						.startDate(record.getDateStartPlan().toInstant())
						.endDate(record.getDateFinishPlan().toInstant())
						.allDay(BudgetProjectResourceRepository.IsAllDay_TRUE)
						.build())
				.isAppliedOnActualData(record.isProcessed())
				.dateRangeBeforeApplying(extractDateRangeBeforeApplying(record))
				.build();
	}

	@Nullable
	private static CalendarDateRange extractDateRangeBeforeApplying(final I_C_Project_Resource_Budget_Simulation record)
	{
		final Timestamp dateStartPlan_prev = record.getDateStartPlan_Prev();
		final Timestamp dateFinishPlan_prev = record.getDateFinishPlan_Prev();
		if (dateStartPlan_prev == null || dateFinishPlan_prev == null)
		{
			return null;
		}

		return CalendarDateRange.builder()
				.startDate(dateStartPlan_prev.toInstant())
				.endDate(dateFinishPlan_prev.toInstant())
				.allDay(BudgetProjectResourceRepository.IsAllDay_TRUE)
				.build();
	}

	private static void updateRecord(final I_C_Project_Resource_Budget_Simulation record, final BudgetProjectResourceSimulation from)
	{
		record.setC_Project_ID(from.getProjectResourceId().getProjectId().getRepoId());
		record.setC_Project_Resource_Budget_ID(from.getProjectResourceId().getRepoId());
		record.setDateStartPlan(TimeUtil.asTimestamp(from.getDateRange().getStartDate()));
		record.setDateFinishPlan(TimeUtil.asTimestamp(from.getDateRange().getEndDate()));

		record.setProcessed(from.isAppliedOnActualData());
		record.setDateStartPlan(from.getDateRangeBeforeApplying() != null ? Timestamp.from(from.getDateRangeBeforeApplying().getStartDate()) : null);
		record.setDateFinishPlan(from.getDateRangeBeforeApplying() != null ? Timestamp.from(from.getDateRangeBeforeApplying().getEndDate()) : null);
		//record.setIsAllDay_Prev(from.getDateRangeBeforeApplying() != null && from.getDateRangeBeforeApplying().isAllDay());
	}

	public void savePlan(@NonNull final BudgetProjectSimulationPlan plan)
	{
		final HashMap<BudgetProjectResourceId, I_C_Project_Resource_Budget_Simulation> existingRecords = queryBL
				.createQueryBuilder(I_C_Project_Resource_Budget_Simulation.class)
				.addEqualsFilter(I_C_Project_Resource_Budget_Simulation.COLUMNNAME_C_SimulationPlan_ID, plan.getSimulationPlanId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(BudgetProjectSimulationRepository::extractBudgetProjectResourceId));

		for (final BudgetProjectResourceSimulation resource : plan.getAll())
		{
			I_C_Project_Resource_Budget_Simulation record = existingRecords.remove(resource.getProjectResourceId());
			if (record == null)
			{
				record = InterfaceWrapperHelper.newInstance(I_C_Project_Resource_Budget_Simulation.class);
				record.setC_SimulationPlan_ID(plan.getSimulationPlanId().getRepoId());
			}

			updateRecord(record, resource);

			InterfaceWrapperHelper.save(record);
		}

		InterfaceWrapperHelper.deleteAll(existingRecords.values());
	}

	@NonNull
	private static BudgetProjectResourceId extractBudgetProjectResourceId(final I_C_Project_Resource_Budget_Simulation record)
	{
		return BudgetProjectResourceId.ofRepoId(record.getC_Project_ID(), record.getC_Project_Resource_Budget_ID());
	}

	public OldAndNewValues<BudgetProjectResourceSimulation> createOrUpdate(@NonNull final BudgetProjectResourceSimulation.UpdateRequest request)
	{
		BudgetProjectSimulationPlan simulationPlan = getSimulationPlanById(request.getSimulationId());
		final BudgetProjectSimulationPlan changedSimulationPlan = simulationPlan.mapByProjectResourceId(
				request.getProjectResourceId(),
				existingSimulation -> BudgetProjectResourceSimulation.reduce(existingSimulation, request)
		);

		final BudgetProjectResourceId projectResourceId = request.getProjectResourceId();
		return OldAndNewValues.ofOldAndNewValues(
				simulationPlan.getByProjectResourceIdOrNull(projectResourceId),
				changedSimulationPlan.getByProjectResourceId(projectResourceId)
		);
	}

	public void copySimulationDataTo(
			@NonNull final SimulationPlanId fromSimulationId,
			@NonNull final SimulationPlanId toSimulationId)
	{
		final BudgetProjectSimulationPlan fromSimulationPlan = getSimulationPlanById(fromSimulationId);
		changeSimulationPlanById(
				toSimulationId,
				toSimulation -> toSimulation.mergeFrom(fromSimulationPlan));
	}

}
