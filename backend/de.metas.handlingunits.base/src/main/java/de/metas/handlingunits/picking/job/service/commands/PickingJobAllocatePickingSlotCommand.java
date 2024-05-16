package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;

public class PickingJobAllocatePickingSlotCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobSlotService pickingSlotService;

	private final PickingJob initialPickingJob;
	private final PickingSlotQRCode pickingSlotQRCode;

	@Builder
	private PickingJobAllocatePickingSlotCommand(
			@NonNull final PickingJobRepository pickingJobRepository,
			@NonNull final PickingJobSlotService pickingSlotService,
			//
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingSlotQRCode pickingSlotQRCode)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingSlotService = pickingSlotService;

		this.initialPickingJob = pickingJob;
		this.pickingSlotQRCode = pickingSlotQRCode;
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		// Make sure that scanned picking slot exist in our system
		final PickingSlotIdAndCaption newPickingSlot = pickingSlotService.getPickingSlotIdAndCaption(pickingSlotQRCode);

		// No picking slot change
		final PickingSlotId oldPickingSlotId = initialPickingJob.getPickingSlotId().orElse(null);
		if (PickingSlotId.equals(newPickingSlot.getPickingSlotId(), oldPickingSlotId))
		{
			return initialPickingJob;
		}

		if (oldPickingSlotId != null)
		{
			pickingSlotService.release(oldPickingSlotId, initialPickingJob.getId());
		}

		pickingSlotService.allocate(newPickingSlot, initialPickingJob.getDeliveryBPLocationId());

		final PickingJob pickingJob = initialPickingJob.withPickingSlot(newPickingSlot);
		pickingJobRepository.save(pickingJob);

		return pickingJob;
	}
}
