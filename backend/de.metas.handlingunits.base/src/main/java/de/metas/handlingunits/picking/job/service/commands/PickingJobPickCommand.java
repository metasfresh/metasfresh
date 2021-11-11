package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
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
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;

public class PickingJobPickCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingCandidateService pickingCandidateService;

	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final PickingJobStep initialStep;
	@NonNull private final PickingJobStepPickFromKey pickFromKey;
	@NonNull private final Quantity qtyToPick;
	@Nullable private final QtyRejectedWithReason qtyRejected;

	@Builder
	private PickingJobPickCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingCandidateService pickingCandidateService,
			//
			final @NonNull PickingJob pickingJob,
			final @NonNull PickingJobStepId pickingJobStepId,
			final @Nullable PickingJobStepPickFromKey pickFromKey,
			final @NonNull BigDecimal qtyToPickBD,
			final @Nullable BigDecimal qtyRejectedBD,
			final @Nullable QtyRejectedReasonCode qtyRejectedReasonCode)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingCandidateService = pickingCandidateService;

		this.initialPickingJob = pickingJob;
		this.initialStep = initialPickingJob.getStepById(pickingJobStepId);
		this.pickFromKey = pickFromKey != null ? pickFromKey : PickingJobStepPickFromKey.MAIN;

		final I_C_UOM uom = initialStep.getUOM();
		this.qtyToPick = Quantity.of(qtyToPickBD, uom);

		if (qtyRejectedReasonCode != null)
		{
			final Quantity qtyRejected = qtyRejectedBD != null
					? Quantity.of(qtyRejectedBD, uom)
					: computeQtyRejected(this.initialStep, this.pickFromKey, this.qtyToPick);

			this.qtyRejected = QtyRejectedWithReason.of(qtyRejected, qtyRejectedReasonCode);
		}
		else
		{
			this.qtyRejected = null;
		}
	}

	private static Quantity computeQtyRejected(
			@NonNull PickingJobStep step,
			@NonNull final PickingJobStepPickFromKey pickFromKey,
			@NonNull Quantity qtyPicked)
	{
		if (pickFromKey.isMain())
		{
			return step.getQtyToPick().subtract(qtyPicked);
		}
		else
		{
			// NOTE: because, in case of alternatives, we don't know which is the qty scheduled to pick
			// we cannot calculate the qtyRejected
			throw new AdempiereException("Cannot calculate QtyRejected in case of alternatives");
		}

	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		initialStep.getPickFrom(pickFromKey).assertNotPicked();

		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		final PickingCandidate pickingCandidate = createAndProcessPickingCandidate();

		final PickingJob pickingJob = initialPickingJob.withChangedStep(
				initialStep.getId(),
				step -> updateStepFromPickingCandidate(step, pickFromKey, pickingCandidate));

		pickingJobRepository.save(pickingJob);

		return pickingJob;
	}

	private PickingCandidate createAndProcessPickingCandidate()
	{
		final HuId pickFromHUId = initialStep.getPickFrom(pickFromKey).getPickFromHU().getId();
		final PickHUResult pickResult = pickingCandidateService.pickHU(PickRequest.builder()
				.shipmentScheduleId(initialStep.getShipmentScheduleId())
				.pickFrom(PickFrom.ofHuId(pickFromHUId))
				.packToId(HuPackingInstructionsId.VIRTUAL)
				.qtyToPick(qtyToPick)
				.qtyRejected(qtyRejected)
				.pickingSlotId(initialPickingJob.getPickingSlotId().orElse(null))
				.autoReview(true)
				.build());

		final ProcessPickingCandidatesResult processResult = pickingCandidateService.process(ImmutableSet.of(pickResult.getPickingCandidateId()));

		return processResult.getSinglePickingCandidate();
	}

	private static PickingJobStep updateStepFromPickingCandidate(
			@NonNull final PickingJobStep step,
			@NonNull final PickingJobStepPickFromKey pickFromKey,
			@NonNull PickingCandidate from)
	{
		final PickingJobStepPickedTo picked = extractPickedInfo(from);

		return step.reduceWithPickedEvent(pickFromKey, picked);
	}

	private static PickingJobStepPickedTo extractPickedInfo(final @NonNull PickingCandidate from)
	{
		return PickingJobStepPickedTo.builder()
				.qtyPicked(from.getQtyPicked())
				.qtyRejected(from.getQtyRejected())
				.actualPickedHUId(Objects.requireNonNull(from.getPackedToHuId()))
				.pickingCandidateId(Objects.requireNonNull(from.getId()))
				.build();
	}
}
