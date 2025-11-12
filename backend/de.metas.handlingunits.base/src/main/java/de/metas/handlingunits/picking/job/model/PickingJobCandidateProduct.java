package de.metas.handlingunits.picking.job.model;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder(toBuilder = true)
public class PickingJobCandidateProduct
{
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@Nullable Quantity qtyToDeliver;
	@Nullable Quantity qtyAvailableToPick;

	public boolean hasQtyAvailableToPick() {return qtyAvailableToPick != null && qtyAvailableToPick.isPositive();}

	public PickingJobCandidateProduct withQtyAvailableToPick(@Nullable Quantity qtyAvailableToPick)
	{
		return Objects.equals(this.qtyAvailableToPick, qtyAvailableToPick)
				? this
				: toBuilder().qtyAvailableToPick(qtyAvailableToPick).build();
	}
}

