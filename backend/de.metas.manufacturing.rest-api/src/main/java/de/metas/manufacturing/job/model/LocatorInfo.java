package de.metas.manufacturing.job.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

@Value
@Builder
public class LocatorInfo
{
	@NonNull LocatorId id;
	@NonNull String caption;
	@NonNull LocatorQRCode qrCode;
}
