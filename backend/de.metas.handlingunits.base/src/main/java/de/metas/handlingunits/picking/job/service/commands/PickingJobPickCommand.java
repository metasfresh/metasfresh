package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesResult;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedInfo;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;

public class PickingJobPickCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull private final PickingCandidateService pickingCandidateService;

	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final PickingJobStep initialStep;
	@NonNull private final Quantity qtyToPick;
	@Nullable private final QtyRejectedReasonCode qtyRejectedReasonCode;

	@Builder
	private PickingJobPickCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJobHUReservationService pickingJobHUReservationService,
			final @NonNull PickingCandidateService pickingCandidateService,
			//
			final @NonNull PickingJob pickingJob,
			final @NonNull PickingJobStepId pickingJobStepId,
			final @NonNull BigDecimal qtyToPickBD,
			final @Nullable QtyRejectedReasonCode qtyRejectedReasonCode)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
		this.pickingCandidateService = pickingCandidateService;

		this.initialPickingJob = pickingJob;
		this.initialStep = initialPickingJob.getStepById(pickingJobStepId);
		this.qtyToPick = Quantity.of(qtyToPickBD, initialStep.getUOM());
		this.qtyRejectedReasonCode = qtyRejectedReasonCode;
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		initialStep.assertNotPicked();

		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		final PickingCandidate pickingCandidate = createAndProcessPickingCandidate();

		final PickingJob pickingJob = initialPickingJob.withChangedStep(
				initialStep.getId(),
				step -> updateStepFromPickingCandidate(step, pickingCandidate));

		pickingJobRepository.save(pickingJob);

		return pickingJob;
	}

	private PickingCandidate createAndProcessPickingCandidate()
	{
		final Quantity qtyNotPicked = initialStep.getQtyToPick().subtract(qtyToPick);
		final QtyRejectedWithReason qtyRejectedWithReason = computeQtyRejectedWithReason(qtyNotPicked, qtyRejectedReasonCode);

		final PickHUResult pickResult = pickingCandidateService.pickHU(PickRequest.builder()
				.shipmentScheduleId(initialStep.getShipmentScheduleId())
				.pickFrom(PickFrom.ofHuId(initialStep.getPickFromHUId()))
				.packToId(HuPackingInstructionsId.VIRTUAL)
				.qtyToPick(qtyToPick)
				.qtyRejected(qtyRejectedWithReason)
				.pickingSlotId(initialPickingJob.getPickingSlotId().orElse(null))
				.autoReview(true)
				.build());

		pickingJobHUReservationService.releaseReservations(initialStep);

		final ProcessPickingCandidatesResult processResult = pickingCandidateService.process(ImmutableSet.of(pickResult.getPickingCandidateId()));

		return processResult.getSinglePickingCandidate();
	}

	private static PickingJobStep updateStepFromPickingCandidate(@NonNull final PickingJobStep step, @NonNull PickingCandidate from)
	{
		final PickingJobStepPickedInfo picked = extractPickedInfo(from);

		return step.reduceWithPickedEvent(picked);
	}

	private static PickingJobStepPickedInfo extractPickedInfo(final @NonNull PickingCandidate from)
	{
		return PickingJobStepPickedInfo.builder()
				.qtyPicked(from.getQtyPicked())
				.qtyRejectedReasonCode(from.getQtyRejected() != null ? from.getQtyRejected().getReasonCode() : null)
				.actualPickedHUId(Objects.requireNonNull(from.getPackedToHuId()))
				.pickingCandidateId(Objects.requireNonNull(from.getId()))
				.build();
	}

	@Nullable
	private static QtyRejectedWithReason computeQtyRejectedWithReason(
			@NonNull final Quantity qtyNotPicked,
			@Nullable final QtyRejectedReasonCode qtyRejectedReasonCode)
	{
		if (qtyNotPicked.signum() < 0)
		{
			throw new AdempiereException("Picking more than required is not allowed");
		}
		else if (qtyNotPicked.signum() == 0)
		{
			return null;
		}
		else // qtyNotPicked > 0
		{
			if (qtyRejectedReasonCode == null)
			{
				throw new AdempiereException("Reject reason code is mandatory when not picking the entire required quantity");
			}
			return QtyRejectedWithReason.of(qtyNotPicked, qtyRejectedReasonCode);
		}
	}

}
