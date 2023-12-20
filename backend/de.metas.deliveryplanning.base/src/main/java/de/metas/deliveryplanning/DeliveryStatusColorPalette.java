package de.metas.deliveryplanning;

import de.metas.util.ColorId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class DeliveryStatusColorPalette
{
	@Nullable ColorId deliveredColorId;
	@Nullable ColorId notDeliveredColorId;
}
