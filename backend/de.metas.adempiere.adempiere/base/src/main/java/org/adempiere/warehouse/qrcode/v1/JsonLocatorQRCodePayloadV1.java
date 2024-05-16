package org.adempiere.warehouse.qrcode.v1;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonLocatorQRCodePayloadV1
{
	int warehouseId;
	int locatorId;
	String caption;
}
