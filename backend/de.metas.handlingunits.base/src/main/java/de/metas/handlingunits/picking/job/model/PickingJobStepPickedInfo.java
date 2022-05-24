package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingJobStepPickedInfo
{
	@NonNull Quantity qtyPicked;
	@Nullable QtyRejectedReasonCode qtyRejectedReasonCode;
	@NonNull HuId actualPickedHUId;
	@NonNull PickingCandidateId pickingCandidateId;

	public void validateQtyRejectedReasonCode(final @NonNull Quantity qtyToPick)
	{
		final Quantity qtyRejected = qtyToPick.subtract(qtyPicked);
		if (qtyRejected.signum() < 0)
		{
			throw new AdempiereException("Maximum allowed qty to pick is " + qtyToPick);
		}
		else if (qtyRejected.signum() == 0)
		{
			if (qtyRejectedReasonCode != null)
			{
				throw new AdempiereException("No reject reason needs to be provided when the whole qty was picked");
			}
		}
		else // if(qtyRejected.signum() > 0)
		{
			if (qtyRejectedReasonCode == null)
			{
				throw new AdempiereException("Reject reason must be provided when QtyRejected != 0");
			}
		}
	}

}
