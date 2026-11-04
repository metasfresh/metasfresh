package de.metas.handlingunits.rest_api.createfromlabel;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;

import javax.annotation.Nullable;

/** The one definition of a location the "New HU" flow may book into: the preview proposes only such a location and the create step accepts only such a location. */
@UtilityClass
class LabelLocatorRules
{
	/** an existing, active location of the client, stored in the warehouse that {@code locatorId} names */
	static boolean isUsable(@Nullable final I_M_Locator locator, @NonNull final LocatorId locatorId, @NonNull final ClientId clientId)
	{
		return locator != null
				&& locator.isActive()
				&& ClientId.equals(ClientId.ofRepoId(locator.getAD_Client_ID()), clientId)
				&& WarehouseId.equals(WarehouseId.ofRepoId(locator.getM_Warehouse_ID()), locatorId.getWarehouseId());
	}
}
