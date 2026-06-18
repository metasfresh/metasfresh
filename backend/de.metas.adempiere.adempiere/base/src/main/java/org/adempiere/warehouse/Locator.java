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
	@Builder.Default boolean active = true;
	@NonNull String value;
	int priorityNo;
	boolean isGroundFloor;

	public LocatorQRCode getQrCode()
	{
		return LocatorQRCode.builder()
				.locatorId(locatorId)
				.caption(value)
				.build();
	}
}
