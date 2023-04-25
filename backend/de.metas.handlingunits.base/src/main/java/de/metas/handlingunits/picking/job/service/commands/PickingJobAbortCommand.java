package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;

import java.util.stream.Stream;

public class PickingJobAbortCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull private final PickingCandidateService pickingCandidateService;

	@NonNull private final PickingJob initialPickingJob;

	@Builder
	private PickingJobAbortCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJobLockService pickingJobLockService,
			final @NonNull PickingJobSlotService pickingSlotService,
			final @NonNull PickingJobHUReservationService pickingJobHUReservationService,
			final @NonNull PickingCandidateService pickingCandidateService,
			//
			final @NonNull PickingJob pickingJob)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingSlotService = pickingSlotService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
		this.pickingCandidateService = pickingCandidateService;

		this.initialPickingJob = pickingJob;
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		//noinspection OptionalGetWithoutIsPresent
		return Stream.of(initialPickingJob)
				.sequential()
				.map(this::releasePickingSlotAndSave)
				.map(this::unpickAllStepsAndSave)
				.peek(pickingJobHUReservationService::releaseAllReservations)
				.peek(pickingJobLockService::unlockShipmentSchedules)
				.map(this::markAsVoidedAndSave)
				.findFirst().get();
	}

	private PickingJob markAsVoidedAndSave(PickingJob pickingJob)
	{
		pickingJob = pickingJob.withDocStatus(PickingJobDocStatus.Voided);
		pickingJobRepository.save(pickingJob);
		return pickingJob;
	}

	private PickingJob unpickAllStepsAndSave(PickingJob pickingJob)
	{
		return PickingJobUnPickCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingJobHUReservationService(pickingJobHUReservationService)
				.pickingCandidateService(pickingCandidateService)
				.pickingJob(pickingJob)
				.build().execute();
	}

	private PickingJob releasePickingSlotAndSave(PickingJob pickingJob)
	{
		final PickingJobId pickingJobId = pickingJob.getId();
		final PickingSlotId pickingSlotId = pickingJob.getPickingSlotId().orElse(null);
		if (pickingSlotId != null)
		{
			pickingJob = pickingJob.withPickingSlot(null);
			pickingJobRepository.save(pickingJob);

			pickingSlotService.release(pickingSlotId, pickingJobId);
		}

		return pickingJob;
	}
}
