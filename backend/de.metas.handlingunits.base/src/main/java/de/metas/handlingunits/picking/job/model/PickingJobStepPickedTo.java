package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class PickingJobStepPickedTo
{
	@NonNull Quantity qtyPicked;
	@Nullable QtyRejectedWithReason qtyRejected;
	@NonNull HuId actualPickedHUId;
	@NonNull PickingCandidateId pickingCandidateId;

	@Builder
	private PickingJobStepPickedTo(
			@NonNull final Quantity qtyPicked,
			@Nullable final QtyRejectedWithReason qtyRejected,
			@NonNull final HuId actualPickedHUId,
			@NonNull final PickingCandidateId pickingCandidateId)
	{
		Quantity.assertSameUOM(qtyPicked, qtyRejected != null ? qtyRejected.toQuantity() : null);

		this.qtyPicked = qtyPicked;
		this.qtyRejected = qtyRejected;
		this.actualPickedHUId = actualPickedHUId;
		this.pickingCandidateId = pickingCandidateId;
	}

	// public void validateQtyRejectedReasonCode(final @NonNull Quantity qtyToPick)
	// {
	// 	final Quantity qtyRejected = qtyToPick.subtract(qtyPicked);
	// 	if (qtyRejected.signum() < 0)
	// 	{
	// 		throw new AdempiereException("Maximum allowed qty to pick is " + qtyToPick);
	// 	}
	// 	else if (qtyRejected.signum() == 0)
	// 	{
	// 		if (qtyRejectedReasonCode != null)
	// 		{
	// 			throw new AdempiereException("No reject reason needs to be provided when the whole qty was picked");
	// 		}
	// 	}
	// 	else // if(qtyRejected.signum() > 0)
	// 	{
	// 		if (qtyRejectedReasonCode == null)
	// 		{
	// 			throw new AdempiereException("Reject reason must be provided when QtyRejected != 0");
	// 		}
	// 	}
	// }

}
