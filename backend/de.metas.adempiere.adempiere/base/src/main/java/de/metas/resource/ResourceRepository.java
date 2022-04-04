/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import de.metas.product.ResourceId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

// TODO merge IResourceDAO into this repository
@Repository
public class ResourceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ResourcesMap> cache = CCache.<Integer, ResourcesMap>builder()
			.tableName(I_S_Resource.Table_Name)
			.build();

	public List<Resource> getResources()
	{
		return getResourcesMap().toList();
	}

	private ResourcesMap getResourcesMap()
	{
		return cache.getOrLoad(0, this::retrieveResourcesMap);
	}

	private ResourcesMap retrieveResourcesMap()
	{
		final ImmutableList<Resource> resources = queryBL.createQueryBuilder(I_S_Resource.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toResource(record))
				.collect(ImmutableList.toImmutableList());

		return new ResourcesMap(resources);
	}

	private static Resource toResource(final I_S_Resource record)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(record);

		return Resource.builder()
				.resourceId(ResourceId.ofRepoId(record.getS_Resource_ID()))
				.name(trls.getColumnTrl(I_S_Resource.COLUMNNAME_Name, record.getName()))
				.build();
	}

	//
	//
	//
	//
	//

	private static final class ResourcesMap
	{
		private final ImmutableList<Resource> resources;

		ResourcesMap(final List<Resource> resources)
		{
			this.resources = ImmutableList.copyOf(resources);
		}

		public List<Resource> toList()
		{
			return resources;
		}
	}
}
