package de.metas.workflow.rest_api.controller.v2.json;

import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonSetCurrentTrolley
{
	@NonNull ScannedCode scannedCode;
}
