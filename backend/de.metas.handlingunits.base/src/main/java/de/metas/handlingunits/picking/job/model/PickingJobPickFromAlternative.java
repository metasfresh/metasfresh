package de.metas.handlingunits.picking.job.model;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PickingJobPickFromAlternative
{
	@NonNull PickingJobPickFromAlternativeId id;
	@NonNull LocatorInfo locatorInfo;
	@NonNull HUInfo pickFromHU;
	@NonNull ProductId productId;
	@NonNull Quantity qtyAvailable;
}
