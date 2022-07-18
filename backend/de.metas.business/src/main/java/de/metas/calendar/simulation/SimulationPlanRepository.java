package de.metas.calendar.simulation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_SimulationPlan;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public class SimulationPlanRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<SimulationPlanId, SimulationPlanRef> cacheById = CCache.<SimulationPlanId, SimulationPlanRef>builder()
			.tableName(I_C_SimulationPlan.Table_Name)
			.build();

	private final CCache<Integer, ImmutableSet<SimulationPlanId>> cacheNotProcessedIds = CCache.<Integer, ImmutableSet<SimulationPlanId>>builder()
			.tableName(I_C_SimulationPlan.Table_Name)
			.build();

	public SimulationPlanRef createNewSimulation(
			@NonNull String name,
			@NonNull UserId responsibleUserId)
	{
		final I_C_SimulationPlan record = InterfaceWrapperHelper.newInstance(I_C_SimulationPlan.class);
		record.setName(name);
		record.setAD_User_Responsible_ID(responsibleUserId.getRepoId());
		record.setProcessed(false);
		InterfaceWrapperHelper.saveRecord(record);

		final SimulationPlanRef simulation = fromRecord(record);
		cacheById.put(simulation.getId(), simulation);

		return simulation;
	}

	private static SimulationPlanRef fromRecord(@NonNull final I_C_SimulationPlan record)
	{
		return SimulationPlanRef.builder()
				.id(SimulationPlanId.ofRepoId(record.getC_SimulationPlan_ID()))
				.name(record.getName())
				.responsibleUserId(UserId.ofRepoId(record.getAD_User_Responsible_ID()))
				.processed(record.isProcessed())
				.created(record.getCreated().toInstant())
				.build();
	}

	public SimulationPlanRef getById(@NonNull final SimulationPlanId id)
	{
		return cacheById.getOrLoad(id, this::retrieveById);
	}

	public Collection<SimulationPlanRef> getAllNotProcessed()
	{
		final ImmutableSet<SimulationPlanId> notProcessedIds = cacheNotProcessedIds.getOrLoad(0, this::retrieveNotProcessedIds);
		return cacheById.getAllOrLoad(notProcessedIds, this::retrieveByIds);
	}

	private SimulationPlanRef retrieveById(@NonNull final SimulationPlanId id)
	{
		final I_C_SimulationPlan record = InterfaceWrapperHelper.load(id, I_C_SimulationPlan.class);
		return fromRecord(record);
	}

	private ImmutableMap<SimulationPlanId, SimulationPlanRef> retrieveByIds(@NonNull final Set<SimulationPlanId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableMap.of();
		}

		return queryBL.createQueryBuilder(I_C_SimulationPlan.class)
				.addInArrayFilter(I_C_SimulationPlan.COLUMNNAME_C_SimulationPlan_ID, ids)
				.stream()
				.map(SimulationPlanRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(SimulationPlanRef::getId, simulation -> simulation));
	}

	private ImmutableSet<SimulationPlanId> retrieveNotProcessedIds()
	{
		return queryBL
				.createQueryBuilder(I_C_SimulationPlan.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SimulationPlan.COLUMNNAME_Processed, false)
				.create()
				.listIds(SimulationPlanId::ofRepoId);
	}

	public boolean isNameUnique(final String name)
	{
		return !queryBL
				.createQueryBuilder(I_C_SimulationPlan.class)
				//.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SimulationPlan.COLUMNNAME_Name, name)
				.create()
				.anyMatch();
	}
}
