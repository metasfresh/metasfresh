package de.metas.scannable_code;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPrintableScannedCode
{
	@NonNull ScannedCode code;
	@NonNull String displayable;
}
