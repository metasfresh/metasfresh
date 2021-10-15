package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import java.util.Objects;

class PickingJobCompleteCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final PickingJobSlotService pickingSlotService;

	private final PickingJob initialPickingJob;

	@Builder
	private PickingJobCompleteCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJobLockService pickingJobLockService,
			final @NonNull PickingJobSlotService pickingSlotService,
			final @NonNull PickingCandidateService pickingCandidateService,
			//
			final @NonNull PickingJob pickingJob)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingCandidateService = pickingCandidateService;
		this.pickingSlotService = pickingSlotService;

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
		final PickingJob pickingJob = initialPickingJob
				.withChangedSteps(step -> completeStep(step, initialPickingJob))
				.withDocStatus(PickingJobDocStatus.Completed);

		pickingJobRepository.save(pickingJob);

		initialPickingJob.getPickingSlotId()
				.ifPresent(pickingSlotId -> pickingSlotService.release(pickingSlotId, initialPickingJob.getId()));

		shipmentScheduleBL.closeShipmentSchedules(pickingJob.getShipmentScheduleIds());

		pickingJobLockService.unlockShipmentSchedules(pickingJob);

		return pickingJob;
	}

	private PickingJobStep completeStep(final PickingJobStep step, final PickingJob pickingJob)
	{
		final PickHUResult pickResult = pickingCandidateService.pickHU(PickRequest.builder()
				.existingPickingCandidateId(step.getPickingCandidateId())
				.shipmentScheduleId(step.getShipmentScheduleId())
				.pickFrom(PickFrom.ofHuId(step.getHuId()))
				.packToId(HuPackingInstructionsId.VIRTUAL)
				.qtyToPick(step.getQtyPicked())
				.qtyRejected(step.getQtyRejectedWithReason().orElse(null))
				.pickingSlotId(pickingJob.getPickingSlotId().orElse(null))
				.autoReview(true)
				.build());

		final PickingCandidateId pickingCandidateId = Objects.requireNonNull(pickResult.getPickingCandidate().getId());

		pickingCandidateService.process(ImmutableSet.of(pickingCandidateId));

		return step.withPickingCandidateId(pickingCandidateId);
	}
}
