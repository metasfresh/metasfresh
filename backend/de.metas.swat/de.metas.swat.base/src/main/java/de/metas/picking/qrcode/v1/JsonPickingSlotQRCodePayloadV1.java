package de.metas.picking.qrcode.v1;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPickingSlotQRCodePayloadV1
{
	int pickingSlotId;
	String caption;
}
