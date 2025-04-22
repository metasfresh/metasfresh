package de.metas.frontend_testing.masterdata.picking_slot;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPickingSlotCreateResponse
{
	String code;
	String qrCode;
}
