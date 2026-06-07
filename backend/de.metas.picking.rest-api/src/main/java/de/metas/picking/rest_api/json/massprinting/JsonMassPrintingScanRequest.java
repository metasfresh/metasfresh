package de.metas.picking.rest_api.json.massprinting;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Request body for the mass-printing scan endpoint.
 * Carries the scanned LU code; the picker identity is resolved from the REST authentication context.
 */
@Value
@Builder
@Jacksonized
public class JsonMassPrintingScanRequest
{
	/**
	 * The scanned LU QR code string (global QR code format).
	 * Used to identify the LU to scan and pack all self-packed products from.
	 */
	@NonNull String scannedCode;
}
