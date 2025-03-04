package de.metas.rest_api.v2.workstation;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonGetWorkstationByQRCodeRequest
{
	@NonNull String qrCode;
}
