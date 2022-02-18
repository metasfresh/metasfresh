package de.metas.handlingunits.rest_api;

import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonMoveHURequest
{
	@NonNull HuId huId;
	@NonNull String huQRCode;

	@NonNull String targetQRCode;
}
