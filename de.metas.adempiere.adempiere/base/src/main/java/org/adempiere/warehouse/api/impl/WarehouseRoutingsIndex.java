package org.adempiere.warehouse.api.impl;

import java.util.List;
import java.util.Set;

import org.adempiere.warehouse.WarehouseId;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class WarehouseRoutingsIndex
{
	public static final WarehouseRoutingsIndex of(final List<WarehouseRouting> warehouseRoutings)
	{
		return new WarehouseRoutingsIndex(warehouseRoutings);
	}

	private final ImmutableListMultimap<WarehouseId, String> docBaseTypesByWarehouseId;

	private WarehouseRoutingsIndex(final List<WarehouseRouting> warehouseRoutings)
	{
		docBaseTypesByWarehouseId = warehouseRoutings.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						WarehouseRouting::getWarehouseId,
						WarehouseRouting::getDocBaseType));
	}

	public boolean isAllowAnyDocType(@NonNull final WarehouseId warehouseId)
	{
		// allow any docType if there were no routings defined for given warehouse
		return getDocBaseTypesForWarehouse(warehouseId).isEmpty();
	}

	private List<String> getDocBaseTypesForWarehouse(final WarehouseId warehouseId)
	{
		return docBaseTypesByWarehouseId.get(warehouseId);
	}

	public boolean isDocTypeAllowed(@NonNull final WarehouseId warehouseId, @NonNull final String docBaseType)
	{
		if (isAllowAnyDocType(warehouseId))
		{
			return true;
		}

		return getDocBaseTypesForWarehouse(warehouseId).contains(docBaseType);
	}

	public Set<WarehouseId> getWarehouseIdsAllowedForDocType(final Set<WarehouseId> warehouseIds, final String docBaseType)
	{
		return warehouseIds
				.stream()
				.filter(warehouseId -> isDocTypeAllowed(warehouseId, docBaseType))
				.collect(ImmutableSet.toImmutableSet());
	}

}
