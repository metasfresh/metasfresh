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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import java.util.List;

public class WarehouseGroupsIndex
{
	public static WarehouseGroupsIndex ofList(@NonNull final List<WarehouseGroup> groups)
	{
		return new WarehouseGroupsIndex(groups);
	}

	private final ImmutableMap<WarehouseGroupId, WarehouseGroup> byId;
	private final ImmutableMap<WarehouseGroupAssignment, WarehouseGroup> byAssignment;

	private WarehouseGroupsIndex(final List<WarehouseGroup> groups)
	{
		byId = Maps.uniqueIndex(groups, WarehouseGroup::getId);

		byAssignment = groups.stream()
				.flatMap(group -> group.getAssignments()
						.stream()
						.map(assignment -> GuavaCollectors.entry(assignment, group)))
				.collect(GuavaCollectors.toImmutableMap());
	}

	public WarehouseGroup getById(@NonNull final WarehouseGroupId id)
	{
		final WarehouseGroup group = byId.get(id);
		if (group == null)
		{
			throw new AdempiereException("No Warehouse Picking Group found for " + id);
		}
		return group;
	}

	public ImmutableSet<WarehouseId> getWarehouseIdsOfSameGroup(
			@NonNull final WarehouseId warehouseId,
			@NonNull final WarehouseGroupAssignmentType assignmentType)
	{
		final WarehouseGroup group = byAssignment.get(WarehouseGroupAssignment.of(warehouseId, assignmentType));
		return group == null ? ImmutableSet.of(warehouseId) : group.getWarehouseIds(assignmentType);
	}
}
