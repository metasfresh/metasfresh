package de.metas.handlingunits.rest_api.createfromlabel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

/**
 * OK on the "New HU" preview: the scanned label plus the two values the user may change (quantity, location).
 * Any other field (e.g. the product the preview showed) is ignored: the server derives it from the scanned code.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@Builder
@Jacksonized
public class JsonCreateHUFromScannedCodeRequest
{
	@NonNull ScannedCode scannedCode;
	@NonNull BigDecimal qty;
	/** global QR code of the location, as proposed by the preview or scanned on it */
	@NonNull ScannedCode locatorQRCode;
}
