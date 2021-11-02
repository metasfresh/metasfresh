package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
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
	@NonNull HuId pickFromHUId;
	@NonNull HUBarcode pickFromHUBarcode;
	@NonNull ProductId productId;
	@NonNull Quantity qtyAvailable;
}
