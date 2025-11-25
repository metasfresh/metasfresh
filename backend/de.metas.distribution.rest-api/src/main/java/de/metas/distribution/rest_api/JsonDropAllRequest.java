package de.metas.distribution.rest_api;

import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonDropAllRequest
{
	@NonNull ScannedCode dropToQRCode;
}
