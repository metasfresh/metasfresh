package de.metas.handlingunits;

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

import de.metas.handlingunits.model.I_M_Locator;
import de.metas.util.ISingletonService;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;

import java.util.List;
import java.util.Set;

public interface IHUWarehouseDAO extends ISingletonService
{
	/**
	 * Retrieves all warehouses that have isPickingWarehouse set to 'Y' and have at least one "after picking locator".
	 *
	 * @param ctx
	 * @return
	 */
	List<I_M_Warehouse> retrievePickingWarehouses();

	/**
	 * Suggests an after-picking locator in the same warehouse as given <code>locator</code>.
	 *
	 * If given locator is after-picking then it will be returned.
	 *
	 * @param locatorRepoId
	 * @return after-picking locator or null
	 */
	I_M_Locator suggestAfterPickingLocator(int locatorRepoId);

	/**
	 * Suggests an after-picking locator in the given <code>warehouse</code>.
	 *
	 * @param warehouse
	 * @return after-picking locator or null
	 */
	I_M_Locator suggestAfterPickingLocator(I_M_Warehouse warehouse);

	/**
	 * Retrieve the warehouses where the quality returns will be kept.
	 * These warehouses must have the field <code>de.metas.handlingunits.model.I_M_Warehouse.COLUMNNAME_IsQualityReturnWarehouse</code> set on true.
	 * See task #1056.
	 * 
	 * This method fails if no Warehouses were found.
	 */
	List<de.metas.handlingunits.model.I_M_Warehouse> retrieveQualityReturnWarehouses();

	/**
	 * Retrieve the first warehouseId with  <code>de.metas.handlingunits.model.I_M_Warehouse.COLUMNNAME_IsQuarantineWarehouse</code> = `Y`
	 *
	 * @throws org.adempiere.exceptions.AdempiereException if no such a warehouse could be found.
	 */
	WarehouseId retrieveQuarantineWarehouseId();

	Set<WarehouseId> retrieveQualityReturnWarehouseIds();
}
