/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.adempiere.warehouse.groups;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
public class WarehouseGroup
{
	@NonNull WarehouseGroupId id;
	@NonNull String name;
	@Nullable String description;
	@NonNull ImmutableSet<WarehouseGroupAssignment> assignments;
	@NonNull ImmutableSetMultimap<WarehouseGroupAssignmentType, WarehouseId> warehouseIdsByAssignmentType;

	@Builder
	public WarehouseGroup(
			@NonNull final WarehouseGroupId id,
			@NonNull final String name,
			@Nullable final String description,
			@NonNull @Singular final ImmutableSet<WarehouseGroupAssignment> assignments)
	{
		this.id = id;
		this.name = StringUtils.trimBlankToOptional(name)
				.orElseThrow(() -> new AdempiereException("Blank warehouse group name not allowed"));
		this.description = StringUtils.trimBlankToNull(description);
		this.assignments = assignments;
		this.warehouseIdsByAssignmentType = assignments
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						WarehouseGroupAssignment::getAssignmentType,
						WarehouseGroupAssignment::getWarehouseId));
	}

	public ImmutableSet<WarehouseId> getWarehouseIds(@NonNull final WarehouseGroupAssignmentType assignmentType)
	{
		return warehouseIdsByAssignmentType.get(assignmentType);
	}
}
