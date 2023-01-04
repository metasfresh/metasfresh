package de.metas.device.accessor.qrcode.v1;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonDeviceQRCodePayloadV1
{
	@NonNull String deviceId;
	@NonNull String caption;
}
