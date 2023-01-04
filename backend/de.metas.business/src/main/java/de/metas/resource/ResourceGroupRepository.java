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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.product.ProductCategoryId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.workflow.WFDurationUnit;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_Resource_Group;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
class ResourceGroupRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ResourceGroupsMap> cache = CCache.<Integer, ResourceGroupsMap>builder()
			.tableName(I_S_Resource_Group.Table_Name)
			.build();

	public ImmutableList<ResourceGroup> getAllActive()
	{
		return getMap().getAllActive();
	}

	public ImmutableList<ResourceGroup> getByIds(@NonNull final Set<ResourceGroupId> ids)
	{
		return !ids.isEmpty() ? getMap().getByIds(ids) : ImmutableList.of();
	}

	public ResourceGroup getById(@NonNull final ResourceGroupId id)
	{
		return getMap().getById(id);
	}

	private ResourceGroupsMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private ResourceGroupsMap retrieveMap()
	{
		final ImmutableList<ResourceGroup> list = queryBL.createQueryBuilder(I_S_Resource_Group.class)
				//.addOnlyActiveRecordsFilter()
				.stream()
				.map(ResourceGroupRepository::toResourceGroup)
				.collect(ImmutableList.toImmutableList());

		return new ResourceGroupsMap(list);
	}

	static ResourceGroup toResourceGroup(final I_S_Resource_Group record)
	{
		final IModelTranslationMap trl = InterfaceWrapperHelper.getModelTranslationMap(record);

		return ResourceGroup.builder()
				.id(ResourceGroupId.ofRepoId(record.getS_Resource_Group_ID()))
				.name(trl.getColumnTrl(I_S_Resource_Group.COLUMNNAME_Name, record.getName()))
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				.isActive(record.isActive())
				.productCategoryId(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
				.durationUnit(WFDurationUnit.ofCode(record.getDurationUnit()).getTemporalUnit())
				.build();
	}

	private static class ResourceGroupsMap
	{
		@Getter
		private final ImmutableList<ResourceGroup> allActive;
		private final ImmutableMap<ResourceGroupId, ResourceGroup> byId;

		private ResourceGroupsMap(@NonNull final ImmutableList<ResourceGroup> list)
		{
			this.allActive = list.stream().filter(ResourceGroup::isActive).collect(ImmutableList.toImmutableList());
			this.byId = Maps.uniqueIndex(list, ResourceGroup::getId);
		}

		private ResourceGroup getById(@NonNull final ResourceGroupId id)
		{
			final ResourceGroup resourceGroup = byId.get(id);
			if (resourceGroup == null)
			{
				throw new AdempiereException("No resource group found for " + id);
			}
			return resourceGroup;
		}

		public ImmutableList<ResourceGroup> getByIds(@NonNull final Set<ResourceGroupId> ids)
		{
			return ids.stream()
					.map(this::getById)
					.collect(ImmutableList.toImmutableList());
		}
	}
}
