package de.metas.resource.qrcode.v1;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonResourceQRCodePayloadV1
{
	int id;
	String type; // resourceType
	String caption;
}
