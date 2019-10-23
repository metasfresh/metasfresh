package org.adempiere.warehouse.api.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseType;
import org.adempiere.warehouse.WarehouseTypeId;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

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

class WarehouseTypesIndex
{
	public static WarehouseTypesIndex of(final List<WarehouseType> warehouseTypes)
	{
		return new WarehouseTypesIndex(warehouseTypes);
	}

	private final ImmutableMap<WarehouseTypeId, WarehouseType> warehouseTypesById;

	private WarehouseTypesIndex(final List<WarehouseType> warehouseTypes)
	{
		warehouseTypesById = Maps.uniqueIndex(warehouseTypes, WarehouseType::getId);
	}

	public WarehouseType getById(@NonNull final WarehouseTypeId id)
	{
		final WarehouseType warehouseType = warehouseTypesById.get(id);
		if (warehouseType == null)
		{
			throw new AdempiereException("@NotFound@ @M_Warehouse_Type_ID@: " + id);
		}
		return warehouseType;
	}
}
