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

import de.metas.i18n.ITranslatableString;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.workplace.WorkplaceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class Resource
{
	@NonNull OrgId orgId;
	@NonNull ResourceId resourceId;
	boolean isActive;
	@NonNull String value;
	@NonNull ITranslatableString name;
	@Nullable String description;
	@Nullable ResourceGroupId resourceGroupId;
	@NonNull ResourceTypeId resourceTypeId;
	@Nullable ManufacturingResourceType manufacturingResourceType;

	@Nullable UserId responsibleId;

	@Nullable String internalName;

	@Nullable HumanResourceTestGroupId humanResourceTestGroupId;

	@Nullable WorkplaceId workplaceId;

	public boolean isPlant() {return manufacturingResourceType != null && manufacturingResourceType.isPlant();}
}
