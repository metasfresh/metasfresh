package de.metas.picking.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonGetHUInfoByScannedCodeRequest
{
	@NonNull String scannedCode;
	@Nullable String productNo;
}
