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

package org.adempiere.warehouse.api;

import de.metas.document.location.DocumentLocation;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.Nullable;
import java.util.Optional;

public interface IWarehouseBL extends ISingletonService
{
	I_M_Warehouse getById(WarehouseId warehouseId);

	/**
	 * @deprecated please use {@link #getDefaultLocatorId(WarehouseId)} instead.
	 */
	@Deprecated
	I_M_Locator getDefaultLocator(I_M_Warehouse warehouse);

	I_M_Locator getDefaultLocator(WarehouseId warehouseId);

	/**
	 * Get the first default locatorId.
	 * <p>
	 * In case there is no default locator, get the first non default locator.
	 * <p>
	 * In case none found, create a new one, with the coordinates (0,0,0)
	 *
	 * @return default locator's Id; never return null
	 */
	LocatorId getDefaultLocatorId(WarehouseId warehouse);

	@Nullable
	CountryId getCountryId(WarehouseId warehouseId);

	@NonNull
	OrgId getWarehouseOrgId(WarehouseId warehouseId);

	DocumentLocation getPlainDocumentLocation(WarehouseId warehouseId);

	String getLocatorNameById(final LocatorId locatorId);

	String getWarehouseName(WarehouseId warehouseId);

	LocatorId getLocatorIdByRepoId(int locatorRepoId);

	I_M_Locator getLocatorByRepoId(int locatorRepoId);

	WarehouseId getInTransitWarehouseId(OrgId adOrgId);

	Optional<ResourceId> getPlantId(WarehouseId warehouseId);

	void updateWarehouseLocation(@NonNull LocationId oldLocationId, @NonNull LocationId newLocationId);
}
