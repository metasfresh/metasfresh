/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.material.planning;

import de.metas.product.ResourceId;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceId;
import lombok.NonNull;
import org.compiere.model.I_S_Resource;
import org.springframework.stereotype.Service;

@Service
public class ResourceService
{
	private final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);

	@NonNull
	public Resource getById(@NonNull final ResourceId resourceId)
	{
		return ofRecord(resourceDAO.getById(resourceId));
	}

	@NonNull
	private static Resource ofRecord(@NonNull final I_S_Resource record)
	{
		return Resource.builder()
				.resourceId(ResourceId.ofRepoId(record.getS_Resource_ID()))
				.name(record.getName())
				.isManufacturingResource(record.isManufacturingResource())
				.manufacturingResourceType(ManufacturingResourceType.ofNullableCode(record.getManufacturingResourceType()).orElse(null))
				.workplaceId(WorkplaceId.ofRepoIdOrNull(record.getC_Workplace_ID()))
				.externalSystemParentConfigId(record.getExternalSystem_Config_ID())
				.build();
	}
}
