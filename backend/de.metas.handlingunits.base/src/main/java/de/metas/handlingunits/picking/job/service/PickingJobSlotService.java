package de.metas.handlingunits.picking.job.service;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingSlotAllocateRequest;
import de.metas.handlingunits.picking.PickingSlotConnectedComponent;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.i18n.BooleanWithReason;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PickingJobSlotService implements PickingSlotConnectedComponent
{
	private final IHUPickingSlotBL pickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final PickingJobRepository pickingJobRepository;

	public PickingJobSlotService(
			@NonNull final PickingJobRepository pickingJobRepository)
	{
		this.pickingJobRepository = pickingJobRepository;
	}

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotQRCode pickingSlotQRCode)
	{
		return pickingSlotBL.getPickingSlotIdAndCaption(pickingSlotQRCode.getPickingSlotId());
	}

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotBL.getPickingSlotIdAndCaption(pickingSlotId);
	}

	@NonNull
	public BooleanWithReason allocate(
			@NonNull final PickingSlotIdAndCaption pickingSlot,
			@NonNull final BPartnerLocationId deliveryBPLocationId)
	{
		return pickingSlotBL.allocatePickingSlotIfPossible(
				PickingSlotAllocateRequest.builder()
						.pickingSlotId(pickingSlot.getPickingSlotId())
						.bpartnerAndLocationId(deliveryBPLocationId)
						.build());
	}

	public void release(
			@NonNull final PickingSlotId pickingSlotId,
			@NonNull final PickingJobId pickingJobId)
	{
		if (!pickingJobRepository.hasDraftJobsUsingPickingSlot(pickingSlotId, pickingJobId))
		{
			pickingSlotBL.releasePickingSlotIfPossible(pickingSlotId);
		}
	}

	@Override
	public boolean hasAllocationsForSlot(@NonNull final PickingSlotId slotId)
	{
		return pickingJobRepository.hasDraftJobsUsingPickingSlot(slotId, null);
	}
}
