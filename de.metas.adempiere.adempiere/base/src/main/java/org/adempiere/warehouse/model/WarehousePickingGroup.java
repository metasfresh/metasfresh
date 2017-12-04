package org.adempiere.warehouse.model;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
public final class WarehousePickingGroup
{
	private final int id;
	private final String name;
	private final String description;
	private final ImmutableSet<Integer> warehouseIds;

	@Builder
	public WarehousePickingGroup(
			final int id,
			@NonNull final String name,
			final String description,
			@NonNull @Singular final ImmutableSet<Integer> warehouseIds)
	{
		Check.assume(id > 0, "id > 0");

		this.id = id;
		this.name = name;
		this.description = description;
		this.warehouseIds = warehouseIds;
	}

	public boolean containsWarehouseId(final int warehouseId)
	{
		return warehouseIds.contains(warehouseId);
	}
}
