package de.metas.frontend_testing.masterdata.picking_slot;

import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPickingSlotCreateResponse
{
	PickingSlotId id;
	String code;
	String qrCode;
}
