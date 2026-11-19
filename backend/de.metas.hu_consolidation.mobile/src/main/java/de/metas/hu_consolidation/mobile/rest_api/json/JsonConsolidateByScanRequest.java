package de.metas.hu_consolidation.mobile.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonConsolidateByScanRequest
{
	@NonNull String scannedCode;
}
