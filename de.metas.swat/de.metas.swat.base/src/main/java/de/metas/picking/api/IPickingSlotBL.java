package de.metas.picking.api;

import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.ISingletonService;

public interface IPickingSlotBL extends ISingletonService
{
	/**
	 * Returns <code>true</code> if the given picking slot has no partner assigned.
	 * 
	 * @param pickingSlot
	 * @return
	 */
	boolean isAvailableForAnyBPartner(I_M_PickingSlot pickingSlot);

	boolean isAvailableForBPartnerID(I_M_PickingSlot pickingSlot, int bpartnerId);

	boolean isAvailableForBPartnerAndLocation(I_M_PickingSlot pickingSlot, int bpartnerId, int bpartnerLocationId);
}
