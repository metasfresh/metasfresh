package de.metas.picking.rest_api.json.massprinting;

import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** Request body for the mass-printing scan endpoint. Picker identity is resolved from the REST authentication context. */
@Value
@Builder
@Jacksonized
public class JsonMassPrintingScanRequest
{
	@NonNull ScannedCode scannedCode;
}
