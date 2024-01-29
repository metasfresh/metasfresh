package de.metas.security.qr;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonUserAuthQRCodePayloadV1
{
	@NonNull String token;
	@NonNull JsonMetasfreshId userId;
}
