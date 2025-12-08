package de.metas.manufacturing.job.model;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class HUInfo
{
	@NonNull HuId id;
	@NonNull Quantity huCapacity;
	@Nullable HUQRCode barcode;
}
