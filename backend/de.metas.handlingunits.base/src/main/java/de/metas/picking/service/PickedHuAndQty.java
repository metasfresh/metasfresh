package de.metas.picking.service;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

@Value
@Builder
public class PickedHuAndQty
{
	@NonNull private final HuId originalHUId;
	@NonNull private final HuId pickedHUId;
	@NonNull private final Quantity qtyToPick;
	@NonNull private final Quantity qtyPicked;
}
