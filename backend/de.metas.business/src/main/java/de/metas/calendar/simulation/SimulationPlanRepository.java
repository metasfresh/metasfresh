package de.metas.calendar.simulation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_SimulationPlan;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Repository
public class SimulationPlanRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<SimulationPlanId, SimulationPlanRef> cacheById = CCache.<SimulationPlanId, SimulationPlanRef>builder()
			.tableName(I_C_SimulationPlan.Table_Name)
			.build();

	private final CCache<Integer, ImmutableSet<SimulationPlanId>> cacheDraftIds = CCache.<Integer, ImmutableSet<SimulationPlanId>>builder()
			.tableName(I_C_SimulationPlan.Table_Name)
			.build();

	@NonNull
	public SimulationPlanRef createNewSimulation(
			@NonNull final SimulationPlanCreateRequest simulationPlanCreateRequest, 
			@NonNull final Supplier<String> defaultNameSupplier)
	{
		final I_C_SimulationPlan record = InterfaceWrapperHelper.newInstance(I_C_SimulationPlan.class);
		record.setAD_Org_ID(simulationPlanCreateRequest.getOrgId().getRepoId());
		record.setName(simulationPlanCreateRequest.getName().orElseGet(defaultNameSupplier));
		record.setAD_User_Responsible_ID(simulationPlanCreateRequest.getResponsibleUserId().getRepoId());
		record.setDocStatus(SimulationPlanDocStatus.Drafted.getCode());
		record.setProcessed(SimulationPlanDocStatus.Drafted.isProcessed());
		record.setIsMainSimulation(simulationPlanCreateRequest.isMainSimulationPlan());
		InterfaceWrapperHelper.saveRecord(record);

		final SimulationPlanRef simulation = fromRecord(record);
		cacheById.put(simulation.getId(), simulation);

		return simulation;
	}

	@NonNull
	private static SimulationPlanRef fromRecord(@NonNull final I_C_SimulationPlan record)
	{
		return SimulationPlanRef.builder()
				.id(SimulationPlanId.ofRepoId(record.getC_SimulationPlan_ID()))
				.name(record.getName())
				.responsibleUserId(UserId.ofRepoId(record.getAD_User_Responsible_ID()))
				.docStatus(SimulationPlanDocStatus.ofCode(record.getDocStatus()))
				.processed(record.isProcessed())
				.isMainSimulation(record.isMainSimulation())
				.created(record.getCreated().toInstant())
				.build();
	}

	@NonNull
	public SimulationPlanRef changeDocStatus(@NonNull final SimulationPlanId simulationId, @NonNull final SimulationPlanDocStatus docStatus)
	{
		final I_C_SimulationPlan record = retrieveRecordById(simulationId);
		record.setDocStatus(docStatus.getCode());
		record.setProcessed(docStatus.isProcessed());
		InterfaceWrapperHelper.saveRecord(record);
		return fromRecord(record);
	}

	public SimulationPlanRef getById(@NonNull final SimulationPlanId id)
	{
		return cacheById.getOrLoad(id, this::retrieveById);
	}

	public ImmutableSet<SimulationPlanId> getDraftSimulationIds()
	{
		return cacheDraftIds.getOrLoad(0, this::retrieveDraftIds);
	}

	public Collection<SimulationPlanRef> getAllDrafts(@Nullable final SimulationPlanId alwaysIncludeId)
	{
		ImmutableSet<SimulationPlanId> simulationIds = getDraftSimulationIds();
		if (alwaysIncludeId != null && !simulationIds.contains(alwaysIncludeId))
		{
			simulationIds = ImmutableSet.<SimulationPlanId>builder()
					.addAll(simulationIds)
					.add(alwaysIncludeId)
					.build();
		}

		return cacheById.getAllOrLoad(simulationIds, this::retrieveByIds);
	}
	
	@NonNull
	public Optional<SimulationPlanRef> getCurrentMainSimulationPlan(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_C_SimulationPlan.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SimulationPlan.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_C_SimulationPlan.COLUMNNAME_IsMainSimulation, true)
				.addEqualsFilter(I_C_SimulationPlan.COLUMNNAME_Processed, false)
				.create()
				.firstOnlyOptional(I_C_SimulationPlan.class)
				.map(SimulationPlanRepository::fromRecord);
	}

	private SimulationPlanRef retrieveById(@NonNull final SimulationPlanId id)
	{
		return fromRecord(retrieveRecordById(id));
	}

	private I_C_SimulationPlan retrieveRecordById(final @NonNull SimulationPlanId id)
	{
		final I_C_SimulationPlan record = InterfaceWrapperHelper.load(id, I_C_SimulationPlan.class);
		if (record == null)
		{
			throw new AdempiereException("No simulation plan found for " + id);
		}
		return record;
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

	private ImmutableSet<SimulationPlanId> retrieveDraftIds()
	{
		return queryBL
				.createQueryBuilder(I_C_SimulationPlan.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SimulationPlan.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_SimulationPlan.COLUMNNAME_DocStatus, SimulationPlanDocStatus.Drafted)
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
