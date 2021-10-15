package de.metas.handlingunits.picking.job.service;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;

class PickingJobAbortCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;

	private final PickingJob initialPickingJob;

	@Builder
	private PickingJobAbortCommand(
			@NonNull final PickingJobRepository pickingJobRepository,
			@NonNull final PickingJobLockService pickingJobLockService,
			@NonNull final PickingJobSlotService pickingSlotService,
			//
			@NonNull final PickingJob pickingJob)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingSlotService = pickingSlotService;

		this.initialPickingJob = pickingJob;
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		initialPickingJob.getPickingSlotId()
				.ifPresent(pickingSlotId -> pickingSlotService.release(pickingSlotId, initialPickingJob.getId()));

		pickingJobLockService.unlockShipmentSchedules(initialPickingJob);

		final PickingJob pickingJob = initialPickingJob.withDocStatus(PickingJobDocStatus.Voided);
		pickingJobRepository.save(pickingJob);

		return pickingJob;
	}
}
