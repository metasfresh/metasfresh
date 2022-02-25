package de.metas.workflow.rest_api.activity_features.set_scanned_barcode;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonQRCode
{
	@NonNull String qrCode;
	@NonNull String caption;
}
