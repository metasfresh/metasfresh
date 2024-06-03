package de.metas.handlingunits.qrcodes.model.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonRenderedHUQRCode
{
	@NonNull String code;
	@NonNull String displayable;
}
