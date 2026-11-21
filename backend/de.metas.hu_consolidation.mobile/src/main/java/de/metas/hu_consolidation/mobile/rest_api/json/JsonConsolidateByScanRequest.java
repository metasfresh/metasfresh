package de.metas.hu_consolidation.mobile.rest_api.json;

import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonConsolidateByScanRequest
{
	@NonNull String scannedCode;

	/** @return the scanned code; {@code null} if it is blank */
	@Nullable
	public ScannedCode toScannedCodeOrNull()
	{
		return ScannedCode.ofNullableString(scannedCode);
	}
}
