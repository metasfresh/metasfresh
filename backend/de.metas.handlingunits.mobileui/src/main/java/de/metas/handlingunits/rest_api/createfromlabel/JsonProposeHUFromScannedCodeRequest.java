package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** A scanned code that is no existing HU: what would it create? */
@Value
@Builder
@Jacksonized
public class JsonProposeHUFromScannedCodeRequest
{
	@NonNull ScannedCode scannedCode;
}
