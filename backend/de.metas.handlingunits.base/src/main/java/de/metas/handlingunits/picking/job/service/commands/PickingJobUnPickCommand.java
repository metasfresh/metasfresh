package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.HUIdAndQRCode;
import de.metas.handlingunits.movement.MoveHUCommand;
import de.metas.handlingunits.movement.MoveHURequestItem;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.PickingJobStepUnpickInfo;
import de.metas.handlingunits.picking.job.model.PickingTarget;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PickingJobUnPickCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final HUQRCodesService huQRCodesService;

	//
	// Params
	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final ImmutableListMultimap<PickingJobStepId, StepUnpickInstructions> unpickInstructionsMap;
	@Nullable private final HUQRCode unpickToHU;

	//
	// State
	private final ArrayList<PickingCandidate> unprocessedPickingCandidates = new ArrayList<>();

	@Builder
	private PickingJobUnPickCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingCandidateService pickingCandidateService,
			final @NonNull HUQRCodesService huQRCodesService,
			//
			final @NonNull PickingJob pickingJob,
			final @Nullable PickingJobStepId onlyPickingJobStepId,
			final @Nullable PickingJobStepPickFromKey onlyPickFromKey,
			final @Nullable HUQRCode unpickToHU)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingCandidateService = pickingCandidateService;
		this.huQRCodesService = huQRCodesService;

		this.initialPickingJob = pickingJob;

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

		for (final PickingJobStepPickedToHU pickedToHU : pickedToHUs)
		{
			final PickingCandidate unprocessedPickingCandidate = pickingCandidateService.unprocess(pickedToHU.getPickingCandidateId())
					.getSinglePickingCandidate();
			unprocessedPickingCandidates.add(unprocessedPickingCandidate);
		}

		moveToTargetHU(pickedToHUs);

		return step.reduceWithUnpickEvent(
				pickFromKey,
				PickingJobStepUnpickInfo.ofUnpickedHUs(pickedToHUs)
		);
	}

	private void moveToTargetHU(@NonNull final List<PickingJobStepPickedToHU> pickedToHUs)
	{
		final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList = pickedToHUs.stream()
				.map(PickingJobStepPickedToHU::getActualPickedHU)
				.map(HUInfo::toHUIdAndQRCode)
				.collect(ImmutableSet.toImmutableSet());

		if (unpickToHU == null)
		{
			newHUTransformService().extractToTopLevel(huIdAndQRCodeList);
		}
		else
		{
		final ImmutableList<MoveHURequestItem> requestItems = pickedToHUs.stream()
				.map(PickingJobStepPickedToHU::getActualPickedHUId)
				.map(HUIdAndQRCode::ofHuId)
				.map(MoveHURequestItem::ofHUIdAndQRCode)
				.collect(ImmutableList.toImmutableList());

			MoveHUCommand.builder()
					.huQRCodesService(huQRCodesService)
				.requestItems(requestItems)
					.targetQRCode(unpickToHU.toGlobalQRCode())
					.build()
					.execute();
		}
	}

	private HUTransformService newHUTransformService()
	{
		return HUTransformService.builder()
				.huQRCodesService(huQRCodesService)
				.build();
	}

	private PickingJob reinitializePickingTargetIfDestroyed(final PickingJob pickingJob)
	{
		final PickingTarget pickingTarget = pickingJob.getPickTarget().orElse(null);
		if (pickingTarget == null)
		{
			return pickingJob;
		}

		final HuId luId = pickingTarget.getLuId();
		if (luId == null)
		{
			return pickingJob;
		}

		final I_M_HU lu = handlingUnitsBL.getById(luId);
		if (!handlingUnitsBL.isDestroyed(lu))
		{
			return pickingJob;
		}

		final HuPackingInstructionsIdAndCaption luPI = handlingUnitsBL.getEffectivePackingInstructionsIdAndCaption(lu);
		return pickingJob.withPickTarget(PickingTarget.ofPackingInstructions(luPI));
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
