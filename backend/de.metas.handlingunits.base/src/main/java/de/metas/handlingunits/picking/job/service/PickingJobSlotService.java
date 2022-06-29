package de.metas.handlingunits.picking.job.service;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingSlotAllocateRequest;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.api.PickingSlotBarcode;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
public class PickingJobSlotService
{
	private final IHUPickingSlotBL pickingSlotBL = Services.get(IHUPickingSlotBL.class);

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotBarcode pickingSlotBarcode)
	{
		return pickingSlotBL.getPickingSlotIdAndCaption(pickingSlotBarcode.getPickingSlotId());
	}

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotBL.getPickingSlotIdAndCaption(pickingSlotId);
	}

	public void allocate(
			@NonNull final PickingSlotIdAndCaption pickingSlot,
			@NonNull final BPartnerLocationId deliveryBPLocationId,
			@NonNull final PickingJobId pickingJobId)
	{
		final BooleanWithReason allocated = pickingSlotBL.allocatePickingSlotIfPossible(
				PickingSlotAllocateRequest.builder()
						.pickingSlotId(pickingSlot.getPickingSlotId())
						.bpartnerAndLocationId(deliveryBPLocationId)
						.pickingJobId(pickingJobId)
						.build());
		if (allocated.isFalse())
		{
			throw new AdempiereException(TranslatableStrings.builder()
					.append("Failed allocating picking slot ").append(pickingSlot.getCaption()).append(" because ")
					.append(allocated.getReason())
					.build());
		}
	}

	public void release(@NonNull final PickingSlotId pickingSlotId, @NonNull final PickingJobId pickingJobId)
	{
		pickingSlotBL.releasePickingSlotFromJob(pickingSlotId, pickingJobId);
	}

}
