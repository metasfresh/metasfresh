/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_S_HumanResourceTestGroup;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public class HumanResourceTestGroupRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, HumanResourceTestGroupRepository.HumanResourceTestGroupMap> cache = CCache.<Integer, HumanResourceTestGroupRepository.HumanResourceTestGroupMap>builder()
			.tableName(I_S_HumanResourceTestGroup.Table_Name)
			.build();

	public ImmutableList<HumanResourceTestGroup> getAll()
	{
		return getMap().getAllActive();
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
				//.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(HumanResourceTestGroupRepository::fromRecord)
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
		public HumanResourceTestGroup getById(@NonNull final HumanResourceTestGroupId id)
		{
			return Optional.ofNullable(byId.get(id))
					.orElseThrow(() -> new AdempiereException("No HumanResourceTestGroup found!")
							.appendParametersToMessage()
							.setParameter("HumanResourceTestGroupId", id));
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
