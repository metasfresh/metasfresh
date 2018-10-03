package de.metas.picking.api;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.ISingletonService;

public interface IPickingSlotBL extends ISingletonService
{
	/**
	 * @return <code>true</code> if the given picking slot has no partner assigned.
	 */
	boolean isAvailableForAnyBPartner(I_M_PickingSlot pickingSlot);

	boolean isAvailableForBPartnerId(I_M_PickingSlot pickingSlot, BPartnerId bpartnerId);

	boolean isAvailableForBPartnerAndLocation(I_M_PickingSlot pickingSlot, BPartnerId bpartnerId, BPartnerLocationId bpartnerLocationId);
}
