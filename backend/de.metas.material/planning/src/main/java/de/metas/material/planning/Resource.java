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
import de.metas.resource.qrcode.ResourceQRCode;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class Resource
{
	@NonNull ResourceId resourceId;
	@NonNull String name;
	@Nullable ManufacturingResourceType manufacturingResourceType;
	@Nullable WorkplaceId workplaceId;
	@Nullable Integer externalSystemParentConfigId;
	boolean isManufacturingResource;

	public boolean isWorkstation()
	{
		return isManufacturingResource
				&& ManufacturingResourceType.WorkStation == manufacturingResourceType;
	}

	public boolean isExternalSystem()
	{
		return isManufacturingResource
				&& ManufacturingResourceType.ExternalSystem == manufacturingResourceType;
	}

	@NonNull
	public ResourceQRCode toQrCode()
	{
		return ResourceQRCode.builder()
				.resourceId(resourceId)
				.resourceType(ManufacturingResourceType.toCodeOrNull(manufacturingResourceType))
				.caption(name)
				.build();
	}
}
