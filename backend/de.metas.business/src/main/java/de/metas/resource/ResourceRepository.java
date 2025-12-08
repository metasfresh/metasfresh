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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceId;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public class ResourceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ResourcesMap> cache = CCache.<Integer, ResourcesMap>builder()
			.tableName(I_S_Resource.Table_Name)
			.build();

	public Resource getById(@NonNull final ResourceId id)
	{
		return getResourcesMap().getById(id);
	}

	public List<Resource> getByIds(@NonNull final Set<ResourceId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableList.of();
		}

		return getResourcesMap().getByIds(ids);
	}

	/**
	 * Used for legacy purposes
	 */
	public static I_S_Resource retrieveRecordById(@NonNull final ResourceId id)
	{
		final I_S_Resource resourceRecord = InterfaceWrapperHelper.load(id, I_S_Resource.class);
		if (resourceRecord == null)
		{
			throw new AdempiereException("No resource found for id " + id);
		}
		return resourceRecord;
	}

	private ResourcesMap getResourcesMap()
	{
		return cache.getOrLoad(0, this::retrieveResourcesMap);
	}

	private ResourcesMap retrieveResourcesMap()
	{
		final ImmutableList<Resource> resources = queryBL.createQueryBuilder(I_S_Resource.class)
				//.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(ResourceRepository::toResource)
				.collect(ImmutableList.toImmutableList());

		return new ResourcesMap(resources);
	}

	static Resource toResource(@NonNull final I_S_Resource record)
	{
		final IModelTranslationMap trl = InterfaceWrapperHelper.getModelTranslationMap(record);

		return Resource.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.resourceId(ResourceId.ofRepoId(record.getS_Resource_ID()))
				.isActive(record.isActive())
				.value(record.getValue())
				.name(trl.getColumnTrl(I_S_Resource.COLUMNNAME_Name, record.getName()))
				.description(record.getDescription())
				.resourceTypeId(ResourceTypeId.ofRepoId(record.getS_ResourceType_ID()))
				.manufacturingResourceType(ManufacturingResourceType.ofNullableCode(record.getManufacturingResourceType()))
				.responsibleId(UserId.ofRepoIdOrNull(record.getAD_User_ID()))
				.workplaceId(WorkplaceId.ofRepoIdOrNull(record.getC_Workplace_ID()))
				.externalSystemParentConfigId(record.getExternalSystem_Config_ID() > 0 ? record.getExternalSystem_Config_ID() : null)
				.build();
	}

	public ImmutableSet<ResourceId> getActivePlantIds()
	{
		return getResourcesMap().getActivePlantIds();
	}

	//
	//
	//
	//
	//

	private static final class ResourcesMap
	{
		@Getter private final ImmutableList<Resource> allActive;
		private final ImmutableMap<ResourceId, Resource> byId;

		ResourcesMap(final List<Resource> list)
		{
			this.allActive = list.stream().filter(Resource::isActive).collect(ImmutableList.toImmutableList());
			this.byId = Maps.uniqueIndex(list, Resource::getResourceId);
		}

		public Resource getById(@NonNull final ResourceId id)
		{
			final Resource resource = byId.get(id);
			if (resource == null)
			{
				throw new AdempiereException("Resource not found by ID: " + id);
			}
			return resource;
		}

		public Stream<Resource> streamAllActive()
		{
			return allActive.stream();
		}

		public List<Resource> getByIds(final Set<ResourceId> ids)
		{
			return ids.stream()
					.map(byId::get)
					.filter(Objects::nonNull)
					.collect(ImmutableList.toImmutableList());
		}

		public ImmutableSet<ResourceId> getActivePlantIds()
		{
			return streamAllActive()
					.filter(resource -> resource.isActive() && resource.isPlant())
					.map(Resource::getResourceId)
					.collect(ImmutableSet.toImmutableSet());
		}

	}
}