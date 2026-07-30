package de.metas.handlingunits;

import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;

/**
 * Immutable result of a GTIN lookup against {@link de.metas.handlingunits.model.I_M_HU_PI_Item_Product}.
 * Carries the resolved product and the packing instruction item product IDs,
 * without exposing the underlying record.
 */
@Value
public class HUPIItemProductGtinMatch
{
	@NonNull ProductId productId;
	@NonNull HUPIItemProductId hupiItemProductId;

	public static HUPIItemProductGtinMatch of(
			@NonNull final ProductId productId,
			@NonNull final HUPIItemProductId hupiItemProductId)
	{
		return new HUPIItemProductGtinMatch(productId, hupiItemProductId);
	}
}
