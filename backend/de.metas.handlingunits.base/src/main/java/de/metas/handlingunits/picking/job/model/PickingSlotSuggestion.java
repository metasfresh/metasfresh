package de.metas.handlingunits.picking.job.model;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.location.DocumentLocation;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Delegate;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingSlotSuggestion
{
	@NonNull @Delegate PickingSlotIdAndCaption pickingSlotIdAndCaption;
	@Nullable DocumentLocation deliveryLocation;
	int countHUs;

	public String getCaption() {return pickingSlotIdAndCaption.getCaption();}

	public PickingSlotQRCode getQRCode() {return PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlotIdAndCaption);}

	@Nullable
	public String getDeliveryAddress() {return deliveryLocation != null ? deliveryLocation.getBpartnerAddress() : null;}

	public BPartnerLocationId getDeliveryBPLocationId() {return deliveryLocation != null ? deliveryLocation.getBpartnerLocationId() : null;}
}
