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

import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;

import java.util.List;
import java.util.Optional;

public interface IHUWarehouseDAO extends ISingletonService
{
	I_M_Warehouse getById(WarehouseId warehouseId);

	/**
	 * Retrieves all warehouses that have isPickingWarehouse set to 'Y' and have at least one "after picking locator".
	 */
	List<I_M_Warehouse> retrievePickingWarehouses();

	/**
	 * Suggests an after-picking locator in the same warehouse as given <code>locator</code>.
	 * If given locator is after-picking then it will be returned.
	 *
	 * @return after-picking locator or null
	 */
	Optional<LocatorId> suggestAfterPickingLocatorId(int locatorRepoId);

	@NonNull
	WarehouseId retrieveFirstQualityReturnWarehouseId();
}
