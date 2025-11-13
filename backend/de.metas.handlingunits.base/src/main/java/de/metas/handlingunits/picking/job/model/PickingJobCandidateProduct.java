package de.metas.handlingunits.picking.job.model;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class PickingJobCandidateProduct
{
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@Nullable Quantity qtyToDeliver;
	@Nullable Quantity qtyAvailableToPick;

	public PickingJobCandidateProduct withQtyAvailableToPick(@Nullable Quantity qtyAvailableToPick)
	{
		return Objects.equals(this.qtyAvailableToPick, qtyAvailableToPick)
				? this
				: toBuilder().qtyAvailableToPick(qtyAvailableToPick).build();
	}

	public Optional<QtyAvailableStatus> getQtyAvailableStatus()
	{
		if (qtyToDeliver == null || qtyAvailableToPick == null)
		{
			return Optional.empty();
		}

		final Quantity qtyNotAvailable = qtyToDeliver.subtract(qtyAvailableToPick);

		if (qtyNotAvailable.signum() <= 0)
		{
			return Optional.of(QtyAvailableStatus.FULLY_AVAILABLE);
		}
		else if (qtyAvailableToPick.signum() > 0)
		{
			return Optional.of(QtyAvailableStatus.PARTIALLY_AVAILABLE);
		}
		else
		{
			return Optional.of(QtyAvailableStatus.NOT_AVAILABLE);
		}
	}
}

