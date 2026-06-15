package org.adempiere.warehouse;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

@Value
@Builder
public class Locator
{
	@NonNull LocatorId locatorId;
	@NonNull String value;
	int priorityNo;

	public LocatorQRCode getQrCode()
	{
		return LocatorQRCode.builder()
				.locatorId(locatorId)
				.caption(value)
				.build();
	}
}
