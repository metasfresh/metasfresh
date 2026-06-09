package de.metas.handlingunits.picking.job.model;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class PickingJobLineQtyAvailable
{
	@NonNull PickingJobLineId lineId;
	@NonNull Quantity qtyRemainingToPick;
	@Nullable Quantity qtyAvailableToPick;
	@NonNull String uomSymbol;
	@Nullable QtyAvailableStatus status;

	@Builder
	private PickingJobLineQtyAvailable(
			@NonNull final PickingJobLineId lineId,
			@NonNull final Quantity qtyRemainingToPick,
			@Nullable final Quantity qtyAvailableToPick)
	{
		Quantity.getCommonUomIdOfAll(qtyRemainingToPick, qtyAvailableToPick);

		this.lineId = lineId;
		this.qtyRemainingToPick = qtyRemainingToPick;
		this.qtyAvailableToPick = qtyAvailableToPick;

		this.uomSymbol = qtyRemainingToPick.getUOMSymbol();
		this.status = qtyAvailableToPick != null
				? QtyAvailableStatus.computeOfQtyRequiredAndQtyAvailable(qtyRemainingToPick, qtyAvailableToPick)
				: null;
	}
}
