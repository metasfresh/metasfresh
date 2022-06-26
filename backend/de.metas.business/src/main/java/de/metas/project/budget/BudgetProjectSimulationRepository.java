package de.metas.project.budget;

import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.project.ProjectId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_Resource_Budget_Simulation;
import org.compiere.model.I_C_Project_WO_Resource_Simulation;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

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

	private BudgetProjectSimulationPlan changeSimulationPlanById(
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
		return changedSimulationPlan;
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
				.projectAndResourceId(BudgetProjectAndResourceId.of(
						ProjectId.ofRepoId(record.getC_Project_ID()),
						BudgetProjectResourceId.ofRepoId(record.getC_Project_Resource_Budget_ID())))
				.dateRange(CalendarDateRange.builder()
						.startDate(record.getDateStartPlan().toInstant())
						.endDate(record.getDateFinishPlan().toInstant())
						.allDay(true)
						.build())
				.build();
	}

	private static void updateRecord(final I_C_Project_Resource_Budget_Simulation record, final BudgetProjectResourceSimulation from)
	{
		record.setC_Project_ID(from.getProjectAndResourceId().getProjectId().getRepoId());
		record.setC_Project_Resource_Budget_ID(from.getProjectAndResourceId().getProjectResourceId().getRepoId());
		record.setDateStartPlan(TimeUtil.asTimestamp(from.getDateRange().getStartDate()));
		record.setDateFinishPlan(TimeUtil.asTimestamp(from.getDateRange().getEndDate()));
	}

	public void savePlan(@NonNull final BudgetProjectSimulationPlan plan)
	{
		final HashMap<BudgetProjectResourceId, I_C_Project_Resource_Budget_Simulation> existingRecords = queryBL
				.createQueryBuilder(I_C_Project_Resource_Budget_Simulation.class)
				.addEqualsFilter(I_C_Project_Resource_Budget_Simulation.COLUMNNAME_C_SimulationPlan_ID, plan.getSimulationPlanId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> BudgetProjectResourceId.ofRepoId(record.getC_Project_Resource_Budget_ID())));

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

	public BudgetProjectResourceSimulation createOrUpdate(@NonNull final BudgetProjectResourceSimulation.UpdateRequest request)
	{
		final BudgetProjectSimulationPlan changedSimulation = changeSimulationPlanById(
				request.getSimulationId(),
				simulationPlan -> simulationPlan.mapByProjectResourceId(
						request.getProjectAndResourceId(),
						existingSimulation -> BudgetProjectResourceSimulation.reduce(existingSimulation, request)
				)
		);

		return changedSimulation.getByProjectResourceId(request.getProjectAndResourceId().getProjectResourceId());
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
