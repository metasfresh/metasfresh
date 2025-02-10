package de.metas.frontend_testing;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonGetFreePickingSlotResponse
{
	@NonNull String qrCode;
}
