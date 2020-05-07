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

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.model.I_M_Locator;

public interface IHUWarehouseDAO extends ISingletonService
{
	/**
	 * Retrieves all warehouses that have isPickingWarehouse set to 'Y' and have at least one "after picking locator".
	 *
	 * @param ctx
	 * @return
	 */
	List<I_M_Warehouse> retrievePickingWarehouses(Properties ctx);

	/**
	 * Suggests an after-picking locator in the same warehouse as given <code>locator</code>.
	 *
	 * If given locator is after-picking then it will be returned.
	 *
	 * @param locator
	 * @return after-picking locator or null
	 */
	I_M_Locator suggestAfterPickingLocator(org.compiere.model.I_M_Locator locator);

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
	 * See task #1056
	 * 
	 * @param ctx
	 * @return
	 */
	List<de.metas.handlingunits.model.I_M_Warehouse> retrieveQualityReturnWarehouse(Properties ctx);
}
