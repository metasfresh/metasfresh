package de.metas.hu_consolidation.mobile.rest_api.json;

import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonConsolidateByScanRequest
{
	/** {@code null} if the code is missing or blank */
	@Nullable ScannedCode scannedCode;
}
