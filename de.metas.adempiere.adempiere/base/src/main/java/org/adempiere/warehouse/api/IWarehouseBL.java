package org.adempiere.warehouse.api;

import de.metas.location.CountryId;
import de.metas.organization.OrgId;

import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import de.metas.util.ISingletonService;

public interface IWarehouseBL extends ISingletonService
{

	/**
	 * @deprecated please use {@link #getDefaultLocatorId(WarehouseId)} instead.
	 */
	@Deprecated
	I_M_Locator getDefaultLocator(I_M_Warehouse warehouse);

	I_M_Locator getDefaultLocator(WarehouseId warehouseId);

	/**
	 * Get the first default locatorId.
	 *
	 * In case there is no default locator, get the first non default locator.
	 *
	 * In case none found, create a new one, with the coordinates (0,0,0)
	 *
	 * @param warehouse
	 * @return default locator's Id; never return null
	 */
	LocatorId getDefaultLocatorId(WarehouseId warehouse);

	CountryId getCountryId(WarehouseId warehouseId);

	OrgId getWarehouseOrgId(WarehouseId warehouseId);
}
