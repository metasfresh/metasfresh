package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Quantity and weight of a label for a product, as computed by {@link LabelQtyAndWeightService}.
 */
@Value
@Builder
class LabelQtyAndWeight
{
	@NonNull UomId stockUomId;
	/** {@link #stockUomId} as code, for the JSON */
	@NonNull X12DE355 stockUom;
	/** the proposed quantity, in {@link #stockUomId} */
	@NonNull BigDecimal qty;
	/** set only when the label weight becomes the CU's net weight (catch-weight product) */
	@Nullable BigDecimal weightKgForCU;
	/** the label carries a weight that this product does not use */
	boolean isWeightIgnored;
}
