package de.metas.handlingunits.shipping;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * One contained product of a top-level HU, as extracted by
 * {@link PackedHUShippingInfoService#getProductItems(de.metas.handlingunits.model.I_M_HU)}.
 *
 * <p>One instance per {@code M_HU_Storage} product of the top-level HU.
 */
@Value
@Builder
public class PackedHUProductItem
{
	@NonNull ProductId productId;

	@NonNull Quantity qty;

	/**
	 * Country of origin, read from the top-level HU's {@code CountryOfOrigin} attribute.
	 * HU-level (not per-product) — see {@link PackedHUShippingInfoService#getProductItems}.
	 */
	@Nullable String countryOfOrigin;
}
