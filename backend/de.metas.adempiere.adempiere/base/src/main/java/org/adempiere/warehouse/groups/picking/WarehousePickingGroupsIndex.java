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

package org.adempiere.warehouse.groups.picking;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import java.util.List;
import java.util.Set;

public final class WarehousePickingGroupsIndex
{
	public static WarehousePickingGroupsIndex of(@NonNull final List<WarehousePickingGroup> groups)
	{
		return new WarehousePickingGroupsIndex(groups);
	}

	private final ImmutableMap<WarehousePickingGroupId, WarehousePickingGroup> groupsById;
	private final ImmutableMap<WarehouseId, WarehousePickingGroup> groupsByWarehouseId;

	private WarehousePickingGroupsIndex(final List<WarehousePickingGroup> groups)
	{
		groupsById = Maps.uniqueIndex(groups, WarehousePickingGroup::getId);

		// NOTE: we assume a warehouse is part of one and only one picking group
		groupsByWarehouseId = groups.stream()
				.flatMap(group -> group.getWarehouseIds()
						.stream()
						.map(warehouseId -> GuavaCollectors.entry(warehouseId, group)))
				.collect(GuavaCollectors.toImmutableMap());
	}

	public WarehousePickingGroup getById(@NonNull final WarehousePickingGroupId id)
	{
		final WarehousePickingGroup group = groupsById.get(id);
		if (group == null)
		{
			throw new AdempiereException("No Warehouse Picking Group found for " + id);
		}
		return group;
	}

	public Set<WarehouseId> getWarehouseIdsOfSamePickingGroup(@NonNull final WarehouseId warehouseId)
	{
		final WarehousePickingGroup group = groupsByWarehouseId.get(warehouseId);
		if (group == null)
		{
			return ImmutableSet.of(warehouseId);
		}

		return group.getWarehouseIds();
	}

}
