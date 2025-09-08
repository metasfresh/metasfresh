package de.metas.handlingunits.picking.slot;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingSlotReservationValue
{
	@Nullable BPartnerId bpartnerId;
	@Nullable BPartnerLocationId bpartnerLocationId;
}
