package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.logging.LogManager;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.util.GuavaCollectors;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Resource_Conflict;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public class WOProjectResourceConflictRepository
{
	private static final Logger logger = LogManager.getLogger(WOProjectResourceConflictRepository.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(
			@NonNull final ResourceAllocationConflicts conflicts,
			@NonNull final Set<WOProjectResourceId> projectResourceIds)
	{
		final SimulationPlanId simulationId = conflicts.getSimulationId();

		final HashMap<ProjectResourceIdsPair, I_C_Project_WO_Resource_Conflict> recordsByResourceIds = streamRecords(
				Collections.singleton(simulationId),
				null,
				projectResourceIds)
				.collect(GuavaCollectors.toHashMapByKey(WOProjectResourceConflictRepository::extractProjectResourceIds));

		for (final ResourceAllocationConflict conflict : conflicts.toCollection())
		{
			if (simulationId != null && !conflict.isSimulation())
			{
				logger.warn("Skip saving actual conflict when saving simulation conflicts: {}", conflict);
				continue;
			}

			final ProjectResourceIdsPair resourceIdsPair = conflict.getProjectResourceIdsPair();

			I_C_Project_WO_Resource_Conflict record = recordsByResourceIds.remove(resourceIdsPair);
			if (record == null)
			{
				record = newRecord(resourceIdsPair, simulationId);
			}

			record.setStatus(conflict.getStatus().getCode());
			conflict.getApproved().ifPresent(record::setIsApproved);

			InterfaceWrapperHelper.saveRecord(record);
		}

		InterfaceWrapperHelper.deleteAll(recordsByResourceIds.values());
	}

	@NonNull
	private I_C_Project_WO_Resource_Conflict newRecord(final @NonNull ProjectResourceIdsPair resourceIdsPair, final @Nullable SimulationPlanId simulationId)
	{
		I_C_Project_WO_Resource_Conflict record = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Resource_Conflict.class);

		record.setC_Project_ID(resourceIdsPair.getProjectResourceId1().getProjectId().getRepoId());
		record.setC_Project_WO_Resource_ID(resourceIdsPair.getProjectResourceId1().getRepoId());

		record.setC_Project2_ID(resourceIdsPair.getProjectResourceId2().getProjectId().getRepoId());
		record.setC_Project_WO_Resource2_ID(resourceIdsPair.getProjectResourceId2().getRepoId());

		record.setC_SimulationPlan_ID(SimulationPlanId.toRepoId(simulationId));

		record.setIsApproved(false);

		return record;
	}

	public ResourceAllocationConflicts getActualConflicts(
			@NonNull final Set<WOProjectResourceId> projectResourceIds)
	{
		if (projectResourceIds.isEmpty())
		{
			return ResourceAllocationConflicts.empty();
		}

		final ImmutableList<ResourceAllocationConflict> conflicts = streamRecords(
				Collections.singleton(null),
				null,
				projectResourceIds)
				.map(WOProjectResourceConflictRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return ResourceAllocationConflicts.actualConflicts(conflicts);
	}

	public ResourceAllocationConflicts getActualAndSimulation(@Nullable final SimulationPlanId simulationId, @NonNull final Set<ProjectId> onlyProjectIds)
	{
		final ArrayList<ResourceAllocationConflict> actualConflictsList = new ArrayList<>();
		final ArrayList<ResourceAllocationConflict> simulationOnlyConflictsList = new ArrayList<>();

		streamRecords(
				Arrays.asList(null, simulationId),
				onlyProjectIds,
				null)
				.map(WOProjectResourceConflictRepository::fromRecord)
				.forEach(conflict -> {
					if (!conflict.isSimulation())
					{
						actualConflictsList.add(conflict);
					}
					else
					{
						simulationOnlyConflictsList.add(conflict);
					}
				});

		final ResourceAllocationConflicts actualConflicts = ResourceAllocationConflicts.actualConflicts(actualConflictsList);
		if (simulationId == null)
		{
			return actualConflicts;
		}

		final ResourceAllocationConflicts simulationOnlyConflicts = ResourceAllocationConflicts.of(simulationId, simulationOnlyConflictsList);

		return actualConflicts.mergeWithSimulation(simulationOnlyConflicts);
	}

	public ResourceAllocationConflicts getSimulationOnly(@NonNull final SimulationPlanId simulationId)
	{
		final ImmutableList<ResourceAllocationConflict> conflictsList = streamRecords(ImmutableList.of(simulationId), null, null)
				.map(WOProjectResourceConflictRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return ResourceAllocationConflicts.of(simulationId, conflictsList);
	}

	private Stream<I_C_Project_WO_Resource_Conflict> streamRecords(
			@Nullable final Collection<SimulationPlanId> simulationIds,
			@Nullable final Set<ProjectId> onlyProjectIds,
			@Nullable final Set<WOProjectResourceId> onlyProjectResourceIds)
	{
		final IQueryBuilder<I_C_Project_WO_Resource_Conflict> queryBuilder = queryBL.createQueryBuilder(I_C_Project_WO_Resource_Conflict.class)
				.addOnlyActiveRecordsFilter();

		if (simulationIds != null && !simulationIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_SimulationPlan_ID, simulationIds);
		}

		if (onlyProjectIds != null && !onlyProjectIds.isEmpty())
		{
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addInArrayFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_ID, onlyProjectIds)
					.addInArrayFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project2_ID, onlyProjectIds);
		}

		if (onlyProjectResourceIds != null && !onlyProjectResourceIds.isEmpty())
		{
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addInArrayFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_WO_Resource_ID, onlyProjectResourceIds)
					.addInArrayFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_WO_Resource2_ID, onlyProjectResourceIds);
		}

		return queryBuilder.stream();
	}

	private static ResourceAllocationConflict fromRecord(@NonNull final I_C_Project_WO_Resource_Conflict record)
	{
		return ResourceAllocationConflict.builder()
				.projectResourceIdsPair(extractProjectResourceIds(record))
				.simulationId(SimulationPlanId.ofRepoIdOrNull(record.getC_SimulationPlan_ID()))
				.status(ResourceAllocationConflictStatus.ofCode(record.getStatus()))
				.approved(OptionalBoolean.ofBoolean(record.isApproved()))
				.build();
	}

	@NonNull
	private static ProjectResourceIdsPair extractProjectResourceIds(final @NonNull I_C_Project_WO_Resource_Conflict record)
	{
		return ProjectResourceIdsPair.of(
				WOProjectResourceId.ofRepoId(record.getC_Project_ID(), record.getC_Project_WO_Resource_ID()),
				WOProjectResourceId.ofRepoId(record.getC_Project2_ID(), record.getC_Project_WO_Resource2_ID())
		);
	}

	public void deactivateBySimulationId(@NonNull final SimulationPlanId simulationId)
	{
		queryBL.createQueryBuilder(I_C_Project_WO_Resource_Conflict.class)
				.addEqualsFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_SimulationPlan_ID, simulationId)
				.addOnlyActiveRecordsFilter()
				.create()
				.update(record -> {
					record.setIsActive(false);
					return IQueryUpdater.MODEL_UPDATED;
				});
	}
}
