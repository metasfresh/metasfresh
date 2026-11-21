package de.metas.hu_consolidation.mobile.rest_api.json;

import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

@Value
@Builder
@Jacksonized
public class JsonConsolidateByScanRequest
{
	@NonNull String scannedCode;

	/** @throws AdempiereException {@code INVALID_GRAI_BARCODE} if the code is blank */
	public ScannedCode toScannedCode()
	{
		final ScannedCode code = ScannedCode.ofNullableString(scannedCode);
		if (code == null)
		{
			throw new AdempiereException(MobileQRCodeMessages.INVALID_GRAI_BARCODE, scannedCode);
		}
		return code;
	}
}
