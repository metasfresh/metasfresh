package de.metas.distribution.mobileui.external_services.warehouse;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

@Value
@Builder
public class LocatorInfo
{
	@NonNull LocatorId locatorId;
	@NonNull LocatorQRCode qrCode;
	@NonNull String caption;
}
