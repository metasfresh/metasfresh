package de.metas.handlingunits.qrcodes.model.json.v1;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonHUQRCodePackingInfoV1
{
	@NonNull HUQRCodeUnitType huUnitType;
	@NonNull HuPackingInstructionsId packingInstructionsId;
	@NonNull String caption;
}
