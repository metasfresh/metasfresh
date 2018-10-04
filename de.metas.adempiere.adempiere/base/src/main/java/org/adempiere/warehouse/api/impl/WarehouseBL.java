package org.adempiere.warehouse.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class WarehouseBL implements IWarehouseBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public I_M_Locator getDefaultLocator(@NonNull final I_M_Warehouse warehouse)
	{
		final LocatorId defaultLocator = getDefaultLocatorId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));
		return load(defaultLocator, I_M_Locator.class);
	}

	@Override
	public LocatorId getDefaultLocatorId(@NonNull final WarehouseId warehouseId)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

		final List<I_M_Locator> locators = warehouseDAO.retrieveLocators(warehouseId);
		int activeLocatorsCount = 0;
		if (!locators.isEmpty())
		{
			I_M_Locator locatorFirst = null;

			//
			// Search for default locator if any
			for (final I_M_Locator locator : locators)
			{
				if (!locator.isActive())
				{
					continue;
				}

				activeLocatorsCount++;

				// Just found the default locator, return it now
				if (locator.isDefault())
				{
					return LocatorId.ofRecordOrNull(locator);
				}

				// Remember the first locator in case we don't find a default
				if (locatorFirst == null)
				{
					locatorFirst = locator;
				}
			}

			//
			// No Default Locator, return the first one
			if (locatorFirst != null)
			{
				// Log a warning, in case there are more then one active locators.
				if (activeLocatorsCount > 1)
				{
					logger.warn("No default locator for warehouse {}. Returning the first one: {}", loadWarehouse(warehouseId).getName(), locatorFirst);
				}

				return LocatorId.ofRecordOrNull(locatorFirst);
			}
		}

		//
		// No Locator was found: no default one and non which is active
		// => Create a new Locator and return it
		final I_M_Warehouse warehouse = loadWarehouse(warehouseId);
		final I_M_Locator locatorNew = newInstance(I_M_Locator.class, warehouse);

		locatorNew.setAD_Org_ID(warehouse.getAD_Org_ID());
		locatorNew.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		locatorNew.setValue("Standard");
		locatorNew.setX("0");
		locatorNew.setY("0");
		locatorNew.setZ("0");
		locatorNew.setIsDefault(true);
		save(locatorNew);
		if (logger.isInfoEnabled())
		{
			logger.info("Created default locator for " + warehouse.getName());
		}
		return LocatorId.ofRecordOrNull(locatorNew);
	}

	private I_M_Warehouse loadWarehouse(@NonNull final WarehouseId warehouseId)
	{
		return load(warehouseId, I_M_Warehouse.class);
	}

	@Override
	public I_C_Location getC_Location(final I_M_Warehouse warehouse)
	{
		Check.assumeNotNull(warehouse, "warehouse not null");

		final I_C_BPartner_Location bpLocation = warehouse.getC_BPartner_Location();
		Check.assumeNotNull(bpLocation, "C_BPartner_Location_ID not null for {}", warehouse);

		final I_C_Location location = bpLocation.getC_Location();
		Check.assumeNotNull(location, "C_Location_ID not null for {}, {}", bpLocation, warehouse);

		return location;
	}
}
