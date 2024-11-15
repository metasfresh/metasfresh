/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.warehouse;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarehouseRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static WarehouseRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new WarehouseRepository();
	}

	private final CCache<Integer, WarehouseMap> cache = CCache.<Integer, WarehouseMap>builder()
			.tableName(I_M_Warehouse.Table_Name)
			.build();

	@NonNull
	public Warehouse getById (@NonNull final WarehouseId warehouseId)
	{
		return getWarehouseMap().getById(warehouseId);
	}

	private WarehouseMap getWarehouseMap()
	{
		return cache.getOrLoad(0, this::retrieveWarehouseMap);
	}

	private WarehouseMap retrieveWarehouseMap()
	{
		final ImmutableList<Warehouse> warehouses = queryBL.createQueryBuilder(I_M_Warehouse.class)
				//.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(WarehouseRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new WarehouseMap(warehouses);
	}

	private static Warehouse fromRecord(@NonNull final I_M_Warehouse warehouse)
	{
		return Warehouse.builder()
				.warehouseId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()))
				.name(warehouse.getName())
				.resourceId(ResourceId.ofRepoIdOrNull(warehouse.getPP_Plant_ID()))
				.active(warehouse.isActive())
				.build();
	}
	@NonNull
	public ImmutableSet<WarehouseId> getAllActiveIds()
	{
		return getWarehouseMap().allActive.stream()
				.map(Warehouse::getWarehouseId)
				.collect(ImmutableSet.toImmutableSet());
	}

	//
	//
	//
	//
	//

	private static final class WarehouseMap
	{
		@Getter private final ImmutableList<Warehouse> allActive;
		private final ImmutableMap<WarehouseId, Warehouse> byId;

		WarehouseMap(final List<Warehouse> list)
		{
			this.allActive = list.stream().filter(Warehouse::isActive).collect(ImmutableList.toImmutableList());
			this.byId = Maps.uniqueIndex(list, Warehouse::getWarehouseId);
		}

		@NonNull
		public Warehouse getById(@NonNull final WarehouseId id)
		{
			final Warehouse warehouse = byId.get(id);
			if (warehouse == null)
			{
				throw new AdempiereException("Warehouse not found by ID: " + id);
			}
			return warehouse;
		}
	}
}