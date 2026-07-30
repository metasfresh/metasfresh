package de.metas.handlingunits.picking.job.model;

import de.metas.i18n.ITranslatableString;
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

	@NonNull ITranslatableString productName;

	/** null when nothing is packed for this product; a packed qty of exactly zero is non-null and not unpickable. */
	@Nullable Quantity packedQty;

	public boolean isUnpickable()
	{
		return packedQty != null && !packedQty.isZero();
	}
}
