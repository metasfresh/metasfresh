package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Warehouse;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class HUWarehouseDAO implements IHUWarehouseDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private static final AdMessageKey MSG_NoQualityWarehouse = AdMessageKey.of("NoQualityWarehouse");

	@Override
	public I_M_Warehouse getById(@NonNull final WarehouseId warehouseId) {return warehouseDAO.getById(warehouseId);}

	@Override
	public List<I_M_Warehouse> retrievePickingWarehouses()
	{
		// 06902: We only take warehouses that have at least one *active* after picking locator.
		final IQuery<I_M_Locator> subQuery = queryBL.createQueryBuilder(I_M_Locator.class)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_IsAfterPickingLocator, true)
				.addOnlyActiveRecordsFilter()
				.create();

		final Set<WarehouseId> warehouseIds = queryBL.createQueryBuilderOutOfTrx(I_M_Warehouse.class)
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_IsPickingWarehouse, true)
				.addInSubQueryFilter(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Locator.COLUMNNAME_M_Warehouse_ID, subQuery)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds()
				.stream()
				.map(WarehouseId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		return warehouseDAO.getByIds(warehouseIds);
	}

	@Override
	public Optional<LocatorId> suggestAfterPickingLocatorId(final int locatorRepoId)
	{
		Check.assumeGreaterThanZero(locatorRepoId, "locatorRepoId");
		final I_M_Locator locator = InterfaceWrapperHelper.create(
				warehouseDAO.getLocatorByRepoId(locatorRepoId),
				I_M_Locator.class);

		//
		// If given locator is "after-picking" return it
		if (locator.isAfterPickingLocator())
		{
			return Optional.of(LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID()));
		}

		//
		// Search for an after-picking locator in same warehouse as our given locator
		final WarehouseId warehouseId = WarehouseId.ofRepoId(locator.getM_Warehouse_ID());
		return suggestAfterPickingLocatorId(warehouseId);
	}

	public Optional<LocatorId> suggestAfterPickingLocatorId(@NonNull final WarehouseId warehouseId)
	{
		for (final I_M_Locator huCurrentLocator : warehouseDAO.getLocators(warehouseId, I_M_Locator.class))
		{
			if (!huCurrentLocator.isActive())
			{
				continue;
			}

			if (huCurrentLocator.isAfterPickingLocator())
			{
				return Optional.of(LocatorId.ofRepoId(huCurrentLocator.getM_Warehouse_ID(), huCurrentLocator.getM_Locator_ID()));
			}
		}

		// no after-picking locator was found => return null
		return Optional.empty();
	}

	@Override
	@NonNull
	public WarehouseId retrieveFirstQualityReturnWarehouseId()
	{
		final Set<WarehouseId> warehouseIds = retrieveQualityReturnWarehouseIds();
		return warehouseIds.iterator().next();
	}

	private Set<WarehouseId> retrieveQualityReturnWarehouseIds()
	{
		final Set<WarehouseId> warehouseIds = queryBL.createQueryBuilderOutOfTrx(de.metas.handlingunits.model.I_M_Warehouse.class)
				.addEqualsFilter(de.metas.handlingunits.model.I_M_Warehouse.COLUMNNAME_IsQualityReturnWarehouse, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds(WarehouseId::ofRepoId);

		if (warehouseIds.isEmpty())
		{
			throw new AdempiereException(MSG_NoQualityWarehouse);
		}

		return warehouseIds;
	}
}
