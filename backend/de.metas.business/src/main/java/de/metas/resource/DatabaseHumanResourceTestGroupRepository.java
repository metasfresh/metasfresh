package de.metas.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_S_HumanResourceTestGroup;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class DatabaseHumanResourceTestGroupRepository implements HumanResourceTestGroupRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, HumanResourceTestGroupMap> cache = CCache.<Integer, HumanResourceTestGroupMap>builder()
			.tableName(I_S_HumanResourceTestGroup.Table_Name)
			.build();

	@Override
	@NonNull
	public ImmutableList<HumanResourceTestGroup> getAll()
	{
		return getMap().getAllActive();
	}

	@Override
	@NonNull
	public ImmutableList<HumanResourceTestGroup> getByIds(@NonNull final Set<HumanResourceTestGroupId> ids)
	{
		return (ImmutableList<HumanResourceTestGroup>)getMap().getByIds(ids);
	}

	@NonNull
	private HumanResourceTestGroupMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	@NonNull
	private HumanResourceTestGroupMap retrieveMap()
	{
		final ImmutableList<HumanResourceTestGroup> humanResourceTestGroups = queryBL.createQueryBuilder(I_S_HumanResourceTestGroup.class)
				.create()
				.stream()
				.map(DatabaseHumanResourceTestGroupRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new HumanResourceTestGroupMap(humanResourceTestGroups);
	}

	@NonNull
	private static HumanResourceTestGroup fromRecord(@NonNull final I_S_HumanResourceTestGroup record)
	{
		return HumanResourceTestGroup.builder()
				.id(HumanResourceTestGroupId.ofRepoId(record.getS_HumanResourceTestGroup_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.groupIdentifier(record.getGroupIdentifier())
				.name(record.getName())
				.department(record.getDepartment())
				.weeklyCapacity(Duration.ofHours(record.getCapacityInHours()))
				.isActive(record.isActive())
				.build();
	}

	//
	//
	//

	private final static class HumanResourceTestGroupMap
	{
		@NonNull
		private final ImmutableList<HumanResourceTestGroup> allActive;
		@NonNull
		private final ImmutableMap<HumanResourceTestGroupId, HumanResourceTestGroup> byId;

		private HumanResourceTestGroupMap(@NonNull final List<HumanResourceTestGroup> list)
		{
			this.allActive = list.stream().filter(HumanResourceTestGroup::getIsActive).collect(ImmutableList.toImmutableList());
			this.byId = Maps.uniqueIndex(list, HumanResourceTestGroup::getId);
		}

		@NonNull
		public ImmutableList<HumanResourceTestGroup> getAllActive()
		{
			return allActive;
		}

		@NonNull
		public List<HumanResourceTestGroup> getByIds(final Set<HumanResourceTestGroupId> ids)
		{
			return ids.stream()
					.map(byId::get)
					.filter(Objects::nonNull)
					.collect(ImmutableList.toImmutableList());
		}
	}
}
