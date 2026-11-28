package de.metas.handlingunits;

import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Request to create an {@code M_HU_PI_Item_Product} (TU capacity row) on a material {@code M_HU_PI_Item}.
 */
@Value
@Builder
public class HUPIItemProductCreateRequest
{
	@NonNull HuPackingInstructionsItemId piItemId;

	/**
	 * {@code null} → a row without product ({@code M_Product_ID} null).
	 */
	@Nullable ProductId productId;

	@NonNull UomId uomId;

	/**
	 * CUs per TU; {@code null} → infinite capacity.
	 */
	@Nullable BigDecimal qtyCUsPerTU;

	@NonNull Instant validFrom;

	@Nullable String eanTU;
}
