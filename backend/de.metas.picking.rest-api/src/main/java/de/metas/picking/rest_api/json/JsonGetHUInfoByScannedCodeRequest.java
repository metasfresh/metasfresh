package de.metas.picking.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonGetHUInfoByScannedCodeRequest
{
	@NonNull String scannedCode;
}
