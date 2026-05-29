package de.metas.hu_consolidation.mobile.rest_api.json;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonHUConsolidationJobPickingSlot
{
	@NonNull PickingSlotId pickingSlotId;
	@NonNull JsonDisplayableQRCode pickingSlotQRCode;
	int countHUs;
}
