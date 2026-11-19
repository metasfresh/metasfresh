package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.movement.HUIdAndQRCode;
import de.metas.handlingunits.movement.MoveHUCommand;
import de.metas.handlingunits.movement.MoveHURequestItem;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.PickingJobStepUnpickInfo;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class PickingJobUnPickCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobShipmentScheduleService shipmentScheduleService;
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final PickingJobHUService huService;

	//
	// Params
	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final PickingJobLineId lineId;
	@NonNull private final ImmutableListMultimap<PickingJobStepId, StepUnpickInstructions> unpickInstructionsMap;
	@Nullable private final HUQRCode unpickToHU;

	//
	// State
	private final ArrayList<PickingCandidate> unprocessedPickingCandidates = new ArrayList<>();

	@Builder
	private PickingJobUnPickCommand(
			final @NonNull PickingJobShipmentScheduleService shipmentScheduleService,
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingCandidateService pickingCandidateService,
			final @NonNull PickingJobHUService huService,
			//
			final @NonNull PickingJob pickingJob,
			final @NonNull PickingJobLineId lineId,
			final @Nullable PickingJobStepId onlyPickingJobStepId,
			final @Nullable PickingJobStepPickFromKey onlyPickFromKey,
			final @Nullable HUQRCode unpickToHU)
	{
		this.shipmentScheduleService = shipmentScheduleService;
		this.pickingJobRepository = pickingJobRepository;
		this.pickingCandidateService = pickingCandidateService;
		this.huService = huService;

		this.initialPickingJob = pickingJob;
		this.lineId = lineId;

		this.unpickToHU = unpickToHU;

		final Stream<StepUnpickInstructions> unpickInstructionsStream;
		if (onlyPickingJobStepId != null)
		{
			Check.assumeNotNull(onlyPickFromKey, "onlyPickFromKey shall be set when onlyPickingJobStepId is set");
			initialPickingJob.getStepById(onlyPickingJobStepId).getPickFrom(onlyPickFromKey).assertPicked();

			unpickInstructionsStream = Stream.of(
					StepUnpickInstructions.builder()
							.stepId(onlyPickingJobStepId)
							.pickFromKey(onlyPickFromKey)
							.build()
			);
		}
		else
		{
			unpickInstructionsStream = initialPickingJob.streamSteps()
					.flatMap(step -> step.getPickFromKeys().stream()
							.filter(pickFromKey -> step.getPickFrom(pickFromKey).isPicked())
							.map(pickFromKey -> StepUnpickInstructions.builder()
									.stepId(step.getId())
									.pickFromKey(pickFromKey)
									.build()));
		}

		this.unpickInstructionsMap = unpickInstructionsStream.collect(ImmutableListMultimap.toImmutableListMultimap(
				StepUnpickInstructions::getStepId,
				unpickInstructions -> unpickInstructions));
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();

		if (!unpickInstructionsMap.isEmpty())
		{
			return trxManager.callInThreadInheritedTrx(this::executeInTrx);
		}
		else
		{
			return initialPickingJob;
		}
	}

	private PickingJob executeInTrx()
	{
		PickingJob pickingJob = initialPickingJob.withChangedSteps(unpickInstructionsMap.keySet(), this::unpickStep);
		pickingJob = reinitializePickingTargetIfDestroyed(pickingJob);
		pickingJobRepository.save(pickingJob);

		pickingCandidateService.deleteDraftPickingCandidates(unprocessedPickingCandidates);

		return pickingJob;
	}

	@Nullable
	private PickingJobStep unpickStep(@NonNull final PickingJobStep step)
	{
		final ImmutableList<StepUnpickInstructions> unpickInstructionsList = this.unpickInstructionsMap.get(step.getId());

		PickingJobStep changedStep = step;
		for (final StepUnpickInstructions unpickInstructions : unpickInstructionsList)
		{
			final PickingJobStepPickFromKey pickFromKey = unpickInstructions.getPickFromKey();
			changedStep = unpickStep(changedStep, pickFromKey);
		}

		if (changedStep.isGeneratedOnFly() && changedStep.isNothingPicked())
		{
			return null;
		}

		return changedStep;
	}

	private PickingJobStep unpickStep(
			@NonNull final PickingJobStep step,
			@NonNull final PickingJobStepPickFromKey pickFromKey)
	{
		final PickingJobStepPickFrom pickFrom = step.getPickFrom(pickFromKey);
		final List<PickingJobStepPickedToHU> pickedToHUs = pickFrom.getPickedTo() != null
				? pickFrom.getPickedTo().getActualPickedHUs()
				: ImmutableList.of();
		if (pickedToHUs.isEmpty())
		{
			return step;
		}

		final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList = extractHuIdAndQRCodes(pickedToHUs);

		// A picked CU in a box (TU) leaves it as a whole: no quantity moves, so nothing rewrites its QtyPicked rows; they keep pointing to
		// the box and the top-level lookup below would not find them. Decided by the booked data (the picked HU), not by the line's mode:
		// a picked aggregated TU is not such a CU and keeps today's handling (split, compensating rows).
		final ShipmentScheduleId shipmentScheduleId = step.getScheduleId().getShipmentScheduleId();
		huIdAndQRCodeList.forEach(huIdAndQRCode -> deleteQtyPickedOfCUInBox(huIdAndQRCode.getHuId(), shipmentScheduleId));

		final List<I_M_HU> topLevelHUs = extractToTopLevelHUs(huIdAndQRCodeList);
		shipmentScheduleService.deleteByTopLevelHUsAndShipmentScheduleId(topLevelHUs, step.getScheduleId().getShipmentScheduleId());
		changeHUStatusFromPickedToActive(topLevelHUs);

		// extractToTopLevel above may have relocated/regenerated the QR codes (and the picked HU can carry
		// surplus QR rows), so the pre-extraction snapshot no longer matches a live M_HU_QRCode. Re-resolve
		// each extracted top-level HU's current QR by its id before moving it to the scanned target.
		moveToTargetHUIfNeeded(toCurrentHuIdAndQRCodes(topLevelHUs));

		return step.reduceWithUnpickEvent(
				pickFromKey,
				PickingJobStepUnpickInfo.ofUnpickedHUs(pickedToHUs)
		);
	}

	private ImmutableSet<HUIdAndQRCode> toCurrentHuIdAndQRCodes(@NonNull final Collection<I_M_HU> hus)
	{
		return hus.stream()
				.map(hu -> {
					final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
					return HUIdAndQRCode.builder()
							.huId(huId)
							.huQRCode(huService.getQRCodeByHuId(huId))
							.build();
				})
				.collect(ImmutableSet.toImmutableSet());
	}

	private void moveToTargetHUIfNeeded(final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList)
	{
		if (unpickToHU == null)
		{
			return;
		}

		MoveHUCommand.builder()
				.huQRCodesService(huService.getHuQRCodesService())
				.requestItems(huIdAndQRCodeList.stream().map(MoveHURequestItem::ofHUIdAndQRCode).collect(ImmutableSet.toImmutableSet()))
				.targetQRCode(unpickToHU.toScannedCode())
				.build()
				.execute();
	}

	private List<I_M_HU> extractToTopLevelHUs(@NonNull final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList)
	{
		final Set<HuId> topLevelHUIds = newHUTransformService().extractToTopLevel(huIdAndQRCodeList);
		return huService.getByIds(topLevelHUIds);
	}

	private void deleteQtyPickedOfCUInBox(@NonNull final HuId huId, @NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		if (huService.isCUInsideBox(huService.getById(huId)))
		{
			shipmentScheduleService.deleteByVHUAndShipmentScheduleId(huId, shipmentScheduleId);
		}
	}

	private static ImmutableSet<HUIdAndQRCode> extractHuIdAndQRCodes(final @NonNull List<PickingJobStepPickedToHU> pickedToHUs)
	{
		return pickedToHUs.stream()
				.map(PickingJobStepPickedToHU::getActualPickedHU)
				.map(HUInfo::toHUIdAndQRCode)
				.collect(ImmutableSet.toImmutableSet());
	}

	private void changeHUStatusFromPickedToActive(final Collection<I_M_HU> topLevelHUs)
	{
		topLevelHUs.forEach(this::changeHUStatusFromPickedToActive);
	}

	private void changeHUStatusFromPickedToActive(final I_M_HU topLevelHU)
	{
		if (X_M_HU.HUSTATUS_Picked.equals(topLevelHU.getHUStatus()))
		{
			huService.setHUStatusActive(topLevelHU);
		}
	}

	private HUTransformService newHUTransformService()
	{
		return HUTransformService.builder()
				.huQRCodesService(huService.getHuQRCodesService())
				.allowedReservedVhuIds(getAllowedReservedVhuIds())
				.build();
	}

	/**
	 * Collects all reserved VHU IDs from all steps being un-picked into a single set.
	 * This intentionally gives the {@link de.metas.handlingunits.allocation.transfer.HUTransformService}
	 * wider permission than strictly necessary (i.e. all steps rather than just the current one),
	 * because the same service instance is reused for the entire un-pick batch and all steps
	 * are being reversed in the same transaction.
	 * <p>
	 * The resulting {@link HUTransformService} instance is single-use: it is created by
	 * {@link #newHUTransformService()} for this un-pick batch only and must not be reused
	 * for unrelated operations, as it would carry over the wider VHU exemption.
	 */
	private ImmutableSet<HuId> getAllowedReservedVhuIds()
	{
		return unpickInstructionsMap.keySet().stream()
				.map(HUReservationDocRef::ofPickingJobStepId)
				.flatMap(docRef -> huService.getVHUIdsByDocumentRef(docRef).stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private PickingJob reinitializePickingTargetIfDestroyed(final PickingJob pickingJob)
	{
		final PickingJobLineId targetLineId = isLineLevelPickTarget(pickingJob) ? lineId : null;
		final PickingJob pickingJobWithLUTarget = pickingJob.withLuPickingTarget(targetLineId, this::reinitializeLUPickingTarget);
		return reinitializeTUPickingTargetIfDestroyed(pickingJobWithLUTarget, targetLineId);
	}

	/**
	 * Un-picking the last CU of the open box (TU) empties it, and the HU framework destroys an emptied TU; a later pick into that target
	 * would fail ("Illegal M_HU.HUStatus change from D to S"). As for the LU target below, the open box becomes a new box of the same box
	 * type, which also keeps the box's GRAI: for the picker the box stays open.
	 */
	@NonNull
	private PickingJob reinitializeTUPickingTargetIfDestroyed(@NonNull final PickingJob pickingJob, @Nullable final PickingJobLineId targetLineId)
	{
		final TUPickingTarget tuPickingTarget = pickingJob.getTuPickingTarget(targetLineId).orElse(null);
		if (tuPickingTarget == null || !tuPickingTarget.isExistingTU())
		{
			return pickingJob;
		}

		final HuId tuId = tuPickingTarget.getTuIdNotNull();
		final I_M_HU tu = huService.getById(tuId);
		if (!huService.isDestroyedOrEmptyStorage(tu))
		{
			return pickingJob;
		}

		final HuPackingInstructionsIdAndCaption tuPI = huService.getEffectivePackingInstructionsIdAndCaption(tu);
		final GRAI grai = huService.getGrais(tuId).noneOrSingleElement();
		return pickingJob.withTuPickingTarget(targetLineId, TUPickingTarget.ofPackingInstructions(tuPI.getId(), tuPI.getCaption(), grai));
	}

	private boolean isLineLevelPickTarget(final PickingJob pickingJob) {return pickingJob.isLineLevelPickTarget();}

	@Nullable
	private LUPickingTarget reinitializeLUPickingTarget(@Nullable final LUPickingTarget luPickingTarget)
	{
		if (luPickingTarget == null)
		{
			return null;
		}

		final HuId luId = luPickingTarget.getLuId();
		if (luId == null)
		{
			return luPickingTarget;
		}

		final I_M_HU lu = huService.getById(luId);
		if (!huService.isDestroyedOrEmptyStorage(lu))
		{
			return luPickingTarget;
		}

		final HuPackingInstructionsIdAndCaption luPI = huService.getEffectivePackingInstructionsIdAndCaption(lu);
		return LUPickingTarget.ofPackingInstructions(luPI);
	}

	//
	//
	//

	@Value
	@Builder
	private static class StepUnpickInstructions
	{
		@NonNull PickingJobStepId stepId;
		@NonNull PickingJobStepPickFromKey pickFromKey;
	}
}
