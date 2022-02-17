package de.metas.handlingunits.rest_api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonMoveToRequest
{
	@NonNull String targetQRCode;
}
