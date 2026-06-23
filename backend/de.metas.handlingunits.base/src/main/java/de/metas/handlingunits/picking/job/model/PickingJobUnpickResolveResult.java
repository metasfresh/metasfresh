package de.metas.handlingunits.picking.job.model;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Result of resolving a scanned product barcode against a picking job for partial unpick purposes.
 */
@Value
@Builder
public class PickingJobUnpickResolveResult
{
	@NonNull ProductId productId;
	@NonNull String productName;

	/** Total qty currently packed for this product across all steps of the job; null if nothing is packed */
	@Nullable Quantity packedQty;

	/** true when packedQty is not null and > 0 */
	boolean unpickable;
}
