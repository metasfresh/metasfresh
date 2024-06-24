package de.metas.material.planning;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface IResourceDAO extends ISingletonService
{
	ResourceType getResourceTypeById(ResourceTypeId resourceTypeId);

	ResourceType getResourceTypeByResourceId(ResourceId resourceId);

	ResourceTypeId getResourceTypeIdByResourceId(ResourceId resourceId);

	I_S_Resource getById(ResourceId resourceId);

	List<I_S_Resource> getByIds(@NonNull Set<ResourceId> resourceIds);

	List<I_S_Resource> retrievePlants(Properties ctx);

	I_S_Resource retrievePlant(Properties ctx, int resourceId);

	void onResourceChanged(I_S_Resource resource);

	void onResourceTypeChanged(I_S_ResourceType resourceType);

	ImmutableSet<ResourceId> getResourceIdsByUserId(@NonNull UserId userId);

	ImmutableSet<ResourceId> getResourceIdsByResourceTypeIds(@NonNull Collection<ResourceTypeId> resourceTypeIds);
}
