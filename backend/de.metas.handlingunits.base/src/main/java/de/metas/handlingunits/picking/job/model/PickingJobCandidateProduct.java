package de.metas.handlingunits.picking.job.model;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class PickingJobCandidateProduct
{
	@NonNull private final ProductId productId;
	@NonNull private final ITranslatableString productName;
	@NonNull private final Quantity qtyToDeliver;
	@Nullable private Quantity qtyAvailableToPick;

	public boolean hasQtyAvailableToPick() {return qtyAvailableToPick != null && qtyAvailableToPick.isPositive();}
}

