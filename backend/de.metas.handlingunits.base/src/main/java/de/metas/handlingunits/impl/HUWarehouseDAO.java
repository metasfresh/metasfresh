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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.model.I_M_Locator;

public class HUWarehouseDAO implements IHUWarehouseDAO
{
	@Override
	public List<I_M_Warehouse> retrievePickingWarehouses(final Properties ctx)
	{
		// 06902: We only take warehouses that have at least one *active* after picking locator.
		final IQuery<I_M_Locator> subQuery = Services.get(IQueryBL.class).createQueryBuilder(I_M_Locator.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_IsAfterPickingLocator, true)
				.addOnlyActiveRecordsFilter()
				.create();

		final List<I_M_Warehouse> warehouses = Services.get(IQueryBL.class).createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(org.adempiere.warehouse.model.I_M_Warehouse.COLUMNNAME_isPickingWarehouse, true)
				.addInSubQueryFilter(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Locator.COLUMNNAME_M_Warehouse_ID, subQuery)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_Warehouse.class);

		return warehouses;
	}

	@Override
	public I_M_Locator suggestAfterPickingLocator(final org.compiere.model.I_M_Locator locator)
	{
		Check.assumeNotNull(locator, "locator not null");

		//
		// If given locator is "after-picking" return it
		final I_M_Locator huLocator = InterfaceWrapperHelper.create(locator, I_M_Locator.class);
		if (huLocator.isAfterPickingLocator())
		{
			return huLocator;
		}

		//
		// Search for an after-picking locator in same warehouse as our given locator
		final I_M_Warehouse warehouse = huLocator.getM_Warehouse();
		return suggestAfterPickingLocator(warehouse);
	}

	@Override
	public I_M_Locator suggestAfterPickingLocator(final I_M_Warehouse warehouse)
	{
		Check.assumeNotNull(warehouse, "warehouse not null");

		for (final org.compiere.model.I_M_Locator currentLocator : Services.get(IWarehouseDAO.class).retrieveLocators(warehouse))
		{
			final I_M_Locator huCurrentLocator = InterfaceWrapperHelper.create(currentLocator, I_M_Locator.class);
			if (!huCurrentLocator.isActive())
			{
				continue;
			}

			if (huCurrentLocator.isAfterPickingLocator())
			{
				return huCurrentLocator;
			}
		}

		// no after-picking locator was found => return null
		return null;
	}

	@Override
	public List<de.metas.handlingunits.model.I_M_Warehouse> retrieveQualityReturnWarehouse(final Properties ctx)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(de.metas.handlingunits.model.I_M_Warehouse.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(de.metas.handlingunits.model.I_M_Warehouse.COLUMNNAME_IsQualityReturnWarehouse, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}
}
