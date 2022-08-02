package de.metas.handlingunits.qrcodes.model;

import de.metas.handlingunits.HuPackingInstructionsId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class HUQRCodePackingInfo
{
	@NonNull HUQRCodeUnitType huUnitType;
	@NonNull HuPackingInstructionsId packingInstructionsId;
	@NonNull String caption;
}
