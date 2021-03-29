/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.warehouse.api.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class WarehouseBL implements IWarehouseBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	@Override
	public I_M_Warehouse getById(@NonNull final WarehouseId warehouseId)
	{
		return Services.get(IWarehouseDAO.class).getById(warehouseId);
	}

	@Override
	public I_M_Locator getDefaultLocator(@NonNull final I_M_Warehouse warehouse)
	{
		return getDefaultLocator(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));
	}

	@Override
	public I_M_Locator getDefaultLocator(@NonNull final WarehouseId warehouseId)
	{
		final LocatorId defaultLocatorId = getDefaultLocatorId(warehouseId);
		return Services.get(IWarehouseDAO.class).getLocatorById(defaultLocatorId);
	}

	@Override
	@NonNull
	public LocatorId getDefaultLocatorId(@NonNull final WarehouseId warehouseId)
	{
		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);

		final List<I_M_Locator> locators = warehousesRepo.getLocators(warehouseId);
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
					final String warehouseName = warehousesRepo.getWarehouseName(warehouseId);
					logger.warn("No default locator for warehouse {}. Returning the first one: {}", warehouseName, locatorFirst);
				}

				return LocatorId.ofRecordOrNull(locatorFirst);
			}
		}

		//
		// No Locator was found: no default one and non which is active
		// => Create a new Locator and return it
		return warehousesRepo.createDefaultLocator(warehouseId);
	}

	private I_C_Location getC_Location(@NonNull final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouse = Services.get(IWarehouseDAO.class).getById(warehouseId);
		return getC_Location(warehouse);
	}

	private I_C_Location getC_Location(final I_M_Warehouse warehouse)
	{
		Check.assumeNotNull(warehouse, "warehouse not null");

		final I_C_BPartner_Location bpLocation = bPartnerDAO.getBPartnerLocationById(BPartnerLocationId.ofRepoIdOrNull(warehouse.getC_BPartner_ID(),warehouse.getC_BPartner_Location_ID()));
		Check.assumeNotNull(bpLocation, "C_BPartner_Location_ID not null for {}", warehouse);

		final I_C_Location location = bpLocation.getC_Location();
		Check.assumeNotNull(location, "C_Location_ID not null for {}, {}", bpLocation, warehouse);

		return location;
	}

	@Nullable
	@Override
	public CountryId getCountryId(@NonNull final WarehouseId warehouseId)
	{
		final I_C_Location location = getC_Location(warehouseId);
		return CountryId.ofRepoIdOrNull(location.getC_Country_ID());
	}

	@Override
	public OrgId getWarehouseOrgId(@NonNull final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouseRecord = loadOutOfTrx(warehouseId, I_M_Warehouse.class);
		return OrgId.ofRepoIdOrAny(warehouseRecord.getAD_Org_ID());
	}
}
