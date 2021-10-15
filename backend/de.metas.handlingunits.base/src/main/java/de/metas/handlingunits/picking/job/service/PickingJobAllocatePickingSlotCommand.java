package de.metas.handlingunits.picking.job.service;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.picking.api.PickingSlotBarcode;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;

class PickingJobAllocatePickingSlotCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobSlotService pickingSlotService;

	private final PickingJob initialPickingJob;
	private final PickingSlotBarcode pickingSlotBarcode;

	@Builder
	private PickingJobAllocatePickingSlotCommand(
			@NonNull final PickingJobRepository pickingJobRepository,
			@NonNull final PickingJobSlotService pickingSlotService,
			//
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingSlotBarcode pickingSlotBarcode)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingSlotService = pickingSlotService;

		this.initialPickingJob = pickingJob;
		this.pickingSlotBarcode = pickingSlotBarcode;
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		// Make sure that scanned picking slot exist in our system
		final PickingSlotIdAndCaption pickingSlot = pickingSlotService.getPickingSlotIdAndCaption(pickingSlotBarcode);

		// No picking slot change
		if (PickingSlotId.equals(pickingSlot.getPickingSlotId(), initialPickingJob.getPickingSlotId().orElse(null)))
		{
			return initialPickingJob;
		}

		pickingSlotService.allocate(
				pickingSlot,
				initialPickingJob.getDeliveryBPLocationId(),
				initialPickingJob.getId());

		final PickingJob pickingJob = initialPickingJob.withPickingSlot(pickingSlot);
		pickingJobRepository.save(pickingJob);

		return pickingJob;
	}
}
