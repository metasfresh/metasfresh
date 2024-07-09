package de.metas.handlingunits.rest_api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonBulkMoveHURequest
{
	@NonNull List<String> huQRCodes;
	@NonNull String targetQRCode;
}
