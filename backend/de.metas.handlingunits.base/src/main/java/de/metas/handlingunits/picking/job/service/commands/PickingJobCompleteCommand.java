package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

public class PickingJobCompleteCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;

	private final PickingJob initialPickingJob;

	@Builder
	private PickingJobCompleteCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJobLockService pickingJobLockService,
			final @NonNull PickingJobSlotService pickingSlotService,
			final @NonNull PickingJobHUReservationService pickingJobHUReservationService,
			//
			final @NonNull PickingJob pickingJob)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingSlotService = pickingSlotService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;

		this.initialPickingJob = pickingJob;
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		if (!initialPickingJob.getProgress().isDone())
		{
			throw new AdempiereException("Picking shall be DONE on all steps in order to complete the job");
		}

		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		if (!initialPickingJob.getProgress().isDone())
		{
			throw new AdempiereException("All steps shall be picked");
		}

		final PickingJob pickingJob = initialPickingJob.withDocStatus(PickingJobDocStatus.Completed);
		pickingJobRepository.save(pickingJob);

		initialPickingJob.getPickingSlotId()
				.ifPresent(pickingSlotId -> pickingSlotService.release(pickingSlotId, initialPickingJob.getId()));

		pickingJobHUReservationService.releaseAllReservations(pickingJob);

		pickingJobLockService.unlockShipmentSchedules(pickingJob);

		return pickingJob;
	}
}
