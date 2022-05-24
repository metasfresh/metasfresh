/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_Resource_Group;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceGroupRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ResourceGroupsMap> cache = CCache.<Integer, ResourceGroupsMap>builder()
			.tableName(I_S_Resource_Group.Table_Name)
			.build();

	public ImmutableList<ResourceGroup> getAllActive()
	{
		return getMap().toList();
	}

	private ResourceGroupsMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private ResourceGroupsMap retrieveMap()
	{
		final ImmutableList<ResourceGroup> list = queryBL.createQueryBuilder(I_S_Resource_Group.class)
				.addOnlyActiveRecordsFilter()
				.stream()
				.map(record -> toResourceGroup(record))
				.collect(ImmutableList.toImmutableList());

		return new ResourceGroupsMap(list);
	}

	private static ResourceGroup toResourceGroup(final I_S_Resource_Group record)
	{
		final IModelTranslationMap trl = InterfaceWrapperHelper.getModelTranslationMap(record);

		return ResourceGroup.builder()
				.id(ResourceGroupId.ofRepoId(record.getS_Resource_Group_ID()))
				.name(trl.getColumnTrl(I_S_Resource_Group.COLUMNNAME_Name, record.getName()))
				.build();
	}

	private static class ResourceGroupsMap
	{
		private final ImmutableList<ResourceGroup> list;

		private ResourceGroupsMap(@NonNull final ImmutableList<ResourceGroup> list)
		{
			this.list = list;
		}

		public ImmutableList<ResourceGroup> toList()
		{
			return list;
		}
	}
}
