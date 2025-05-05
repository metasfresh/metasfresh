package de.metas.picking.api;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import java.util.Set;

public interface IPickingSlotBL extends ISingletonService
{
	/**
	 * @return <code>true</code> if the given picking slot has no partner assigned.
	 */
	boolean isAvailableForAnyBPartner(I_M_PickingSlot pickingSlot);

	boolean isAvailableForBPartnerId(I_M_PickingSlot pickingSlot, BPartnerId bpartnerId);

	boolean isAvailableForBPartnerAndLocation(I_M_PickingSlot pickingSlot, BPartnerId bpartnerId, BPartnerLocationId bpartnerLocationId);

	PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull PickingSlotId pickingSlotId);

	Set<PickingSlotIdAndCaption> getPickingSlotIdAndCaptions(@NonNull PickingSlotQuery query);

	QRCodePDFResource createQRCodesPDF(Set<PickingSlotIdAndCaption> pickingSlotIdAndCaptions);

	boolean isAvailableForAnyBPartner(@NonNull PickingSlotId pickingSlotId);
}
