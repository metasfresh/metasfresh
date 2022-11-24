package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HUInfo
{
	@NonNull HuId id;
	@NonNull HUBarcode barcode;
}
